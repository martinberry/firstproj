var lock = false ;
$(function(){
	$('.blueSolidBtn').click(function(){
		var msg = {};
		msg.content = $('.bottomFixInput').val();
		msg.authorId = $('#author_id').val();
		msg.anotherId = $('#another_id').val();

		if(!$.trim(msg.content)){
			toggleErrorHint("内容不能为空！") ;
			return;
		}else if(msg.content.length >1000){
			toggleErrorHint("请将内容限定在1000个字符内！") ;
			return;
		}
		if(!lock){
			lock = true ;
			$.ajax({
				type: 'POST',
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				url: wxServer +'/privateletter/addmsg',
				data: JSON.stringify(msg),
				dataType: 'json',
				success: function(response){
					lock = false ;
					if(response.res_code && response.res_code == 'success'){
						toggleErrorHint("消息已发送！") ;
						window.setTimeout(function(){
							  window.location.href=window.location.href;
						},1000);
					}
				},error: function(response){
					lock = false ;
				}
			});
		}
	});

})



function deleteMsg(msgId){
	var letterId = $("#letter_id").val() ;
	if(letterId && msgId){
		$.ajax({
			type : "POST",
			url: wxServer+"/privateletter/deletemsg/"+letterId+"/"+msgId,
			dataType: "json",
			success: function(response){
				if(response.res_code == 'success'){
					  window.location.href=window.location.href;
				}
			}
		});
	}
}

function deleteLetter(){
	var letterId = $("#letter_id").val() ;
	if(letterId){
		$.ajax({
			type : "POST",
			url: wxServer+"/privateletter/deleteletter/"+letterId,
			dataType: "json",
			success: function(response){
				if(response.res_code == 'success'){
					window.location.href= wxServer+"/privateletter/list";
				}
			}
		});
	}
}