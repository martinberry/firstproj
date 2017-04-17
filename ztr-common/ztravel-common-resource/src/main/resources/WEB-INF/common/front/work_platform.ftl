<script>
	$(function(){
		if(isLogin == 'true'){
			$(".workpla-afterlogin").show() ;
			$(".workpla-login").hide() ;
			$(".workpla-afterlogin").css("opacity","1");
			loginedLong();
		}else{
			$(".workpla-afterlogin").hide() ;
			$(".workpla-login").show() ;
			keepLongPoll();
		}
	})
</script>

<div class="work-platform" style="height: 899px;">
    <div class="workpla-login">
      <span class="logintext">您还未登录账号，请先登录 </span>
      <div style="width:262px;display:none;" class="errorBoxContent">
	    <i class="commonIcon errorIcon"></i><span class="errorHint">输入的用户名或密码有错</span>
	  </div>
	  <div style="height:3px;"></div>
      <div class="componentInput">
      	<i class="commonIcon personHeaderIcon"></i>
        <input type="text" id="accountInputer" placeholder="手机号/邮箱" style="width: 225px;">
      </div>
      <div class="componentInput forgetPW">
      	<i class="commonIcon lockIcon"></i>
        <input type="password" id="passwordInputer" placeholder="请输入密码" style="width: 143px;">
        <a href="javascript:void(0);" class="forgetPWFonts">忘记密码?</a>
      </div>
      <div class="componentInput verificationCode" style="display:none;">
	    <i class="commonIcon verificationCodeIcon"></i>
	    <input id="verifyCodeInputer" type="text" placeholder="请输入验证码" style="width: 120px;">
	    <img id="kaptchaImage">
	  </div>
      <a href="javascript:void(0);" class="commonBtn blueBtn loginBtn" style="width:262px;">登 录</a>
      <div class="login-attach">
        <div class="checkboxContent">
          <label class="checkbox">
            <span class="commonIcon checkboxIcon"></span>
            记住我
          </label>
        </div>
        <a class="register-link" href="javascript:;">免费注册</a>
      </div>

	  <div class="workTwoDimension" style="<#if wpfv.isLogin?c == "false">display<#else>display:none;</#if>">
        <img id="wxQrCodeImg" src="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=${(wpfv.ticket)!''}">
        <div class="workTwoDimensionHint">使用微信扫描二维码直接登录</div>
      </div>
      <input type="hidden" value="${(wpfv.headImageId)!}" id="headImageId">
      <input type="hidden" value="${(wpfv.isLogin)?c}" id="isLogin">
      <input type="hidden" id="loginFailCount" value="${(wpfv.loginFailCount)!'0'}">
    </div>
    <div class="workpla-afterlogin">
      <div class="exit">
        <div class="exitbtn">
          <span class="commonIcon exitIcon"></span>
          <span>退出</span>
        </div>
      </div>
      <div class="userInfo" style="cursor:pointer">
        <span class="leftImg">
          <img <#if wpfv.headImageId??>src="${mediaserver}imageservice?mediaImageId=${(wpfv.headImageId)!}"</#if> style="width:70px;height:70px;" class="round_photo">
        </span>
        <div class="rightUserInfo">
          <span class="rightUsername">${(wpfv.userName)!''}</span>
        </div>
      </div>
      <div class="columnModel">  <!-- 栏目模块 -->
        <ul>
          <li>
            <a href="${memberServer}/order/front/list" target="_blank">
              <span class="orderIcon"></span>
              <span>我的订单</span>
            </a>
          </li>
          <li>
            <a href="${memberServer}/member/wish/list" target="_blank">
              <span class="favorIcon"></span>
              <span>心愿清单</span>
            </a>
          </li>
          <li>
            <a href="${memberServer}/electronicWallet/coupon/index" target="_blank">
              <span class="elecWalletIcon"></span>
              <span>我的钱包</span>
            </a>
          </li>
          <li>
            <a href="${memberServer}/systemnotice/list" target="_blank">
              <span class="messageIcon"></span>
              <span>网站消息</span>
            </a>
          </li>
        </ul>
      </div>
      <div class="tipTab">
        <div class="tab-toggle">
          <ul class="message-tip-tab clearfix">
            <li class="current" id="sn_span">提醒
            	<#if (wpfv.sns)?? && (wpfv.sns?size>0) && !(wpfv.isSnAllRead)>
            		<span></span>
            	</#if>
            </li>
            <li id="pl_span">私信
            	<#if (wpfv.pls)?? && (wpfv.pls?size>0) && !(wpfv.isPlAllRead)>
            		<span></span>
            	</#if>
            </li>
            <li>
            	<div class="slidline"></div>
            </li>
          </ul>
          <div class="tab-cont-block">
            <div class="tab-cont remind remind-cont-block">
              	<div class="remind-tab-cont tab-cont-height">
              		<#if (wpfv.sns)?? && (wpfv.sns?size>0)>
		            	<ul>
	              			<#list wpfv.sns as sn>
			              		<#if sn.hasRead!false>
			              			<#assign isReadClass="">
			              		<#else>
			              			<#assign isReadClass="unread">
			              		</#if>
			              		<#if sn.type == 'ORDERTYPE'>
			              			<#assign typeClass="ordericon">
			              		<#elseif sn.type == 'COUPONTYPE'>
			              			<#assign typeClass="vouchericon">
			              		<#elseif sn.type == 'REMAINTYPE'>
			              			<#assign typeClass="balanceicon">
			              		</#if>
			              		<#if isReadClass != ''>
			              			<#assign combinationClass=isReadClass+"-"+typeClass>
			              		<#else>
			              			<#assign combinationClass=typeClass>
			              		</#if>
			              		<li class="${isReadClass}" value="${(sn.id)!}">
			              			<span class="remind-conmenicon ${combinationClass}"></span><#noescape>${sn.content!''}</#noescape>
			              		</li>
	              			</#list>
	              		</ul>
	              	<#else>
	              		<div class="privletter-empty-block sns-empty" style="margin-top: 79.5px;">
	                      <span class="letter-empty-img"></span>
	                      <p>您暂时还没有提醒哦！</p>
	                    </div>
              		</#if>
	              </div>
	              <a class="more" href="javascript:jumpToSn();">更多</a>
            </div>
            <div class="tab-cont private-letter privletter-cont-block">
              	<div class="privletter-tab-cont tab-cont-height">
              		<#if (wpfv.pls)?? && (wpfv.pls?size>0)>
              		<ul>
		              	<#list wpfv.pls as pl>
		              		<#if pl.hasRead!false>
		              			<#assign isReadClass="">
		              		<#else>
		              			<#assign isReadClass="unread">
		              		</#if>
		              		<li class="${isReadClass}">
		              			<a href="${memberServer}/privateletter/talk/${pl.anotherId!''}" target="_blank">
			              			<div class="privaletter-left">
				                      <img src="${mediaserver}imageservice?mediaImageId=${(pl.anotherHead)!}" style="width:42px;height:42px;" class="round_photo">
				                    </div>
				                    <div class="privaletter-right">
				                      <div class="priletter-top">
				                        <span class="username">${pl.anotherNickname}</span>
				                        <span class="priletter-data">${pl.updateTime}</span>
				                      </div>
				                      <div class="priletter-bottom secondEllipsis">
				                      	<p>
									        ${(pl.msgContent)!}
								        </p>
				                      </div>
				                    </div>
			                    </a>
		              		</li>
		              	</#list>
              		</ul>
              		<#else>
	              		<div class="privletter-empty-block pl-empty" style="margin-top: 79.5px;">
	                      <span class="letter-empty-img"></span>
	                      <p>您暂时还没有私信哦！</p>
	                    </div>
              		</#if>
              	</div>
              	<a class="more" href="javascript:jumpToPl();">更多</a>
            </div>
          </div>
        </div>
      </div>

      <div class="maskingContent" style="top: 122px; height: 100%;<#if wpfv.isLogin?c == "true" && wpfv.wxLogined?c == "false">display;<#else>display:none;</#if> ">
	        <span class="closeIcon"></span>
	        <div>
	            <img class="loadingTwoDimension" src="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=${(wpfv.ticket)!''}">
	        </div>
	        <div class="twoDimensionFonts">扫码二维码</div>
	        <div class="loadingHint">扫码即可登录真旅行微信，随时查看订单状态、咨询24小时线上客服、了解最新旅行产品、与旅行达人互动。</div>
	    </div>
    </div>
    <div class="workplabtn">
    <li class="current" id="nm_span">
    	<#if ((wpfv.sns)?? && (wpfv.sns?size>0) && !(wpfv.isSnAllRead)) || ((wpfv.pls)?? && (wpfv.pls?size>0) && !(wpfv.isPlAllRead))>
    		<span></span>
    	</#if>
    </li>
    </div>
    </div>
<script>
	$(".maskingContent").animate({'top':'122px','height':'100%','opacity':"show"},1000);
	$(".closeIcon").click(function(){
        $(this).parents(".maskingContent").animate({'top':'122px','height':'0','opacity':"hide"},1000);
        window.clearInterval(loginedLongTimer) ;
    });

</script>