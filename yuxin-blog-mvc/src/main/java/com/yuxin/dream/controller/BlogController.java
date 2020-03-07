package com.yuxin.dream.controller;

import com.yuxin.dream.mvc.FreemarkerView;
import com.yuxin.dream.mvc.MvcMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName BlogController.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月06日 02:41:00
 */
public class BlogController {
    List<BlogDoc> docs = new ArrayList<>();

    @MvcMapping("/edit")
    public FreemarkerView openEditPage(String user){
        FreemarkerView freemarkerView = new FreemarkerView("edit.ftl");
        freemarkerView.setModel("authorName",user);
        freemarkerView.setModel("user",user);
        return freemarkerView;
    }
    @MvcMapping("/list")
    public FreemarkerView openDocList(String author){
        List<BlogDoc> result = new ArrayList<>();
        if (author != null){
            for (BlogDoc doc :docs) {
                if (author.equals(doc.getAuthor()));
                result.add(doc);
            }
        }else {
            result.addAll(docs);
        }
        FreemarkerView freemarkerView = new FreemarkerView("docList.ftl");
        freemarkerView.setModel("authorName",author);
        freemarkerView.setModel("docs",result);
        return freemarkerView;
    }

    /**
     * 它是基于参数调用的
     * @param title
     * @param author
     * @param content
     * @param resp
     */
    @MvcMapping("/save")
    public void openEditPage(String title, String author, String content, HttpServletResponse resp){
        BlogDoc doc = new BlogDoc(title,author,content, new Date());
        docs.add(doc);
        try{
            //业务代码
            resp.sendRedirect("/list");
           }catch (IOException e){
            throw new RuntimeException(e);
           }
    }
    @MvcMapping("/save2")
    public void save(BlogDoc doc, HttpServletResponse resp){
        docs.add(doc);
        try{
            //业务代码
            resp.sendRedirect("/list");
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}

