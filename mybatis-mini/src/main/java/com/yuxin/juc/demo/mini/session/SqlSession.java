package com.yuxin.juc.demo.mini.session;

import com.yuxin.juc.demo.mini.binding.MapperMethod;

public interface SqlSession {
    <T> T selectOne(MapperMethod mapperMethod, Object statement) throws Exception;
}
