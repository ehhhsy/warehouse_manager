package com.hsy.warehouse_manager2.controller;

import com.google.code.kaptcha.Producer;
import com.hsy.warehouse_manager2.pojo.CurrentUser;
import com.hsy.warehouse_manager2.pojo.LoginUser;
import com.hsy.warehouse_manager2.pojo.Result;
import com.hsy.warehouse_manager2.pojo.User;
import com.hsy.warehouse_manager2.service.UserService;
import com.hsy.warehouse_manager2.until.DigestUtil;
import com.hsy.warehouse_manager2.until.TokenUtils;
import com.hsy.warehouse_manager2.until.WarehouseConstants;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RequestMapping("/captcha")
@RestController
public class VerificationCodeController {

    //注入id引用名为captchaProducer的Producer接口的实现类DefaultKaptcha的bean对象
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    //注入redis模板
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 生成验证码图片的url接口/captcha/captchaImage
     */
    @GetMapping("/captchaImage")
    public void getKaptchaImage(HttpServletResponse response) {

        ServletOutputStream out = null;
        try {
            //禁止浏览器缓存验证码图片的响应头
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");

            //响应正文为jpg图片即验证码图片
            response.setContentType("image/jpeg");

            //生成验证码文本
            String code = captchaProducer.createText();
            //生成验证码图片
            BufferedImage bi = captchaProducer.createImage(code);

            //将验证码文本存储到redis
            stringRedisTemplate.opsForValue().set(code, code,60, TimeUnit.MINUTES);

            //将验证码图片响应给浏览器
            out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}