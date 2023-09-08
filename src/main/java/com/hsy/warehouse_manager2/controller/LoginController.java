package com.hsy.warehouse_manager2.controller;

import com.hsy.warehouse_manager2.pojo.*;
import com.hsy.warehouse_manager2.service.AuthService;
import com.hsy.warehouse_manager2.service.UserService;
import com.hsy.warehouse_manager2.until.DigestUtil;
import com.hsy.warehouse_manager2.until.TokenUtils;
import com.hsy.warehouse_manager2.until.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LoginController {
    /**
     * 登录的接口
     * @RequestBody LoginUser:这个注解表示接收并封装前端传来的登录的用户信息的json数据
     * 返回值是result：表示响应结果Result对象的json对象（响应状态码，成功失败响应，响应信息，响应数据）
     */

//    注入UserService
    @Autowired
    private UserService userService;

    //注入TokenUtil
    @Autowired
    private TokenUtils tokenUtils;

    //注入Redis模板
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @PostMapping("/login")
    public Result login(@RequestBody LoginUser loginUser){
        //一开始校验验证码
        String verificationCode = loginUser.getVerificationCode();
        //看看和redis存的是否一致
        if (!stringRedisTemplate.hasKey(verificationCode)){
            return Result.err(Result.CODE_ERR_BUSINESS,"验证码错误111");
        }

        User user = userService.queryUserByCode(loginUser.getUserCode());
        if (user!=null){ //账号存在
            //存在则判断账号状态
            if (user.getUserState().equals(WarehouseConstants.USER_STATE_PASS)){
                //已审核,继续
                //拿到用户的密码进行md5加密，并和数据库的密码做对比
                String userPwd = loginUser.getUserPwd();
                userPwd = DigestUtil.hmacSign(userPwd);
                if (userPwd.equals(user.getUserPwd())){
                    //密码正确，所有验证完成，

                    CurrentUser currentUser = new CurrentUser(user.getUserId(),user.getUserCode(),user.getUserName());
                    //传参给一个currentUser对象和  加密密码（经过加密的用户密码）
                    String Token = tokenUtils.loginSign(currentUser, userPwd);
                    //向客户端响应JWT Token
                    return Result.ok("登录成功",Token);

                }else{
                    //密码错误
                    return Result.err(Result.CODE_ERR_BUSINESS,"密码错误44");
                }
            }else{
                //为审核
                return Result.err(Result.CODE_ERR_BUSINESS,"用户未审核33");
            }
        }else{      //账号不存在
            return Result.err(Result.CODE_ERR_BUSINESS,"账号不存在22");
        }
    }


    /**
     * 获取当前用户的用户信息  /url-user
     * @RequestHeader(Token):获取请求头中Token的值
     */
        //已经注入过了，直接使用
    @RequestMapping("/curr-user")
    public  Result currentUser(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //响应
        return Result.ok(currentUser);
    }

    /**
     * 加载用户权限菜单树
     */
    @Autowired
    private AuthService authService;
    @RequestMapping("/user/auth-list")
    public Result loadAuthTree(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        //拿到当前登录的用户id
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        int userId = currentUser.getUserId();

        //执行业务
        List<Auth> authTreeList = authService.AuthTreeByUid(userId);
        //响应
        return Result.ok(authTreeList);
    }

    /**
     * 用户退出登录 /loginout
     */
    @RequestMapping("/logout")
    public Result logout(@RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        //从redis删除Token
        stringRedisTemplate.delete(token);
        //响应
        return Result.ok("推出系统?！");
    }
}
