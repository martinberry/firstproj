package com.ztravel.operator.basicdata.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ztravel.operator.basicdata.service.IPieceProductService;
import com.ztravel.product.dao.IUnVisaProductDao;
import com.ztravel.product.dao.IVisaProductDao;
import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;
import com.ztravel.product.po.pieces.visa.VisaProduct;

@Service
public class PieceProductServiceImpl implements IPieceProductService {

    @Resource
    IVisaProductDao visaProductDao;

    @Resource
    IUnVisaProductDao unVisaProductDao;

    @Override
    public VisaProduct queryVisaProductByPid(String pid) throws Exception {
        return visaProductDao.getProductByPid(pid);
    }

    @Override
    public UnVisaProduct queryUnVisaProductByPid(String pid) throws Exception {
        return unVisaProductDao.getUnvisaProductByPid(pid);
    }

}
