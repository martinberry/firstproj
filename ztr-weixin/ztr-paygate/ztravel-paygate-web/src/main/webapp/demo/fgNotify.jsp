<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF-8"); %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>机构接收网银网关支付结果通知</title>
</head>
<body>
<h1>机构接收网银网关支付结果通知</h1>
<table border=1>
	<tr><td>终端ID(clientId)：</td><td>${param.clientId}</td></tr>
	<tr><td>支付平台(gateType)：</td><td>${param.gateType}</td></tr>
	<tr><td>返回码(retCode)：</td><td>${param.retCode}</td></tr>
	<tr><td>支付状态(payState)：</td><td>${param.payState}</td></tr>
	<tr><td>返回信息(retMsg)：</td><td>${param.retMsg}</td></tr>
	<tr><td>订单号(orderNum)：</td><td>${param.orderNum}</td></tr>
	<tr><td>银行核算日期(bankCheckDate)：</td><td>${param.bankCheckDate}</td></tr>
	<tr><td>支付平台流水号(traceNum)：</td><td>${param.traceNum}</td></tr>
	<tr><td>支付金额(amount)：</td><td>${param.amount}</td></tr>
</table>
</body>
</html>
