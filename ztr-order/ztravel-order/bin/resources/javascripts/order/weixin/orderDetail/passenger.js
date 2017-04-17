/**
 *
 */
var disappear = "";
var cnNameReg = /^[\u4E00-\u9FA5]/;
var enNameReg = /^[A-Za-z]+$/;
var nationality_hint_error='对不起，找不到：';
var passenger = {
		init : function(){
			var orderId = $("#orderId").val();
			$.ajax({
				type : "POST",
				url : "../detail/getPassengers",
				data : orderId,
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				dataType : "html",
				success : function(result) {
					$("#passengerContainer").html(result);
					passenger.translate();
					orderDetailInit.initPassengerEdit();
					orderDetailInit.initCreditType();
					orderDetailInit.initDatePicker();
					passenger.countryAutoComplete();
					passenger.nationalityBlurIfNotFoundSetEmpty();
				}
			});
		},
		update : function(){
			if(passenger.validate()){
				var list = {};
				var passengers = [];
				$("#passengerContainer div.traveler-edt-box").each(function (i) {
					var passenger = {};
					passenger.id= $.trim($(this).find('input.hiddenId').val());
					passenger.firstName= $.trim($(this).find('input.firstName').val());
					passenger.lastName= $.trim($(this).find('input.lastName').val());
					passenger.firstEnName= $.trim($(this).find('input.firstEnName').val());
					passenger.lastEnName= $.trim($(this).find('input.lastEnName').val());
					passenger.credentialType= $.trim($(this).find('div.credentialType').attr("credentialType"));
					passenger.credentialNum= $.trim($(this).find('input.credentialNum').val());
					passenger.credentialDeadLine= $.trim($(this).find('div.dead-time').attr("data-time"));
					passenger.country= $.trim($(this).find('input.country').val());
					passenger.birthday= $.trim($(this).find('div.brithdayDiv').attr("data-time"));
					if($(this).find('input.male').prop("checked")){
						passenger.gender= "MALE";
					}else{
						passenger.gender= "FEMALE";
					}
					passengers[i] = passenger;
				});
				list.passengers = passengers;
				list.orderId = $("#orderId").val();
				if($("#isDomestic").val() == "true"){
					list.isDomestic = true;
				}else{
					list.isDomestic = false;
				}
				$.ajax({
					type: "POST",
					url: wxServer + '/order/weixin/detail/updatePassenger',
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					data : JSON.stringify(list),
					dataType: "json",
					success: function(resp){
						$(".order-traveler-edt").slideUp("fast");
						if (resp.res_code == 'SF_ORDE_1002') {
							_paq.push(['trackEvent', 'myorderpage', 'ztrchangepassinfo', 'SUCCESS']);
							$(".order-traveler-edt").slideUp("fast");
							passenger.init();
						}else if (resp.res_code == 'FF_ORDE_1010') {
							_paq.push(['trackEvent', 'myorderpage', 'ztrchangepassinfo', 'FAIL']);
							$("#tipMsg").html(resp.res_msg);
							$("#validateTip").popup("open");
						}else if (resp.res_code == 'FF_ORDE_1020') {
							_paq.push(['trackEvent', 'myorderpage', 'ztrchangepassinfo', 'FAIL']);
							$("#tipMsg").html(resp.res_msg);
							$("#validateTip").popup("open");
						}else{
							_paq.push(['trackEvent', 'myorderpage', 'ztrchangepassinfo', 'FAIL']);
							$("#tipMsg").html("网络异常，请稍后重试！");
							$("#validateTip").popup("open");
						}
					}
				});
			}
			},
		 validate : function(){
			 var lastResult = true;
			 $("div.order-traveler-edt input[data-cv]").each(function(i,ele){
					var r1 = $(ele).bookValidate({
		                validateAll: false
		            });
					if(!r1){
						lastResult = false;
						return lastResult;
					};
				});
			 return lastResult;
		 },
		 translate : function(){
			 $("div.order-traveler-edt input.firstName").each(function(){
					$(this).blur(function(){
						var firstName = $(this).val();
						if(cnNameReg.test(firstName)){
							$(this).closest('div.traveler-edt-box').find('input.firstEnName').val( Pinyin.GetQP($.trim(firstName)).toUpperCase());
						}else if(enNameReg.test(firstName)){
							$(this).closest('div.traveler-edt-box').find('input.firstEnName').val(firstName);
						}
					});
				});
				$("div.order-traveler-edt input.lastName").each(function(){
					$(this).blur(function(){
						var lastName = $(this).val();
						if(cnNameReg.test(lastName)){
							$(this).closest('div.traveler-edt-box').find('input.lastEnName').val( Pinyin.GetQP($.trim(lastName)).toUpperCase());
						}else if(enNameReg.test(lastName)){
							$(this).closest('div.traveler-edt-box').find('input.lastEnName').val(lastName);
						}
					});
				});
		 },
		 countryAutoComplete : function(){
				$("div.order-traveler-edt div.traveler-edt-box input.country").each(function(i,item){
					var $this = $(this);
					$(this).typeahead({
					    source: function (query, process) {
					    	$((($(this)[0]).$element)[0]).val($((($(this)[0]).$element)[0]).val().replace(/</g,"&lt").replace(/>/g,"&gt"));
					        var parameter = {query: query};
					        $.post(wxServer + "/usercenter/weixin/countryAutoComplete", parameter, function (data) {
					        	if(data.length==0 || !passenger.isExistNationality(data,i)){
					        		notFindHint =nationality_hint_error+$("#nationality_"+i).val();
					        		data=[notFindHint];
					        	}else{
					        		nationalityDropListValues=data;
					        		$("#nationalityDropList_"+i).val(data);
					        	}
					        	process(data);
					        });
					    }
					});

				});
			},
			isExistNationality : function(data,i){
				var isExist= false;
				var nationality = $("#nationality_"+i).val();
				if(null!=nationality  ){
					for(var i=0;i<data.length;i++){
						if(data[i].indexOf(nationality) != -1){
							isExist=true;
							break;
						}
					}
				}
				return isExist;
			},
			nationalityBlurIfNotFoundSetEmpty : function(){
				$("div.order-traveler-edt div.traveler-edt-box input.country").each(function(i,item){
					$(item).blur(function(){
						var nationalityDropList = $("#nationalityDropList_"+i).val();

						if(nationalityDropList.indexOf(nationality_hint_error) != -1 ){
							 $("#nationality_"+i).val('');
						}
						if(null!=nationalityDropList && nationalityDropList !='' && $.inArray( $("#nationality_"+i).val(), nationalityDropList.split(',')) <0){
							 $("#nationality_"+i).val('');
						}

					});
				});
			}
}