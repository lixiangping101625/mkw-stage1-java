package com.hlkj.controller;

import com.hlkj.bo.UserBO;
import com.hlkj.pojo.Users;
import com.hlkj.service.UserService;
import com.hlkj.utils.HLKJJSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/passport")
public class PassportController {

    @Resource
    private UserService userService;

    @GetMapping("/usernameIsExist")
    public HLKJJSONResult usernameIsExist(@RequestParam String username){
        if (StringUtils.isBlank(username)) {
            return HLKJJSONResult.errorMsg("用户名不能为空！");
        }
        boolean b = userService.usernameIsExist(username);
        if (b) {
            return HLKJJSONResult.errorMsg("用户名已存在！");
        }
        return HLKJJSONResult.ok();
    }

    @PostMapping("/regist")
    public HLKJJSONResult regist(@RequestBody UserBO userBO){
        //1、用户名和密码不能为空
        if (StringUtils.isBlank(userBO.getUsername()) ||
                StringUtils.isBlank(userBO.getPassword()) ||
                StringUtils.isBlank(userBO.getConfirmPassword())) {
            return HLKJJSONResult.errorMsg("用户名或密码不呢个好为空！");
        }
        //2、后端判断用户名是否已存在
        boolean b = userService.usernameIsExist(userBO.getUsername());
        if (b) {
            return HLKJJSONResult.errorMsg("用户名已存在！");
        }
        //3、密码长度不能小于6位
        if (userBO.getPassword().length()<6) {
            return HLKJJSONResult.errorMsg("密码长度不能少于6！");
        }
        //4、判断密码和重复密码是否一致
        if (!userBO.getPassword().equals(userBO.getConfirmPassword())){
            return HLKJJSONResult.errorMsg("两次输入密码不一致");
        }
        //5、注册
        Users user = userService.createUser(userBO);
        return HLKJJSONResult.ok(user);
    }

}
