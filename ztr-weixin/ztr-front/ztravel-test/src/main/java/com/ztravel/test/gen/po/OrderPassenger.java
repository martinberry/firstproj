package com.ztravel.test.gen.po;

public class OrderPassenger {
    private String id;

    private String orderId;

    private String name;

    private String firstName;

    private String lastName;

    private String passengerType;

    private String credentialType;

    private String credentialNum;

    private String credentialDeadLine;

    private String country;

    private String birthday;

    private String gender;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName == null ? null : firstName.trim();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName == null ? null : lastName.trim();
    }

    public String getPassengerType() {
        return passengerType;
    }

    public void setPassengerType(String passengerType) {
        this.passengerType = passengerType == null ? null : passengerType.trim();
    }

    public String getCredentialType() {
        return credentialType;
    }

    public void setCredentialType(String credentialType) {
        this.credentialType = credentialType == null ? null : credentialType.trim();
    }

    public String getCredentialNum() {
        return credentialNum;
    }

    public void setCredentialNum(String credentialNum) {
        this.credentialNum = credentialNum == null ? null : credentialNum.trim();
    }

    public String getCredentialDeadLine() {
        return credentialDeadLine;
    }

    public void setCredentialDeadLine(String credentialDeadLine) {
        this.credentialDeadLine = credentialDeadLine == null ? null : credentialDeadLine.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }
}