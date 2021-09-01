package com.hlkj.impl;

import com.hlkj.mapper.CategoryMapper;
import com.hlkj.mapper.CategoryMapperCustom;
import com.hlkj.pojo.Category;
import com.hlkj.service.CategoryService;
import com.hlkj.vo.CategoryVO;
import com.hlkj.vo.NewItemsVO;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private CategoryMapperCustom categoryMapperCustom;

    @Override
    public List<Category> listCategoryLevel1() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        //建议新建枚举类。尽量杜绝魔法值
        criteria.andEqualTo("type", 1);
        List<Category> categories = categoryMapper.selectByExample(example);
        return categories;
    }

    @Override
    public List<CategoryVO> getSubCatList(Integer rootCatId) {
        List<CategoryVO> subCatList = categoryMapperCustom.getSubCatList(rootCatId);
        return subCatList;
    }

    @Override
    public List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("rootCatId", rootCatId);
        return categoryMapperCustom.getSixNewItemsLazy(map);
    }

}
