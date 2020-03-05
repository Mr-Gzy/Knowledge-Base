package com.yuxin.dream.spring.tx;


import java.sql.*;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: review-points
 * @ClassName ReadcommitedExample.java
 * @Since 1.0
 * @Description 读已提交：
 * 本例中会出现不可重复读的状况
 * 不可重复读：一个线程里执行两条相同的sql语句，它们查询的结果不同。
 * @createTime 2020年02月24日 21:55:00
 */
public class ReadcommitedExample {
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

    /**
     * //设置手动提交
     *
     * @param accountName
     * @param name
     * @param money
     */
    public static void insert(String accountName, String name, int money) {
        try {
            Connection conn = openConnection();
            // conn.setAutoCommit(false);//手动提交
            PreparedStatement prepare = conn.prepareStatement("insert into account(accountName,user,money)values (?,?,?)");
            prepare.setString(1, accountName);
            prepare.setString(2, name);
            prepare.setInt(3, money);
            prepare.executeUpdate();
            System.out.println("执行插入成功");
            conn.close();
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
                insert("1111", "yuxin", 10000);
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
                    //Connection.TRANSACTION_READ_COMMITTED);//设置事务的隔离级别，解决脏读的问题
                    conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
                    //查询
                    select("yuxin", conn);
                    //唤醒
                    synchronized (lock) {
                        lock.notify();
                    }
                    Thread.sleep(5000);
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

