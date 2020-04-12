package com.yuxin.juc.demo.mini.statement;

import com.yuxin.juc.demo.mini.binding.MapperMethod;
import com.yuxin.juc.demo.mini.resultset.DefaultResultSetHandler;
import com.yuxin.juc.demo.mini.resultset.ResultSetHandler;
import com.yuxin.juc.demo.mini.session.Configuration;
import com.yuxin.juc.demo.mini.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName StatementHandler.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月29日 02:00:00
 */
public class StatementHandler {
    private Configuration configuration;
    private ResultSetHandler resultSetHandler;

    public StatementHandler() {
    }

    public StatementHandler(Configuration configuration) {
        this.configuration = configuration;
        resultSetHandler = new DefaultResultSetHandler();
    }
    public <T> T query(MapperMethod method , Object parameter) throws Exception {
        Connection connection = DBUtil.open();
        PreparedStatement preparedStatement = connection.prepareStatement
                (String.format(method.getSql(), Integer.parseInt(String.valueOf(parameter))));
        preparedStatement.execute();
        return resultSetHandler.handler(preparedStatement,method);
    }
}

