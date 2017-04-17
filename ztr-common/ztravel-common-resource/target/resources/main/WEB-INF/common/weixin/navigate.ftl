<#assign imageId=getMemberImageId()>
<#assign isLogin=isMemberLogin()>
<#assign isActive=isMemberActive()>

<span class="acut-menu">
    <span class="menu-pop">
        <a class="acut-box ui-link nomarginR" data-role="none" href="javascript:;">
            <img class="acut-img" src="">
        </a>
        <div class="menu-list-container">
            <i class="arrow-up"></i>
            <ul class="menu-list">
                <li><a href="javascript:jump('/order/weixin/list');">我的订单</a></li>
                <li><a href="javascript:jump('/accountBalance/index');">我的钱包</a></li>
                <#if isLogin>
                    <li style="border-bottom:2px solid #666;"><a href="javascript:jump('/usercenter/weixin/index?src=self');">个人中心</a></li>
                    <li><a href="javascript:logout();">退出</a></li>
                <#else>
                    <li><a href="javascript:jump('/usercenter/weixin/index');">个人中心</a></li>
                </#if>
            </ul>
        </div>
    </span>
</span>
<script>
	$(function(){
//if('${imageId}' != '' && '${isActive?c}' == 'true'){
			if('${imageId}' != ''){
            $(".acut-img").attr("src","${mediaserver!}imageservice?mediaImageId=${imageId!}") ;
		}
		$(".menu-btn").click(function(event){
			$(this).find(".menu-list-container").toggle();
			if($(".menu-list-container").is(":visible")){
				$(document).click(function(){
					$(".menu-list-container").fadeOut("fast");
				});
			}
			event.stopPropagation();
		});


		$(".menu-pop").click(function(event){
            if('${isLogin?c}' != 'true'){
                //弹框登陆
                var self = window.location.href;
                if(self.indexOf("/product/weixin/detail/chooseTrip") > 0 || self.indexOf("/book/weixin/tobook") > 0){
                    $("#accountNaviInputer").val("");
                    $("#passwordNaviInputer").val("");
                    $("#userLogin-dlg").popup("open");
                }else{
                    window.location.href =  wxServer + "/rl/torl";
                }
            }else{
                 var pop = $(this).prev().find(".menu-pop-container");
                if(pop.is(":visible")){
                    pop.slideUp("fast");
                }
                $(this).find(".menu-list-container").toggle();
                if($(".menu-list-container").is(":visible")){
                    $(document).click(function(){
                        $(".menu-list-container").fadeOut("fast");
                    });
                }
                event.stopPropagation();
            }
        });

		countHeaderUnread();
	}) ;

	function jump(where){
        window.location.href = wxServer + where;
	}

	function logout(){
		$.ajax({
		    url: ssoServer + '/sso/wx/logout' ,
		    dataType:'jsonp',
		    jsonp:'callback',
		    success: function(data){
		    	if(data.res_code == 'success'){
		    		window.location.href = window.location.href ;
		    	}
		    }
		});
	}

	function countHeaderUnread(){
	$.ajax({
		type : "POST",
		url: wxServer+"/privateletter/countunread/",
		dataType: "json",
		success: function(response){
			if(response.res_code == 'success'){
				$('#unreadNum').html(response.res_msg);
				if(response.res_msg > 0){
			//		$(".numberFonts").html("(" + response.res_msg + ")") ;
						 $("#menu-img").addClass("has-notice-icon");
						 $("#privateLetters").addClass("has-notice-icon");
				}
			}
		}
	});
}
</script>

    <!--提交弹窗-->
    <div class="submit-dlg" id="userLogin-dlg" data-role="popup" data-history="false" data-transition="pop" data-theme="a" data-overlay-theme="b">
        <div class="ui-grid-a">
            <div class="ui-block-a"><i class="person-icon"></i></div>
            <div class="ui-block-b"><input class="submit-dlg-val" type="text" id="accountNaviInputer" data-cv="required,userName" data-ct="用户名" placeholder="手机号" data-role="none"></div>
        </div>
        <div class="ui-grid-a">
            <div class="ui-block-a"><i class="lock-icon"></i></div>
            <div class="ui-block-b"><input class="submit-dlg-val" type="password" id="passwordNaviInputer" placeholder="密码" data-cv="required" data-ct="用户密码" data-role="none"></div>
        </div>
        <div class="dlg-foot">
            <a class="submit-dlg-confirm" data-role="none" href="javascript:void(0);">登录</a>
        </div>
    </div>

    <div class="ui-content" data-role="popup" id="userLoginError" data-history="false" data-theme="a" data-overlay-theme="b"  data-transition="pop" data-position-to="window" data-dismissible="false">
        <p class="dlg-cnt" id="userLoginErrorCont"></p>
        <div class="dlg-foot">
            <a class="btn-confirm" data-role="none" href="javascript:void(0);" data-rel="back">好的</a>
        </div>
    </div>

<script>

    $(function(){
        $("#userLogin-dlg .submit-dlg-confirm").click(function(){
            userLogin();
        });
    });

    function userLogin(){
            var userName = $("#accountNaviInputer").val();
            var passWord = $("#passwordNaviInputer").val();
            var verifyCode = "";
            passWord = escape(passWord) ;
            _paq.push(['trackEvent', 'login', 'ztrorderlogin', userName]);
            $.ajax({
                url: ssoServer + '/sso/wx/login' ,
                data: 'account=' + userName + "&password=" + passWord + "&rememberMe=false&verifyCode=" + verifyCode ,
                dataType:'jsonp',
                jsonp:'callback',
                success: function(data){
                     $("#userLogin-dlg").popup({
                            afterclose: function() {
                                if(data.res_code == 'EF_MEMB_1027'){
                                    $("#userLoginErrorCont").html("用户名或密码错误");
                                    $("#userLoginError").popup("open");
                                }else if(data.res_code == 'EF_MEMB_1026'){
                                    $("#userLoginErrorCont").html("用户名或密码错误");
                                      $("#userLoginError").popup("open");
                                }else if(data.res_code == 'EF_MEMB_10261'){
                                    $("#userLoginErrorCont").html("账号异常,请联系客服");
                                      $("#userLoginError").popup("open");
                                }else if(data.res_code == 'EF_MEMB_1028'){
                                    $("#userLoginErrorCont").html(data.res_msg);
                                    $("#userLoginError").popup("open");
                                }else if(data.res_code == 'EF_MEMB_1024'){
                                    $("#userLoginErrorCont").html("密码为8-28位数字、字母或常用字符组合");
                                     $("#userLoginError").popup("open");
                                }else if(data.res_code == 'SF_MEMB_1053'){
                                    $("#userLoginErrorCont").html("输入的账号或密码不能为空");
                                     $("#userLoginError").popup("open");
                                }else if(data.res_code == 'SF_MEMB_1005'){
                                    window.location.href = window.location.href;
                                }
                            }
                        });
                     $("#userLogin-dlg").popup("close");
                }
            });

        }
</script>
