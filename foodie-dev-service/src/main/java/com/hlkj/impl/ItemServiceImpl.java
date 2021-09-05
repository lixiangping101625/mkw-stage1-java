package com.hlkj.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hlkj.mapper.*;
import com.hlkj.pojo.*;
import com.hlkj.service.ItemService;
import com.hlkj.utils.DesensitizationUtil;
import com.hlkj.utils.PagedGridResult;
import com.hlkj.vo.CommentLevelCountsVO;
import com.hlkj.vo.ItemCommentVO;
import com.hlkj.vo.SearchItemsVO;
import com.hlkj.vo.ShopCatVO;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    @Resource
    private ItemsMapper itemsMapper;
    @Resource
    private ItemsImgMapper itemsImgMapper;
    @Resource
    private ItemsSpecMapper itemsSpecMapper;
    @Resource
    private ItemsParamMapper itemsParamMapper;
    @Resource
    private ItemsCommentsMapper itemsCommentsMapper;
    @Resource
    private ItemsMapperCustom itemsMapperCustom;

    @Override
    public Items queryByItemId(String itemId) {

        return itemsMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {
        Example example = new Example(ItemsImg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);

        return itemsImgMapper.selectByExample(example);
    }

    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example example = new Example(ItemsSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);

        return itemsSpecMapper.selectByExample(example);
    }

    @Override
    public ItemsParam queryItemParam(String itemId) {
        Example example = new Example(ItemsParam.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);

        return itemsParamMapper.selectOneByExample(example);
    }

    @Override
    public CommentLevelCountsVO queryCommentsCount(String itemId) {

        Integer goodCounts = this.getCommentCounts(itemId, 1);
        Integer normalCounts = this.getCommentCounts(itemId, 2);
        Integer badCounts = this.getCommentCounts(itemId, 3);

        CommentLevelCountsVO vo = new CommentLevelCountsVO();
        vo.setGoodCounts(goodCounts);
        vo.setNormalCounts(normalCounts);
        vo.setBadCounts(badCounts);
        vo.setTotalCounts(goodCounts + normalCounts + badCounts);

        return vo;
    }

    private Integer getCommentCounts(String itemId, Integer level){
        ItemsComments itemsComments = new ItemsComments();
        itemsComments.setItemId(itemId);
        itemsComments.setCommentLevel(level);
        return itemsCommentsMapper.selectCount(itemsComments);
    }

    @Override
    public PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize) {

        HashMap<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("itemId", itemId);
        paramsMap.put("level", level);

        //mybatis-pagehelper
        PageHelper.startPage(page, pageSize);
        //这里已经是分页后的数据
        List<ItemCommentVO> list = itemsMapperCustom.queryItemComment(paramsMap);//一个很基本的关联查询
        list.forEach(vo -> {
            vo.setNickname(DesensitizationUtil.commonDisplay(vo.getNickname()));
        });
        //设置数据返回
        return this.setterPagedGrid(list, page);
    }

    @Override
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {
        HashMap<String, Object> paramsMap = new HashMap<>();
        /**
             -- k: 默认，代表name
             -- c: 代表销量排序
             -- p: 代表价格
         */
        paramsMap.put("keywords", keywords);
        paramsMap.put("sort", sort);

        //mybatis-pagehelper
        PageHelper.startPage(page, pageSize);
        //这里已经是分页后的数据
        List<SearchItemsVO> list = itemsMapperCustom.searchItems(paramsMap);//一个很基本的关联查询
        //设置数据返回
        return this.setterPagedGrid(list, page);
    }

    @Override
    public PagedGridResult searchItemsByThirdCat(String catId, String sort, Integer page, Integer pageSize) {
        HashMap<String, Object> paramsMap = new HashMap<>();
        /**
             -- k: 默认，代表name
             -- c: 代表销量排序
             -- p: 代表价格
         */
        paramsMap.put("catId", catId);
        paramsMap.put("sort", sort);

        //mybatis-pagehelper
        PageHelper.startPage(page, pageSize);
        //这里已经是分页后的数据
        List<SearchItemsVO> list = itemsMapperCustom.searchItemsByThirdCat(paramsMap);//一个很基本的关联查询
        //设置数据返回
        return this.setterPagedGrid(list, page);
    }

    @Override
    public List<ShopCatVO> queryItemsBySpecIds(String specIds) {
        String[] ids = specIds.split(",");
        List<String> idList = Arrays.asList(ids);
        List<ShopCatVO> shopCatVOS = itemsMapperCustom.queryItemsBySpecIds(idList);
        return shopCatVOS;
    }

    private PagedGridResult setterPagedGrid(List<?> list, Integer page){
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());

        return grid;
    }
}
