package com.yuxin.dream.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName SerializeUtil.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月27日 11:19:00
 */
public class SerializeUtil {
    public static byte[] serialize(Object object){
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try{
            //序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
           }catch (Exception e){
            e.printStackTrace();
           }
        return null;
    }
    public static Object unsterilized(byte[] bytes){
        ByteArrayInputStream  bais = null;
        try{
            //发序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
           }catch (Exception e){
            e.printStackTrace();
           }
        return null;
    }
}

