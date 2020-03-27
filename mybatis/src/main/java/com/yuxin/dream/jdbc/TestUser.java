package com.yuxin.dream.jdbc;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName User.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月15日 03:25:00
 */
@Data
public class TestUser implements Serializable {
    private Integer id;
    private String username;
    private Integer age;
    private String phone;
    private String desc;

}

