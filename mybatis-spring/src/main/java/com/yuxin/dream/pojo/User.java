package com.yuxin.dream.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName User.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月27日 15:45:00
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private Integer age;


    private Long phone;

    private String desc;
    }

