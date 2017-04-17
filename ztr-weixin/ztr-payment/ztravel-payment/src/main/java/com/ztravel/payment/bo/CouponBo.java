/**
 *
 */
package com.ztravel.payment.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.constants.OperatorConstants;
import com.ztravel.common.enums.CouponItemStatus;
import com.ztravel.common.payment.CouponGrantBean;
import com.ztravel.common.payment.CouponItemBean;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.order.client.service.IOrderThriftClientService;
import com.ztravel.payment.dao.CouponAccountDao;
import com.ztravel.payment.dao.CouponItemDao;
import com.ztravel.payment.po.CouponAccount;
import com.ztravel.payment.po.CouponItem;
import com.ztravel.payment.po.factory.ModelFactory;
import com.ztravel.product.client.service.IVoucherClientService;

/**
 * @author zuoning.shen
 *
 */
@Component
public class CouponBo {
    private static Logger logger = LoggerFactory.getLogger(CouponBo.class);
    @Resource
    private CouponItemDao couponItemDao;
    @Resource
    private CouponAccountDao couponAccountDao;
    @Resource
	private IdGeneratorUtil idGeneratorUtil ;

    @Resource
    IOrderThriftClientService orderThriftClientServiceImpl;
    
    @Resource
	private IVoucherClientService voucherClientService ;

    public String grant(CouponGrantBean bean) throws Exception {
    	logger.info("grant coupon start...{}",TZBeanUtils.describe(bean));
        String memberId = bean.getMemberId();
        CouponAccount account = couponAccountDao.selectByMemberId(memberId);
        if (account == null) {
            throw new Exception("账户不存在");
        }
        CouponItem item = ModelFactory.createCouponItem(idGeneratorUtil.getCouponItemId());
        item.setMemberId(memberId);
        item.setCouponCode(bean.getCouponCode());
        item.setCouponAccountId(account.getCouponAccountId());
        item.setActivityId(bean.getActivityId());
        item.setName(bean.getName());
        item.setDescription(bean.getDescription());
        item.setAmount(bean.getAmount());
        item.setValidDateFrom(bean.getValidDateFrom());
        item.setValidDateTo(bean.getValidDateTo());
        item.setUpdated(DateTime.now());
        logger.info("insert couponItem start : {}",TZBeanUtils.describe(item));
        couponItemDao.insert(item);
        logger.info("insert couponItem end");
        // update coupon account
        logger.info("addAccount start: couponAccount is {}, amount is {} ",TZBeanUtils.describe(account),TZBeanUtils.describe(bean.getAmount()));
        addAmount(account, bean.getAmount());
        logger.info("account {} , addAccount end", account);
        logger.info("grant coupon end");
        return item.getCouponItemId() ;
    }
    
    public void invalid(String couponItemId) throws Exception {
    	logger.info("invalid coupon start...{}",TZBeanUtils.describe(couponItemId));
    	CouponItem item = couponItemDao.selectById(couponItemId);
    	if (item == null) {
            throw new Exception("代金券不存在");
        }
    	couponItemDao.updateStatus(couponItemId, CouponItemStatus.INVALID.name());
    	CouponAccount account = couponAccountDao.selectByMemberId(item.getMemberId());
        if (account == null) {
            throw new Exception("账户不存在");
        }
        logger.info("deductAmount start: couponAcount is {}, amount is {} ",TZBeanUtils.describe(account), item.getAmount());
        deductAmount(account, item.getAmount());
        logger.info("deductAmount end");
    	logger.info("invalid coupon end");
    }

    public void freeze(String couponItemId) throws Exception {
    	logger.info("freeze couponItem start: couponItemId is {} ", couponItemId);
        CouponItem item = couponItemDao.selectById(couponItemId);
        logger.info("couponItem is {} ",TZBeanUtils.describe(item));
        if (item == null) {
            throw new Exception("代金券不存在");
        }
        couponItemDao.updateStatus(couponItemId, CouponItemStatus.FROZEN.name());
        CouponAccount account = couponAccountDao.selectByMemberId(item.getMemberId());
        if (account == null) {
            throw new Exception("账户不存在");
        }
        if (!account.isActive()) {
            throw new Exception("账户已冻结");
        }
        logger.info("freezeAmount start: couponAccount is {}, amount is {} ", TZBeanUtils.describe(account), item.getAmount());
        freezeAmount(account, item.getAmount());
        logger.info("account {}, freezeAmount end", account);
        logger.info("freeze couponItem end");
    }

    public void unFreeze(String couponItemId) throws Exception {
    	logger.info("unFreeze coupon start: couponItemId is {} ", couponItemId);
        CouponItem item = couponItemDao.selectById(couponItemId);
        logger.info("couponItem is {} ", TZBeanUtils.describe(item));
        if (item == null) {
            throw new Exception("代金券不存在");
        }
        couponItemDao.updateStatus(couponItemId, CouponItemStatus.AVAILABLE.name());
        CouponAccount account = couponAccountDao.selectByMemberId(item.getMemberId());
        if (account == null) {
            throw new Exception("账户不存在");
        }
        logger.info("unFreezeAmount start: couponAcount is {}, amount is {} ",TZBeanUtils.describe(account), item.getAmount());
        unFreezeAmount(account, item.getAmount());
        logger.info("account {} , unFreezeAmount end", account);
        logger.info("unFreeze coupon end");
    }

    public void consume(String couponItemId) throws Exception {
    	logger.info("consume coupon start: couponItemId is {} ", couponItemId);
        CouponItem item = couponItemDao.selectById(couponItemId);
        logger.info("couponItem is {} ", TZBeanUtils.describe(item));
        if (item == null) {
            throw new Exception("代金券不存在");
        }
        couponItemDao.updateStatus(couponItemId, CouponItemStatus.USED.name());
        CouponAccount account = couponAccountDao.selectByMemberId(item.getMemberId());
        if (account == null) {
            throw new Exception("账户不存在");
        }
        logger.info("consumeAmount start: couponAcount is {}, amount is {} ",TZBeanUtils.describe(account), item.getAmount());
        consume(account, item.getAmount());
        logger.info("account {} , consumeAmount end", account);
        
        //大宁活动使用
        if(voucherClientService.countByCouponItemId(couponItemId) == 1){
        	voucherClientService.updateVoucher4TravelPaid(couponItemId) ;
        }
        
        logger.info("consume coupon end");
    }

    public void share(String couponItemId) throws Exception {
    	logger.info("share coupon start: couponItemId is {} ", couponItemId);
        CouponItem item = couponItemDao.selectById(couponItemId);
        logger.info("couponItem is {} ", TZBeanUtils.describe(item));
        if (item == null) {
            throw new Exception("代金券不存在");
        }
        couponItemDao.updateStatus(couponItemId, CouponItemStatus.SHARED.name());
        CouponAccount account = couponAccountDao.selectByMemberId(item.getMemberId());
        if (account == null) {
            throw new Exception("账户不存在");
        }
        if (!account.isActive()) {
            throw new Exception("账户已冻结");
        }
        logger.info("deductAmount start: couponAcount is {}, amount is {} ",TZBeanUtils.describe(account), item.getAmount());
        deductAmount(account, item.getAmount());
        logger.info("account {} deductAmount end",account);
        logger.info("share coupon end");
    }

    public void cancel(String couponItemId) throws Exception {
    	logger.info("cancel coupon start: couponItemId is {} ", couponItemId);
        CouponItem item = couponItemDao.selectById(couponItemId);
        logger.info("couponItem is {} ", TZBeanUtils.describe(item));
        if (item == null) {
            throw new Exception("代金券不存在");
        }
        couponItemDao.updateStatus(couponItemId, CouponItemStatus.AVAILABLE.name());
        CouponAccount account = couponAccountDao.selectByMemberId(item.getMemberId());
        if (account == null) {
            throw new Exception("账户不存在");
        }
        logger.info("addAmount start: couponAcount is {}, amount is {} ",TZBeanUtils.describe(account), item.getAmount());
        addAmount(account, item.getAmount());
        logger.info("account {}, addAmount end", account);
        logger.info("cancel coupon end");
        
        //大宁活动使用
        if(voucherClientService.countByCouponItemId(couponItemId) == 1){
        	voucherClientService.updateVoucher4Cancel(couponItemId) ;
        }
    }

    public void expire(String couponItemId) throws Exception {
    	logger.info("expire coupon start: couponItemId is {} ", couponItemId);
        CouponItem item = couponItemDao.selectById(couponItemId);
        logger.info("couponItem is {} ", TZBeanUtils.describe(item));
        if (item == null) {
            throw new Exception("代金券不存在");
        }
        couponItemDao.updateStatus(couponItemId, CouponItemStatus.EXPIRED.name());
        CouponAccount account = couponAccountDao.selectByMemberId(item.getMemberId());
        if (account == null) {
            throw new Exception("账户不存在");
        }
        logger.info("deductAmount start: couponAcount is {}, amount is {} ",TZBeanUtils.describe(account), item.getAmount());
        deductAmount(account, item.getAmount());
        logger.info("deductAmount end");
        logger.info("expire coupon end");
    }

    //Definition of coupon account operation: addAmount, deductAmount, freezeAmount, unFreezeAmount, consume
    private void addAmount(CouponAccount account, long amount) throws Exception {
        long originAmount = account.getAmount();
        long originAvailableAmount = account.getAvailableAmount();
        account.setAmount(originAmount + amount);
        account.setAvailableAmount(originAvailableAmount + amount);
        account.setUpdated(DateTime.now());
        account.setUpdatedby(OperatorConstants.AUTO_USER);
        checkAmount(account);
        couponAccountDao.update(account);
    }

    private void deductAmount(CouponAccount account, long amount) throws Exception {
        long originAmount = account.getAmount();
        long originAvailableAmount = account.getAvailableAmount();
        account.setAmount(originAmount - amount);
        account.setAvailableAmount(originAvailableAmount - amount);
        account.setUpdated(DateTime.now());
        account.setUpdatedby(OperatorConstants.AUTO_USER);
        checkAmount(account);
        couponAccountDao.update(account);
    }

    private void freezeAmount(CouponAccount account, long amount) throws Exception {
        long originAvailableAmount = account.getAvailableAmount();
        long originFrozenAmount = account.getFrozenAmount();
        account.setAvailableAmount(originAvailableAmount - amount);
        account.setFrozenAmount(originFrozenAmount + amount);
        account.setUpdated(DateTime.now());
        account.setUpdatedby(OperatorConstants.AUTO_USER);
        checkAmount(account);
        couponAccountDao.update(account);
    }

    private void unFreezeAmount(CouponAccount account, long amount) throws Exception {
        long originAvailableAmount = account.getAvailableAmount();
        long originFrozenAmount = account.getFrozenAmount();
        account.setAvailableAmount(originAvailableAmount + amount);
        account.setFrozenAmount(originFrozenAmount - amount);
        account.setUpdated(DateTime.now());
        account.setUpdatedby(OperatorConstants.AUTO_USER);
        checkAmount(account);
        couponAccountDao.update(account);
    }

    private void consume(CouponAccount account, long amount) throws Exception {
        long originAmount = account.getAmount();
        long originFrozenAmount = account.getFrozenAmount();
        account.setAmount(originAmount - amount);
        account.setFrozenAmount(originFrozenAmount - amount);
        account.setUpdated(DateTime.now());
        account.setUpdatedby(OperatorConstants.AUTO_USER);
        checkAmount(account);
        couponAccountDao.update(account);
    }

    private void checkAmount(CouponAccount account) throws Exception{
    	logger.info("checkAmount start ...");
    	logger.info(TZBeanUtils.describe(account));
        long amount = account.getAmount();
        long availableAmount = account.getAvailableAmount();
        long frozenAmount = account.getFrozenAmount();
        if(amount <0 || availableAmount < 0 || frozenAmount < 0){
            throw new Exception("金额计算错误");
        }
        if(amount != availableAmount + frozenAmount){
            throw new Exception("金额计算错误");
        }
        logger.info("checkAmount end");
    }

    public CouponItemBean getCouponItem(String couponItemId) throws Exception {
        CouponItem item = couponItemDao.selectById(couponItemId);
        if (item == null)
            return null;
        CouponItemBean itemBean = convertCouponItem(item);
        return itemBean;
    }

    public List<CouponItemBean> getAvailableCouponItems(String memberId) throws Exception {
        List<CouponItemBean> itemList = new ArrayList<CouponItemBean>();
        for (CouponItem item : couponItemDao.selectAvailableItems(memberId)) {
            itemList.add(convertCouponItem(item));
        }
        return itemList;
    }

    private CouponItemBean convertCouponItem(CouponItem item) {
        CouponItemBean itemBean = new CouponItemBean();
        itemBean.setCouponItemId(item.getCouponItemId());
        itemBean.setCouponCode(item.getCouponCode());
        itemBean.setName(item.getName());
        itemBean.setDescription(item.getDescription());
        itemBean.setMemberId(item.getMemberId());
        itemBean.setActivityId(item.getActivityId());
        itemBean.setAmount(item.getAmount());
        itemBean.setStatus(item.getStatus());
        itemBean.setGrantDate(item.getGrantDate());
        if(CouponItemStatus.FROZEN.name().equals(item.getStatus()) || CouponItemStatus.USED.name().equals(item.getStatus())){
            itemBean.setUseDate(item.getUseDate()== null ? item.getUpdated() : item.getUseDate());
        }
        itemBean.setValidDateFrom(item.getValidDateFrom());
        itemBean.setValidDateTo(item.getValidDateTo());
        return itemBean;
    }

    public int countCouponItems(Map params) {
        return couponItemDao.countCouponItems(params);
    }

    public List<CouponItemBean> getCouponItems(Map params) {
        List<CouponItemBean> itemList = new ArrayList<CouponItemBean>();
        for (CouponItem item : couponItemDao.selectCouponItems(params)) {
            itemList.add(convertCouponItem(item));
        }
        return itemList;
    }

    public List<String> getItemsForExpire() {
        return couponItemDao.selectItemsForExpire();
    }

	public void consume(String couponItemId, String orderId) throws Exception{
		logger.info("notify conusme coupon start... couponItemId is {}, orderId is{}", couponItemId, orderId);
    	CommonResponse commonResponse = orderThriftClientServiceImpl.weatherOrderCancled(orderId);
		logger.info(TZBeanUtils.describe(commonResponse));
		if(commonResponse.isSuccess()){
			logger.info("订单{}已取消，不消费代金券", orderId);
		}else{
			logger.info("订单{}未取消", orderId);
			consume(couponItemId);
		}
		logger.info("notify conusme coupon end");
	}

	 public List<CouponItemBean> getCouponItemsNew(Map params) {
	        List<CouponItemBean> itemList = new ArrayList<CouponItemBean>();
	        for (CouponItem item : couponItemDao.selectCouponItemsNew(params)) {
	            itemList.add(convertCouponItem(item));
	        }
	        return itemList;
	    }

}
