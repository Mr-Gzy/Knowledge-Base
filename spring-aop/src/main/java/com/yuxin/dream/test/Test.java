package com.yuxin.dream.test;

import com.yuxin.dream.config.Appconfig;
import com.yuxin.dream.service.Dao;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName Test.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月08日 23:16:00
 */
public class Test {
    public static void main(String[] args) {
        //读取上下文文件XML或者配置类
        //ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("");
        //spring的初始化操作
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Appconfig.class);
         annotationConfigApplicationContext.start();//启动
         Logger log = LoggerFactory.getLogger("");
//          Dao dao = annotationConfigApplicationContext.getBean(Dao.class);//这里以jdkDynamicAopProxy实现
//          dao.query();
//          dao.query1("args");

        // cglib
        //以cglibAopProxy代理实现  this=CglibAopProxy$DynamicAdvisedInterceptor  IndexDao没有实现接口，它就以CGlib代理实现
        // IndexDao dao = annotationConfigApplicationContext.getBean(IndexDao.class);

        /**
         //测试java底层代理实现的打印出二进制字节码$ProxyYuXin.class，相当于
         Dao dao = (Dao) Proxy.newProxyInstance(Test.class.getClassLoader(),new Class[]{Dao.class},new TestInvocationHandler(new IndexDao()));
         这个方法。然后经由java Proxy类底层native方法加载二进制字节码转换成类
         byte[] proxyClassFile = ProxyGenerator.generateProxyClass(
         proxyName, interfaces, accessFlags);
         try {
         return defineClass0(loader, proxyName,
         proxyClassFile, 0, proxyClassFile.length);

         private static native Class<?> defineClass0(ClassLoader loader, String name,
         byte[] b, int off, int len);

         //第一个方法：下面的是调用java jdk的方法调用的
         byte[] bytes = ProxyGenerator.generateProxyClass("$ProxyYuXin", new Class[]{Dao.class});
         try {
         FileOutputStream fileOutputStream = new FileOutputStream("D:/tmp/$ProxyYuXin.class");//二进制字节码在项目的target中可以解析出来的。
         fileOutputStream.write(bytes);
         fileOutputStream.flush();
         fileOutputStream.close();
         } catch (FileNotFoundException e) {
         e.printStackTrace();
         }catch (Exception e){
         e.printStackTrace();
         }
         */

        /**第二个方法：直接调用Proxy.newProxyInstance的方法
         Dao dao = (Dao) Proxy.newProxyInstance(Test.class.getClassLoader(),new Class[]{Dao.class},new TestInvocationHandler(new IndexDao()));
         dao.query();
         */
    }
}

