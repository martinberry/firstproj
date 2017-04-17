package com.ztravel.product.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan({
	"com.ztravel.product.back.freetravel.controller",
	"com.ztravel.product.back.freetravel.service",
	"com.ztravel.product.back.freetravel.dao",
	"com.ztravel.product.back.common",
	"com.ztravel.product.back.hotel.controller",
	"com.ztravel.product.back.hotel.service",
	"com.ztravel.product.back.hotel.dao",
	"com.ztravel.product.back.activity.controller",
	"com.ztravel.product.back.activity.service",
	"com.ztravel.product.back.activity.dao",
	"com.ztravel.product.back.activity.validate",
    "com.ztravel.product.back.pieces.controller",
    "com.ztravel.product.back.pieces.service",
    "com.ztravel.product.dao",
	"com.ztravel.product.common"})
public class ProductBackModuleConfig {

}
