package com.ztravel.member.client.service.impl;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.config.tops.TopsConfEnum.ConfScope;
import com.travelzen.framework.config.tops.TopsConfReader;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.constants.Const;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.common.entity.ProductDestination;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.holder.TokenHolder;
import com.ztravel.common.util.DeparturePlaceUtil;
import com.ztravel.common.util.HttpUtil;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.common.util.ProductDestUtil;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.reuse.member.entity.SearchModuleVo;
import com.ztravel.reuse.member.entity.WorkPlatFormVo;
import com.ztravel.member.client.service.FrontCommonService;
import com.ztravel.member.client.service.IMemberClientService;
import com.ztravel.member.client.service.IPrivateLetterClientService;
import com.ztravel.member.client.service.ISystemNoticeClientService;
import com.ztravel.reuse.member.entity.PrivateLetterVo;
import com.ztravel.member.po.SystemNotice;

@Service
public class FrontCommonServiceImpl implements FrontCommonService{

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(FrontCommonServiceImpl.class);

	private final String QRCODEURL = TopsConfReader.getConfContent("properties/wx-mp.properties", "QRCODE_CREATE_URL", ConfScope.R) ;

	private final long EXPIRE_SECONDS = 604800;

	private final String ACTION_NAME = "QR_SCENE";

	private final String CONTENT_TYPE = "application/json";

	private static final RedisClient redisClient = RedisClient.getInstance();

	@Resource
	private IMemberClientService MemberClientService;

	@Resource
	ISystemNoticeClientService systemNoticeClientService;

	@Resource
	private IPrivateLetterClientService privateLetterClientService;

	@Resource
	private IdGeneratorUtil idGeneratorUtil ;

	@Override
	public SearchModuleVo getSearchModuleVo(String key) {
		SearchModuleVo smv = new SearchModuleVo() ;

		//从redis中取全部出发地
		List<String> departurePlaces = DeparturePlaceUtil.getDeparturePlaces();
		smv.setDeparturePlaceList(departurePlaces);

		List<ProductDestination> destinations = new ArrayList<ProductDestination>();
		//加"全部"选项
		ProductDestination allDest = new ProductDestination();
		allDest.setDestName("全部");
		List<String> allDestSub = new ArrayList<String>();
		allDestSub.add("世界");
		allDest.setSubDestinations(allDestSub);
		destinations.add(allDest);
		//从redis中取可用目的地(精确到第二级)
		List<ProductDestination> availableDestinations = ProductDestUtil.getAvailableDestinations(key);
		destinations.addAll(availableDestinations);

		smv.setDestinations(destinations);

		//从redis中取默认目的地
		String defaultDest = ProductDestUtil.getDefaultDestination();
		if( StringUtils.isNotBlank(defaultDest) ){
			smv.setDefaultDestination(defaultDest);
		}else{
			smv.setDefaultDestination(Const.DESTINATION_PLACEHOLDER);
		}
		return smv;
	}

	@Override
	public WorkPlatFormVo getWorkPlatFormVo(HttpServletRequest request) {
		WorkPlatFormVo wpfv = new WorkPlatFormVo() ;
		MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;
		Integer loginFailCount = 0 ;
		List<PrivateLetterVo> pls = null ;
		List<SystemNotice> sns = null ;
		boolean isPlAllRead = true ;
		boolean isSnAllRead = true ;
		if(memberSessionBean != null){
			loginFailCount = memberSessionBean.getLoginFailCount() ;
			if(memberSessionBean.getIsLogin() && memberSessionBean.getIsActive()){
				wpfv.setIsLogin(true);
				wpfv.setUserName(memberSessionBean.getNickName());
				wpfv.setHeadImageId(memberSessionBean.getImageId());
				pls = (List<PrivateLetterVo>) privateLetterClientService.list(SSOUtil.getMemberId(), 1, 18).getData() ;
				if(pls == null) pls = new ArrayList<PrivateLetterVo>() ;
				try {
					sns = systemNoticeClientService.list(SSOUtil.getMemberId(), 18) ;
				} catch (Exception e) {
					LOGGER.error("get systemNotic error...", e);
				}
				if(pls != null && pls.size() > 0){
					for(PrivateLetterVo pl : pls){
						if(!pl.isHasRead()) isPlAllRead = false ;
					}
				}
				if(sns != null && sns.size() > 0){
					for(SystemNotice sn : sns){
						if(!sn.isHasRead()) isSnAllRead = false ;
					}
				}
				wpfv.setIsPlAllRead(isPlAllRead);
				wpfv.setIsSnAllRead(isSnAllRead);
				wpfv.setPls(pls);
				wpfv.setSns(sns);
				wpfv.setWxLogined(isWxLogined(memberSessionBean.getMemberId()));
			}else{
				wpfv.setWxLogined(false);
			}
		}
		wpfv.setTicket(getTicket());
		wpfv.setLoginFailCount(loginFailCount);
		return wpfv;
	}

	/**
	 * 生成显示微信二维码的ticket信息
	 * */
	@Override
	public String getTicket(){
		String ticketResult = null;
		try {
	        Integer senceId = Integer.parseInt(idGeneratorUtil.getSenceId());
	        ticketResult = createTicket(senceId);
	        recordSenceIdToRedis(senceId);
		}catch (Exception e) {
			LOGGER.error("生成微信二维码错误", e);
		}
		return ticketResult;
	}

	private String createTicket(Integer senceId) throws IOException {
	    String ticketResult = null;
	    String accessToken = redisClient.get("WECHAT_ACCESS_TOKEN");
        if(StringUtils.isBlank(accessToken)){
            throw ZtrBizException.instance("", "微信access_token为空");
        }
        URL qrCodeUrl = new URL(QRCODEURL.replace("#ACCESS_TOKEN#", accessToken));
        JSONObject requestObject = new JSONObject();
        JSONObject actionInfoObjetc = new JSONObject();
        JSONObject sceneObject = new JSONObject();
        requestObject.put("expire_seconds", EXPIRE_SECONDS);
        requestObject.put("action_name", ACTION_NAME);

        sceneObject.put("scene_id", senceId);
        actionInfoObjetc.put("scene", sceneObject);
        requestObject.put("action_info", actionInfoObjetc);
        LOGGER.info("二维码请求参数信息:[{}]",requestObject.toJSONString());
        String response = HttpUtil.doPost(qrCodeUrl, CONTENT_TYPE, requestObject.toJSONString());
        if(StringUtils.isNotBlank(response)){
            ticketResult = (String) JSONObject.parseObject(response).get("ticket");
        }
        return ticketResult;
	}

	private void recordSenceIdToRedis(Integer senceId) {
        MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;
	    JSONObject info = new JSONObject();
        if(null != memberSessionBean && StringUtils.isNotBlank(memberSessionBean.getMemberId())){
            info.put("cookies", TokenHolder.get());
            info.put("memberId", memberSessionBean.getMemberId());
            info.put("type", "wxLogin");
        }else{
            info.put("cookies", TokenHolder.get());
            info.put("type", "login");
        }
        redisClient.set(senceId.toString(), info);
	}

	private void recordBindSenceIdToRedis(Integer senceId) {
        MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;
        if(null != memberSessionBean && StringUtils.isNotBlank(memberSessionBean.getMemberId())){
            JSONObject info = new JSONObject();
            info.put("cookies", TokenHolder.get());
            info.put("memberId", memberSessionBean.getMemberId());
            info.put("type", "bind");
            redisClient.set(senceId.toString(), info);
        }else{
            LOGGER.info("生成用户绑定微信账号信息二维码时获取不到memberSessionBean信息");
        }
    }

	private void recordUnbindSenceIdToRedis(Integer senceId) {
        MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;
        if(null != memberSessionBean && StringUtils.isNotBlank(memberSessionBean.getMemberId())){
            JSONObject info = new JSONObject();
            info.put("cookies", TokenHolder.get());
            info.put("memberId", memberSessionBean.getMemberId());
            info.put("type", "unbind");
            redisClient.set(senceId.toString(), info);
        }else{
            LOGGER.info("生成用户解绑微信账号信息二维码时获取不到memberSessionBean信息");
        }
    }

    @Override
    public String getUnbindTicket() {
        String ticketResult = null;
        try {
            Integer senceId = Integer.parseInt(idGeneratorUtil.getSenceId());
            ticketResult = createTicket(senceId);
            recordUnbindSenceIdToRedis(senceId);
        }catch (Exception e) {
            LOGGER.error("生成微信二维码错误", e);
        }
        return ticketResult;
    }

    @Override
    public String getBindTicket() {
        String ticketResult = null;
        try {
            Integer senceId = Integer.parseInt(idGeneratorUtil.getSenceId());
            ticketResult = createTicket(senceId);
            recordBindSenceIdToRedis(senceId);
        }catch (Exception e) {
            LOGGER.error("生成微信二维码错误", e);
        }
        return ticketResult;
    }

	@Override
	public Boolean isWxLogined(String memberId){
	    return MemberClientService.isWxLogined(memberId);
	}

}
