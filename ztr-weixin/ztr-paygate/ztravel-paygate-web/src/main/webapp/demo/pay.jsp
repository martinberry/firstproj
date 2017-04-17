<%@page import="com.travelzen.framework.core.util.RandomUtil"%>
<%@page import="com.travelzen.framework.core.time.DateTimeUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String clientId = "900003";
	String signKey = "16ac04b17a35407ca83147c98b7d7b35";
	String orderNum = DateTimeUtil.datetime14() + RandomUtil.getRandomStr(6);
	String amount = "100";
	String contextPath = request.getContextPath();
    String scheme = request.getScheme();
    String serverName = request.getServerName();
    int port = request.getServerPort();
	String bankId = "";
	String fgNotifyUrl = scheme+"://"+request.getLocalAddr()+":"+request.getLocalPort()+contextPath+"/demo/fgNotify.jsp";
	String userIP = request.getRemoteAddr();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
	<body>
		<form action="pay_confirm.jsp" method="POST">
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
						<input type="text" name="signkey" value="<%=signKey%>">
					</td>
				</tr>
				<tr>
					<td width="20%">
						订单号(orderNum)：
					</td>
					<td>
						<input type="text" name="orderNum" value="<%= orderNum%>">*
					</td>
				</tr>
				<tr>
					<td width="20%">
						商品描述(comment)：
					</td>
					<td>
						<input type="text" name="comment" value="商品描述">
					</td>
				</tr>
				<tr>
					<td width="20%">
						金额(amount)：
					</td>
					<td>
						<input type="text" name="amount" value="<%=amount%>">*
					</td>
				</tr>
				<tr>
					<td width="20%">
						前台通知地址(fgNotifyUrl)：
					</td>
					<td>
						<input type="text" style="width:640;" name="fgNotifyUrl" value="<%=fgNotifyUrl%>">*
					</td>
				</tr>
				<tr>
					<td width="20%">
						用户IP(userIP)：
					</td>
					<td>
						<input type="text" style="width:120;" name="userIP" value="<%=userIP%>">*
					</td>
				</tr>
				<tr>
					<td width="20%">
						银行代码(bankId)：
					</td>
					<td>
						<input type="text" style="width:120;" name="bankId" value="<%=bankId%>">*
					</td>
				</tr>
				<tr>
					<td width="20%">
						支付平台(gateType)：
					</td>
					<td>
						<input type="radio" name="gateType" value="0001">支付宝(0001)
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
