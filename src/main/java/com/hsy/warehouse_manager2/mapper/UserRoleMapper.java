package com.hsy.warehouse_manager2.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface UserRoleMapper {

    //    根据用户id删除用户已分配的角色关系表
    public int removeUserRoleById(@Param("userId") Integer userId);

    //添加用户角色关系的方法
    public void insertUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);




}
