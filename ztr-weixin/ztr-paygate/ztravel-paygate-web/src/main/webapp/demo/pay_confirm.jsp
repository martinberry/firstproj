<%@page import="com.ztravel.paygate.core.util.BeanMapUtil"%>
<%@page import="com.ztravel.paygate.core.util.PaygateEncryptUtil"%>
<%@page import="com.travelzen.framework.util.TZBeanUtils"%>
<%@page import="com.ztravel.paygate.web.dto.request.RequestPayBean"%>
<%@page import="com.travelzen.framework.core.util.RandomUtil"%>
<%@page import="com.travelzen.framework.core.time.DateTimeUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("utf-8");
response.setCharacterEncoding("utf-8");
	RequestPayBean payBean = new RequestPayBean();
	TZBeanUtils.setProperties(payBean, request);
	String sign = PaygateEncryptUtil.getSignStr(BeanMapUtil.mapBean(payBean), request.getParameter("signkey"));
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
	function changePayType(type){
		if(type==3){
			document.getElementById("formPay").action="<%=request.getContextPath()%>/paygate/reqMobilePay";
		} else if(type==2){
			document.getElementById("formPay").action="<%=request.getContextPath()%>/paygate/reqPay";
		} else {
			document.getElementById("formPay").action="<%=request.getContextPath()%>/paygate/reqPayD";
		}
	}
</script>
</head>
	<body>
		<form id="formPay" action="<%=request.getContextPath()%>/paygate/reqPayD" method="POST">
			<table width="90%">
				<tr>
					<td width="20%">
						终端ID(clientId)：
					</td>
					<td>
						<input type="text" name="clientId" readonly="readonly" value="<%=payBean.getClientId()%>">*
					</td>
				</tr>
				<tr>
					<td width="20%">
						订单号(orderNum)：
					</td>
					<td>
						<input type="text" name="orderNum" readonly="readonly" value="<%= payBean.getOrderNum()%>">*
					</td>
				</tr>
				<tr>
					<td width="20%">
						商品描述(comment)：
					</td>
					<td>
						<input type="text" name="comment" readonly="readonly" value="<%=payBean.getComment()%>">
					</td>
				</tr>
				<tr>
					<td width="20%">
						金额(amount)：
					</td>
					<td>
						<input type="text" name="amount"  value="<%=payBean.getAmount()%>">*
					</td>
				</tr>
				<tr>
					<td width="20%">
						前台通知地址(fgNotifyUrl)：
					</td>
					<td>
						<input type="text" style="width:640;" name="fgNotifyUrl" readonly="readonly" value="<%=payBean.getFgNotifyUrl()%>">*
					</td>
				</tr>
				<tr>
					<td width="20%">
						用户IP(userIP)：
					</td>
					<td>
						<input type="text" style="width:120;" name="userIP" readonly="readonly" value="<%=payBean.getUserIP()%>">*
					</td>
				</tr>
				<tr>
					<td width="20%">
						银行代码(bankId)：
					</td>
					<td>
						<input type="text" style="width:120;" name="bankId" readonly="readonly" value="<%=payBean.getBankId()%>">*
					</td>
				</tr>
				<tr>
					<td width="20%">
						支付平台(gateType)：
					</td>
					<td>
						 <input type="text" style="width:120;" name="gateType" readonly="readonly" value="<%=payBean.getGateType()%>">*
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
						支付方式:
					</td>
					<td>
						 <input type="radio" name="payTypeRadio" onclick="changePayType(1)" checked id="payType_1"><label for="payType_1">页面跳转</label>
						 <input type="radio" name="payTypeRadio" onclick="changePayType(2)" id="payType_2"><label for="payType_2">后台调用</label>
						 <input type="radio" name="payTypeRadio" onclick="changePayType(3)" id="payType_3"><label for="payType_3">手机支付</label>
					</td>
				<tr>
					<td width="20%">
						
					</td>
					<td>
						<input type="submit" value="  去 支 付   ">
					</td>
				</tr>
			</table>	
		</form>
	</body>
</html>
