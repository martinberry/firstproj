/**
 * 
 */
package com.ztravel.common.payment;

import com.ztravel.common.rpc.CommonResponse;

/**
 * @author zuoning.shen
 *
 */
public class PaymentResponse extends CommonResponse{
    private String paymentUrl;
    public String getPaymentUrl() {
        return paymentUrl;
    }
    public void setPaymentUrl(String paymentUrl) {
        this.paymentUrl = paymentUrl;
    }
}
