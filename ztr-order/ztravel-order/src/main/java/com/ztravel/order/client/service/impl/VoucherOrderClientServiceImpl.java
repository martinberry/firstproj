package com.ztravel.order.client.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.travelzen.framework.logger.core.TZMarkers;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.CouponBookConstants;
import com.ztravel.common.entity.CouponBookVo;
import com.ztravel.common.entity.CouponSnapshot;
import com.ztravel.common.entity.VoucherCombineOrderInfo;
import com.ztravel.common.enums.ProductType;
import com.ztravel.common.enums.VoucherOrderStatus;
import com.ztravel.common.order.OrderPaidBean;
import com.ztravel.common.payment.CouponItemBean;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.common.util.OpenIdUtil;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.order.client.service.IVoucherOrderClientService;
import com.ztravel.order.dao.IOrderOpenIdDao;
import com.ztravel.order.dao.IVoucherOrderDao;
import com.ztravel.order.po.OrderOpenId;
import com.ztravel.order.po.VoucherCombineOrder;
import com.ztravel.order.po.VoucherOrder;
import com.ztravel.payment.service.ICouponService;
import com.ztravel.product.client.entity.CouponClientEntity;
import com.ztravel.product.client.entity.Voucher;
import com.ztravel.product.client.service.IActivityClientService;
import com.ztravel.product.client.service.IVoucherClientService;
import com.ztravel.reuse.order.entity.OrderPayVo;
import com.ztravel.reuse.order.service.IOrderLogReuseService;

@Service
public class VoucherOrderClientServiceImpl implements IVoucherOrderClientService{

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(VoucherOrderClientServiceImpl.class);
    private static final RedisClient redisClient = RedisClient.getInstance();

    @Resource
    private IdGeneratorUtil idGeneratorUtil ;

	@Resource
	private IVoucherOrderDao voucherOrderDao ;

	@Resource
	private IVoucherClientService voucherClientService ;

	@Resource
	private ICouponService couponService ;

	@Resource
	private IOrderLogReuseService orderLogReuseService;

    @Resource
    IOrderOpenIdDao orderOpenIdDaoImpl;

    @Resource
    IActivityClientService activityClientService;

	@Override
	public boolean cancelVoucherOrder(VoucherOrder voucherOrder) {
		Map<String, Object> params = Maps.newHashMap() ;
		params.put("voucherOrderId", voucherOrder.getVoucherOrderId()) ;
		params.put("status", VoucherOrderStatus.CANCELED) ;
		params.put("oldStatus", VoucherOrderStatus.UNPAY) ;

		int count = voucherOrderDao.updateByMap(params) ;
		if(count != 1){
			RuntimeException ex
				= new RuntimeException("cancelVoucherOrder params:::{" + params + "} effect records:::{" + count + "}") ;
			LOGGER.error(ex.getMessage());
			throw ex ;
		}

		voucherClientService.updateVoucher4VoucherOrderExpired(voucherOrder.getVoucherCode()) ;

		return true ;
	}

	@Override
	public long getCouponAmountByVoucherOrderId(String voucherOrderId) throws Exception {
		LOGGER.debug("getCouponAmountByVoucherOrderId start ::: {}", voucherOrderId);
		VoucherOrder voucherOrder = this.selectById(voucherOrderId) ;
		Voucher voucher = voucherClientService.getVoucherByCode(voucherOrder.getVoucherCode()) ;
		CouponItemBean couponItemBean = couponService.getCouponItem(voucher.getCouponItemId()) ;
		LOGGER.debug("getCouponAmountByVoucherOrderId end, response ::: {}", couponItemBean.getAmount());
		return couponItemBean.getAmount() ;
	}

	@Override
	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED, rollbackFor = Throwable.class)
	public boolean cancelVoucherCombineOrder(String voucherCombineOrderId) throws Exception {
		Map<String, Object> params = Maps.newHashMap() ;
		params.put("oldCombineOrderId", voucherCombineOrderId) ;
		params.put("status", VoucherOrderStatus.CANCELED) ;
		params.put("oldStatus", VoucherOrderStatus.UNPAY) ;
		int count = voucherOrderDao.updateCombineOrderByMap(params) ;
		if(count != 1){
			RuntimeException ex
				= new RuntimeException("cancelVoucherCombineOrder params:::{" + params + "} effect records:::{" + count + "}") ;
			LOGGER.error(ex.getMessage());
			throw ex ;
		}

		List<VoucherOrder> voucherOrders = selectByCombineOrderId(voucherCombineOrderId) ;

		for(VoucherOrder voucherOrder : voucherOrders){
			cancelVoucherOrder(voucherOrder) ;
		}

        VoucherOrder voucherOrder = voucherOrders.get(0);
        if (voucherOrder.getStatus().equals(VoucherOrderStatus.UNPAY)) {
            CouponSnapshot couponSnapshot = JSON.parseObject(voucherOrder.getCouponSnapshot(), CouponSnapshot.class);
            activityClientService.updateCouponNum(couponSnapshot.getActivityId(), couponSnapshot.getCouponId(), -voucherOrders.size());//释放库存
        }

		return true ;
	}

	@Override
	public boolean canBuy(String memberId, String couponId, int limtNum, int orderNum) {
		int accountAlreadyhave = couponService.countCouponNumWithoutShared(memberId, couponId) ;
		return accountAlreadyhave + orderNum <= limtNum ;
	}

	@Override
	public List<VoucherOrder> selectVoucherOrders4Cancel() {
		Map<String,Object> params = Maps.newHashMap();
		params.put("createdTo", DateTime.now().minusHours(2));
		List<VoucherOrderStatus> statusList = new ArrayList<VoucherOrderStatus>() ;
		statusList.add(VoucherOrderStatus.UNPAY) ;
		params.put("statusList", statusList) ;
		return voucherOrderDao.select(params) ;
	}

	@Override
	public List<VoucherCombineOrder> selectVoucherCombineOrders4Cancel() {
		Map<String,Object> params = Maps.newHashMap();
		params.put("createdTo", DateTime.now().minusMinutes(30));
		List<VoucherOrderStatus> statusList = new ArrayList<VoucherOrderStatus>() ;
		statusList.add(VoucherOrderStatus.UNPAY) ;
		params.put("statusList", statusList) ;
		return voucherOrderDao.selectVoucherCombineOrders(params) ;
	}

	@Override
	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED, rollbackFor = Throwable.class)
	public boolean applyRefund(String couponItemId) throws Exception{
		int count = voucherOrderDao.applyRefundByCouoponItemId(couponItemId) ;
		if(count != 1){
			Exception ex
				= new Exception("applyRefund:::updateVoucherOrder params:::{" + couponItemId + "} effect records:::{" + count + "}") ;
			LOGGER.error(ex.getMessage());
			throw ex ;
		}

		voucherClientService.updateVoucher4ApplyRefund(couponItemId) ;

		couponService.invalid(couponItemId) ;

		return true ;
	}

	@Override
	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED, rollbackFor = Throwable.class)
	public boolean updateVoucherOrder4Paid(OrderPaidBean orderPaidBean) throws Exception{
		//更新总订单表
		Map<String, Object> params = Maps.newHashMap() ;
		params.put("status", VoucherOrderStatus.PAYED) ;
		params.put("oldStatus", VoucherOrderStatus.UNPAY) ;
		params.put("oldCombineOrderId", orderPaidBean.getOrderId()) ;
		params.put("payType", orderPaidBean.getPaymentType()) ;
		params.put("paySerialNum", orderPaidBean.getTraceNum()) ;
		int count = voucherOrderDao.updateCombineOrderByMap(params) ;
		if(count != 1){
			Exception ex
				= new Exception("updateCombineVoucherOrder4Paid params:::{" + params + "} effect records:::{" + count + "}") ;
			LOGGER.error(ex.getMessage());
			throw ex ;
		}

		//更新子订单表
		params.clear();
		params.put("status", VoucherOrderStatus.PAYED) ;
		params.put("oldStatus", VoucherOrderStatus.UNPAY) ;
		params.put("oldCombineOrderId", orderPaidBean.getOrderId()) ;
		params.put("payType", orderPaidBean.getPaymentType()) ;
		params.put("paySerialNum", orderPaidBean.getTraceNum()) ;
		params.put("payTime", DateTime.now()) ;
		count = voucherOrderDao.updateByMap(params) ;
		if(count < 1){
			Exception ex
				= new Exception("updateVoucherOrder4Paid params:::{" + params + "} effect records:::{" + count + "}") ;
			LOGGER.error(ex.getMessage());
			throw ex ;
		}

		//更新产品表
//		List<VoucherOrder> voucherOrders = this.selectByCombineOrderId(orderPaidBean.getOrderId()) ;
//		for(VoucherOrder voucherOrder : voucherOrders){
//			voucherClientService.updateVoucher4Paid(voucherOrder.getVoucherCode()) ;
//		}

		return true ;
	}

	@Override
	public boolean updateVoucherOrder4Refunding(String voucherOrderId) {
		Map<String, Object> params = Maps.newHashMap() ;
		params.put("status", VoucherOrderStatus.REFUNDING) ;
		params.put("oldStatus", VoucherOrderStatus.AUDITING) ;
		params.put("voucherOrderId", voucherOrderId) ;
		int count = voucherOrderDao.updateByMap(params) ;
		if(count != 1){
			RuntimeException ex
				= new RuntimeException("updateVoucherOrder4Refunding params:::{" + params + "} effect records:::{" + count + "}") ;
			LOGGER.error(ex.getMessage());
			throw ex ;
		}
		return true ;
	}

	@Override
	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED, rollbackFor = Throwable.class)
	public boolean updateCombineVoucherOrder4Refunding(String combineVoucherId, String voucherOrderId) throws Exception{
		Map<String, Object> params = Maps.newHashMap() ;
		params.put("status", VoucherOrderStatus.REFUNDING) ;
		List<VoucherOrderStatus> vss = Lists.newArrayList() ;
		vss.add(VoucherOrderStatus.PAYED) ;
		vss.add(VoucherOrderStatus.REFUNDING) ;
		params.put("statusList", vss) ;
		params.put("oldCombineOrderId", combineVoucherId) ;
		int count = voucherOrderDao.updateCombineOrderByMap(params) ;
		if(count != 1){
			RuntimeException ex
				= new RuntimeException("updateCombineVoucherOrder4Refunding params:::{" + params + "} effect records:::{" + count + "}") ;
			LOGGER.error(ex.getMessage());
			throw ex ;
		}

		updateVoucherOrder4Refunding(voucherOrderId) ;

		return true ;
	}

	@Override
	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED, rollbackFor = Throwable.class)
	public boolean updateVoucherOrder4Refunded(String voucherOrderId, long refundAmount) throws Exception{
		Map<String, Object> params = Maps.newHashMap() ;
		params.put("status", VoucherOrderStatus.REFUNDED) ;
		List<VoucherOrderStatus> vss = Lists.newArrayList() ;
		vss.add(VoucherOrderStatus.REFUNDFAILED) ;
		vss.add(VoucherOrderStatus.REFUNDING) ;
		params.put("statusList", vss) ;
		params.put("voucherOrderId", voucherOrderId) ;
		params.put("refundedTime", DateTime.now()) ;
		int count = voucherOrderDao.updateByMap(params) ;
		if(count != 1){
			Exception ex
				= new Exception("updateVoucherOrder4Refunded params:::{" + params + "} effect records:::{" + count + "}") ;
			LOGGER.error(ex.getMessage());
			throw ex ;
		}

		VoucherOrder order = voucherOrderDao.selectById(voucherOrderId) ;
		updateCombineVoucherOrder4Refunded(order.getCombineOrderId(), refundAmount) ;

		return true ;
	}

	@Override
	public boolean updateCombineVoucherOrder4Refunded(String combineVoucherId, long refundAmount) throws Exception{

		VoucherCombineOrder voucherCombineOrder = this.selectCombineOrderById(combineVoucherId) ;
		long refundedAmount = voucherCombineOrder.getRefundedAmount() ;
		long newRefundedAmount = refundedAmount + refundAmount ;

		Map<String, Object> params = Maps.newHashMap() ;
		if(newRefundedAmount > voucherCombineOrder.getPayAmount()){
			RuntimeException ex
				= new RuntimeException("Refund amount error, orderId: {"+combineVoucherId+"}, refunded: {"+refundedAmount+"}, refund: {"+refundAmount+"}") ;
			LOGGER.error(ex.getMessage());
			throw ex ;
		}else if(newRefundedAmount < voucherCombineOrder.getPayAmount()){
			params.put("status", VoucherOrderStatus.REFUNDING) ;
		}else{
			params.put("status", VoucherOrderStatus.REFUNDED) ;
		}

		List<VoucherOrderStatus> vss = Lists.newArrayList() ;
		vss.add(VoucherOrderStatus.REFUNDFAILED) ;
		vss.add(VoucherOrderStatus.REFUNDING) ;
		params.put("statusList", vss) ;
		params.put("refundedAmount", newRefundedAmount) ;
		params.put("oldCombineOrderId", combineVoucherId) ;
		int count = voucherOrderDao.updateCombineOrderByMap(params) ;
		if(count != 1){
			RuntimeException ex
				= new RuntimeException("updateCombineVoucherOrder4Refunded params:::{" + params + "} effect records:::{" + count + "}") ;
			LOGGER.error(ex.getMessage());
			throw ex ;
		}

		return true ;
	}

	@Override
	public VoucherCombineOrder selectCombineOrderById(String combineOrderId) throws Exception{
		Map<String, Object> params = Maps.newHashMap() ;
		params.put("combineOrderId", combineOrderId) ;
		List<VoucherCombineOrder> voucherCombineOrders = voucherOrderDao.selectVoucherCombineOrders(params) ;
		if(CollectionUtils.isEmpty(voucherCombineOrders)){
			Exception ex
				= new Exception("can't find voucherCombineOrders by id:::{" + combineOrderId + "}") ;
			LOGGER.error(ex.getMessage());
			throw ex ;
		}
		if(voucherCombineOrders.size() != 1){
			Exception ex
				= new Exception("find too many voucherCombineOrders by id:::{" + combineOrderId + "}") ;
			LOGGER.error(ex.getMessage());
			throw ex ;
		}
		return voucherCombineOrders.get(0) ;
	}

	@Override
	public VoucherOrder selectById(String voucherOrderId) throws Exception{
		VoucherOrder voucherOrder = voucherOrderDao.selectById(voucherOrderId) ;
		if(voucherOrder == null){
			Exception ex
				= new Exception("can't find voucherOrder by id:::{" + voucherOrderId + "}") ;
			LOGGER.error(ex.getMessage());
			throw ex ;
		}
		return voucherOrder ;
	}

	@Override
	public boolean isVoucherOrderRefunded(String voucherOrderId) throws Exception{
		VoucherOrder voucherOrder = voucherOrderDao.selectForUpdate(voucherOrderId) ;
		return voucherOrder.getStatus() == VoucherOrderStatus.REFUNDED ;
	}

	@Override
	public List<VoucherOrder> selectByCombineOrderId(String combineOrderId) throws Exception{
		List<VoucherOrder> voucherOrders = voucherOrderDao.selectByCombineOrderId(combineOrderId) ;
		if(CollectionUtils.isEmpty(voucherOrders)){
			Exception ex
				= new Exception("can't find voucherOrder by combineOrderId:::{" + combineOrderId + "}") ;
			LOGGER.error(ex.getMessage());
			throw ex ;
		}
		return voucherOrders ;
	}


	@Override
	public List<VoucherOrder> selectVoucherOrderByVoucherCode(String voucherCode) throws Exception {
		Map<String, Object> params = Maps.newHashMap();
		params.put("voucherCode", voucherCode);
		List<VoucherOrder> voucherOrderList = voucherOrderDao.select(params);
		return voucherOrderList;
	}

	@Override
	public List<String> selectVoucherCodeByMap(Map map) throws Exception {
        List<VoucherOrder> voucherorderlist=voucherOrderDao.select(map);
        List<String> vouchercodelist=new ArrayList<String>();
        for(VoucherOrder voucherordertmp:voucherorderlist){
    	    vouchercodelist.add(voucherordertmp.getVoucherCode());
        }
		return vouchercodelist;
	}

	@Override
	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED, rollbackFor = Throwable.class)
	public boolean updateVoucherOrder4RefundFail(String voucherOrderId) throws Exception{
		Map<String, Object> params = Maps.newHashMap() ;
		params.put("status", VoucherOrderStatus.REFUNDFAILED) ;
		params.put("oldStatus", VoucherOrderStatus.REFUNDING) ;
		params.put("voucherOrderId", voucherOrderId) ;
		int count = voucherOrderDao.updateByMap(params) ;
		if(count != 1){
			Exception ex
				= new Exception("updateVoucherOrder4RefundFail params:::{" + params + "} effect records:::{" + count + "}") ;
			LOGGER.error(ex.getMessage());
			throw ex ;
		}

		VoucherOrder order = voucherOrderDao.selectById(voucherOrderId) ;
		updateCombineVoucherOrder4RefundFail(order.getCombineOrderId()) ;

		return true ;
	}

	@Override
	public boolean updateCombineVoucherOrder4RefundFail(String combineOrderId) throws Exception{
		Map<String, Object> params = Maps.newHashMap() ;
		params.put("status", VoucherOrderStatus.REFUNDFAILED) ;
		params.put("oldStatus", VoucherOrderStatus.REFUNDING) ;
		params.put("oldCombineOrderId", combineOrderId) ;
		int count = voucherOrderDao.updateCombineOrderByMap(params) ;
		if(count != 1){
			RuntimeException ex
				= new RuntimeException("updateCombineVoucherOrder4RefundFail params:::{" + params + "} effect records:::{" + count + "}") ;
			LOGGER.error(ex.getMessage());
			throw ex ;
		}

		return true ;
	}

    @Override
    @Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Map<String, Object> applyVoucherOrder(CouponBookVo voucherBookVo) throws Exception {

        Map<String,Object> applyResult = new HashMap<String,Object>();
        AjaxResponse result = AjaxResponse.instance(CouponBookConstants.APPLY_ORDER_FAILURE,"");

        String couponId = voucherBookVo.getCouponId();

        VoucherCombineOrder voucherCombineOrder = buildVoucherCombineOrderByVoucher(voucherBookVo);
        String voucherCombineOrderId = voucherCombineOrder.getCombineOrderId();
        if(!insertVoucherCombineOrder(voucherCombineOrder)){
            result.setRes_msg("插入代金券总订单失败");
            applyResult.put("result", result);
            return applyResult;
        }
        Map<String, Object> params = Maps.newHashMap();
        params.put("voucherIdList", voucherBookVo.getVoucherIdList());
        List<Voucher> voucherList = voucherClientService.getVoucherListByParams(params);
        for (Voucher voucher : voucherList) {
            //构建订单信息
            VoucherOrder voucherOrder = buildVoucherOrderByVoucher(voucher);
            voucherOrder.setCombineOrderId(voucherCombineOrderId);
            voucherOrder.setCouponSnapshot(voucherBookVo.getCouponSnapshot());
            //插入订单信息
            voucherOrderDao.insert(voucherOrder);
        }

        orderLogReuseService.save(voucherCombineOrderId, SSOUtil.getMemberSessionBean().getMid(), "创建","代金券活动订单");

        //微信建单操作
        if (StringUtils.isNotBlank(OpenIdUtil.getOpenId())) {
            OrderOpenId orderOpenId = new OrderOpenId();
            orderOpenId.setOrderId(voucherCombineOrderId);
            orderOpenId.setOpenId(OpenIdUtil.getOpenId());
            orderOpenIdDaoImpl.insert(orderOpenId);
        }

        //构建,保存支付页需要显示的相关信息
        OrderPayVo orderPayVo = buildOrderPayVo(voucherBookVo, voucherCombineOrderId, voucherCombineOrderId);
        redisClient.set(voucherCombineOrderId, orderPayVo);
        //校验,更新产品剩余库存
        activityClientService.updateCouponNum(voucherBookVo.getActivityId(), couponId, voucherBookVo.getBookAmount());

        result.setRes_code(CouponBookConstants.COUPON_APPLY_ORDER_SUCCESS);
        result.setRes_msg(voucherCombineOrderId);
        applyResult.put("result", result);
        return applyResult;
    }

    @Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public List<Voucher> lockVouchersByCouponId(CouponBookVo voucherBookVo) throws Exception {

        String couponId = voucherBookVo.getCouponId();
        int amount = voucherBookVo.getBookAmount();

        voucherClientService.selectVoucherLockForUpdateByCouponId(couponId);

        List<Voucher> voucherList = voucherClientService.selectBuyAvailableByCouponIdAndNum(couponId, amount);
        if (voucherList.size() < amount) {
            return null;
        }
        List<String> voucherIdList = getVoucherIdListByVoucherList(voucherList);
        boolean flag = voucherClientService.updateVoucher4ApplyLock(voucherIdList);
        if (flag) {
            return null;
        }

        return voucherList;
    }

    private List<String> getVoucherIdListByVoucherList(List<Voucher> voucherList) {
        List<String> voucherIdList = new ArrayList<String>();
        for (Voucher voucher : voucherList) {
            voucherIdList.add(voucher.getVoucherId());
        }
        return voucherIdList;
    }

    /**
     * 根据Voucher构建VoucherOrder订单
     * @param couponId
     * @return
     * @throws Exception
     */
    public VoucherOrder buildVoucherOrderByVoucher(Voucher voucher) throws Exception {

        VoucherOrder voucherOrder = new VoucherOrder();
        voucherOrder.setPayAmount(voucher.getPrice());
        voucherOrder.setTotalPrice(voucher.getPrice());
        voucherOrder.setCreatedBy(SSOUtil.getMemberSessionBean().getMemberId());
        voucherOrder.setCreated(new DateTime());
        voucherOrder.setUpdatedBy(SSOUtil.getMemberSessionBean().getMemberId());
        voucherOrder.setUpdated(new DateTime());
        voucherOrder.setCouponCode(voucher.getCouponCode());
        voucherOrder.setCouponId(voucher.getCouponId());
        voucherOrder.setOrderFrom("WEIXIN");
        voucherOrder.setStatus(VoucherOrderStatus.UNPAY);
        voucherOrder.setVoucherOrderId(idGeneratorUtil.getVoucherOrderId());
        voucherOrder.setVoucherCode(voucher.getVoucherCode());
        LOGGER.info(TZMarkers.p4, "提交的订单信息：[{}]", JSONObject.toJSONString(voucherOrder));
        return voucherOrder;
    }

    /**
     * 根据Voucher构建VoucherCombineOrder订单
     * @param couponId
     * @return
     * @throws Exception
     */
    public VoucherCombineOrder buildVoucherCombineOrderByVoucher(CouponBookVo voucherBookVo) throws Exception {
        VoucherCombineOrder voucherCombineOrder = new VoucherCombineOrder();
        voucherCombineOrder.setCombineOrderId(idGeneratorUtil.getVoucherCombineOrderId());
        voucherCombineOrder.setItemPrice(voucherBookVo.getPrice());
        voucherCombineOrder.setNum(voucherBookVo.getBookAmount());
        voucherCombineOrder.setPayAmount(voucherBookVo.getPrice() * voucherBookVo.getBookAmount());
        voucherCombineOrder.setCreatedBy(SSOUtil.getMemberSessionBean().getMemberId());
        voucherCombineOrder.setCreated(new DateTime());
        voucherCombineOrder.setUpdatedBy(SSOUtil.getMemberSessionBean().getMemberId());
        voucherCombineOrder.setUpdated(new DateTime());
        voucherCombineOrder.setStatus(VoucherOrderStatus.UNPAY);
        voucherCombineOrder.setMobile(voucherBookVo.getMobile());
        LOGGER.info(TZMarkers.p4, "提交的订单信息：[{}]", JSONObject.toJSONString(voucherCombineOrder));
        return voucherCombineOrder;
    }

    public OrderPayVo buildOrderPayVo(CouponBookVo voucherBookVo, String voucherCombineOrderId, String voucherOrderCode) {
        OrderPayVo payVo = new OrderPayVo();
        payVo.setProductType(ProductType.VOUCHER.name());
        payVo.setMemberId(SSOUtil.getMemberSessionBean().getMemberId());
        payVo.setPayAmount(voucherBookVo.getPrice());
        payVo.setTitle(voucherBookVo.getCouponName());
        payVo.setOrderId(voucherCombineOrderId);
        payVo.setOrderCode(voucherOrderCode);
        payVo.setTotalPrice(voucherBookVo.getPrice() * voucherBookVo.getBookAmount());
        return payVo;
    }

    @Override
    public boolean insertVoucherCombineOrder(VoucherCombineOrder voucherCombineOrder) throws Exception {
        return voucherOrderDao.insertVoucherCombineOrder(voucherCombineOrder) == 1;
    }

    @Override
    public VoucherCombineOrder selectCombineOrderByCombineOrderId(
            String voucherCombineOrderId) throws Exception {
        VoucherCombineOrder voucherCombineOrder = voucherOrderDao.selectCombineOrderByCombineOrderId(voucherCombineOrderId);
        if(voucherCombineOrder == null){
            Exception ex
                = new Exception("can't find voucherCombineOrder by id:::{" + voucherCombineOrderId + "}") ;
            LOGGER.error(ex.getMessage());
            throw ex ;
        }
        return voucherCombineOrder ;
    }

    @Override
    public VoucherCombineOrderInfo selectCombineOrderInfoByCombineOrderId(String voucherOrderId) throws Exception {
        VoucherCombineOrder voucherCombineOrder = selectCombineOrderByCombineOrderId(voucherOrderId);
        if (voucherCombineOrder != null) {
            VoucherCombineOrderInfo combineOrderInfo = new VoucherCombineOrderInfo();
            combineOrderInfo.setCombineOrderId(voucherCombineOrder.getCombineOrderId());
            combineOrderInfo.setCreatedBy(voucherCombineOrder.getCreatedBy());
            combineOrderInfo.setMobile(voucherCombineOrder.getMobile());
            
            List<VoucherOrder> voucherOrders = this.selectByCombineOrderId(voucherCombineOrder.getCombineOrderId()) ;
            VoucherOrder voucherOrder = voucherOrders.get(0) ;
            Voucher voucher = voucherClientService.getVoucherByCode(voucherOrder.getVoucherCode()) ;
            CouponClientEntity entity = activityClientService.getCouponById(voucher.getActivityId(), voucher.getCouponId()) ;
            combineOrderInfo.setCouponName(entity.getName());
            
            return combineOrderInfo;
        }

        return null ;

    }

}
