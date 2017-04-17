/**
 * 
 */
package com.ztravel.payment.processor;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zuoning.shen
 *
 */
@Component
@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
public class OrderRefundProcessor {

}
