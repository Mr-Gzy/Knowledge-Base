package com.yuxin.dream.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: review-points
 * @ClassName SpringIocTest.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年02月22日 23:58:00
 */
public class SpringIocTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ioc = new ClassPathXmlApplicationContext("spring.xml");
       // ioc.getBean(HelloSpring.class);
        ioc.getBean("driver"); //运行debugger时，选中要执行的语句，右键选择Evaluate Expression。
    }
}

