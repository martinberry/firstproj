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
		url : wxServer + "/weixin/product/visa/search",
		data : JSON.stringify(criteria),
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		dataType : "html",
		success : function(result) {
			$("div.visaTrip-box ul.visaTrip-list li").remove();
			$("div.visaTrip-box ul.visaTrip-list").html(result);
			toDetail();
		}
	});
}

function toDetail(){
	$("div.visaTrip-wrap ul.visaTrip-list a.detail").click(function(){
		var pid = $(this).attr("data-pid");
		window.location = wxServer + "/weixin/product/visa/detail/"+ pid;
	});
}


