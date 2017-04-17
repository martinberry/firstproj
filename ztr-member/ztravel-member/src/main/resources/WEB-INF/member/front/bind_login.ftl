<#import "/common/front/htmlIndex.ftl" as html/>
<@html.htmlIndex curModule="用户" title="微信授权登录" jsFiles=["js/client/adjust.js", "member/front/bind_login.js"] cssFiles=["css/client/register.css"]>
	<div class="main-container">
		<div class="main-wrapper" id="main-wrapper">
			<div class="main-box" id="main-box" style="width:800px;">
				<div class="top-border"><span class="clip"></span></div>
				<div class="box-container">
					<div class="top-block">
						<span class="earch-icon"></span>
						<span class="title">欢迎加入真旅行</span>
					</div>
                    <div class="weiXin">亲爱的微信用户：<span class="blueFonts">${(bindRequest.nickName)!''}</span> 您好</div>
					<div class="cont-block clearfix">
                        <div class="registerForm pullLeft width440">
                            <div class="blueTitleFonts">已有真旅行会员账号</div>
                            <div style="width:262px;display:none;" class="errorBoxContent">
                                <i class="commonIcon errorIcon"></i><span class="errorHint">输入的用户名或密码有错</span>
                            </div>
                            <div style="height:3px;"></div>
                            <div class="componentInput phoneNumber mobileRow">
                                <i class="commonIcon phoneNumberIcon"></i>
                                <input id="mobileInputer" type="text" placeholder="请输入手机号" style="width: 260px;"/>
                            </div>
                            <div class="componentInput PWStrong" style="margin-bottom:26px;position: relative;">
                                <i class="commonIcon lockIcon"></i>
                                <input id="passwordInputer" type="password" placeholder="请输入密码" style="width: 260px;">
                                <i class="view-pwd"></i>
                                <a href="javascript:void(0);" class="forgetPWFonts">忘记密码?</a>
                            </div>
                            <div class="componentInput verificationCode" style="display:none;">
                                <i class="commonIcon verificationCodeIcon"></i>
                                <input id="verifyCodeInputer" type="text" placeholder="请输入验证码" style="width: 120px;">
                                <img id="kaptchaImage">
                            </div>
                            <div class="buttonRow marginTop20">
                                <a href="javascript:void(0);" class="bigBlue11b9b7Btn" style="width:260px;">绑定并登录</a>
                            </div>
                            <div class="borderSeparate"></div>
                        </div>
                        <div class="rightContent">
                            <div class="blueTitleFonts">暂无真旅行会员账号</div>
                            <div id="headImgUrl">
                                <img src="${(bindRequest.headImgUrl)!}" class="weiXinPhoto">
                            </div>
                            <div class="userName" id="nickName">${(bindRequest.nickName)!''}</div>
                            <div style="display:none" class="openId" id="openId">${(bindRequest.openId)!}</div>
                            <a href="javascript:void(0);" class="bigYellowBtn">微信登录</a>
                        </div>
					</div>
				</div>
			</div>
		</div>
    </div>
    <script type="text/javascript">
        $(function(){
            // $(".top-nav-list").slideNav({
            //     fx: "swing",
            //     speed: 300,
            //     changeTextColor: "#fff"
            // });
            $(".view-pwd").click(function(){
                $(this).toggleClass("active");

                if ($(this).hasClass("active")) {
                    $(this).parent(".componentInput").find("input").attr("type", "text");
                } else {
                    $(this).parent(".componentInput").find("input").attr("type", "password");
                }
            });


        });
    </script>
</@html.htmlIndex>