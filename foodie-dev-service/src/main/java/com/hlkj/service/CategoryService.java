package com.hlkj.service;

import com.hlkj.pojo.Category;
import com.hlkj.vo.CategoryVO;
import com.hlkj.vo.NewItemsVO;

import java.util.List;

public interface CategoryService {

    /**
     * 获取一级分类列表
     * @return
     */
    List<Category> listCategoryLevel1();

    /**
     * 根据一级分类id查询子分类
     * @param rootCatId 一级分类id
     * @return
     */
    List<CategoryVO> getSubCatList(Integer rootCatId);

    /**
     * 获取一级分类下6个商品
     * @param rootCatId 一级分类id
     * @return
     */
    List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);
}
