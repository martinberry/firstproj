$(function(){
	/*后台未读消息数*/
	$.ajax({
		type: "POST",
		url: basepath + '/operation/message/countUnread',
		dataType : "json",
		success: function (result) {
			if(result.res_code == 'success'){
				$('.emailFonts').html(result.res_msg);
			}else{
				console.log(result);
			}
		}
	});
});