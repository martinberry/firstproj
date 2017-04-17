package com.ztravel.common.util;

import java.util.UUID;

import com.ztravel.common.constants.ProductBookConstans;

public class ProductUtil {
	
	public static String genProductSnapshotId() {
		StringBuffer sb = new StringBuffer();
		sb.append(ProductBookConstans.PRODUCT_SNAPSHOT_KEY);
		sb.append("_");
		sb.append(UUID.randomUUID().toString());
		return sb.toString();
		
	}

}
