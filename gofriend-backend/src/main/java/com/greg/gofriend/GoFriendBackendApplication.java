package com.greg.gofriend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.greg.gofriend.mapper")
@EnableScheduling
public class GoFriendBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoFriendBackendApplication.class, args);

        // 启动第二个实例
//        new SpringApplicationBuilder(GoFriendBackendApplication.class)
//                .properties("server.port=" + 8081)
//                .run(args);
    }

}
