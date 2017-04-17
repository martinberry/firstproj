package com.ztravel.order.back.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.enums.CommonOrderStatus;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.order.back.service.ICommonOrderService;
import com.ztravel.order.back.vo.CommonOrderListVo;
import com.ztravel.order.dao.ICommonOrderDao;
import com.ztravel.order.dao.IOrderLogDao;
import com.ztravel.order.po.CommonOrder;
import com.ztravel.order.po.CommonOrderPo;
import com.ztravel.order.po.CommonOrderSearchCriteria;
import com.ztravel.order.po.OrderLog;

@Service
public class CommonOrderServiceImpl implements ICommonOrderService{
	@Resource
	private ICommonOrderDao commonOrderDaoImpl;
	@Resource
	private IOrderLogDao orderLogDaoImpl;

	private static final Logger LOGGER=LoggerFactory.getLogger(CommonOrderServiceImpl.class);
	private static final RedisClient redisClient = RedisClient.getInstance();

	//搜索列表
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		public List<CommonOrderListVo> searchCO(CommonOrderSearchCriteria criteria)  {
			Map paramsMap =  convertSearchCriteria(criteria);
			paramsMap.put("offset", (criteria.getPageNo()-1)*criteria.getPageSize());
			paramsMap.put("limit", criteria.getPageSize());
			List<CommonOrderPo> CommonOrderPoList = commonOrderDaoImpl.selectCombineOrder(paramsMap);
			List<CommonOrderListVo> CommonOrderVoList = convert2VOList(CommonOrderPoList);
			return  CommonOrderVoList;
		}


	//搜索到的记录数目
		@SuppressWarnings({ "rawtypes"})
		@Override
	    public Integer countCOs(CommonOrderSearchCriteria criteria) throws Exception{
			Map paramsMap=convertSearchCriteria(criteria);
			return commonOrderDaoImpl.count(paramsMap);
		}



  //根据ordernoorigin查找
		@Override
		public CommonOrder search(String orderNoOrigin) throws Exception{
			return commonOrderDaoImpl.selectByOriginOrderNo(orderNoOrigin);
		}
  //根据ordervice查找
		@Override
	    public CommonOrder searchByOrderNoVice(String orderNoVice) throws Exception{
			return commonOrderDaoImpl.selectByorderVice(orderNoVice);
		}



  //更改状态并增加操作日志
		@Override
	    @Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED,rollbackFor=Throwable.class)
		public void updateAndinsertlog(CommonOrder commonordertmp) throws Exception{
		    	OrderLog orderlog=new OrderLog();
		    	if(commonordertmp.getStatus()==CommonOrderStatus.REFUNDING){
		    	orderlog.setContent("退款审核通过");
		    	}else{
		    	orderlog.setContent(commonordertmp.getStatus().getDescription());
		    	}
				orderlog.setRemark(commonordertmp.getRemark());
				orderlog.setOperateTime(new Date());
				orderlog.setOperator(redisClient.get(OperatorSidHolder.get()));
				orderlog.setOrderId(commonordertmp.getOrderIdOrigin());
				orderlog.setId(UUID.randomUUID().toString());
				commonOrderDaoImpl.update(commonordertmp);
				orderLogDaoImpl.insert(orderlog);
		}


		private List<CommonOrderListVo> convert2VOList(List<CommonOrderPo> CommonOrderPoList){

			List<CommonOrderListVo> CommonOrderVoList=new ArrayList<CommonOrderListVo>();
			for(CommonOrderPo commonorderpotmp:CommonOrderPoList){
				CommonOrderListVo covtmp=new CommonOrderListVo();
				covtmp.setOrderNoOrigin(commonorderpotmp.getOrderNoOrigin());
				covtmp.setOrderId(commonorderpotmp.getOrderId());
                covtmp.setOrderOriginStatus(commonorderpotmp.getBackState());
                covtmp.setMemberId(commonorderpotmp.getMemberId());
                covtmp.setTravellerNames(commonorderpotmp.getTravellerNames());
                covtmp.setOrderType(commonorderpotmp.getType().toString());
                covtmp.setOrderIdOrigin(commonorderpotmp.getOrderIdOrigin());
                if(commonorderpotmp.getStateChangeHistory()!=null)
                covtmp.setStateChangeHistory(convertstateChangeHistory(commonorderpotmp.getStateChangeHistory()));
                if(commonorderpotmp.getCreateDate()!=null){
                covtmp.setCreateDate(commonorderpotmp.getCreateDate().toDateTime(DateTimeZone.forOffsetHours(8)).toString("yyyy-MM-dd HH:mm:ss"));
                }
                if(commonorderpotmp.getStatus()==CommonOrderStatus.INIT){
                covtmp.setStatus("待审核");
                }else if(commonorderpotmp.getStatus()==CommonOrderStatus.REFUNDING){
                covtmp.setStatus("待退款");
                }
                else if(commonorderpotmp.getStatus()==CommonOrderStatus.REFUNDED){
                covtmp.setStatus("已退款");
                }else if(commonorderpotmp.getStatus()==CommonOrderStatus.REFUNDFAIL){
                covtmp.setStatus("退款失败");
                }else if(commonorderpotmp.getStatus()==CommonOrderStatus.AUDIT_UNPASS){
                covtmp.setStatus("审核不通过");
                }else if(commonorderpotmp.getStatus()==CommonOrderStatus.UNPAY){
                covtmp.setStatus("待支付");
                }else if(commonorderpotmp.getStatus()==CommonOrderStatus.PAID){
                covtmp.setStatus("已支付");
                }else{
                covtmp.setStatus("已取消");
                }
                if(commonorderpotmp.getBackState().equals("UNPAY")){
                	covtmp.setOrderOriginStatus("待支付");
                }else if(commonorderpotmp.getBackState().equals("PAYED")){
                	covtmp.setOrderOriginStatus("支付成功");
                }else if(commonorderpotmp.getBackState().equals("CONFIRM")){
                	covtmp.setOrderOriginStatus("OP确认");
                }else if(commonorderpotmp.getBackState().equals("GIFTSEND")){
                	covtmp.setOrderOriginStatus("礼盒发放");
                }else if(commonorderpotmp.getBackState().equals("OUTNOTICE")){
                	covtmp.setOrderOriginStatus("出行通知");
                }else if(commonorderpotmp.getBackState().equals("OUTTING")){
                	covtmp.setOrderOriginStatus("出行中");
                }else if(commonorderpotmp.getBackState().equals("COMPLETED")){
                	covtmp.setOrderOriginStatus("已完成");
                }else if(commonorderpotmp.getBackState().equals("CANCELED")){
                	covtmp.setOrderOriginStatus("已取消");
                }else if(commonorderpotmp.getBackState().equals("REFUNDING")){
                	covtmp.setOrderOriginStatus("退款中");
                }else{
                	covtmp.setOrderOriginStatus("退款失败");
                }


				covtmp.setPrice(commonorderpotmp.getPrice());
				if(commonorderpotmp.getRemark() !=null){
					covtmp.setRemark(commonorderpotmp.getRemark());
				}

				CommonOrderVoList.add(covtmp);
			}
			return CommonOrderVoList;

		}


		@Override
		public String convertstateChangeHistory(String statechangepo){
			statechangepo=statechangepo.replace("INIT","待审核");
			statechangepo=statechangepo.replace("AUDIT_UNPASS","审核未通过");
			statechangepo=statechangepo.replace("UNPAY","待支付");
			statechangepo=statechangepo.replace("PAID","已支付");
			statechangepo=statechangepo.replace("REFUNDING","待退款");
			statechangepo=statechangepo.replace("REFUNDED","已退款");
			statechangepo=statechangepo.replace("REFUNDFAIL","退款失败");
			statechangepo=statechangepo.replace("CANCELED","已取消");
			return statechangepo;

		}






		@SuppressWarnings({ "rawtypes", "unchecked" })
		private Map convertSearchCriteria(CommonOrderSearchCriteria criteria){
			Map map = new HashMap();

			if( StringUtils.isNotBlank(criteria.getOrderNo()) ){
				map.put("orderNo", criteria.getOrderNo());
			}
			if( StringUtils.isNotBlank(criteria.getOrderId()) ){
				map.put("orderId", criteria.getOrderId());
			}
			if( StringUtils.isNotBlank(criteria.getMemberId()) ){
				map.put("memberId", criteria.getMemberId());
			}
            if( StringUtils.isNotBlank(criteria.getTravellerNames()) ){
                map.put("travellerNames", "%" + criteria.getTravellerNames() + "%");
            }

			if( StringUtils.isNotBlank(criteria.getStatus())&&!((criteria.getStatus()).equals("全部"))){
				List<String> statuslist=new ArrayList<String>();
				if(criteria.getStatus().equals("待退款")){
					statuslist.add("REFUNDING");
					map.put("statuslist", statuslist);
				}else if(criteria.getStatus().equals("已退款")){
					statuslist.add("REFUNDED");
					map.put("statuslist", statuslist);
				}
				else if(criteria.getStatus().equals("审核不通过")){
					statuslist.add("AUDIT_UNPASS");
					map.put("statuslist", statuslist);
				}else if(criteria.getStatus().equals("待审核")){
					statuslist.add("INIT");
					map.put("statuslist", statuslist);
				}else if(criteria.getStatus().equals("待支付")){
					statuslist.add("UNPAY");
					map.put("statuslist", statuslist);
				}else if(criteria.getStatus().equals("已支付")){
					statuslist.add("PAID");
					map.put("statuslist", statuslist);
				}else{
					statuslist.add("CANCELED");
					map.put("statuslist", statuslist);
				}

			}

			if( StringUtils.isNotBlank(criteria.getStatus())){
				map.put("orderType", criteria.getOrderType());
			}
    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try{
				if( StringUtils.isNotBlank(criteria.getCreateDateFrom()) ){
					map.put("createDateFrom", format.parse(criteria.getCreateDateFrom()+" 00:00:00"));
				}
				if( StringUtils.isNotBlank(criteria.getCreateDateTo()) ){
					map.put("createDateTo", format.parse(criteria.getCreateDateTo()+" 23:59:59"));
				}
			} catch(ParseException e) {
				LOGGER.error(e.getMessage(), e);
			}


			return map;
		}


}
