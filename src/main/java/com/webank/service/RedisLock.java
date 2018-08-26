package com.webank.service;

import lombok.extern.slf4j.Slf4j;
import org.simpleframework.xml.core.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Redis分布式锁
 */
@Component
@Slf4j
public class RedisLock {
    /**
     * 使用redis优点
     * 1、速度快，因为数据存在内存中，查找和操作简单
     * 2、支持丰富的数据类型，五种数据类型：string（字符串），hash（哈希），list（列表），set（集合）及zset(sorted set：有序集合)
     * 3、支持事务，操作都是原子性的，对数据的更改，要么全部执行，要么全部不执行
     * 4、丰富的特性：可用于缓存，消息，按key设置过期时间，过期后将会自动删除
     * redis相比memcached有哪些优势？
     * 1、memcached所有的值均是简单的字符串，redis作为其替代者，支持更为丰富的数据类型
     * 2、redis的速度比memcached快很多
     * 3、redis可以持久化其数据
     */
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 加锁
     *
     * @param key
     * @param value 当前时间+超时时间
     * @return
     */
    public boolean lock(String key, String value) {
        if (redisTemplate.opsForValue().setIfAbsent(key, value)) {
            return true;
        }
        String currentValue = redisTemplate.opsForValue().get(key);
        // 如果锁过期
        if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {
            // 获取上一个锁的时间，两个线程同时来修改value值，只会有一个线程拿到锁
            String oldValue = redisTemplate.opsForValue().getAndSet(key, value);
            return !StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue);
        }
        return false;
    }

    /**
     * 解锁
     *
     * @param key
     * @param value
     */
    public void unlock(String key, String value) {
        try {
            String currentValue = redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
            log.error("【redis分布式锁】解锁异常，{}", e);
        }
    }
}
