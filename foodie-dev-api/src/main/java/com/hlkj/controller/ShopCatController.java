package com.hlkj.controller;

import com.hlkj.bo.ShopCatBO;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Api(value = "购物车相关api", tags = "购物车相关api")
@RestController
@RequestMapping("/shopcart")
public class ShopCatController {


    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
    @PostMapping("/add")
    public HLKJJSONResult add(@RequestParam String userId,
                              @RequestBody ShopCatBO shopCatBO,
                              HttpServletRequest request,
                              HttpServletResponse response){
        if (StringUtils.isBlank(userId)){
            return HLKJJSONResult.errorMsg("");
        }

        System.out.println(shopCatBO);
        // TODO: 2021/9/5 redis 前端用户在登录的情况下，同步购物车到redis缓存

        return HLKJJSONResult.ok();
    }

}
