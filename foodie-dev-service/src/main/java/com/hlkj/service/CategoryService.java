package com.hlkj.service;

import com.hlkj.pojo.Category;
import java.util.List;

public interface CategoryService {

    /**
     * 获取一级分类列表
     * @return
     */
    List<Category> listCategoryLevel1();

    /**
     * 获取下级分类列表
     * @param pId 父级id
     * @return
     */
//    List<Category> listCategorySub(Integer pId);
}
