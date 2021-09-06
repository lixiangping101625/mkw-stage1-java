package com.hlkj.mapper;

import com.hlkj.my.mapper.MyMapper;
import com.hlkj.pojo.OrderItems;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderItemsMapper extends MyMapper<OrderItems> {
}