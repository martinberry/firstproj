package com.ztravel.member.opera.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.member.opera.entity.MemberSearchCriteria;

public class ValidationUtils {
	private static final Logger LOGGER = RequestIdentityLogger.getLogger(ValidationUtils.class);

	private static final String MOBILE_REG = "1\\d{10}";
	private static final String EMAIL_REG = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	private static final String REAL_NAME_REG1 = "^[a-zA-Z]+$";
	private static final String REAL_NAME_REG2 = "^[\u4E00-\u9FA5]+$";
	private static final String NICK_NAME_REG = "^([0-9a-zA-Z\u002A\u4E00-\u9FA5]+)$";
	private static final String MEMBER_ID_REG = "\\d{8}";
	private static final String PURCHASE_AMOUNT_REG = "\\d\\.{0,1}\\d{0,2}";

	public static boolean isMemberSearchCriteriaValid(MemberSearchCriteria criteria){
		if( StringUtils.isNotBlank(criteria.getMobile()) ){
			if( !ValidationUtils.isMobileValid(criteria.getMobile()) ){
				LOGGER.error("手机号格式错误");
				return false;
			}
		}
		if( StringUtils.isNotBlank(criteria.getEmail()) ){
			if( !ValidationUtils.isEmailValid(criteria.getEmail()) ){
				LOGGER.error("邮箱格式错误");
				return false;
			}
		}
		if( StringUtils.isNotBlank(criteria.getRealName()) ){
			if( !ValidationUtils.isRealNameValid(criteria.getRealName()) ){
				LOGGER.error("真实姓名格式错误");
				return false;
			}
		}
		if( StringUtils.isNotBlank(criteria.getNickName()) ){
			if( !ValidationUtils.isNickNameValid(criteria.getNickName()) ){
				LOGGER.error("昵称格式错误");
				return false;
			}
		}
		if( StringUtils.isNotBlank(criteria.getMemberId()) ){
			if( !ValidationUtils.isMemberIdValid(criteria.getMemberId()) ){
				LOGGER.error("会员ID格式错误");
				return false;
			}
		}
		if( StringUtils.isNotBlank(criteria.getAmountFrom()) && StringUtils.isNotBlank(criteria.getAmountTo()) ){
			if( !ValidationUtils.isPurAmountValid(criteria.getAmountFrom()) || !ValidationUtils.isPurAmountValid(criteria.getAmountTo()) ){
				LOGGER.error("消费金额格式错误");
				return false;
			}
		}
		if( StringUtils.isNotBlank(criteria.getRegisterFromDate()) && StringUtils.isNotBlank(criteria.getRegisterToDate()) ){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				Date fromDate = format.parse(criteria.getRegisterFromDate());
				Date toDate = format.parse(criteria.getRegisterToDate());
				if( fromDate.compareTo(toDate) > 0 ){
					LOGGER.error("注册日期前后不符");
					return false;
				}
			} catch (ParseException e) {
				LOGGER.error("日期时间解析错误");
			}
		}
		return true;
	 }

	 public static boolean isMobileValid(String mobile){
		 if( !Pattern.compile(MOBILE_REG).matcher(mobile).matches() )
			 return false;
		else
			return true;
	 }

	 public static boolean isEmailValid(String email){
		 if( !Pattern.compile(EMAIL_REG).matcher(email).matches() )
			 return false;
		 else
			 return true;
	 }

	 public static boolean isRealNameValid(String realName){
		 if( !Pattern.compile(REAL_NAME_REG1).matcher(realName).matches() && !Pattern.compile(REAL_NAME_REG2).matcher(realName).matches() )
			 return false;
		 else
			 return true;
	 }

	 public static boolean isNickNameValid(String nickName){
		 if( !Pattern.compile(NICK_NAME_REG).matcher(nickName).matches() )
			 return false;
		 else
			 return true;
	 }

	 public static boolean isMemberIdValid(String memberId){
		 if( !Pattern.compile(MEMBER_ID_REG).matcher(memberId).matches() )
			 return false;
		 else
			 return true;
	 }

	 public static boolean isPurAmountValid(String purAmount){
		 if( !Pattern.compile(PURCHASE_AMOUNT_REG).matcher(purAmount).matches() )
			 return false;
		 else
			 return true;
	 }
}
