package com.hlkj.service;

import com.hlkj.pojo.Items;
import com.hlkj.pojo.ItemsImg;
import com.hlkj.pojo.ItemsParam;
import com.hlkj.pojo.ItemsSpec;
import com.hlkj.utils.PagedGridResult;
import com.hlkj.vo.CommentLevelCountsVO;
import com.hlkj.vo.ItemCommentVO;

import java.util.List;

public interface ItemService {

    /**
     * 根据商品id获取商品信息
     * @param itemId
     * @return
     */
    Items queryByItemId(String itemId);

    /**
     * 根据商品id获取获取图片列表
     * @param itemId
     * @return
     */
    List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品id获取获取规格列表
     * @param itemId
     * @return
     */
    List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品id获取获取商品参数
     * @param itemId
     * @return
     */
    ItemsParam queryItemParam(String itemId);

    /**
     * 根据商品id获取商品评价数量
     * @param itemId
     * @return
     */
    CommentLevelCountsVO queryCommentsCount(String itemId);

    /**
     * 根据商品id查询商品评价
     * @param itemId
     * @param level
     * @return
     */
    PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize);
}
