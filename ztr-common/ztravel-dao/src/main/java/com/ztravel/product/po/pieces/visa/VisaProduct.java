package com.ztravel.product.po.pieces.visa;

import java.util.List;
import java.util.Map;

import com.github.jmkgreen.morphia.annotations.Entity;
import com.ztravel.product.po.pieces.common.PieceProduct;

@Entity(value="VisaProduct", noClassnameStored=true)
public class VisaProduct extends PieceProduct{

	private VisaDetailInfo detailInfo;

	private Map<String,List<VisaMaterial>> materias;

	private List<VisaProcess> processes;

    public VisaDetailInfo getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(VisaDetailInfo detailInfo) {
        this.detailInfo = detailInfo;
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
