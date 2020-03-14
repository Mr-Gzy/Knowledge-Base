package com.yuxin.dream.mapper;

import org.apache.ibatis.annotations.Insert;

public interface MusicMapper {
    @Insert("INSERT INTO music(`name`,`singer`,`cover`,`lrc`,`album`)VALUES('刀郎','内地男歌手','西海情歌','西海情歌','刀郎Ⅲ')")
    public  void save();
}
