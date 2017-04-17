var commentMaxLength = 1000;
var blankCommentReg = /^[\s]+$/;

$(function(){
	//评星
	$("#stars li").bind("click", function () {
		if( !$(this).hasClass("yellowStar") ){
			$(this).toggleClass("yellowStar");
		}
		$(this).prevAll().addClass("yellowStar");
		$(this).nextAll().removeClass("yellowStar");
	});

	//字数校验
	$("#commentTextarea").keyup(function(){
		var comment = $(this).val();
		var length = comment.length;
		if( length > commentMaxLength ){
			$(this).val(comment.substring(0,commentMaxLength));
			length = commentMaxLength;
		}
		$("#remainNum").text(commentMaxLength - length);
	});

});

//提交评价
function submitComment(){
	var params = $("#commentForm").serializeObject();
	params.comment = $("#commentTextarea").val();
	params.stars = $("#stars").children(".yellowStar").length;

	if( params.comment.length == 0 || blankCommentReg.test(params.comment) ){
		showErrorSlide("您还没有填写评价！");
	}else{
		$.ajax({
			type : "POST",
			url : basepath + "/order/comment/submit",
			data : JSON.stringify(params),
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			dataType : "json",
			success : function(resp) {
				if( resp.res_code == "SF_ORDR_2001" ){
					$("#sucTips").text("发布成功！");
	                $("#sucTips").slideDown("normal", function(){
	                    setTimeout(function(){
	                        $("#sucTips").slideUp();
	                        window.location.href = basepath + "/order/front/list";
	                    }, 2000)
	                }).removeClass("hidden");
				}else if( resp.res_code == "FF_ORDR_2007"){
					showErrorSlide(resp.res_msg);
				}else if( resp.res_code == "FF_ORDR_2005" || resp.res_code == "FF_ORDR_2002" ){
					showErrorSlide("发布评论失败！");
				}else if( resp.res_code == "SENSITIVE_WORD"){
					showErrorSlide("评论包含敏感词汇!");
				}
				$("#submitCommentBtn").attr("onclick", "submitComment()");  //ajax请求成功返回后，enable发布评论按钮
			}
		});
		$("#submitCommentBtn").removeAttr("onclick");  //ajax请求发出后，disable发布评论按钮
	}
}

function showErrorSlide(errorMsg){
	$("#errTips").text(errorMsg);
	$("#errTips").slideDown("normal", function(){
		setTimeout(function(){
			$("#errTips").slideUp();
		}, 2000)
	}).removeClass("hidden");
}

$.fn.serializeObject = function(){
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};