package com.example.distributelock;

import com.example.distributelock.lock.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DistributeLockApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {
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
                    try (RedisLock redisLock = new RedisLock(redisTemplate, "redisKey", 30)) {
                        if (redisLock.getLock()) {
                            log.info("我进入了锁！！");
                            Thread.sleep(15000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
