package com.ztravel.operator.basicdata.dao;


import java.util.List;

import com.mongodb.MongoException;
import com.ztravel.operator.basicdata.entity.RecProductEntity;

public interface IRecProductDAO {



	public void insertRecProductList(List<RecProductEntity> recproductlist) throws MongoException;

	public void deleteRecProductCollection() throws MongoException;

	public List<RecProductEntity> searchRecProduct() throws MongoException;

}
