package com.ztravel.paygate.wx.controller;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.travelzen.framework.config.tops.TopsConfReader;
import com.travelzen.framework.config.tops.TopsConfEnum.ConfScope;
import com.ztravel.common.util.WeixinMessageDigestUtil;


/**
 * 微信验证URL
 * @author haofan.wan
 *
 */
@Controller
public class WechatValidController {
	
	private static Logger logger = LoggerFactory.getLogger(WechatValidController.class);
	
	private static final String TOKEN = TopsConfReader.getConfContent("properties/wx-pay.properties", "token", ConfScope.R) ;
	
	@RequestMapping(value = "/mobile/valid")
    public void valid(String signature, String timestamp, String nonce, String echostr, HttpServletResponse response) {
    	logger.debug("signature:" + signature);
        logger.debug("timestamp:" + timestamp);
        logger.debug("nonce:" + nonce);
        logger.debug("echostr:" + echostr); 
        String[] ArrTmp = { TOKEN, timestamp, nonce };
        Arrays.sort(ArrTmp);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ArrTmp.length; i++) {
            sb.append(ArrTmp[i]);
        }
        String pwd = WeixinMessageDigestUtil.getInstance().encipher(sb.toString()); 
        logger.debug("pwd:" + pwd); 
        if(pwd.equals(signature)){
            if(!"".equals(echostr) && echostr != null){
                try {
                    response.getWriter().write(echostr);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
}
