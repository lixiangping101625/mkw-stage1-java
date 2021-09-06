package com.hlkj.mapper;

import com.hlkj.vo.ItemCommentVO;
import com.hlkj.vo.SearchItemsVO;
import com.hlkj.vo.ShopCatVO;
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

    /**
     * 获取购物车商品信息
     * @param specIdsList 商品规格id列表
     * @return
     */
    List<ShopCatVO> queryItemsBySpecIds(@Param("paramsList") List specIdsList);

    /**
     * 乐观锁扣除库存
     * @param specId
     * @param buyCounts
     * @return
     */
    int decreaseItemStock(@Param("specId") String specId, @Param("buyCounts") Integer buyCounts);

}