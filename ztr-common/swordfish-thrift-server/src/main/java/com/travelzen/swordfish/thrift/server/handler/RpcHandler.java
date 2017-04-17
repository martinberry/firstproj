package com.travelzen.swordfish.thrift.server.handler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.incubate.framework.thrift.ThriftException;
import com.incubate.framework.thrift.ThriftRequest;
import com.incubate.framework.thrift.ThriftResponse;
import com.incubate.framework.thrift.ThriftService.Iface;
import com.travelzen.framework.logger.core.TZMarkers;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.swordfish.thrift.client.annotation.ThriftServiceEndpoint;
import com.travelzen.swordfish.thrift.constants.ThriftConstants;
import com.travelzen.swordfish.thrift.util.JacksonUtil;
import com.travelzen.swordfish.thrift.util.ThriftReflectionUtil;
import com.travelzen.swordfish.thrift.util.ThriftUtil;




/**
 *
 * @author liuzhuo
 *
 */

@Component
public class RpcHandler implements Iface{

	public static final Logger logger = RequestIdentityLogger.getLogger(RpcHandler.class);

	@Resource
	private ApplicationContext applicationContext;

	private Map<String, Object> services = new HashMap<>();
	private Map<String, Method> methods = new HashMap<>();

	@PostConstruct
	public void initServiceMethods() throws Exception {

		Map<String, Object> serviceBeans = applicationContext.getBeansWithAnnotation(ThriftServiceEndpoint.class);
		for (Object svc : serviceBeans.values()) {

			Class<?> target = ThriftUtil.getTargetClassBySource(svc).getClass();

			services.put(target.getAnnotation(Service.class).value(), svc);
			for (Method mth : target.getDeclaredMethods()) {
				methods.put(target + "." + ThriftReflectionUtil.getSimpleMethodStr(mth), mth);
			}

		}
	}

	@Override
	public ThriftResponse request(ThriftRequest arg) throws ThriftException {

		try {
			logger.info("请求thrift 接口参数\n" +  JacksonUtil.obj2json(arg));
		} catch (Exception e) {
			logger.error(TZMarkers.p2, "请求thrift 接口打印参数\n", e);
		}

		Object itface = services.get(arg.getImplClass());


		if(null == itface) {
			logger.error(TZMarkers.p4, ThriftConstants.THRIFT_INVALID_INTERFACE_ERR_MSG + arg.getImplClass() );

			ThriftException tfe = new ThriftException();
			tfe.setErrorCode(ThriftConstants.THRIFT_INVALID_INTERFACE_ERR_CODE);
			tfe.setMessage(ThriftConstants.THRIFT_INVALID_INTERFACE_ERR_MSG);
			throw tfe;
		}

		Method method = methods.get(itface.getClass() + "." + arg.getMethod());

		if(null == method) {
			logger.error(TZMarkers.p4, ThriftConstants.FIT_THRIFT_UNKONW_METHOD_ERR_MSG + arg.getMethod() );

			ThriftException tfe = new ThriftException();
			tfe.setErrorCode(ThriftConstants.FIT_THRIFT_UNKONW_METHOD_ERR_CODE);
			tfe.setMessage(ThriftConstants.FIT_THRIFT_UNKONW_METHOD_ERR_MSG);
			throw tfe;
		}

		try {

			Object result = null;
			if(StringUtils.isEmpty(arg.getParameters())) {
				result = method.invoke(itface);
			}else {
				String jsonArrayStr = arg.getParameters() ;
				Class<?>[] clazz = method.getParameterTypes() ;
				JSONArray array = JSONArray.parseArray(jsonArrayStr) ;
				for(int i=0;i<array.size();i++){
					try{
						Map<String,Object> map = JacksonUtil.json2map(array.getString(i)) ;
						if(map.containsKey("actualClazz")){
							clazz[i] = Class.forName(map.get("actualClazz").toString()) ;
						}
					}catch(Exception e){
						logger.error(e.getMessage());
					}
				}

				List<Object> params = JacksonUtil.json2list(jsonArrayStr, clazz);

				Object[] objects = new Object[method.getParameterTypes().length];

				if(!params.isEmpty()) {
					params.toArray(objects);
				}

				result = method.invoke(itface, objects);
			}

			ThriftResponse tResponse = new ThriftResponse();


			if(null != result) {
				tResponse.setResult(JacksonUtil.obj2json(result));
			}
			logger.info("请求 thrift 接口返回结果\n" +  JacksonUtil.obj2json(tResponse));


			return tResponse;

		}catch(Throwable t) {

			logger.error(TZMarkers.p2, "请求thrift 接口异常\n", t);

			ThriftException tfe = new ThriftException();
			tfe.setErrorCode(ThriftConstants.THRIFT_INVOKE_ERR_CODE);
			tfe.setMessage(ThriftConstants.THRIFT_INVOKE_ERR_MSG);
			throw tfe;
		}
	}

}
