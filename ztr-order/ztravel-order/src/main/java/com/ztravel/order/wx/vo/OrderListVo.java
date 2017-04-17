package com.ztravel.order.wx.vo;

import java.util.List;

public class OrderListVo {

	/**
	 * 标识数据是否查询正常
	 * */
	Boolean success;
	/**
	 * 总订单数
	 * */
	private Integer totalNum;
	/**
	 * 总的未支付订单数
	 * */
	private Integer totalUnpayNum;
	/**
	 * 总的未评论订单数
	 * */
	private Integer totalUnRecNum;
	/**
	 * 订单信息
	 * */
	private List<OrderVo> orderVoList;

	private Integer pageNum;

	private Integer totalPage;

	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public Integer getTotalUnpayNum() {
		return totalUnpayNum;
	}
	public void setTotalUnpayNum(Integer totalUnpayNum) {
		this.totalUnpayNum = totalUnpayNum;
	}
	public Integer getTotalUnRecNum() {
		return totalUnRecNum;
	}
	public void setTotalUnRecNum(Integer totalUnRecNum) {
		this.totalUnRecNum = totalUnRecNum;
	}
	public List<OrderVo> getOrderVoList() {
		return orderVoList;
	}
	public void setOrderVoList(List<OrderVo> orderVoList) {
		this.orderVoList = orderVoList;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
}
