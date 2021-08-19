package com.hlkj.mapper;

import com.hlkj.my.mapper.MyMapper;
import com.hlkj.pojo.Users;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsersMapper extends MyMapper<Users> {
}