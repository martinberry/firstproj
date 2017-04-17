<#macro htmlIndex remoteJsFiles=[] remoteCssFiles=[] localCssFiles=[] localJsFiles=[] title="">
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>${title}</title>
	<meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta name="viewport" content="width=device-width, initial-scale=1">


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
		var mediaServer = '${mediaserver}';
		var host = '${host}';
		var panguServer = '${(panguServer)!}';
		var isLogin = '${((wpfv.isLogin)!false)?c}';
		var ssoServer = '${(ssoServer)!}';
    </script>

</head>


<body>
	<#nested/>
</body>
</html>
</#macro>