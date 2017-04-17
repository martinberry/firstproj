$(function(){
	$(".delTraveller").click(function(){
		var id = $(this).attr("value");
		$("#delTravellerBtn").attr("value", id);
	});

	$("#delTravellerBtn").click(function(){
		var travellerId = $(this).attr("value");
		$.ajax({
			type: "POST",
			url: basepath + "/usercenter/weixin/deleteTraveller",
			data: travellerId,
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			dataType : "json",
			success: function(resp){
				switch(resp.res_code){
				case "SW_MEMB_0001":
					location.reload(true);
					break;
				case "FW_MEMB_0002":
					alert(resp.res_msg);
					break;
				}
			}
		});
	});

});