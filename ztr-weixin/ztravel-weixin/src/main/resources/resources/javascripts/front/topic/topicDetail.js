
window.onload = function(){
	if($("#scrollType").val() == 'comment'){
		$.mobile.silentScroll($("#commentAnchor").offset().top - $(".headerBar").outerHeight());
	}
}

   var like=0;     


$(function(){
	var openId = $("#openId").val();
	if(!is_weixin()){
    	$(".cancelLike").attr("style","display:none;");           
    }else if(openId == "" && is_weixin()){
        /*发起微信授权请求*/
        var fromUrl = location.href;
        var appId=$("#appId").val();
        var url='https://open.weixin.qq.com/connect/oauth2/authorize?appid='+appId+'&redirect_uri='+encodeURIComponent(fromUrl)+'&response_type=code&scope=snsapi_userinfo&state=topic#wechat_redirect';        location.href=url;
    }
	
	$(".wantSay").click(function(){
		window.location.href= wxServer + "/topicdiscuss/toSubmitDiscuss?topicId=" + $("#topicId").val() ;
	})
        	
    $(".topic_prize a").click(function(){
    	if($(this).hasClass("unfold")){
    		$(".topic_prize p").css("height","inherit");
    		$(".unfoldIcon").addClass("foldIcon");
    		$(".topic_prize a span").text("收起");
    		$(this).removeClass("unfold");
    	}else{
            $(".topic_prize p").css("height","1rem");
            $(".unfoldIcon").removeClass("foldIcon");
            $(".topic_prize a span").text("展开");
            $(this).addClass("unfold");
        }
    });
	  
      $(".cancelLike").click(function(){
      	if(like==0){
      		like=1;
          	var obj=this;
          	var oid=$("#openId").val();
      	    var cid=$(obj).siblings("input[name='commentId']").val();
          	$.ajax({
          		type : "POST",
          		url : wxServer + "/topic/praise",           	
          		data : {openId:oid,commentId:cid},
          		dataType : "json",
          		success : function(result) {
          			var num = parseInt($(obj).find("em").text());
                   if(result.res_code=='praiseAdd'){                   	   
                  	   $(obj).addClass("clickLike");
                  	   num++;
                  	   $(obj).find("em").text(num); 
                   }else if(result.res_code=='praiseMinus'){
                  	 if($(obj).hasClass("clickLike")){
                           $(obj).removeClass("clickLike");                            
                       }
                  	 num--;
                  	 $(obj).find("em").text(num); 
                   }else{
                  	 alert("点赞失败");
                   }
                   like=0;
          		}
          	});
      	}
      })
});


function is_weixin(){
	var ua = navigator.userAgent.toLowerCase();
	if(ua.match(/MicroMessenger/i)=="micromessenger") {
		return true;
	} else {
		return false;
	}
}
        
        

