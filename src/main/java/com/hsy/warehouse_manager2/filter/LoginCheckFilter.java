package com.hsy.warehouse_manager2.filter;

import com.alibaba.fastjson.JSON;
import com.hsy.warehouse_manager2.pojo.Result;
import com.hsy.warehouse_manager2.until.WarehouseConstants;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

//1.实现Filter
public class LoginCheckFilter implements Filter {

    private StringRedisTemplate stringRedisTemplate;

    public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    //过滤器拦截到请求该干什么
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse)  servletResponse;
        //前端访问时，会在请求头携带Token,除了登录页面

        //1.白名单直接放行
        ArrayList<Object> urlList = new ArrayList<>();
        urlList.add("/captcha/captchaImage");
        urlList.add("/login");
        urlList.add("/product/img-upload");
        String url = request.getServletPath();
        if (urlList.contains(url)||url.contains("/img/upload")){//白名单请求
            filterChain.doFilter(request, response);
            return;
        }

        //2.其他请求都校验是否携带Token,判断redis是否存在token的键
            //拿到token
        String token = request.getHeader(WarehouseConstants.HEADER_TOKEN_NAME);
            //1.有,放行
            if (StringUtils.hasText(token)&&stringRedisTemplate.hasKey(token)){
                filterChain.doFilter(request, response);
                return;
            }

            //2.没有,说明未登录或者会话过期,并给前端做出反应
        Result result = Result.err(Result.CODE_ERR_UNLOGINED, "宁尚未登录");
        String jsonStr = JSON.toJSONString(result);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonStr);
        out.flush();
        out.close();
    }

}
