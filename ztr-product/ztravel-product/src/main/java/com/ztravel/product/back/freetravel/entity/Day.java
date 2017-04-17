package com.ztravel.product.back.freetravel.entity;

/**
 * @author wanhaofan
 * 真旅行时间价格中的一天
 * */
public class Day {
	//日期
	private String day ;
	
	//团号
	private String teamNum ;

	//成本
	private Cost cost ;

	//销售情况
	private Sale sale ;

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public Cost getCost() {
		return cost;
	}

	public void setCost(Cost cost) {
		this.cost = cost;
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public String getTeamNum() {
		return teamNum;
	}

	public void setTeamNum(String teamNum) {
		this.teamNum = teamNum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		result = prime * result + ((day == null) ? 0 : day.hashCode());
		result = prime * result + ((sale == null) ? 0 : sale.hashCode());
		result = prime * result + ((teamNum == null) ? 0 : teamNum.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Day other = (Day) obj;
		if (cost == null) {
			if (other.cost != null)
				return false;
		} else if (!cost.equals(other.cost))
			return false;
		if (day == null) {
			if (other.day != null)
				return false;
		} else if (!day.equals(other.day))
			return false;
		if (sale == null) {
			if (other.sale != null)
				return false;
		} else if (!sale.equals(other.sale))
			return false;
		if (teamNum == null) {
			if (other.teamNum != null)
				return false;
		} else if (!teamNum.equals(other.teamNum))
			return false;
		return true;
	}

}
