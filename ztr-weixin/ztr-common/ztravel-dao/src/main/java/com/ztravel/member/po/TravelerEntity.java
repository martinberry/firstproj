package com.ztravel.member.po;

import java.util.List;

import org.bson.types.ObjectId;

import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;

/**
 * 常旅客保存信息实体
 * @author liuzhuo
 *
 */
@Entity(value="traveler", noClassnameStored=true)
public class TravelerEntity{


	@Id
	private ObjectId id;

	/**
	 * 页面存储id,不能直接转成ObjectId
	 */
	private String pageStoreId;

	/**
	 * 用户id
	 */
	private String memberId;

	/**
	 * 常旅客中文名
	 */
	private String travelerNameCn;

	/**
	 * 常旅客英文名
	 */
	private String travelerNameEn;


	/**
	 * 常旅客中文 姓
	 */
	private String firstNameCn;

	/**
	 * 常旅客中文 名
	 */
	private String lastNameCn;


	/**
	 * 常旅客英文名
	 */
	private String firstEnName;

	/**
	 * 常旅客英文名
	 */
	private String lastEnName;

	/**
	 * 常旅客电话
	 */
	private String phoneNum;

	/**
	 * 常旅客国籍
	 */
	private String nationality;

	/**
	 * 常旅客证件信息
	 */
	private List<Credentials> credentials;

	/**
	 * 常旅客性别
	 */
	private String gender;

	/**
	 * 常旅客类型(成人 儿童 婴儿)
	 */
	private String travelType;

	/**
	 * 常旅客生日
	 */
	private String birthday;

	/**
	 * 常旅客邮箱
	 */
	private String email;

	/**
	 * 逻辑删除
	 */
	private boolean isActive;

	/**
	 * 是否常用旅客
	 */
	private boolean isDefault;

	/**
	 * 省
	 */
	private String province ;
	/**
	 * 市
	 */
	private String city ;
	/**
	 * 区
	 */
	private String district ;
	/**
	 * 详细地址
	 */
	private String detailAddress ;

	public ObjectId getId() {
		return id;
	}




	public void setId(ObjectId id) {
		this.id = id;
	}




	public String getTravelerNameCn() {
		return travelerNameCn;
	}




	public void setTravelerNameCn(String travelerNameCn) {
		this.travelerNameCn = travelerNameCn;
	}




	public String getFirstEnName() {
		return firstEnName;
	}




	public void setFirstEnName(String firstEnName) {
		this.firstEnName = firstEnName;
	}




	public String getLastEnName() {
		return lastEnName;
	}




	public void setLastEnName(String lastEnName) {
		this.lastEnName = lastEnName;
	}




	public String getPhoneNum() {
		return phoneNum;
	}




	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}




	public String getNationality() {
		return nationality;
	}




	public void setNationality(String nationality) {
		this.nationality = nationality;
	}




	public List<Credentials> getCredentials() {
		return credentials;
	}




	public void setCredentials(List<Credentials> credentials) {
		this.credentials = credentials;
	}




	public String getGender() {
		return gender;
	}




	public void setGender(String gender) {
		this.gender = gender;
	}




	public String getTravelType() {
		return travelType;
	}




	public void setTravelType(String travelType) {
		this.travelType = travelType;
	}




	public String getBirthday() {
		return birthday;
	}




	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}




	public boolean getIsActive() {
		return isActive;
	}




	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}




	public String getMemberId() {
		return memberId;
	}




	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}




	public boolean getIsDefault() {
		return isDefault;
	}




	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}



	public String getPageStoreId() {
		return pageStoreId;
	}




	public void setPageStoreId(String pageStoreId) {
		this.pageStoreId = pageStoreId;
	}





	public static class Credentials {

		/**
		 * 证件类型
		 */
		private String type;

		/**
		 * 证件号码
		 */
		private String number;

		/**
		 * 证件有效期
		 */
		private String deadLineDay;

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getNumber() {
			return number;
		}

		public void setNumber(String number) {
			this.number = number;
		}

		public String getDeadLineDay() {
			return deadLineDay;
		}

		public void setDeadLineDay(String deadLineDay) {
			this.deadLineDay = deadLineDay;
		}


		}





	public String getProvince() {
		return province;
	}




	public void setProvince(String province) {
		this.province = province;
	}




	public String getCity() {
		return city;
	}




	public void setCity(String city) {
		this.city = city;
	}




	public String getDistrict() {
		return district;
	}




	public void setDistrict(String district) {
		this.district = district;
	}




	public String getDetailAddress() {
		return detailAddress;
	}




	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public String getTravelerNameEn() {
		return travelerNameEn;
	}




	public void setTravelerNameEn(String travelerNameEn) {
		this.travelerNameEn = travelerNameEn;
	}




	public String getFirstNameCn() {
		return firstNameCn;
	}




	public void setFirstNameCn(String firstNameCn) {
		this.firstNameCn = firstNameCn;
	}




	public String getLastNameCn() {
		return lastNameCn;
	}




	public void setLastNameCn(String lastNameCn) {
		this.lastNameCn = lastNameCn;
	}




	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}




	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}


	}

