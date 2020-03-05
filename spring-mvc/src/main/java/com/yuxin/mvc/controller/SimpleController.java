package com.yuxin.mvc.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: review-points
 * @ClassName SimpleController.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月01日 01:53:00
 */
public class SimpleController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        ModelAndView mv = new ModelAndView("userView");
        mv.addObject("name","基于手动配置springmvc.xml文件的 url 与control 映谢");
        return mv;
    }
}

