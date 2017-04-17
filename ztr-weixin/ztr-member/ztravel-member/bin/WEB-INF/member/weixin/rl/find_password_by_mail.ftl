<#import "/common/weixin/htmlIndex.ftl" as html />
<#import "/common/weixin/headerBar.ftl" as header />

<@html.htmlIndex remoteJsFiles=[] remoteCssFiles=["mstatic/css/register-login.css"] localCssFiles=[]
                                  localJsFiles=["member/weixin/find_password_by_mail.js","common/weixin/common_utils.js"] title="邮箱找回密码">


<body>
<div data-role="page">
    <@header.headerBar title="邮箱找回密码"></@header.headerBar>
    <div class="wrapper">
        <div class="wrap-space"></div>
        <#if errorHint??>
	        <div class="main-row">
	            <input class="row-val" type="text" value="${errorHint!''}" data-role="none">
	        </div>
        </#if>
        <div class="main-row">
            <div class="row-til">邮箱地址</div>
            <input class="row-val" type="text" id="emailInputer" value="" data-role="none" maxlength="50">
            <img class="row-close" src="${host}/mstatic/images/icon-close.png">
        </div>
        <div class="main-next">
            <a class="link-fin" href="javascript:void(0);">提 交</a>
        </div>
    </div>
    <div class="ui-content" data-role="popup" id="alert-dialog-email" data-history="false" data-theme="a" data-overlay-theme="b">
        <p class="dlg-cnt" id="errHintMsgEmail" >邮箱格式有误</p>
        <div class="dlg-foot">
            <a class="btn-confirm" data-role="none" href="javascript:void(0);" data-rel="back">好的</a>
        </div>
    </div>

</div>
</body>
</@html.htmlIndex>
