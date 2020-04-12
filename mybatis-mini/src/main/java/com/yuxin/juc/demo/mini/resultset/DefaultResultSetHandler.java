package com.yuxin.juc.demo.mini.resultset;

import com.yuxin.juc.demo.mini.binding.MapperMethod;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName DefaultResultSetHandler.java
 * @Since 1.0
 * @Description TODO 拿到结果，然后进行反射赋值
 * @createTime 2020年03月29日 02:05:00
 */
public class DefaultResultSetHandler implements ResultSetHandler {

    public <T> T handler(PreparedStatement preparedStatement, MapperMethod mapperMethod)
            throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Object resultObj = new DefaultObjectFactory().create(mapperMethod.getType());
        ResultSet rs = preparedStatement.getResultSet();
        if (rs.next()){
            int i = 0;
            for(Field field: resultObj.getClass().getDeclaredFields()){
                setValue(resultObj,field,rs,i);
            }
        }
        return (T)resultObj;
    }

    private void setValue(Object resultObj, Field field, ResultSet rs, int i)
            throws NoSuchMethodException, SQLException, InvocationTargetException, IllegalAccessException {
        Method setMethod = resultObj.getClass().getMethod("set" + upperCapital(field.getName()), field.getType());
        setMethod.invoke(resultObj,getResult(field,rs));
    }

    private String upperCapital(String name) {
        String first = name.substring(0,1);
        String tail = name.substring(1);
        return first.toUpperCase() +tail;
    }

    private Object getResult(Field field, ResultSet rs) throws SQLException {
        Class<?> type = field.getType();
        if (Integer.class == type){
            return Integer.parseInt(rs.getString(field.getName()));
        }
        if (String.class == type){
            return rs.getString(field.getName());
        }
        if (Long.class == type){
            return Long.parseLong(rs.getString(field.getName()));
        }
        return rs.getString(field.getName());
    }
}

