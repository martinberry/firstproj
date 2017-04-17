/**
 * 
 */
package com.ztravel.common.rpc;

/**
 * @author zuoning.shen
 *
 */
public class CommonResponse {
    private boolean success;
    private String errMsg;
    
    public CommonResponse(){}
    
    public CommonResponse(boolean success){
    	this.success = success ;
    }
    
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getErrMsg() {
        return errMsg;
    }
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
