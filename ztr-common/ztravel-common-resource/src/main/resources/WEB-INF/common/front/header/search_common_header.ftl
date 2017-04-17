<header>
	<div class="head clearfix">
	  <a href="${memberServer}">
	  	<div class="logo"></div>
	  </a>
	  <#include "../search_module.ftl"/>
	  <div class="top-right-block">
	      <div class="login-entrance">
	          <a class="login commonBtn" href="javascript:;">登录</a><a class="register commonBtn" href="javascript:;">注册</a>
	      </div>
	      <div class="after-login">
	          <span class="userName">
	              <span class="commonIcon userIcon"></span>
	              <em>${(wpfv.userName)!''}</em>
	          </span>
	      </div>
	  </div>
	</div>
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
</header>