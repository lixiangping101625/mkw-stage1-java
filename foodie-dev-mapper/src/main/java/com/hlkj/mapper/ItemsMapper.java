package com.hlkj.mapper;

import com.hlkj.my.mapper.MyMapper;
import com.hlkj.pojo.Items;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemsMapper extends MyMapper<Items> {
}