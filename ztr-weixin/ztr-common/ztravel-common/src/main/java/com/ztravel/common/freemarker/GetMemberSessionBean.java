package com.ztravel.common.freemarker;

import java.util.List;

import com.ztravel.common.util.SSOUtil;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class GetMemberSessionBean implements TemplateMethodModelEx {

	@SuppressWarnings("rawtypes")
	@Override
	public Object exec(List arguments) throws TemplateModelException {
		return SSOUtil.getMemberSessionBean() ;
	}

}
