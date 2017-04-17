package com.ztravel.product.back.pieces.service;

import com.ztravel.product.back.pieces.vo.UnVisaDetailInfoVo;
import com.ztravel.product.back.pieces.vo.VisaDetailInfoVo;

public interface IPieceDetailInfoService extends IPieceService {

    /**
     * 根据id查询获取签证产品的详情信息Vo
     * @param id
     * @return
     * @throws Exception
     */
    VisaDetailInfoVo queryVisaDetailInfoVoById(String id) throws Exception;

    /**
     * 根据id查询获取非签证产品的详情信息Vo
     * @param id
     * @return
     * @throws Exception
     */
    UnVisaDetailInfoVo queryUnVisaDetailInfoVoById(String id) throws Exception;

    /**
     * 保存非签证产品详情信息
     * @param unVisaDetailInfoVo
     * @return
     * @throws Exception
     */
    void save(UnVisaDetailInfoVo unVisaDetailInfoVo)throws Exception;

    /**
     * 保存签证产品详情信息
     * @param visaDetailInfoVo
     * @return
     * @throws Exception
     */
    void save(VisaDetailInfoVo visaDetailInfoVo)throws Exception;

}
