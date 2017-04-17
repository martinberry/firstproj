<#import "/common/weixin/htmlIndex.ftl" as html />
<#import "/common/weixin/headerBar.ftl" as header />

<@html.htmlIndex remoteJsFiles=[] remoteCssFiles=["mstatic/css/register-login.css"] localCssFiles=[]
                                  localJsFiles=["member/weixin/find_password_by_mobile.js","common/weixin/common_utils.js"] title="手机找回密码">


<body>
<div data-role="page">
    <@header.headerBar title="手机找回密码"></@header.headerBar>
    <div class="wrapper">
        <div class="wrap-space"></div>
        <div class="main-row">
            <div class="row-til">手机号码</div>
            <input class="row-val" id='mobileInputer' type="text" value="" data-role="none">
            <img class="row-close" src="${host}/mstatic/images/icon-close.png">
        </div>
        <div class="main-row01">
            <div class="row-til">验证码</div>
            <input type="text" class="row-input" id="verifyCodeInputer" maxlength="6" value="" data-role="none">
            <span class="checkCodeBtn">获取验证码</span>
            <div style="clear: both;"></div>
        </div>
        <div class="main-next">
            <a class="link-fin" data-transition="slidefade">下一步</a>
        </div>
    </div>
    <div class="ui-content" data-role="popup" id="alert-dialog-mobile" data-history="false" data-theme="a" data-overlay-theme="b">
        <p class="dlg-cnt" id="errHintMsgMobile"></p>
        <div class="dlg-foot">
            <a class="btn-confirm" data-role="none" href="javascript:void(0);" data-rel="back">好的</a>
        </div>
    </div>
</div>
</body>
</@html.htmlIndex>
