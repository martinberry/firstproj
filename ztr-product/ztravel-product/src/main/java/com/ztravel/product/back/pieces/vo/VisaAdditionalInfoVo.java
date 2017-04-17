package com.ztravel.product.back.pieces.vo;

import java.util.List;
import java.util.Map;

import com.ztravel.product.po.pieces.visa.VisaMaterial;
import com.ztravel.product.po.pieces.visa.VisaProcess;

public class VisaAdditionalInfoVo {

    private String id;

    private String pid;

    private Integer progress;

    private Boolean withNext;

    private Map<String, String> additionalInfos;

    private Map<String,List<VisaMaterial>> materias;

    private List<VisaProcess> processes;

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

    public Map<String, List<VisaMaterial>> getMaterias() {
        return materias;
    }

    public void setMaterias(Map<String, List<VisaMaterial>> materias) {
        this.materias = materias;
    }

    public List<VisaProcess> getProcesses() {
        return processes;
    }

    public void setProcesses(List<VisaProcess> processes) {
        this.processes = processes;
    }

}
