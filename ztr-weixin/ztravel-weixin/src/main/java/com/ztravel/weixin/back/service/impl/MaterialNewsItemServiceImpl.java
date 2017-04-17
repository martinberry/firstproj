package com.ztravel.weixin.back.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ztravel.weixin.back.entity.MaterialMediaEntity;
import com.ztravel.weixin.back.entity.MaterialNewsItemEntity;
import com.ztravel.weixin.back.entity.ShowMaterialEntity;
import com.ztravel.weixin.back.service.IMaterialNewsItemService;
import com.ztravel.weixin.dao.IMaterialMediaDao;
import com.ztravel.weixin.dao.IMaterialNewsItemDao;

@Service
public class MaterialNewsItemServiceImpl implements IMaterialNewsItemService {

    @Resource
    private IMaterialNewsItemDao materialNewsItemDaoImpl ;

    @Resource
    private IMaterialMediaDao materialMediaDaoImpl ;

    @Override
    @Transactional(rollbackFor=RuntimeException.class, propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED)
    public void collectNewsItemForAll(List<MaterialNewsItemEntity> list) {

    }

    @Override
    @Transactional(rollbackFor=RuntimeException.class, propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED)
    public void collectNewsItemForAdd(List<MaterialNewsItemEntity> list) {
        for (MaterialNewsItemEntity materialNewsItem : list) {
            materialNewsItemDaoImpl.insert(materialNewsItem);
        }
    }



    @Override
    public ShowMaterialEntity newsConvert(MaterialNewsItemEntity news,List<String> titlelist){
    	ShowMaterialEntity showMaterial=new ShowMaterialEntity();
    	showMaterial.setMediaId(news.getRelatedMediaId());
    	showMaterial.setTitle(titlelist);
    	String picId=news.getThumbMediaId();
    	MaterialMediaEntity media=materialMediaDaoImpl.selectById(picId);
    	if (media==null){
    		showMaterial.setPicUrl("");
    	}
    	else{
    		showMaterial.setPicUrl(media.getUrl());
    	}
    	showMaterial.setType("news");
    	return showMaterial;
    }


	@SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public List<ShowMaterialEntity> NewsShow(MaterialNewsItemEntity newsp){
    	List<ShowMaterialEntity> newshowlist=new ArrayList<ShowMaterialEntity>();

    	String title=newsp.getTitle();
    	List<String> idlist=selectRelated(title);
    	int pageSize=newsp.getPageSize();
    	int offset=(newsp.getPageNo()-1)*pageSize;
    	int limit=pageSize;

    	for(int i=offset;(i<offset+limit)&&(i<idlist.size());i++){
    		Map map=new HashMap ();
    		map.put("relatedMediaId",idlist.get(i));
    		List<MaterialNewsItemEntity> newslist= materialNewsItemDaoImpl.select(map);
    		MaterialNewsItemEntity mainnews=newslist.get(0);
    		MaterialNewsItemEntity news=new MaterialNewsItemEntity();
    		news.setRelatedMediaId(mainnews.getRelatedMediaId());
    		news.setThumbMediaId(mainnews.getThumbMediaId());
    		List<String> titlelist=new ArrayList<String>();
    		for(MaterialNewsItemEntity onenew:newslist){
    			String onetitle=onenew.getTitle();
    			if(onetitle.length()>50){
    				onetitle=onetitle.substring(0, 51)+"...";
    			}
    			titlelist.add(onetitle);
    		}
    		ShowMaterialEntity shownews=newsConvert(news,titlelist);
    		newshowlist.add(shownews);

    	}

    	return newshowlist;
    }





    @Override
    public List<MaterialNewsItemEntity> selectrelated(String mediaId){
    	Map<String,String> map=new HashMap<String,String>();
    	map.put("relatedMediaId", mediaId);
    	List<MaterialNewsItemEntity> newlist=materialNewsItemDaoImpl.select(map);
    	int sizes=newlist.size();
    	MaterialNewsItemEntity news=new MaterialNewsItemEntity();
    	for(int i=sizes-1;i>0;i--){
    		for(int t=0;t<i;t++){
    		int a=newlist.get(t).getPriority();
    		int b=newlist.get(t+1).getPriority();
    		if(a>b){
    			news=newlist.get(t+1);
    			newlist.set((t+1),newlist.get(i));
    			newlist.set(t,news);
    		}
    		}
    	}
  return newlist;
    }


  //搜索图文记录数目
  	@SuppressWarnings({ "rawtypes", "unchecked" })
  	@Override
      public Integer countNews(String title) throws Exception{
  		List<String> idlist=selectRelated(title);
  		return idlist.size();
  	}


//根据title找到relatedid,并排除重复的
	@SuppressWarnings({ "rawtypes", "unchecked" })
  	@Override
  	public List<String> selectRelated(String title){
		List<String> idlist=new ArrayList<String>();
  		Map map=new HashMap ();
  		if(title!=null&&title!=""){
  	 		String searchtitle;
  	  		if(title.contains("%")){
  	  			searchtitle = title.replace("%", "\\%");
  	  		}
  	  		else{
  	  			searchtitle=title;
  	  		}
  	  		map.put("title", "%"+searchtitle+"%");
  		}
  		List<MaterialNewsItemEntity> newslist=materialNewsItemDaoImpl.select(map);
  		for(MaterialNewsItemEntity news:newslist){
  			int flag=0;
  			String relatedid=news.getRelatedMediaId();
  			for(int i=0;i<idlist.size();i++){
  				String relatedtempid=idlist.get(i);
  				if(relatedid.equals(relatedtempid)){
  					flag=1;
  				}
  			}
  			if(flag==0){
  				idlist.add(relatedid);
  			}
  		}
  		return idlist;
  	}


	@Override
	public void deleteAllNews(){
		materialNewsItemDaoImpl.deleteAll();
	}


}