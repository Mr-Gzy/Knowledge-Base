package com.yuxin.juc.demo.mini.binding;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName MapperRegistry.java
 * @Since 1.0
 * @Description TODO 从xml配置文件中加载的数据注册到mapperRegistry中
 * @createTime 2020年03月28日 22:54:00
 */

public class MapperRegistry {
    private Map<String , MapperMethod> knownMappers = new HashMap<String ,MapperMethod>();

    public Map<String, MapperMethod> getKnownMappers() {
        return knownMappers;
    }

    public void setKnownMappers(Map<String, MapperMethod> knownMappers) {
        this.knownMappers = knownMappers;
    }
}

