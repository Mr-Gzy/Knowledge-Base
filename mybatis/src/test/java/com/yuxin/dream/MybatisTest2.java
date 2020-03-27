package com.yuxin.dream;


import com.yuxin.dream.mapper.UserMapper;
import com.yuxin.dream.pojo.User;
import com.yuxin.dream.pojo.UserExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName MybatisTest.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月16日 22:06:00
 */
@Slf4j
public class MybatisTest2 {
    @Test
    public void testGeneratorMapper() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(1L);
        List<User> userList = mapper.selectByExample(userExample);
        log.info("user:{}",userList.get(0));
    }
}

