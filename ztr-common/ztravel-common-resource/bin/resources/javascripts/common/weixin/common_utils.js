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

function toggleErrorHint(hint, win, hintLocation){
	if(typeof(win) == undefined || win == null){
		win = "#alert-dialog";
	}
	if(typeof(hintLocation) == undefined || hintLocation == null){
		hintLocation = ".dlg-cnt";
	}
	$(hintLocation).html(hint) ;
	$(win).popup('open') ;
}