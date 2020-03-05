package com.yuxin.dream.spring.tx;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: review-points
 * @ClassName SpringTransactionExample.java
 * @Since 1.0
 * @Description spring编程式事务示例   	基于API实现事物
 * @createTime 2020年02月24日 21:55:00
 */
public class SpringTransactionExample {
    private static String url = "jdbc:mysql://192.168.87.133:3306/spring_tx";
    private static String user = "root";
    private static String password = "root";

    public static Connection openConnection() throws ClassNotFoundException, SQLException {
        //获取驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //获取连接
        Connection conn = DriverManager.getConnection("jdbc:mysql://192.168.87.133:3306/spring_tx", "root", "root");
        return conn;
    }

    public static void main(String[] args) {
        //获取数据源
        final DataSource ds = new DriverManagerDataSource(url, user, password);
        //创建事务模板
        final TransactionTemplate template = new TransactionTemplate();

        //把数据源设置事务，无论是以注解的方式还是XML的配置文件都是以这种方式实现的
        template.setTransactionManager(new DataSourceTransactionManager(ds));
        //设置回滚事务策略 jdbcTemplate也是基于以下方式实现的
        template.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {
                //获取同一个连接
                Connection conn = DataSourceUtils.getConnection(ds);
                Object savePoint = null;
                /*
                Connection conn = null;
                try {
                    //这里获取的不是同一个连接，重新创建了一个新的连接，所以事务不起作用了。
                    conn= ds.getConnection();
                    //如想要在同一个事务必须都执行完再提交就再同一个事务中了
                    conn.setAutoCommit(false);
                } catch (SQLException e) {
                    e.printStackTrace();
                }*/
                try {
                    {
                        //插入
                        PreparedStatement prepare = conn.prepareStatement("insert into account (accountName,user,money) values(?,?,?)");
                        prepare.setString(1, "111");
                        prepare.setString(2, "aaaa");
                        prepare.setInt(3,10000);
                        prepare.executeUpdate();
                    }
                    //设置保存点
                    savePoint = status.createSavepoint();
                    {
                        //插入
                        PreparedStatement prepare = conn.prepareStatement("insert into account(accountName,user,money)values(?,?,?)");
                        prepare.setString(1, "222");
                        prepare.setString(2, "bbb");
                        prepare.setInt(3,10000);
                        prepare.executeUpdate();
                    }
                    {
                        //更新
                        PreparedStatement prepare = conn.prepareStatement("update account set money=money+1 where user=?");
                        prepare.setString(1, "yuxincompany");
                       // int i = 1/0;
                      //  Assert.isTrue(prepare.executeUpdate() > 0, "");
                    }
                    //在完成所有的操作再提交，在同一个事务中，报错回滚事务
                   // conn.commit();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    System.out.println("更新失败");
                    if (savePoint != null) {
                        //如果是spring管理事务，最终是会提交正确提交的事务的语句，最终会commit
                        status.rollbackToSavepoint(savePoint);
                    } else {
                        status.setRollbackOnly();
                    }
                }
                return null;
            }
        });
    }
}

