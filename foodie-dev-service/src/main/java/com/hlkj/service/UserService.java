package com.hlkj.service;


import com.hlkj.bo.UserBO;
import com.hlkj.pojo.Users;

public interface UserService {

    /**
     * 判断用户名是否已存在
     * @param username
     * @return
     */
    public boolean usernameIsExist(String username);

    /**
     * 创建用户
     * @param userBO
     * @return
     */
    public Users createUser(UserBO userBO) throws Exception;

}
