package com.yuxin.dream.service;

import com.yuxin.dream.bean.Account;

import java.util.List;

public interface AccountService {
    void addAccount(String name ,int initMoney);
    List<Account> queryAccount(String name);
    int updateAccount(String name,int money);
}
