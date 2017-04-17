package com.ztravel.operator.basicdata.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ztravel.operator.basicdata.dao.IAdSpotDAO;
import com.ztravel.operator.basicdata.entity.AdSpotEntity;
import com.ztravel.operator.basicdata.service.IAdSpotService;

/**
 * 国家数据
 * @author MH
 */
@Service
public class AdSpotServiceImpl implements IAdSpotService {

	@Resource
	private IAdSpotDAO adSpotDaoImpl;

	@Override
	public void saveAdSpotList(List<AdSpotEntity> adSpotList) throws Exception {
		adSpotDaoImpl.deleteAll();
		adSpotDaoImpl.insertAdSpotList(adSpotList);
	}

	@Override
	public List<AdSpotEntity> getAdSpotList() throws Exception {
		return adSpotDaoImpl.getAdSpotList();
	}

}
