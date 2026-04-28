package com.greg.gofriend.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.greg.gofriend.model.User;
import com.greg.gofriend.service.UserService;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@Data
public class PreCacheJob {

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Resource
    private RedissonClient redissonClient;

    // 重点用户
    private List<Long> mainUserList = Arrays.asList(4L);

    /**
     * 每天定时对重点用户进行缓存预热
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void preCache(){
        RLock lock  = redissonClient.getLock("gofriend:precachejob:precache:lock");
        try {
            lock.lock();
            // 分布式锁，确保只有一个用户获取锁
            if (lock.tryLock()) {
                log.info("获取锁成功");
                Thread.sleep(20000);
                mainUserList.forEach(userId -> {
                    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                    Page<User> page = userService.page(new Page<>(1, 20), queryWrapper);
                    String redisKey = String.format("gofriend:user:recommend:%s", userId);
                    ValueOperations<Object, Object> valueOperations = redisTemplate.opsForValue();
                    // 写入缓存
                    try {
                        valueOperations.set(redisKey, page, 30000, TimeUnit.MILLISECONDS);
                    } catch (Exception e) {
                        log.error("redis set key error", e);
                    }
                });
            } else {
                log.info("获取锁失败");
            }
        } catch (InterruptedException e) {
            log.error("lock error", e);
        } finally {
            // 只能释放自己的锁
            if (lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }
}
