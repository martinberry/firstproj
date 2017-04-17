$(function(){

	//亮点配色初始化
	var lightColor = $('#lightColor_hidden').val();
	if(lightColor)setBannerBgColor(lightColor);

    $(".tab-cont").hide();
    $(".tab-cont-block").each(function(index){
        $(this).find(".tab-cont").eq(0).show();
     });

    $("a[href^=tel]").click(function(){
    	return false ;
    })

    $(".hotel-info-tab li").each(function(index){
     $(this).click(function(){
            $(".hotel-info-tab").eq(parseInt(index/3)).children("li").removeClass("current");
            $(this).addClass("current");
            $(".rightHotelTabs .tab-cont-block").eq(parseInt(index/3)).find(".tab-cont").hide();
            $(".rightHotelTabs .tab-cont").eq(index).show();
         });
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

    //二维码生成
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

    //默认收起三个以后的
    var hotelInfoModelCount = 0;
    $(".hotelInfoModel").each(function(index){
    	hotelInfoModelCount++;
    	if (index > 2) {
            $(this).hide();
        }
    });
    if(hotelInfoModelCount <4){
    	$(".expand-btn").parent().hide();
    }else{
    	// 展开/收起更多酒店
        $(".expand-btn").click(function(){
            if ($(this).hasClass("collapse")) {
                $(this).removeClass("collapse");
                $(this).find("em").html("展开更多酒店信息");
                $(".hotelInfoModel").each(function(index){
                    if (index > 2) {
                        $(this).hide();
                    }
                });
            } else {
                $(this).addClass("collapse");
                $(this).find("em").html("收起酒店信息");

                $(".hotelInfoModel").each(function(){
                    if (!$(this).is(":visible")) {
                        $(this).show();
                    }
                });
            }
        });
    }

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
//设置亮点Banner配色
function setBannerBgColor(color) {
    $(".bannerBottomList .middleListContent").css({
        "background-color": "#" + color
    });
    $(".productDetailModel").css({
        "border-bottom-color": "#" + color
    });
}