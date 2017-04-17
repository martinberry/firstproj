package com.ztravel.product.front.book.validate;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.util.Assert;

import com.ztravel.common.constants.ProductBookConstans;
import com.ztravel.common.entity.ContactorInfo;
import com.ztravel.common.entity.PassengerInfo;
import com.ztravel.common.entity.ProductBookItem;
import com.ztravel.common.enums.CredentialType;
import com.ztravel.common.enums.DestinationType;
import com.ztravel.common.enums.PieceType;
import com.ztravel.common.exception.ZtrBizException;

public class ProductBookValidate {

	private static final String wholeEnNameReg = "^[a-zA-z]{1,61}$";
	private static final String wholeChNameReg = "^[\\u4E00-\\u9FA5]{1,41}$";

	public static void validate(ProductBookItem item)throws Exception{
		Assert.hasText(item.getProductId(),"产品ID为空");
		Assert.hasText(item.getBookDate(),"产品预订日期为空");
		if(!item.getNature().equals(PieceType.VISA.name())){
			Assert.isTrue(item.getAdaultNum() != null && item.getAdaultNum() > 0,"成人数不能为空且成人数必需大于0");
			passengerValidate(item.getPassengerList(), item.getIsDomestic());
		}
		contactorValidate(item.getContactorInfo());
		Assert.isTrue(!(item.getPayAmount().longValue()==0l),"订单金额总价不能为0.00");
	}
	
	public static void passengerValidate(List<PassengerInfo> passengers,String desType)throws Exception{
		Assert.isTrue(!CollectionUtils.isEmpty(passengers),"乘客信息不能为空");
		for(PassengerInfo passenger : passengers){
			Assert.hasText(passenger.getCredentialNum(),"乘客{"+passenger.getCredentialType()+"}号必填");
			Assert.hasText(passenger.getGender(),"乘客性别为空");
			String passengerName = passenger.getFirstName() + passenger.getLastName();
			if(!passengerName.matches(wholeEnNameReg) && !passengerName.matches(wholeChNameReg)){
				throw ZtrBizException.instance(ProductBookConstans.PASSENGER_NAME_ERROE_ORDER_FAILURE, "旅客姓名不正确");
			}
			if(desType.equals(DestinationType.DOMESTIC.getName())){//国内
				Assert.hasText(passenger.getPassengerName(),"乘客姓名为空");
				Assert.hasText(passenger.getType(),"乘客类型为空");
				if(passenger.getCredentialType().equals(CredentialType.GANGAOPASS.name()) || passenger.getCredentialType().equals(CredentialType.PASSPORT.name())){
					Assert.hasText(passenger.getBirthday(),"通行证或护照的出生日期为空");
				}
			}else{//国际
				Assert.isTrue(StringUtils.isNotBlank(passenger.getFirstName()) && StringUtils.isNotBlank(passenger.getLastName()),"乘客姓或名为空");
				Assert.hasText(passenger.getCredentialsDeadLine(),"国际产品乘客证件有效期为空");
				Assert.hasText(passenger.getCountry(),"国际产品乘客国籍为空");
				Assert.hasText(passenger.getBirthday(),"证件类型为{"+passenger.getCredentialType()+"}，出生日期为空");
			}
		}
	}
	
	public static void contactorValidate(ContactorInfo contactor)throws Exception{
		Assert.isTrue(contactor != null,"联系人信息为空");
		Assert.hasText(contactor.getName(),"联系人姓名为空");
		Assert.hasText(contactor.getPhone(),"联系人手机号为空");
		Assert.hasText(contactor.getEmail(),"联系人邮箱为空");
		Assert.hasText(contactor.getProvince(),"联系人省份信息为空");
		Assert.hasText(contactor.getCity(),"联系人市信息为空");
		Assert.hasText(contactor.getAddressDetail(),"联系人具体街道地址为空");
	}
	
	public static void passengerValidate(List<PassengerInfo> passengers)throws Exception{
	}
	

}
