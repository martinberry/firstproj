package com.ztravel.reuse.member.event;

/**
 * @author haofan.wan
 *
 */
public class MemberEvent {

    private String memberId;

    private MemberEventType type ;

    private String token ;

    public MemberEvent(String memberId, MemberEventType type, String token) {
        this.memberId = memberId;
        this.type = type ;
        this.token = token ;
    }

	public String getMemberId() {
		return memberId;
	}

	public MemberEventType getType() {
		return type;
	}

	public void setType(MemberEventType type) {
		this.type = type;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
