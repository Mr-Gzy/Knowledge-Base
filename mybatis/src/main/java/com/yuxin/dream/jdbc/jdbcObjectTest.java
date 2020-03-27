package com.yuxin.dream.jdbc;

import com.yuxin.dream.util.DBUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName jdbc1.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月15日 03:25:00
 */
@Slf4j
public class jdbcObjectTest {
    public static void main(String[] args) {
        TestUser user = new TestUser();
        user.setUsername("guozhengyu");
        user.setAge(28);
        user.setPhone("138388888888");
        user.setDesc("yuxin is a big company");
        insert(user);
        //查询
        user=query(1);
        Logger log = LoggerFactory.getLogger(jdbcObjectTest.class);
        log.info("id:{},name:{}",user.getId(),user.getUsername());
        // System.out.println(user.getId());
        // System.out.println(user.getUsername());
    }

    private static void insert(TestUser user){
        String sql = "insert into user(`username`,`age`,`phone`,`desc`)values(?,?,?,?)";
        Connection connection = DBUtil.open();
        try{
            PreparedStatement prepare = connection.prepareStatement(sql);
            prepare.setString(1,user.getUsername());
            prepare.setInt(2,user.getAge());
            prepare.setString(3,user.getPhone());
            prepare.setString(4,user.getDesc());
            prepare.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.close(connection);
        }
    }

    private static TestUser query(int id) {
        String sql="select * from `user` where `id`=?";
        Connection conn = DBUtil.open();
        try {
            PreparedStatement prepare = (PreparedStatement) conn.prepareStatement(sql);
            prepare.setInt(1,id);
            ResultSet res = prepare.executeQuery();
            if (res.next()){
                String name = res.getString(2);
//                int age = res.getInt(3);
//                String phone = res.getString(4);
//                String desc = res.getString(5);
                TestUser user = new TestUser();
                user.setId(id);
                user.setUsername(name);
//                user.setAge(age);
//                user.setPhone(phone);
//                user.setDesc(desc);
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtil.close(conn);
        }
        return null;
    }
}

