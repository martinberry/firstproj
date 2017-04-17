<#macro htmlIndex jsFiles=[] cssFiles=[] localCssFiles=[] curModule="" title="">
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${title!'真旅行'}</title>
	<meta http-equiv="X-UA-Compatible" content="IE=Edge">
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<link rel="stylesheet" type="text/css" href="${host}/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="${host}/css/bootstrap.reset.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/common.css">
	<link rel="stylesheet" type="text/css" href="${host}/css/maintain/common.css">

	<script type="text/javascript" src="${host}/js/base/jquery-1.11.2.min.js"></script>
	<script type="text/javascript" src="${host}/js/bootdist/bootstrap.min.js"></script>
	<script type="text/javascript" src="${host}/js/maintain/common.js"></script>

	<!--[if lte IE 9]>
    <script type="text/javascript" src="${host}/js/base/html5.js"></script>
	<script type="text/javascript" src="${host}/js/base/respond.js"></script>
    <![endif]-->

    <#list cssFiles as css>
		<link rel="stylesheet" href="${host}/${css}"/>
	</#list>

	<#list localCssFiles as localCss>
		<link rel="stylesheet" href="${basepath}/resources/css/${localCss}"/>
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
		var memberServer = '${memberServer}';
		var mediaserver = '${mediaserver}';
		var operaServer = '${operaServer}';
	</script>

	<script type="text/javascript" src="${basepath}/resources/javascripts/common/backCommon.js"></script>
	<script type="text/javascript" src="${basepath}/resources/javascripts/common/operate_common.js"></script>
</head>
<body>


<div class="wrapper clearfix">

		<#nested/>

		<footer>Copyright © 2015 天地行 All Rights Reserved. 沪ICP备08004120号-5</footer>
	</div>
</body>
</html>
</#macro>
