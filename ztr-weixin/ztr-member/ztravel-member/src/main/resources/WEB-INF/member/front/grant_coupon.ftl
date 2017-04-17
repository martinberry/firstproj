<#import "/common/front/htmlIndex.ftl" as html/>
<@html.htmlIndex curModule="用户" title="领券" jsFiles=["member/front/common_utils.js", "member/front/grant_coupon.js"] cssFiles=["css/client/register.css"]>
		<div class="main-wrapper" id="main-wrapper">
			<div class="main-box" id="main-box" style="width:500px;">
				<div class="top-border"><span class="clip"></span></div>
				<div class="box-container">
					<div class="top-block">
						<span class="earch-icon"></span>
						<span class="title">领券</span>
					</div>
					<div class="cont-block">
                        <div class="registerForm">
                            <div class="componentInput phoneNumber mobileRow">
                                <i class="commonIcon phoneNumberIcon"></i>
                                <input id="mobileInputer" type="text" maxlength="11" placeholder="请输入手机号" style="width: 310px;"/>
                            </div>
                            <div class="componentInput verificationCode messageCodeRow">
                                <i class="commonIcon verificationCodeIcon"></i>
                                <input id="verificationCodeInputer" type="text" maxlength="6" placeholder="请输入验证码" style="width: 188px;">
                                <span class="getVerificationCode">获取短信验证码</span>
                            </div>
							<div type="text" class="registerErrorRow errorBoxContent" style="display:none;">
                                <i class="commonIcon errorIcon"></i><span class="errorHint"><span>
                            </div>
                            <div class="buttonRow">
                                <a href="javascript:void(0);" class="bigOrangeBtn" style="width:345px;">提   交</a>
                            </div>
                        </div>
					</div>
				</div>
				<div class="bottom-border"></div>
			</div>
		</div>
</@html.htmlIndex>