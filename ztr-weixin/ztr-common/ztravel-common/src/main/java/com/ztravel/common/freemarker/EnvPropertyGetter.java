package com.ztravel.common.freemarker;

import java.util.List;

import com.ztravel.common.util.WebEnv;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class EnvPropertyGetter implements TemplateMethodModelEx{
	

	@SuppressWarnings("rawtypes")
	@Override
	public Object exec(List arguments) throws TemplateModelException {

		if (arguments == null || arguments.isEmpty()) {
			return null;
		}
		Object key = arguments.get(0);
		if (key == null) {
			return null;
		}
		return WebEnv.get(key.toString(), "");
		
	}

}
