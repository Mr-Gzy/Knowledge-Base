package com.yuxin.dream.controller;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName BlogDoc.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月06日 02:42:00
 */

public class BlogDoc implements Serializable {
    private static final long serialVersionUID = -3042686055658047285L;
    private String title;
    private String content;
    private Date createTime;

    public  BlogDoc(){
    }
    public BlogDoc(String title, String author, String content, Date createTime) {
        this.title = title;
        this.author = author;
        this.content= content;
        this.createTime = createTime;
    }
    private String author;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


}

