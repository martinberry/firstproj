<#macro detailHeader jsFiles=[] cssFiles=[] localCssFiles=[] curModule="" title="">
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>真旅行 我的订单 订单详情</title>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" type="text/css" href="${host}/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/client/bootstrap.reset.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/bootstrap-datepicker.min.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/common.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/client/common.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/client/flight_logo.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/client/orderInfo.css">

    <script type="text/javascript" src="${host}/js/base/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="${host}/js/bootdist/bootstrap.min.js"></script>
    <script type="text/javascript" src="${host}/js/vendor/bootstrap-datepicker.min.js"></script>
    <script type="text/javascript" src="${host}/js/vendor/bootstrap-datepicker.zh-CN.min.js"></script>
    <script type="text/javascript" src="${host}/js/client/common.js"></script>
    <!--  渐变背景和mainbox 需要调整距离和位置，引入adjust.js  -->
    <script type="text/javascript" src="${host}/js/client/adjust.js"></script>

    <!--[if lte IE 9]>
    <script type="text/javascript" src="${host}/js/base/html5.js"></script>
    <![endif]-->

        <#list cssFiles as css>
		<link rel="stylesheet" type="text/css" href="${host}/${css}"/>
	</#list>

	<#list localCssFiles as localCss>
		<link rel="stylesheet" type="text/css" href="${basepath}/resources/css/${localCss}"/>
	</#list>

	<#list jsFiles as js>
		<#if js?starts_with('js')>
			<script type="text/javascript" src="${host}/${js}"></script>
		<#else>
			<script type="text/javascript" src="${basepath}/resources/javascripts/${js}"></script>
		</#if>
	</#list>

    	<script type="text/javascript">
		var basepath = '${basepath}';
	    var memberServer = '${memberServer!}';
		var mediaserver = '${mediaserver}';
		var host = '${host}';
    </script>

</head>
<body>

<header>
    <div class="head clearfix">
        <div class="logo"></div>
    </div>
</header>

	<div class="main-container">

		<#nested/>

    <div class="foot clearfix">
        <span class="copyright">Copyright © 2015 真旅行 All Rights Reserved. 沪ICP备08004120号-5</span>
        <span class="service-tel"><span class="phoneIcon"></span>服务热线：<span class="telnum">400-888-666</span></span>
    </div>
</div>
<#include "/common/front/panguTracking.ftl"/>
</body>
</html>

</#macro>