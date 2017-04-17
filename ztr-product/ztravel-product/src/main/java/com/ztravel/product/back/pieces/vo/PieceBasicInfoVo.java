package com.ztravel.product.back.pieces.vo;

import java.util.List;

public class PieceBasicInfoVo {

    private String id;

    private String pid;

    private String pname;

    private String type;

    private List<String> toContinent;//洲

    private List<String> toCountry;

    private List<String> toCity;

    private String nature;

    //产品进度
    private Integer progress;

    private Boolean withNext;

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public List<String> getToContinent() {
        return toContinent;
    }

    public void setToContinent(List<String> toContinent) {
        this.toContinent = toContinent;
    }

    public List<String> getToCountry() {
        return toCountry;
    }

    public void setToCountry(List<String> toCountry) {
        this.toCountry = toCountry;
    }

    public List<String> getToCity() {
        return toCity;
    }

    public void setToCity(List<String> toCity) {
        this.toCity = toCity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getWithNext() {
        return withNext;
    }

    public void setWithNext(Boolean withNext) {
        this.withNext = withNext;
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

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

}
