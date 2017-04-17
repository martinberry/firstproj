<script type="text/html" id="common-login-box">
        <div style="width:262px;display:none;" class="errorBoxContent">
		    <i class="commonIcon errorIcon"></i><span class="errorHint">输入的用户名或密码有错</span>
		</div><br/>
        <div class="componentInput">
            <i class="commonIcon personHeaderIcon"></i>
            <input type="text" id="accountInputer" placeholder="手机号/邮箱" style="width: 225px;">
        </div><br/>
        <div class="componentInput forgetPW">
            <i class="commonIcon lockIcon"></i>
            <input type="password" id="passwordInputer" placeholder="请输入密码" style="width: 143px;">
            <a href="javascript:void(0);" class="forgetPWFonts">忘记密码?</a>
        </div><br/>
        <div class="componentInput verificationCode" style="display:none;">
		    <i class="commonIcon verificationCodeIcon"></i>
		    <input id="verifyCodeInputer" type="text" placeholder="请输入验证码" style="width: 120px;">
		    <img id="kaptchaImage">
		</div><br/>
        <a href="javascript:void(0);" class="commonBtn blueBtn loginBtn" style="width:263px;">登 录</a>
        <div class="login-attach">
            <div class="checkboxContent">
                <label class="checkbox">
                    <span class="commonIcon checkboxIcon"></span>记住我
                </label>
            </div>
            <a class="register-link" href="javascript:void(0);">免费注册</a>
        </div>
        <input type="hidden" value="${headImageId!''}" id="headImageId">
		<input type="hidden" id="loginFailCount" value="${loginFailCount!'0'}">
    </script>