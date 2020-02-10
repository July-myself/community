package com.july.community.mapper;

import com.july.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into tbl_user (name,account_id,token,time_create,time_modified) values (#{name},#{accountId},#{token},#{timeCreate},#{timeModified})")
    void insert(User user);

    @Select("select * from tbl_user where token = #{token}")
    User findByToken(@Param("token") String token);
}
