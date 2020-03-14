package com.yuxin.dream.asm;


import aj.org.objectweb.asm.ClassWriter;
import aj.org.objectweb.asm.MethodVisitor;
import aj.org.objectweb.asm.Opcodes;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName AsmTest.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月13日 23:01:00
 */
public class AsmTest {
    public static void main(String[] args) {
        //测试
        System.out.println("测试完成");
    }

    /**
     *
     * @param className
     * @return
     */
    static ClassWriter createClassWriter(String className) {
        //操作类
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        //声明一个类，使用JDK1.8版本，public的类，父类是UserDao，没有实现任何接口
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, className, null, "com/yuxin/dream/dao/UserDao", null);
        //初始化一个无参的构造函数
        MethodVisitor constructor = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        constructor.visitVarInsn(Opcodes.ALOAD, 0);
        //执行父类的init初始化
        constructor.visitMethodInsn(Opcodes.INVOKESPECIAL, "com/yuxin/dream/dao/UserDao", "<init>", "()V", false);
        //从当前方法返回void
        constructor.visitInsn(Opcodes.RETURN);
        constructor.visitMaxs(1, 1);
        constructor.visitEnd();
        return cw;
    }

    /**
     *
     * @param className
     * @param message
     * @return
     * @throws Exception
     */
    public static byte[] createVoidMethod(String className, String message) throws Exception {
        //注意，这里需要把classname里面的.改成/，如com.asm.Test改成com/asm/Test
        ClassWriter cw = createClassWriter(className.replace('.', '/'));
        //创建run方法
        //()V表示函数，无参数，无返回值
        MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "run", "()V",
                null, null);
        //先获取一个java.io.PrintStream对象
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out",
                "Ljava/io/PrintStream;");
        //将int, float或String型常量值从常量池中推送至栈顶  (此处将message字符串从常量池中推送至栈顶[输出的内容])
        mv.visitLdcInsn(message);
        //执行println方法（执行的是参数为字符串，无返回值的println函数）
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println",
                "(Ljava/lang/String;)V", false);
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();
        return cw.toByteArray();
    }
}

