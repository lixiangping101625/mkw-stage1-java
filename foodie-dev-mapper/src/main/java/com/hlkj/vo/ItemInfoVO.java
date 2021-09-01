package com.hlkj.vo;

import com.hlkj.pojo.Items;
import com.hlkj.pojo.ItemsImg;
import com.hlkj.pojo.ItemsParam;
import com.hlkj.pojo.ItemsSpec;

import java.util.List;

public class ItemInfoVO {

    private Items item;
    private List<ItemsImg> itemImgList;
    private List<ItemsSpec> itemSpecList;
    private ItemsParam itemParams;

    @Override
    public String toString() {
        return "ItemInfoVO{" +
                "item=" + item +
                ", itemImgList=" + itemImgList +
                ", itemSpecList=" + itemSpecList +
                ", itemParams=" + itemParams +
                '}';
    }

    public Items getItem() {
        return item;
    }

    public void setItem(Items item) {
        this.item = item;
    }

    public List<ItemsImg> getItemImgList() {
        return itemImgList;
    }

    public void setItemImgList(List<ItemsImg> itemImgList) {
        this.itemImgList = itemImgList;
    }

    public List<ItemsSpec> getItemSpecList() {
        return itemSpecList;
    }

    public void setItemSpecList(List<ItemsSpec> itemSpecList) {
        this.itemSpecList = itemSpecList;
    }

    public ItemsParam getItemParams() {
        return itemParams;
    }

    public void setItemParams(ItemsParam itemParams) {
        this.itemParams = itemParams;
    }
}
