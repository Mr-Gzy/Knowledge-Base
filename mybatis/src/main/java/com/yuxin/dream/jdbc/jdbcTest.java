package com.yuxin.dream.jdbc;

import com.yuxin.dream.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName jdbc.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月15日 03:24:00
 */
public class jdbcTest {
    public static void main(String[] args) {
        insert("海鸥",15,"15063838383","yuxin is a good company");
        System.out.println("执行完成插入语句");
    }

    static void insert(String name , int  age,String phone,String desc){
        String sql = "insert into user(`username`,`age`,`phone`,`desc`) values(?,?,?,?)";

        Connection connection = DBUtil.open();
        try{
            PreparedStatement prepared = connection.prepareStatement(sql);
            prepared.setString(1,name);
            prepared.setInt(2,age);
            prepared.setString(3,phone);
            prepared.setString(4,desc);

            prepared.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtil.close(connection);
        }
    }
}

