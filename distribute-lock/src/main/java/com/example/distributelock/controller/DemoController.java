package com.example.distributelock.controller;

import com.example.distributelock.dao.DistributeLockMapper;
import com.example.distributelock.model.DistributeLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@Slf4j
public class DemoController {

    /*private Lock lock = new ReentrantLock();

    @RequestMapping("singleLock")
    public String singleLock() throws Exception {
        log.info("我进入了方法！");
        lock.lock();
        log.info("我进入了锁！");
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();
        return "我已经执行完成！";
    }*/

    @Resource
    private DistributeLockMapper distributeLockMapper;

    @RequestMapping("singleLock")
//    @Transactional(rollbackFor = Exception.class)
    public String singleLock() throws Exception {
        log.info("我进入了方法！");
        DistributeLock distributeLock = distributeLockMapper.selectDistributeLock("demo");
        if (distributeLock==null) throw new Exception("分布式锁找不到");
        log.info("我进入了锁！");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "我已经执行完成！";
    }
}
