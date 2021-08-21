package com.hlkj.impl;

import com.hlkj.bo.UserBO;
import com.hlkj.enums.Sex;
import com.hlkj.mapper.UsersMapper;
import com.hlkj.pojo.Users;
import com.hlkj.service.UserService;
import com.hlkj.utils.DateUtil;
import com.hlkj.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UsersMapper userMapper;
    @Resource
    private Sid sid;

    private static final String USER_FACE = "https://img2.baidu.com/it/u=2436646421,1026055356&fm=26&fmt=auto&gp=0.jpg";

    @Override
    public boolean usernameIsExist(String username) {
        Example example = new Example(Users.class);
        example.createCriteria().andEqualTo("username", username);
        Users user = userMapper.selectOneByExample(example);
        return user==null ? false:true;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users createUser(UserBO userBO) throws Exception {
        if (!userBO.getPassword().equals(userBO.getConfirmPassword())){

        }
        Users user = new Users();
        user.setId(sid.next());//设置主键
        user.setUsername(userBO.getUsername());
        user.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        user.setNickname(userBO.getUsername());
        user.setFace(USER_FACE);
        user.setBirthday(DateUtil.stringToDate("1991-10-10"));
        user.setSex(Sex.man.type);
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());
        //保存数据
        userMapper.insert(user);
        return user;
    }

    @Override
    public Users loginByUsernamePwd(UserBO userBO) throws Exception {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", userBO.getUsername());
        criteria.andEqualTo("password", MD5Utils.getMD5Str(userBO.getPassword()));

        return userMapper.selectOneByExample(example);
    }
}
