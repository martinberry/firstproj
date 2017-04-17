package com.ztravel.datasource.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * media数据源剥离
 * @author liuzhuo
 *
 */
@Configuration
@ImportResource({
	"classpath:spring/ztravel-media-datastore.xml"
})
public class MediaDatastoreConfig {

}
