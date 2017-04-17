/**
 *
 */
package com.ztravel.common.security;

import org.joda.time.DateTime;

/**
 * @author zuoning.shen
 *
 */
public class SignUtil {
    public static final String DEFAULT_CHARSET = "utf-8";

    public static final String FRONT_SIGN_KEY = "ztravel.front.user" ;

    public static final int THREE_MONTH_MILLIS = 3 * 30 * 24 * 60 * 60 ;

    /**
     * 生成24位sid用于重设密码
     * @param origin   原始信息，可以是mid或者手机号等
     * @param outDate   签名过期时间（毫秒）
     * @param signKey   签名key
     * @return
     */
    public static String signSid(String origin, long outDate, String signKey){
        String preSignStr = origin +  outDate + signKey;
        return Md5Util.encode(preSignStr, DEFAULT_CHARSET).substring(0, 24);
    }

    public static boolean verifySid(String origin, long outDate, String signKey, String signStr){
        return signSid(origin, outDate, signKey).equals(signStr);
    }

    public static String signPassword(String password, String signKey){
        String preSignStr = password + signKey;
        return Md5Util.encode(preSignStr, DEFAULT_CHARSET);
    }

    public static boolean verifyPassword(String password, String signKey, String signStr){
        return signPassword(password, signKey).equals(signStr);
    }

    public static void main(String[] args){
        String origin = "123456";
        String signKey = "ztravel.operator.user";
        long outDate = DateTime.now().getMillis() + 10 * 60 * 1000;
        System.out.println(signPassword(origin, signKey));
    }
}
