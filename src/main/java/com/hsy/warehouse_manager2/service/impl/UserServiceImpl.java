package com.hsy.warehouse_manager2.service.impl;

import com.hsy.warehouse_manager2.mapper.UserMapper;
import com.hsy.warehouse_manager2.page.Page;
import com.hsy.warehouse_manager2.pojo.Result;
import com.hsy.warehouse_manager2.pojo.User;
import com.hsy.warehouse_manager2.service.UserService;
import com.hsy.warehouse_manager2.until.DigestUtil;
import com.hsy.warehouse_manager2.until.WarehouseConstants;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    //注入UserMapper，实现业务层调用持久层
    @Autowired
    private UserMapper userMapper;
    @Override
    public User queryUserByCode(String userCode) {
        return userMapper.findUserById(userCode);
    }

    @Override
    public Page queryUserByPage(Page page, User user) {
        //查询用户行数
        Integer count = userMapper.findUserRowCount(user);

        //分页查询用户
        List<User> userList = userMapper.findUserByPage(page, user);

        //组装所有用户信息
        page.setTotalNum(count);
        page.setResultList(userList);
        return page;
    }

    /**
     * 添加用户的方法
     * @return
     */
    @Override
    public Result saveUser(User user) {
        //判断账号是否重复
        User userById = userMapper.findUserById(user.getUserCode());
        if (userById!=null){
            //说明已存在
            return Result.err(Result.CODE_ERR_BUSINESS,"已存在，已存在");
        }

        //对密码进行加密
        String password = DigestUtil.hmacSign(user.getUserPwd());
        user.setUserPwd(password);

        //执行添加，mapper
        int i = userMapper.insertUser(user);
        if (i>0) {
            return Result.ok("添加成功!?");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"用户添加失败!?");
    }

    /**
     * 设置用户状态
     */
    @Override
    public Result setUserState(User user) {
        int i = userMapper.setStateByUid(user.getUserId(), user.getUserState());
        if (i>0){
            return Result.ok("启用/禁用用户成功!?");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"启用/禁用用户失败!?");
    }

    /**
     * //删除用户的业务方法
     */
    @Override
    public Result deleteUserByIds(List<Integer> userIdList) {
        int i = userMapper.setIsDeleteByUids(userIdList);
        if (i>0){
            return Result.ok("删除成功》");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"删除失败！?");
    }

    /**
     * 修改用户名的方法
     */
    @Override
    public Result updateUserNameByUserId(User user) {
        int i = userMapper.setUserNameByUser(user);
        if (i > 0) {
            return Result.ok("修改成功》");
        }
        return Result.err(Result.CODE_ERR_BUSINESS, "修改失败！?");
    }

    /**
     * 重置密码的方法
     */
    @Override
    public Result resetPwdByUserId(Integer userId) {
        //将123456加密传给mapper层方法
        String reset_password = DigestUtil.hmacSign("123456");
        int i = userMapper.resetPwdByUserId(userId, reset_password);
        if (i>0){
            return Result.ok("重置密码成功<");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"重置失败!?");
    }
}
