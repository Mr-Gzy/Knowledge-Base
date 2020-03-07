package com.yuxin.dream.controller;

import com.yuxin.dream.mvc.FreemarkerView;
import com.yuxin.dream.mvc.MvcMapping;
import org.springframework.stereotype.Controller;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName YuXinController.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月06日 02:42:00
 */
@Controller
public class YuXinController {
    @MvcMapping(value = "/yuxin", contentType = "JSON")
    public FreemarkerView openYuXinPage(String name){
        FreemarkerView freemarkerView = new FreemarkerView("yuxin.ftl");
        freemarkerView.setModel("name" ,name);
        return freemarkerView;
    }
}

