package com.yuxin.dream.spring.tx;

import java.sql.*;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: review-points
 * @ClassName ReadRepeatableExample.java
 * @Since 1.0
 * @Description 修改：在事务读已提交下也会出现不可重复读的状况
 * @createTime 2020年02月24日 21:54:00
 */
public class ReadRepeatableExample {
    static {
        try {
            openConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static Object lock = new Object();

    public static Connection openConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.87.133:3306/spring_tx", "root", "root");
        return conn;
    }

    public static void update(String user) {
        try {
            Connection conn = openConnection();
            PreparedStatement prepare = conn.prepareStatement("update account set money=money+1 where user =?");
            prepare.setString(1, user);
            prepare.executeUpdate();
            conn.close();
            System.out.println("执行修改成功");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询
     *
     * @param name
     * @param conn
     */
    public static void select(String name, Connection conn) {
        try {
            PreparedStatement prepare = conn.prepareStatement("select * from account where user = ?");
            prepare.setString(1, name);
            ResultSet resultSet = prepare.executeQuery();
            System.out.println("执行查询");
            while (resultSet.next()) {
                for (int i = 1; i <= 4; i++) {
                    System.out.println(resultSet.getString(i) + ",");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Thread run(Runnable runnable) {
        Thread thread1 = new Thread(runnable);
        thread1.start();
        return thread1;
    }

    public static void main(String[] args) {
        //启动插入线程
        Thread t1 = run(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (lock) {
                        lock.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                update("yuxin");
            }
        });
        //启动查询线程,两个查询在同一个事务中
        Thread t2 = run(new Runnable() {
            @Override
            public void run() {
                try {
                    //先查询在插入，休眠
                    Connection conn = openConnection();
                    //设置手动提交
                    conn.setAutoCommit(false);
                    //Connection.TRANSACTION_REPEATABLE_READ);//设置事务的隔离级别，解决不可重复读的问题
                    // conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);//出现不可重复读的现象
                    conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                    //查询
                    select("yuxin", conn);
                    //唤醒
                    synchronized (lock) {
                        lock.notify();
                    }
                    Thread.sleep(1000);
                    //第二次读取到数据不一样，不可重复读
                    select("yuxin", conn);
                    conn.close();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

