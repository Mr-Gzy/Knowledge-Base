package com.yuxin.juc.demo.mini.binding;

import com.yuxin.juc.demo.mini.session.DefaultSqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName MapperProxy.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月29日 01:59:00
 */
public class MapperProxy<T> implements InvocationHandler {
    private final DefaultSqlSession sqlSession;
    private  final Class<T> mapperInterface;
    //构造方法
    public MapperProxy(DefaultSqlSession sqlSession, Class<T> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
        //通过反射获取mapper接口名和方法名
        MapperMethod mapperMethod = sqlSession.getConfiguration().getMapperRegistry()
                .getKnownMappers().get(method.getDeclaringClass().getName()+ "." +method.getName());
        if (mapperMethod != null){
            return sqlSession.selectOne(mapperMethod,String.valueOf(args[0]));
        }
        return method.invoke(proxy,args);
    }
}

