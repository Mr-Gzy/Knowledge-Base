package com.yuxin.juc.demo.mini.session;



import com.yuxin.juc.demo.mini.binding.MapperRegistry;
import com.yuxin.juc.demo.mini.binding.MapperMethod;
import lombok.extern.slf4j.Slf4j;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName Configuration.java
 * @Since 1.0
 * @Description TODO 读取xml文件加载到内存中
 * @createTime 2020年03月28日 22:52:00
 */
@Slf4j
public class Configuration {
    private InputStream inputStream;
    MapperRegistry mapperRegistry = new MapperRegistry();

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }


    public MapperRegistry getMapperRegistry() {
        return mapperRegistry;
    }

    public void setMapperRegistry(MapperRegistry mapperRegistry) {
        this.mapperRegistry = mapperRegistry;
    }

    public Configuration() {
    }

    public Configuration(InputStream inputStream,  MapperRegistry mapperRegistry) {
        this.inputStream = inputStream;
        this.mapperRegistry = mapperRegistry;
    }


    /**通过Dom4j读取配置文件中的信息*/
    public void loadConfigurations() throws IOException {
        try {
            //读取IO流
            Document document = new SAXReader().read(inputStream);
            //获取配置文件元素mappers中的内容
            Element root = document.getRootElement();
            List<Element> mappers = root.element("mappers").elements("mapper");
            //遍历
            for (Element mapper : mappers) {
                if (mapper.attribute("resource") != null){
                    mapperRegistry.setKnownMappers(LoadXMLConfiguration(mapper.attribute("resource").getText()));
                }
                if (mapper.attribute("class") != null){
                }
            }
        }catch (Exception e){
          e.printStackTrace();
          log.error("读取配置文件错误");
        }finally {
            inputStream.close();
        }
    }
    /**通过dom4j读取Mapper.xml中的信息 */
    private Map<String, MapperMethod> LoadXMLConfiguration(String resource) throws IOException {
        Map<String,MapperMethod> map = new HashMap<String, MapperMethod>();
        InputStream in = null;
        try{
           in=this.getClass().getClassLoader().getResourceAsStream(resource);
           Document document = new SAXReader().read(in);
           Element root = document.getRootElement();
           if (root.getName().equalsIgnoreCase("mapper")){
               String namespace = root.attribute("namespace").getText();
               List<Element> selects = root.elements("select");
               for (Element select : selects) {
                   MapperMethod mapperModel = new MapperMethod();
                   mapperModel.setSql(select.getText().trim());
                   mapperModel.setType(Class.forName(select.attribute("resultType").getText()));
                   map.put(namespace + "." + select.attribute("id").getText(),mapperModel);
               }
           }
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (DocumentException e) {
            e.printStackTrace();
        }finally {
         in.close();
        }
        return map;
    }
}

