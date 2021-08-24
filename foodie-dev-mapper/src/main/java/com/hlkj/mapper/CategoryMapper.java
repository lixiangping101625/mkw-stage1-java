package com.hlkj.mapper;

import com.hlkj.my.mapper.MyMapper;
import com.hlkj.pojo.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends MyMapper<Category> {
}