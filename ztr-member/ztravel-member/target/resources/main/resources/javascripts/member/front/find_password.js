$(function(){
	$(".findByMobile").click(function(){
		$(".findByMobile").addClass("current") ;
		$(".findByEmail").removeClass("current") ;
		window.location.href = ssoServer + '/sso/findPasswordByMobile' ;
	});

	$(".findByEmail").click(function(){
		$(".findByEmail").addClass("current") ;
		$(".findByMobile").removeClass("current") ;
		window.location.href = ssoServer + '/sso/findPasswordByEmail' ;
	});


});