package com.yuxin.dream.mybatis.mapper;

import com.yuxin.dream.mybatis.bean.User;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * @author Guozhengyu guozy0816@gmail.com
 * @version 1.0.0
 * @program: Knowledge-Base
 * @ClassName UserMapper.java
 * @Since 1.0
 * @Description TODO
 * @createTime 2020年03月16日 16:44:00
 */
public interface UserMapper {
    //测试test()时打开
//    @Results({
//            @Result(property = "`yuxin`",column = "`desc`")
//    })
   @Select("select `id`, `username`, `age`, `phone`, `desc` from `user` where id=#{id}")
    User selectUser(Integer id);
}

