package com.ztravel.product.back.hotel.validation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.product.back.hotel.wo.HotelEntityWo;

public class HotelEditValidate {

	private static Logger logger = RequestIdentityLogger.getLogger(HotelEditValidate.class);

	private static final String hotelNameCnReg = "^[a-zA-Z0-9 | \\u4E00-\\u9FA5|.|（|）|【|】|——|,|，|、|。|\\/|\\\\|:|;|：|；|“|”|’| ]{4,60}$";
	private static final String hotelNameEnReg = "^[a-zA-Z0-9|\\.|（|）|【|】|——|,|，|、|。|\\/|\\\\|:|;|：|；|“|”|’| ]{4,60}$";
	private static final String phoneReg = "^[\\+|0-9][0-9|\\+|-]{0,18}[0-9]$";
	private static final String lonAndlatReg = "^[0-9]{1,3}\\.[0-9]{1,3}$";
	private static final String addressReg = "^[a-zA-Z0-9|\\u4E00-\\u9FA5|\\.|（|）|——|,|，|、|。|\\/|\\\\|:|;|：|；|“|”|’| ]{4,60}$";
	private static final String advantageReg = "^[a-zA-Z0-9|\\u4E00-\\u9FA5|\\.|（|）|【|】|——|,|，|、|。|\\/|\\\\|:|;|：|；|“|”|’| ]{2,100}$";
	private static final String describeReg = "^[a-zA-Z0-9|\\u4E00-\\u9FA5|\\.|（|）|【|】|——|,|，|、|。|\\/|\\\\|:|;|：|；|“|”|’| |\\n]{10,500}$";
	private static final String facilitiesReg = "^[a-zA-Z0-9|\\u4E00-\\u9FA5|\\.|（|）|【|】|——|,|，|、|。|\\/|\\\\|:|;|：|；|“|”|’|！"
			+ "|·|@|#|￥|%|……|&|\\*|\\+|=|\\-|\\$|{|}|\\[|\\]|<|>|《|》|\\?| |\\n|~]{2,500}$";
	private static final String notesReg = "^[a-zA-Z0-9|\\u4E00-\\u9FA5|\\.|（|）|【|】|——|,|，|、|。|\\/|\\\\|:|;|：|；|“|”|’|！"
			+ "|·|@|#|￥|%|……|&|\\*|\\+|=|\\-|\\$|{|}|\\[|\\]|<|>|《|》|\\?| |\\n|~]{2,500}$";

	public static boolean validateHotelEntity(HotelEntityWo hotelEntityWo) {

	String hotelNameCn= hotelEntityWo.getHotelNameCn();
	if ( StringUtils.isBlank(hotelNameCn) || !testNameCn(hotelNameCn)) {
//		logger.error("仅可输入4-60位字符数以内的中文、字母、数字及常用字符");
		logger.error("中文名称仅可输入4-60位字符");
		return false;
	}
	String phone= hotelEntityWo.getPhone();
	if ( StringUtils.isNotBlank(phone) && !testPhone(phone)) {
		logger.error("酒店电话请输入12位以内字符（数字和“-”）");
		return false;
	}
	String hotelNameEn= hotelEntityWo.getHotelNameEn();
	if ( StringUtils.isNotBlank(hotelNameEn) && !testNameEn(hotelNameEn)) {
//		logger.error("仅可输入4-60位字符数以内的字母、数字及常用字符");
		logger.error("英文名称仅可输入4-60位字符");
		return false;
	}
	String lon= hotelEntityWo.getLon();
	if ( StringUtils.isNotBlank(lon) && !testLonAndLat(lon)) {
		logger.error("酒店坐标格式输入有误");
		return false;
	}
	String lat= hotelEntityWo.getLat();
	if ( StringUtils.isNotBlank(lat) && !testLonAndLat(lat)) {
		logger.error("酒店坐标格式输入有误");
		return false;
	}
	String address= hotelEntityWo.getAddress();
	if ( StringUtils.isNotBlank(address) && !testAddress(address)) {
//		logger.error("仅可输入4-60位字符数以内的中文、字母、数字及常用字符");
		logger.error("酒店地址仅可输入4-60位字符");
		return false;
	}
	String advantage= hotelEntityWo.getAdvantage();
	if ( StringUtils.isNotBlank(advantage) && !testAdvantage(advantage)) {
//		logger.error("仅可输入2-100位字符数以内的中文、字母、数字及常用字符");
		logger.error("卖点仅可输入2-100位字符");
		return false;
	}
	String describe= hotelEntityWo.getDescribe();
	if ( StringUtils.isNotBlank(describe) && !testDescribe(describe)) {
//		logger.error("仅可输入10-500位字符数以内的中文、字母、数字及常用字符");
		logger.error("酒店描述仅可输入10-500位字符");
		return false;
	}
	String compFacilities= hotelEntityWo.getCompFacilities();
	if ( StringUtils.isNotBlank(compFacilities) && !testFacilities(compFacilities)) {
//		logger.error("请输入2-500位字符数以内的中文、字母、数字及常用字符");
		logger.error("综合设施请输入2-500位字符");
		return false;
	}
	String cateringFacilities= hotelEntityWo.getCateringFacilities();
	if ( StringUtils.isNotBlank(cateringFacilities) && !testFacilities(cateringFacilities)) {
//		logger.error("请输入2-500位字符数以内的中文、字母、数字及常用字符");
		logger.error("餐饮设施请输入2-500位字符");
		return false;
	}
	String networkFacilities= hotelEntityWo.getNetworkFacilities();
	if ( StringUtils.isNotBlank(networkFacilities) && !testFacilities(networkFacilities)) {
//		logger.error("请输入2-500位字符数以内的中文、字母、数字及常用字符");
		logger.error("网络设施请输入2-500位字符");
		return false;
	}
	String activityFacilities= hotelEntityWo.getActivityFacilities();
	if ( StringUtils.isNotBlank(activityFacilities) && !testFacilities(activityFacilities)) {
//		logger.error("请输入2-500位字符数以内的中文、字母、数字及常用字符");
		logger.error("活动设施请输入2-500位字符");
		return false;
	}
	String serviceFacilities= hotelEntityWo.getServiceFacilities();
	if ( StringUtils.isNotBlank(serviceFacilities) && !testFacilities(serviceFacilities)) {
//		logger.error("请输入2-500位字符数以内的中文、字母、数字及常用字符");
		logger.error("服务项目请输入2-500位字符");
		return false;
	}
	String notes= hotelEntityWo.getNotes();
	if ( StringUtils.isNotBlank(notes) && !testNotes(notes)) {
//		logger.error("请输入2-500位字符数以内的中文、字母、数字及常用字符");
		logger.error("入住须知请输入2-500位字符");
		return false;
	}
	return true;
}

	public static boolean validateIsComplete(HotelEntityWo hotelEntityWo) {

	if(StringUtils.isBlank(hotelEntityWo.getHotelNameCn())||StringUtils.isBlank(hotelEntityWo.getAddress())||StringUtils.isBlank(hotelEntityWo.getContinent())
			||StringUtils.isBlank(hotelEntityWo.getAdvantage())||StringUtils.isBlank(hotelEntityWo.getDescribe())
			||StringUtils.isBlank(hotelEntityWo.getNation())||StringUtils.isBlank(hotelEntityWo.getCity())){
		logger.error("中文名称、目的地、酒店地址、酒店类型、酒店星级、卖点、酒店描述完成后才能发布");
		return false;
	}

	if( hotelEntityWo.getPictureIds()==null || hotelEntityWo.getPictureIds().size() < 5){
		logger.error("至少5张高清（XXX*XXX）JPG/PNG图");
		return false;
	}

	return true;
	}

//	public static boolean testNameCn(String str){
//		return str.matches(hotelNameCnReg);
//	}
	public static boolean testNameCn(String str){
		return str.length() >= 4 && str.length() <= 60 ? true : false;
	}

	public static boolean testPhone(String str){
		return str.matches(phoneReg);
	}

//	public static boolean testNameEn(String str){
//		return str.matches(hotelNameEnReg);
//	}
	public static boolean testNameEn(String str){
		return str.length() >= 4 && str.length() <= 60 ? true : false;
	}

	public static boolean testLonAndLat(String str){
		return str.matches(lonAndlatReg);
	}

//	public static boolean testAddress(String str){
//		return str.matches(addressReg);
//	}
	public static boolean testAddress(String str){
		return str.length() >= 4 && str.length() <= 60 ? true : false;
	}

//	public static boolean testAdvantage(String str){
//		return str.matches(advantageReg);
//	}
	public static boolean testAdvantage(String str){
		return str.length() >= 2 && str.length() <= 100 ? true : false;
	}

//	public static boolean testDescribe(String str){
//		return str.matches(describeReg);
//	}
	public static boolean testDescribe(String str){
		return str.length() >= 10 && str.length() <= 500 ? true : false;
	}

//	public static boolean testFacilities(String str){
//		return str.matches(facilitiesReg);
//	}
	public static boolean testFacilities(String str){
		return str.length() >= 2 && str.length() <= 500 ? true : false;
	}

//	public static boolean testNotes(String str){
//		return str.matches(notesReg);
//	}
	public static boolean testNotes(String str){
		return str.length() >= 2 && str.length() <= 500 ? true : false;
	}

	public static void main(String args[]){
//		String str="·~！@#￥%……&*（）——+=- ${}[]【】<>《》,.，、。?/、\\:;：；“”’";
//		System.out.println(testNotes(str));
		String phoneReg1 = "^[\\+|0-9][0-9|\\+|-]{0,18}[0-9]$";
		System.out.println("+121+2313-121".matches(phoneReg));
	}


}
