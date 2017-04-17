//var titleTail = " 自由行,自助游,旅游度假";

$(function(){
	submit();
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
			$("div.freeWalk-wrap div.freeWalk-box").remove();
			$("div.freeWalk-wrap").html(result);
			discussInit();
		}
	});
}

function discussInit(){
	$("div.freeWalk-topic").click(function(){
		var tid = $(this).attr("data-tid");
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
