package com.example.distributelock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.distributelock.dao")
@SpringBootApplication
public class DistributeLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributeLockApplication.class, args);
    }

}
