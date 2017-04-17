
var subLock = false ;
$(function(){
	var openId = $("#openId").val();
    if (openId == "") {
        /*发起微信授权请求*/
        var fromUrl = location.href;
        var appId = $("#appId").val() ;
        var url='https://open.weixin.qq.com/connect/oauth2/authorize?appid='+appId+'&redirect_uri='+encodeURIComponent(fromUrl)+'&response_type=code&scope=snsapi_userinfo&state=topicDiscuss#wechat_redirect';
        location.href=url;
    }
    
    $(".switch-checkbox").click(function(){
        $(this).toggleClass("on");

         if ($(this).hasClass("on")) {
             $(".trip-num-block").hide();
             $(".menu-block").show();
         } else {
             $(".trip-num-block").show();
             $(".menu-block").hide();
         }
    });
    
    $("body").delegate(".publishBtn", "click", function(){
    	var disCont = $(".disCont").val() ;
    	if(disCont.length > 150 || disCont.length == 0){
    		showErrorSlide("输入内容长度限制1~150个字符");
    		return ;
    	}
    	var topicCommentSubmitVo = {} ;
    	topicCommentSubmitVo.topicId = $("#topicId").val() ;
    	topicCommentSubmitVo.comment = disCont ;
    	topicCommentSubmitVo.anonymous = $(".switch-checkbox").hasClass("on") ? 1 : 0 ;
    	topicCommentSubmitVo.nickName = $("#nickName").val() ;
    	topicCommentSubmitVo.submiterId = $("#openId").val() ;
    	topicCommentSubmitVo.headImage = $("#headImageUrl").val() ;
    	
    	if(!subLock){
    		subLock = true ;
    		$.ajax({
    			type : "POST",
    			url : wxServer + "/topicdiscuss/submitDiscuss",
    			data : JSON.stringify(topicCommentSubmitVo),
    			headers : {
    				'Accept' : 'application/json',
    				'Content-Type' : 'application/json'
    			},
    			dataType : "json",
    			success : function(resp) {
    				if( resp.res_code == "SUCCESS" ){
    					$("#sucTips").text("您的评论提交成功");
    	                $("#sucTips").slideDown("normal", function(){
    	                    setTimeout(function(){
    	                        $("#sucTips").slideUp();
    	                        window.location.href = wxServer + "/topic/topicDetail/" + topicCommentSubmitVo.topicId + "?scrollType=comment";
    	                    }, 2000)
    	                }).removeClass("hidden");
    				}else if( resp.res_code == "FAIL" || resp.res_code == "SENSITIVE_WORD"){
    					showErrorSlide(resp.res_msg);
    					$(".disCont").val("") ;
    					subLock = false ;
    				}
    			},error:function(){
    				showErrorSlide("提交评论失败,请重试");
    				subLock = false ;
    			}
    		});
    	}
    });
    
});

function showErrorSlide(errorMsg){
	$("#errTips").text(errorMsg);
	$("#errTips").slideDown("normal", function(){
		setTimeout(function(){
			$("#errTips").slideUp();
		}, 2000)
	}).removeClass("hidden");
}

