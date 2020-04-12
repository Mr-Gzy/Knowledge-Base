package com.yuxin.juc.demo.mini.session;

import java.io.IOException;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName SqlSessionFactoryBuilder.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月29日 00:08:00
 */
public class SqlSessionFactoryBuilder {
    public SqlSessionFactory build(Configuration configuration) throws IOException {
        configuration.loadConfigurations();
        return new SqlSessionFactory();
    }
}

