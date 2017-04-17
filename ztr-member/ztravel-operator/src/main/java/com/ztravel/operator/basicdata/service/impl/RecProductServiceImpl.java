package com.ztravel.operator.basicdata.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mongodb.MongoException;
import com.ztravel.operator.basicdata.dao.IRecProductDAO;
import com.ztravel.operator.basicdata.entity.RecProductEntity;
import com.ztravel.operator.basicdata.service.IRecProductService;


@Service
public class RecProductServiceImpl implements IRecProductService{
@Resource
private IRecProductDAO recProductDao;



public void saveRecProduct(List<RecProductEntity> recproductlist) throws MongoException{

	recProductDao.deleteRecProductCollection();
	recProductDao.insertRecProductList(recproductlist);

}

public List<RecProductEntity> searchRecProduct() throws MongoException{
	return recProductDao.searchRecProduct();
}





}
