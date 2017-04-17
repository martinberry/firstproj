$(function(){
	//图片延迟加载
    $("img").lazyload({
        effect: "fadeIn"               //载入使用何种效果
    });

    $(".top-nav-list").slideNav({
        fx: "swing",
        speed: 300,
        changeTextColor: "#fff"
    });

    $("#productData").on("click", ".wishList", function(){
        if (!$(this).hasClass("active")) {
        	if(isLogin == 'true'){
        		addWishList($(this));
    		}else{
    			$('.work-platform').stop().animate({'left':'0'});
    			event.stopPropagation();
    		}
        } else {
            var $tip = $(this).next(".hasActiveTip");
            $tip.fadeIn(function(){
                setTimeout(function(){
                    $tip.fadeOut();
                }, 1500);
            });
        }

    });

    //搜索按钮
	$("#searchBtn").click(function(){
		submit(true);
	});
	
	$(".sl-destName").click(function(){
		submit(true,$(this).html(), 2) ;
	});
	
	$(".destSelect-first-level").click(function(){
		submit(true,$(this).find(".fl-destName").html(), 1) ;
	});
	
	//页面打开时异步请求查询产品,此次请求条件来自home选择的条件
	submit();

});

function submit(withClick, destination, destLevel){
	var criteria = {};
	criteria.departure = $(".startOffInput").val();
	criteria.destination = destination ? destination : $(".destinationInput").val();
	criteria.destLevel = destLevel ? destLevel : $("#selectedDestLevel").val();

	$.ajax({
		type : "POST",
		url : basepath + "/product/search",
		data : JSON.stringify(criteria),
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		dataType : "html",
		success : function(result) {
			$("#productData").html(result);
			//时差滚动效果初始化
			var scroller = skrollr.init({
	            smoothScrolling: false,
	            mobileDeceleration: 0.004,
	            forceHeight: false
	        });
			scroller.refresh();
			if(withClick){
				if($("#productData").find("p.withoutTips").length <=0){
					_paq.push(['trackSiteSearch', JSON.stringify(criteria), "ztrmainsearch", $("#productData").find("section.product-wrapper").length ]);
				}else{
					_paq.push(['trackSiteSearch', JSON.stringify(criteria), "ztrmainsearch", 0 ]);
				}
			}
		}
	});
}


function addWishList(section){
	var data = {};
	data.productId = section.parents(".pro-list-white").siblings('input[name="productId"]').val();
	data.pid = section.parents(".pro-list-white").siblings('input[name="pid"]').val();
	data.pName = section.parents(".pro-list-white").siblings('input[name="pName"]').val();

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
				section.addClass("active");
				_paq.push(['trackEvent', 'listpage', 'ztraddwishlist', data.productId, 'success']);
	    	}else if( resp.res_code == "FF_MEMB_1001" ){
	    		$('.work-platform').stop().animate({'left':'0'});
	    		_paq.push(['trackEvent', 'listpage', 'ztraddwishlist', data.productId, 'fail']);
	    	}else{
	    		alert("网络异常，请稍后重试");
	    		_paq.push(['trackEvent', 'listpage', 'ztraddwishlist', data.productId, 'error']);
	    	}
		}
	});
}

function logout_callback(){
	$(".wishList").removeClass("active");
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
					$('.product-wrapper').each(function(){
						if(resp.res_msg.indexOf($(this).children('input[name="productId"]').val()) >= 0){
							$(this).find('.wishList').addClass("active");
						}
					 });
		    	}
			}
		});
	}

}
