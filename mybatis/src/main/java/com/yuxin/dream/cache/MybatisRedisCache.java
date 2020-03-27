package com.yuxin.dream.cache;

import com.yuxin.dream.util.SerializeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName MybatisRedisCache.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月27日 01:49:00
 */
@Slf4j
public class MybatisRedisCache implements Cache {
    private Jedis redisClient = createRedis();
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private String id;

    public MybatisRedisCache(final String id){
        if (id == null){
            throw new IllegalArgumentException("Cache instances require an Id");
        }
        log.debug("*********************mybatisRedisCache:id=" + id);
        this.id = id;
    }


    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public int getSize() {
        return Integer.valueOf(redisClient.dbSize().toString());
    }

    @Override
    public void putObject(Object key, Object value) {
        log.debug(">>>>>>>>>>>>>>>>>>>>>putObject:" + key + "=" + value);
        redisClient.set(SerializeUtil.serialize(key.toString()),SerializeUtil.serialize(value));

    }

    @Override
    public Object getObject(Object key) {
        Object value = SerializeUtil.unsterilized(redisClient.get(SerializeUtil.serialize(key.toString())));
        log.debug(">>>>>>>>>>>>>>>>>>>>>>getObject:" +key + "=" +value);
        return value;
    }

    @Override
    public Object removeObject(Object key) {
        return redisClient.expire(SerializeUtil.serialize(key.toString()),500);
    }

    @Override
    public void clear() {
      redisClient.flushDB();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    private static Jedis createRedis() {//简单的redis操作试验，它的其他的问题以后在详解
        JedisPool pool = new JedisPool("127.0.0.1",6379);
        return  pool.getResource();
    }
}

