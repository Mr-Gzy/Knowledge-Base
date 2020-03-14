package com.yuxin.dream.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName YuxinAspect.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月09日 01:27:00
 */
@Aspect
@Component  //暂时测试Proxy时去掉这个注解
public class YuxinAspect {
    /**
     * 不同的切入点，但是它的执行粒度不一样，详见aop详解
     */
    @Pointcut("execution(* com.yuxin.dream.dao.*.*(..))")//常用
    public void pointCutExecution() {//这里就是个切入点方法，不需要其它代码
    }

    @Pointcut("within(com.yuxin.dream.dao.IndexDao)")
    public void pointCutWithin() {
    }
    @Pointcut("@within(com.yuxin.dream.annotation.AspectTest)")
    public void pointCutWithinAnno() {
    }

    @Pointcut("target(com.yuxin.dream.dao.IndexDao)")
    public void pointCutTarget() {
    }

    @Pointcut("@annotation(com.yuxin.dream.annotation.YuXin)")
    public void pointCutAnno() {
    }

    @Pointcut("this(com.yuxin.dream.dao.IndexDao)")
    public void pointCutThis() {
    }

    @Pointcut("args(java.lang.String)")//★匹配指定参数类型和指定参数数量的方法，与包名和类名无关
    public void pointCutArgs() {
    }

    /**五种类型切入通知：@Before、@AfterReturning、@AfterThrowing、@After、@Around
      @After("com.yuxin.dream.config.YuxinAspect.pointCutExecution()")
      public void after(){
        System.out.println("after...");
      }
      @AfterThrowing("com.yuxin.dream.config.YuxinAspect.pointCutExecution()")
      public void afterThrowing() {
        System.out.println("AfterThrowing...");
      }
      @Before("com.yuxin.dream.config.YuXinAspect.pointCutExecution()")
      public void before(){
        System.out.println("before...");
      }
     */

   /**测试切入点的
    @Before("pointCutExecution()") //
    public void before(JoinPoint joinPoint) {
        //System.out.println(str);
        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            System.out.println(joinPoint.getArgs()[i]);
        }
        System.out.println("proxy-before");
    }
    @After("pointCutArgs()")
    public void before0(JoinPoint joinPoint) {
        //System.out.println(str);
        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            System.out.println(joinPoint.getArgs()[i]);
        }
        System.out.println("proxy-before");
    }


    @After("pointCutAnno()")//它作用在方法上
    public void before1(JoinPoint joinPoint) {
        //System.out.println(str);
        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            System.out.println(joinPoint.getArgs()[i]);
        }
        System.out.println("proxy-before");
    }

    @After("pointCutThis()")//this是代理的对象，这里就没有代理到IndexDao
    public void before1(JoinPoint joinPoint) {
        //System.out.println(str);
        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            System.out.println(joinPoint.getArgs()[i]);
        }
        System.out.println("proxy-before");
    }

   @After("pointCutWithinAnno()")//基于注解的切入
   public void before1(JoinPoint joinPoint) {
       //System.out.println(str);
       for (int i = 0; i < joinPoint.getArgs().length; i++) {
           System.out.println(joinPoint.getArgs()[i]);
       }
       System.out.println("proxy-before");
   }

   @After("pointCutExecution()&&!pointCutAnno()")//混合使用注解
   public void before1(JoinPoint joinPoint) {
       //System.out.println(str);
       for (int i = 0; i < joinPoint.getArgs().length; i++) {
           System.out.println(joinPoint.getArgs()[i]);
       }
       System.out.println("proxy-before");
   }
    */

   //环绕通知
   @Around("pointCutExecution()&&pointCutAnno()")
   public Object pointCutAround(ProceedingJoinPoint pjp) throws Throwable {
       System.out.println("start around");

       Object[] args = pjp.getArgs();
       String tempArgs = "";
       for (Object arg:args) {
         if (arg.getClass().getSimpleName().equals("String")){
             tempArgs=arg+"\t"+"around "+"\t";
         }
       }
       Object retVal = pjp.proceed(new Object[]{tempArgs});
       System.out.println("end around");
       return retVal;
   }
}

