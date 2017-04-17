package com.ztravel.paygate.core.util;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.travelzen.framework.core.time.DateTimeUtil;
import com.travelzen.framework.dao.rdbms.SequenceGenerator;
import com.ztravel.paygate.core.enums.PaygateError;
import com.ztravel.paygate.core.exception.PaygateException;
/**
 * 主键生成器
 * @author dingguangxian
 *
 */
@Component
public class PrimaryKeyGenerator {
	@Resource
	private SequenceGenerator sequenceGenerator;
	/**
	 * Sequence名称定义 
	 */
	public static enum Sequence{
		PAYGATE_COMMON("paygate_common_seq"),
		ALIPAY("paygate_alipay_seq"),
		TENPAY("paygate_tenpay_seq"),
		CHINAPNR("paygate_chinapnr_seq"),
		TRADE_BATCH_BILL("paygate_trade_batch_bill_seq"),
		TRADE_DATE_BILL("paygate_trade_date_bill_seq"),
		TRADE_BILL_RECORD("paygate_trade_bill_record_seq"),
		TRANSFER_ACCOUNT("paygate_transfer_account_seq"),
		SHARE_PROFIT("paygate_share_profit_seq"),
		REFUND_FREEZE("paygate_refund_freeze_seq")
		;
		private String seqName;
		private Sequence(String seqName) {
			this.seqName = seqName;
		}
		public String getSequenceName() {
			return seqName;
		}
	}
	/**
	 * 生成主键
	 * @param sequence
	 * @return
	 */
	public String generateKey(Sequence sequence){
		try {
			return DateTimeUtil.datetime14() + sequenceGenerator.getNextSeq(sequence.getSequenceName(), 4);
		} catch (Exception e) {
			throw new PaygateException(PaygateError.E101_DB_ERROR);
		}
	}
	
	/**
	 * alipay
	 * @return
	 */
	public String generateAlipayKey(){
		return generateKey(Sequence.ALIPAY);
	}
	/**
	 * alipay_refund
	 * @return
	 */
	public String generateAlipayRefundKey(){
		return generateKey(Sequence.ALIPAY);
	}
	/**
	 * tenpay
	 * @return
	 */
	public String generateTenpayKey(){
		return generateKey(Sequence.TENPAY);
	}
	/**
	 * tenpay_refund
	 * @return
	 */
	public String generateTenpayRefundKey(){
		return generateKey(Sequence.TENPAY);
	}
	/**
	 * chinapnr
	 * @return
	 */
	public String generateChinapnrKey(){
		return generateKey(Sequence.CHINAPNR);
	}
	/**
	 * chinapnr_refund
	 * @return
	 */
	public String generateChinapnrRefundKey(){
		return generateKey(Sequence.CHINAPNR);
	}
	/**
	 * 签约表partner_sign
	 * @return
	 */
	public String generatePartnerSignKey(){
		return generateKey(Sequence.PAYGATE_COMMON);
	}
	/**
	 * paygate_share_profit
	 * @return
	 */
	public String generateShareProfitKey(){
		return generateKey(Sequence.SHARE_PROFIT);
	}
	/**
	 * paygate_refund_freeze
	 * @return
	 */
	public String generateRefundFreezeKey(){
		return generateKey(Sequence.REFUND_FREEZE);
	}
	/**
	 * paygate_transfer_account
	 * @return
	 */
	public String generateTransferAccountKey(){
		return generateKey(Sequence.TRANSFER_ACCOUNT);
	}
	
}
