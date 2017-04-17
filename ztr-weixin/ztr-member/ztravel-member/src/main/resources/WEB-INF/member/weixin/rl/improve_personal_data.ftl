<#import "/common/weixin/htmlIndex.ftl" as html />
<#import "/common/weixin/headerBar.ftl" as header />

<@html.htmlIndex remoteJsFiles=[] remoteCssFiles=["mstatic/css/register-login.css"] localCssFiles=["member/front/round_image.css"]
                                  localJsFiles=["member/weixin/improve_personal_data.js","common/weixin/common_utils.js"] title="完善信息">
<body>
	<div data-role="page">
	<@header.headerBar title="注册成功，您可以完善个人资料或跳过"></@header.headerBar>
	    <div class="wrapper">
	        <div class="pop-head">
	            <img class="pop-img round_photo" src="${mediaserver}imageservice?mediaImageId=${randomDefaultImageId!''}">
	        </div>
	        <div class="wrap-row">
	            <img class="row-img" src="${host}/mstatic/images/telicon.png">
	            <div class="row-box">
	                <input type="text" id="uName" value="${randomNickName!''}" placeholder="用户名" data-role="none">
	                <img class="row-icon" src="${host}/mstatic/images/icon-close.png">
	            </div>
	        </div>
	        <div class="wrap-row">
	            <img class="row-mail" src="${host}/mstatic/images/icon-mail.png">
	            <div class="row-box">
	                <input type="email" id="uEmail" placeholder="确保账户安全,请输入准确邮箱地址" data-role="none" maxlength="50">
	                <img class="row-icon" src="${host}/mstatic/images/icon-close.png">
	            </div>
	        </div>
	        <div class="wrap-row">
	            <img class="row-cnt" src="${host}/mstatic/images/icon-contact.png">
	            <div class="row-box">
	                <input type="text" id="uPhone" placeholder="请输入推荐人登录名（手机号）" data-role="none">
	                <img class="row-icon" src="${host}/mstatic/images/icon-close.png">
	            </div>
	        </div>
	        <div class="wrap-look">
	            <img class="icon-qus" src="${host}/mstatic/images/icon-qus.png"><a href="" id="jumpToSharePlan">查看“推荐人奖励计划”</a>
	        </div>
	        <div class="wrap-link">
	            <a class="link-fin" href="javascript:save();">完 成</a>
	            <a class="link-ign" href="javascript:skip();" id="skip">跳过，随便看看</a>
	        </div>
	    </div>
	    <div class="ui-content" data-role="popup" id="alert-dialog-improve" data-history="false" data-theme="a" data-overlay-theme="b">
        <p class="dlg-cnt" id="errHintImprove"></p>
        <div class="dlg-foot">
            <a class="btn-confirm" data-role="none" href="javascript:void(0);" data-rel="back">好的</a>
        </div>
    </div>
	</div>
</body>
</@html.htmlIndex>
