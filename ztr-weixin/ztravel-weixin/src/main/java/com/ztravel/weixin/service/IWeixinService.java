package com.ztravel.weixin.service;

import java.util.Map;

/**
 *
 * @author xutian
 *
 */
public interface IWeixinService {

    /**
     * 微信二维码扫描事件处理
     * @param requestMap
     * @throws Exception
     */
    public void wxScan(Map<String, String> requestMap) throws Exception;

}
