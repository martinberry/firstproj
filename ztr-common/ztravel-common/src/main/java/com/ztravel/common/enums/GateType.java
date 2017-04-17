/**
 * 
 */
package com.ztravel.common.enums;

/**
 * @author zuoning.shen
 *
 */
public enum GateType {
    Alipay("0001"), WeChatpay("0002");
    
    private String gateCode;
    private GateType(String gateCode){
        this.gateCode = gateCode;
    }
    public String getGateCode() {
        return gateCode;
    }
    
    public static GateType fromPayType(PaymentType paymentType){
        return GateType.valueOf(paymentType.name());
    }
    
    public static GateType fromGateCode(String gateCode){
        for(GateType gateType: GateType.values()){
            if(gateType.gateCode.equals(gateCode)) {
                return gateType;
            }
        }
        return null;
    }
    
    public static void main(String[] args){
        System.out.println(fromGateCode("0001").name());
    }
}
