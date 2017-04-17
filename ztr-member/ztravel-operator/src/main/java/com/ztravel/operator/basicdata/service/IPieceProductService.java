package com.ztravel.operator.basicdata.service;

import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;
import com.ztravel.product.po.pieces.visa.VisaProduct;

public interface IPieceProductService {

    VisaProduct queryVisaProductByPid(String pid) throws Exception;

    UnVisaProduct queryUnVisaProductByPid(String pid) throws Exception;

}
