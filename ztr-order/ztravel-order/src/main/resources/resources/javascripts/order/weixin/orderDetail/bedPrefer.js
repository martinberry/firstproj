/**
 *
 */

var bed = {
		init : function(){
			var orderId = $("#orderId").val();
			$.ajax({
				type : "POST",
				url : "../detail/getBedPrefer",
				data : orderId,
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				dataType : "html",
				success : function(result) {
					$("#bedPreferContainer").html(result);
					bed.edit();
				}
			});
		},
		edit : function(){
			$(".hotel-bed-type").click(function(){
				var orderStatus = $("#orderStatus").val();
				 if(orderStatus != "CANCELED"){
					 if($(".hotel-bed-op").is(":hidden")){
						 $(".hotel-bed-op").fadeIn("fast");
						 }
						$(".span-confirm").click(function(){
							bed.update();
							 var bedType = $(".choice-bed input[type='radio']:checked").next().text();
							 $("#bed-type").text(bedType);
							 $(".hotel-bed-op").fadeOut("fast");
						});
						$(".span-cancel").click(function(){
						 $(".hotel-bed-op").fadeOut("fast");
						});
				 }
				 $(".choice-bed").click(function(){
					 $(this).find("input[type='radio']").prop("checked", "checked");
					 if($(this).find("input").val() == "double"){
						 $("#bedTips").attr("style","display;");
					 }else{
						 $("#bedTips").attr("style","display:none");
					 }
				 })
		});
		},
		update : function(){
			var bedPrefer;
			var orderId = $.trim($("#orderId").val());
			if($("#bedPreferContainer").find("input.bigbed").prop("checked")){
				bedPrefer ="big";
			 }else{
				bedPrefer ="double";
			 }
			$.ajax({
				   type: "POST",
				   url: wxServer + '/order/weixin/detail/updateBedPrefer',
				   data : "bedPrefer="+bedPrefer+"&orderId="+orderId,
				   dataType: "json",
				   success: function(resp){
					   if (resp.res_code == 'SF_ORDE_1003') {
						   _paq.push(['trackEvent', 'myorderpage', 'ztrchangebedtype', 'SUCCESS']);
						   bed.init();
						}else if(resp.res_code == 'FF_ORDE_1020'){
							_paq.push(['trackEvent', 'myorderpage', 'ztrchangebedtype', 'FAIL']);
							alert(resp.res_msg);
						}else{
							_paq.push(['trackEvent', 'myorderpage', 'ztrchangebedtype', 'FAIL']);
							alert("网络异常，请稍后重试！");
						}
					}
				});
		}
}