package com.ztravel.product.utils;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.product.po.pieces.common.PriceInfo;

public class PieceProductUtils {
	
	private static final Logger LOGGER = RequestIdentityLogger.getLogger(PieceProductUtils.class);
	
	public static Double getLowestPrice(String productId,List<PriceInfo> priceInfos) throws Exception{
		if(CollectionUtils.isNotEmpty(priceInfos)){
			Double[] prices = new Double[priceInfos.size()];
			for(int i = 0 ; i < priceInfos.size() ; i++){
				prices[i] = priceInfos.get(i).getAdultPrice() == null ? Double.MAX_VALUE : priceInfos.get(i).getAdultPrice();
			}
			sort(prices);
			return prices[0];
		}else{
			LOGGER.error("产品[{}]未设置销售价格信息",priceInfos);
			throw new RuntimeException("产品["+productId+"]未设置销售价格信息");
		}
	}
	
	private static void sort(Double[] data) {
		for (int i = 0; i < data.length; i++) {
			Double temp = data[i];
			int left = 0;
			int right = i - 1;
			int mid = 0;
			while (left <= right) {
				mid = (left + right) / 2;
				if (temp < data[mid]) {
					right = mid - 1;
				} else {
					left = mid + 1;
				}
			}
			for (int j = i - 1; j >= left; j--) {
				data[j + 1] = data[j];
			}
			if (left != i) {
				data[left] = temp;
			}
		}
	}
}
