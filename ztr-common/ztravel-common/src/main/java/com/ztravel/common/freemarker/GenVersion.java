package com.ztravel.common.freemarker;

import java.io.InputStream;
import java.util.List;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.travelzen.framework.config.tops.util.TopsAppRegistry;
import com.travelzen.framework.spring.web.context.SpringApplicationContext;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

@Component
public class GenVersion implements TemplateMethodModelEx {
	private static final Logger LOGGER = LoggerFactory.getLogger(GenVersion.class);
    private static final String salt = "zhaopengfei";
    private String timestamp = null;


    @Override
    @SuppressWarnings("rawtypes")
    public Object exec(List arguments) throws TemplateModelException {
        if(timestamp == null) {
        	getTimestamp();
        }
        return Hashing.murmur3_32().hashString(salt + timestamp, Charsets.UTF_8).toString();
    }


    private void getTimestamp() {
        try {
        	ServletContext servletContext = (ServletContext) SpringApplicationContext.getApplicationContext().getBean("servletContext") ;
            InputStream inputStream = servletContext.getResourceAsStream("/META-INF/MANIFEST.MF");
            Manifest manifest = new Manifest(inputStream);
            if (StringUtils.equalsIgnoreCase(TopsAppRegistry
                            .getApplicationName(), StringUtils.trimToEmpty(manifest
                            .getMainAttributes().getValue("Implementation-Title")))) {
                timestamp = manifest.getMainAttributes().getValue("Implementation-Timestamp");
                LOGGER.info("find Implementation-Timestamp:{}", timestamp);
                return;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        timestamp = StringUtils.EMPTY;
        LOGGER.info("not found META-INF/MANIFEST.MF");
    }

}
