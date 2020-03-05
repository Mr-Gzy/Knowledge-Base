package com.yuxin.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: review-points
 * @ClassName RequestMappingController.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月05日 18:33:00
 */
@Controller
public class RequestMappingController {

    @RequestMapping("/yuxin.get")
    public ModelAndView hello(){
        ModelAndView modelAndView = new ModelAndView("userView");
        modelAndView.addObject("name","yuxin");
        return modelAndView;
    }
}

