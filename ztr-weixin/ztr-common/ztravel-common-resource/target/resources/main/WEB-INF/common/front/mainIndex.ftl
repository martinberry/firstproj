<#import "/common/front/header/mainHeader.ftl" as mainHeader />
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
    <link rel="stylesheet" type="text/css" href="${host}/css/client/indexPage.css">

    <script type="text/javascript" src="${host}/js/base/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="${host}/js/bootdist/bootstrap.min.js"></script>
    <script type="text/javascript" src="${host}/js/vendor/bootstrap-datepicker.min.js"></script>
    <script type="text/javascript" src="${host}/js/vendor/bootstrap-datepicker.zh-CN.min.js"></script>
    <script type="text/javascript" src="${host}/js/vendor/jquery.color-2.1.2.min.js"></script>
    <script type="text/javascript" src="${host}/js/global/global.js"></script>
    <script type="text/javascript" src="${host}/js/client/common.js"></script>
    <script type="text/javascript" src="${basepath}/resources/javascripts/common/isOuterTrigger.js"></script>
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
		var mediaServer = '${mediaserver}';
		var isLogin = '${isLogin?c}' ;
		var host = '${host}';
		var ssoServer = '${ssoServer}' ;
    </script>

</head>

<body>

    <@mainHeader.mainHeader currentMenu="目的地"/>

	<div class="main-container">
		<div class="main-wrapper" id="main-wrapper">
		    <#include "/common/front/productSearchBlock.ftl" />
		</div>

		<div class="foot clearfix">
			<span class="copyright">Copyright © 2015 真旅行 All Rights Reserved. 沪ICP备08004120号-5</span>
			<span class="service-tel">服务热线：<span class="telnum">400-888-666</span></span>
		</div>
	</div>

	<div class="right-nav-block">
		<div class="main-nav">
			<div class="right-nav-icon right-nav-list"></div>
			<div class="right-nav-icon right-nav-favor"></div>
			<div class="right-nav-icon right-nav-asset"></div>
			<div class="right-nav-icon right-nav-notice"></div>
			<div class="right-nav-icon right-nav-contacts"></div>
		</div>
		<div class="bottom-nav">
			<div class="right-nav-icon right-nav-edit"></div>
			<div class="right-nav-icon right-nav-backToTop"></div>
		</div>
	</div>

	<div class="login-box box-relative-location" style="top: 71px; display: none;">
		<div class="arrow"><div class="innerArrow"></div></div>
	</div>

	<#nested/>

	<script type="text/javascript">
		$(function(){

			setRightNavHeight();
			setWrapperHeight();

			window.onresize = function(){
				setRightNavHeight();
			}

			$(".main-nav .right-nav-icon").click(function(event){
				var $ele = $(this);
				if(isLogin == 'true'){
					if($ele.hasClass("right-nav-list")){
						window.location.href = basepath + "/order/front/list" ;
					}
				}else{
					cleanRightLoginBox() ;
					$(".box-relative-location").append($("#common-login-box").html()) ;
					$(".box-relative-location").css({
						"top": $ele.position().top + "px"
					}).show();
	                event.stopPropagation();
				}
			});

            $("body").click(function(event){
                if (!isOuterTrigger(event, $(".box-relative-location"))) {
                    $(".box-relative-location").hide();
                    cleanRightLoginBox() ;
                }
            });

			// $(".top-nav-list").slideNav({
			// 	fx: "swing",
			// 	speed: 300,
			// 	changeTextColor: "#fff"
			// });

		});

		function setRightNavHeight() {
		    if ($(".right-nav-block").length !== 0) {
		    	$(".right-nav-block").eq(0).css({
		    		"height": document.documentElement.clientHeight + "px"
		    	});

		    	$(".right-nav-block .main-nav").css({
		    		"margin-top": ((document.documentElement.clientHeight - 450) / 2) + "px"
		    	});
		    }
		}

		function setWrapperHeight() {
		    if ($(".main-wrapper").length !== 0) {
		    	$(".main-wrapper").eq(0).css({
		    		"min-height": (document.documentElement.clientHeight - 142) + "px"
		    	});
		    }
		}

	</script>
<#include "panguTracking.ftl"/>
</body>
</html>
</#macro>