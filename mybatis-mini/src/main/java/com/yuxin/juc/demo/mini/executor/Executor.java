package com.yuxin.juc.demo.mini.executor;

import com.yuxin.juc.demo.mini.binding.MapperMethod;

public interface Executor {
    <T> T  query(MapperMethod method , Object parameter) throws Exception;
}
