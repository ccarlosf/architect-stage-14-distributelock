package com.example.redissonlock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
public class RedissonLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedissonLockApplication.class, args);
    }

}
