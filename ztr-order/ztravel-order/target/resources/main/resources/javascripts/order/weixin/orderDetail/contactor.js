/**
 *
 */
var address_1;
var contactor = {
		initAddress : function(){
			sessionStorage.clear();
	        sessionStorage.setItem("province", JSON.stringify(province));
	        sessionStorage.setItem("city", JSON.stringify(city));
	        sessionStorage.setItem("county", JSON.stringify(county));
	        var defaultProvince;
	        var defaultCity;
	        var defaultCounty;
	        if($("#hideProvince").val()!=""){
	        	defaultProvince = $("#hideProvince").val();
	        }else{
	        	defaultProvince = "北京市";
	        }
			if($("#hideCity").val()!=""){
				defaultCity = $("#hideCity").val();
	        }else{
	        	defaultCity = "北京市";
	        }
			if($("#hideCounty").val()!=""){
				defaultCounty = $("#hideCounty").val();
			}else{
				defaultCounty = "朝阳区";
			}
			if($("#provinceWrapper").length > 0){
				address_1.setAddress([defaultProvince,defaultCity, defaultCounty]);
			}else{
				address_1 = new AddressPicker("#addressWrapperContainer", {
		        	currentAddress: [defaultProvince, defaultCity, defaultCounty]
		        }).init();
			}
			$("#address").click(function(){
	        	$("#addressWrapperContainer").closest("div[data-role='popup']").popup("open");
	        });
			$("#addressWrapperContainer").closest("div[data-role='popup']").find(".dp-sure-btn").click(function(){
				var address = address_1.getAddress();
				var addressHtml = address[0]+"  "+address[1]+"   "+address[2];
				var province = address[0];
				var city = address[1];
				var county = address[2];
				$("#hideProvince").val(province);
				$("#hideCity").val(city);
				$("#hideCounty").val(county);
				$("#wholeAddress").html(addressHtml);
			});
		},
		init : function(){
			var orderId = $("#orderId").val();
			$.ajax({
				type : "POST",
				url : "../detail/getContactor",
				data : orderId,
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				dataType : "html",
				success : function(result) {
					$("#contactorContainer").html(result);
					orderDetailInit.initContactorEdit();
					contactor.initAddress();
				}
			});
		},
		update : function(){
			if(contactor.validate()){
				 var data = {};
				 data.id= $("#contactorId").val();
				 data.orderId = $("#orderId").val();
				 data.contactor= $.trim($("div.order-pop-edt input.contactorName").val());
				 data.phone= $.trim($("div.order-pop-edt input.phone").val());
				 data.email= $.trim($("div.order-pop-edt input.email").val());
				 data.province= $.trim($('#hideProvince').val());
				 data.city= $.trim($('#hideCity').val());
				 data.county= $.trim($('#hideCounty').val());
				 data.address= $.trim($('#streetName').val());
				 $.ajax({
					   type: "POST",
					   url: wxServer + '/order/weixin/detail/updateContactor',
					   headers : {
							'Accept' : 'application/json',
							'Content-Type' : 'application/json'
						},
					   data : JSON.stringify(data),
					   dataType: "json",
					   success: function(resp){
						   if (resp.res_code == 'SF_ORDE_1001') {
							   _paq.push(['trackEvent', 'myorderpage', 'ztrchangeconinfo', 'SUCCESS']);
							   $(".order-pop-edt").slideUp("fast");
							   $(".contactInfoTab").show();
							   contactor.init();
							}else if (resp.res_code == 'FF_ORDE_1005') {
								 _paq.push(['trackEvent', 'myorderpage', 'ztrchangeconinfo', 'FAIL']);
								$("#tipMsg").html(resp.res_msg);
						    	$("#validateTip").popup("open");
							}else if (resp.res_code == 'FF_ORDE_1020') {
								 _paq.push(['trackEvent', 'myorderpage', 'ztrchangeconinfo', 'FAIL']);
								$("#tipMsg").html(resp.res_msg);
						    	$("#validateTip").popup("open");
							}else{
								 _paq.push(['trackEvent', 'myorderpage', 'ztrchangeconinfo', 'FAIL']);
								$("#tipMsg").html("网络异常，请稍后重试！");
						    	$("#validateTip").popup("open");
							}
							 }
					});
			}
		},
		validate : function(){
			var lastResult = true;
			$("div.order-pop-edt input[data-cv]").each(function(i,ele){
				var r1 = $(ele).bookValidate({
					validateAll: false
				});
				if(!r1){
					lastResult = false;
					return lastResult;
				};
			});
			return lastResult;
		}

}