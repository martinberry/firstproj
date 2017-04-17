<#macro navHeader remoteJsFiles=[] remoteCssFiles=[] localJsFiles=[] localCssFiles=[] title="真旅行" currentMenu="">
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>${title}</title>
	<meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- css -->
    <link rel="stylesheet" type="text/css" href="${host}/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/client/bootstrap.reset.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/common.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/client/common.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/client/navHeader.css"><!--导航头样式-->
    <#list remoteCssFiles as css>
		<link rel="stylesheet" type="text/css" href="${host}/${css}"/>
	</#list>
	<#list localCssFiles as localCss>
		<link rel="stylesheet" type="text/css" href="${basepath}/resources/css/${localCss}"/>
	</#list>
	<!-- js -->
    <script type="text/javascript" src="${host}/js/base/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="${host}/js/bootdist/bootstrap.min.js"></script>
    <script type="text/javascript" src="${host}/js/vendor/underscore.js"></script>
    <script type="text/javascript" src="${host}/js/client/common.js"></script>
    <script type="text/javascript" src="${host}/js/client/navHeader.js"></script><!--导航头交互-->
	<#list remoteJsFiles as js>
		<script type="text/javascript" src="${host}/${js}"></script>
	</#list>
	<#list localJsFiles as js>
		<script type="text/javascript" src="${basepath}/resources/javascripts/${js}"></script>
	</#list>

	<!--[if lte IE 9]>
    <script type="text/javascript" src="${host}/js/base/html5.js"></script>
    <![endif]-->

	<script type="text/javascript">
		var basepath = '${basepath}';
		var memberServer = '${memberServer}';
		var mediaserver = '${mediaserver}';
		var host = '${host}';
		var ssoServer = '${ssoServer}';
    </script>
</head>
<body>
	<header>
	    <div class="head clearfix">
	        <div class="logo"><a class="logoLink" href="${memberServer}"></a></div>
	        <div class="top-nav">
	            <ul class="top-nav-list clearfix">
	                <li class="top-nav-item <#if currentMenu='个人中心'>current</#if>" onClick="window.location.href='${basepath}/member/info'">个人中心</li>
	                <li class="top-nav-item <#if currentMenu='我的订单'>current</#if>" onClick="window.location.href='${basepath}/order/front/list'">我的订单</li>
                    <li class="top-nav-item <#if currentMenu='心愿清单'>current</#if>" onClick="window.location.href='${basepath}/member/wish/list'">心愿清单</li>
                    <li class="top-nav-item <#if currentMenu='我的钱包'>current</#if>" onClick="window.location.href='${basepath}/electronicWallet/coupon/index'">我的钱包</li>
	                <li class="top-nav-item <#if currentMenu='网站消息'>current</#if>" onClick="window.location.href='${basepath}/systemnotice/list'">网站消息</li>
	            </ul>
	        </div>
	    </div>
	</header>
	<div class="main-container">
		<#nested/>
		<#include "/common/front/right_side.ftl"/>
		<#include "/common/front/header/footer.ftl"/>
		<#include "/common/front/panguTracking.ftl"/>
	</div>
</body>
</html>
</#macro>