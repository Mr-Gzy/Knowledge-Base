package com.yuxin.dream.test;

import com.yuxin.dream.asm.AsmTest;
import com.yuxin.dream.asm.ProxyCustom;
import com.yuxin.dream.config.Appconfig;
import com.yuxin.dream.dao.UserDao;
import com.yuxin.dream.service.Dao;
import com.yuxin.dream.util.YuXinClassLoader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName Test.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月14日 04:13:00
 */
public class TestAop {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Appconfig.class);
        // annotationConfigApplicationContext.start();//启动

//        MemberDao memberDao = (MemberDao) annotationConfigApplicationContext.getBean("dao");
//        memberDao.query();
//        memberDao.update();

          //asm技术，它在cglib源码中被调用，它用来操作字节码
//        long cs = System.currentTimeMillis();
//        YuXinClassLoader yuXinClassLoader = new YuXinClassLoader();
//        Object object = null;
//        try {
//            Class clazz = yuXinClassLoader.generatorClassByName("com.yuxin.dream.dao.AsmDynamic",
//                    AsmTest.createVoidMethod("com.yuxin.dream.dao.AsmDynamic","YuXin"));
//            object = clazz.newInstance();
//            clazz.getDeclaredMethod("run").invoke(object);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

         //jdk动态代理
        long js = System.currentTimeMillis();
        UserDao userDao = new UserDao();
        try {
            //ProxyCustom类是模拟了java动态代理
            Dao dao = (Dao) ProxyCustom.getInstance(Dao.class,userDao);
            dao.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
        long je = System.currentTimeMillis();
        System.out.println(je - js);

    }
}

