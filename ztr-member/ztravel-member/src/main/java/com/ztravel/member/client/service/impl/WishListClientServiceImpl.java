package com.ztravel.member.client.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ztravel.member.client.service.IWishListClientService;
import com.ztravel.member.dao.IWishListDao;
import com.ztravel.member.po.WishEntity;

@Service
public class WishListClientServiceImpl implements IWishListClientService {

	@Resource
	private IWishListDao wishListDaoImpl;

	@Override
	public boolean isWishorNot(String memberId, String productId) throws Exception{

		List<WishEntity> wishs = wishListDaoImpl.queryWishs(memberId, productId);

		if(wishs == null || wishs.size() < 1){
			return false;
		}else{
			return true;
		}

	}

	@Override
	public void deleteWishByProductId(String productId) throws Exception{

		wishListDaoImpl.deleteWishByProductId(productId);

	}

}
