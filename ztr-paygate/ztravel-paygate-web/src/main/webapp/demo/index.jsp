<%@page import="com.travelzen.framework.core.util.RandomUtil"%>
<%@page import="com.travelzen.framework.core.time.DateTimeUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
	<body>
		<h1 style="align:left">支付网关示例</h1>
		<ul>支付类
			<li> <a target="_blank" href="pay.jsp">支付</a></li>
			<li> <a target="_blank" href="refund.jsp">退款</a></li>
			<li> <a target="_blank" href="query.jsp">支付结果查询</a></li>
			<li> <a target="_blank" href="refund_query.jsp">退款结果查询</a></li>
		</ul>
		<ul>签约类
			<li> <a target="_blank" href="partner_sign.jsp">签约地址获取</a></li>
			<li> <a target="_blank" href="partner_unsign.jsp">解约</a></li>
			<li> <a target="_blank" href="partner_sign_query.jsp">签约结果查询</a></li>
		</ul>
		<ul>分润类
			<li> <a target="_blank" href="share_profit.jsp">分润请求</a></li>
			<li> <a target="_blank" href="javascript:void(0)">分润结果查询</a></li>
		</ul>
		<ul>冻结类
			<li> <a target="_blank" href="refund_freeze.jsp">冻结请求</a></li>
			<li> <a target="_blank" href="refund_unfreeze.jsp">解冻请求</a></li>
		</ul>
		<ul>转账
			<li> <a target="_blank" href="transfer_account.jsp">转账请求</a></li>
		</ul>
		<ul>代扣
			<li> <a target="_blank" href="agent_pay.jsp">代扣</a></li>
			<li> <a target="_blank" href="javascript:void(0)">代扣结果查询</a></li>
		</ul>
		<ul>对账类
			<li> <a target="_blank" href="downloadBills.jsp">对账文件下载</a></li>
		</ul>
	</body>
</html>
