package com.july.community.mapper;

import com.july.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into tbl_question(title,description,time_create,time_modified,creator,tag) values(#{title},#{description},#{timeCreate},#{timeModified},#{creator},#{tag})")
     void create(Question question);

    @Select("select * from tbl_question limit #{offset} , #{size}")
    List<Question> getList(@Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select * from tbl_question where creator = #{userId} limit #{offset} , #{size} ")
    List<Question> getListByUserId(@Param("userId") Integer id, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from tbl_question")
    Integer count();

    @Select("select count(1) from tbl_question where creator = #{userId}")
    Integer countByUserId(@Param("userId") Integer id);

    @Select("select * from tbl_question where id = #{id}")
    Question getListById(@Param("id") Integer id);
}
