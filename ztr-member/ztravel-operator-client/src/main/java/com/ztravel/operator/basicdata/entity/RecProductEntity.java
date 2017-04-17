package com.ztravel.operator.basicdata.entity;

import java.util.List;

import com.github.jmkgreen.morphia.annotations.Entity;

@Entity(value="recProduct",noClassnameStored=true)



public class RecProductEntity {
private String Id;

//@Pattern(regexp = "^[^/^{^}^^^*^>^\\]{0,18}$")
private String mainTitle;

//@Pattern(regexp = "^[^/^{^}^^^*^>^\\]{0,20}$")
private String viceTitle;

//@Pattern(regexp = "^[0-9a-zA-Z]{0,10}$")
private String productId;

//业务类型
private String productType;

private String pictureId;
private int priority;

private Double lowestPrice ;
private List<String> tags ;

private List<String> highLights ;

public Double getLowestPrice() {
	return lowestPrice;
}
public void setLowestPrice(Double lowestPrice) {
	this.lowestPrice = lowestPrice;
}
public List<String> getTags() {
	return tags;
}
public void setTags(List<String> tags) {
	this.tags = tags;
}
public List<String> getHighLights() {
	return highLights;
}
public void setHighLights(List<String> highLights) {
	this.highLights = highLights;
}


public String getId() {
	return Id;
}
public void setId(String id) {
	Id = id;
}
public String getMainTitle() {
	return mainTitle;
}
public void setMainTitle(String mainTitle) {
	this.mainTitle = mainTitle;
}
public String getViceTitle() {
	return viceTitle;
}
public void setViceTitle(String viceTitle) {
	this.viceTitle = viceTitle;
}
public String getProductId() {
	return productId;
}
public void setProductId(String productId) {
	this.productId = productId;
}
public String getPictureId() {
	return pictureId;
}
public void setPictureId(String pictureId) {
	this.pictureId = pictureId;
}
public int getPriority() {
	return priority;
}
public void setPriority(int priority) {
	this.priority = priority;
}
public String getProductType() {
    return productType;
}
public void setProductType(String productType) {
    this.productType = productType;
}

}
