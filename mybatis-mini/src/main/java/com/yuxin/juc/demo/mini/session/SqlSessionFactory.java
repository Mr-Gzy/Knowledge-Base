package com.yuxin.juc.demo.mini.session;


import com.yuxin.juc.demo.mini.executor.SimpleExecutor;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName SqlSessionFactory.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月29日 00:06:00
 */
public class SqlSessionFactory {
    public DefaultSqlSession openSession(Configuration configuration){

        return new DefaultSqlSession(configuration ,new SimpleExecutor(configuration));
    }
}
