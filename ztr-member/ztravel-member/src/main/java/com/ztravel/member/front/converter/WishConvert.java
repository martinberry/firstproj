package com.ztravel.member.front.converter;

import com.ztravel.member.front.vo.AddToWishRequest;
import com.ztravel.member.po.WishEntity;

public class WishConvert {
	public static WishEntity convertAddToWishRequest2WishEntity(AddToWishRequest request){
		WishEntity entity = new WishEntity() ;
		entity.setPid(request.getPid());
		entity.setProductId(request.getProductId());
		entity.setpName(request.getpName()) ;
		return entity ;
	}
}
