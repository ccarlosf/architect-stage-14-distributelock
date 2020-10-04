package com.example.distributezklock.controller;

import com.example.distributezklock.lock.ZkLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
public class ZookeeperController {

    @RequestMapping("zkLock")
    public String zookeeperLock() {
        log.info("我进入了方法！");
        try (ZkLock zkLock = new ZkLock()) {
            if (zkLock.getLock("order")) {
                log.info("我获得了锁");
                Thread.sleep(10000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("方法执行完成！");
        return "方法执行完成！";
    }

}
