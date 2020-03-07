package com.yuxin.dream.mvc;


import javax.lang.model.element.NestingKind;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface MvcMapping {
    public String value();
    public String contentType() default "JSON";
}
