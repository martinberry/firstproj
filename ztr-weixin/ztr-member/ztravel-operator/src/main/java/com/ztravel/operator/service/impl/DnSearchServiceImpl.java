package com.ztravel.operator.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.travelzen.swordfish.thrift.util.JacksonUtil;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.enums.VoucherOrderStatus;
import com.ztravel.common.enums.VoucherStatus;
import com.ztravel.common.enums.VoucherType;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.member.client.service.IMemberClientService;
import com.ztravel.operator.convert.DNConvert;
import com.ztravel.operator.electronicWallet.entity.DnCouponSearchCriteria;
import com.ztravel.operator.service.IDnSearchService;
import com.ztravel.order.client.service.IOrderClientService;
import com.ztravel.order.client.service.IVoucherOrderClientService;
import com.ztravel.order.po.VoucherOrder;
import com.ztravel.payment.paygate.model.VoucherOrderRefundRequest;
import com.ztravel.payment.service.ICouponService;
import com.ztravel.payment.service.IOrderPaymentService;
import com.ztravel.product.client.entity.Voucher;
import com.ztravel.product.client.entity.vo.RecievedCouponVo;
import com.ztravel.product.client.entity.vo.RefundedCouponVo;
import com.ztravel.product.client.entity.vo.SystemLockCouponVo;
import com.ztravel.product.client.entity.vo.UnRefundedCouponVo;
import com.ztravel.product.dao.IVoucherDao;


@Service
public class DnSearchServiceImpl implements IDnSearchService{
    @Resource
	private IVoucherDao voucherDao;
    @Resource
    private IOrderPaymentService orderPaymentService;
    @Resource
	private ICouponService couponService;
	@Resource
	private IOrderClientService orderservice;
	@Resource
    private IVoucherOrderClientService voucherOrderClientservice;
	@Resource
	private IMemberClientService memberClientService;


	private static final Logger LOGGER = LoggerFactory.getLogger(DnSearchServiceImpl.class);



				@Override
                public AjaxResponse ChangeAuditStatusrByVoucherCode(String voucherCode){
					AjaxResponse ajaxresponse=new AjaxResponse();

					try{
						List<VoucherOrder> voucherorderlist=voucherOrderClientservice.selectVoucherOrderByVoucherCode(voucherCode);

						VoucherOrder voucherorder= null ;

						for(VoucherOrder order : voucherorderlist){
							if(order.getStatus() != VoucherOrderStatus.CANCELED){
								voucherorder = order ;
							}
						}

                       VoucherOrderRefundRequest request = new VoucherOrderRefundRequest() ;
                       request.setCombineVoucherOrderId(voucherorder.getCombineOrderId());
                       request.setVoucherOrderId(voucherorder.getVoucherOrderId());
                       request.setPaymentType(voucherorder.getPayType().toString());
                       request.setRefundAmount(voucherorder.getPayAmount());
                       request.setOperator("admin");
                       CommonResponse res= orderPaymentService.cancelOrder(request) ;
                       if(res.isSuccess()){
                        	 ajaxresponse.setRes_code("success");
                        	 ajaxresponse.setRes_msg("操作成功");
                         }
                       return ajaxresponse;

					}catch(Exception e){
						ajaxresponse.setRes_code("failed");
						ajaxresponse.setRes_msg("操作失败");
						return ajaxresponse;
					}

                 }

		@Override
		public List searchByCriteria(DnCouponSearchCriteria criteria) throws Exception {
			String searchTab = criteria.getSearchTab();
			List dataList = Lists.newArrayList();
			if(searchTab.equals(VoucherStatus.INIT.toString())){
				dataList = searchInit(criteria);
			}else if(searchTab.equals(VoucherStatus.RECIEVED.toString())){
				dataList = searchReceived(criteria);
			}else if(searchTab.equals(VoucherOrderStatus.AUDITING.toString())){
				dataList = searchUnrefunded(criteria);
			}else if(searchTab.equals(VoucherOrderStatus.REFUNDED.toString())){
				dataList = searchRefunded(criteria);
			}
			return dataList;
		}


		 @SuppressWarnings("rawtypes")
		private List searchRefunded(DnCouponSearchCriteria criteria) throws Exception{
			 	Map<String, Object> paramsMap =  convertCriteria2RefundedMap(criteria);
				paramsMap.put("offset", (criteria.getPageNo()-1)*criteria.getPageSize());
				paramsMap.put("limit", criteria.getPageSize());
				List<RefundedCouponVo> refundedCouponList = voucherDao.selectRefund(paramsMap);
				if(CollectionUtils.isNotEmpty(refundedCouponList)){
					for(RefundedCouponVo bean : refundedCouponList){
						if(bean.getPayTime() != null){
							bean.setPayTimeStr((bean.getPayTime().toDateTime(DateTimeZone.forOffsetHours(8))).toString("yyyy-MM-dd HH:mm:ss"));
						}
						if(bean.getRefundTime() != null){
							bean.setRefundTimeStr((bean.getRefundTime().toDateTime(DateTimeZone.forOffsetHours(8))).toString("yyyy-MM-dd HH:mm:ss"));
						}
					}
				}
				return refundedCouponList;

		}

			private Map<String, Object> convertCriteria2RefundedMap(DnCouponSearchCriteria criteria){
				Map<String, Object> map = Maps.newHashMap();
				if( StringUtils.isNotBlank(criteria.getVoucherCode()) ){
					String voucherCode=criteria.getVoucherCode();
					 voucherCode=voucherCode.toUpperCase();
					map.put("voucherCode",voucherCode );
				}
				if(StringUtils.isNotBlank(criteria.getMemberId())){
					String ordermemberId=criteria.getMemberId();
					map.put("memberId", ordermemberId);
				}

				if(StringUtils.isNotBlank(criteria.getVoucherOrderId())){
					String voucherOrderId=criteria.getVoucherOrderId();
					map.put("voucherOrderId", voucherOrderId);
				}

				if(StringUtils.isNotEmpty(criteria.getCouponId())){
					map.put("couponId", criteria.getCouponId());
				}
				map.put("voucherOrderStatus", VoucherOrderStatus.REFUNDED);

				try{
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					if( StringUtils.isNotBlank(criteria.getPayTimeFrom()) ){
						map.put("PayTimeFrom", format.parse(criteria.getPayTimeFrom()+" 00:00:00"));
					}
					if( StringUtils.isNotBlank(criteria.getPayTimeTo()) ){
						map.put("PayTimeTo", format.parse(criteria.getPayTimeTo()+" 23:59:59"));
					}
					if( StringUtils.isNotBlank(criteria.getRefundTimeFrom()) ){
						map.put("FundTimeFrom", format.parse(criteria.getRefundTimeFrom()+" 00:00:00"));
					}
					if( StringUtils.isNotBlank(criteria.getRefundTimeTo()) ){
						map.put("FundTimeTo", format.parse(criteria.getRefundTimeTo()+" 23:59:59"));
					}

				} catch(ParseException e) {
					LOGGER.error(e.getMessage(), e);
				}
				return map;
			}

		private List searchUnrefunded(DnCouponSearchCriteria criteria) throws Exception{
			Map<String, Object> paramsMap =  convertCriteria2UnrefundedMap(criteria);
			paramsMap.put("offset", (criteria.getPageNo()-1)*criteria.getPageSize());
			paramsMap.put("limit", criteria.getPageSize());
			List<UnRefundedCouponVo> unRefundedCouponList = voucherDao.selectPrefund(paramsMap);

			if(CollectionUtils.isNotEmpty(unRefundedCouponList)){
				for(UnRefundedCouponVo bean : unRefundedCouponList){
					if(bean.getPayTime() != null){
						bean.setPayTimeStr((bean.getPayTime().toDateTime(DateTimeZone.forOffsetHours(8))).toString("yyyy-MM-dd HH:mm:ss"));
					}
					if(bean.getPrefundTime() != null){
						bean.setPrefundTimeStr((bean.getPrefundTime().toDateTime(DateTimeZone.forOffsetHours(8))).toString("yyyy-MM-dd HH:mm:ss"));
					}
				}
			}

              return unRefundedCouponList;
		}

	private List searchReceived(DnCouponSearchCriteria criteria) throws Exception{
			Map<String, Object> receivedTabParams = convertCriteria2RecievedMap(criteria);
			receivedTabParams.put("offset", (criteria.getPageNo()-1)*criteria.getPageSize());
			receivedTabParams.put("limit", criteria.getPageSize());
			List<RecievedCouponVo> recievedCouponVoList = voucherDao.selectReceived(receivedTabParams);

			if(CollectionUtils.isNotEmpty(recievedCouponVoList)){
				if(sortTime(recievedCouponVoList)!=null){
					recievedCouponVoList=sortTime(recievedCouponVoList);
				}
				   for(RecievedCouponVo recievedCouponVo : recievedCouponVoList){

				                                  if(recievedCouponVo.getPayTime() != null){
			                                          recievedCouponVo.setPayTimeStr((recievedCouponVo.getPayTime().toDateTime(DateTimeZone.forOffsetHours(8))).toString("yyyy-MM-dd HH:mm:ss"));
				                             }

				                                if(StringUtils.isEmpty(recievedCouponVo.getMobile()) && StringUtils.isNotEmpty(recievedCouponVo.getVoucherOrderId())){
				                                        String mid = recievedCouponVo.getMid();
			                                             if(StringUtils.isNotEmpty(mid)){
			                                                   String memberStr = memberClientService.getMemberByMid(mid);
		                                                    if(StringUtils.isNotEmpty(memberStr)){
					                                                           Map<String,Object> memberMap = JacksonUtil.json2map(memberStr);
		                                                      String mobile = (String)memberMap.get("mobile");
					                                                            recievedCouponVo.setMobile(mobile);
					                                              }
                                             }
				                        }
	/*			for(RecievedCouponVo bean : recievedCouponVoList){
					if(bean.getPayTime() != null){
						bean.setPayTimeStr((bean.getPayTime().toDateTime(DateTimeZone.forOffsetHours(8))).toString("yyyy-MM-dd HH:mm:ss"));
					}
				}*/
			}
		}

          return recievedCouponVoList;
	}


	////时间排序
	private List<RecievedCouponVo> sortTime(List<RecievedCouponVo> recievedCouponVoList){
		List<DateTime> timelist= new ArrayList<DateTime>();
		for(RecievedCouponVo recievedcouponvo:recievedCouponVoList){
			if(recievedcouponvo.getOrderCreateTime()!=null){
				timelist.add(recievedcouponvo.getOrderCreateTime());
			}else if(recievedcouponvo.getPayTime()!=null){
				timelist.add(recievedcouponvo.getPayTime());
			}else{
				return null;
			}
		}
		int totalsize=timelist.size();
		List<Integer> indexlist=new ArrayList<Integer>();
		for(int i=0;i<totalsize;i++){
			indexlist.add(i);
		}

		for(int m=0;m<totalsize;m++){
			for(int n=totalsize-1;n>m;n--){
                    if((timelist.get(n).getMillis())>=(timelist.get(n-1).getMillis())){
                    	DateTime timetmp=timelist.get(n);
                    	timelist.set(n, timelist.get(n-1));
                    	timelist.set(n-1,timetmp);
                           int tmp=indexlist.get(n);
                           indexlist.set(n, indexlist.get(n-1));
                           indexlist.set(n-1,tmp);
                     }
			}
		}

		List<RecievedCouponVo> tmprecievedCouponVoList=new ArrayList<RecievedCouponVo>();
		for(int n:indexlist){
			tmprecievedCouponVoList.add(recievedCouponVoList.get(n));
		}
		recievedCouponVoList=tmprecievedCouponVoList;
		return recievedCouponVoList;
	}


		private List<SystemLockCouponVo> searchInit(DnCouponSearchCriteria criteria) throws Exception {
			Map<String, Object> initTabParams =  convertCriteria2InitMap(criteria);
			initTabParams.put("offset", (criteria.getPageNo()-1)*criteria.getPageSize());
			initTabParams.put("limit", criteria.getPageSize());
			List<Voucher> dnfirstdataList = voucherDao.select(initTabParams);
			List<SystemLockCouponVo> dnfirstlistVo = DNConvert.convertFirstList2VO(dnfirstdataList);
			return  dnfirstlistVo;
		}

		private Map<String, Object> convertCriteria2InitMap(DnCouponSearchCriteria criteria) {
			Map<String, Object> params = Maps.newHashMap();
			if( StringUtils.isNotBlank(criteria.getVoucherCode()) ){
				String voucherCode=criteria.getVoucherCode();
				 voucherCode=voucherCode.toUpperCase();
				params.put("voucherCode",voucherCode);
			}
			if(criteria.getVoucherType()!=null){
				VoucherType voucherType=criteria.getVoucherType();
				String searchvoucherType=voucherType.getCode();
				params.put("voucherType",searchvoucherType);
			}
			if(StringUtils.isNotEmpty(criteria.getCouponId())){
				params.put("couponId", criteria.getCouponId());
			}
			List<VoucherStatus> statusList = Lists.newArrayList();
			statusList.add(VoucherStatus.INIT);
			params.put("statusList", statusList);
			return params;
		}

		@Override
		public Integer countByCriteria(DnCouponSearchCriteria criteria)throws Exception {
			String searchTab = criteria.getSearchTab();
			int count =0;
			if(searchTab.equals(VoucherStatus.INIT.toString())){
				count = countInit(criteria);
			}else if(searchTab.equals(VoucherStatus.RECIEVED.toString())){
				count = countReceived(criteria);
			}else if(searchTab.equals(VoucherOrderStatus.AUDITING.toString())){
				count = countUnrefunded(criteria);
			}else if(searchTab.equals(VoucherOrderStatus.REFUNDED.toString())){
				count = countRefunded(criteria);
			}
			return count;
		}

		private int countInit(DnCouponSearchCriteria criteria) {
			Map<String, Object> paramsMap =  convertCriteria2InitMap(criteria);
			return voucherDao.count(paramsMap);
		}

		private int countReceived(DnCouponSearchCriteria criteria) {
			Map<String, Object> paramsMap =  convertCriteria2RecievedMap(criteria);
			return voucherDao.countreceived(paramsMap);
		}

		private Map<String, Object> convertCriteria2RecievedMap(DnCouponSearchCriteria criteria) {
			Map<String, Object> map = Maps.newHashMap();
			if( StringUtils.isNotEmpty(criteria.getVoucherCode()) ){
				String voucherCode=criteria.getVoucherCode();
				 voucherCode=voucherCode.toUpperCase();
				map.put("voucherCode",voucherCode);
			}
			if(StringUtils.isNotEmpty(criteria.getMemberId())){
				String memberId=criteria.getMemberId();
				map.put("memberId", memberId);
			}
			if(StringUtils.isNotEmpty(criteria.getOrdermemberId())){
				String ordermemberId=criteria.getOrdermemberId();
				map.put("creator", ordermemberId);
			}
			if(criteria.getStatusList() != null && !criteria.getStatusList().isEmpty()){
				List<VoucherStatus> statusList=criteria.getStatusList();
				map.put("statusList", statusList);
			}else{
				List<VoucherStatus> statusList= Lists.newArrayList();
				statusList.add(VoucherStatus.RECIEVED);
				statusList.add(VoucherStatus.USED);
				map.put("statusList", statusList);
			}

			if(StringUtils.isNotEmpty(criteria.getOrderId())){
				String orderId=criteria.getOrderId();
				map.put("orderNo", orderId);
			}
			if(criteria.getVoucherType()!=null){
				VoucherType voucherType=criteria.getVoucherType();
				String searchvoucherType=voucherType.getCode();
				map.put("voucherType",searchvoucherType);
			}
			if(StringUtils.isNotEmpty(criteria.getVoucherOrderId())){
				String voucherOrderId=criteria.getVoucherOrderId();
				map.put("voucherOrderId", voucherOrderId);
			}
			if(StringUtils.isNotEmpty(criteria.getCouponId())){
				map.put("couponId", criteria.getCouponId());
			}
			try{
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if( StringUtils.isNotEmpty(criteria.getPayTimeFrom()) ){
					map.put("PayTimeFrom", format.parse(criteria.getPayTimeFrom()+" 00:00:00"));
				}
				if( StringUtils.isNotEmpty(criteria.getPayTimeTo()) ){
					map.put("PayTimeTo", format.parse(criteria.getPayTimeTo()+" 23:59:59"));
				}
			} catch(ParseException e) {
				LOGGER.error(e.getMessage(), e);
			}
			return map;
		}

		private int countUnrefunded(DnCouponSearchCriteria criteria) {
			Map<String, Object> paramsMap =  convertCriteria2UnrefundedMap(criteria);
			return voucherDao.countprefund(paramsMap);
		}

		private Map<String, Object> convertCriteria2UnrefundedMap(DnCouponSearchCriteria criteria){
			Map<String, Object> map = Maps.newHashMap();
			if( StringUtils.isNotBlank(criteria.getVoucherCode()) ){
				String voucherCode=criteria.getVoucherCode();
				 voucherCode=voucherCode.toUpperCase();
				map.put("voucherCode",voucherCode );
			}
			if(StringUtils.isNotBlank(criteria.getMemberId())){
				String ordermemberId=criteria.getMemberId();
				map.put("memberId", ordermemberId);
			}

			if(StringUtils.isNotBlank(criteria.getVoucherOrderId())){
				String voucherOrderId=criteria.getVoucherOrderId();
				map.put("voucherOrderId", voucherOrderId);
			}

			if(StringUtils.isNotEmpty(criteria.getCouponId())){
				map.put("couponId", criteria.getCouponId());
			}
			List<String> voucherOrderStatusList= Lists.newArrayList();
			voucherOrderStatusList.add(VoucherOrderStatus.AUDITING.toString());
			voucherOrderStatusList.add(VoucherOrderStatus.REFUNDING.toString());
		    map.put("voucherOrderStatusList", voucherOrderStatusList);


			try{
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if( StringUtils.isNotBlank(criteria.getPayTimeFrom()) ){
					map.put("PayTimeFrom", format.parse(criteria.getPayTimeFrom()+" 00:00:00"));
				}
				if( StringUtils.isNotBlank(criteria.getPayTimeTo()) ){
					map.put("PayTimeTo", format.parse(criteria.getPayTimeTo()+" 23:59:59"));
				}
				if( StringUtils.isNotBlank(criteria.getPrefundTimeFrom()) ){
					map.put("prefundTimeFrom", format.parse(criteria.getPrefundTimeFrom()+" 00:00:00"));
				}
				if( StringUtils.isNotBlank(criteria.getPrefundTimeTo()) ){
					map.put("prefundTimeTo", format.parse(criteria.getPrefundTimeTo()+" 23:59:59"));
				}

			} catch(ParseException e) {
				LOGGER.error(e.getMessage(), e);
			}
			return map;
		}

		private int countRefunded(DnCouponSearchCriteria criteria) {
			Map<String, Object> paramsMap =  convertCriteria2RefundedMap(criteria);
			return voucherDao.countrefund(paramsMap);
		}


}







