package com.hsy.warehouse_manager2.service;

import com.hsy.warehouse_manager2.dto.AssignAuthDto;
import com.hsy.warehouse_manager2.dto.AssignRoleDto;
import com.hsy.warehouse_manager2.page.Page;
import com.hsy.warehouse_manager2.pojo.Auth;
import com.hsy.warehouse_manager2.pojo.Result;
import com.hsy.warehouse_manager2.pojo.Role;

import java.util.List;

public interface RoleService {

    //查询所有角色的业务方法
    public List<Role> queryAllRole();

    //查询用户已分配的角色的url接口
    public List<Role> queryRolesByUserId(Integer userId);

    //给用户分配角色的业务方法
    public void assignRole(AssignRoleDto assignRoleDto);

    //分页查询角色的业务方法
    public Page queryRolePage(Page page, Role role);

    //添加角色发方法
    public Result addRole(Role role);

    //修改角色状态方法
    public Result modifyRoleState(Role role);

    //删除角色的方法
    public Result deleteRole(Integer roleId);

    //查询角色已分配的权限(菜单)的业务方法
    public List<Integer> queryAuthIds(Integer roleId);

    //分配角色方法
    public void AssignAuth(AssignAuthDto assignAuthDto);

    //根据角色id修改角色描述
    public Result setRoleByRoleId(Role role);
}
