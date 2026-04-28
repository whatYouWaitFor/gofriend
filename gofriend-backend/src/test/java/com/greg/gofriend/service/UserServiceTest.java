package com.greg.gofriend.service;

import com.greg.gofriend.model.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    private ExecutorService executor = new ThreadPoolExecutor(5, 15, 1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue(50));

    @Test
    void testAddUser() {
        User user = new User();
        user.setUsername("greg11");
        user.setUserAccount("123");
        user.setAvatarUrl("https://cdn.nlark.com/yuque/0/2022/png/307638/1648232637114-d0f0c0c5-c0d7-4c0c-b0c0-c0d7c0d7c0d7.png");
        user.setGender(0);
        user.setUserPassword("<PASSWORD>");
        boolean save = userService.save(user);
        Assert.isTrue(save, "chenggg");
    }

    @Test
    void testRegister() {
        String userAccount = "greg";
        String userPassword = "";
        String checkPassword = "123456";
        String planetCode = "1";
        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "gr";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "greg";
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "gre%$g";
        userPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "greg";
        checkPassword = "123456789";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertTrue(result > 0);

    }

    @Test
    void testLogin() {
        String userAccount = "gr";
        String userPassword = "12345678";
        String checkPassword = "12345678";
        String planetCode = "1";
        User result = userService.userLogin(userAccount, userPassword, null);

    }

    @Test
    void searchUserByTags() {
        List<String> list = Arrays.asList("java", "python");
        List<User> users = userService.searchUserByTags(list);
        Assertions.assertNotNull(users);
    }

    /**
     * 批量插入
     */
    @Test
    public void doInsertUsers() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM = 100000;
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < INSERT_NUM; i++) {
            User user = new User();
            user.setUsername("greg" + i);
            user.setUserAccount("123" + i);
            user.setAvatarUrl("https://cdn.nlark.com/yuque/0/2022/png/307638/1648232637114-d0f0c0c5-c0d7-4c0c-b0c0-c0d7c0d7c0d7.png");
            user.setGender(0);
            user.setUserPassword("<PASSWORD>" + i);
            user.setPhone("123" + i);
            user.setEmail("123" + i + "@qq.com");
            user.setUserStatus(0);
            user.setUserRole(0);
            user.setPlanetCode("1" + i);
            userList.add(user);
        }
        // 批量插入1w条
        userService.saveBatch(userList, 10000);
        stopWatch.stop();
        // 19870
        System.out.println(stopWatch.getTotalTimeMillis());
    }

    @Test
    public void doConcurrencyInsertUsers() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        int batchSize = 5000;
        int j = 0;
        List<CompletableFuture<Void>> futureList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            List<User> userList = new ArrayList<>();
            while (true){
                j++;
                User user = new User();
                user.setUsername("greg" + i);
                user.setUserAccount("123" + i);
                user.setAvatarUrl("https://cdn.nlark.com/yuque/0/2022/png/307638/1648232637114-d0f0c0c5-c0d7-4c0c-b0c0-c0d7c0d7c0d7.png");
                user.setGender(0);
                user.setUserPassword("<PASSWORD>" + i);
                user.setPhone("123" + i);
                user.setEmail("123" + i + "@qq.com");
                user.setUserStatus(0);
                user.setUserRole(0);
                user.setPlanetCode("1" + i);
                userList.add(user);
                if (j % batchSize == 0){
                    break;
                }
            }

            // 异步执行
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                userService.saveBatch(userList, batchSize);
            }, executor);
            futureList.add(future);
        }
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{})).join();
        stopWatch.stop();
        // 9749
        System.out.println(stopWatch.getTotalTimeMillis());
    }
}