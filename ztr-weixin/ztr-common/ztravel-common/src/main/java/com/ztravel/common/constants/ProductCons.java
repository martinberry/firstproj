package com.ztravel.common.constants;

/**
 * @author junhui.xu
 * 处理AJAX的返回值
 *
 * 规范
 * 1.第1位 S-->成功  F-->失败
 * 2.第2位 F-->C端   O-->后台
 * 3.第3,4,5,6位 表示模块
 * 4.最后4位   表示状态码
 *
 * 如--> SF_MEMB_0001 表示 C端用户模块某一业务成功
 * */
public class ProductCons {
	/**
	 * ------------------××××××后台产品×××××××----------------------
	 * */
	public static final String PROD_AJAX_SUCCESS_CODE = "success";
	public static final String PROD_AJAX_ERROR_CODE = "error";

	public static final String PROD_QUERY_ERROR_CODE = "FO_PROD_1001";
	public static final String PROD_QUERY_ERROR_MSG = "查询产品失败";

	public static final String PROD_UPDATE_SUCCESS_CODE = "FO_PROD_1002";
	public static final String PROD_UPDATE_SUCCESS_MSG = "更新数据库成功";

	public static final String PROD_UPDATE_FAIL_CODE = "FO_PROD_1003";
	public static final String PROD_UPDATE_FAIL_MSG = "更新数据库失败";

	public static final String PROD_UPDATE_MULT_ERROR_CODE = "FO_PROD_1004";
	public static final String PROD_UPDATE_MULT_ERROR_MSG = "更新了多条数据";

	/*图片*/
	public static final String SAFE_IMAGE_EXT_NAME = "jpg,png,jpeg" ;
	public static final String SAFE_FILE_EXT_NAME = "doc,docx,xls,xlsx,zip,rar,pdf,jpg,png,txt";

	public static final String IMG_SIZE_ERROR_CODE = "FO_IMAG_100１";
	public static final String IMG_SIZE_ERROR_MSG = "图像必须小于2M";

	public static final String IMG_EXTNAME_ERROR_CODE = "FO_IMAG_1002";
	public static final String IMG_EXTNAME_ERROR_MSG = "图片格式不合法，请上传png,jpg,jpeg格式图片";

	public static final String IMG_SERVER_ERROR_CODE = "FO_IMAG_1003";
	public static final String IMG_SERVER_ERROR_MSG = "图片服务器出错";

	/*文件*/
    public static final String FILE_SIZE_ERROR_CODE = "FO_FILE_1001";
    public static final String FILE_SIZE_ERROR_MSG = "文件必须小于2M";

	public static final String FILE_EXTNAME_ERROR_CODE = "FO_FILE_1002";
    public static final String FILE_EXTNAME_ERROR_MSG = "文件格式不合法，请上传zip,rar,pdf,jpg,png,xls,xlsx,txt,doc,docx格式文件";

    public static final String FILE_SERVER_ERROR_CODE = "FO_FILE_1003";
    public static final String FILE_SERVER_ERROR_MSG = "文件服务器出错";


	/**
	 * ------------------××××××后台酒店资源×××××××----------------------
	 * */
	public static final String PROD_HOTEL_DELETE_SUCCESS_CODE = "SO_PROD_1101";
	public static final String PROD_HOTEL_DELETE_SUCCESS_MSG = "删除酒店成功";

	public static final String PROD_HOTEL_DELETE_ERROR_CODE = "FO_PROD_1101";
	public static final String PROD_HOTEL_DELETE_ERROR_MSG = "删除酒店失败";

	public static final String PROD_HOTEL_SEARCH_ERROR_CODE = "FO_PROD_1102";
	public static final String PROD_HOTEL_SEARCH_ERROR_MSG = "查询酒店失败";

	public static final String PROD_HOTEL_CONTAINED_BY_PRODUCT_DELETE_ERROR_CODE = "FO_PROD_1103";
	public static final String PROD_HOTEL_CONTAINED_BY_PRODUCT_DELETE_ERROR_MSG = "该酒店已被添加到产品中，不可删除";

	/**
	 * ------------------××××××后台产品列表×××××××----------------------
	 * */
	public static final String PROD_PRODUCT_SEARCH_ERROR_CODE = "FO_PROD_PRODUCT_1102";
	public static final String PROD_PRODUCT_SEARCH_ERROR_MSG = "查询产品失败";

	public static final String PROD_PRODUCT_DEL_SUCCESS_CODE = "SO_PROD_PRODUCT_1101";
	public static final String PROD_PRODUCT_DEL_SUCCESS_MSG = "产品删除成功";

	public static final String PROD_PRODUCT_DEL_ERROR_CODE = "FO_PROD_PRODUCT_1101";
	public static final String PROD_PRODUCT_DEL_ERROR_MSG = "产品删除失败";

	public static final String PROD_PRODUCT_ONLINE_SUCCESS_CODE = "SO_PROD_PRODUCT_1103";
	public static final String PROD_PRODUCT_ONLINE_SUCCESS_MSG = "产品上线成功";

	public static final String PROD_PRODUCT_ONLINE_ERROR_CODE = "FO_PROD_PRODUCT_1103";
	public static final String PROD_PRODUCT_ONLINE_ERROR_MSG = "产品上线失败";

	public static final String PROD_PRODUCT_OFFLINE_SUCCESS_CODE = "SO_PROD_PRODUCT_1104";
	public static final String PROD_PRODUCT_OFFLINE_SUCCESS_MSG = "产品下线成功";

	public static final String PROD_PRODUCT_OFFLINE_ERROR_CODE = "FO_PROD_PRODUCT_1104";
	public static final String PROD_PRODUCT_OFFLINE_ERROR_MSG = "产品下线失败";

	public static final String PROD_PRODUCT_CLOSE_SUCCESS_CODE = "SO_PROD_PRODUCT_1105";
	public static final String PROD_PRODUCT_CLOSE_SUCCESS_MSG = "产品关闭成功";

	public static final String PROD_PRODUCT_CLOSE_ERROR_CODE = "FO_PROD_PRODUCT_1105";
	public static final String PROD_PRODUCT_CLOSE_ERROR_MSG = "产品关闭失败";


}
