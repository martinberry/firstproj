package com.ztravel.paygate.web.processor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ztravel.paygate.core.enums.GateType;

/**
 * 
 * @author dingguangxian
 * 
 */
@Component("paygate_request_processor_factory")
public class RequestProcessorFactory {

	private List<RequestProcessor> processors = new ArrayList<RequestProcessor>();

	public RequestProcessor getProcessor(GateType gateType) {
		if (processors != null) {
			for (RequestProcessor processor : processors) {
				if (processor.supportsGateType() == gateType) {
					return processor;
				}
			}
		}
		return null;
	}
	
	public void registProcessor(RequestProcessor requestProcessor){
		if(processors == null){
			processors = new ArrayList<>();
		}
		processors.add(requestProcessor);
	}

	public List<RequestProcessor> getProcessors() {
		return processors;
	}

	public void setProcessors(List<RequestProcessor> processors) {
		this.processors = processors;
	}

}
