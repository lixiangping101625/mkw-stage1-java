package com.hlkj.controller;

import com.hlkj.bo.UserBO;
import com.hlkj.pojo.Users;
import com.hlkj.service.UserService;
import com.hlkj.utils.CookieUtils;
import com.hlkj.utils.HLKJJSONResult;
import com.hlkj.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "登录注册相关api")
@RestController
@RequestMapping("/passport")
public class PassportController {

    final static Logger logger = LoggerFactory.getLogger(PassportController.class);

    @Resource
    private UserService userService;

    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
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

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
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
        Users user = null;
        try {
            user = userService.createUser(userBO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HLKJJSONResult.ok(user);
    }

    @ApiOperation(value = "用户名密码登录", notes = "码", httpMethod = "POST")
    @PostMapping("/login")
    public HLKJJSONResult login(@RequestBody UserBO userBO, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        if (StringUtils.isBlank(userBO.getUsername()) ||
                StringUtils.isBlank(userBO.getPassword())) {
            return HLKJJSONResult.errorMsg("用户名或密码不能为空！");
        }
        Users user = userService.loginByUsernamePwd(userBO);
        //设置cookie
        CookieUtils.setCookie(request, response,
                "user", JsonUtils.objectToJson(user), true);
        return user!=null?HLKJJSONResult.ok(user):HLKJJSONResult.errorMsg("用户名或密码错误s");
    }

}
