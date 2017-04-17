package com.ztravel.product.back.freetravel.utils;

public class CharCounter {
	public static boolean isLetter(char c) {
		int k = 0x80;
		return c / k == 0 ? true : false;
	}
	/**
	 * 判断字符串是否为空
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		if (str == null || str.trim().equals("")
				|| str.trim().equalsIgnoreCase("null")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
	 *
	 * @param String
	 *            s 需要得到长度的字符串
	 * @return int 得到的字符串长度
	 */
	public static int getLength(String s) {
		if (s == null)
			return 0;
		char[] c = s.toCharArray();
		int len = 0;
		for (int i = 0; i < c.length; i++) {
			len++;
			if (!isLetter(c[i])) {
				len++;
			}
		}
		return len;
	}

	public static void main(String[] args) {
		System.out.println(getLength("aa哈"));
	}

}
