package com.hlkj.controller;

import com.hlkj.enums.YesOrNo;
import com.hlkj.pojo.*;
import com.hlkj.service.ItemService;
import com.hlkj.utils.HLKJJSONResult;
import com.hlkj.utils.PagedGridResult;
import com.hlkj.vo.CommentLevelCountsVO;
import com.hlkj.vo.ItemInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "根据评价数量", notes = "根据评价数量", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public HLKJJSONResult getItemComments(@RequestParam String itemId){

        if (StringUtils.isBlank(itemId)){
            return HLKJJSONResult.errorMsg(null);
        }
        CommentLevelCountsVO vo = itemService.queryCommentsCount(itemId);
        return HLKJJSONResult.ok(vo);
    }

    @ApiOperation(value = "分页获取评论列表", notes = "分页获取评论列表", httpMethod = "GET")
    @GetMapping("/comments")
    public HLKJJSONResult comments(@RequestParam String itemId,
                                   @RequestParam Integer level,
                                   @RequestParam Integer page,
                                   @RequestParam Integer pageSize){

        if (StringUtils.isBlank(itemId)){
            return HLKJJSONResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        PagedGridResult pagedGridResult = itemService.queryPagedComments(itemId, level, page, pageSize);
        return HLKJJSONResult.ok(pagedGridResult);
    }

}
