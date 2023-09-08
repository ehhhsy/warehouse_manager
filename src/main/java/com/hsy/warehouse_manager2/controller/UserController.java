package com.hsy.warehouse_manager2.controller;

import com.hsy.warehouse_manager2.dto.AssignRoleDto;
import com.hsy.warehouse_manager2.pojo.CurrentUser;
import com.hsy.warehouse_manager2.pojo.Result;
import com.hsy.warehouse_manager2.pojo.Role;
import com.hsy.warehouse_manager2.pojo.User;
import com.hsy.warehouse_manager2.page.Page;
import com.hsy.warehouse_manager2.service.RoleService;
import com.hsy.warehouse_manager2.service.UserService;
import com.hsy.warehouse_manager2.until.TokenUtils;
import com.hsy.warehouse_manager2.until.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * 分页查询用户的url接口/user/user-list
     * page 接收分页信息，user接收用户信息
     */
    @RequestMapping("user-list")
    public Result userList(Page page, User user){
        //执行业务
        Page byPage = userService.queryUserByPage(page, user);
        return Result.ok(byPage);
    }

    /**
     * 添加用户/user/addUser
     * @RequestBody User user:将传入的参数封撞到user对象
     */
    @Autowired
    private TokenUtils tokenUtils;
    @RequestMapping("/addUser")
    public Result addUser(@RequestBody User user,@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        //拿到当前登录用户的id，用作createBy字段
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int userId = currentUser.getUserId();
        //传给user
        user.setCreateBy(userId);

        //执行业务，service
        Result result = userService.saveUser(user);
        return result;
    }


    /**
     * 修改用户状态 /user/updateState
     */
    @RequestMapping("/updateState")
    public Result updateUserState(@RequestBody User user){
        Result result = userService.setUserState(user);
        return result;
    }

    /**
     * 查询用户已分配角色
     *   @PathVariable 获取路径的变量
     */
    @RequestMapping("/user-role-list/{userId}")
    public Result userRoleList(@PathVariable Integer userId){
        List<Role> roleList = roleService.queryRolesByUserId(userId);
        return Result.ok(roleList);
    }

    /**
     * 给用户分配角色 user/assignRole
     */
    @RequestMapping("/assignRole")
    public Result assignRole(@RequestBody AssignRoleDto assignRoleDto){
        //执行业务
        roleService.assignRole(assignRoleDto);
        return Result.ok("分配角色成功!?");
    }

    /**
     * 单次删除用户接口/user/deleteUser/35
     */
    @RequestMapping("/deleteUser/{userId}")
    public Result deleteUser(@PathVariable Integer userId){
        List<Integer> list = Arrays.asList(userId);
        Result result = userService.deleteUserByIds(list);
        return result;
    }

    /**
     * 批量删除用户 /user/deleteUserList 通过请求头 [34,35] 传入
     */
    @RequestMapping("/deleteUserList")
    public Result deleteUserByIds(@RequestBody List<Integer> userIdList){
        Result result = userService.deleteUserByIds(userIdList);
        return result;
    }

    /**
     * 修改用户 /user/updateUser  通过请求体传入
     */
    @RequestMapping("/updateUser")
    public Result updateUserName(@RequestBody User user,@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        //需要考虑 `update_by`字段，这个字段需要当前登录的信息，在Token可以拿到
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int userId = currentUser.getUserId();
        //传给user
        user.setUpdateBy(userId);

        Result result = userService.updateUserNameByUserId(user);
        return result;
    }

    /**
     * 重置密码 /user/updatePwd/35
     */
    @RequestMapping("/updatePwd/{userId}")
    public Result resetPwd(@PathVariable Integer userId){
        Result result = userService.resetPwdByUserId(userId);
        return result;
    }

    /**
     * /user/exportTable 导出数据
     */
    @RequestMapping("/exportTable")
    public Result exportDate(Page page,User user){
        //使用分页查询的接口
        Page byPage = userService.queryUserByPage(page, user);
        //拿取list
        List<?> resultList = byPage.getResultList();
        return Result.ok(resultList);
    }
}


