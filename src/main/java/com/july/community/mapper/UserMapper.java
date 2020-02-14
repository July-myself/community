package com.july.community.mapper;

import com.july.community.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("insert into tbl_user (name,account_id,token,time_create,time_modified,avatar_url) values (#{name},#{accountId},#{token},#{timeCreate},#{timeModified},#{avatarUrl})")
    void insert(User user);

    @Select("select * from tbl_user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from tbl_user where id = #{id}")
    User findById(@Param("id") Integer id);

    @Select("select * from tbl_user where account_id = #{accountId}")
    User findByAccountId(@Param("accountId") String accountId);

    @Update("update tbl_user set name = #{name},token = #{token},time_modified = #{timeModified},avatar_url = #{avatarUrl}")
    void update(User user);
}
