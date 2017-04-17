package com.ztravel.weixin.back.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.site.lookup.util.StringUtils;
import com.ztravel.weixin.back.entity.MaterialMediaEntity;
import com.ztravel.weixin.back.entity.MaterialNewsItemEntity;
import com.ztravel.weixin.back.entity.ShowMaterialEntity;
import com.ztravel.weixin.back.service.IMaterialMediaService;
import com.ztravel.weixin.bean.BaseMessageBean;
import com.ztravel.weixin.bean.ImageMessageBean;
import com.ztravel.weixin.bean.NewsItem;
import com.ztravel.weixin.bean.NewsMessageBean;
import com.ztravel.weixin.bean.TextMessageBean;
import com.ztravel.weixin.dao.IMaterialMediaDao;
import com.ztravel.weixin.dao.IMaterialNewsItemDao;

@Service
public class MaterialMediaServiceImpl implements IMaterialMediaService {

	private final static Logger logger = LoggerFactory.getLogger(MaterialMediaServiceImpl.class);

    @Resource
    private IMaterialMediaDao materialMediaDaoImpl ;

    @Resource
    private IMaterialNewsItemDao materialNewsItemDaoImpl ;

    public static String FIRST= "first";

    @Override
    @Transactional(rollbackFor=RuntimeException.class, propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED)
    public void collectMaterialMediaForAll(List<MaterialMediaEntity> list) {

        Map<String, List<MaterialMediaEntity>> materialMedias = devideMaterialMedia(list);
        List<MaterialMediaEntity> addMaterialMedias = materialMedias.get("ADD");
        List<MaterialMediaEntity> updateMaterialMedias = materialMedias.get("UPDATE");
        for (MaterialMediaEntity materialMedia : addMaterialMedias) {
            materialMediaDaoImpl.insert(materialMedia);
        }
        for (MaterialMediaEntity materialMedia : updateMaterialMedias) {
            materialMediaDaoImpl.update(materialMedia);
        }

    }

    @Override
    @Transactional(rollbackFor=RuntimeException.class, propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED)
    public void collectMaterialMediaForAdd(List<MaterialMediaEntity> list) {

        Map<String, List<MaterialMediaEntity>> materialMedias = devideMaterialMedia(list);
        List<MaterialMediaEntity> addMaterialMedias = materialMedias.get("ADD");
        for (MaterialMediaEntity materialMedia : addMaterialMedias) {
            materialMediaDaoImpl.insert(materialMedia);
        }

    }


    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public List<ShowMaterialEntity> MediaShow(MaterialMediaEntity mediap,int templimit,int newstotalPageCount){
    	List<ShowMaterialEntity> mediashowlist =new ArrayList<ShowMaterialEntity>();
    	Map map=new HashMap();
    	int pageSize=mediap.getPageSize();
    	String name=mediap.getName();
    	if(name!=null&&name!=""){
  	 		String searchtitle;
  	  		if(name.contains("%")){
  	  			searchtitle = name.replace("%", "\\%");
  	  		}
  	  		else{
  	  			searchtitle=name;
  	  		}
  	  		map.put("name", "%"+searchtitle+"%");
  		}

       	int pageNo=mediap.getPageNo();
       	int disPageNo=newstotalPageCount-1;
       	if((newstotalPageCount==pageNo)||((pageNo-1==newstotalPageCount)&&(templimit==pageSize))){
       		map.put("limit",templimit);
       		map.put("offset", 0);
       		FIRST="notfirst";
       	}
       	else{
       		map.put("limit", pageSize);
       		int overoffset;

       		if((templimit!=pageSize)&&(disPageNo!=0)){
       		overoffset=(pageNo-disPageNo-2)*pageSize+templimit;
       		}
       		else{
       		overoffset=(pageNo-2)*pageSize+templimit;
       		}
       		map.put("offset",overoffset);
       	}




    	List<MaterialMediaEntity> medialist=materialMediaDaoImpl.select(map);
    	for(MaterialMediaEntity media:medialist){
    		ShowMaterialEntity mediashow=mediaConvert(media);
    		mediashowlist.add(mediashow);
    	}
    	return mediashowlist;
    }



    @Override
    public ShowMaterialEntity mediaConvert(MaterialMediaEntity media){
    	ShowMaterialEntity showMaterial=new ShowMaterialEntity();
    	showMaterial.setMediaId(media.getMediaId());
    	List<String> titleone=new ArrayList<String>();
    	String onename=media.getName();
		if(onename.length()>50){
			onename=onename.substring(0, 51)+"...";
		}
    	titleone.add(onename);
    	showMaterial.setTitle(titleone);
    	showMaterial.setPicUrl(media.getUrl());
    	showMaterial.setType(media.getType());
    	return showMaterial;
    }

    @Override
    public  MaterialMediaEntity selectmediabyid(String mediaId){
    	Map<String,String> map=new HashMap<String,String> ();
    	map.put("mediaId",mediaId);
    	MaterialMediaEntity media=materialMediaDaoImpl.selectOne(map);
    	return media;
    }

    //搜索media记录数目
  	@SuppressWarnings({ "rawtypes", "unchecked" })
  	@Override
      public Integer countMedia(String name) throws Exception{
  		Map map=new HashMap ();


  		if(name!=""&&name!=null){
  			String tempname;
  			if(name.contains("%")){
  				tempname = name.replace("%", "\\%");
  	  		}
  			else{
  				tempname=name;
  			}
  			map.put("name","%"+tempname+"%");
  		}
  		return materialMediaDaoImpl.count(map);
  	}




    private List<String> getMediaIdList(List<MaterialMediaEntity> list) {
        List<String> mediaIdList = new ArrayList<String>();
        for (MaterialMediaEntity materialMedia : list) {
            String mediaId = materialMedia.getMediaId();
            mediaIdList.add(mediaId);
        }
        return mediaIdList;
    }

    private Map<String, List<MaterialMediaEntity>> devideMaterialMedia(List<MaterialMediaEntity> list) {

        List<String> mediaIdList = getMediaIdList(list);
        List<String> currentMediaIdList = materialMediaDaoImpl.selectMediaIdsByMediaIds(mediaIdList);
        List<MaterialMediaEntity> addList = new ArrayList<MaterialMediaEntity>();
        List<MaterialMediaEntity> updateList = new ArrayList<MaterialMediaEntity>();

        if (currentMediaIdList != null && currentMediaIdList.size() > 0) {
            for (MaterialMediaEntity materialMedia : list) {
                String mediaId = materialMedia.getMediaId();
                if (currentMediaIdList.contains(mediaId)) {
                    updateList.add(materialMedia);
                } else {
                    addList.add(materialMedia);
                }
            }
        } else {
            addList = list;
        }

        Map<String, List<MaterialMediaEntity>> map = new HashMap<String, List<MaterialMediaEntity>>();
        map.put("ADD", addList);
        map.put("UPDATE", updateList);

        return map;

    }

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


	@Override
	public void save(MaterialMediaEntity materialMedia) throws Exception{
		materialMediaDaoImpl.insert(materialMedia);
	}

	@Override
	public void deleteAllMedia(){
		materialMediaDaoImpl.deleteAll();
	}



}
