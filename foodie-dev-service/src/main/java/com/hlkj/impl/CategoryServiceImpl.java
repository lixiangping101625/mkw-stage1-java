package com.hlkj.impl;

import com.hlkj.mapper.CategoryMapper;
import com.hlkj.pojo.Category;
import com.hlkj.service.CategoryService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> listCategoryLevel1() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        //建议新建枚举类。尽量杜绝魔法值
        criteria.andEqualTo("type", 1);
        List<Category> categories = categoryMapper.selectByExample(example);
        return categories;
    }

//    @Override
//    public List<Category> listCategorySub(Integer pId) {
//        Example example = new Example(Category.class);
//        Example.Criteria criteria = example.createCriteria();
//        //建议新建枚举类。尽量杜绝魔法值
//        criteria.andEqualTo("fatherId", pId);
//        List<Category> categories = categoryMapper.selectByExample(example);
//        return categories;
//    }
}
