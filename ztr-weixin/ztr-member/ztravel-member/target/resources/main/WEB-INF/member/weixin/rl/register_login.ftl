<#import "/common/weixin/htmlIndex.ftl" as html />
<#import "/common/weixin/headerBar.ftl" as header />

<@html.htmlIndex remoteJsFiles=[] remoteCssFiles=["mstatic/css/register-login.css"] localCssFiles=[]
                                  localJsFiles=["member/weixin/register_login.js","common/password_verify.js","common/weixin/common_utils.js"] title="登录注册">

<body>
    <div data-role="page">
		<@header.headerBar title="欢迎来到真旅行"></@header.headerBar>
		<input type="hidden" id="toLogin" value="true"/>
        <input type="hidden" id="backUrl" value="${backUrl!}"/>
		<div class="wrapper">
			<div class="mloginTab clearfix">
				<div class="tab">
                    <span class="mloginBtn current">登录</span>
					<span class="mregisterBtn">注册</span>
				</div>
			</div>

            <div class="cont-block">
                <div class="loginFrame">
                    <ul>
                        <li>
                            <i class="person-icon fl"></i>
                            <div class="input-box">
                                <input type="tel" class="mobileInputer" maxlength="11" value="" placeholder="手机号" data-role="none">
                                <i class="clear-input-icon"></i>
                            </div>
                        </li>
                        <li>
                            <i class="lock-icon fl"></i>
                            <div class="input-box">
                                <input type="password" class="passwordInputer" maxlength="28" value="" placeholder="请输入8-28位密码" data-role="none">
                                <i class="clear-input-icon"></i>
                            </div>
                        </li>
                        <li class="loginVerify" style="display:none">
                            <i class="safe-icon fl"></i>
                            <div class="input-box">
                                <input type="hidden" id="loginFailCount" value="${loginFailCount!'0'}">
                                <input type="text" class="loginVerifyCodeInputer" value="" placeholder="请输入验证码" data-role="none">
                                <img id="kaptchaImage">
                                <i class="clear-input-icon"></i>
                            </div>
                        </li>
                        <li class="last">
                            <label class="remindPwd fl">
                                <label class="cus_input_container">
                                    <input type="checkbox" value="" data-role="none">
                                </label>记住密码</label>
                            <a class="findPwd fr" href="javascript:findPassword();" data-role="none">找回密码</a>
                        </li>
                    </ul>

                    <#--<input class="bottom-btn" type="button" name="" value="登录" data-role="none">-->
                    <a class="link-fin ui-link login-regis-btn" href="javascript:void(0);" id="bottom-login-btn">登 录</a>
                    <#if existsOpenId>
                    <div class="orLine">
                        <div class="innerOrLine">
                            <span>或者</span>
                        </div>
                    </div>
                    <a class="buttonLoginWechat ui-link login-regis-btn" href="javascript:;" id="bottom-wxlogin-btn"><i class="icon_wechat"></i>微信登录</a>
                    </#if>
                </div>

				<div class="registerFrame">
					<form method="" action="">
						<ul>
						    <li>
						    	<i class="person-icon fl"></i>
						    	<div class="input-box">
						    		<input type="tel" class="mobileInputer" maxlength="11" value="" placeholder="手机号" data-role="none">
						    		<i class="clear-input-icon"></i>
						    	</div>
						    </li>
						    <li>
						    	<i class="safe-icon fl"></i>
						    	<div class="input-box">
						    		<input type="text" class="verifyCodeInputer" maxlength="6" value="" placeholder="验证码" data-role="none">
						    		<span class="checkCodeBtn">获取验证码</span>
						    		<i class="clear-input-icon"></i>
						    	</div>
						    </li>
						    <li class="placeholder"></li>
						    <li>
						    	<i class="lock-icon fl"></i>
						    	<div class="input-box">
						    		<input type="password" class="passwordInputer" maxlength="28" value="" placeholder="请输入8-28位密码" data-role="none">
						    		<i class="clear-input-icon"></i>
						    	</div>
						    </li>
						</ul>
					<#--	<input class="bottom-btn" type="button" name="" value="注册" data-role="none">-->
						<a class="link-fin ui-link login-regis-btn" href="javascript:void(0);" id="bottom-regist-btn">注 册</a>
					</form>
				</div>

            </div>
        </div>
    </div>
	<div class="ui-content" data-role="popup" id="alert-dialog" data-history="false" data-theme="a" data-overlay-theme="b">
        <p class="dlg-cnt">验证码获取失败,请重新获取</p>
        <div class="dlg-foot">
            <a class="btn-confirm" data-role="none" href="javascript:void(0);" data-rel="back">好的</a>
        </div>
    </div>

    <div class="ui-content" data-role="popup" id="alert-dialog-registerlogin" data-history="false" data-theme="a" data-overlay-theme="b">
        <p class="dlg-cnt" id="errHintregisterlogin"></p>
        <div class="dlg-foot">
            <a class="btn-confirm" data-role="none" href="javascript:void(0);" data-rel="back">好的</a>
        </div>
    </div>
</body>
</@html.htmlIndex>
