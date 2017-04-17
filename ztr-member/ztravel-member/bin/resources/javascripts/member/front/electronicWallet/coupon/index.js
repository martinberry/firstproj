
 $(function(){

            $(".voucher-switch > li").each(function(index){
                $(this).click(function(){
                    $(".voucher-switch > li").removeClass("current");
                    $(this).addClass("current");
                    $(".voucherList").hide();
                  var couponItemType =  $(this).html();
                  changeStatusSearchFunc();
                });
            });

            submitFunc();

            $(".modal-footer").delegate(".waitShare","click",function(){
            	var selfMobile = $("#selfMobile").val();
    			if (selfMobile == null || selfMobile == "") {
         		 	toggleErrorHint("尚未绑定手机号，请到个人中心进行绑定",'#phone_errorHint');
    				return false;
    			}
        		var couponItemId =$("#share_couponItemId").val();
        		var destMembeId =$("#destMembeId").val();
        		var shareAmount =$("#shareAmount").val();
        		var selfMobile = $("#selfMobile").val();
        		var sharerPhone = $("#sharer_phone").val();

        		if(null!=couponItemId && "" !=couponItemId && null !=destMembeId && "" !=destMembeId && $(".errorHint:visible").parent().length <=0){
        			//判断分享者与被分享者是否挂起，如果有挂起的跳转到首页，如果木有继续分享
        			 $.ajax({
     					type : "get",
     					url : memberServer + "/electronicWallet/coupon/membersIsActive",
     					data : {
     						destMembeId: destMembeId
     					},
     					async:false,
     					dataType : "json",
     					success : function(data) {
     						if(data.isActive=='no'){
     							window.location.href= memberServer+"/home"
     						}
     					}
     				});
        			 $.ajax({
        					type : "get",
        					url : memberServer + "/electronicWallet/coupon/transferCouponItem",
        					data : {
        						couponItemId: couponItemId,
        						destMembeId: destMembeId,
        						shareAmount:shareAmount
        					},
        					dataType : "json",
        					async:false,
        					success : function(data) {
        						if(data.success){
        							$("#shared-notice").removeClass("hidden");
        							$("#confirmShare").html("知道了");
        							$("#confirmShare").addClass("shareSuccessed");
        							$("#confirmShare").removeClass("waitShare");
        						     $("#sharer_phone").attr("readonly",true);
        							_paq.push(['trackEvent', 'ewalletpage', 'ztrcouponshare', couponItemId, 'success']);
        						}else{
        							toggleErrorHint("",'#phone_errorHint') ;
        			     			toggleErrorHint(data.errMsg,'#phone_errorHint');
        							_paq.push(['trackEvent', 'ewalletpage', 'ztrcouponshare', couponItemId, 'fail']);
        						}
        					}
        				});
        		}
        	});

           $(".modal-footer").delegate(".shareSuccessed","click",function(){
        	   if(!$("#confirmShare").hasClass("waitShare")){
        		   $("#confirmShare").removeClass("shareSuccessed");
        		   $("#confirmShare").addClass("waitShare");
	           		$("#destMembeId").val('');
	           		$("#confirmShare").html("确认分享");
	   				$("#sharer_head_img").hide();
	   				$("#share-voucher").modal("hide");
	   			    $("#sharer_phone").attr("readonly",false);
	   			    $("#shared-notice").addClass("hidden");
	   				submitFunc();
        	   }
           });



           $(".modal-header").delegate(".close","click",function(){
        	   if(!$("#confirmShare").hasClass("waitShare")){
        		   $("#confirmShare").removeClass("shareSuccessed");
        		   $("#confirmShare").addClass("waitShare");
	           		$("#destMembeId").val('');
	           		$("#confirmShare").html("确认分享");
	   				$("#sharer_head_img").hide();
	   				$("#share-voucher").modal("hide");
	   			    $("#sharer_phone").attr("readonly",false);
	   			    $("#shared-notice").addClass("hidden");
	   				submitFunc();
        	   }
           });








     	  $(".share-person-block").delegate("#sharer_phone","blur",function(){
     		  if($("#sharer_phone").attr("readonly")=="readonly"){
     			  return false;
     		  }

          	var selfMobile = $("#selfMobile").val();
  			if (selfMobile == null || selfMobile == "") {
       		 	toggleErrorHint("尚未绑定手机号，请到个人中心进行绑定",'#phone_errorHint');
  				return false;
  			}
     		 toggleErrorHint("",'#phone_errorHint') ;

     		  var phone = $(this).val();
     		 if(!validateSharePerson(phone)){
     			 return false;
     		 }
     		 if(null!=phone && null!= selfMobile && phone == selfMobile){
     			toggleErrorHint("",'#phone_errorHint') ;
     			toggleErrorHint("不能分享给自己,请输入好友号码",'#phone_errorHint');
     			$("#sharer_head_img").hide();
     			return false;
     		 }else{
     			if(isPhoneRegist(phone) == "error"){
    				return false;
    			}
     		 }

     		  var memberId = $("#memberId").val();
     		  if(null!=phone && ''!=phone && isPhone(phone)){
     			   getNickNameByPhone(phone);
     		  }
     		  if(null==phone || "" == phone){
     			 $("#destMembeId").val('');
     			$("#sharer_head_img").hide();

     		  }
      	   });

     	   $(".voucher-instruction").attr("title", $("#voucherIns-tooltip").html());

           $(".green-border-tooltip").tooltip({
               html: true,
               placement: 'bottom',
               template: '<div class="tooltip green-border-tooltip" role="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>'
           });
        });

 function isPhoneRegist(sharePhone){
			var result ="yes";
		//手机号码对应的用户存在，不存在，提示用户未注册,
		 $.ajax({
				type : "get",
				url : memberServer + "/electronicWallet/coupon/checkMobile",
				data : {
					mobile:sharePhone
				},
				async:false,
				success : function(data) {
					if(data.res_code !='EF_MEMB_1002'){
						result = "no";
					}
				},
				error:function(data){
					result = "error";
				}
			});
		 return result;
 }

 function validateSharePerson(person){
	 var result =true;
	 if(null !=person && "" !=person){

		 if(!isNaN(person)){
			 $("#sharer_head_img").hide();
			 if(!isPhone(person)){
				 toggleErrorHint("",'#phone_errorHint') ;
	  			toggleErrorHint("请输入手机号码",'#phone_errorHint');
	  			result =  false;
			 }
		 }else{
			   var nickNameReg = /^([a-zA-Z\u4E00-\u9FA5]{1,11})$/;
				var destMembeId =$("#destMembeId").val();
			   if(destMembeId =="" ||  !(nickNameReg.test(person)) ){
				    toggleErrorHint("",'#phone_errorHint') ;
		           	toggleErrorHint("请输入手机号码",'#phone_errorHint') ;
		        	$("#sharer_head_img").hide();
		   			result= false;
	           }
		 }
	 }else{
		 	toggleErrorHint("",'#phone_errorHint') ;
			toggleErrorHint("请输入手机号码",'#phone_errorHint');
			$("#sharer_head_img").hide();
			result =  false;
	 }
	 return result;
 }

 function isPhone(phoneNum){
	 var r= /^(?:13\d|14\d|15\d|17\d|18\d)\d{8}$/ ;
	if(r.test(phoneNum)){
		return true;
	}else{
		return false;
	}
}

 function getNickNameByPhone(phone){

	 var url = memberServer + "/electronicWallet/coupon/getNickNameByPhone";
	 console.log("url: "+url);
	 $.ajax({
			type : "get",
			url : url,
			data : {
				phone:phone
			},
			dataType : "json",
			success : function(data) {

				if(data.result=='success'){
					if(null!=data.destMember){
						toggleErrorHint("",'#phone_errorHint') ;
						var imgSrc = mediaserver+"imageservice?mediaImageId="+data.destMember.headImageId;
						$("#sharer_head_img").attr("src",imgSrc);
						$("#sharer_head_img").show();
						$("#sharer_phone").val(data.destMember.nickName);
						$("#destMembeId").val(data.destMember.id);
					}
				}
				if(data.result=='fail'){
					toggleErrorHint("",'#phone_errorHint') ;
		    		toggleErrorHint(data.msg,'#phone_errorHint') ;
				}
			}
		});
 }

//提交查询请求
 function submitFunc() {

 	var criteria = {};
 	var status = $(".voucher-switch .current").html();
 	if(null!=status){
 		if(status=='未使用'){
 			criteria.status = "AVAILABLE";
 		}
 		if(status=='已使用'){
 			criteria.status = "USED";
 		}
 		if(status=='已失效'){
 			criteria.status = "INVALID"
 		}
 		if(status=='已赠送'){
 			criteria.status = "SHARED"
 		}
 	}
 	criteria.pageNo = $("input[name=pageNo]").val();
 	criteria.pageSize = $("input[name=pageSize]").val();

 	$.ajax({
 		type : "POST",
 		url : "../coupon/search",
 		data : JSON.stringify(criteria),
 		headers : {
 			'Accept' : 'application/json',
 			'Content-Type' : 'application/json'
 		},
 		dataType : "html",
 		success : function(result) {
 			var dataArray = result.split("<-split->");
 			var tableData = dataArray[0];
 			var paginationData = dataArray[1];

 			$(".voucherListContainer").html(tableData);

 			$("#searchField").html(paginationData);
 			$(window).trigger("resize");
 		},
 	})
 }

//提交查询请求
 function changeStatusSearchFunc() {

 	var criteria = {};
 	var status = $(".voucher-switch .current").html();
 	if(null!=status){
 		if(status=='未使用'){
 			criteria.status = "AVAILABLE";
 		}
 		if(status=='已使用'){
 			criteria.status = "USED";
 		}
 		if(status=='已失效'){
 			criteria.status = "INVALID"
 		}
		if(status=='已赠送'){
 			criteria.status = "SHARED"
 		}
 	}
 	criteria.pageNo = 1;
 	criteria.pageSize = $("input[name=pageSize]").val();

 	$.ajax({
 		type : "POST",
 		url : "../coupon/search",
 		data : JSON.stringify(criteria),
 		headers : {
 			'Accept' : 'application/json',
 			'Content-Type' : 'application/json'
 		},
 		dataType : "html",
 		success : function(result) {
 			var dataArray = result.split("<-split->");
 			var tableData = dataArray[0];
 			var paginationData = dataArray[1];

 			$(".voucherListContainer").html(tableData);

 			$("#searchField").html(paginationData);
 			$(window).trigger("resize");
 		},
 	})
 }


 $(function(){

     $("body").on("click","a[name='refundCoupon']", function(){
    	 var couponItemId = $(this).siblings("[name='couponItemId']").val();
         if(null!=couponItemId){
          	$("#refund-couponItemId").val(couponItemId);
         }
         $("#voucher-refund").modal("show");
     });

	 $(".voucher-exchange").click(function(){
		 $("#exchangeCode").val("");
		 toggleErrorHint("",'#exchange_errorHint') ;
          $("#exchange-voucher").modal("show");
      });

	 var submit_lock = false ;
	 $("#confirmExchange").click(function(){
		 var exchangeCode = $("#exchangeCode").val();
		 if(exchangeCode == null || exchangeCode =="" || !validateExchangeCode()){
			 toggleErrorHint("兑换码输入有误，请重新输入",'#exchange_errorHint') ;
			 return false;
		 }
		 if(!submit_lock){
			 submit_lock = true;
			 $.ajax({
				 type:"GET",
				 url:"../coupon/exchange",
				 data:{
					 exchangeCode : exchangeCode
				 },
				 success:function(data){
					 submit_lock = false;
						if(data.res_code=='success'){
							$("#exchange-voucher").modal("hide");
							$("#voucher-exchange-success").modal("show");
						}else{
							$("#exchange-voucher").modal("show");
							toggleErrorHint("兑换码输入有误，请重新输入",'#exchange_errorHint') ;
						}
				 },
				 error:function(data){
					 submit_lock = false;
				 }
			 })
		 }

	 })

	 $("#exchange-success-ok").click(function(e){
		 submitFunc();
	 })

	 $("#cancelExchange").click(function(e){
		 $("#exchange-voucher").modal("hide");
	 })

	 $("#refund-success-ok").click(function(e){
		 submitFunc();
	 });

	 var refund_lock = false;
      $("#confirm-refund").click(function(e){
    	  var couponItemId = $("#refund-couponItemId").val();
    	  if(couponItemId == null || couponItemId == ''){
    		  return false;
    	  }

    	  if(!refund_lock){
    		  refund_lock = true;
    		  $.ajax({
        		  type:"GET",
        		  url:"../coupon/refundVoucher",
        		  data:{
        			  couponItemId : couponItemId
        		  },
        		  success: function(data){
        			  refund_lock = false;
        			  if(data.res_code=="success"){
        				  $("#voucher-refund-success").modal("show");
        				  submitFunc();
        			  }else{
        				  $("#voucher-refund-failed").show();
        			  }
        		  },
        		  error: function(data){
        			  refund_lock = false;
        			  $("#voucher-refund-failed").show();
        		  }
        	  })
    	  }

      });
 })

 function validateExchangeCode(){
	 var result = true;
 	  var exchangeCodeRegx = /^[A-Za-z0-9]{6}$/;
 	  var exchangeCode = $("#exchangeCode").val();
 	 toggleErrorHint("",'#exchange_errorHint') ;
	  if(!exchangeCodeRegx.test(exchangeCode)){
		  	result= false;
  			toggleErrorHint("兑换码输入有误，请重新输入",'#exchange_errorHint') ;
      }
	  return result;
 }

