package com.hsy.warehouse_manager2.service;

import com.hsy.warehouse_manager2.page.Page;
import com.hsy.warehouse_manager2.pojo.Result;
import com.hsy.warehouse_manager2.pojo.User;
import java.util.*;

public interface UserService {
    //根据账号查询用户的业务方法
    public User queryUserByCode(String userCode);

    //分页查询用户的方法
    public Page queryUserByPage(Page page, User user);

    //添加用户业务
    public Result saveUser(User user);

    //启用/禁用业务方法
    public Result setUserState(User user);

    //删除用户的业务方法
    public Result deleteUserByIds(List<Integer> userIdList);

    //修改用户名方法
    public Result updateUserNameByUserId(User user);

    //重置密码的方法
    public Result resetPwdByUserId(Integer userId);



}
