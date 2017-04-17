package com.ztravel.paygate.server.alipay.test;

import java.security.MessageDigest;

import com.ztravel.paygate.server.alipay.api.AlipaySignMD5;

public class TestMD5 {
	/**
	 * md5加密
	 * 
	 * @param params
	 *            要签名的参数(升序后的参数)
	 * @param charsetname
	 *            编码需要与_input_charset一致
	 * @return
	 */
	public static String MD5Encode(String params, String charsetname) {
		String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
		String resultString = null;
		try {
			resultString = new String(params);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname)) {
				StringBuffer resultSb = new StringBuffer();
				byte[] b = md.digest(resultString.getBytes());
				for (int i = 0; i < b.length; i++) {
					int n = b[i];
					if (n < 0)
						n += 256;
					int d1 = n / 16;
					int d2 = n % 16;
					resultSb.append(hexDigits[d1] + hexDigits[d2]);
				}
				resultString = resultSb.toString();
			} else {
				StringBuffer resultSb = new StringBuffer();
				byte[] b = md.digest(resultString.getBytes(charsetname));
				for (int i = 0; i < b.length; i++) {
					int n = b[i];
					if (n < 0)
						n += 256;
					int d1 = n / 16;
					int d2 = n % 16;
					resultSb.append(hexDigits[d1] + hexDigits[d2]);
				}
				resultString = resultSb.toString();
			}
		} catch (Throwable thr) {
		}
		return resultString;
	}

	public static void main(String[] args) {
		String key = "_input_charset=GBK&notify_url=http://180.169.46.150:8280/ztravel-paygate-web/paygate/alipay/payResult&out_trade_no=20130905140438568832&partner=2088101964737404&payment_type=1&return_url=http://192.168.163.127:8090/ztravel-paygate-web/paygate/alipay/payResult?fg=1&seller_email=bycoa@10106266.com&service=create_direct_pay_by_user&subject=null&total_fee=0.01kqyvoebvumo8jcar2zzo2qifipe20k9j";
		System.out.println(MD5Encode(key, "GBK"));
		System.out.println(MD5Encode(key, "GBK"));
		System.out.println(AlipaySignMD5.sign(key, "", "GBK"));
	}
}
