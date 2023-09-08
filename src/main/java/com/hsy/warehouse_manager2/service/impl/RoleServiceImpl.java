package com.hsy.warehouse_manager2.service.impl;

import com.hsy.warehouse_manager2.dto.AssignAuthDto;
import com.hsy.warehouse_manager2.dto.AssignRoleDto;
import com.hsy.warehouse_manager2.mapper.AuthMapper;
import com.hsy.warehouse_manager2.mapper.RoleMapper;
import com.hsy.warehouse_manager2.mapper.UserRoleMapper;
import com.hsy.warehouse_manager2.page.Page;
import com.hsy.warehouse_manager2.pojo.Result;
import com.hsy.warehouse_manager2.pojo.Role;
import com.hsy.warehouse_manager2.pojo.User;
import com.hsy.warehouse_manager2.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
//指定缓存的名称(数据保存到redis中的键的前缀，一般值给标注@CacheConfig类的全路径 com.hsy.warehouse_manager2.service.impl.RoleServiceImpl)
@CacheConfig(cacheNames = "com.hsy.warehouse_manager2.service.impl.RoleServiceImpl")
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private AuthMapper authMapper;

    //再查询方法上指定缓存的键key
    @Cacheable(key = "'all:role'")
    @Override
    public List<Role> queryAllRole() {
        List<Role> roleList = roleMapper.findAllRole();
        return roleList;
    }

    //查询已分配的角色id
    @Override
    public List<Role> queryRolesByUserId(Integer userId) {
        return roleMapper.findRolesByUserId(userId);
    }


    //给用户分配角色的业务方法

    /**
     * 1.先根据userId删除user_info
       2.根据角色名称获取roleId
       3.重新把userId和roleId通过循环添加用户user_role
     */
    @Transactional //事务
    @Override
    public void assignRole(AssignRoleDto assignRoleDto) {
        //拿到用户id
        Integer userId = assignRoleDto.getUserId();
        //拿到给用户分配的所有角色名
        List<String> roleNameList = assignRoleDto.getRoleCheckList();
        //根据用户id删除给用户已分配的所有角色
        userRoleMapper.removeUserRoleById(userId);

        //循环-->获取roleId,添加角色--<
        for (String roleName : roleNameList) {
            Integer roleId = roleMapper.findRoleIdByName(roleName);
            userRoleMapper.insertUserRole(userId,roleId);
        }
        //最好：实现时利用批处理或批量插入功能,一次性插入多条记录,减少数据库访问次数。
    }

    @Override
    public Page queryRolePage(Page page, Role role) {
        //查询角色行数
        Integer count = roleMapper.findRoleRowCount(role);

        //分页查询角色
        List<Role> roleList = roleMapper.findRolePage(page, role);

        //组装分页信息
        page.setTotalNum(count);
        page.setResultList(roleList);
        return page;
    }

//    添加角色
//    @CacheEvict("'com.hsy.warehouse_manager2.service.impl.RoleServiceImpl::all:role'")  //清除缓存
    @Override
    public Result addRole(Role role) {
        Role exit_role = roleMapper.verifyRole(role);
        if (exit_role==null){ //没有查到，说明可添加
            Integer addRole = roleMapper.addRole(role);
            if (addRole>0){
                stringRedisTemplate.opsForValue().getOperations().delete("com.hsy.warehouse_manager2.service.impl.RoleServiceImpl::all:role");
                return Result.ok("添加成功");
            }
            return Result.err(Result.CODE_ERR_BUSINESS,"添加失败");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"角色存在!?");
    }

    //根据角色id修改角色状态
//    @CacheEvict("'com.hsy.warehouse_manager2.service.impl.RoleServiceImpl::all:role'")  //清除缓存
    @Override
    public Result modifyRoleState(Role role) {
        Integer i = roleMapper.modifyRoleState(role);
        if (i>0){
            //清除缓存
            stringRedisTemplate.opsForValue().getOperations().delete("com.hsy.warehouse_manager2.service.impl.RoleServiceImpl::all:role");

            return Result.ok("修改成功.");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"修改失败!?");
    }

    @Transactional
    //删除角色，也需要清缓存
    @Override
    public Result deleteRole(Integer roleId) {
        Integer i = roleMapper.deleteRole(roleId);
        if (i>0){
            //清除缓存
            stringRedisTemplate.opsForValue().getOperations().delete("com.hsy.warehouse_manager2.service.impl.RoleServiceImpl::all:role");
            //根据角色id删除 角色已分配的所有权限
            authMapper.delAuthByRoleId(roleId);
            return Result.ok("删除成功!>");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"删除失败!?");
    }
    //查询角色已分配的权限(菜单)的业务方法
    @Override
    public List<Integer> queryAuthIds(Integer roleId) {
        List<Integer> authIdList = roleMapper.findAuthIds(roleId);
        return authIdList;
    }
    @Transactional //事务
    @Override
    public void AssignAuth(AssignAuthDto assignAuthDto) {
        Integer roleId = assignAuthDto.getRoleId();
        //1.先删除roleId对应的所有authId
        roleMapper.deleteAuthIdByRoleId(roleId);
        //2.分配角色权限关系
        for (Integer authId : assignAuthDto.getAuthIds()) {
            authMapper.insertRoleAuth(roleId,authId);
        }
    }

    @Override
    public Result setRoleByRoleId(Role role) {
        Integer integer = roleMapper.modifyRoleDesc(role);
        if (integer>0){
            //清除缓存
            stringRedisTemplate.opsForValue().getOperations().delete("com.hsy.warehouse_manager2.service.impl.RoleServiceImpl::all:role");
            return Result.ok("修改成功>");
        }
        return Result.err(Result.CODE_ERR_BUSINESS,"修改失败");
    }


}
