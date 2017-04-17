<#import "/common/weixin/htmlIndex.ftl" as html />
<#import "/common/weixin/headerBar.ftl" as header />

<@html.htmlIndex remoteJsFiles=[] remoteCssFiles=["mstatic/css/register-login.css"] localCssFiles=[]
                                  localJsFiles=["common/password_verify.js","common/weixin/common_utils.js","member/weixin/reset_password_by_mobile.js"] title="重置密码">


<body>
<div data-role="page">
    <@header.headerBar title="重置密码"></@header.headerBar>
    <div class="wrapper">
        <div class="wrap-space"></div>
        <div class="reset-row">
            <input class="reset-val" type="password" id="newPasswordInputer" placeholder="新密码，8~28位数字字母的组合，区分大小写" data-role="none">
            <span class="type03" id="stronger">弱</span>
        </div>
        <div class="wrap-space"></div>
        <div class="reset-row">
            <input class="reset-val" type="password" id="againPasswordInputer" placeholder="再次确认密码" data-role="none">
        </div>
        <div class="main-next">
            <a class="link-fin" href="javascript:void(0);">完 成</a>
        </div>
    </div>
    <div class="ui-content" data-role="popup" id="alert-dialog" data-history="false" data-theme="a" data-overlay-theme="b">
        <p class="dlg-cnt"></p>
        <div class="dlg-foot">
            <a class="btn-confirm" data-role="none" href="javascript:void(0);" data-rel="back">好的</a>
        </div>
    </div>
</div>
</body>
</@html.htmlIndex>
