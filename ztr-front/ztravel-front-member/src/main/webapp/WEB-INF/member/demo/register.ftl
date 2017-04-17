<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>注册页面</title>
	<meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" type="text/css" href="${host}/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/bootstrap.reset.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/bootstrap-datepicker.min.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/common.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/client/common.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/client/register.css">

    <script type="text/javascript" src="${host}/js/base/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="${host}/js/bootdist/bootstrap.min.js"></script>
    <script type="text/javascript" src="${host}/js/vendor/bootstrap-datepicker.min.js"></script>
    <script type="text/javascript" src="${host}/js/vendor/bootstrap-datepicker.zh-CN.min.js"></script>
    <script type="text/javascript" src="${host}/js/client/common.js"></script>

    <!--[if lte IE 9]>
    <script type="text/javascript" src="${host}/js/base/html5.js"></script>
    <![endif]-->
</head>
<body>

	<header>
		<div class="head">
			<div class="logo"></div>
		</div>
	</header>

	<div class="main-container">
		<div class="main-wrapper" id="main-wrapper">
			<div class="main-box" id="main-box" style="width:500px;">
				<div class="top-border"><span class="clip"></span></div>
				<div class="box-container">
					<div class="top-block">
						<span class="earch-icon"></span>
						<span class="title">欢迎加入真旅行</span>
					</div>
					<div class="cont-block">
                        <div class="registerForm">
                            <div type="text" class="registerErrorRow errorBoxContent">
                                <i class="commonIcon errorIcon"></i>请输入手机验证码
                            </div>
                            <div class="componentInput phoneNumber mobileRow">
                                <i class="commonIcon phoneNumberIcon"></i>
                                <input type="text" placeholder="请输入手机号" style="width: 309px;"/>
                            </div>
                            <div class="componentInput verificationCode messageCodeRow">
                                <i class="commonIcon verificationCodeIcon"></i>
                                <input type="text" placeholder="请输入验证码" style="width: 174px;">
                                <span class="getVerificationCode">获取短信验证码</span>
                            </div>
                            <div class="buttonRow">
                                <a href="javascript:void(0);" class="bigOrangeBtn" style="width:345px;">注   册</a>
                                <!--<a href="javascript:void(0);" class="bigOrangeBtn active" style="width:345px;">注   册</a>-->
                            </div>
                        </div>
					</div>
				</div>
				<div class="bottom-border"></div>
			</div>
		</div>

		<div class="foot clearfix">
			<span class="copyright">Copyright © 2015 真旅行 All Rights Reserved. 沪ICP备08004120号-5</span>
			<span class="service-tel">服务热线：<span class="telnum">400-888-666</span></span>
		</div>
	</div>

	<script type="text/javascript">
		function setWrapperHeight() {
			document.getElementById("main-wrapper").style.cssText =
					 "height:" + (document.documentElement.clientHeight - 142) + "px";
		}

		setWrapperHeight();


		window.onresize = function() {
			setWrapperHeight();

			$(".main-box").css({
				"top": ($(".main-wrapper").height() - $(".main-box").height()) / 2 + "px"
			});
		}

		$(function(){
			$(".main-box").css({
				"top": ($(".main-wrapper").height() - $(".main-box").height()) / 2 + "px"
			});
		});
	</script>

</body>
</html>