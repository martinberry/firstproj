package com.ztravel.weixin.front.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.site.lookup.util.StringUtils;
import com.ztravel.weixin.back.entity.MaterialMediaEntity;
import com.ztravel.weixin.back.entity.MaterialNewsItemEntity;
import com.ztravel.weixin.bean.BaseMessageBean;
import com.ztravel.weixin.bean.ImageMessageBean;
import com.ztravel.weixin.bean.NewsItem;
import com.ztravel.weixin.bean.NewsMessageBean;
import com.ztravel.weixin.bean.TextMessageBean;
import com.ztravel.weixin.dao.IMaterialMediaDao;
import com.ztravel.weixin.dao.IMaterialNewsItemDao;
import com.ztravel.weixin.front.service.IMaterialMediaService;

@Service
public class MaterialMediaServiceImpl implements IMaterialMediaService {

	private final static Logger logger = LoggerFactory.getLogger(MaterialMediaServiceImpl.class);

    @Resource
    private IMaterialMediaDao materialMediaDaoImpl ;

    @Resource
    private IMaterialNewsItemDao materialNewsItemDaoImpl ;

    public static String FIRST= "first";


	@Override
	public BaseMessageBean buildMessageByKey(String eventKey){
		logger.info("buildMessageByKey key :{}", eventKey);
		Map<String, Object> params = Maps.newHashMap();
		try{
			params.put("mediaId", eventKey);
			List<MaterialMediaEntity> materialMedias = materialMediaDaoImpl.select(params);
			MaterialMediaEntity materialMedia = new MaterialMediaEntity();
			if(materialMedias != null && materialMedias.size()>0){
				materialMedia = materialMedias.get(0);
				if(materialMedia != null){
					String type = materialMedia.getType();
					if(type != null){
						 if(type.equals("text") ){
							 TextMessageBean messageBean = new TextMessageBean(materialMedia.getName());
							 return messageBean;
						 }else if(type.equals("image")){
							 ImageMessageBean imageMessage = new ImageMessageBean(materialMedia.getMediaId());
							 return imageMessage;
						 }else if(type.equals("vedio")){
							 //TODO
						 }
					}
				}
			}else{
				Map<String, String> materialNewsItemParams = Maps.newHashMap();
				materialNewsItemParams.put("relatedMediaId", eventKey);
				List<MaterialNewsItemEntity> materialNewsItems = materialNewsItemDaoImpl.select(materialNewsItemParams);
				if(materialNewsItems != null && materialNewsItems.size()>0){
					MaterialNewsItemEntity materialNewsItem = materialNewsItems.get(0);
					 NewsMessageBean messageBean = new NewsMessageBean();
					 messageBean = convert2NewsMessage(materialNewsItem);
					 return messageBean;
				}
			}
		}catch(Exception e){
			logger.error("通过eventKey: [{}]构造messageBean 失败: "+ e.toString(), eventKey);
		}
		return null;

	}

	private NewsMessageBean convert2NewsMessage(MaterialNewsItemEntity materialNewsItem) {
		String relatedMediaId = materialNewsItem.getRelatedMediaId();
		return convert2NewsMessage(relatedMediaId);
	}

	private NewsMessageBean convert2NewsMessage(String relatedMediaId){
		NewsMessageBean newsMessage = new NewsMessageBean();
		if(StringUtils.isNotEmpty(relatedMediaId)){
			Map<String, Object> params = Maps.newHashMap();
			params.put("relatedMediaId", relatedMediaId);
			List<MaterialNewsItemEntity> materialNewsItemList = materialNewsItemDaoImpl.select(params);
			if(materialNewsItemList != null && materialNewsItemList.size()>0){
				List<NewsItem> itemList = new ArrayList<NewsItem>();
				for(MaterialNewsItemEntity materialNewsItem : materialNewsItemList){
					String picUrl = getPicUrlByNewsItem(materialNewsItem);
					NewsItem newsItem = new NewsItem(materialNewsItem.getTitle(), "", picUrl, materialNewsItem.getUrl());
					itemList.add(newsItem);
				}
				newsMessage = new NewsMessageBean(itemList);
			}
		}
		return newsMessage;
	}

	private String getPicUrlByNewsItem(MaterialNewsItemEntity materialNewsItem) {
		String picUrl = "";
		String thumbMediaId = materialNewsItem.getThumbMediaId();
		Map<String, Object> params = Maps.newHashMap();
		params.put("mediaId", thumbMediaId);
		List<MaterialMediaEntity> materialMediaEntityList = materialMediaDaoImpl.select(params);
		if(materialMediaEntityList != null && materialMediaEntityList.size()>0){
			picUrl = materialMediaEntityList.get(0).getUrl();
		}
		return picUrl;
	}




}
