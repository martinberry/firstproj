package com.ztravel.product.back.pieces.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.enums.Nature;
import com.ztravel.common.enums.ProductStatus;
import com.ztravel.product.back.pieces.bean.PieceResponseBean;
import com.ztravel.product.back.pieces.convert.PieceProductConvert;
import com.ztravel.product.back.pieces.entity.PieceSearchCriteria;
import com.ztravel.product.back.pieces.service.IPieceService;
import com.ztravel.product.back.pieces.utils.PiecesCheckValidator;
import com.ztravel.product.back.pieces.vo.PieceMenuVo;
import com.ztravel.product.back.pieces.vo.PieceProductVo;
import com.ztravel.product.dao.IPieceProductDao;
import com.ztravel.product.po.pieces.common.PieceProduct;
import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;
import com.ztravel.product.po.pieces.visa.VisaProduct;

@Service
public class PieceServiceImpl implements IPieceService {

    private static final Logger LOGGER = RequestIdentityLogger.getLogger(PieceServiceImpl.class) ;

    @Resource
    protected IPieceProductDao pieceProductDaoImpl;

    @Override
    public PieceMenuVo getPieceMenuVo(String id, String nature) {

        PieceMenuVo menuVo = new PieceMenuVo() ;
        PieceProduct pieceProduct = pieceProductDaoImpl.queryPieceProductById(id, nature);
        menuVo.setCode(pieceProduct.getPid());
        menuVo.setName(pieceProduct.getBasicInfo().getPname());
        if (Nature.VISA.name().equals(nature)) {
            menuVo.setType(Nature.VISA.getDesc());
        } else if (Nature.UNVISA.name().equals(nature)) {
            menuVo.setType(Nature.UNVISA.getDesc());
        }
        menuVo.setNature(pieceProduct.getNature());
        menuVo.setPid(pieceProduct.getId().toString());
        menuVo.setProgress(pieceProduct.getProgress() == null ? 0 : pieceProduct.getProgress());
        return menuVo;
    }

    @Override
    public PieceResponseBean updatePieceStatus(String id, Nature nature, ProductStatus status) {
        PieceResponseBean bean = new PieceResponseBean() ;
        bean.setFlag(true);
        if (status == ProductStatus.RELEASED) {
            bean = checkPiece(id, nature);
        }
        if (bean.getFlag()) {
            bean.setFlag(pieceProductDaoImpl.updatePieceStatus(id, nature, status)) ;
            if (!bean.getFlag()) {
                bean.setMsg("更新碎片化产品数据出错");
            }
        }
        return bean ;
    }

    @Override
    public PieceResponseBean checkPiece(String id, Nature nature) {

        PieceResponseBean bean = new PieceResponseBean() ;
        bean.setFlag(true);

        try {
            if (Nature.VISA.equals(nature)) {
                    VisaProduct visaProduct = pieceProductDaoImpl.queryVisaProductById(id);
                    PiecesCheckValidator.AssertVisaProduct(visaProduct);
            } else if (Nature.UNVISA.equals(nature)) {
                UnVisaProduct unVisaProduct = pieceProductDaoImpl.queryUnVisaProductById(id);
                PiecesCheckValidator.AssertUnVisaProduct(unVisaProduct);
            } else {
                bean.setFlag(false);
                bean.setMsg("非碎片化产品");
            }
        } catch (IllegalArgumentException e) {
            LOGGER.error("checkPiece 检查结果为：" + e.getMessage(), e);
            bean.setFlag(false);
            bean.setMsg(e.getMessage());
        }
        return bean;
    }

    @Override
    public Map<String, Object> search(PieceSearchCriteria searchCriteria) {

        Map<String, Object>  resultMap = new HashMap<String, Object>();

        int pageNo = searchCriteria.getPageNo() < 1 ? 1 : searchCriteria.getPageNo();
        int pageSize = searchCriteria.getPageSize() < 1 ? 1 : searchCriteria.getPageSize();

        Map<String, Map<String, String>> searchMap = convert2serachParam(searchCriteria);

        List<PieceProduct> pieceProductList = pieceProductDaoImpl.findByConditions(searchMap, pageNo, pageSize);
        List<PieceProductVo> pieceProductVoList = PieceProductConvert.convert2PieceProductVoList(pieceProductList);
        resultMap.put("pieceProductVoList", pieceProductVoList);

        Long totalItemCount = pieceProductDaoImpl.getCountByConditions(searchMap);
        Integer totalPageCount = (int) Math.ceil(new Double(totalItemCount) / pageSize);

        resultMap.put("pageNo", pageNo);
        resultMap.put("pageSize", pageSize);
        resultMap.put("totalItemCount", totalItemCount);
        resultMap.put("totalPageCount", totalPageCount);

        return resultMap;
    }

    private Map<String, Map<String, String>> convert2serachParam(PieceSearchCriteria searchCriteria) {

        Map<String, Map<String, String>> searchParams = Maps.newHashMap();

        Map<String, String> subPrams = Maps.newHashMap();
        if (StringUtils.isNotEmpty(searchCriteria.getPid())) {
            subPrams.put("pid", searchCriteria.getPid().trim());
        }
        if (StringUtils.isNotEmpty(searchCriteria.getStatus()) && !"ALL".equals(searchCriteria.getStatus())) {
            subPrams.put("status", searchCriteria.getStatus());
        }
        searchParams.put("sub", subPrams);

        Map<String, String> basicPrams = Maps.newHashMap();
        if (StringUtils.isNotEmpty(searchCriteria.getPname())) {
            basicPrams.put("pname", searchCriteria.getPname().trim());
        }
        if (StringUtils.isNotEmpty(searchCriteria.getType()) && !"ALL".equals(searchCriteria.getType())) {
            basicPrams.put("type", searchCriteria.getType());
        }
        if (StringUtils.isNotEmpty(searchCriteria.getToContinent()) && !"全部".equals(searchCriteria.getToContinent())) {
            basicPrams.put("toContinent", searchCriteria.getToContinent());
        }
        if (StringUtils.isNotEmpty(searchCriteria.getToCountry()) && !"全部".equals(searchCriteria.getToCountry())) {
            basicPrams.put("toCountry", searchCriteria.getToCountry());
        }
        if (StringUtils.isNotEmpty(searchCriteria.getToCity()) && !"全部".equals(searchCriteria.getToCity())) {
            basicPrams.put("toCity", searchCriteria.getToCity());
        }
        searchParams.put("basic", basicPrams);

        return searchParams;
    }

    @Override
    public Boolean deletePieceProductByNatureAndId(Nature nature, String id) {
        return pieceProductDaoImpl.delete(nature, id);
    }

}
