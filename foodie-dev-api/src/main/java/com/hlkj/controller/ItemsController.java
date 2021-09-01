package com.hlkj.controller;

import com.hlkj.enums.YesOrNo;
import com.hlkj.pojo.*;
import com.hlkj.service.ItemService;
import com.hlkj.utils.HLKJJSONResult;
import com.hlkj.vo.ItemInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "商品相关api", tags = "商品相关api")
@RestController
@RequestMapping("/items")
public class ItemsController {

    @Resource
    private ItemService itemService;

    @ApiOperation(value = "获取轮播图列表", notes = "获取轮播图列表", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public HLKJJSONResult info(@PathVariable String itemId){

        if (StringUtils.isBlank(itemId)){
            return HLKJJSONResult.errorMsg(null);
        }

        Items items = itemService.queryByItemId(itemId);
        List<ItemsImg> itemsImgs = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecs = itemService.queryItemSpecList(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);

        ItemInfoVO vo = new ItemInfoVO();
        vo.setItem(items);
        vo.setItemImgList(itemsImgs);
        vo.setItemSpecList(itemsSpecs);
        vo.setItemParams(itemsParam);

        return HLKJJSONResult.ok(vo);
    }

}
