package com.hsy.warehouse_manager2.controller;

import com.hsy.warehouse_manager2.pojo.Auth;
import com.hsy.warehouse_manager2.pojo.Result;
import com.hsy.warehouse_manager2.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    /**
     * 查询所有权限菜单树  /auth/auth-tree
     */
    @RequestMapping("/auth-tree")
    public Result AllAuthTree(){
        //执行业务
        List<Auth> authList = authService.allAuthTree();
        return Result.ok(authList);
    }
}
