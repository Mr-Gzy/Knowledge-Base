package com.yuxin.dream.service.impl;

import com.yuxin.dream.bean.Account;
import com.yuxin.dream.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName AccountServiceImpl.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月06日 01:02:00
 */
@Service
public class AccountServiceImpl  implements AccountService {
    @Autowired
    JdbcTemplate jdbcTemplate;


    //@Transactional(propagation = Propagation.REQUIRES_NEW)
    //@Transactional(propagation = Propagation.NOT_SUPPORTED)
    //@Transactional(propagation = Propagation.REQUIRES_NEW)
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addAccount(String name, int initMoney) {
        String accountid = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        jdbcTemplate.update("insert into account(accountName,user,money)values (?,?,?)",accountid,name,initMoney);
        //人为错误
        //int i = 1/0;
    }

    @Transactional
    @Override
    public List<Account> queryAccount(String name) {
        List<Account> list = jdbcTemplate.queryForList("select * from account where user =?", Account.class,name);
        Arrays.toString(list.toArray());
        return list;
    }

    @Transactional
    @Override
    public int updateAccount(String name, int money) {
        return jdbcTemplate.update("select * from account set money=money+? where user=?", money, name);
    }
}

