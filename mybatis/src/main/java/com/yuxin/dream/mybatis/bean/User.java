package com.yuxin.dream.mybatis.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName User.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月16日 16:43:00
 */
@Data
public class User implements Serializable {
    private Integer id;

    private String username;

    private Integer age;

    private Long phone;

    private String desc;//如果这里的参数与数据库的不一致，用@Resource来指定

    private static final long serialVersionUID = 1L;
}