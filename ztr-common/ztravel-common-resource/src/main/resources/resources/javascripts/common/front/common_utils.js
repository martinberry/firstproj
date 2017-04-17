var isMobile = /^(?:13\d|14\d|15\d|17\d|18\d)\d{8}$/ ;

function trim(selector){
	return $(selector).val($(selector).val().replace(/\ +/g,"")).val() ;
}

function isErrorHintDisplay($trigger){
	if(typeof($trigger) == undefined || $trigger == null){
		$trigger = ".errorHint";
	}
	if($($trigger).parent().css("display") == 'none')
		return false
	else
		return true ;
}

function toggleErrorHint(hint,$trigger){
	if(typeof($trigger) == undefined || $trigger == null){
		$trigger = ".errorHint";
	}
	if(hint == ''){
		$($trigger).parent().css("display","none") ;
	}else{
		$($trigger).html(hint) ;
		$($trigger).parent().css("display","") ;
	}
}