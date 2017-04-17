$(function(){
	$(".login-entrance .login").click(function(event){
	    workplatformChange();
	    event.stopPropagation();
	    $(".maskingContent").animate({'top':'122px','height':'100%','opacity':"show"},1000);
	});

	$('.after-login .userName').click(function(event){
		$('.work-platform').height($(window).height());
        TabcontControl();
	    workplatformChange();
	    event.stopPropagation();
	});


	//免费注册
	$("body").delegate(".register.commonBtn","click",function(){
		window.location.href = ssoServer + '/sso/register' ;
	}) ;

	$(".closeIcon").click(function(){
        $(this).parents(".maskingContent").animate({'top':'122px','height':'0','opacity':"hide"},1000);
		window.clearInterval(loginedLongTimer) ;
    });

})
