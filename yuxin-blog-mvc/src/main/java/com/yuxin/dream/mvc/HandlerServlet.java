package com.yuxin.dream.mvc;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName HandlerServlet.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月06日 02:44:00
 */
public class HandlerServlet extends HttpServlet {
    private WebApplicationContext context;
    private MvcBeanFactory beanFactory;
    final ParameterNameDiscoverer parameterUtil = new LocalVariableTableParameterNameDiscoverer();
    private Configuration freemarkerConfig;

    @Override
    public void init() throws ServletException {
        context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        //创建工厂
        beanFactory = new MvcBeanFactory(context);
        Configuration freemarkerConfig = null;
        try {
            //业务代码
            freemarkerConfig = context.getBean(Configuration.class);
        } catch (NoSuchBeanDefinitionException e) {
            e.printStackTrace();
        }
        if (freemarkerConfig == null) {
            freemarkerConfig = new Configuration(Configuration.VERSION_2_3_23);
            freemarkerConfig.setDefaultEncoding("UTF-8");
            freemarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            try {
                //业务代码
                freemarkerConfig.setDirectoryForTemplateLoading(new File(getServletContext().getRealPath("")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.freemarkerConfig = freemarkerConfig;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // super.doGet(req, resp);
        doHandler(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);
        doHandler(req, resp);
    }

    private void doHandler(HttpServletRequest req, HttpServletResponse resp) {
        String uri = req.getServletPath();
        //TODO 静态文件
        if (uri.equals("/favicon.ico")) {
            return;
        }
        MvcBeanFactory.MvcBean mvcBean = beanFactory.getMvcBean(uri);
        if (mvcBean == null) {
            throw new IllegalArgumentException(String.format("not found %s mapping", uri));
        }
        Object[] args = buildParams(mvcBean, req, resp);
        try {
            Object result = mvcBean.run(args);
            processResult(result, resp);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processResult(Object result, HttpServletResponse resp) throws IOException, TemplateException {
        if (result instanceof FreemarkerView) {
            FreemarkerView fview = (FreemarkerView) result;
            Template temp = freemarkerConfig.getTemplate(fview.getFtlPath());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            resp.setContentType("text/html; charset=utf-8");
            resp.setCharacterEncoding("utf-8");
            resp.setStatus(200);
            temp.process(fview.getModels(), resp.getWriter());
        }
        //TODO JSTL VIEW
        //TODO JSON VIEW
    }

    /**
     * 构建参数：获取参数的名称与类型
     *
     * @param mvcBean
     * @param req
     * @param resp
     * @return
     */
    private Object[] buildParams(MvcBeanFactory.MvcBean mvcBean, HttpServletRequest req, HttpServletResponse resp) {
        Method method = mvcBean.getTargetMethod();
        List<String> paramNames = Arrays.asList(parameterUtil.getParameterNames(method));
        Class<?>[] paramTypes = method.getParameterTypes();
        Object[] args = new Object[paramTypes.length];
        for (int i = 0; i < paramNames.size(); i++) {
            if (paramTypes[i].isAssignableFrom(HttpServletRequest.class)) {
                args[i] = req;
            } else if (paramTypes[i].isAssignableFrom(HttpServletResponse.class)) {
                args[i] = resp;
            } else if (paramTypes[i].isAssignableFrom(Serializable.class)) {
                try {
                    Object arg = paramTypes[i].newInstance();
                    while (req.getParameterNames().hasMoreElements()) {
                        String name = (String) req.getParameterNames().nextElement();
                        if (name.startsWith(paramNames.get(i) + ("."))) {
                            Field field = paramTypes[i].getField(paramNames.get(i).split(".")[1]);
                            field.set(arg, convert(req.getParameter(name), field.getType()));
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            } else {
                if (req.getParameter(paramNames.get(i)) == null) {
                    args[i] = null;
                } else {
                    args[i] = convert(req.getParameter(paramNames.get(i)), paramTypes[i]);
                }
            }
        }
        return args;
    }

    /**
     * 类型转换
     *
     * @param val
     * @param targetClass
     * @param <T>
     * @return
     */
    private <T> T convert(String val, Class<?> targetClass) {
        Object result = null;
        if (val == null) {
            return null;
        } else if (Integer.class.equals(targetClass)) {
            result = Integer.parseInt(val.toString());
        } else if (Long.class.equals(targetClass)) {
            result = Long.parseLong(val.toString());
        } else if (Date.class.equals(targetClass)) {
            try {
                result = new SimpleDateFormat("").parse(val);
            } catch (ParseException e) {
                throw new IllegalArgumentException("date format Illegal must be 'yyyy-MM-dd HH:mm:ss'");
            }
        } else if (String.class.equals(targetClass)) {
            result = val;
        }
        //TODO Serializable 进行自动封装
        else {
            System.err.println(String.format("mot support param type %s", targetClass.getName()));
        }
        return (T) result;
    }
}

