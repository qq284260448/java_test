package com.demo.myf.controller;

import com.demo.myf.annotation.RedisLockAnnotation;
import com.demo.myf.bean.RedisLockTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Book;

@RestController
public class testRedisLock {


    private static final Logger logger = LoggerFactory.getLogger(testRedisLock.class);

    @GetMapping("/testRedisLock")
    @RedisLockAnnotation(typeEnum = RedisLockTypeEnum.ONE, lockTime = 3)
    public Book testRedisLock(@RequestParam("userId") Long userId) {
        try {
            logger.info("睡眠执行前");
            Thread.sleep(10000);
            logger.info("睡眠执行后");
        } catch (Exception e) {
            // log error
            logger.info("has some error", e);
        }
        return null;
    }
}
