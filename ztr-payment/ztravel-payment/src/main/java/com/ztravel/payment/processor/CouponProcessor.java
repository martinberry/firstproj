/**
 *
 */
package com.ztravel.payment.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.travelzen.framework.core.wrapper.Pagination;
import com.ztravel.common.enums.AccountType;
import com.ztravel.common.enums.CouponItemStatus;
import com.ztravel.common.enums.InoutDetailType;
import com.ztravel.common.enums.InoutType;
import com.ztravel.common.enums.ProductType;
import com.ztravel.common.payment.CouponCountBean;
import com.ztravel.common.payment.CouponGrantBean;
import com.ztravel.common.payment.CouponItemBean;
import com.ztravel.common.payment.CouponItemQueryBean;
import com.ztravel.common.payment.CouponSumBean;
import com.ztravel.common.payment.DNCouponGrantBean;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.payment.bo.CouponBo;
import com.ztravel.payment.dao.AccountDao;
import com.ztravel.payment.dao.AccountHistoryDao;
import com.ztravel.payment.dao.CouponItemDao;
import com.ztravel.payment.po.Account;
import com.ztravel.payment.po.AccountHistory;
import com.ztravel.payment.po.CouponItem;
import com.ztravel.payment.po.factory.ModelFactory;
import com.ztravel.product.client.service.IVoucherClientService;

/**
 * @author zuoning.shen
 *
 */
@Component
public class CouponProcessor {
	  private static Logger logger = LoggerFactory.getLogger(CouponProcessor.class);

    @Resource
    private CouponBo couponBo;
    @Resource
    private CouponItemDao couponItemDao;
    @Resource
    private AccountDao accountDao;
    @Resource
    private AccountHistoryDao accountHistoryDao;
    @Resource
   	private IdGeneratorUtil idGeneratorUtil ;
    @Resource
	private IVoucherClientService voucherClientService ;

    @Profiled
    @Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public CommonResponse grant(CouponGrantBean bean) throws Exception{
        CommonResponse res = new CommonResponse();
        //Lock account
        Account account = accountDao.selectForUpdate(bean.getMemberId());
        if (account == null) {
            throw new Exception("账户不存在");
        }
        String couponItemId = couponBo.grant(bean);

        AccountHistory accountHistory = ModelFactory.createAccountHistory(idGeneratorUtil.getAccountHistoryId());
        accountHistory.setMemberId(bean.getMemberId());
        accountHistory.setAccountType(AccountType.COUPON.name());
        accountHistory.setAmount(bean.getAmount());
        accountHistory.setInoutType(InoutType.INCOME.name());
        accountHistory.setInoutDetailType(InoutDetailType.GRANTED_COUPON.name());
        accountHistoryDao.insert(accountHistory);

        //大宁活动使用
        if(bean instanceof DNCouponGrantBean){
            DNCouponGrantBean dNBean = ((DNCouponGrantBean) bean) ;
        	voucherClientService.updateVoucher4Grant(couponItemId, dNBean) ;
        }

        res.setSuccess(true);
        return res;
    }
    
    @Profiled
    @Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public CommonResponse buy(CouponGrantBean bean, String orderId) throws Exception{
        CommonResponse res = new CommonResponse();
        //Lock account
        Account account = accountDao.selectForUpdate(bean.getMemberId());
        if (account == null) {
            throw new Exception("账户不存在");
        }
        String couponItemId = couponBo.grant(bean);

        AccountHistory accountHistory = ModelFactory.createAccountHistory(idGeneratorUtil.getAccountHistoryId());
        accountHistory.setMemberId(bean.getMemberId());
        accountHistory.setOrderId(orderId);
        accountHistory.setProductType(ProductType.VOUCHER.toString());
        accountHistory.setAccountType(AccountType.COUPON.name());
        accountHistory.setAmount(bean.getAmount());
        accountHistory.setInoutType(InoutType.INCOME.name());
        accountHistory.setInoutDetailType(InoutDetailType.GRANTED_COUPON.name());
        accountHistoryDao.insert(accountHistory);

        if(bean instanceof DNCouponGrantBean){
            DNCouponGrantBean dNBean = ((DNCouponGrantBean) bean) ;
        	voucherClientService.updateVoucher4Grant(couponItemId, dNBean) ;
        }

        res.setSuccess(true);
        return res;
    }
    
    @Profiled
    @Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public CommonResponse invalid(String couponItemId) throws Exception{
        CommonResponse res = new CommonResponse();
        couponBo.invalid(couponItemId) ;
        res.setSuccess(true);
        return res;
    }

    @Profiled
    @Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public CommonResponse transfer(String couponItemId, String destMemberId) throws Exception {
        if (StringUtils.isBlank(couponItemId) || StringUtils.isBlank(destMemberId)) {
            throw new Exception("代金券分享失败，参数错误");
        }

        CouponItem item = couponItemDao.selectById(couponItemId);
        if (item == null) {
            throw new Exception("代金券不存在");
        }
        if (!CouponItemStatus.AVAILABLE.name().equals(item.getStatus())) {
            throw new Exception("代金券已使用");
        }
        // Lock account
        Account account = accountDao.selectForUpdate(item.getMemberId());
        if (account == null) {
            throw new Exception("后台数据错误，源账户不存在");
        }
        couponBo.share(couponItemId);

        AccountHistory sharedAccountHistory = ModelFactory.createAccountHistory(idGeneratorUtil.getAccountHistoryId());
        sharedAccountHistory.setMemberId(item.getMemberId());
        sharedAccountHistory.setAccountType(AccountType.COUPON.name());
        sharedAccountHistory.setAmount(item.getAmount());
        sharedAccountHistory.setInoutType(InoutType.OUTGO.name());
        sharedAccountHistory.setInoutDetailType(InoutDetailType.SHARED_COUPON.name());
        accountHistoryDao.insert(sharedAccountHistory);

        // Lock dest account
        Account destAccount = accountDao.selectForUpdate(destMemberId);
        if (destAccount == null) {
            throw new Exception("后台数据错误，目标账户不存在");
        }
        CouponGrantBean bean = new CouponGrantBean();
        bean.setMemberId(destMemberId);
        bean.setActivityId(item.getActivityId());
        bean.setAmount(item.getAmount());
        bean.setCouponCode(item.getCouponCode());
        bean.setName(item.getName());
        bean.setDescription(item.getDescription());
        bean.setValidDateFrom(item.getValidDateFrom());
        bean.setValidDateTo(item.getValidDateTo());
        String newCouponItemId = couponBo.grant(bean);

        AccountHistory grantedAccountHistory = ModelFactory.createAccountHistory(idGeneratorUtil.getAccountHistoryId());
        grantedAccountHistory.setMemberId(destMemberId);
        grantedAccountHistory.setAccountType(AccountType.COUPON.name());
        grantedAccountHistory.setAmount(bean.getAmount());
        grantedAccountHistory.setInoutType(InoutType.INCOME.name());
        grantedAccountHistory.setInoutDetailType(InoutDetailType.GRANTED_COUPON.name());
        accountHistoryDao.insert(grantedAccountHistory);

        //大宁活动使用
        if(voucherClientService.countByCouponItemId(couponItemId) == 1){
        	voucherClientService.updateVoucher4Share(newCouponItemId, couponItemId) ;
        }

        CommonResponse res = new CommonResponse();
        res.setSuccess(true);
        return res;
    }

    @Profiled
    @Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public CommonResponse freeze(String couponItemId) throws Exception{
        if (StringUtils.isBlank(couponItemId)) {
            throw new Exception("代金券冻结失败，参数错误");
        }

        CouponItem item = couponItemDao.selectById(couponItemId);
        if (item == null) {
            throw new Exception("代金券不存在");
        }
        if (!CouponItemStatus.AVAILABLE.name().equals(item.getStatus())) {
            throw new Exception("代金券已使用");
        }
        // Lock account
        Account account = accountDao.selectForUpdate(item.getMemberId());
        if (account == null) {
            throw new Exception("后台数据错误，账户不存在");
        }
        couponBo.freeze(couponItemId);
        CommonResponse res = new CommonResponse();
        res.setSuccess(true);
        return res;
    }

    @Profiled
    @Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public CommonResponse unfreeze(String couponItemId) throws Exception{
        if (StringUtils.isBlank(couponItemId)) {
            throw new Exception("代金券解冻失败，参数错误");
        }

        CouponItem item = couponItemDao.selectById(couponItemId);
        if (item == null) {
            throw new Exception("代金券不存在");
        }
        if (!CouponItemStatus.FROZEN.name().equals(item.getStatus())) {
            throw new Exception("代金券状态错误");
        }
        // Lock account
        Account account = accountDao.selectForUpdate(item.getMemberId());
        if (account == null) {
            throw new Exception("后台数据错误，账户不存在");
        }
        couponBo.unFreeze(couponItemId);
        CommonResponse res = new CommonResponse();
        res.setSuccess(true);
        return res;
    }

    public CouponItemBean getCouponItem(String couponItemId) throws Exception{
        return couponBo.getCouponItem(couponItemId);
    }

    public List<CouponItemBean> getAvailableCouponItems(String memberId) throws Exception{
        return couponBo.getAvailableCouponItems(memberId);
    }

    public Pagination<CouponItemBean> getCouponItems(CouponItemQueryBean bean) throws Exception {
        Pagination<CouponItemBean> pagination = new Pagination<CouponItemBean>();
        Map params = new HashMap();
        if (bean.getConditionUseDateFrom() != null) {
            params.put("conditionUseDateFrom", bean.getConditionUseDateFrom());
        }
        if (bean.getConditionUseDateTo() != null) {
            params.put("conditionUseDateTo", bean.getConditionUseDateTo());
        }
        if (bean.getCouponCode() != null) {
            params.put("couponCode", bean.getCouponCode());
        }
        if (bean.getMemberId() != null) {
            params.put("memberId", bean.getMemberId());
        }
        if (bean.getStatusList() != null) {
            params.put("statusList", bean.getStatusList());
        }
        if (bean.getPageNo() != -1 && bean.getPageSize() != -1) {
            params.put("offset", bean.getPageSize() * (bean.getPageNo() - 1));
            params.put("limit", bean.getPageSize());
            int totalCount = couponBo.countCouponItems(params);
            pagination.setTotalItemCount(totalCount);
        }
        pagination.setData(couponBo.getCouponItems(params));
        return pagination;
    }

    public Pagination<CouponItemBean> getCouponItemsNew(CouponItemQueryBean bean) throws Exception {
        Pagination<CouponItemBean> pagination = new Pagination<CouponItemBean>();
        Map<String, Object> params = Maps.newHashMap();
        if (bean.getConditionUseDateFrom() != null) {
            params.put("conditionUseDateFrom", bean.getConditionUseDateFrom());
        }
        if (bean.getConditionUseDateTo() != null) {
            params.put("conditionUseDateTo", bean.getConditionUseDateTo());
        }
        if (bean.getCouponCode() != null) {
            params.put("couponCode", bean.getCouponCode());
        }
        if (bean.getMemberId() != null) {
            params.put("memberId", bean.getMemberId());
        }
        if (bean.getStatusList() != null) {
            params.put("statusList", bean.getStatusList());
        }
        if (bean.getPageNo() != -1 && bean.getPageSize() != -1) {
            params.put("offset", bean.getPageSize() * (bean.getPageNo() - 1));
            params.put("limit", bean.getPageSize());
            int totalCount = couponBo.countCouponItems(params);
            pagination.setTotalItemCount(totalCount);
        }
        pagination.setData(couponBo.getCouponItemsNew(params));
        return pagination;
    }
    
    public int getCouponItemNumByMap(Map<String, Object> params) throws Exception {
    	return couponItemDao.countCouponItems(params) ;
    }

    public CouponSumBean sumAmount(String couponCode) throws Exception{
        CouponSumBean bean = new CouponSumBean();
        bean.setCouponCode(couponCode);
        bean.setGrantedAmount(couponItemDao.sumGrantedAmount(couponCode));
        bean.setUsedAmount(couponItemDao.sumUsedAmount(couponCode));
        return bean;
    }

    public CouponCountBean countByCode(String couponCode) throws Exception{
        CouponCountBean bean = new CouponCountBean();
        bean.setCouponCode(couponCode);
        bean.setGrantedCount(couponItemDao.countGranted(couponCode));
        bean.setUsedCount(couponItemDao.countUsed(couponCode));
        return bean;
    }

	public int countByStatusListAndMemberId(List<CouponItemStatus> statusList, String memberId) throws Exception{
		logger.info("countByStatusAndMemberId start.., CouponItemStatus {}, memberId {}", statusList.toString(), memberId);
		Map params = Maps.newHashMap();
		params.put("statusList", statusList);
		params.put("memberId", memberId);
		return couponItemDao.countCouponItems(params);
	}
}
