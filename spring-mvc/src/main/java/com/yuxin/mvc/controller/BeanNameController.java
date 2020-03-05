package com.yuxin.mvc.controller;

import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: review-points
 * @ClassName BeanNameController.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月03日 04:22:00
 */
public class BeanNameController implements HttpRequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("基于ioc name 中以 \"/\" 开头的Bean时,容器自动配置组件 注册至映谢.");
        int i = 1/0;
    }
}

