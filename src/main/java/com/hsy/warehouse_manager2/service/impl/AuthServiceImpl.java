package com.hsy.warehouse_manager2.service.impl;

import com.alibaba.fastjson.JSON;
import com.hsy.warehouse_manager2.mapper.AuthMapper;
import com.hsy.warehouse_manager2.pojo.Auth;
import com.hsy.warehouse_manager2.service.AuthService;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     *查询用户菜单树的业务方法
     * 向redis缓存用户菜单树 -- authTree:userId 值菜单ist<Auth>转的json串
     */
    @Override
    public List<Auth> AuthTreeByUid(Integer id) {
        //首先先从redis中查询
        String authTreeJson = stringRedisTemplate.opsForValue().get(id + ":authTree");

        //判断是不是拿到了
        if (StringUtils.hasText(authTreeJson)){
            //说明有缓存，则将菜单树List<Auth>转的JSON串转回List<Auth>转回
            List<Auth> authTreeList = JSON.parseArray(authTreeJson, Auth.class);
            return authTreeList;
        }

        //redis中没有查到缓存,从数据库表中查询所有权限(菜单)
        List<Auth> allAuthList = authMapper.findAuthById(id);
        //将所有权限(菜单)List<Auth>转成权限(菜单)树List<Auth>
        List<Auth> authTreeList = allAuthToAuthTree(allAuthList, 0);
        //将权限(菜单)树List<Auth>转成json串并保存到redis
        stringRedisTemplate.opsForValue().set(id + ":authTree", JSON.toJSONString(authTreeList));
        //返回权限(菜单)树List<Auth>
        return authTreeList;
    }

    @Override
    public List<Auth> allAuthTree() {
        //先从redis里查询
        String allAuthTreeJson = stringRedisTemplate.opsForValue().get("all:authTree");
        if (StringUtils.hasText(allAuthTreeJson)){ //如果在redis中查到了缓存
            //将json串转回整个权限(菜单)树List<Auth>并返回
            List<Auth> authList = JSON.parseArray(allAuthTreeJson, Auth.class);
            return authList;
        }

        //redis中没有查到缓存,从数据库表中查询所有权限(菜单)
        List<Auth> authList = authMapper.getAllAuth();
        //将所有权限(菜单)List<Auth>转成整个权限(菜单)树List<Auth>
        List<Auth> authTree = allAuthToAuthTree(authList, 0);
        //将权限树转为JSON串并保持到redis中
        stringRedisTemplate.opsForValue().set("all:authTree",JSON.toJSONString(authTree));
        return authTree;
    }



    //将所有权限(菜单)转成权限(菜单)树的递归算法
    private List<Auth> allAuthToAuthTree(List<Auth> allAuthList, int parentId){
        //获取父权限(菜单)id为参数parentId的所有权限(菜单)
        //【parentId最初为0,即最初查的是所有一级权限(菜单)】
        List<Auth> authList = new ArrayList<>();
        for (Auth auth : allAuthList) {
            if(auth.getParentId()==parentId){
                authList.add(auth);
            }
        }
        //查询List<Auth> authList中每个权限(菜单)的所有子级权限(菜单)
        for (Auth auth : authList) {
            List<Auth> childAuthList = allAuthToAuthTree(allAuthList, auth.getAuthId());
            auth.setChildAuth(childAuthList);
        }
        return authList;
    }
}
