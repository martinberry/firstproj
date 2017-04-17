<%@page import="com.travelzen.framework.core.util.RandomUtil"%>
<%@page import="com.travelzen.framework.core.time.DateTimeUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String clientId = "900003";
	String signkey = "16ac04b17a35407ca83147c98b7d7b35";
	String refundNum = DateTimeUtil.datetime14() + RandomUtil.getRandomStr(6);
	String refundTime = DateTimeUtil.datetime14Readable();
	String amount = "100";
	String fgNotifyUrl = "http://192.168.163.127:8090/ztravel-paygate-web/demo/fgNotify.jsp";
	String userIP = request.getRemoteAddr();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<h1>交易退款示例</h1>
	<body>
		<form action="refund_confirm.jsp" method="POST">
			<table width="90%">
				<tr>
					<td width="20%">
						终端ID(clientId)：
					</td>
					<td>
						<input type="text" name="clientId" value="<%=clientId%>">*
					</td>
				</tr>
				<tr>
					<td width="20%">
						终端签名key：
					</td>
					<td>
						<input type="text" style="width:280px;" name="signkey" value="<%=signkey%>">
					</td>
				</tr>
				<tr>
					<td width="20%">
						退款标识号(refundNum)：
					</td>
					<td>
						<input type="text" name="refundNum" value="<%= refundNum%>">*
					</td>
				</tr>
				<tr>
					<td width="20%">
						退款时间(refundTime)：
					</td>
					<td>
						<input type="text" name="refundTime" value="<%= refundTime%>">*
					</td>
				</tr>
				<tr>
					<td width="20%">
						原订单号(orderNum)：
					</td>
					<td>
						<input type="text" name="orderNum" value="">*
					</td>
				</tr>
				<tr>
					<td width="20%">
						原交易号(traceNum)：
					</td>
					<td>
						<input type="text" name="traceNum" value="">*
					</td>
				</tr>
				<tr>
					<td width="20%">
						原交易金额(transAmount)：
					</td>
					<td>
						<input type="text" name="transAmount" value="">*
					</td>
				</tr>
				<tr>
					<td width="20%">
						本次退款金额(refundAmount)：
					</td>
					<td>
						<input type="text" name="refundAmount" value="">*
					</td>
				</tr>
				<tr>
					<td width="20%">
						退款备注信息(comment)：
					</td>
					<td>
						<input type="text" name="comment" value="交易退款">
					</td>
				</tr>
				<tr>
					<td width="20%">
						退分润信息(refundProfitDetails)：
					</td>
					<td>
						<input type="text" style="width:360px" name="refundProfitDetails" value="">(退分润格式:分润方账号|退分润金额|备注)
					</td>
				</tr>
				<tr>
					<td width="20%">
						支付平台(gateType)：
					</td>
					<td>
						<input type="radio" checked name="gateType" value="0001">支付宝(0001)
						<input type="radio" name="gateType" value="0002">财付通(0002)
						<input type="radio" name="gateType" value="0003">汇付天下(0003)
						<input type="radio" name="gateType" value="0004">德付通(0004)
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
