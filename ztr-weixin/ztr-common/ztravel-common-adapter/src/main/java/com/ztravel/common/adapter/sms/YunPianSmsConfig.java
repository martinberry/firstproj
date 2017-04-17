package com.ztravel.common.adapter.sms;

import com.travelzen.framework.config.tops.TopsConfEnum;
import com.travelzen.framework.config.tops.TopsConfReader;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hetao on 15-4-14.
 */
public class YunPianSmsConfig {
    private static final Logger logger = LoggerFactory.getLogger(YunPianSmsConfig.class);
    private static final String YUNPIAN_PROP_PATH = "properties/yunpian.properties";

    public static final String SEND_SMS_URL;
    public static final String SEND_BY_TEMPLATE_URL;
    public static final String GET_ACCOUNT_INFO_URL;
    public static final String YP_TUNNEL_API_KEY;
    public static final String ALI_TUNNEL_API_KEY;
    public static final String SMS_POSTFIX;

    static {
        YP_TUNNEL_API_KEY = StringUtils.trimToEmpty(TopsConfReader.getConfContent(YUNPIAN_PROP_PATH, "yunpian.ypTunnelApikey", TopsConfEnum.ConfScope.R));
        logger.info("yunpian.ypTunnelApikey --> {}", YP_TUNNEL_API_KEY);
        ALI_TUNNEL_API_KEY = StringUtils.trimToEmpty(TopsConfReader.getConfContent(YUNPIAN_PROP_PATH, "yunpian.aliTunnelApiKey", TopsConfEnum.ConfScope.R));
        logger.info("yunpian.aliTunnelApiKey --> {}", ALI_TUNNEL_API_KEY);
        SEND_SMS_URL = StringUtils.trimToEmpty(TopsConfReader.getConfContent(YUNPIAN_PROP_PATH, "yunpian.sendSMSUrl", TopsConfEnum.ConfScope.R));
        logger.info("yunpian.sendSMSUrl --> {}", SEND_SMS_URL);
        SEND_BY_TEMPLATE_URL = StringUtils.trimToEmpty(TopsConfReader.getConfContent(YUNPIAN_PROP_PATH, "yunpian.sendByTemplateUrl", TopsConfEnum.ConfScope.R));
        logger.info("yunpian.sendByTemplateUrl --> {}", SEND_BY_TEMPLATE_URL);
        GET_ACCOUNT_INFO_URL = StringUtils.trimToEmpty(TopsConfReader.getConfContent(YUNPIAN_PROP_PATH, "yunpian.getAccountInfoUrl", TopsConfEnum.ConfScope.R));
        logger.info("yunpian.getAccountInfoUrl --> {}", GET_ACCOUNT_INFO_URL);
        SMS_POSTFIX = StringUtils.trimToEmpty(TopsConfReader.getConfContent(YUNPIAN_PROP_PATH, "yunpian.postfix", TopsConfEnum.ConfScope.R));
        logger.info("yunpian.postfix --> {}", SMS_POSTFIX);
    }
}
