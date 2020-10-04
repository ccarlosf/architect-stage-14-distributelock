package com.example.distributezklock;

import com.example.distributezklock.lock.ZkLock;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DistributeZkLockApplicationTests {

    @Test
    public void contextLoads() {
    }


    @Test
    public void testZkLock() throws Exception {
        ZkLock zkLock = new ZkLock();
        boolean lock = zkLock.getLock("order");
        log.info("获得锁的结果："+lock);
        zkLock.close();
    }
}
