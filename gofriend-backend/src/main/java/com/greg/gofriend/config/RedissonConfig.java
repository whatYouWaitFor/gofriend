package com.greg.gofriend.config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redisson配置
 */
@Configuration
@ConfigurationProperties(prefix = "spring.data.redis")
@Data
public class RedissonConfig {

    private String host;

    private String port;

    @Bean
    public RedissonClient redissonClient() {
        // 创建配置
        String address = String.format("redis://%s:%s", host, port);

        Config config = new Config();
        // Redis单机模式
        config.useSingleServer().setAddress(address).setDatabase(3);
        // Redis集群模式
        //config.useClusterServers().addNodeAddress(address).setDatabase(3);
        // 创建Redisson实例
        return Redisson.create(config);
    }
}
