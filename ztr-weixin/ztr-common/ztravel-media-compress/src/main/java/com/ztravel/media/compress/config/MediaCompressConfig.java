package com.ztravel.media.compress.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ztravel.datasource.config.MediaDatastoreConfig;
import com.ztravel.mq.config.ZtrMqConfig;




@Configuration
@Import({
	MediaDatastoreConfig.class,
	ZtrMqConfig.class,
	MediaMqContainerConfig.class
})
@ComponentScan({
	"com.ztravel.media.compress.handler",
	"com.ztravel.media.compress.listener",
	"com.ztravel.mediaserver.dao.impl"
})
public class MediaCompressConfig {

}
