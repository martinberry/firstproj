$(function(){

	$('html,body').animate({scrollTop:$('.form-horizontal').offset().top-(window.innerHeight*0.618)}, 800);
	$('#msgContent').val('');

	$('#submitMsgBtn').click(function(){
		var msg = {};
		msg.content = $('#msgContent').val();
		msg.authorId = $('#author_id').val();
		msg.anotherId = $('#another_id').val();

		if(!$.trim(msg.content)){
			$('.modal-body p').html("内容不能为空！");
			$("#ac-HintWindow").modal("show");
			return;
		}else if(msg.content.length >1000){
			$('.modal-body p').html("请将内容限定在1000个字符内！");
			$("#ac-HintWindow").modal("show");
			return;
		}

		$.ajax({
			type: 'POST',
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			url: basepath +'/privateletter/addmsg',
			data: JSON.stringify(msg),
			dataType: 'json',
			success: function(response){
				if(response.res_code && response.res_code == 'success'){
					$('.modal-body p').html("消息已发送！");
					$("#ac-HintWindow").modal("show");
					window.setTimeout(function(){
						window.location.reload();
					},1000);
				}
			},
		});
	});

	$(".modal").modal({
        backdrop:"static",
        keyboard: false,
        show: false
    });
	resizeModalTop();
    $(window).resize(function(){
        var top = ($(window).height() - $(".modal-dialog:visible").height()) / 2;
        $(".modal-dialog").css({
            "margin-top": top + "px"
        });
    });
});

function deleteMsg(msgId){
	var letterId = $('#letter_id').val();
	if(letterId && msgId){
		$.ajax({
			type : "POST",
			url: basepath+"/privateletter/deletemsg/"+letterId+"/"+msgId,
			dataType: "json",
			success: function(response){
				if(response.res_code == 'success'){
					window.location.reload();
				}
			},
		});
	}
}

function deleteLetter(letterId){
	$("#delletterWindow").modal("show");
	$(".commonBtn.blueBtn.delete").click(function(){
	if(letterId){
		$.ajax({
			type : "POST",
			url: basepath+"/privateletter/deleteletter/"+letterId,
			dataType: "json",
			success: function(response){
				if(response.res_code == 'success'){
					window.location.href = basepath + "/privateletter/list"
				}
			},
		});
	}
	});
}
function resizeModalTop() {
    $('.modal').on('show.bs.modal', function (e) {
        var top = ($(window).height() - $(this).find(".modal-dialog").height()) / 2;
        $(this).find(".modal-dialog").css({
            "margin-top": top + "px"
        });
    });
}