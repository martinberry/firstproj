<%@page import="com.ztravel.paygate.core.util.BeanMapUtil"%>
<%@page import="com.ztravel.paygate.core.util.PaygateEncryptUtil"%>
<%@page import="com.travelzen.framework.util.TZBeanUtils"%>
<%@page import="com.ztravel.paygate.web.dto.request.RequestRefundBean"%>
<%@page import="com.travelzen.framework.core.util.RandomUtil"%>
<%@page import="com.travelzen.framework.core.time.DateTimeUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("utf-8");
response.setCharacterEncoding("utf-8");
	RequestRefundBean refundBean = new RequestRefundBean();
	TZBeanUtils.setProperties(refundBean, request);
	String sign = PaygateEncryptUtil.getSignStr(BeanMapUtil.mapBean(refundBean), request.getParameter("signkey"));
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<h1>交易退款示例(已实现支付宝和汇付天下)</h1>
	<body>
		<form action="<%=request.getContextPath()%>/paygate/reqRefund" method="POST">
			<table width="90%">
				<tr>
					<td width="20%">
						终端ID(clientId)：
					</td>
					<td>
						<input type="text" name="clientId" value="<%=refundBean.getClientId()%>">*
					</td>
				</tr>
				<tr>
					<td width="20%">
						退款标识号(refundNum)：
					</td>
					<td>
						<input type="text" name="refundNum" value="<%= refundBean.getRefundNum()%>">*
					</td>
				</tr>
				<tr>
					<td width="20%">
						退款时间(refundTime)：
					</td>
					<td>
						<input type="text" name="refundTime" value="<%= refundBean.getRefundTime()%>">*
					</td>
				</tr>
				<tr>
					<td width="20%">
						原订单号(orderNum)：
					</td>
					<td>
						<input type="text" name="orderNum" value="<%= refundBean.getOrderNum() %>">*
					</td>
				</tr>
				<tr>
					<td width="20%">
						原交易号(traceNum)：
					</td>
					<td>
						<input type="text" name="traceNum" value="<%= refundBean.getTraceNum() %>">*
					</td>
				</tr>
				<tr>
					<td width="20%">
						原交易金额(transAmount)：
					</td>
					<td>
						<input type="text" name="transAmount" value="<%= refundBean.getTransAmount()%>">*
					</td>
				</tr>
				<tr>
					<td width="20%">
						本次退款金额(refundAmount)：
					</td>
					<td>
						<input type="text" name="refundAmount" value="<%= refundBean.getRefundAmount()%>">*
					</td>
				</tr>
				<tr>
					<td width="20%">
						退款备注信息(comment)：
					</td>
					<td>
						<input type="text" name="comment" value="<%= refundBean.getComment()%>">
					</td>
				</tr>
				<tr>
					<td width="20%">
						退分润信息(refundProfitDetails)：
					</td>
					<td>
						<input type="text" style="width:360px" name="refundProfitDetails" value="<%=refundBean.getRefundProfitDetails()%>">(退分润格式:分润方账号|退分润金额|备注)
					</td>
				</tr>
				<tr>
					<td width="20%">
						支付平台(gateType)：
					</td>
					<td>
						 <input type="text" style="width:120;" name="gateType" readonly="readonly" value="<%=refundBean.getGateType()%>">*
					</td>
				</tr>
				<tr>
					<td width="20%">
						签名串(sign)：
					</td>
					<td>
						 <input style="width:280;" type="text" style="width:120;" name="sign" readonly="readonly" value="<%=sign%>">*
					</td>
				</tr>
				<tr>
					<td width="20%">
					</td>
					<td>
						<input type="submit" value="  提  交    ">
					</td>
				</tr>
			</table>	
		</form>
	</body>
</html>
