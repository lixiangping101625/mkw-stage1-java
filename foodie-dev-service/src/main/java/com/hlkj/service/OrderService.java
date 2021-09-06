package com.hlkj.service;

import com.hlkj.bo.SubmitOrderBO;

public interface OrderService {

    /**
     * 新建订单
     * @param orderBO
     * return 订单id
     */
    String createOrder(SubmitOrderBO orderBO);

}
