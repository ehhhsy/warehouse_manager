package com.hsy.warehouse_manager2.controller;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @RequestMapping("/test")
    public String test(){
        return "hello";
    }
    @PostMapping("/redis/{k}/{v}")
    public String getKV(@PathVariable String k, @PathVariable String v){
        stringRedisTemplate.opsForValue().set(k,v);
        return "使用使用stringRedisTemplate添加对象";
    }
}
