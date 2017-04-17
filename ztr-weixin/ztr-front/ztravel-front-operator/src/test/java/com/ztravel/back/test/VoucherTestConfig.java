package com.ztravel.back.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import com.ztravel.front.operator.config.MybatisGlobalConfig;
import com.ztravel.product.dao.IVoucherDao;
import com.ztravel.product.dao.impl.VoucherDaoImpl;


@Configuration
@Import({
	MybatisGlobalConfig.class
})
@ImportResource({
    "classpath:spring/ztravel-datasource.xml"
})
public class VoucherTestConfig {
	@Bean
	public IVoucherDao voucherDao(){
		IVoucherDao voucherDao = new VoucherDaoImpl() ;
		return voucherDao ;
	}
}
