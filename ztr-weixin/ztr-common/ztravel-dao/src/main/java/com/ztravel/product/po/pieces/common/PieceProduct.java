package com.ztravel.product.po.pieces.common;

import java.util.List;
import java.util.Map;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;

import com.github.jmkgreen.morphia.annotations.Id;
import com.ztravel.common.enums.AdditionalRule;
import com.ztravel.common.enums.DestinationType;
import com.ztravel.common.enums.Nature;
import com.ztravel.common.enums.ProductStatus;

public class PieceProduct {
	@Id
	private ObjectId id;

	private String pid;//编号

	private ProductStatus status;

	private DateTime createTime;

	private DateTime updateTime;

	private String creator;

	private String updator;

	/**
	 * {@link Nature}
	 * */
	private Nature nature;

	/**
	 * {@link DestinationType}
	 * */
	private DestinationType destinationType;


    private BasicInfo basicInfo;

    private List<PriceInfo> priceInfos;

    private Map<AdditionalRule, String> additionalInfos;

    //产品进度
    private Integer progress;
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public DateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(DateTime updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getUpdator() {
		return updator;
	}

	public void setUpdator(String updator) {
		this.updator = updator;
	}

	public DateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}

	public Nature getNature() {
		return nature;
	}

	public void setNature(Nature nature) {
		this.nature = nature;
	}

	public DestinationType getDestinationType() {
		return destinationType;
	}

	public void setDestinationType(DestinationType destinationType) {
		this.destinationType = destinationType;
	}

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public BasicInfo getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(BasicInfo basicInfo) {
        this.basicInfo = basicInfo;
    }

    public Map<AdditionalRule, String> getAdditionalInfos() {
        return additionalInfos;
    }

    public void setAdditionalInfos(Map<AdditionalRule, String> additionalInfos) {
        this.additionalInfos = additionalInfos;
    }

    public List<PriceInfo> getPriceInfos() {
        return priceInfos;
    }

    public void setPriceInfos(List<PriceInfo> priceInfos) {
        this.priceInfos = priceInfos;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }
}
