package com.travelzen.swordfish.thrift.constants;

public class ThriftConstants {
	
	public static final String THRIFT_INVALID_INTERFACE_ERR_CODE = "E901";
	public static final String THRIFT_INVALID_INTERFACE_ERR_MSG = "无效的接口名";

	public static final String FIT_THRIFT_UNKONW_METHOD_ERR_CODE = "E902";
	public static final String FIT_THRIFT_UNKONW_METHOD_ERR_MSG = "未知的方法";

	public static final String THRIFT_INVOKE_ERR_CODE = "E903";
	public static final String THRIFT_INVOKE_ERR_MSG = "反射调用方法异常";
	
	public static final String THRIFT_PROXY_ERR_CODE = "E904";
	public static final String THRIFT_PROXY_ERR_MSG = "thfift server 不接受cglib生成的代理对象";

}
