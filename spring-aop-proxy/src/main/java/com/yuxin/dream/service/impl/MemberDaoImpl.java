package com.yuxin.dream.service.impl;

import com.yuxin.dream.service.MemberDao;
import org.springframework.stereotype.Component;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName MemberDaoImpl.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月13日 23:03:00
 */
@Component("service")
public class MemberDaoImpl implements MemberDao {
    @Override
    public final  void query() {
        System.out.println("spring-query-logic");
    }

    @Override
    public void update() {
        System.out.println("spring-update-logic");
     }
}

