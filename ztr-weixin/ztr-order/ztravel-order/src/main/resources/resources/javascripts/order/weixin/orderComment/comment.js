/**
 *
 */
var commentMaxLength = 1000;
var blankCommentReg = /^[\s]+$/;
var comment = {
		submitComment : function(){
			var params = {};
			params.comment = $("#commentTextarea").val();
			params.orderId = $("#orderId").val();
			params.stars = $("#commentForm em.active").length;
			if( params.comment.length == 0 || blankCommentReg.test(params.comment) ){
				comment.showErrorSlide("您还没有填写评价！");
			}else{
				$.ajax({
					type : "POST",
					url : wxServer + "/order/weixin/comment/submit",
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
			                        window.location.href = wxServer + "/order/weixin/list";
			                    }, 2000)
			                }).removeClass("hidden");
						}else if( resp.res_code == "FF_ORDR_2007"){
							comment.showErrorSlide(resp.res_msg);
						}else if( resp.res_code == "FF_ORDR_2005" || resp.res_code == "FF_ORDR_2002" ){
							comment.showErrorSlide("发布评论失败！");
						}else if( resp.res_code == "SENSITIVE_WORD"){
							comment.showErrorSlide("评论包含敏感词汇!");
						}
						$("#submitCommentBtn").attr("onclick", "comment.submitComment()");  //ajax请求成功返回后，enable发布评论按钮
					}
				});
				$("#submitCommentBtn").removeAttr("onclick");  //ajax请求发出后，disable发布评论按钮
			}
		},
		initStars : function(){
			/*点击评论星星*/
			$('.evaluateStars em').click(function(){
				$('.evaluateStars em').removeClass('active');
				var starnum=$(this).index();
				for(var i=0;i<starnum;i++){
					$('.evaluateStars em').eq(i).addClass('active');
				}
				/*$('.evaluateStars em:lt(starnum)').addClass('active');*/
				$('.evalScore i').text(starnum);
			});
		},
		initWordsCount : function(){
			//字数校验
			/*输入框内字数控制*/
			$(".evaluateFrame textarea").keyup(function(){
				var len = $(this).val().length;
				if(len > 499){
					$(this).val($(this).val().substring(0,500));
				}
				var num = 500 - len;
				$(".wordNum i").text(num);
			});
		},
		initBtn : function(){
			$("#commentTip a.btn-confirm").click(function(){
				$("#commentTip").popup("close");
				window.location.href = wxServer + "/order/weixin/list";
			});
		},
		showErrorSlide : function(errorMsg){
			$("#errTips").text(errorMsg);
			$("#errTips").slideDown("normal", function(){
				setTimeout(function(){
					$("#errTips").slideUp();
				}, 2000)
			}).removeClass("hidden");
		}
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
