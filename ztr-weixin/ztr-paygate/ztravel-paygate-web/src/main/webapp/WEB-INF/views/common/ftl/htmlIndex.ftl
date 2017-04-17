<#macro htmlIndex jsFiles=[] cssFiles=[] removeExtHeader=false removeExtFooter=false>
<#escape x as x?js_string>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" >
	<title>${title!"天地行-Paygate"}</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<#-- Common Kendo UI Web CSS -->
	<link rel="stylesheet" href="${statichost}/css/smoothness/jquery-ui-1.10.3.custom.css"/>
	<link href="${statichost}/css/kendo.common.css" rel="stylesheet"/>
	<#--<link href="${statichost}/css/kendo.gray.css" rel="stylesheet"/>-->
	<link href="${statichost}/css/common.css" rel="stylesheet"/>
	<link href="${statichost}/css/b2b.css" rel="stylesheet"/>
	<link href="${statichost}/css/kendo.reset.css" rel="stylesheet"/>

	<#list cssFiles as css>
		<link rel="stylesheet" href="${basepath}/static/css/${css}"/>
	</#list>

	<script type="text/javascript">
		var basepath = '${basepath}';
		var statichost = '${statichost}'
	</script>
<#-- jQuery JavaScript -->
	<script type="text/javascript" src="${statichost}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${statichost}/js/json2.js"></script>
	<script type="text/javascript" src="${statichost}/js/kendo.web.min.js"></script>
	<#--<script type="text/javascript" src="${statichost}/js/plugins/jquery.fixedBar.js"></script>-->
	<script type="text/javascript" src="${statichost}/js/cultures/kendo.culture.zh-CN.min.js"></script>
	<script src="${statichost}/js/jquery-ui-1.10.3.custom.js"></script>
<#--	<script src="${statichost}/js/i18n/jquery.ui.datepicker-zh-CN.js"></script>-->
	<script src="${statichost}/js/global.js"></script>


	<#list jsFiles as js>
		<script type="text/javascript" src="${basepath}/static/js/${js}"></script>
	</#list>
	
	<!--[if lt IE 8]>
	<link rel="stylesheet" href="${statichost}/css/ie.css"/>
	<script type="text/javascript" src="${statichost}/js/vendor/IE7.min.js"></script>
	<![endif]-->
</head>
<body>
	<#if !removeExtHeader>
	<div id="extHeader">
		<div></div>
	</div>
	</#if>
	<#nested/>
	<#if !removeExtFooter>
	<div id="extFooter">
		<div>
			<p>
				<a target="_blank" href="javascript:void(0);">关于我们</a>&nbsp;|
				<a target="_blank" href="javascript:void(0);">服务中心</a>&nbsp;|
				<a target="_blank" href="javascript:void(0);">联系我们</a>
			</p>

			<p>客户服务热线:400-720-6666</p>
		</div>
	</div>
	</#if>
</body>
</html>
	</#escape>
</#macro>
