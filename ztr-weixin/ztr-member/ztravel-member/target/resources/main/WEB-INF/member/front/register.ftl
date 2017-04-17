<#import "/common/front/htmlIndex.ftl" as html/>
<@html.htmlIndex curModule="用户" title="真旅行-用户注册" jsFiles=["member/front/common_utils.js", "member/front/register.js","common/password_verify.js"] cssFiles=["css/client/register.css"]>
		<div class="main-wrapper" id="main-wrapper">
			<div class="main-box" id="main-box" style="width:820px;">
				<div class="top-border"><span class="clip"></span></div>
				<div class="box-container">
					<div class="top-block">
						<span class="earch-icon"></span>
						<span class="title">欢迎加入真旅行</span>
					</div>
					<div class="cont-block clearfix">
                        <div class="registerForm pullLeft">
                            <div class="componentInput phoneNumber mobileRow">
                                <i class="commonIcon phoneNumberIcon"></i>
                                <input id="mobileInputer" type="text" maxlength="11" placeholder="请输入手机号" style="width: 310px;"/>
                            </div>
                            <div class="componentInput verificationCode messageCodeRow">
                                <i class="commonIcon verificationCodeIcon"></i>
                                <input id="verificationCodeInputer" type="text" maxlength="6" placeholder="请输入验证码" style="width: 188px;">
                                <span class="getVerificationCode">获取短信验证码</span>
                            </div>
                            <div class="componentInput PWStrong" style="margin-bottom:26px;position: relative;">
							    <i class="commonIcon lockIcon"></i>
							    <input id="passwordInputer" maxlength="28" type="password" placeholder="请输入8-28位密码" style="width: 266px;">
							    <span class="strength weak">弱</span>
							    <i class="view-pwd"></i>
							</div>
							<div type="text" class="registerErrorRow errorBoxContent" style="display:none;">
                                <i class="commonIcon errorIcon"></i><span class="errorHint"><span>
                            </div>
                            <div class="buttonRow">
                                <a href="javascript:void(0);" class="bigOrangeBtn" style="width:345px;">注   册</a>
                                <!--<a href="javascript:void(0);" class="bigOrangeBtn active" style="width:345px;">注   册</a>-->
                            </div>
                            <div class="borderSeparate"></div>
                        </div>
                         <div class="rightTwoDimension">
                            <div class="twoDimensionHint">使用微信扫描二维码直接登录</div>
                            <img src="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=${ticket!''}">
                        </div>
					</div>
				</div>
			</div>
		</div>
</@html.htmlIndex>
