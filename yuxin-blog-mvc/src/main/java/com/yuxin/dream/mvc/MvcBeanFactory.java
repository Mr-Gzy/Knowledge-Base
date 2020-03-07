package com.yuxin.dream.mvc;

import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName MvcBeanFactory.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月06日 02:44:00
 */
public class MvcBeanFactory {

    private ApplicationContext applicationContext;

    public MvcBeanFactory(ApplicationContext applicationContext) {
        Assert.notNull(applicationContext,"argument 'applicationContext' must not be null");
        this.applicationContext = applicationContext;
        //装载bean的名称
        loadApiFromSpringBeans();
    }
    //API接口住的地方
    private HashMap<String, MvcBean> apiMap = new HashMap<String, MvcBean>();

    private void loadApiFromSpringBeans() {
       apiMap.clear();
      String[] names= applicationContext.getBeanDefinitionNames();
      Class<?> type;
        for (String name:names) {
            type = applicationContext.getType(name);
            for (Method m : type.getDeclaredMethods()) {
              MvcMapping MvcMapping=  m.getAnnotation(MvcMapping.class);
              if (MvcMapping != null){
                  addApiItem(MvcMapping,name,m);
              }
            }
        }
    }
   public MvcBean getMvcBean(String urlName){
        return apiMap.get(urlName);
    }

    /**
     * 添加api
     * @param MvcMapping api配置
     * @param beanName   bean在spring context中的名称
     * @param method
     */
    private void addApiItem(MvcMapping MvcMapping, String beanName, Method method) {
        MvcBean apiRun = new MvcBean();
        apiRun.urlName = MvcMapping.value();
        apiRun.targetMethod = method;
        apiRun.targetName= beanName;
        apiRun.context = this.applicationContext;
        //保存
        apiMap.put(MvcMapping.value(),apiRun);
    }

    public boolean containsApi(String urlName,String version){
        return apiMap.containsKey(urlName + "_" + version);
    }
    public ApplicationContext getApplicationContext(){
        return applicationContext;
    }
    //内部类
   public static class MvcBean {
       String urlName; //bit.api.user.getUser
       String targetName;  //ioc bean 名称
       Object target; //UserServiceImpl 实例
       Method targetMethod;  //目标方法 getUser
       ApplicationContext context;

       public Object run(Object... args) throws IllegalAccessException, IllegalArgumentException, IllegalStateException, InvocationTargetException {
           //懒加载
           if (target == null) {
               //spring ioc 容器里面去服务Bean
               target = context.getBean(targetName);
           }
           return targetMethod.invoke(target, args);
       }

       public Class<?>[] getParamTypes() {
           return targetMethod.getParameterTypes();
       }
        public String urlName() {
            return urlName;
        }
       public String getTargetName() {
           return targetName;
       }

       public Method getTargetMethod() {
           return targetMethod;
       }

       public ApplicationContext getContext() {
           return context;
       }

       public Object getTarget() {
           return target;
       }
   }
}