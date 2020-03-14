package com.yuxin.dream.test;

import com.yuxin.dream.service.Dao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName TestIvocationHandler.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月10日 02:26:00
 */

/**
 * 这个类是测试java原生jdk Proxy
 */
public class TestInvocationHandler implements InvocationHandler {
    Dao dao;
    public TestInvocationHandler(Dao dao){
      this.dao=dao;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("jdkDynamicProxy");
        //dao.query();
        return null;
    }
}

