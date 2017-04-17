package com.ztravel.operator.basicdata.entity;

import org.bson.types.ObjectId;

import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;

/**
 * 全球国家地区信息
 * @author liuzhuo
 *
 */

@Entity(value="countryArea",noClassnameStored=true)
public class CountryAreaEntity {
	
	@Id
	private ObjectId id;
	
	/**
	 * 中文名
	 */
	private String nameCn;
	
	/**
	 * 英文名
	 */
	private String namaEn;
	
	/**
	 * 英文全称
	 */
	private String fullNameEn;
	
	/**
	 * 二字码
	 */
	private String twoLetterCode;
	
	/**
	 * 三字码
	 */
	private String threeLetterCode;
	
	/**
	 * 数字编码
	 */
	private String numberCode;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getNameCn() {
		return nameCn;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

	public String getNamaEn() {
		return namaEn;
	}

	public void setNamaEn(String namaEn) {
		this.namaEn = namaEn;
	}

	public String getFullNameEn() {
		return fullNameEn;
	}

	public void setFullNameEn(String fullNameEn) {
		this.fullNameEn = fullNameEn;
	}

	public String getTwoLetterCode() {
		return twoLetterCode;
	}

	public void setTwoLetterCode(String twoLetterCode) {
		this.twoLetterCode = twoLetterCode;
	}

	public String getThreeLetterCode() {
		return threeLetterCode;
	}

	public void setThreeLetterCode(String threeLetterCode) {
		this.threeLetterCode = threeLetterCode;
	}

	public String getNumberCode() {
		return numberCode;
	}

	public void setNumberCode(String numberCode) {
		this.numberCode = numberCode;
	}
	
	
	
}
