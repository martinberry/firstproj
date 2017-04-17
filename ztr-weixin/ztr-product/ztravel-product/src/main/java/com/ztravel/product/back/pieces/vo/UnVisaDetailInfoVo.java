package com.ztravel.product.back.pieces.vo;

import java.util.List;

import com.ztravel.common.enums.LanguageType;

public class UnVisaDetailInfoVo {

    private String id;

    private String pid;

    //产品进度
    private Integer progress;

    private Boolean withNext;

    /**
     * {@link LanguageType}
     * */
    private String languageType;

    private String serviceTime;

    private List<String> images;

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

    public String getLanguageType() {
        return languageType;
    }

    public void setLanguageType(String languageType) {
        this.languageType = languageType;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

}
