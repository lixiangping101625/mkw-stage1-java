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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
        //?????????????????????????????????
        List<ItemCommentVO> list = itemsMapperCustom.queryItemComment(paramsMap);//??????????????????????????????
        list.forEach(vo -> {
            vo.setNickname(DesensitizationUtil.commonDisplay(vo.getNickname()));
        });
        //??????????????????
        return this.setterPagedGrid(list, page);
    }

    @Override
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {
        HashMap<String, Object> paramsMap = new HashMap<>();
        /**
             -- k: ???????????????name
             -- c: ??????????????????
             -- p: ????????????
         */
        paramsMap.put("keywords", keywords);
        paramsMap.put("sort", sort);

        //mybatis-pagehelper
        PageHelper.startPage(page, pageSize);
        //?????????????????????????????????
        List<SearchItemsVO> list = itemsMapperCustom.searchItems(paramsMap);//??????????????????????????????
        //??????????????????
        return this.setterPagedGrid(list, page);
    }

    @Override
    public PagedGridResult searchItemsByThirdCat(String catId, String sort, Integer page, Integer pageSize) {
        HashMap<String, Object> paramsMap = new HashMap<>();
        /**
             -- k: ???????????????name
             -- c: ??????????????????
             -- p: ????????????
         */
        paramsMap.put("catId", catId);
        paramsMap.put("sort", sort);

        //mybatis-pagehelper
        PageHelper.startPage(page, pageSize);
        //?????????????????????????????????
        List<SearchItemsVO> list = itemsMapperCustom.searchItemsByThirdCat(paramsMap);//??????????????????????????????
        //??????????????????
        return this.setterPagedGrid(list, page);
    }

    @Override
    public List<ShopCatVO> queryItemsBySpecIds(String specIds) {
        String[] ids = specIds.split(",");
        List<String> idList = Arrays.asList(ids);
        List<ShopCatVO> shopCatVOS = itemsMapperCustom.queryItemsBySpecIds(idList);
        return shopCatVOS;
    }

    @Override
    public ItemsSpec queryItemsSpecById(String specId) {

        return itemsSpecMapper.selectByPrimaryKey(specId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void decreaseItemStock(String specId, Integer buyCounts) {
        ItemsSpec itemsSpec = itemsSpecMapper.selectByPrimaryKey(specId);
        Integer stock = itemsSpec.getStock();//10
        //synchronized ?????????????????????????????????,???????????????
        //??????????????????(????????????)
        //???????????? ???????????? ???????????????????????????
        //???????????? zookeeper redis

        //lockUtil.getLock() ??????

        //if (buyCounts > stock){
            //10 - 3- 5 -3 = -1
        //}
        //lockUtil.unLock() ??????
        int i = itemsMapperCustom.decreaseItemStock(specId, buyCounts);

        if (i!=1){
            throw new RuntimeException("?????????????????????????????????!");
        }
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
