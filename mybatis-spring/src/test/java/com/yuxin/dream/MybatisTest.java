package com.yuxin.dream;


import com.yuxin.dream.mapper.UserMapperTest;
import com.yuxin.dream.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName MybatisTest.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月27日 14:17:00
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/spring*.xml"})
@Slf4j
public class MybatisTest {
    @Autowired
    private UserMapperTest userMapperTest;
    @Test
    public void testUserMapper(){
        User selectUser = userMapperTest .selectUser(1);
        log.info("user:{}",selectUser);
    }
}

