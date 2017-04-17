package com.ztravel.paygate.web.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ztravel.paygate.core.po.gen.Alipay;
import com.ztravel.paygate.core.po.gen.AlipayRefund;

/**
 * 支付宝
 * 
 * @author dingguangxian
 * 
 */
public interface IAlipayService {

	/**
	 * 创建新的实体实例,返回的实例中已经附加有alipay_id和createTime信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public Alipay createEntity() throws Exception;

	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Alipay findById(String id) throws SQLException;

	/**
	 * 新增一条数据到DB
	 * 
	 * @param entity
	 * @return
	 * @throws SQLException
	 */
	public Alipay insertSelective(Alipay entity) throws SQLException;

	/**
	 * 由记录的ID更新数据
	 * 
	 * @param telpay
	 * @return
	 * @throws SQLException
	 */
	public void updateSelectiveById(Alipay entity) throws SQLException;

	/**
	 * 根据订单号查询数据,如果查询结果不唯一,只会返回签名结果为{@link PaygateError#SUCCESS#code()} (即支付接口签名成功)的订单,如果多条返回结果中均签名失败,会抛出异常. 在已知订单号和流水号的情况下,建议优先使用
	 * {@link #searchByOrderNumAndTraceNum(String, String)}方法
	 * 
	 * @param orderNum
	 * @return
	 * @throws SQLException
	 */
	public Alipay searchByOrderNum(String orderNum) throws SQLException;

	/**
	 * 检索支付成功的订单
	 * 
	 * @param orderNum
	 * @return
	 * @throws SQLException
	 */
	public Alipay searchPaySuccessRecord(String orderNum) throws SQLException;

	/**
	 * 根据订单号和流水号检索记录
	 * 
	 * @param orderNum
	 *            订单号
	 * @param traceNum
	 *            流水号
	 * @return
	 * @throws SQLException
	 */
	public Alipay searchByOrderNumAndTraceNum(String orderNum, String traceNum) throws SQLException;

	// ////////////////退款/////////////////
	/**
	 * 创建新的退款实体实例,返回的实例中已经附加有chinapnrRefundId和createTime信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public AlipayRefund createRefundEntity() throws Exception;

	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public AlipayRefund findByRefundId(String refundId) throws SQLException;

	/**
	 * 根据订单号查询数据,如果查询结果不唯一,只会返回签名结果为{@link PaygateError#SUCCESS#code()} (即退单请求成功)的订单,如果多条返回结果中均签名失败,返回最后一次请求记录.
	 * 
	 * @param refundNum
	 * @return
	 * @throws SQLException
	 */
	public AlipayRefund searchByRefundNum(String refundNum) throws SQLException;

	/**
	 * 检索退单成功的记录
	 * 
	 * @param refundNum
	 * @return
	 * @throws SQLException
	 */
	public AlipayRefund searchRefundSuccessRecord(String refundNum) throws SQLException;

	/**
	 * 新增一条退款记录到DB
	 * 
	 * @param entity
	 * @return
	 * @throws SQLException
	 */
	public AlipayRefund insertSelective(AlipayRefund entity) throws SQLException;

	/**
	 * 由退款记录的ID更新数据
	 * 
	 * @param telpay
	 * @return
	 * @throws SQLException
	 */
	public void updateSelectiveById(AlipayRefund entity) throws SQLException;
	
	/**
	 * 记录退款通知信息
	 * @param alipayRefundId
	 * @param responseData
	 */
	public void recordRefundNotify(String alipayRefundId,
			Map<String, String> responseData) throws Exception;
	
	/**
	 * 查询未处理的支付回调
	 */
	public List<Alipay> searchUnProceedPayRecord() throws Exception;
	
	/**
	 * 查询未处理的退款回调
	 */
	List<AlipayRefund> searchUnProceedRefundRecord() throws Exception ;

}
