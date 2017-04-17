package com.ztravel.weixin.front.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.ztravel.weixin.back.entity.ViewState;
import com.ztravel.weixin.dao.IViewStateDao;
import com.ztravel.weixin.front.service.IViewStateService;

@Service
public class ViewStateServiceImpl implements IViewStateService {

	private final static Logger logger = LoggerFactory.getLogger(ViewStateServiceImpl.class);

    @Resource
    private IViewStateDao viewStateDaoImpl ;

	@Override
	public String getUrlById(String id) {
		logger.info("weixinOpenIdServlet 传过来的id: [{}]",id);
		String url = "";
		Map<String, Object> params = Maps.newHashMap();
		params.put("id", id);
		List<ViewState> viewStates = viewStateDaoImpl.select(params);
		if(viewStates != null && viewStates.size() >0){
			url = viewStates.get(0).getUrl();
		}
		logger.info("viewState url is : [{}]",url);
		return url;
	}

}
