package com.ztravel.product.back.pieces.service;

import com.ztravel.product.back.pieces.vo.PiecePriceInfoVo;

public interface IPiecePriceInfoService extends IPieceService {

    /**
     * 根据id查询获取碎片产品的价格信息Vo（包括成本配置和价格维护）
     * @param id
     * @param nature
     * @return
     * @throws Exception
     */
    PiecePriceInfoVo queryPiecePriceInfoVoById(String id, String nature) throws Exception;

    /**
     * 保存价格信息
     * @param piecePriceInfoVo
     * @return
     * @throws Exception
     */
    void save(PiecePriceInfoVo piecePriceInfoVo)throws Exception;



}
