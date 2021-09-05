package com.hlkj.controller;

import com.hlkj.pojo.Items;
import com.hlkj.pojo.ItemsImg;
import com.hlkj.pojo.ItemsParam;
import com.hlkj.pojo.ItemsSpec;
import com.hlkj.service.ItemService;
import com.hlkj.utils.HLKJJSONResult;
import com.hlkj.utils.PagedGridResult;
import com.hlkj.vo.CommentLevelCountsVO;
import com.hlkj.vo.ItemInfoVO;
import com.hlkj.vo.ShopCatVO;
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

    @ApiOperation(value = "分页搜索（关键字）", notes = "分页搜索（关键字）", httpMethod = "GET")
    @GetMapping("/search")
    public HLKJJSONResult searchByKeywords(@RequestParam String keywords,
                                 @RequestParam String sort,
                                 @RequestParam Integer page,
                                 @RequestParam Integer pageSize){

        if (StringUtils.isBlank(keywords)){
            return HLKJJSONResult.errorMsg("请输入搜索关键字");
        }
        if (page == null) {
            page = 1;//建议使用枚举类
        }
        if (pageSize == null) {
            page = 20;//建议使用枚举类
        }
        PagedGridResult pagedGridResult = itemService.searchItems(keywords, sort, page, pageSize);
        return HLKJJSONResult.ok(pagedGridResult);
    }

    @ApiOperation(value = "分页搜索（三级分类）", notes = "分页搜索（三级分类）", httpMethod = "GET")
    @GetMapping("/catItems")
    public HLKJJSONResult searchByThirdCatId(@RequestParam String catId,
                                 @RequestParam String sort,
                                 @RequestParam Integer page,
                                 @RequestParam Integer pageSize){

        if (StringUtils.isBlank(catId)){
            return HLKJJSONResult.errorMsg("请选择三级分类");
        }
        if (page == null) {
            page = 1;//建议使用枚举类
        }
        if (pageSize == null) {
            page = 20;//建议使用枚举类
        }
        PagedGridResult pagedGridResult = itemService.searchItemsByThirdCat(catId, sort, page, pageSize);
        return HLKJJSONResult.ok(pagedGridResult);
    }

    /**
     *
     * @param itemSpecIds 拼接的规格id
     * @return
     */
    @ApiOperation(value = "根据商品规格ids查询商品最新数据", notes = "根据商品规格ids查询商品最新数据", httpMethod = "GET")
    @GetMapping("/refresh")
    public HLKJJSONResult queryItemsBySpecIds(@RequestParam String itemSpecIds){

        if (StringUtils.isBlank(itemSpecIds)){
            return HLKJJSONResult.errorMsg("参数错误");
        }
        List<ShopCatVO> shopCatVOS = itemService.queryItemsBySpecIds(itemSpecIds);
        return HLKJJSONResult.ok(shopCatVOS);
    }

}
