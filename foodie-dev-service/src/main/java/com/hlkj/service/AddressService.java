package com.hlkj.service;


import com.hlkj.bo.AddressBO;
import com.hlkj.pojo.UserAddress;

import java.util.List;

public interface AddressService {

    List<UserAddress> queryList(String userId);

    Integer addAddress(UserAddress userAddress);

    Integer updateAddress(AddressBO addressBO);

    Integer deleteAddress(String userId, String addressId);

    void setDefault(String userId, String addressId);

    UserAddress queryUserAddress(String userId, String addressId);

}
