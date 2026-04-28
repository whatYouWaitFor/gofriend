package com.greg.gofriend;

import com.greg.gofriend.mapper.UserMapper;
import com.greg.gofriend.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class GoFriendBackendApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("hello world");
        Assertions.assertEquals(1, 2);
        System.out.println("hello ");
    }

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        //Assert.isTrue(5 == userList.size(), "");
        userList.forEach(System.out::println);
    }
}
