<#macro userIndex remoteJsFiles=[] remoteCssFiles=[] localCssFiles=[] localJsFiles=[] title="">
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>${title}</title>
	<meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="${host}/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/bootstrap.reset.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/bootstrap-datepicker.min.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/common.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/client/common.css">

    <script type="text/javascript" src="${host}/js/base/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="${host}/js/bootdist/bootstrap.min.js"></script>
    <script type="text/javascript" src="${host}/js/vendor/bootstrap-datepicker.min.js"></script>
    <script type="text/javascript" src="${host}/js/vendor/bootstrap-datepicker.zh-CN.min.js"></script>
    <script type="text/javascript" src="${host}/js/vendor/jquery.color-2.1.2.min.js"></script>
    <script type="text/javascript" src="${host}/js/vendor/underscore.js"></script>
    <script type="text/javascript" src="${host}/js/global/global.js"></script>
    <script type="text/javascript" src="${host}/js/client/common.js"></script>
    <!--  渐变背景和mainbox 需要调整距离和位置，引入adjust.js  -->
    <script type="text/javascript" src="${host}/js/client/adjust.js"></script>
	<!-- 选择支付页面需加入的css   -->
    <link rel="stylesheet" type="text/css" href="${host}/css/client/flight_logo.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/client/userOrder.css">
    <!--[if lte IE 9]>
    <script type="text/javascript" src="{host}/js/base/html5.js"></script>
    <![endif]-->
    <#list remoteCssFiles as css>
		<link rel="stylesheet" type="text/css" href="${host}/${css}"/>
	</#list>

	<#list localCssFiles as localCss>
		<link rel="stylesheet" type="text/css" href="${basepath}/resources/css/${localCss}"/>
	</#list>

	<#list remoteJsFiles as js>
		<script type="text/javascript" src="${host}/${js}"></script>
	</#list>

	<#list localJsFiles as js>
		<script type="text/javascript" src="${basepath}/resources/javascripts/${js}"></script>
	</#list>
	<script type="text/javascript">
		var basepath = '${basepath}';
		var memberServer = '${memberServer}';
		var mediaserver = '${mediaserver}';
		var host = '${host}';
		var ssoServer = '${ssoServer}';
    </script>
</head>
<body>
<div class="main-container">
	<#nested/>
	<#include "/common/front/right_side.ftl"/>
	<#include "panguTracking.ftl"/>
	<div class="foot clearfix">
        <span class="copyright">Copyright © 2015 真旅行 All Rights Reserved. 沪ICP备08004120号-5</span>
        <span class="service-tel"><span class="phoneIcon"></span>服务热线：<span class="telnum">400-888-666</span></span>
    </div>
</div>
</body>
</html>
</#macro>