package com.ztravel.timming.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * 产品定时器 配置,定时器server只需要依赖此配置即可
 * @author liuzhuo
 *
 */

@Configuration
@ImportResource({
	"classpath*:spring/ztravel-scheduling-ctx-timer.xml"
})
public class SpringTimmingGlobalConfig {

}
