package com.ztravel.product.back.piece.visa;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan({
	"com.ztravel.product.back.pieces.controller",
	"com.ztravel.product.back.pieces.service",
	"com.ztravel.order.dao",
	"com.ztravel.member.dao",
	"com.ztravel.sso.dao",
	"com.ztravel.product.dao"})
@Import({
	MybatisTestGlobalConfig.class,
//	DataSourceConfig.class
})

/***
 * 测试时，需手动通过buildpath去添加对data-source模块的依赖,并将上面注释去掉
 * */
public class PieceProductBackTestConfig {
	
}
