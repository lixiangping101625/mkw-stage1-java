package com.hlkj.impl;

import com.hlkj.bo.SubmitOrderBO;
import com.hlkj.enums.OrderStatusEnum;
import com.hlkj.enums.YesOrNo;
import com.hlkj.mapper.ItemsImgMapper;
import com.hlkj.mapper.OrderItemsMapper;
import com.hlkj.mapper.OrderStatusMapper;
import com.hlkj.mapper.OrdersMapper;
import com.hlkj.pojo.*;
import com.hlkj.service.AddressService;
import com.hlkj.service.ItemService;
import com.hlkj.service.OrderService;
import org.n3r.idworker.Sid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private Sid sid;
    @Resource
    private OrdersMapper ordersMapper;
    @Resource
    private AddressService addressService;
    @Resource
    private ItemService itemService;
    @Resource
    private ItemsImgMapper itemsImgMapper;
    @Resource
    private OrderItemsMapper orderItemsMapper;
    @Resource
    private OrderStatusMapper orderStatusMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String createOrder(SubmitOrderBO orderBO) {
        String userId = orderBO.getUserId();
        String addressId = orderBO.getAddressId();
        String itemSpecIds = orderBO.getItemSpecIds();
        Integer payMethod = orderBO.getPayMethod();
        String leftMsg = orderBO.getLeftMsg();
        //统一设置邮费为0
        Integer postAmount = 0;

        //1、保存订单数据
        Orders order = new Orders();
        String orderId = sid.nextShort();
        order.setId(orderId);
        order.setCreated_time(new Date());
        order.setUpdatedTime(new Date());
        order.setUserId(userId);
        order.setIsComment(YesOrNo.NO.type);
        order.setIsDelete(YesOrNo.NO.type);
        order.setLeftMsg(leftMsg);
        order.setPostAmount(postAmount);
        order.setPayMethod(payMethod);
        //地址信息
        UserAddress address = addressService.queryUserAddress(userId, addressId);
        order.setReceiverAddress(address.getProvince()
                                    +address.getCity()
                                    +address.getDistrict()
                                    +address.getDetail());
        order.setReceiverMobile(address.getMobile());
        order.setReceiverName(address.getReceiver());
        //2、循环itemSpecIds，保存商品数据
        String[] specIds = itemSpecIds.split(",");
        int totalAmount = 0;
        int realPayAmount = 0;
        for (int i = 0; i < specIds.length; i++) {
            String specId = specIds[i];
            ItemsSpec itemsSpec = itemService.queryItemsSpecById(specId);
            // TODO: 2021/9/6 整合redis后从后端啊购物车中获取
            int buyCount = 1;

            totalAmount += itemsSpec.getPriceNormal() * buyCount;
            realPayAmount += itemsSpec.getPriceDiscount() * buyCount;
            //查询商品信息
            String itemId = itemsSpec.getItemId();
            Items items = itemService.queryByItemId(itemId);
            //查询商品图片
            Example example = new Example(ItemsImg.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("itemId", itemId);
            criteria.andEqualTo("isMain", YesOrNo.YES.type);
            ItemsImg itemsImg = itemsImgMapper.selectByExample(example).get(0);

            //创建保存对象
            OrderItems orderItem = new OrderItems();
            orderItem.setId(sid.nextShort());
            orderItem.setOrderId(orderId);
            orderItem.setItemId(itemId);
            orderItem.setItemImg(itemsImg.getUrl());
            orderItem.setItemName(itemsSpec.getName());
            orderItem.setItemSpecId(specId);
            orderItem.setItemSpecName(itemsSpec.getName());
            orderItem.setBuyCounts(buyCount);
            orderItem.setPrice(itemsSpec.getPriceDiscount() * buyCount);//存放的是优惠价格

            orderItemsMapper.insert(orderItem);

            //规格表扣除库存!!!
            itemService.decreaseItemStock(specId, buyCount);
        }
        //根据itemSpecIds计算价格
        order.setTotalAmount(totalAmount);
        order.setRealPayAmount(realPayAmount);
        ordersMapper.insert(order);

        //3、保存订单状态数据
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);//等待付款
        orderStatus.setCreatedTime(new Date());

        orderStatusMapper.insert(orderStatus);

        return orderId;
    }

}
