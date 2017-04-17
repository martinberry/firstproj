/**
 *
 */
var commonContactorList = [];
var contactor = {
//		init : function(){
//			var url = wxServer + "/book/weixin/getContactors";
//			$.ajax({
//				type : "POST",
//				url : url,
//				headers : {
//					'Accept' : 'application/json',
//					'Content-Type' : 'application/json'
//				},
//				dataType : "html",
//				success : function(result) {
//					$("#contactorDiv").html(result);
//					if($("#provinceWrapper").length < 1){
//						contactor.initAddress();
//					}
//				}
//			});
//		},
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
				defaultCounty = "东城区";
			}
	        var address_1 = new AddressPicker("#addressWrapperContainer", {
	        	currentAddress: [defaultProvince, defaultCity, defaultCounty]
	        }).init();

	        $("#addressWrapperContainer").closest("div[data-role='popup']").find(".dp-sure-btn").click(function(){
				var address = address_1.getAddress();
				var addressHtml = address[0]+"  "+address[1]+"   "+address[2];
				var province = address[0];
				var city = address[1];
				var county = address[2];
				$("#hideProvince").val(province);
				$("#hideCity").val(city);
				$("#hideCounty").val(county);
				$("#wholeAddress").val(addressHtml);
			});
		},
		getParams : function(){
			//联系人
			var contactorInfo = {};
			contactorInfo.name = $("#contactorDiv input.name").val();
			contactorInfo.phone = $("#contactorDiv input.mobile").val();
			contactorInfo.email = $("#contactorDiv input.email").val();
			if($("#hideProvince").val() == "" || $("#hideCity").val() == "" || $("#hideCounty").val() == ""){
				contactorInfo.province = "上海市";
				contactorInfo.city = "上海市";
				contactorInfo.county = "闸北区"
			}else{
				contactorInfo.province = $("#hideProvince").val();
				contactorInfo.city = $("#hideCity").val();
				contactorInfo.county = $("#hideCounty").val();
			}
			contactorInfo.addressDetail = $("#detail").val();
			return contactorInfo;
		},
		convertContactor : function(data){
			var result = [];
			if(data.length > 0){
				for(i = 0; i < data.length ; i++){
					var passengerTemp = {};
					passengerTemp.name = data[i].contactor;
					passengerTemp.number = i;
					result.push(passengerTemp);
				}
			}else{
				var passengerTemp = {};
				passengerTemp.name = "没有常用联系人";
				passengerTemp.number = -1;
				result.push(passengerTemp);
			}
			return result;
		},
		getCommonContactor : function(){
			$("#contactorDiv span.contactor-lab").click(function(){
				_paq.push(['trackEvent', 'orderpage', 'ztrtraveller']);
				var url = wxServer + "/book/weixin/getContactors";
				commonContactorList = [];
				var $this = $(this);
				$.ajax({
					type:'POST',
					contentType:"application/json",
					dataType:'json',
					url:url,
					success:function(result){
						var data = new Array();
						commonContactorList = result.contactors;
						if(commonContactorList != undefined && commonContactorList.length > 0){
							data = contactor.convertContactor(commonContactorList);
						}
						setTimeout(function(){
							$this.cusListPop({
								title: "请选择联系人",
								scrollId: "contactorInfo",
								data: data,
								reCreate: true,
								callback : function(){
									$("#contactorInfoWrapper").find("li").click(function(){
										if ($(this).hasClass("current")) {
											contactor.fillContactor($this,$(this).attr("data-number"));
										}
										$("#contactorInfoPopContainer").popup("close");
									});
								}
							});
						}, 500);
					}
				});
			});
		},
		fillContactor : function($obj,indexNum){
			if(indexNum >= 0){
				var contactor =  commonContactorList[indexNum];
				if(contactor){
					if(contactor.contactor != null && contactor.contactor != ""){
						$obj.closest("div.order-traveler-box").find("input.name").val(contactor.contactor);
					}
					if(contactor.phone != null && contactor.phone != ""){
						$obj.closest("div.order-traveler-box").find("input.mobile").val(contactor.phone);
					}
					if(contactor.email != null && contactor.email != ""){
						$obj.closest("div.order-traveler-box").find("input.email").val(contactor.email);
					}
					if(contactor.province != null && contactor.province != "" && contactor.city != null && contactor.city != "" && contactor.county != null && contactor.county != ""){
						$("#hideProvince").val(contactor.province);
						$("#hideCity").val(contactor.city);
						$("#hideCounty").val(contactor.county);
						$obj.closest("div.order-traveler-box").find("input.province").val(contactor.province+"  " + contactor.city+"  " + contactor.county);
					}
					if(contactor.address != null && contactor.address != ""){
						$obj.closest("div.order-traveler-box").find("input.address").val(contactor.address);
					}
				}
			}
		}
}
