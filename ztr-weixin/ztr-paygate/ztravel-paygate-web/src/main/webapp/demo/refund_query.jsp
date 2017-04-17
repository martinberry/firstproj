<%@page import="com.ztravel.paygate.web.dto.request.RequestRefundQueryBean"%>
<%@page import="com.ztravel.paygate.web.dto.middlebean.QueryProcessBean"%>
<%@page import="com.ztravel.paygate.core.util.BeanMapUtil"%>
<%@page import="com.ztravel.paygate.core.util.PaygateEncryptUtil"%>
<%@page import="com.travelzen.framework.util.TZBeanUtils"%>
<%@page import="com.ztravel.paygate.web.dto.request.RequestPayBean"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.travelzen.framework.core.util.RandomUtil"%>
<%@page import="com.travelzen.framework.core.time.DateTimeUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8");
	response.setCharacterEncoding("utf-8");
	String clientId = "900003";
	String signKey = "16ac04b17a35407ca83147c98b7d7b35";
	String refundNum = request.getParameter("refundNum");
	String sign = "";
	boolean confirmQuery = false;
	if(StringUtils.isNotBlank(refundNum)){
		confirmQuery = true;
		RequestRefundQueryBean queryBean = new RequestRefundQueryBean();
		TZBeanUtils.setProperties(queryBean, request);
		sign = PaygateEncryptUtil.getSignStr(BeanMapUtil.mapBean(queryBean), request.getParameter("signkey"));;
	} else {
		refundNum = "";
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
var confirmQuery = <%=confirmQuery%>;
$(function(){
	if(confirmQuery){
		$(":radio[name=gateType][value=<%=request.getParameter("gateType")%>]").attr("checked","checked");
		$.post("<%=request.getContextPath()%>/paygate/query/refund",$("#formQuery").serialize(),function(content){
			$("#content").text(content);
			$("#resultDiv").show();
		},"text");
	}
});
</script>
</head>
	<body>
		<form action="refund_query.jsp" id="formQuery" method="POST">
			<input type="hidden" name="sign" value="<%=sign%>">
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
						退单标识号(refundNum)：
					</td>
					<td>
						<input type="text" name="refundNum" value="<%= refundNum%>">*
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
						<input type="submit" id="submit" value="  提  交    ">
					</td>
				</tr>
			</table>	
		</form>
		<div id="resultDiv" style="display:none">
			<textarea id="content" style="width:640px;height:460px;"></textarea>
		</div>
	</body>
</html>
