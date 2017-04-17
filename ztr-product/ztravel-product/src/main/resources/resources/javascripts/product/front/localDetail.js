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
        
        create_qrcode();
        $(".qrCode-icon").attr("title", $("div.qrCodeRealBlock").html());
        $(".qrCode-tooltip").tooltip({
            html: true,
            placement: 'bottom',
            template: '<div class="tooltip green-border-tooltip" role="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>'
        });
        
        $("img.lazyload-img").lazyload({
        	threshold: 1500,
        	placeholder: ''
        });

    })
    
    
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
				_paq.push(['trackEvent', 'detailpage', 'ztraddwishlist', data.productId,'success']);
	    	}else if( resp.res_code == "FF_MEMB_1001" ){
	    		$('.work-platform').stop().animate({'left':'0'});
	    		_paq.push(['trackEvent', 'detailpage', 'ztraddwishlist', data.productId, 'fail']);
	    	}else{
	    		_paq.push(['trackEvent', 'detailpage', 'ztraddwishlist', data.productId, 'error']);
	    		alert("网络异常，请稍后重试");
	    	}
		}
	});
}

function logout_callback(){
	$(".wishlist").removeClass("actived");
}

function login_callback(){
	if(isLogin == 'true'){
		$.ajax({
			type : "POST",
			url : basepath + "/member/wish/query",
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			dataType : "json",
			success : function(resp) {
				if( resp.res_code == "SF_MEMB_1003" ){
					if(resp.res_msg.indexOf(productId) >= 0){
						$('.wishlist').addClass("actived");
						}
		    	}
			}
		});
	}

}

//生成二维码跳转到微信共享产品详情页面
function create_qrcode() {
	var sharePath = window.location.href ;
	var queryString = sharePath.substring(sharePath.lastIndexOf('/') + 1,sharePath.length) ;
	sharePath = wxServer + '/product/weixin/detail/' + queryString ;
	$("div.qrCodeRealBlock").empty();
	$("div.qrCodeRealBlock").qrcode({
		render : "table",
		maxVersion:40,
		minVersion:3,
		size: 180,
		width : 170,
		height : 170,
		fill: '#000',
		background : "#fff",
		foreground : "#f00",
		quiet: 1,
		text : sharePath,
	});
}

