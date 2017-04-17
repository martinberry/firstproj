package com.ztravel.member.po;

import org.joda.time.DateTime;

import com.ztravel.member.enums.BonusStatus;
import com.ztravel.member.enums.TravelStatus;


/**
 * @author wanhaofan
 * */
public class BalanceDetailEntity {
	private String memberId ;
	private String friend ;
	private String fMobile ;

	private TravelStatus fStatus ;

	private Long bonus ;

	private String bonusStr;

	private BonusStatus bStatus ;

	private DateTime updateTime ;

	private DateTime createTime ;


	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getFriend() {
		return friend;
	}

	public void setFriend(String friend) {
		this.friend = friend;
	}

	public String getfMobile() {
		return fMobile;
	}

	public void setfMobile(String fMobile) {
		this.fMobile = fMobile;
	}

	public TravelStatus getfStatus() {
		return fStatus;
	}

	public void setfStatus(TravelStatus fStatus) {
		this.fStatus = fStatus;
	}

	public Long getBonus() {
		return bonus;
	}

	public void setBonus(Long bonus) {
		this.bonus = bonus;
	}

	public BonusStatus getbStatus() {
		return bStatus;
	}

	public void setbStatus(BonusStatus bStatus) {
		this.bStatus = bStatus;
	}

	public DateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(DateTime updateTime) {
		this.updateTime = updateTime;
	}

	public DateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}

	public String getBonusStr() {
		return bonusStr;
	}

	public void setBonusStr(String bonusStr) {
		this.bonusStr = bonusStr;
	}


}
