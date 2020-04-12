package com.yuxin.juc.demo.mini.executor;

import com.yuxin.juc.demo.mini.binding.MapperMethod;
import com.yuxin.juc.demo.mini.session.Configuration;
import com.yuxin.juc.demo.mini.statement.StatementHandler;
import lombok.extern.slf4j.Slf4j;


/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName SimpleExecutor.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月29日 00:33:00
 */
@Slf4j
public class SimpleExecutor implements Executor{
    private Configuration configuration;

    public SimpleExecutor(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> T query(MapperMethod method , Object parameter) throws Exception {
        log.info("测试通过");
        StatementHandler statementHandler = new StatementHandler(configuration);
        return statementHandler.query(method, parameter);
    }
}

