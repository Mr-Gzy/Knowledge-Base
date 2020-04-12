package com.yuxin.juc.demo.mini;

import com.yuxin.juc.demo.mini.mapper.UserMapper;
import com.yuxin.juc.demo.mini.session.Configuration;
import com.yuxin.juc.demo.mini.session.DefaultSqlSession;
import com.yuxin.juc.demo.mini.session.SqlSessionFactory;
import com.yuxin.juc.demo.mini.session.SqlSessionFactoryBuilder;
import com.yuxin.juc.demo.mini.pojo.User;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName MiniMybatis.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月29日 03:06:00
 */
@Slf4j
public class MiniMybatis {
    public static void main(String[] args) throws IOException {
        //加载配置文件到内存中
        InputStream inputStream = MiniMybatis.class.getClassLoader().getResourceAsStream("mybatis-config-mini.xml");
        Configuration configuration = new Configuration();
        configuration.setInputStream(inputStream);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        DefaultSqlSession sqlSession = (DefaultSqlSession) sqlSessionFactory.openSession(configuration);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.getUser(1);
       log.info("user");
    }
}

