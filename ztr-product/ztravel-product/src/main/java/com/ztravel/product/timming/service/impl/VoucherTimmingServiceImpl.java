package com.ztravel.product.timming.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.enums.VoucherStatus;
import com.ztravel.product.dao.IVoucherDao;
import com.ztravel.product.timming.service.IVoucherTimmingService;

@Service
public class VoucherTimmingServiceImpl implements IVoucherTimmingService{

	@Resource
	private IVoucherDao voucherDao ;

	private static final Logger LOGGER  = RequestIdentityLogger.getLogger(VoucherTimmingServiceImpl.class);

	@Override
	public boolean setVoucherExpired(String couponId) {
		Map<String, Object> params = Maps.newHashMap() ;
		params.put("couponId", couponId) ;
		List<VoucherStatus> vss = Lists.newArrayList() ;
		vss.add(VoucherStatus.INIT) ;
		vss.add(VoucherStatus.LOCK) ;
		vss.add(VoucherStatus.RECIEVED) ;
		params.put("statusList", vss) ;
		params.put("status", VoucherStatus.EXPIRED) ;
		int count = voucherDao.updateByMap(params) ;
		if(count != 1){
			RuntimeException ex
				= new RuntimeException("setVoucherExpired params:::{" + params + "} effect records:::{" + count + "}") ;
			LOGGER.error(ex.getMessage());
			throw ex ;
		}
		return true;
	}

	

}
