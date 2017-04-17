package com.ztravel.common.entity;

/**
 * 订单乘客信息
 * @author liuzhuo
 *
 */
public class PassengerInfo {

	/**
	 * 旅客姓名
	 */
	private String passengerName;

	private String passengerEnName;

	/**
	 * 中文姓
	 */
	private String firstName;

	/**
	 * 中文 名
	 */
	private String lastName;

	/**
	 * 英文 姓
	 */
	private String firstNameEn;

	/**
	 * 英文 名
	 */
	private String lastNameEn;

	/**
	 * 乘客类型(成人 儿童 婴儿)
	 */
	private String type;

	/**
	 * 证件类型
	 */
	private String credentialType;

	/**
	 * 证件号
	 */
	private String credentialNum;

	/**
	 * 证件有效期
	 */
	private String credentialsDeadLine;

	/**
	 * 国籍
	 */
	private String country;

	/**
	 * 出生日期
	 */
	private String birthday;

	/**
	 * 性别
	 */
	private String gender;

	/**
	 * 是否保存为常旅客
	 */
	private boolean savePassenger;

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCredentialType() {
		return credentialType;
	}

	public void setCredentialType(String credentialType) {
		this.credentialType = credentialType;
	}

	public String getCredentialNum() {
		return credentialNum;
	}

	public void setCredentialNum(String credentialNum) {
		this.credentialNum = credentialNum;
	}

	public String getCredentialsDeadLine() {
		return credentialsDeadLine;
	}

	public void setCredentialsDeadLine(String credentialsDeadLine) {
		this.credentialsDeadLine = credentialsDeadLine;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public boolean isSavePassenger() {
		return savePassenger;
	}

	public void setSavePassenger(boolean savePassenger) {
		this.savePassenger = savePassenger;
	}

	public String getPassengerEnName() {
		return passengerEnName;
	}

	public void setPassengerEnName(String passengerEnName) {
		this.passengerEnName = passengerEnName;
	}

	public String getFirstNameEn() {
		return firstNameEn;
	}

	public void setFirstNameEn(String firstNameEn) {
		this.firstNameEn = firstNameEn;
	}

	public String getLastNameEn() {
		return lastNameEn;
	}

	public void setLastNameEn(String lastNameEn) {
		this.lastNameEn = lastNameEn;
	}


}
