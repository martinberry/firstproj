package com.ztravel.operator.basicdata.service;

import java.util.List;

import com.mongodb.MongoException;
import com.ztravel.operator.basicdata.entity.RecProductEntity;

public interface IRecProductService {
	public void saveRecProduct(List<RecProductEntity> recproductlist) throws MongoException;
	public List<RecProductEntity> searchRecProduct() throws MongoException;
}
