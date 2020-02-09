package com.july.community.mapper;

import com.july.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    @Insert("insert into tbl_user (name,account_id,token,time_create,time_modified) values (#{name},#{accountId},#{token},#{timeCreate},#{timeModified})")
    void insert(User user);
}
