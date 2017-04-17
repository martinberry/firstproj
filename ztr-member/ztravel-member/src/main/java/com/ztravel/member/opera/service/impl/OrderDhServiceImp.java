package com.ztravel.member.opera.service.impl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.travelzen.framework.core.util.MoneyUtil;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.OrderConstans;
import com.ztravel.common.constants.ResponseConstBackMemb;
import com.ztravel.common.enums.AccountConvertContent;
import com.ztravel.common.enums.AccountCovertStatus;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.member.dao.IOrderDhDao;
import com.ztravel.member.po.Dhpo;
import com.ztravel.member.opera.converter.DHConvert;
import com.ztravel.member.opera.entity.DHSearchCriteria;
import com.ztravel.member.opera.entity.MemberSearchCriteria;
import com.ztravel.member.opera.service.IMemberService;
import com.ztravel.member.opera.service.IOrderDhService;
import com.ztravel.member.opera.vo.DHOrderDetailVO;
import com.ztravel.member.opera.vo.DHOrderListVO;
import com.ztravel.member.opera.vo.MemberVO;
import com.ztravel.payment.service.IAccountService;

@Service
public class OrderDhServiceImp implements IOrderDhService{
	//orderDhServiceImp
	@Resource
	private IOrderDhDao orderdhdao;

	@Resource
	private IAccountService accountService;

	@Resource
	private IMemberService memberServiceImpl;

	private final RedisClient redisClient = RedisClient.getInstance();



	private static final Logger LOGGER = LoggerFactory.getLogger(OrderDhServiceImp.class);
	//把通过搜索模块的关键字放入哈希表

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map convertDHSearchCriteria(DHSearchCriteria criteria){
		Map map = new HashMap();
		//会员id和兑换单id模糊查询
		if( StringUtils.isNotBlank(criteria.getDhId()) ){
			String DhId;
			if( criteria.getDhId().contains("%") ){
			DhId = criteria.getDhId().replace("%", "\\%");
			}else{
			          DhId = criteria.getDhId();
			}
			map.put("dhId", "%"+DhId+"%");
		}
		if( StringUtils.isNotBlank(criteria.getMemberId()) ){
			String MemberId;
			if( criteria.getMemberId().contains("%") ){
				MemberId = criteria.getMemberId().replace("%", "\\%");
			}else{
				MemberId = criteria.getMemberId();
			}
			map.put("memberId", "%"+MemberId+"%");
		}

		if( StringUtils.isNotBlank(criteria.getDhStatus()+"")){
			map.put("dhStatus", criteria.getDhStatus());
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			if( StringUtils.isNotBlank(criteria.getPledhTimeFrom()) ){
				map.put("pledhTimeFrom", format.parse(criteria.getPledhTimeFrom()+" 00:00:00"));
			}
			if( StringUtils.isNotBlank(criteria.getPledhTimeTo()) ){
				map.put("pledhTimeTo", format.parse(criteria.getPledhTimeTo()+" 23:59:59"));
			}
		} catch(ParseException e) {
			LOGGER.error(e.getMessage(), e);
		}

		if( StringUtils.isNotBlank(criteria.getDhPriceLowerBound()) ){
			Long DhPriceLowerBound= Long.parseLong(MoneyUtil.yuan2Cent(criteria.getDhPriceLowerBound()));
			map.put("dhPriceLowerBound", DhPriceLowerBound);
		}
		if( StringUtils.isNotBlank(criteria. getDhPriceUpperBound()) ){
			Long  DhPriceUpperBound = Long.parseLong(MoneyUtil.yuan2Cent(criteria. getDhPriceUpperBound()));
			map.put("dhPriceUpperBound",  DhPriceUpperBound);
		}
		return map;
	}


 //搜索列表
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<DHOrderListVO> searchDH(DHSearchCriteria criteria) throws Exception {
		Map paramsMap =  convertDHSearchCriteria(criteria);
		paramsMap.put("offset", (criteria.getPageNo()-1)*criteria.getPageSize());
		paramsMap.put("limit", criteria.getPageSize());
		List<Dhpo> orderdhList = orderdhdao.select(paramsMap);
		List<DHOrderListVO> DHorderVoList = DHConvert.convertPOList2VOList(orderdhList);

		return  DHorderVoList;
	}
///////////////////////////////////////////////////////////////////////////

//搜索到的记录数目
	@SuppressWarnings({ "rawtypes"})
	@Override
    public Integer countDHs(DHSearchCriteria criteria) throws Exception{
		Map paramsMap=convertDHSearchCriteria(criteria);
		return orderdhdao.count(paramsMap);
	}

///////////////////////////////////////////////////////////////////////////
//兑换详情
	@Override
	public DHOrderDetailVO getDHDetailByDHId(String dhId) throws Exception {
		DHOrderDetailVO dhDetail = new DHOrderDetailVO();
		//订单信息
		Dhpo DHorder = orderdhdao.selectById(dhId);
		if( DHorder == null ){
			LOGGER.error(OrderConstans.ORDER_GET_ORDER_BY_ID_FAILED_MSG + "  dhId：" + dhId);
			throw ZtrBizException.instance(OrderConstans.ORDER_GET_ORDER_BY_ID_FAILED_CODE, OrderConstans.ORDER_GET_ORDER_BY_ID_FAILED_MSG);
		}
		dhDetail.setDhId(DHorder.getDhId());//兑换Id传递
		dhDetail.setMembername(memberServiceImpl.getNickNameByMid(DHorder.getMemberId()));
		dhDetail.setDhphonenumber(DHorder.getDhPhonenum());//电话号传递
		dhDetail.setDhmoney(DHorder.getDhMoney());//兑换金额传递
		dhDetail.setDhcontent(DHorder.getDhContent());//兑换内容传递
		dhDetail.setStatusDesc(AccountCovertStatus.valueOf(DHorder .getDhStatus()).getDescription());
		dhDetail.setContentDesc(AccountConvertContent.valueOf(DHorder .getDhContent()).getDescription());

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(null != DHorder.getPledhTime()){
			dhDetail.setPledhtime(format.format(DHorder.getPledhTime()));//申兑时间传递
		}
		if(null != DHorder.getCondhTime()){
			dhDetail.setConfdhtime(format.format(DHorder.getCondhTime()));//确认兑换时间传递
		}

		return dhDetail;
	}

//////////////////////////////////////////////////////////////////////////////


	//根据id查找整条记录
	public Dhpo search(String dhid) throws Exception{
		Dhpo dh=orderdhdao.selectById(dhid);
		return dh;
	}

	//根据id更新整条记录
	public AjaxResponse update(Dhpo dh) throws Exception{
		AjaxResponse ajaxResponse = AjaxResponse.instance("","");
		try{
			String status=AccountCovertStatus.CONVERTED.name();
			dh.setDhStatus(status);
			Date  currentSystemDate=new Date();
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date CreateDate=	dateFormat.parse( dateFormat.format(currentSystemDate));
			dh.setCondhTime(CreateDate);
			dh.setLastOperator( redisClient.get(OperatorSidHolder.get()));
			orderdhdao.update(dh);     //
			ajaxResponse.setRes_code(ResponseConstBackMemb.MEMB_MODIFY_DH_STATUS_SUCCESS_CODE);
			ajaxResponse.setRes_msg(redisClient.get(OperatorSidHolder.get()));
			return ajaxResponse;
		}catch(Exception e){
			LOGGER.error("服务层更新兑换数据错误", e);
			ajaxResponse.setRes_code(ResponseConstBackMemb.MEMB_MODIFY_DH_STATUS_ERRROR_CODE);
		}
     return ajaxResponse;
	}






}
