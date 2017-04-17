$(function(){

    var docEle = document.body || document.getElementsByTagName("body")[0];
    docEle.setAttribute("data-spy", "scroll");
    docEle.setAttribute("data-target", "#navbar");

    $(".fixedList").affix({
        offset: {
            top: function(){
                return this.top = $("#leftFlag").offset().top;
            }
        }
    });

    $(".jobGroup > li").bind("click", function(e){
        var index = $(this).index();
        $(this).addClass("active").siblings().removeClass("active");
        $(".materialModel > .jobElement").eq(index).removeClass("hidden").siblings(".jobElement").addClass("hidden");
    });

    $('#scrollImg a').css('visibility','visible');
    $('#scrollImg').before('<div id="nav" class="slider_nav">').cycle({fx:"fade",timeout: 3000,center:true,pause:true,pager:"#nav",pagerEvent: "click.cycle"});
    $("#nav").css({
        left: ($(".scrollImg").parent().outerWidth() - $("#nav").outerWidth()) / 2 + "px",
        top: ($(".scrollImg").height()*0.95)+"px"
    });

    $(".wishlist").click(function(event){
        if (!$(this).hasClass("actived")) {
        	if(isLogin == 'true'){
        		addWishList($(this));
    		}else{
    			$('.work-platform').stop().animate({'left':'0'});
    			event.stopPropagation();
    		}

        } else {
            $(".wishedTip").fadeIn(function(){
                setTimeout(function(){
                    $(".wishedTip").fadeOut();
                }, 1500);
            });
        }
    });

});

function addWishList(section){
	var data = {};
	data.productId = productId;
	data.pid = productPid;
	data.pName = pName;

	$.ajax({
		type : "POST",
		url : basepath + "/member/wish/add",
		data : JSON.stringify(data),
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		dataType : "json",
		success : function(resp) {
			if( resp.res_code == "SF_MEMB_1001" ){
				section.addClass("actived");
	    	}else if( resp.res_code == "FF_MEMB_1001" ){
	    		$('.work-platform').stop().animate({'left':'0'});
	    	}else{
	    		alert("网络异常，请稍后重试");
	    	}
		}
	});
}
