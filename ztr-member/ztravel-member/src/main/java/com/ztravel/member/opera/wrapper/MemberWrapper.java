package com.ztravel.member.opera.wrapper;

import java.util.List;

import com.ztravel.member.opera.vo.MemberVO;

public class MemberWrapper {
	private int total;
	private List<MemberVO> list;

	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<MemberVO> getList() {
		return list;
	}
	public void setList(List<MemberVO> list) {
		this.list = list;
	}

}
