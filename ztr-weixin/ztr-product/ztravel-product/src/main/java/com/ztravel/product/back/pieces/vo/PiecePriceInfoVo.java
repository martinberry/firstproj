package com.ztravel.product.back.pieces.vo;

import java.util.List;

import com.ztravel.product.po.pieces.common.PriceInfo;

public class PiecePriceInfoVo {

    private String id;

    private String pid;

    private String nature;

    private Integer progress;

    private Boolean withNext;

    private String priceType;

    private List<PriceInfo> priceInfos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Boolean getWithNext() {
        return withNext;
    }

    public void setWithNext(Boolean withNext) {
        this.withNext = withNext;
    }

    public List<PriceInfo> getPriceInfos() {
        return priceInfos;
    }

    public void setPriceInfos(List<PriceInfo> priceInfos) {
        this.priceInfos = priceInfos;
    }

    public String getPriceType() {
        return priceType;
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType;
    }

}
