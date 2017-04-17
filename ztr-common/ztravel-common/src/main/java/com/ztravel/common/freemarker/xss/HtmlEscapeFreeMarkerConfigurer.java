package com.ztravel.common.freemarker.xss;

import java.util.List;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;


import freemarker.cache.TemplateLoader;

public class HtmlEscapeFreeMarkerConfigurer extends FreeMarkerConfigurer{

	@Override
	protected TemplateLoader getAggregateTemplateLoader(List<TemplateLoader> templateLoaders) {
		return new HtmlEscapeTemplateLoader(super.getAggregateTemplateLoader(templateLoaders));
	}


}
