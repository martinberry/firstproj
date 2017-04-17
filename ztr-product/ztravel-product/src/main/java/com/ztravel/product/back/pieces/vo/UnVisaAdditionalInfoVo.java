package com.ztravel.product.back.pieces.vo;

import java.util.Map;

public class UnVisaAdditionalInfoVo {

    private String id;

    private String pid;

    private Integer progress;

    private Boolean withNext;

    private Map<String, String> additionalInfos;

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

    public Map<String, String> getAdditionalInfos() {
        return additionalInfos;
    }

    public void setAdditionalInfos(Map<String, String> additionalInfos) {
        this.additionalInfos = additionalInfos;
    }

}
