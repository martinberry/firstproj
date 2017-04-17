/**
 * 
 */
$(function(){
	submit();
});


function submit(){
	var criteria = {};
	criteria.departure = $("#origin-name").text();
	criteria.destination = $("#dest-name").text();
	criteria.destLevel = 2;
	$.ajax({
		type : "POST",
		url : wxServer + "/weixin/product/local/search",
		data : JSON.stringify(criteria),
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		dataType : "html",
		success : function(result) {
			$("div.localtrip-wrap ul.localtrip-list li").remove();
			$("div.localtrip-wrap ul.localtrip-list").html(result);
			toDetail();
		}
	});
}

function toDetail(){
	$("div.localtrip-wrap ul.localtrip-list a.detail").click(function(){
		var pid = $(this).attr("data-pid");
		window.location = wxServer + "/local/product/weixin/detail/"+ pid;
	});
}


