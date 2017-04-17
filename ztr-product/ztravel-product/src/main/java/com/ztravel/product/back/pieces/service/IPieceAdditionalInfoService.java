package com.ztravel.product.back.pieces.service;

import com.ztravel.product.back.pieces.vo.UnVisaAdditionalInfoVo;
import com.ztravel.product.back.pieces.vo.VisaAdditionalInfoVo;

public interface IPieceAdditionalInfoService extends IPieceService {

    /**
     * 根据id查询获取签证产品的附加信息Vo
     * @param id
     * @return
     * @throws Exception
     */
    VisaAdditionalInfoVo queryVisaAdditionalInfoVoById(String id) throws Exception;

    /**
     * 根据id查询获取非签证产品的附加信息Vo
     * @param id
     * @return
     * @throws Exception
     */
    UnVisaAdditionalInfoVo queryUnVisaAdditionalInfoVoById(String id) throws Exception;

    /**
     * 保存非签证产品附加信息
     * @param unVisaAdditionalInfoVo
     * @return
     * @throws Exception
     */
    void save(UnVisaAdditionalInfoVo unVisaAdditionalInfoVo)throws Exception;

    /**
     * 保存签证产品附加信息
     * @param visaAdditionalInfoVo
     * @return
     * @throws Exception
     */
    void save(VisaAdditionalInfoVo visaAdditionalInfoVo)throws Exception;

}
