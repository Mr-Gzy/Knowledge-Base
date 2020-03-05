package com.yuxin.dream.service.impl;

import com.yuxin.dream.service.AccountService;
import com.yuxin.dream.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName UserServiceImpl.java
 * @Since 1.0
 * @Description //实现ApplicationContextAware:回调接口 根据上下文获取一个接口调用它的方法，或者是直接注入的方式也好
 * @createTime 2020年03月06日 01:06:00
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    AccountService accountService;
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    UserService userService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void createUser(String name) {
        jdbcTemplate.update("insert into `user`(name)values (?)",name);
        //调用accountService添加用户
        accountService.addAccount(name,10000);
      /*测试
     addAccount(name,10000);
     或者是使用userService直接调用addaccount方法，在UserServiceInpl中@Autowired UserService
     userService.addAccount("yuxin666",10000);
    者是通过配置aop，来实现上下文的调用
        ((UserService)AopContext.currentProxy()).addAccount("yuxin666",10000); */
        //或
        //人为错误
        int i = 1/0;
    }

    /**
     * 实验在UserServiceImpl里直接加入了addAccount方法并加入了事务，这里的spring传播机制，查看事务的回滚机制
     * @param name
     * @param initMoney
     */
//    @Override
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
//    public void addAccount(String name, int initMoney) {
//        String accountid = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
//        jdbcTemplate.update("insert into account(accountName,user,money)values (?,?,?)",accountid,name,initMoney);
//    }

}

