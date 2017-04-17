<#import "/common/weixin/htmlIndex.ftl" as html />
<#import "/common/weixin/headerBar.ftl" as header />

<@html.htmlIndex remoteJsFiles=[] remoteCssFiles=["mstatic/css/register-login.css"] localCssFiles=[]
                                  localJsFiles=[] title="找回密码">


<body>
<div data-role="page">
    <@header.headerBar title="找回密码"></@header.headerBar>
    <div class="wrapper">
        <div class="wrap-box">
            <div class="type-one">
                <span class="img-box"><img class="type-img" src="${host}/mstatic/images/icon_phone.png"></span>
                <span class="type-txt mobile">获取手机动态码登录</span>
            </div>
            <div class="type-two">
                <span class="mail-box"><img class="type-mail" src="${host}/mstatic/images/icon-mail.png"></span>
                <span class="type-txt mail">通过邮箱找回密码</span>
            </div>
        </div>
    </div>
</div>
</body>
<script>
	$(function(){
		$(".mobile").click(function(){
			window.location.href = wxServer + "/rl/findPasswordByMobile" ;
		})

		$(".mail").click(function(){
			window.location.href = wxServer + "/rl/findPasswordByMail" ;
		})
	})

</script>
</@html.htmlIndex>
