package com.ztravel.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 金额计算器
 * 提供加减乘除的链式调用(最小精度为分，暂不提供进位)
 * @author liuzhuo
 *
 */
public class MoneyCalculator {


	private BigDecimal value;

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public MoneyCalculator(BigDecimal value) {
		this.value = value;
	}

	public MoneyCalculator(String value) {
		this.value = new BigDecimal(value);
	}

	public MoneyCalculator(Long value) {
		this.value = new BigDecimal(value);
	}


	public MoneyCalculator plus(Long plus) {
		BigDecimal baseBd = new BigDecimal(plus);
		return new MoneyCalculator(this.value.add(baseBd));
	}

	public MoneyCalculator plus(String plus) {
		BigDecimal baseBd = new BigDecimal(plus);
		return new MoneyCalculator(this.value.add(baseBd));
	}

	public MoneyCalculator minus(Long minus) {
		BigDecimal minusBd = new BigDecimal(minus);
		return new MoneyCalculator(this.value.subtract(minusBd));
	}

	public MoneyCalculator minus(String minus) {
		BigDecimal minusBd = new BigDecimal(minus);
		return new MoneyCalculator(this.value.subtract(minusBd));
	}

	public MoneyCalculator multiply(Integer multiplyNum) {
		BigDecimal multiplyNumBd = new BigDecimal(multiplyNum);
		return new MoneyCalculator(this.value.multiply(multiplyNumBd));
	}

	public MoneyCalculator divide(Integer divideNum) {
		BigDecimal divideNumBd = new BigDecimal(divideNum);
		return new MoneyCalculator(this.value.divide(divideNumBd, 2, RoundingMode.HALF_UP ));
	}

	public MoneyCalculator yuanToFen() {
		return this.multiply(100);
	}

	public MoneyCalculator fenToYuan() {
		return this.divide(100);
	}

	public String toString() {
		return this.value.toString();
	}

	public Long toLong() {
		return this.value.longValue();
	}


	public static void main(String[] args) {
		MoneyCalculator money = new MoneyCalculator(100L);
		String result = money.plus(100L).fenToYuan().toString();
		System.out.println(result);

	}

}
