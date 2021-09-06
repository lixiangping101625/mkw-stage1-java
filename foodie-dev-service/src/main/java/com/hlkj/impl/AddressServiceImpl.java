package com.hlkj.impl;

import com.hlkj.bo.AddressBO;
import com.hlkj.enums.YesOrNo;
import com.hlkj.mapper.UserAddressMapper;
import com.hlkj.pojo.UserAddress;
import com.hlkj.service.AddressService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Resource
    private UserAddressMapper userAddressMapper;
    @Resource
    private Sid sid;

    @Override
    public List<UserAddress> queryList(String userId) {

        UserAddress searchDomain = new UserAddress();
        searchDomain.setUserId(userId);

        return userAddressMapper.select(searchDomain);
    }

    @Override
    public Integer addAddress(UserAddress userAddress) {
        //1、查询用户收货地址列表
        UserAddress searchDomain = new UserAddress();
        searchDomain.setUserId(userAddress.getUserId());
        List<UserAddress> addressList = userAddressMapper.select(searchDomain);
        //1-1、暂无收获地址，设置新增地址为默认收货地址
        int isDefault = 0;
        if (addressList==null || addressList.isEmpty() || addressList.size()==0){
            isDefault = 1;
        }
        //2、新增
        userAddress.setId(sid.nextShort());
        userAddress.setIsDefault(isDefault);
        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(new Date());

        return userAddressMapper.insert(userAddress);
    }

    @Override
    public Integer updateAddress(AddressBO addressBO) {
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, userAddress);
        userAddress.setId(addressBO.getAddressId());
        userAddress.setUpdatedTime(new Date());

        return userAddressMapper.updateByPrimaryKeySelective(userAddress);
    }

    @Override
    public Integer deleteAddress(String userId, String addressId) {
        UserAddress delDomain = new UserAddress();
        delDomain.setId(addressId);
        delDomain.setUserId(userId);
        int i = userAddressMapper.delete(delDomain);
        return i;
    }

    @Override
    public void setDefault(String userId, String addressId) {
        //1、将默认地址改为非默认
        UserAddress searchDomain = new UserAddress();
        searchDomain.setUserId(userId);
        searchDomain.setIsDefault(YesOrNo.YES.type);
        UserAddress defaultAddress = userAddressMapper.selectOne(searchDomain);

        if (defaultAddress!=null) {
            defaultAddress.setIsDefault(YesOrNo.NO.type);
            userAddressMapper.updateByPrimaryKeySelective(defaultAddress);
        }
        //2、设置当前地址为默认地址
        UserAddress updateAddress = new UserAddress();
        updateAddress.setUserId(userId);
        updateAddress.setId(addressId);
        updateAddress.setIsDefault(YesOrNo.YES.type);
        userAddressMapper.updateByPrimaryKeySelective(updateAddress);
    }

    @Override
    public UserAddress queryUserAddress(String userId, String addressId) {
        UserAddress searchDomain = new UserAddress();
        searchDomain.setId(addressId);
        searchDomain.setUserId(userId);
        return userAddressMapper.selectOne(searchDomain);
    }

}
