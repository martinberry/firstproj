/**
 * 
 */
package com.ztravel.payment.service;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.travelzen.swordfish.thrift.client.annotation.ThriftServiceEndpoint;
import com.ztravel.payment.processor.OrderRefundProcessor;
import com.ztravel.payment.service.IOrderRefundService;

/**
 * @author zuoning.shen
 *
 */
@Service("OrderRefundService")
@ThriftServiceEndpoint
public class OrderRefundService implements IOrderRefundService {
    private static Logger logger = LoggerFactory.getLogger(OrderRefundService.class);
    
    @Resource
    private OrderRefundProcessor refundProcessor;

}
