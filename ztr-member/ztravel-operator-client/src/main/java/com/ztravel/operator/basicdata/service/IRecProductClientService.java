package com.ztravel.operator.basicdata.service;

import java.util.List;

import com.mongodb.MongoException;
import com.ztravel.operator.basicdata.entity.RecProductEntity;
import com.ztravel.operator.basicdata.entity.RecProductHomePageEntity;

public interface IRecProductClientService {
	public void saveRecProduct(List<RecProductEntity> recproductlist) throws MongoException;
	public List<RecProductHomePageEntity> searchRecProduct() throws Exception;
}
