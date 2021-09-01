package com.hlkj.vo;

/**
 * 6个最新商品的简单vo
 */
public class SimpleItemVO {
    private String itemId;
    private String itemName;
    private String itemUrl;

    @Override
    public String toString() {
        return "SimpleItemVO{" +
                "itemId=" + itemId +
                ", itemName=" + itemName +
                ", itemUrl=" + itemUrl +
                '}';
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }
}
