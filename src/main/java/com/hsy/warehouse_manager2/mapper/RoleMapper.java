package com.hsy.warehouse_manager2.mapper;

import com.hsy.warehouse_manager2.pojo.Role;
import com.hsy.warehouse_manager2.page.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper {

    //查询所有角色
    public List<Role> findAllRole();

    //查询用户已分配的角色
    List<Role> findRolesByUserId(@Param("userId") Integer userId);


    //根据角色名查角色id的方法
    public Integer findRoleIdByName(@Param("roleName") String roleName);

    //查询角色行数
    public Integer findRoleRowCount(Role role);

    //分页查询角色的方法
    public List<Role> findRolePage(@Param("page") Page page,@Param("role") Role role);

    //查询角色是否重复的方法
    public Role verifyRole(Role role);

    //添加角色
    public Integer addRole(Role role);

    //修改角色状态
    public Integer modifyRoleState(Role role);

    //删除角色
    public Integer deleteRole(@Param("roleId") Integer roleId);

    //查询角色已分配的权限(菜单)id的业务方法
    public List<Integer> findAuthIds(Integer roleId);

    //删除角色id
    public Integer deleteAuthIdByRoleId(Integer roleId);

    //根据roleId修改roleDesc 校色描述
    public Integer modifyRoleDesc(Role role);
}
