package com.yuxin.dream.test;

import com.yuxin.dream.service.UserService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: review-points
 * @ClassName UserServiceImplTest.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年02月27日 00:55:00
 */
public class UserServiceImplTest {
    @Test
    public void createUser()throws Exception{
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-tx.xml");
        UserService service = context.getBean(UserService.class);
        service.createUser("yuxin");
    }


}

