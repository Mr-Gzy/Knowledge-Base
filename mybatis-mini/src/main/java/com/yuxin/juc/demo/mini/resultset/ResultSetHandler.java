package com.yuxin.juc.demo.mini.resultset;

import com.yuxin.juc.demo.mini.binding.MapperMethod;
import java.sql.PreparedStatement;

public interface ResultSetHandler {
    public <T> T handler(PreparedStatement preparedStatement, MapperMethod mapperMethod) throws Exception;
}
