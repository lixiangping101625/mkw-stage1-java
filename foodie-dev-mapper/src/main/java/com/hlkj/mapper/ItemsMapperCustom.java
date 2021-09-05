package com.hlkj.mapper;

import com.hlkj.vo.ItemCommentVO;
import com.hlkj.vo.SearchItemsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ItemsMapperCustom {

    /**
     * 详情
     * @param map
     * @return
     */
    List<ItemCommentVO> queryItemComment(@Param("paramsMap") Map<String, Object> map);

    /**
     * 搜索
     * @param map
     * @return
     */
    List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String, Object> map);
    /**
     * 搜索
     * @param map
     * @return
     */
    List<SearchItemsVO> searchItemsByThirdCat(@Param("paramsMap") Map<String, Object> map);

}