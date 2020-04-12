package com.yuxin.juc.demo.mini.session;

import com.yuxin.juc.demo.mini.binding.MapperMethod;
import com.yuxin.juc.demo.mini.binding.MapperProxy;
import com.yuxin.juc.demo.mini.executor.Executor;


import java.lang.reflect.Proxy;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName SqlSessionFactory.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月28日 23:59:00
 */
public class DefaultSqlSession implements SqlSession{
    private Configuration configuration;
    private Executor executor;

    public Configuration getConfiguration() {
        return configuration;
    }

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }
    //通过动态代理获取
    public <T> T getMapper(Class<T> type) {
        return (T) Proxy.newProxyInstance(type.getClassLoader(),new Class[] {type},new MapperProxy<>(this,type));
    }

    @Override
    public <T> T selectOne(MapperMethod mapperMethod, Object statement) throws Exception {
        return executor.query(mapperMethod,statement);
    }
}

