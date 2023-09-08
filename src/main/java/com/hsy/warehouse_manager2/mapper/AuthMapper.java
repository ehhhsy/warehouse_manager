package com.hsy.warehouse_manager2.mapper;

import com.hsy.warehouse_manager2.pojo.Auth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface AuthMapper {

    //根据userId查询用户权限下的所有菜单的方法
    public List<Auth> findAuthById(@Param("userId") Integer userId);

    //根据角色id删除给角色已分配的所有权限(菜单)
    public int delAuthByRoleId(Integer roleId);

    //加载所有状态权限菜单树
    public List<Auth> getAllAuth();

    //分配角色权限关系
    public void insertRoleAuth(@Param("roleId") Integer roleId,@Param("authId") Integer authId);

}
