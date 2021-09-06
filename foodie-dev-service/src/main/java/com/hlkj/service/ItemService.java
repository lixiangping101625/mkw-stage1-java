package com.hlkj.service;

import com.hlkj.pojo.Items;
import com.hlkj.pojo.ItemsImg;
import com.hlkj.pojo.ItemsParam;
import com.hlkj.pojo.ItemsSpec;
import com.hlkj.utils.PagedGridResult;
import com.hlkj.vo.CommentLevelCountsVO;
import com.hlkj.vo.ShopCatVO;

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

    /**
     * 搜索商品:关键字
     * @param keywords
     * @param sort 排序方式： k-默认名称 c-销量降序 p-价格升序
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize);
    /**
     * 搜索商品： 三级分类
     * @param keywords
     * @param sort 排序方式： k-默认名称 c-销量降序 p-价格升序
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult searchItemsByThirdCat(String catId, String sort, Integer page, Integer pageSize);

    /**
     * 根据规格ids查询购物车商品最新信息
     * @param specIds （1,3,5）
     * @return
     */
    List<ShopCatVO> queryItemsBySpecIds(String specIds);

    /**
     * 根据商品规格id获取规格对象
     * @param specId
     * @return
     */
    ItemsSpec queryItemsSpecById(String specId);

    /**
     * 扣除库存
     * @param itemId 规格id
     * @param buyCounts 购买数量
     */
    void decreaseItemStock(String specId, Integer buyCounts);
}
