package com.yuxin.dream.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName Appconfig.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月08日 23:14:00
 */


@Configuration
@EnableAspectJAutoProxy//默认是false jdkDynamicProxy，而且它需要类有实现接口的情况下
//@EnableAspectJAutoProxy(proxyTargetClass = true)它是cgLibProxy
@ComponentScan("com.yuxin.dream")
@MapperScan("com.yuxin.dream.mapper")
@ImportResource("classpath:spring-tx.xml")
public class Appconfig {


    @Bean
    @Autowired
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;
    }

    @Bean
    public DataSource dataSource(){
        //spring自带的数据源，供学习用
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
         driverManagerDataSource.setUrl("jdbc:mysql://127.0.0.1/test?serverTimezone=GMT");
         driverManagerDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
         driverManagerDataSource.setUsername("root");
         driverManagerDataSource.setPassword("root");
         return driverManagerDataSource;
    }
    @Bean
     public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource){
        NamedParameterJdbcTemplate nameParameterJdbcTemplate  = new NamedParameterJdbcTemplate(dataSource);
        return nameParameterJdbcTemplate;
     }

     @Bean("transactionManager")
     @Autowired
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
         return dataSourceTransactionManager;
     }
}

