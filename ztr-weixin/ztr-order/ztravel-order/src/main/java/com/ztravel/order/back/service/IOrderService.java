package com.ztravel.order.back.service;

import java.util.List;

import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.order.back.criteria.OrderSearchCriteria;
import com.ztravel.order.back.vo.OrderDetailVO;
import com.ztravel.order.back.vo.OrderListVO;
import com.ztravel.reuse.order.entity.OrderDetailWo;

public interface IOrderService {

	/**
     * 确认保存op确认单
     * @param orderDetailWo
     * @return
     * @throws Exception
     */
	  public AjaxResponse confirm(String orderId) throws Exception;

    /**
	 * 符合搜索条件的订单数
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public Integer countOrders(OrderSearchCriteria criteria) throws Exception;

	/**
	 * 订单详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public OrderDetailVO getOrderDetailByOrderId(String orderId) throws Exception ;

	public OrderDetailVO getOriginalOrder(String orderId) throws Exception;

	/**
	 * 搜索订单
	 * @param criteria
	 * @return
	 * @throws Exception
	 */
	public List<OrderListVO> searchOrders(OrderSearchCriteria criteria) throws Exception;

	/**
     *查询某个订单id详情
     * @param orderId
     * @return
     * @throws Exception
     */
    public OrderDetailWo selectConfirmOrderById(String orderId) throws Exception;

    /**
     * 查询订单的原认单缓存JSON
     * @param orderId
     * @return
     * @throws Exception
     */
    public OrderDetailWo selectOriginalOrderById(String orderId) throws Exception;

    /**
     * 查询订单的编辑行程确认单缓存JSON
     * @param orderId
     * @return
     * @throws Exception
     */
    public OrderDetailWo selectTempOrderById(String orderId) throws Exception;

    /**
     * 查看行程确认单发送邮件
     * @param orderDetailWo
     * @return
     * @throws Exception
     */
    public AjaxResponse sendEmailByTravelConfirm(String orderId, String email) throws Exception;

    Boolean updateTempOrder(OrderDetailWo orderDetailWo, String orderId) throws Exception;


	/**
	 * OP订单确认
	 * @param orderId : 订单ID
	 * */
	AjaxResponse confirmOrder(String orderId) throws Exception;

	/**
	 *取消订单操作
	 *@param orderId : 订单ID
	 * */
	AjaxResponse cancleOrder(String orderId) throws Exception;
	/**
	 * 发放礼盒操作
	 * @param orderId:订单ID
	 * @param sendContent : 发放须知(寄送礼盒后的快递编号及相关提示信息)
	 * */
	AjaxResponse sendGiftBox(String orderId,String sendContent) throws Exception;
	/**
	 * 出行通知
	 *@param orderId : 订单ID
	 *@param noticeContent : 出行通知内容
	 * */
	AjaxResponse sendOutNotice(String orderId,String noticeContent) throws Exception;

	/**
	 * 更新订单状态
	 * @param orderId : 订单ID
	 * @param status : 更新后的订单前端状态
	 * */
	Boolean updateStatus(String orderId,String frontStatus,String backStatus) throws Exception;

	/**
	 * 更新订单客户端状态
	 * @param orderId : 订单ID
	 * @param status : 更新后的订单前端状态
	 * */
	Boolean updateFrontStatus(String orderId,String status) throws Exception;
	/**
	 * 更新订单后端状态
	 * @param orderId : 订单ID
	 * @param status : 更新后的订单后端状态
	 * */
	Boolean updateBackStatus(String orderId,String status) throws Exception;

	AjaxResponse refundOpConfirmOrder(String orderId) throws Exception;

}
