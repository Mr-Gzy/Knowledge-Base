package com.yuxin.juc.demo.mini.pojo;

import lombok.ToString;

import java.io.Serializable;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName User.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月29日 03:09:00
 */
@ToString
public class User implements Serializable {
    private Long id;

    private String username;

    private Integer age;

    private Long phone;

    private String desc;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }
}

