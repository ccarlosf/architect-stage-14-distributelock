package com.example.redissonlock;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RedissonLockApplicationTests {

    @Autowired
    private RedissonClient redisson;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testRedissonLock() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        RedissonClient redisson = Redisson.create(config);

        RLock rLock = redisson.getLock("order");

        try {
            rLock.lock(30, TimeUnit.SECONDS);
            log.info("我获得了锁！！！");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            log.info("我释放了锁！！");
            rLock.unlock();
        }
    }

    @Test
    public void concurrentOrder() throws InterruptedException {
        CountDownLatch cdl = new CountDownLatch(5);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

        ExecutorService es = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 100; i++) {
            es.execute(() -> {
                try {
                    cyclicBarrier.await();
                    log.info("我进入了方法！");
                    RLock rLock = redisson.getLock("order");
                    rLock.lock(30, TimeUnit.SECONDS);
                    log.info("我获得了锁！！！");
                    Thread.sleep(10000);
                    log.info("方法执行完成");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    cdl.countDown();
                }
            });
        }
        cdl.await();
        es.shutdown();
    }

}
