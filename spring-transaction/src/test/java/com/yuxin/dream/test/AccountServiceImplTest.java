package com.yuxin.test;


import com.yuxin.dream.service.AccountService;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: review-points
 * @ClassName AccountServiceImplTest.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年02月27日 00:55:00
 */
public class AccountServiceImplTest {
    @Test
    public void  addAccount()throws Exception{
        ClassPathXmlApplicationContext context= new ClassPathXmlApplicationContext("spring-tx.xml");
        AccountService service = context.getBean(AccountService.class);
        service.addAccount("yuxin",10000);
    }
}

