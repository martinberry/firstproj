<#import "/common/opera/htmlIndex.ftl" as html/>
<@html.htmlIndex jsFiles=["operator/signin.js"] cssFiles=["css/maintain/login.css"] curModule="员工登录" title="登录页">

<script>
	var errorMsg = "";
</script>
<div class="login-wrapper">
        <div class="loginArea">
            <div class="loginBox">
                <div class="loginBoxRow">
                    <a href="${basepath}/login" class="logo-login"></a>
                </div>
                <div class="loginBoxRow fonts-title">真旅行后台管理系统</div>
                <div class="loginBoxRow errorBox-field" style="display: none;">
                    <div class="errorBox"><i class="errorIcon"></i><span></span></div>
                </div>
                <div class="loginBoxRow userName-field">
                    <label class="userIcon"></label>
                    <i class="delIcon"></i>
                    <input type="text" class="userName" placeholder="用户名"/>
                </div>
                <div class="loginBoxRow pwd-field">
                    <label class="pwdIcon"></label>
                    <input type="password" class="userPwd" placeholder="密码"/>
                </div>
                <div class="loginBoxRow authCode-field">
                    <input type="text" class="authCode" placeholder="请输入验证码"/>
                    <img id="imgCaptcha" class="authCodeImg" onclick="$(this).attr('src', basepath + '/captcha/' + Math.floor(Math.random()*100) ).fadeIn();"/>
                </div>
                <div class="loginBoxRow autoLogin">
                    <label class="checkedBox noCheckedBox"></label>
                    <span>记住我</span>
                </div>
                <div class="loginBoxRow loginButton">
                    <button class="loginBtn">登录</button>
                </div>
            </div>

        </div>
    </div>

</@html.htmlIndex>
