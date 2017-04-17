package com.ztravel.product.back.pieces.service;

import com.ztravel.product.back.pieces.vo.PieceBasicInfoVo;

public interface IPieceBasicInfoService extends IPieceService {

    /**
     * 保存基本信息，可以是新增或者修改
     * @param pieceBasicInfoVo
     * @return
     * @throws Exception
     */
    String save(PieceBasicInfoVo pieceBasicInfoVo)throws Exception;

    /**
     * 根据id查询获取碎片产品的基本信息Vo
     * @param id
     * @param nature
     * @return
     * @throws Exception
     */
    PieceBasicInfoVo queryPieceBasicInfoVoById(String id, String nature) throws Exception;

}
