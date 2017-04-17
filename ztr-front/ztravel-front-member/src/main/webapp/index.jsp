<%@page import="com.ztravel.common.util.WebEnv"%>
<%@page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta name="baidu-site-verification" content="0OrjINQ4ic" />
		<meta name="360-site-verification" content="355a1e6ccaf61cbf972d59f3ee267f70" />
		<meta name="sogou_site_verification" content="TVWXxLek1P"/>
		<meta name="shenma-site-verification" content="0114c1e6361f692efddb5ebe692989d4_1442986866"> 
	</head>
</html>

<%
	String pc = WebEnv.get("server.path.memberServer") + "/home";
	String h5 = WebEnv.get("server.path.wxServer") + "/weixin/product/list";
%>
<script>
	if (navigator.userAgent.match(/iphone|android|phone|mobile|wap|netfront|java|opera mobi|opera mini|ucweb|windows ce|symbian|symbianos|series|webos|sony|blackberry|dopod|nokia|samsung|palmsource|xda|pieplus|meizu|midp|cldc|motorola|foma|docomo|up.browser|up.link|blazer|helio|hosin|huawei|novarra|coolpad|webos|techfaith|palmsource|alcatel|amoi|ktouch|nexian|ericsson|philips|sagem|wellcom|bunjalloo|maui|smartphone|iemobile|spice|bird|zte-|longcos|pantech|gionee|portalmmm|jig browser|hiptop|benq|haier|^lct|320x320|240x320|176x220/i) != null) {
		window.location.href = "<%=h5%>";
	}else{
		window.location.href = "<%=pc%>";
	}
</script>
