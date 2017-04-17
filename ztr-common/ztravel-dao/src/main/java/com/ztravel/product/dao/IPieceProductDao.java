package com.ztravel.product.dao;

import java.util.List;
import java.util.Map;

import com.ztravel.common.enums.Nature;
import com.ztravel.common.enums.ProductStatus;
import com.ztravel.product.po.pieces.common.PieceProduct;
import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;
import com.ztravel.product.po.pieces.visa.VisaProduct;

public interface IPieceProductDao {

    String insertPieceBasicInfo(PieceProduct product);

    int updatePiecePublicInfo(PieceProduct product);

    <T> int updatePieceProduct(PieceProduct product, Class<T> clazz);

	String save(PieceProduct product) throws Exception;

	Boolean delete(Nature nature, String id);

	PieceProduct queryPieceProductById(String id, String nature);

    VisaProduct queryVisaProductById(String id);

    UnVisaProduct queryUnVisaProductById(String id);

    Boolean updatePieceStatus(String id, Nature nature, ProductStatus status);

    Long getCountByConditions(Map<String, Map<String, String>> map);

    List<PieceProduct> findByConditions(Map<String, Map<String, String>> map, int pageNum, int pageSize);

    List<VisaProduct> findVisaByConditions(Map<String, Map<String, String>> map);

    List<UnVisaProduct> findUnVisaByConditions(Map<String, Map<String, String>> map);
}
