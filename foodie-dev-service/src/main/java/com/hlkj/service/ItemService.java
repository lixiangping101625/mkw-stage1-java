package com.hlkj.service;

import com.hlkj.pojo.Items;
import com.hlkj.pojo.ItemsImg;
import com.hlkj.pojo.ItemsParam;
import com.hlkj.pojo.ItemsSpec;

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
}
