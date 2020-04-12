package com.yuxin.juc.demo.mini.binding;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName MapperMethod.java
 * @Since 1.0
 * @Description TODO 把解析的sql(UserMapper)加载在MapperMethod类中
 * @createTime 2020年03月28日 23:10:00
 */
public class MapperMethod <T>{
    private String sql;
    private Class<T> type;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Class<T> getType() {
        return type;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

    public MapperMethod() {
    }

    public MapperMethod(String sql, Class<T> type) {
        this.sql = sql;
        this.type = type;
    }
}

