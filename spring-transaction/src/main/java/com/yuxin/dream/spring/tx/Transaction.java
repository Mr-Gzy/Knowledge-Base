package com.yuxin.dream.spring.tx;


import com.yuxin.dream.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: review-points
 * @ClassName Transaction.java
 * @Since 1.0
 * @Description 动态代理
 * @createTime 2020年02月24日 21:53:00
 */
public class Transaction {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-tx.xml");
        final UserService userService =context.getBean(UserService.class);

        UserService proxyUserService =(UserService) Proxy.newProxyInstance(Transaction.class.getClassLoader(),
                new Class[]{UserService.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                try{
                    //业务代码
                    System.out.println("开启事务"+method.getName());
                    return method.invoke(userService,args);
                   }finally {
                   //最终执行的逻辑代码
                    System.out.println("关闭事务"+method.getName());
                   }
            }
        });
        proxyUserService.createUser("yuxin666");
       // proxyUserService.addAccount("yuxin666",10000);
    }

}

