var cnNameReg = /^[\u4E00-\u9FA5]/;
var enNameReg = /^[a-zA-z|/.]{1,20}$/;
var applyOrder = {
		 getParams : function() {
			var params  = {};
			var isDomestic = $("#isDomestic").val();
			params.isDomestic = isDomestic;
			params.productId = $("#productId").val();
			params.productNo = $("#productNo").val();
			params.firstImageId = $("#firstImageId").val();
			params.couponSub = typeof($("#couponSub").html()) == 'undefined' ? 0 : new Number($("#couponSub").html()).multiply(100);
			params.passWord = $("#passWord").val();
			params.tripDays = $("#tripDays").val();
			params.nature = $("#nature").val();
			params.payAmount = new Number($(".totalPrice").html()).multiply(100);
			params.totalPrice = new Number($("#originTotalPrice").val()).multiply(100);
			params.productType = $("#productType").val();
			params.integralSub = new Number(0).multiply(100);//积分抵消金额
			params.integral = 0;
			params.productTitle = $("#productTitle").val();
			params.bookDate = $("#bookDate").val();
			params.adaultNum = $("#orderAdultNum").html();
			params.childrenNum = $("#orderChildNum").html();
			params.packageId = $("#packageId").val();
			params.packageName = $("#packageName").val();
			params.userName = $("#userName").val();
			params.orderFrom = "WEB";
			params.priceId = $("#costPriceId").val();
			params.bedPrefer = $("table.order-hotelTable div.bedPrefer").find('label.active').attr("val");
			if($("div .coupon").find('label.active').length > 0){
				params.discountcouponId = $("div .coupon").parents('th').siblings('td').find('div.dropdown li.active').data("couponid");
			}
			//需求备注
			params.remark = $(".remark").val();
			//联系人
			var contactorInfo = {};
			contactorInfo.name = $("#contactorName").val();
			contactorInfo.phone = $("#contactorPhone").val();
			contactorInfo.email = $("#contactorEmail").val();
			contactorInfo.province = $(".order-linkManTable div.province a.dropdownBtn span.activeFonts").html();
			contactorInfo.city = $(".order-linkManTable div.city a.dropdownBtn span.activeFonts").html();
			contactorInfo.county = $(".order-linkManTable div.county a.dropdownBtn span.activeFonts").html();
			contactorInfo.addressDetail = $("#contactorAddress").val();
			params.contactorInfo = contactorInfo;
			//旅客信息
			var passengerList = [];
			$(".guestInfo-item tbody").each(function() {
				var passengerTemp = {};
				passengerTemp.credentialType = $(this).find("li[class*='active']").data("val");
				passengerTemp.credentialNum = $(this).find(".credentialNum").val();
				passengerTemp.type = $(this).find(".passengerType").data("val")
				passengerTemp.birthday = $(this).find(".birthday").val();
				passengerTemp.gender = $(this).find("div.gender").find("label[class*='active']").data("val")
				if($(this).find("div.savePassenger").find("label[class*='active']").length > 0){
					passengerTemp.savePassenger = true;
				}else{
					passengerTemp.savePassenger = false;
				}
				var firstName = $(this).find(".firstName").val();
				var lastName = $(this).find(".lastName").val();
				var firstNameEn = $(this).find(".firstNameEn").val();
				var lastNameEn = $(this).find(".lastNameEn").val();
				passengerTemp.firstName = firstName;
				passengerTemp.lastName = lastName;
				passengerTemp.firstNameEn = firstNameEn;
				passengerTemp.lastNameEn = lastNameEn;
				if(cnNameReg.test(firstName) && cnNameReg.test(lastName)){
					passengerTemp.passengerName = firstName + lastName;
				}else{
					passengerTemp.passengerName = firstName + "/" + lastName;
				}
				passengerTemp.passengerEnName = firstNameEn + "/" + lastNameEn;
				if(isDomestic != "domestic"){
					passengerTemp.credentialsDeadLine = $(this).find(".credentialsDeadLine").val();
					if($(this).find(".country").val() != ''){
						passengerTemp.country = $(this).find(".country").val();
					}else{
						passengerTemp.country = '中国';
					}
				}
				passengerList.push(passengerTemp);
			});
			params.passengerList = passengerList;
			return params;
		},
		submitValidate : function(){
			var lastResult = true;
			//校验input
			$('input[data-cv]:not(".datepicker")').each(function(i,ele){
				var r1 = $(ele).bookValidate({
	                validateAll: false
	            });
				if(!r1){
					lastResult = false;
				};
			});
			$('input.datepicker[data-cv]').each(function(i,ele){
				var r1 = $(ele).bookValidate({
	                validateAll: false
	            });
				if(!r1){
					lastResult = false;
				};
			});
			$("textarea.remark").each(function(i,ele){
				var r1 = $(ele).bookValidate({
	                validateAll: false
	            });
				if(!r1){
					lastResult = false;
				};
			});
			//校验下拉框
			$('ul[data-cv]').each(function(i,ele){
				var r1 = $(ele).bookValidate({
	                validateAll: false
	            });
				if(!r1){
					lastResult = false;
				};
			});
			return lastResult;
		},
		checkAgree : function(){
			if($("div.submitOrderBlock label.active").length < 1){
				$("#noAgreeTip").modal("show");
				return false;
			}
			return true;
		},
		submit : function() {
				var params = applyOrder.getParams();
				var productId = params.productId;
				var bookDate = params.bookDate;
				var packageId = params.packageId;
				$(".checkUser").addClass("submitted");
				$(".checkUser").removeClass("active");
				$.ajax({
					  url: basepath + '/product/book/apply',
					  dataType: 'json',
					  headers : {
						  'Accept' : 'application/json',
						  'Content-Type' : 'application/json'
					  },
					  type : 'POST',
					  data : JSON.stringify(applyOrder.getParams()),
					  success:  function(result) {
						  var tipMsg = '' , tipTitle = '' , confirmBtnTitle = '' , unconfirmBtnTitle = '', confirmBtnUrl = '', unconfrimBtnUrl = '';
						  if(result.res_code == "SF_ORDR_1001"){
							  _paq.push(['trackEvent', 'orderpage', 'ztrsubmitorder','success',result.res_msg]);
						  }else{
							  _paq.push(['trackEvent', 'orderpage', 'ztrsubmitorder','error',result.res_msg]);
						  }
						  $(".checkUser").addClass("active");
						  $(".checkUser").removeClass("submitted");
						  switch(result.res_code) {
							  case "EF_PDBK_1001":
								  var productNo = $("#productNo").val();
								  $(".checkUser").removeClass("active");
								  $(".checkUser").addClass("submitted");
								  window.location.href = basepath + "/product/book/noproduct/" + productNo ;
								  break;
							  case "EF_PDBK_1002":
								  $("#newPriceInfo").html(result.res_msg);
								  if (packageId != '') {
									  bookPrice.refreshPackage(productId,bookDate,packageId);
								  } else {
									  bookPrice.refresh(productId,bookDate);
								  }
								  $("#priceChangeTip").modal("show");
								  break;
							  case	"EF_PDBK_1004"://用户已存在,但未登录
								  $("#accountInputer").val(result.res_msg);
								  workplatformChange();
								  break;
							  case 	"EF_PDBK_1005":
							  	  $("#customError .modal-body p").html("系统建单失败,请稍后再试");
							  	  $("#customError").modal("show");
							  	  break;
							  case 	"EF_PDBK_1008":
							  	  $("#customError .modal-body p").html(result.res_msg);
							  	  $("#customError").modal("show");
							  	  break;
							  case 	"EF_PDBK_1009":
							  	  $("#orderFailed").show();
							  	  break;
							  case "SF_ORDR_1001":
								  $(".checkUser").removeClass("active");
								  $(".checkUser").addClass("submitted");
								  var orderId = result.res_msg;
								  window.location.href = basepath + "/orderPay/selectPayType/" + orderId;
								  break;
							  case "FO_ACTIVITY_1025":
							  case "FO_ACTIVITY_1026":
							  case "FO_ACTIVITY_1027":
								  $("#couponUsed").modal("show");
								  break;
							  case "EF_PDBK_1010":
								  $("#customError .modal-body p").html(result.res_msg);
							  	  $("#customError").modal("show");
								  break;
							  case "EF_MEMB_1036":
								  $("#newMobilePhone").val(result.res_msg);
								  bookLogin.initRegisterWin();
								  manulDestoryTimer(t) ;
								  toggleErrorHint(disappear) ;
								  $("#checkUserRegister").modal("show");
								  break;
							  default:
								  break;
						  }

					  }
				});
		}
}