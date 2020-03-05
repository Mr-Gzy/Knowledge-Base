package com.yuxin.dream.spring;

import lombok.Data;
import org.springframework.beans.factory.FactoryBean;

import java.sql.DriverManager;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: review-points
 * @ClassName DriverFactoryBean.java
 * @Since 1.0
 * @Description 自定义创建Bean，格式、类别
 * @createTime 2020年02月23日 00:33:00
 */
@Data
public class DriverFactoryBean implements FactoryBean {
    private String jdbcUrl;
    @Override
    public Object getObject() throws Exception {
        return DriverManager.getDriver(jdbcUrl);
    }

    @Override
    public Class<?> getObjectType() {
        return java.sql.Driver.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}

