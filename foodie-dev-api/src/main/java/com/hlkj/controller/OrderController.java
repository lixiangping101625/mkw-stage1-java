package com.hlkj.controller;

import com.hlkj.bo.SubmitOrderBO;
import com.hlkj.enums.PayMethod;
import com.hlkj.service.OrderService;
import com.hlkj.utils.CookieUtils;
import com.hlkj.utils.HLKJJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "订单相关api", tags = "订单相关api")
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Resource
    private OrderService orderService;

    @ApiOperation(value = "创建订单", notes = "创建订单", httpMethod = "POST")
    @PostMapping("/create")
    public HLKJJSONResult createOrder(@RequestBody SubmitOrderBO orderBO,
                                      HttpServletRequest request,
                                      HttpServletResponse response){

        if (orderBO.getPayMethod() != PayMethod.WECHAT_PAY.type &&
                orderBO.getPayMethod() != PayMethod.ALIPAY.type){
            return HLKJJSONResult.errorMsg("支付方式不支持");
        }

//        System.out.println(orderBO);
        //1、创建订单
        String orderId = orderService.createOrder(orderBO);
        //2、创建订单成功后，移除购物车中已结算的商品
        /**
         * 1001
         * 1002 -> 从redis中移除
         * 1003 -> 从redis中移除
         * 1004
         */
        // TODO: 2021/9/6 整合redis之后，移除购物车中已结算的商品。并且同步的前端
        CookieUtils.setCookie(request, response, "shopcart", "", true);//假设购物车商品全部下单
        //3、准备支付：向支付中心发送当前订单，用于保存支付中心的订单数据

        return HLKJJSONResult.ok(orderId);
    }

}
