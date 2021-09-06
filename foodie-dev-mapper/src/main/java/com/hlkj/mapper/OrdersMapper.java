package com.hlkj.mapper;

import com.hlkj.my.mapper.MyMapper;
import com.hlkj.pojo.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersMapper extends MyMapper<Orders> {
}