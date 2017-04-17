<#import "/common/weixin/htmlIndex.ftl" as html />
<#import "/common/weixin/headerBar.ftl" as header />

<@html.htmlIndex remoteJsFiles=[] remoteCssFiles=["mstatic/css/register-login.css"] localCssFiles=[]
                                  localJsFiles=["member/weixin/bind_login.js","common/password_verify.js","common/weixin/common_utils.js"] title="会员登录">

    <body>
    <div data-role="page">

        <@header.headerBar title="已有真旅行会员账号"></@header.headerBar>
        <input type="hidden" id="backOrigUrl" value="${backOrigUrl!}"/>
        <div class="wrapper">
            <div class="cont-block">
                <div class="loginFrame" style="display: block">
                    <div class="tipsBox">您可以将已有账号和微信账号进行绑定</div>
                    <ul>
                        <li>
                            <i class="person-icon fl"></i>
                            <div class="input-box">
                                <input type="tel" class="mobileInputer"  name="" value="" placeholder="手机号" data-role="none">
                                <i class="clear-input-icon"></i>
                            </div>
                        </li>
                        <li>
                            <i class="lock-icon fl"></i>
                            <div class="input-box">
                                <input type="password" class="passwordInputer"  name="" value="" placeholder="请输入8-18位密码" data-role="none">
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
                            <a class="findPwd fr fc_3fc0ae" href="javascript:findPassword();" data-role="none">忘记密码</a>
                        </li>
                    </ul>
                    <a class="link-fin ui-link login-regis-btn" href="javascript:void(0);" id="bottom-wxbindandlogin-btn">绑定微信并登录</a>
                    <div class="orLineWiden">
                        <div class="innerOrLine">
                            <span>或者暂无真旅行会员账号</span>
                        </div>
                    </div>
                    <a class="buttonLoginWechat ui-link login-regis-btn" href="javascript:;" id="bottom-wxregisterandlogin-btn"><i class="icon_wechat"></i>微信直接登录</a>
                </div>
            </div>
        </div>
    </div>

     <div class="ui-content" data-role="popup" id="alert-dialog-registerlogin" data-history="false" data-theme="a" data-overlay-theme="b">
        <p class="dlg-cnt" id="errHintregisterlogin"></p>
        <div class="dlg-foot">
            <a class="btn-confirm" data-role="none" href="javascript:void(0);" data-rel="back">好的</a>
        </div>
    </div>

<script type="text/javascript">
    $(function(){

        FastClick.attach(document.body);

        setBodyMinHeight();

        window.resize = function() {
            setBodyMinHeight();
        }
    });

    function setBodyMinHeight() {
        var topValue = window.innerHeight - $(".headerBar").outerHeight() - $(".mloginTab").outerHeight();
        $(".cont-block").css({
            "height": topValue + "px"
        });
    }


</script>
</body>
</@html.htmlIndex>