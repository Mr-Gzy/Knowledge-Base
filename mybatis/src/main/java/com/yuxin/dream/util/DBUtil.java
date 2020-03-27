package com.yuxin.dream.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName DbUtil.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月15日 03:24:00
 */
public class DBUtil {

    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    static{
        driver ="com.mysql.cj.jdbc.Driver";
        url="jdbc:mysql://127.0.0.1/yuxin-jdbc?serverTimezone=GMT";
        username="root";
        password="root";
    }
    public static Connection open() {
        try{
            Class.forName(driver);
            return (Connection) DriverManager.getConnection(url,username,password);
        }catch (Exception e){
            System.out.println("数据库连接失败！");
            e.printStackTrace();
        }
        return null;
    }

    public static void close(Connection connection) {
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

