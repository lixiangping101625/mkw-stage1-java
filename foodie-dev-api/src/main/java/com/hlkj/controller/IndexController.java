package com.hlkj.controller;

import com.hlkj.enums.YesOrNo;
import com.hlkj.pojo.Carousel;
import com.hlkj.pojo.Category;
import com.hlkj.service.CarouselService;
import com.hlkj.service.CategoryService;
import com.hlkj.utils.HLKJJSONResult;
import com.hlkj.vo.CategoryVO;
import com.hlkj.vo.NewItemsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "首页展示相关api", tags = "首页展示相关api")
@RestController
@RequestMapping("/index")
public class IndexController {

    @Resource
    private CarouselService carouselService;
    @Resource
    private CategoryService categoryService;

    @ApiOperation(value = "获取轮播图列表", notes = "获取轮播图列表", httpMethod = "GET")
    @GetMapping("/carousel")
    public HLKJJSONResult listCarousel(){
        List<Carousel> list = carouselService.list(YesOrNo.YES.type);
        return HLKJJSONResult.ok(list);
    }

    @ApiOperation(value = "获取一级分类列表", notes = "获取一级分类列表", httpMethod = "GET")
    @GetMapping("/cats")
    public HLKJJSONResult listCategoryLevel1(){
        List<Category> list = categoryService.listCategoryLevel1();
        return HLKJJSONResult.ok(list);
    }

    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public HLKJJSONResult listCategoryLevel1(@PathVariable Integer rootCatId ){
        if (rootCatId == null){
            return HLKJJSONResult.errorMsg("分类不存在");
        }
        List<CategoryVO> list = categoryService.getSubCatList(rootCatId);
        return HLKJJSONResult.ok(list);
    }

    @ApiOperation(value = "获取一级分类下最新6个商品信息", notes = "获取一级分类下最新6个商品信息", httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public HLKJJSONResult sixNewItems(@PathVariable Integer rootCatId ){
        if (rootCatId == null){
            return HLKJJSONResult.errorMsg("分类不存在");
        }
        List<NewItemsVO> list = categoryService.getSixNewItemsLazy(rootCatId);
        return HLKJJSONResult.ok(list);
    }

}
