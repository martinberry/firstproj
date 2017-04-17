package com.ztravel.product.back.pieces.convert;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;

import com.ztravel.product.back.pieces.vo.PieceProductVo;
import com.ztravel.product.po.pieces.common.PieceProduct;

public class PieceProductConvert {

    public static PieceProductVo entity2Vo(PieceProduct pieceProduct) {
        return null;
    }

    public static List<PieceProductVo> convert2PieceProductVoList(List<PieceProduct> pieceProductList) {
        List<PieceProductVo> pieceProductVoList = new ArrayList<PieceProductVo>();
        for (PieceProduct pieceProduct : pieceProductList) {
            PieceProductVo pieceProductVo = convert2PieceProductVo(pieceProduct);
            pieceProductVoList.add(pieceProductVo);
        }
        return pieceProductVoList;
    }

    private static PieceProductVo convert2PieceProductVo(PieceProduct pieceProduct) {
        PieceProductVo pv = new PieceProductVo();
        pv.setId(pieceProduct.getId().toString());
        pv.setPid(pieceProduct.getPid());
        if (pieceProduct.getBasicInfo() != null) {
            pv.setPname(pieceProduct.getBasicInfo().getPname());
            if (pieceProduct.getBasicInfo().getType() != null) {
                pv.setType(pieceProduct.getBasicInfo().getType().getDesc());
            }
            String to = listToString(pieceProduct.getBasicInfo().getToCity());
            pv.setTo(to);
        }
        pv.setStatus(pieceProduct.getStatus().name());
        pv.setNature(pieceProduct.getNature().name());
        pv.setCreateTime(dateTime2String(pieceProduct.getCreateTime()));
        pv.setUpdateTime(dateTime2String(pieceProduct.getUpdateTime()));

        return pv;
    }

    private static String dateTime2String(DateTime dateTime) {

        if (dateTime == null) return "";
        return  dateTime.toString("yyyy-MM-dd HH:mm:ss") ;
    }

    private static String listToString(List<String> list) {
        String result = "";
        if (CollectionUtils.isNotEmpty(list)) {
            for ( int i = 0 ; i < list.size(); i++) {
                if ( i+1 == list.size()) {
                    result = result + list.get(i);
                } else {
                    result = result + list.get(i) + " ";
                }
            }
        }
        return result;
    }

}
