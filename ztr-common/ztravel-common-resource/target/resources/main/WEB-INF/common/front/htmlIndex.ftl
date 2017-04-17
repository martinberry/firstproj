<#macro htmlIndex jsFiles=[] cssFiles=[] localCssFiles=[] curModule="" title="">
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


<!--     选择支付页面需加入的css   -->
     <link rel="stylesheet" type="text/css" href="${host}/css/client/flight_logo.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/client/userOrder.css">



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
		var memberServer = '${memberServer}';
		var mediaserver = '${mediaserver}';
		var host = '${host}';
		var ssoServer = '${ssoServer}' ;
		var wxServer = '${wxServer}';
    </script>
    <script>
		var _hmt = _hmt || [];
		(function() {
		  var hm = document.createElement("script");
		  hm.src = "//hm.baidu.com/hm.js?bcf5ba51430753b5b7138a1ff3f8b534";
		  var s = document.getElementsByTagName("script")[0];
		  s.parentNode.insertBefore(hm, s);
		})();
	</script>

</head>


<body>
	<header>
		<div class="head clearfix">
			<a href="${memberServer}">
				<div class="logo"></div>
			</a>
		</div>
	</header>
	<div class="main-container">
		<#nested/>
		<#include "/common/front/right_side.ftl"/>
		<#include "/common/front/header/footer.ftl"/>
	</div>

	<script type="text/javascript">
        $(function(){
            //$(".top-nav-list").slideNav({
            //    fx: "swing",
            //    speed: 300,
            //    changeTextColor: "#fff"
            //});
        });
    </script>
	<#include "/common/front/panguTracking.ftl"/>
</body>
</html>
</#macro>