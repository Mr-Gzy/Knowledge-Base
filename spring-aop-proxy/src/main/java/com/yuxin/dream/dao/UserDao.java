package com.yuxin.dream.dao;

import com.yuxin.dream.service.Dao;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName UserDao.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月13日 23:04:00
 */
public class UserDao implements Dao {

    @Override
    public void save() {
        System.out.println("userDao");
    }
}

