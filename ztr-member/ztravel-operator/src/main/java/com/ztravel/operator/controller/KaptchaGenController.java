package com.ztravel.operator.controller;

import java.awt.image.BufferedImage;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Producer;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.constants.Const;
import com.ztravel.common.holder.OperatorSidHolder;

/**
 * 登录验证码controller
 * @author
 *
 */
@Controller
public class KaptchaGenController {

	@Resource
	Producer captchaProducer;

	private final RedisClient redisClient = RedisClient.getInstance();

	@RequestMapping("/captcha/{version}")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");
        // create the text for the image
        String capText = captchaProducer.createText();
        // store the text in the session

        // 验证码要放在redis里面
        redisClient.set(Const.KAPTCHA_OPER_LOGIN_KEY + ":" + OperatorSidHolder.get(), capText, 10 * 60);
        // create the image with the text
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        // write the data out
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
        return null;

	}
}
