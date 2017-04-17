package com.ztravel.sso.po;

import org.joda.time.DateTime;

public class OpenidMemberEntity {

    private String openId;

    private String memberId;

    private String preMemberId;

    private DateTime loginTime;

    private String token;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public DateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(DateTime loginTime) {
        this.loginTime = loginTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPreMemberId() {
        return preMemberId;
    }

    public void setPreMemberId(String preMemberId) {
        this.preMemberId = preMemberId;
    }

}
