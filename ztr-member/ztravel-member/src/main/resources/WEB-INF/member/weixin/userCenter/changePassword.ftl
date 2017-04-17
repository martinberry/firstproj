<#import "/common/weixin/htmlIndex.ftl" as html />
<#import "/common/weixin/headerBar.ftl" as header />

<@html.htmlIndex remoteJsFiles=[] remoteCssFiles=["mstatic/css/userCenter.css"] localCssFiles=[]
                                  localJsFiles=["common/password_verify.js", "member/weixin/changePassword.js"] title="真旅行">
    <div class="viewport" data-role="page">
        <div class="main-contain" data-role="content">
            <@header.headerBar title="修改密码">
            </@header.headerBar>
            <div class="row-box">
                <span class="row-cell">原密码</span>
                <input type="password" id="oldPassword" placeholder="填写原密码" data-role="none">
            </div>
            <div class="row-box">
                <span class="row-cell">新密码</span>
                <input type="password" id="newPassword" placeholder="填写新密码" data-role="none">
                <div class="flag-box">
                    <span class="type02">弱</span>
                </div>
            </div>
            <div class="row-box">
                <span class="row-cell">确认密码</span>
                <input type="password" id="newPasswordRetype" placeholder="再次填写确认" data-role="none">
            </div>
            <a class="sub-link" href="javascript:void(0);" id="submitPwdChange">提  交</a>
            <!--<div class="some-type">
                <span class="flag">*</span><span class="type01">强</span><span class="type02">弱</span><span class="type03">中</span>
            </div> -->
        </div>
    </div>
    <div class="ui-content" data-role="popup" id="alert-dialog" data-history="false" data-theme="a" data-overlay-theme="b"  data-transition="pop" data-position-to="window" data-dismissible="false">
        <p class="dlg-cnt">验证码获取失败,请重新获取</p>
        <div class="dlg-foot">
            <a class="btn-confirm" data-role="none" href="javascript:void(0);" data-rel="back">好的</a>
        </div>
    </div>
</@html.htmlIndex>