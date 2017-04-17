package com.ztravel.common.util;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.travelzen.framework.core.time.DateTimeUtil;
import com.travelzen.framework.dao.rdbms.SequenceGenerator;


public class IdGeneratorUtil {

	private final static String ORDER_PAY_PREFIX = "ordp";
	private final static String ACCOUNT_CONVERT_PREFIX = "DH";
    private final static String PAYMENT_PREFIX = "pmnt";
    private final static String ACCOUNT_PREFIX = "acct";
    private final static String COUPON_ACCOUNT_PREFIX = "cact";
    private final static String COUPON_ITEM_PREFIX = "citm";
    private final static String TRADE_PREFIX = "trad";
    private final static String ACCOUNT_HISTORY_PREFIX = "acth";
    private final static String MEMBER_PREFIX = "MEMB";
    private final static String ROLE_PREFIX = "ROLE_";
    private final static String USER_PREFIX = "USER_";
    private final static String GAME_RECORD_PREFIX = "GAME_RECORD_";
    private final static String MATERIAL_MEDIA_PREFIX = "MATERIAL_MEDIA_";
    private final static String MATERIAL_NEWS_ITEM_PREFIX = "MATERIAL_NEWS_ITEM_";
    private final static String T_WX_USER_PREFIX = "T_WX_USER_";

	private SequenceGenerator sequenceGenerator ;

	public void setSequenceGenerator(SequenceGenerator sequenceGenerator){
		this.sequenceGenerator = sequenceGenerator ;
	}

	public SequenceGenerator getSequenceGenerator(SequenceGenerator sequenceGenerator){
		return this.sequenceGenerator ;
	}

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public String getAccountConvertId() throws Exception {
		return ACCOUNT_CONVERT_PREFIX + DateTimeUtil.datetime12()+sequenceGenerator.getNextSeq("account_convert", 4);
	}

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public String getOrderPayId() throws Exception {
		return ORDER_PAY_PREFIX + sequenceGenerator.getNextSeq("t_order_pay", 20);
	}

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public String getPaymentId() throws Exception {
        return PAYMENT_PREFIX + sequenceGenerator.getNextSeq("t_payment", 24);
    }

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public String getAccountId() throws Exception {
        return ACCOUNT_PREFIX + sequenceGenerator.getNextSeq("t_account", 24);
    }

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public String getCouponAccountId() throws Exception {
        return COUPON_ACCOUNT_PREFIX + sequenceGenerator.getNextSeq("t_coupon_account", 24);
    }

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public String getCouponItemId() throws Exception {
        return COUPON_ITEM_PREFIX + sequenceGenerator.getNextSeq("t_coupon_item", 24);
    }

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public String getTradeId() throws Exception {
        return TRADE_PREFIX + sequenceGenerator.getNextSeq("t_trade", 24);
    }

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public String getAccountHistoryId() throws Exception {
        return ACCOUNT_HISTORY_PREFIX + sequenceGenerator.getNextSeq("t_account_history", 24);
    }

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public String getMemberId() throws Exception {
		return MEMBER_PREFIX + DateTimeUtil.datetime12() + sequenceGenerator.getNextSeq("ztravel_sequence", 4);
	}

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public String getMId() throws Exception {
		return sequenceGenerator.getNextSeq("ztravel_sequence", 8);
	}

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public String getOrderId() throws Exception {
		return DateTimeUtil.datetime12() + sequenceGenerator.getNextSeq("ztr_order", 4);
	}

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public String getOrderNo() throws Exception {
		return DateTimeUtil.date8()+ sequenceGenerator.getNextSeq("ztr_order", 6) ;
	}

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public String getCouponId() throws Exception {
		return DateTimeUtil.datetime12() + sequenceGenerator.getNextSeq("ztr_coupon", 4);
	}
	
	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public String getWeixinTopicId() throws Exception {
		return DateTimeUtil.datetime12() + sequenceGenerator.getNextSeq("weixin_topic_id", 4);
	}
	
	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public String getPraiseId() throws Exception {
		return DateTimeUtil.datetime12() + sequenceGenerator.getNextSeq("weixin_praise_id", 4);
	}
	

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public String getCouponCode() throws Exception {
		return sequenceGenerator.getNextSeq("ztr_coupon_code", 8);
	}

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public String getRoleId() throws Exception {
		return ROLE_PREFIX + sequenceGenerator.getNextSeq("ztravel_sequence_role", 4);
	}

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public String getUserId() throws Exception {
		return USER_PREFIX + sequenceGenerator.getNextSeq("ztravel_sequence_user", 4);
	}

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public String getGameRecordId() throws Exception {
		return GAME_RECORD_PREFIX + sequenceGenerator.getNextSeq("ztravel_sequence_game_record", 8);
	}

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public String getMaterialMediaId() throws Exception {
        return MATERIAL_MEDIA_PREFIX + sequenceGenerator.getNextSeq("ztravel_sequence_material_media", 8);
    }

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public String getMaterialNewsItemId() throws Exception {
        return MATERIAL_NEWS_ITEM_PREFIX + sequenceGenerator.getNextSeq("sequence_material_news_item", 8);
    }

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public String getWxUserId() throws Exception {
        return T_WX_USER_PREFIX + sequenceGenerator.getNextSeq("sequence_t_wx_user", 8);
    }

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public String getTeamNum(String cityAb) throws Exception {
		return "zlx" + cityAb + DateTimeUtil.datetime8() + sequenceGenerator.getNextSeq("ztr_team_num", 3);
	}

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public String getMenuButtonId() throws Exception {
		return  DateTimeUtil.datetime12() + sequenceGenerator.getNextSeq("ztr_menu_button", 4);
	}

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public String getSenceId() throws Exception {
		return sequenceGenerator.getNextSeq("ztravel_wx_sence_id", 9);
	}

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public String getVoucherOrderId() throws Exception {
		return "DJ" + DateTimeUtil.datetime12() + sequenceGenerator.getNextSeq("ztravel_voucher_order", 6);
	}

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public String getVoucherId() throws Exception {
		return DateTimeUtil.datetime12() + sequenceGenerator.getNextSeq("ztravel_voucher", 12);
	}

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public String getVoucherCombineOrderId() throws Exception {
		return "DJCB" + DateTimeUtil.datetime12() + sequenceGenerator.getNextSeq("ztravel_voucher_order", 6);
	}

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public String getCommonOrderId(String orderId) throws Exception {
        return orderId + "-" + sequenceGenerator.getNextSeq("ztravel_common_order", 4);
    }

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
	public String getPkgId() throws Exception {
		return "PKG" + DateTimeUtil.datetime12() + sequenceGenerator.getNextSeq("ztravel_product_pkg", 6);
	}

	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public String getUserAwardRecordId() throws Exception {
        return "RD" + DateTimeUtil.datetime12() + sequenceGenerator.getNextSeq("ztravel_user_award_record", 6);
    }
	
	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public String getTopicCommentId() throws Exception {
		return DateTimeUtil.datetime12() + sequenceGenerator.getNextSeq("weixin_topic_id", 4);
    }

}
