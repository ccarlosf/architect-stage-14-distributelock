package com.example.redissonlock.service;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class SchedulerService {
    @Autowired
    private RedissonClient redisson;

    @Scheduled(cron = "0/5 * * * * ?")
    public void sendSms(){

        RLock rLock = redisson.getLock("order");
        try {
            rLock.lock(30, TimeUnit.SECONDS);
            log.info("向138xxxxxxxx发送短信！");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            rLock.unlock();
        }

    }

}
