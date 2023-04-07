package com.demo.myf.util;

import com.alibaba.csp.sentinel.util.StringUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


/**
 * redis分布式锁工具类
 */
@Component
public class RedisLockUtil {


    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 模拟互斥锁
     * @param key
     * @param value
     * @param exp
     * @return
     */
    public boolean tryLock(String key, String value, long exp) {
        Boolean absent = stringRedisTemplate.opsForValue().setIfAbsent(key, value, exp, TimeUnit.SECONDS);
        if (absent) {
            return true;
        }
        return tryLock(key, value, exp); //如果线程没有获取锁，则在此处循环获取
    }

    /**
     * 释放锁
     * @param key
     * @param value
     */
    public void unLock(String key, String value) {
        String s = stringRedisTemplate.opsForValue().get(key);
        if (StringUtil.equals(s, value)) { //避免锁被其他线程误删
            stringRedisTemplate.delete(key);
        }
    }
}