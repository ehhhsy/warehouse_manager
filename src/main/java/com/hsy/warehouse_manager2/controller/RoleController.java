package com.hsy.warehouse_manager2.controller;

import com.hsy.warehouse_manager2.dto.AssignAuthDto;
import com.hsy.warehouse_manager2.pojo.CurrentUser;
import com.hsy.warehouse_manager2.pojo.Result;
import com.hsy.warehouse_manager2.pojo.Role;
import com.hsy.warehouse_manager2.service.RoleService;
import com.hsy.warehouse_manager2.until.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.hsy.warehouse_manager2.page.Page;

import java.util.List;

@RequestMapping("/role")
@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private TokenUtils tokenUtils;

    //查询所有角色的url接口  /role/role-list
    @RequestMapping("/role-list")
    public Result roleList(){
        List<Role> roleList = roleService.queryAllRole();
        return Result.ok(roleList);
    }
    //分页查询所有角色
    @RequestMapping("/role-page-list")
    public Result roleListPage(Page page,Role role){
        //执行业务
        page=roleService.queryRolePage(page, role);
        return Result.ok(page);
    }

    /**
     * 添加角色 /role/role-add
     */
    @RequestMapping("/role-add")
    public Result addRole(@RequestBody Role role, @RequestHeader String token){
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        role.setCreateBy(currentUser.getUserId());
        //执行业务
        Result result = roleService.addRole(role);
        return result;
    }

    /**
     * 修改角色状态/role/role-state-update
     */
    @RequestMapping("/role-state-update")
    public Result modifyStateByRoleId(@RequestBody Role role){
        Result result = roleService.modifyRoleState(role);
        return result;
    }

    /**
     * 删除角色  /role/role-delete/#{role_id}
     */
    @RequestMapping("/role-delete/{roleId}")
    public Result deleteRole(@PathVariable Integer roleId){
        Result result = roleService.deleteRole(roleId);
        return result;
    }

    /**
     *   根据角色id查询角色已分配的所有权限  /role/role-auth?roleId=20
     *   Integer roleId将请求参数roleId赋值给请求处理方法参数roleId;
     */
    @RequestMapping("/role-auth")
    public Result queryRoleAuth(Integer roleId){
        //执行业务
        List<Integer> integerList = roleService.queryAuthIds(roleId);
        return Result.ok(integerList);
    }

    /**
     * 分配角色 发送请求 /role/auth-grant，参数在请求体里
     */
    @RequestMapping("/auth-grant")
    public Result distributeRole(@RequestBody AssignAuthDto assignAuthDto){
        //执行业务
        roleService.AssignAuth(assignAuthDto);
        return Result.ok("权限分配成功");
    }
    /**
     * /role/role-update  修改角色信息，根据名称修改描述
     */
    @RequestMapping("/role-update")
    public Result updateRoleDesc(@RequestBody Role role,@RequestHeader String token){
        //获取当前用户，
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //将当前用户id传入updateBy
        role.setUpdateBy(currentUser.getUserId());

        //执行业务
        Result result = roleService.setRoleByRoleId(role);
        return result;
    }
    /**
     * /role/exportTable 请求导出角色列表
     */
    @RequestMapping("/exportTable")
    public Result exportDate(Page page,Role role){
        //分页查询
        page=roleService.queryRolePage(page, role);
        List<?> list = page.getResultList();
        return Result.ok(list);
    }
}
