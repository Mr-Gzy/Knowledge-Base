package com.yuxin.dream.mybatis.plugin;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;


/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName SqlPrintInterceptor.java
 * @Since 1.0
 * @Description TODO mybatis要执行的sql拦截打印出来,计算它执行的耗时.使用了责任链模式
 * @createTime 2020年03月16日 23:25:00
 */

@Intercepts({
        //签名定义了它执行的
        @Signature(type = Executor.class,method = "query",args = {MappedStatement.class,Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class,method = "update",args = {MappedStatement.class,Object.class})
})
public class SqlPrintInterceptor implements Interceptor {
    private static final Logger log = LoggerFactory.getLogger(SqlPrintInterceptor.class);
    private static final DateFormat DATA_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameterObject = null;
        if (invocation.getArgs().length > 1){
            parameterObject = invocation.getArgs()[1];
        }
        long start = System.currentTimeMillis();
        Object result = invocation.proceed();
        String statementId = mappedStatement.getId();
        BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
        Configuration configuration = mappedStatement.getConfiguration();
        String sql = getSql(boundSql,parameterObject,configuration);

        long end = System.currentTimeMillis();
        long timing = end - start;
        if (log.isInfoEnabled()){
            log.info("执行sql耗时:" + timing + "ms" + "-id:" + statementId + "-Sql:");
            log.info(" " + sql);
        }
        return result;
    }

    private String getSql(BoundSql boundSql, Object parameterObject, Configuration configuration) {
        String sql = boundSql.getSql().replaceAll("[\\s]+", "");
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        if (parameterMappings != null){
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT){
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    if (boundSql.hasAdditionalParameter(propertyName)){
                        value = boundSql.getAdditionalParameter(propertyName);
                    }else if (parameterObject == null){
                        value = null;
                    }else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())){
                        value=parameterObject;
                    }else {
                        MetaObject metaObject = configuration.newMetaObject(parameterObject);
                        value = metaObject.getValue(propertyName);
                    }
                    sql=replacePlaceholder(sql,value);
                }
            }
        }
        return sql;
    }

    private String replacePlaceholder(String sql, Object propertyValue) {
        String result;
        if (propertyValue != null){
            if (propertyValue instanceof String){
                result = "'" + propertyValue + "'";
            }else if (propertyValue instanceof Date){
                result = "'" +DATA_FORMAT.format(propertyValue) + "'";
            }else {
                result = propertyValue.toString();
            }
        }else {
            result = "null";
        }
        return sql.replaceFirst("\\?", Matcher.quoteReplacement(result));
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor){
            return Plugin.wrap(target,this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }

}

