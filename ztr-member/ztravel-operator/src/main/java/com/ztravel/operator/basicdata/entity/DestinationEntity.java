package com.ztravel.operator.basicdata.entity;

import java.util.List;

import org.bson.types.ObjectId;

import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;

/**
 * 产品目的地数据结构
 * @author MH
 */
@Entity(value="destination",noClassnameStored=true)
public class DestinationEntity {

	@Id
	private ObjectId id;

	private List<Destination> destinationList;
	/**
	 * 默认显示目的地
	 */
	private String defaultDestination;

	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public List<Destination> getDestinationList() {
		return destinationList;
	}
	public void setDestinationList(List<Destination> destinationList) {
		this.destinationList = destinationList;
	}
	public String getDefaultDestination() {
		return defaultDestination;
	}
	public void setDefaultDestination(String defaultDestination) {
		this.defaultDestination = defaultDestination;
	}

}
