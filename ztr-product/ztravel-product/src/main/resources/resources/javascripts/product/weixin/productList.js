//var titleTail = " 自由行,自助游,旅游度假";

$(function(){

	FastClick.attach(document.body);

    var til = $(".prdlist-tips").text().trim();
    var subtil = '';
    var maxwidth = 40;
    if (til.length > maxwidth) {
        subtil = til.substring(0, maxwidth);
        $(".prdlist-tips").html(subtil + '...');
    }

    $(".addresslist-org").click(function(){
        $(".address-destination").hide("fast");
        $(".address-origin").slideToggle("fast");
        $(".address-origin ul li").click(function(){
            $(this).siblings().removeClass("add-current");
            $(this).addClass("add-current");
            $("#origin-name").text($(this).text());
            $(".address-origin").slideUp("fast");
            submit();
        });

    });
    $(".addresslist-des").click(function(){
        $(".address-origin").hide("fast");
        $(".address-destination").slideToggle("fast");
        var obj = $(".address-destination ul").find(".add-current");
        obj.addClass("without-border-right");
        obj.children(".add-second-menu").show();

    });
    $(".address-destination > ul > li").click(function(){
//        if($(this).index() == 0){
//            $("#dest-name").text($(this).children("span").text());
//            $(".address-destination").slideUp("fast");
//            $(this).siblings().removeClass("add-current");
//            $(this).siblings().removeClass("without-border-right");
//            $(this).addClass("add-current");
//            $(".add-second-menu").hide();
//            $(this).children(".add-second-menu").show();
//            return;
//        }
        if(!$(this).hasClass("add-current")){
            $(this).siblings().removeClass("add-current");
            $(this).siblings().removeClass("without-border-right");
            $(this).addClass("add-current").addClass("without-border-right");
            $(".add-second-menu").hide();
            $(this).children(".add-second-menu").show();
        }
    });
    $(".add-second-menu > li").click(function(){
        var spot = $(this).text().trim();
        $(".add-second-menu li").removeClass("add-current");
        $(this).addClass("add-current");
        $("#dest-name").text($(this).text());
        $(".address-destination").slideUp("fast");
        submit(true);
        return false;
    });

    $(".add-second-menu > li").each(function(){
    	var place = $(this).text().trim();
    	if(place == $("#dest-name").text()){
    		$(this).parent().parent().click();
    	}
    });

	//页面打开时异步请求查询产品,此次请求条件来自home选择的条件
	submit();
	
	//
	var mySwiper = new Swiper('.swiper-container', {
        autoplay:7000,//可选选项，自动滑动
        speed:1000,
        loop : true,
        pagination: '.swiper-pagination',
        paginationClickable: true
        /*effect : 'fade',   //fade效果
        fade: {
            crossFade: true,
        }*/
    });

});

function submit(withClick){
	var criteria = {};
	criteria.departure = $("#origin-name").text();
	criteria.destination = $("#dest-name").text();
	criteria.destLevel = 2;

	$.ajax({
		type : "POST",
		url : wxServer + "/weixin/product/search",
		data : JSON.stringify(criteria),
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		dataType : "html",
		success : function(result) {
//			$("title").html(criteria.destination+titleTail);   //设置title
			$(".prdlist-box").html(result);
			discussInit();
			if($("body").find(".recom-path").length == 1){
				 $(".recom-path").slideDown();
			}
			if(withClick){
				if($("div.recom-content").length > 0){
					_paq.push(['trackSiteSearch', JSON.stringify(criteria), "ztrmainsearch", 0 ]);
				}else{
					_paq.push(['trackSiteSearch', JSON.stringify(criteria), "ztrmainsearch", $("div.prdlist-box li").length ]);
				}
			}
		}
	});
}

function discussInit(){
	$("div.discussContain").click(function(){
		var tid = $(this).find("div.discuss").data("tid");
		window.location.href = wxServer + "/topic/topicDetail/"+tid;//更换为话题详情地址
	});
}

function jump2DetailPage(productId){
	window.location.href = wxServer + "/product/weixin/detail/" + productId ;
}

function logout_callback(){

}

function login_callback(){


}
