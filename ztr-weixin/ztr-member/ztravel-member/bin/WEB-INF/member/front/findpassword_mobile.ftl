<#import "/common/front/htmlIndex.ftl" as html/>
<@html.htmlIndex curModule="用户" title="找回密码-手机" jsFiles=["member/front/common_utils.js", "member/front/find_password_mobile.js"] cssFiles=["css/client/register.css"]>
		<div class="main-wrapper" id="main-wrapper">
			<div class="main-box" id="main-box" style="width:500px;">
				<div class="top-border"><span class="clip"></span></div>
				<div class="box-container">
					<div class="top-block">
						<span class="findPsw-icon"></span>
						<span class="title">找回密码</span>
					</div>
					<div class="cont-block">
                        <div class="findPswBox">
                            <div type="text" class="registerErrorRow errorBoxContent" style="display:none;">
                                <i class="commonIcon errorIcon"></i><span class="errorHint"><span>
                            </div>
                            <div class="componentInput phoneNumber mobileRow">
                                <i class="commonIcon phoneNumberIcon"></i>
                                <input id="mobileInputer" type="text" maxlength="11" placeholder="请输入手机号" style="width: 309px;"/>
                            </div>
                            <div class="componentInput verificationCode messageCodeRow">
                                <i class="commonIcon verificationCodeIcon"></i>
                                <input id="verificationCodeInputer" type="text" maxlength="6" placeholder="请输入验证码" style="width: 187px;">
                                <span class="getVerificationCode">获取短信验证码</span>
                            </div>
                            <div class="buttonRow">
                                <a href="javascript:void(0);" class="bigBlue11b9b7Btn" style="width:345px;">提   交</a>
                            </div>
                            <div class="goBackRow">
                                <i class="goBackIcon"></i><a href="javascript:history.go(-1);">返回</a>
                            </div>
                        </div>
					</div>
				</div>
				<div class="bottom-border"></div>
			</div>
		</div>
</@html.htmlIndex>