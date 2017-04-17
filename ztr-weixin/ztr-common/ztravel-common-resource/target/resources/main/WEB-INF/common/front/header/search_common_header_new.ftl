
  <a href="${memberServer}" class="index_logo"></a>
  <div class="index_top-right-block">
      <div class="operate login-entrance" >
          <a class="login" href="javascript:;">登录</a><a class="register" href="javascript:window.location.href='${ssoServer}/sso/register';">注册</a>
      </div>
      <div class="after-login">
        <span class="userName">
        	<img class="avatar-img" id="home_member_pic" <#if wpfv.headImageId??>src="${mediaserver}imageservice?mediaImageId=${(wpfv.headImageId)!}"</#if>>
  			<em>${(wpfv.userName)!''}</em>
		</span>
      </div>
  </div>
  
  <#include "../search_module_new.ftl"/>
  <script>
	$(function(){
		if(isLogin == 'true'){
			$(".after-login").show() ;
			$(".login-entrance").hide() ;
			$("body").delegate(".after-login","click",function(){
				$('.work-platform').height($(window).height());
		        TabcontControl();
		    }) ;
		}else{
			$(".after-login").hide() ;
			$(".login-entrance").show() ;
		}
	})
  </script>
