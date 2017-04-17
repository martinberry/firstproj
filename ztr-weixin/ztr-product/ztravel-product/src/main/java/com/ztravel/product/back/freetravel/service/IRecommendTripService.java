package com.ztravel.product.back.freetravel.service;

import com.ztravel.product.back.freetravel.vo.RecommendTripVo;

public interface IRecommendTripService {
	RecommendTripVo queryByid(String id)throws Exception;
	void save(RecommendTripVo vo)throws Exception;
}
