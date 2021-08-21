package com.hlkj.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户对象BO", description = "用户对象BO")
public class UserBO {

    @ApiModelProperty(value = "用户名", name = "用户名", required = true, dataType = "String")
    private String username;
    @ApiModelProperty(value = "密码", name = "密码", required = true, dataType = "String")
    private String password;
    @ApiModelProperty(value = "确认密码", name = "确认密码", required = false, dataType = "String")
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
