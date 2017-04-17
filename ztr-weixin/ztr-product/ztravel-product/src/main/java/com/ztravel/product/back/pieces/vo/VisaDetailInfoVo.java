package com.ztravel.product.back.pieces.vo;

import java.util.List;

public class VisaDetailInfoVo {

    private String id;

    private String pid;

    //产品进度
    private Integer progress;

    private Boolean withNext;

    private Boolean isInterview;

    private String validate;

    private String stayTime;

    private String scope;

    private String times;

    private List<String> images;

    public Boolean getIsInterview() {
        return isInterview;
    }

    public void setIsInterview(Boolean isInterview) {
        this.isInterview = isInterview;
    }

    public String getValidate() {
        return validate;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }

    public String getStayTime() {
        return stayTime;
    }

    public void setStayTime(String stayTime) {
        this.stayTime = stayTime;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

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

}
