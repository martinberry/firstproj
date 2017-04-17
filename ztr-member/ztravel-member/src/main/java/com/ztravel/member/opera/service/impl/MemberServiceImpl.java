package com.ztravel.member.opera.service.impl;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.travelzen.framework.core.util.StringUtil;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.constants.ResponseConstBackMemb;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.member.dao.MemberDao;
import com.ztravel.member.opera.converter.OperaMemberConvertor;
import com.ztravel.member.opera.entity.MemberSearchCriteria;
import com.ztravel.member.opera.enums.LatestLoginPeriodEnum;
import com.ztravel.member.po.Member;
import com.ztravel.member.opera.service.IMemberService;
import com.ztravel.member.opera.vo.MemberVO;
import com.ztravel.reuse.member.service.IMemberReuseService;

/**
 * @author
 *
 */
@Service
public class MemberServiceImpl implements IMemberService {

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(MemberServiceImpl.class);

	@Resource
	private MemberDao memberDao;
	
	@Resource
	private IMemberReuseService memberReuseService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<MemberVO> searchMembers(MemberSearchCriteria criteria) throws Exception {
		Map searchParams = convertMemberSearchCriteria(criteria);
		searchParams.put("offset", (criteria.getPageNo()-1)*criteria.getPageSize());
		searchParams.put("limit", criteria.getPageSize());

		List<Member> memList = memberDao.select(searchParams);
		if( memList == null ){
			throw ZtrBizException.instance(ResponseConstBackMemb.MEMB_SEARCH_MEMBER_ERROR_CODE, ResponseConstBackMemb.MEMB_SEARCH_MEMBER_ERROR_MSG);
		}
		List<MemberVO> memVOList = OperaMemberConvertor.convertListPOtoListVO(memList);
		return memVOList;
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public Integer countMembers(MemberSearchCriteria criteria) throws Exception {
		Map searchParams = convertMemberSearchCriteria(criteria);
		return memberDao.count(searchParams);
	}

	/**
	 * 会员搜索条件MemberSearchCriteria转换成Map
	 * @param MemberSearchCriteria
	 * @return Map
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map convertMemberSearchCriteria(MemberSearchCriteria criteria){
		Map searchParams = new HashMap();
		//真实姓名，昵称模糊查询
		if( StringUtils.isNotBlank(criteria.getRealName()) ){
			searchParams.put("realName", "%"+criteria.getRealName()+"%");
		}
		if( StringUtils.isNotBlank(criteria.getNickName()) ){
			searchParams.put("nickName", "%"+criteria.getNickName()+"%");
		}
		searchParams.put("mobile", criteria.getMobile());
		searchParams.put("memberId", criteria.getMemberId());
		searchParams.put("email", criteria.getEmail());

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if( StringUtils.isNotBlank(criteria.getRegisterFromDate()) ){
				searchParams.put("fromDate", format.parse(criteria.getRegisterFromDate()+" 00:00:00"));
			}
			if( StringUtils.isNotBlank(criteria.getRegisterToDate()) ){
				searchParams.put("toDate", format.parse(criteria.getRegisterToDate()+" 23:59:59"));
			}
		} catch (ParseException e) {
			LOGGER.error(e.getMessage(), e);
		}
		searchParams.put("province", criteria.getProvince());
		searchParams.put("city", criteria.getCity());

		if(criteria.getStatus() != null){
			switch(criteria.getStatus()){
			case "normal":
				searchParams.put("isActive", true);
				break;
			case "suspend":
				searchParams.put("isActive", false);
				break;
			case "all":
				searchParams.put("isActive", null);
				break;
			}
		}else{
			searchParams.put("isActive", null);
		}

		if( StringUtils.isNotBlank(criteria.getLatestLoginPeriod()) ){
			LatestLoginPeriodEnum periodEnum = LatestLoginPeriodEnum.valueOf(criteria.getLatestLoginPeriod());
			Date lastLoginTime = convertLastLoginTime(periodEnum);
			searchParams.put("lastLoginDate", lastLoginTime);
		}
		return searchParams;
	}

	/**
	 * 根据选择时间段，计算出lastLoginTime(已测试)
	 * 一星期，一个月，三个月，六个月，一年
	 * @param timePeriod
	 * @return
	 */
	private  static Date convertLastLoginTime(LatestLoginPeriodEnum periodEnum) {
		DateTime today = new DateTime();
		DateTime result = today;
		switch (periodEnum) {
		case ONE_WEEK:
			result = result.minusDays(7);
			break;
		case ONE_MONTH:
			result = result.minusMonths(1);
			break;
		case THREE_MONTH:
			result = result.minusMonths(3);
			break;
		case SIX_MONTH:
			result = result.minusMonths(6);
			break;
		case ONE_YEAR:
			result = result.minusYears(1);
			break;
		default:
			break;
		}
		return result.toDate();
	}

	@Override
	public MemberVO searchMemberDetailById(String id) throws Exception {
		Member mem = memberDao.selectById(id);
		if( mem == null ){
			throw ZtrBizException.instance(ResponseConstBackMemb.MEMB_GET_MEMBER_DETAIL_ERROR_CODE, ResponseConstBackMemb.MEMB_GET_MEMBER_DETAIL_ERROR_MSG);
		}
		MemberVO memVO = OperaMemberConvertor.convertPOtoVO(mem);
		return memVO;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void modifyMemberStatus(List<String> idList, boolean isActive) throws SQLException {
		Map params = new HashMap();
		params.put("isActive", isActive);
		params.put("idList", idList);
		int nRow = memberDao.updateActiveByIds(params);
		//检查数据库修改行数
		if( nRow != idList.size() ){
			LOGGER.debug(String.format("数据库实际修改行数与预期不符：预期修改%d行，实际修改%d行", idList.size(), nRow));
		}
	}

	@Override
	public String getNickNameByMid(String mid) {
		return memberReuseService.getNickNameByMid(mid) ;
	}

    @Override
    public String getWxNickNameById(String id) {
        Member member = memberDao.selectById(id);
        if (member != null && !StringUtil.isEmpty(member.getOpenId())) {
            String nickName = memberDao.selectWxNickNameByOpenId(member.getOpenId());
            return nickName;
        }
        return null;
    }

}
