package com.hlkj.mapper;

import com.hlkj.vo.ItemCommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ItemsMapperCustom {

    List<ItemCommentVO> queryItemComment(@Param("paramsMap") Map<String, Object> map);

}