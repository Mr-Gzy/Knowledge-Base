package com.yuxin.dream.util;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName CglibClassLoader.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月13日 22:37:00
 */
public class YuXinClassLoader extends ClassLoader{

    public Class generatorClassByName(String clazzName,byte b[]){
        return super.defineClass(clazzName,b,0,b.length);
    }
}

