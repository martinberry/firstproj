package com.ztravel.front.member.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import com.ztravel.front.member.config.MybatisGlobalConfig;
import com.ztravel.order.dao.IVoucherOrderDao;
import com.ztravel.order.dao.impl.VoucherOrderDaoImpl;


@Configuration
@Import({
	MybatisGlobalConfig.class
})
@ImportResource({
    "classpath:spring/ztravel-datasource.xml"
})
public class VoucherTestConfig {
	@Bean
	public IVoucherOrderDao voucherOrderDao(){
		IVoucherOrderDao voucherOrderDao = new VoucherOrderDaoImpl() ;
		return voucherOrderDao ;
	}
}
