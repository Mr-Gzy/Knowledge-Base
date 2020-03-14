package com.yuxin.dream.dao;

import com.yuxin.dream.annotation.AspectTest;
import com.yuxin.dream.annotation.YuXin;
import com.yuxin.dream.service.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName IndexDao.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月08日 23:09:00
 */

//@Component
@Service
@AspectTest
public class IndexDao implements  Dao {
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Override
    public void query() {
        namedParameterJdbcTemplate.update("INSERT INTO music(`name`,`singer`,`cover`,`lrc`,`album`)VALUES('刘欢欢','内地流行乐男歌唱家、原创音乐人、音乐教育家','弯弯的月亮','弯弯的月亮 ','弯弯的月亮')",new HashMap<>());
        //System.out.println("dao~~~~query");
         //人为错误，测试事务
        //   System.out.println(1/0);
    }
      @Override
    public void query1(String args) {
        System.out.println(args + "can't execute this method");
    }
}

