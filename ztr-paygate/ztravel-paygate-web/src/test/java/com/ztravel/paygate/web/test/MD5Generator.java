package com.ztravel.paygate.web.test;

import java.util.UUID;

public class MD5Generator {
	public static void main(String[] args) {
		System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
	}
}
