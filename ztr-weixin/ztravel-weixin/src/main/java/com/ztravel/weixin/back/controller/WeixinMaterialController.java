package com.ztravel.weixin.back.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.weixin.back.common.MaterialCount;
import com.ztravel.weixin.back.entity.MaterialMediaEntity;
import com.ztravel.weixin.back.entity.MaterialNewsItemEntity;
import com.ztravel.weixin.back.entity.SearchMaterialEntity;
import com.ztravel.weixin.back.entity.ShowMaterialEntity;
import com.ztravel.weixin.back.service.IMaterialMediaService;
import com.ztravel.weixin.back.service.IMaterialNewsItemService;
import com.ztravel.weixin.constant.Material;
import com.ztravel.weixin.operate.MaterialOperator;
import com.ztravel.weixin.servlet.AccessTokenThread;

@Controller
@RequestMapping("/weixinMaterialController")
public class WeixinMaterialController {
	  @Resource
	    private IdGeneratorUtil idGeneratorUtil ;

	    @Resource
	    IMaterialMediaService materialMediaServiceImpl;

	    @Resource
	    IMaterialNewsItemService materialNewsItemServiceImpl;

    // static String flag="done";

	    @RequestMapping(value = "/insertMaterial")
	    public String insertMaterial() {
	    	try{
	    		insertTextMaterial();
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    	return null;
	    }

	    private void insertTextMaterial() throws Exception{
//	    	MaterialMediaEntity materialMedia = new MaterialMediaEntity();
//	    	materialMedia.setId(idGeneratorUtil.getMaterialMediaId());
//	    	materialMedia.setLastModifyTime(DateTime.now());
//	    	materialMedia.setType("text");
//	    	materialMedia.setMediaId("subscribe");
//	    	materialMedia.setName(Material.getTextMaterial().SUBSCRIBE_MSG);
//	    	materialMediaServiceImpl.save(materialMedia);

	    	MaterialMediaEntity materialMedia1 = new MaterialMediaEntity();
	    	materialMedia1.setId(idGeneratorUtil.getMaterialMediaId());
	    	materialMedia1.setLastModifyTime(DateTime.now());
	    	materialMedia1.setType("text");
	    	materialMedia1.setMediaId("23");
	    	materialMedia1.setName(Material.getTextMaterial().KF_MSG);
	    	materialMediaServiceImpl.save(materialMedia1);

	    	MaterialMediaEntity materialMedia2 = new MaterialMediaEntity();
	    	materialMedia2.setId(idGeneratorUtil.getMaterialMediaId());
	    	materialMedia2.setLastModifyTime(DateTime.now());
	    	materialMedia2.setType("text");
	    	materialMedia2.setMediaId("24");
	    	materialMedia2.setName(Material.getTextMaterial().ABOUTUS_MSG);
	    	materialMediaServiceImpl.save(materialMedia2);
	    }


	    @RequestMapping(value = "/collectMaterial")
	    @ResponseBody
	    public String collectMaterial() throws Exception {
	    	 JSONObject jsonobj=new JSONObject();

		    	 materialMediaServiceImpl.deleteAllMedia();
		    	 materialNewsItemServiceImpl.deleteAllNews();
		    	try{
		    		String accessToken=AccessTokenThread.getAccessToken();
		        JSONObject materialCountJson = MaterialOperator.getMaterialCount(accessToken);
		        List<MaterialCount> materialCountList = getMaterialCount(materialCountJson);
		        for (MaterialCount materialCount : materialCountList) {
		            String type = materialCount.getType();
		            int count = materialCount.getCount();
		            if (count > 0) {
		                int tax = count % 20 > 0 ? 1 : 0;
		                for (int i = 0; i < count / 20 + tax; i++ ) {
		                    String params = "{\"type\":\"" + type + "\",\"offset\":\"" + (i * 20) + "\",\"count\":\"20\"}";
		                    JSONObject jsonObj = MaterialOperator.getBatchMaterial(params, accessToken);
		                    JSONArray materialsJson = jsonObj.getJSONArray("item");
		                    if (materialsJson != null) {
		              recordMaterial(type, materialsJson);
		                    }

		                }
		            }
		        }
		        jsonobj.put("res_code","ok");
		        insertTextMaterial();

		        return JSONObject.toJSONString(jsonobj);
		    	}catch(Exception e){
		    		jsonobj.put("res_code","failed");

		    		return JSONObject.toJSONString(jsonobj);
		    	}



	    }


	    @RequestMapping(value="/showMaterial",method=RequestMethod.POST)
	  public String showMaterial(@RequestBody SearchMaterialEntity showparam,Model model) throws Exception{
		  List<ShowMaterialEntity> showlist=new ArrayList<ShowMaterialEntity>();
		  MaterialMediaEntity mediap=new MaterialMediaEntity();
		  MaterialNewsItemEntity newsp=new MaterialNewsItemEntity();
		  String title=showparam.getTitle();

		  mediap.setName(title);
		  newsp.setTitle(title);
		  int newscounts=materialNewsItemServiceImpl.countNews(title);
		  int mediacounts=materialMediaServiceImpl.countMedia(title);
		  int facttotalcounts=newscounts+mediacounts;
		  int listtotalcounts=0;
		  String typesearch=showparam.getType();
		 /* if (typesearch.equals("modalshow")){
			  listtotalcounts= 0;
		  }*/
		 if(typesearch.equals("search")){
			  if(title!=null||title==""){
				  listtotalcounts= facttotalcounts;
			  }
			  else{
				  listtotalcounts=0;
			  }
		  }
		  else{
			  if(title!=null&&title!=""){
				  listtotalcounts= facttotalcounts;
			  }
			  else{
				  listtotalcounts=0;
			  }
		  }

		  int No=showparam.getPageNo();
		  mediap.setPageNo(No);
		  newsp.setPageNo(No);
		  int pageNo=0;
		  int pageSize=showparam.getPageSize();
		  mediap.setPageSize(pageSize);
		  newsp.setPageSize(pageSize);
			if( facttotalcounts != 0 ){
				 pageNo=No;
			}else{
				 pageNo=1;
			}
			int totalPageCount=0;
         if(facttotalcounts!=0){
        	   totalPageCount = (int) Math.ceil( (double)facttotalcounts/pageSize);
         }
         else{
        	  totalPageCount=1;
         }
		  int newstotalPageCount = (int) Math.ceil( (double)newscounts/pageSize);
		  int left=newscounts%pageSize;
		  int templimit=pageSize-left;
		  if(No<newstotalPageCount||No==newstotalPageCount){
			  List<ShowMaterialEntity> newslist=materialNewsItemServiceImpl.NewsShow(newsp);
			  showlist.addAll(newslist);
		  }
		  if((No==newstotalPageCount&&left!=0)||No>newstotalPageCount){
			  List<ShowMaterialEntity> medialist=materialMediaServiceImpl.MediaShow(mediap, templimit,newstotalPageCount);
			  showlist.addAll(medialist);
		  }
		  model.addAttribute("totalItemCount", facttotalcounts);
		  model.addAttribute("pageNo",pageNo);
		  model.addAttribute("pageSize",pageSize);
		  model.addAttribute("searchItemCount",listtotalcounts);
		  model.addAttribute("showlist",showlist);
		  model.addAttribute("totalPageCount",totalPageCount);
		  return "back/weixinMenu/materialdata";
	  }

/*
	    @RequestMapping(value="/confirm",method=RequestMethod.POST)
	    @ResponseBody
        public String confirmMaterial(ShowMaterialEntity show){
	    	 JSONObject jsonobj=new JSONObject();
	    	String mediaId=show.getMediaId();
	    	List<String> title=show.getTitle();
	    	String titleone=title.get(0);
	    	  jsonobj.put("mediaId",mediaId);
	    	  jsonobj.put("title",titleone);
	    	  return JSONObject.toJSONString(jsonobj);
        }
*/

	    public List<MaterialCount> getMaterialCount(JSONObject materialCountJson) {
	        List<MaterialCount> list = new ArrayList<MaterialCount>();
	        int voice_count = materialCountJson.getIntValue("voice_count");
	        int video_count = materialCountJson.getIntValue("video_count");
	        int image_count = materialCountJson.getIntValue("image_count");
	        int news_count = materialCountJson.getIntValue("news_count");
	        list.add(new MaterialCount("voice", voice_count));
	        list.add(new MaterialCount("video", video_count));
	        list.add(new MaterialCount("image", image_count));
	        list.add(new MaterialCount("news", news_count));
	        return list;
	    }


	    private void recordMaterial(String type, JSONArray materialsJson) throws Exception {

	        List<MaterialMediaEntity> materialMediaList = new ArrayList<MaterialMediaEntity>();
	        List<MaterialNewsItemEntity> materialNewsItemList = null;
	        if (type.equals("news")) {
	            materialNewsItemList = new ArrayList<MaterialNewsItemEntity>();
	        }
	        for (int i = 0 ; i < materialsJson.size(); i++) {
	            JSONObject materialJson = materialsJson.getJSONObject(i);
	            String media_id = materialJson.getString("media_id");
	            if (type.equals("news")) {
	                JSONObject contentJson = materialJson.getJSONObject("content");
	                JSONArray newsItemsJson = contentJson.getJSONArray("news_item");
	               if (newsItemsJson != null) {
	            	   List<MaterialNewsItemEntity> onenews=recordNewsItem(newsItemsJson,media_id);
                       materialNewsItemList.addAll(onenews);
	               }
	            }
	            else{
	            MaterialMediaEntity materialMedia=recordMedia(materialJson,type);
	            materialMediaList.add(materialMedia);
	            }

	        }
	        //同步之前清除数据



            //同步存储到本地数据库
	        if (materialMediaList.size() > 0 ) {

	            materialMediaServiceImpl.collectMaterialMediaForAdd(materialMediaList);
	        }

	        if (materialNewsItemList != null && materialNewsItemList.size() > 0 ) {

	            materialNewsItemServiceImpl.collectNewsItemForAdd(materialNewsItemList);
	        }
	    }

	    private List<MaterialNewsItemEntity> recordNewsItem( JSONArray newsItemsJson, String media_id) throws Exception {

	        List<MaterialNewsItemEntity> list = new ArrayList<MaterialNewsItemEntity>();

	        for (int i = 0 ; i < newsItemsJson.size(); i++) {
	            MaterialNewsItemEntity materialNewsItem = new MaterialNewsItemEntity();
	            JSONObject newsItemJson = newsItemsJson.getJSONObject(i);
	            materialNewsItem.setId(idGeneratorUtil.getMaterialNewsItemId());
	            materialNewsItem.setTitle(newsItemJson.getString("title"));
	            materialNewsItem.setThumbMediaId(newsItemJson.getString("thumb_media_id"));
	            materialNewsItem.setShowCoverPic(newsItemJson.getString("show_cover_pic"));
	            materialNewsItem.setAuthor(newsItemJson.getString("author"));
	            materialNewsItem.setDigest(newsItemJson.getString("digest"));
	            materialNewsItem.setUrl(newsItemJson.getString("url"));
	            materialNewsItem.setContentSourceUrl(newsItemJson.getString("content_source_url"));
	            materialNewsItem.setLastModifyTime(new DateTime());
	            materialNewsItem.setPriority(i);
	            materialNewsItem.setRelatedMediaId(media_id);
	            list.add(materialNewsItem);
	        }
	        return list;

	    }


	    private MaterialMediaEntity recordMedia(JSONObject materialJson, String type) throws Exception{
	    	String media_id = materialJson.getString("media_id");
	    	String update_time = materialJson.getString("update_time");
            String name = materialJson.getString("name");
            String url = materialJson.getString("url");

            MaterialMediaEntity materialMedia = new MaterialMediaEntity();
            materialMedia.setId(idGeneratorUtil.getMaterialMediaId());
            materialMedia.setMediaId(media_id);
            materialMedia.setUpdateTime(update_time);
            materialMedia.setName(name);
            materialMedia.setUrl(url);
            materialMedia.setType(type);
            materialMedia.setLastModifyTime(new DateTime());
            return materialMedia;

	    }


}
