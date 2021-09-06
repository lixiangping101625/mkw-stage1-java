package com.hlkj.controller;

import com.hlkj.bo.AddressBO;
import com.hlkj.pojo.UserAddress;
import com.hlkj.service.AddressService;
import com.hlkj.utils.HLKJJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "用户收货地址api", tags = "用户收货地址api")
@RestController
@RequestMapping("/address")
public class AddressController {

    @Resource
    private AddressService addressService;

    @ApiOperation(value = "获取用户收货地址列表", notes = "获取用户收货地址列表", httpMethod = "POST")
    @PostMapping("/list")
    public HLKJJSONResult listAddress(@RequestParam String userId){

        List<UserAddress> list = addressService.queryList(userId);
        return HLKJJSONResult.ok(list);
    }

    @ApiOperation(value = "新增用户收货地址", notes = "新增用户收货地址", httpMethod = "POST")
    @PostMapping("/add")
    public HLKJJSONResult add(@RequestBody UserAddress userAddress){
        // TODO: 2021/9/6 省略了地址信息校验
        Integer i = addressService.addAddress(userAddress);
        return i==1 ? HLKJJSONResult.ok() : HLKJJSONResult.errorMsg("新增失败");
    }

    @ApiOperation(value = "更新用户收货地址", notes = "更新用户收货地址", httpMethod = "POST")
    @PostMapping("/update")
    public HLKJJSONResult update(@RequestBody AddressBO addressBO){
        // TODO: 2021/9/6 省略了地址信息校验
        addressService.updateAddress(addressBO);
        return HLKJJSONResult.ok();
    }

    @ApiOperation(value = "删除用户收货地址", notes = "删除用户收货地址", httpMethod = "POST")
    @PostMapping("/delete")
    public HLKJJSONResult delete(@RequestParam String userId, @RequestParam String addressId){
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return HLKJJSONResult.errorMsg("参数错误");
        }
        Integer i = addressService.deleteAddress(userId, addressId);
        return i>0 ? HLKJJSONResult.ok():HLKJJSONResult.errorMsg("删除失败");
    }

    @ApiOperation(value = "设为默认地址", notes = "设为默认地址", httpMethod = "POST")
    @PostMapping("/setDefalut")
    public HLKJJSONResult setDefalut(@RequestParam String userId, @RequestParam String addressId){
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return HLKJJSONResult.errorMsg("参数错误");
        }
        addressService.setDefault(userId, addressId);
        return HLKJJSONResult.ok();
    }

}
