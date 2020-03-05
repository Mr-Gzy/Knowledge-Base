package com.yuxin.dream.spring;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: review-points
 * @ClassName HelloSpring.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年02月22日 23:15:00
 */
public class HelloSpring {
    private  String name;
    private String sex;

    public HelloSpring(){
    }
    public HelloSpring(String name ,String sex){
        this.name=name;
        this.sex=sex;
    }
    public static HelloSpring build(String type){
        if ("A".equals(type)){
            return new HelloSpring("weijianhui","1");
        }else if ("B".equals(type)){
            return new HelloSpring("duomeimei","2");
        }else {
            throw new IllegalArgumentException("type must 'A' or 'B'");
        }
    }
}

