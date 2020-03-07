package com.yuxin.dream.mvc;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName FreemarkeView.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月06日 02:43:00
 */
@Data
public class FreemarkerView {
      private String ftlPath;

    private Map<String ,Object> models = new HashMap<>();

    public FreemarkerView(String ftlPath){
        this.ftlPath = ftlPath;
    }

    public FreemarkerView(String ftlPath, Map<String,Object>model){
        this.ftlPath =ftlPath;
        this.models = model;
    }

    public void setModel(String key,Object model){
        models.put(key,model);
    }
    public void removeModel(String key) {
        models.remove(key);
    }
    public String getFtlPath(){
        return ftlPath;
    }

    public void setFtlPath(String ftlPath) {
        this.ftlPath = ftlPath;
    }

    public Map<String,Object>getModels(){
        return models;
    }

    public void setModels(Map<String, Object> models) {
        this.models = models;
    }
}

