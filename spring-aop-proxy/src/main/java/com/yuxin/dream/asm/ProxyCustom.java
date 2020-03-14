package com.yuxin.dream.asm;


import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;


/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName ProxyCustom.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月13日 23:02:00
 */
public class ProxyCustom {
    public static void main(String[] args) {
        //测试
        System.out.println("文件打印出来了");
    }

    /**
     *
     * @param clazz
     * @param obj
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public static Object getInstance(Class clazz, Object obj) throws IOException,
            ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        //定义
        String line = "\n";
        String tab = "\t";
        //获取类名
        String clazzSimpleName=clazz.getSimpleName();
        //获取包名
        String packageName=clazz.getPackage().getName();
        //获取包的全路径名
        String packageContent="package com.alibb;"+line;
        //获取import引用文件路径
        String importPackage="import "+packageName+"."+clazzSimpleName+";"+line;
        //获取类的接口名
        String classFirstLineContent ="public class $proxy implements "+clazzSimpleName+"{"+line
                +tab+clazzSimpleName+" target;"+line;
        //获取构造函数的内容
        String constructorContent =tab+"public $proxy("+clazzSimpleName+" target) {"+line
                +tab+tab+"this.target=target;"
                +line+tab+"}";
       //获取方法的内容
        String methodsContent="";
        //获取声明的方法
        Method[] methods =clazz.getDeclaredMethods();
        //遍历方法
        for (Method method : methods) {
            //获取返回类型
            String returnType =method.getReturnType().getSimpleName();
            methodsContent+=line+tab+"public "+returnType+" "+method.getName()
                    +"(";
            //获取方法参数类型
            Class[] paramterTypes= method.getParameterTypes();
            int i = 1;
            //获取目标方法参数内容
            String targetMethodArgsContent="";
            for (Class paramClazz : paramterTypes) {
                methodsContent+=paramClazz.getSimpleName()+" arg"+i+",";
                targetMethodArgsContent+="arg"+i+",";
                i++;
            }

            if(i>1) {
                methodsContent =methodsContent.substring(0, methodsContent.length()-1);
                targetMethodArgsContent=targetMethodArgsContent.substring(0,targetMethodArgsContent.length()-1);
            }
            methodsContent+="){"
                    +line+tab+tab+"System.out.println(\"jdk proxy\");";


            if(method.getReturnType().getSimpleName().equals("void")) {
                methodsContent+=line+tab+tab+"target."+method.getName()+"("+targetMethodArgsContent+");"
                        +line+tab+"}";

            }else {
                methodsContent+=line+tab+tab+"return target."+method.getName()+"("+targetMethodArgsContent+");"
                        +line+tab+"}";

            }
        }
          //获取类的内容
        String clazzAllContent =packageContent+importPackage+classFirstLineContent+constructorContent+methodsContent;
        clazzAllContent+=line+"}";

        /**
         * 通过反射动态代理生成java文件，通过IO写如文件
         */
        File file = createClazzFileByName("$proxy");
        FileWriter fw = new FileWriter(file);
        fw.write(clazzAllContent);
        fw.flush();
        fw.close();
        /**
         * 因为java文件是无法被jvm虚拟机识别的，故而需要编译成class文件
         */
        //根据提供定位工具提供程序的方法，获取java编译器
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        //获取java文件标准管理器
        StandardJavaFileManager fileMgr = compiler.getStandardFileManager(null, null, null);
        //获取java文件对象
        Iterable units = fileMgr.getJavaFileObjects(file);
        //获取编译任务
        CompilationTask t = compiler.getTask(null, fileMgr, null, null, null, units);
        t.call();
        fileMgr.close();

        /**
         * 编译后的class文件不在项目中，在磁盘上，在D盘创建临时文件，还需要load一个远程的class到虚拟机，
         * 故而这里使用URLClassLoader把一个远程的class，load到内存
         */
        @SuppressWarnings("rawtypes")
        URL[] urls = new URL[] {new URL("file:d:\\tmp\\")};
        URLClassLoader urlClassLoader = new URLClassLoader(urls);
        Class targetClazz =urlClassLoader.loadClass("com.alibb.$proxy");
        /**
         * 通过反射创建代理对象
         */
        Constructor proxyClzConstructor=targetClazz.getConstructor(clazz);
        Object proxyObject=proxyClzConstructor.newInstance(obj);

        return proxyObject;
    }

    /**
     *
     * @param clazzName
     * @return
     * @throws IOException
     */
    public static File createClazzFileByName(String clazzName) throws IOException {//文件夹提前创建好，输出路径
        File file = new File("d:\\tmp\\com\\alibb\\"+clazzName+".java");
        if(!file.exists()) {
            file.createNewFile();
        }
        return file;
    }
}


