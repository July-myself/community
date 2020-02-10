package com.july.community.mapper;

import com.july.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PublishMapper {

    @Insert("insert into tbl_question(title,description,time_create,time_modified,creator,tag) values(#{title},#{description},#{timeCreate},#{timeModified},#{creator},#{tag})")
     void create(Question question);
}
