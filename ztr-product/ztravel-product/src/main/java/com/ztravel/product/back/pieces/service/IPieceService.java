package com.ztravel.product.back.pieces.service;

import java.util.Map;

import com.ztravel.common.enums.Nature;
import com.ztravel.common.enums.ProductStatus;
import com.ztravel.product.back.pieces.bean.PieceResponseBean;
import com.ztravel.product.back.pieces.entity.PieceSearchCriteria;
import com.ztravel.product.back.pieces.vo.PieceMenuVo;

public interface IPieceService {

    PieceMenuVo getPieceMenuVo(String id, String nature);

    PieceResponseBean updatePieceStatus(String id, Nature nature, ProductStatus status);

    PieceResponseBean checkPiece(String id, Nature nature);

    Map<String, Object> search(PieceSearchCriteria searchCriteria);

    Boolean deletePieceProductByNatureAndId(Nature nature, String id);

}
