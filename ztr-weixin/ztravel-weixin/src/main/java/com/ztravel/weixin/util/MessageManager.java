package com.ztravel.weixin.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztravel.weixin.bean.BaseMessageBean;
import com.ztravel.weixin.bean.ImageMessageBean;
import com.ztravel.weixin.bean.NewsItem;
import com.ztravel.weixin.bean.NewsMessageBean;
import com.ztravel.weixin.bean.TextMessageBean;
import com.ztravel.weixin.constant.Material;

public class MessageManager {

	@SuppressWarnings({ "serial", "static-access" })
	public static Map<String, BaseMessageBean> eventMap = new HashMap<String, BaseMessageBean>() {{

		put("subscribe", new TextMessageBean(Material.getTextMaterial().SUBSCRIBE_MSG));

		put("23", new TextMessageBean(Material.getTextMaterial().KF_MSG));

		put("24", new TextMessageBean(Material.getTextMaterial().ABOUTUS_MSG));

//		put("31", new NewsMessageBean(Material.getNewsMaterial().item18));
//
//		put("32", new NewsMessageBean(Material.getNewsMaterial().item5));
//
//		List<NewsItem> item2List = new ArrayList<NewsItem>();
//    	item2List.add(Material.getNewsMaterial().item2_1);
//    	item2List.add(Material.getNewsMaterial().item2_2);
//    	item2List.add(Material.getNewsMaterial().item2_3);
//    	item2List.add(Material.getNewsMaterial().item2_4);

    	List<NewsItem> item1List = new ArrayList<NewsItem>();
        item1List.add(Material.getNewsMaterial().item20_1);
        item1List.add(Material.getNewsMaterial().item20_2);
        put("31", new NewsMessageBean(item1List));

        put("32", new NewsMessageBean(Material.getNewsMaterial().item21));

		put("33", new NewsMessageBean(Material.getNewsMaterial().item_more));

		put("34", new ImageMessageBean(Material.getImageMaterial().FANS_MEDIAID));

	}};

	@SuppressWarnings({ "serial", "static-access" })
	public static Map<String, BaseMessageBean> keyWordMap = new HashMap<String, BaseMessageBean>() {{

	    	List<NewsItem> item2List = new ArrayList<NewsItem>();
	    	item2List.add(Material.getNewsMaterial().item2_1);
	    	item2List.add(Material.getNewsMaterial().item2_2);
	    	item2List.add(Material.getNewsMaterial().item2_3);
	    	item2List.add(Material.getNewsMaterial().item2_4);

	    	put("more", new NewsMessageBean(Material.getNewsMaterial().item_more));
	    	put("1", new NewsMessageBean(Material.getNewsMaterial().item1));
	    	put("2", new NewsMessageBean(item2List));
	    	put("3",  new NewsMessageBean(Material.getNewsMaterial().item3));
	    	put("4", new NewsMessageBean(Material.getNewsMaterial().item4));
	    	put("5", new NewsMessageBean(Material.getNewsMaterial().item5));
	    	put("6", new NewsMessageBean(Material.getNewsMaterial().item6));
	    	put("7", new NewsMessageBean(Material.getNewsMaterial().item7));
	    	put("8", new NewsMessageBean(Material.getNewsMaterial().item8));
	    	put("9", new NewsMessageBean(Material.getNewsMaterial().item9));
	    	put("10", new NewsMessageBean(Material.getNewsMaterial().item10));
	    	put("11", new NewsMessageBean(Material.getNewsMaterial().item11));
	    	put("12", new NewsMessageBean(Material.getNewsMaterial().item12));
	    	put("13", new NewsMessageBean(Material.getNewsMaterial().item13));
	    	put("14", new NewsMessageBean(Material.getNewsMaterial().item14));
	    	put("15", new NewsMessageBean(Material.getNewsMaterial().item15));
	    	put("16", new NewsMessageBean(Material.getNewsMaterial().item16));
	    	put("17", new NewsMessageBean(Material.getNewsMaterial().item17));
	    	put("18", new NewsMessageBean(Material.getNewsMaterial().item18));
	    	put("19", new NewsMessageBean(Material.getNewsMaterial().item19));

	    }};

}
