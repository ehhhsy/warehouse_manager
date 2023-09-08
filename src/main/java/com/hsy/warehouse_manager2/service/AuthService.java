package com.hsy.warehouse_manager2.service;

import com.hsy.warehouse_manager2.pojo.Auth;
import java.util.*;
public interface AuthService {
    //查询用户菜单树的业务
    public List<Auth> AuthTreeByUid(Integer id);

    //查询所有权限菜单树
    public List<Auth> allAuthTree();
}
