package com.ztravel.product.weixin.vo;

import java.util.List;
import java.util.Map;

import com.ztravel.product.po.pieces.visa.VisaDetailInfo;
import com.ztravel.product.po.pieces.visa.VisaMaterial;
import com.ztravel.product.po.pieces.visa.VisaProcess;

public class VisaProductDetailVo extends PieceProductDetailVo {
	
	private VisaDetailInfo detailInfo;

	private Map<String,List<VisaMaterial>> materias;

	private List<VisaProcess> processes;
	
	private String lowest;

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

	public String getLowest() {
		return lowest;
	}

	public void setLowest(String lowest) {
		this.lowest = lowest;
	}

}
