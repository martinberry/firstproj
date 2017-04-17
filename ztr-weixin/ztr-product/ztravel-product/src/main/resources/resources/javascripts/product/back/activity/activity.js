
/**
 *
 */
var clickEffective = false;
var clickTerminate = false;
var clickSaveDraft = false;
var clickGrant = false;
var addCouponLock = false;

var activity = {
		initBtn : function(){
			//活动列表点击添加活动按钮
			$("#addCustomerGet").click(function(){
				window.location.href = basepath + "/activity/add/customerGet";
			});
			$("#addSystemSend").click(function(){
				window.location.href = basepath + "/activity/add/systemSend";
			});
			$("#saveDraft").click(function(){
				if(!clickSaveDraft){
					clickSaveDraft = true;
					activity.save();
				}
			});
			$("#searchBtn").click(function(){
				searchByParams()
			});
			//活动保存草稿,生效,终止,发券确认操作按钮
			$("button.sureEffect").click(function(){
				if(!clickEffective){
					clickEffective = true;
					activity.effectitve();
				}用户领券活动
			});
			$("button.sureTerminate").click(function(){
				if(!clickTerminate){
					clickTerminate = true;
					activity.terminate();
				}
			});
			$("button.sureDelete").click(function(){
				activity.deleteCoupon($(this).attr("data-val"));
			});
			$("button.sureAddCoupon").click(function(){
				activity.addCoupon();
			});
			$("button.successSaveDraft").click(function(){
				var activityId = $("#activityId").val();
				window.location.href = basepath + "/activity/edit/" + activityId ;
			});
			$("button.successGrant").click(function(){
				$("#deliverWindow").modal("hide");
			});
		},
		initViewCoupon : function(){
			$(".commonTab tbody a.viewVoucherInfo").each(function(){
				$(this).click(function(){
					var activityId = $("#activityId").val();
					var couponItemId = $(this).data("val");
					$.ajax({
						type : "POST",
						url : basepath + "/activity/editCoupon/"+activityId+"/"+couponItemId,
						headers : {
							'Accept' : 'application/json',
							'Content-Type' : 'application/json'
						},
						dataType: 'json',
						success : function(result) {
							if(result.res_code == "SO_ACTIVITY_1013"){
								activity.showCouponInfo(result.res_msg);
							}else{
								alert(result.res_msg);
							}
						}
					});
				});
			});
		},
		initCouponUseDetail : function(){
			$(".commonTab tbody a.couponUserDetail").each(function(){
				$(this).click(function(){
					var activityType = $("#type").val();
					var activityId = $("#activityId").val();
					var systemSendCount = $(this).parent().siblings().find(".voucherTotalNum").val();
					var couponItemId = $(this).data("val");
             if($.trim($(".userRangeCont").html())=="大宁活动"){
            		window.location.href = basepath + "/operator/couponUserDetail/dnindex/"+activityId+"/"+couponItemId+"/"+systemSendCount;
             }
             else{
            		window.location.href = basepath + "/operator/couponUserDetail/index/"+activityId+"/"+couponItemId+"/"+systemSendCount;
             }
				});
			});
		},
		initWin:function(){
	        $("button.effect").click(function(){
	            $("#effectWindow").modal("show");
	        });
	        $("button.cancleEffect").click(function(){
	        	$("#effectWindow").modal("hide");
	        });
	        $("button.termination").click(function(){
	            $("#terminationWindow").modal("show");
	        });
	        $("button.cancleTerminate").click(function(){
	            $("#terminationWindow").modal("hide");
	        });
	        $("#grant").click(function(){
	        	if(!clickGrant){
	        		clickGrant = true;
	        		activity.grant();
	        	}
	        });
	        $("#submitCus").click(function(){
	        	if(!clickGrant){
	        	 	clickGrant = true;
		            activity.grant();
	        	}
	        });
	        $("button.cancleAddCoupon").click(function(){
	        	////////////////////////////////////////////////////////
	        	$("#voucherWindow").modal("hide");
	        });




	        $("body").on("click", ".delVoucher", function(){
	        	$("#deleteWindow button.sureDelete").attr("data-val",$(this).data("val"));
	            $("#deleteWindow").modal("show");
	        });
	        $("body").on("click", ".cancleDelete", function(){
	            $("#deleteWindow").modal("hide");
	        });
	        $("body").on("click", ".addVoucherBtn:not(.disabled)", function(){
	        	////////////////////////////////////////////////////
	        	$("#voucherWindow  input[readonly != 'readonly']").val("");
	        	$("div.addCouponDiv label.addAllProduct").click();
	        	///////////////////////////////////////////////////
	        	$("#voucherWindow").find("div.verifyStyle").remove();
	        	$("#voucherWindow").find("input.verifyBox").removeClass("verifyBox")

	        	if($.trim($(".userRangeCont").html())=="大宁活动"){
	        		$("#amount").attr("maxlength",6);
	        		$("#amount").attr("data-cv","required,number");
	        		$("#common").hide();
	        		$("#special").show();
	        		 $("#orderLeast").attr("maxlength",7);
	        		 $(".salepriceBlocks").show();
	        		  $("#voucherWindow").modal("show");
	        	}else{
	        		$("#amount").attr("maxlength",4);
	        		$("#amount").attr("data-cv","required,positiveNum");
	        		$("#common").show();
	        		$("#special").hide();
	        		$("#orderLeast").attr("maxlength",5);
	        		 $(".salepriceBlocks").hide();
	        		  $("#voucherWindow").modal("show");
	        	}

	        });
	        $("body").on("click", ".addAllProduct", function(){
	        	$(this).parents('td').find('div.verifyStyle').remove()
	        	$("textarea.productRange").hide();
	        	$("textarea.productRange").val("");
	        	$("textarea.productRange").attr("data-cv","productRange");
	        });
	        $("body").on("click", ".addInputProduct", function(){
	        	$("textarea.productRange").attr("data-cv","required,productRange");
	        	$("textarea.productRange").show();
	        });
		},
		initUserSelect : function(){
			//用户范围切换
	        $(".setUserBySelect").click(function(){
	            $(".selectUserBlock").show();
	            if($(".selectUserBlock label.active").length == 0){
	            	$(".selectUserBlock label").first().addClass("active");
	            }
	            $(".inputUserBlock").hide();
	            $(".inputUserTextarea").attr("readonly", "");
	            $(".inputUserTextarea").attr("data-cv", "");
	        });

	        $(".setUserByInput").click(function(){
	            $(".selectUserBlock").hide();
	            $(".inputUserTextarea").attr("readonly", "readonly");
	            $(".inputUserBlock").show();
	            $(".inputUserTextarea").attr("data-cv", "required,userRange");
	        });
	        //编辑手动输入用户名
	        $(".inputUserBlock .editBtn").click(function(){
	            $(".inputUserTextarea").removeAttr("readonly");
	            $(".operInputUser.edit").hide();
	            $(".operInputUser.save").show();
	        });
	        $(".inputUserBlock .saveBtn").click(function(){
	        	$.ajax({
					url: basepath + '/activity/checkMobile',
					  dataType: 'json',
					  headers : {
						  'Accept' : 'application/json',
						  'Content-Type' : 'application/json'
					  },
					  type : 'POST',
					  data : $("textarea.inputUserTextarea").val(),
					  success:  function(result) {
						  if(result.res_code == "SO_ACTIVITY_1019"){
							  $(".inputUserTextarea").attr("readonly", "readonly");
					            $(".operInputUser.save").hide();
					            $(".operInputUser.edit").show();
						  }else{
							  alert(result.res_msg);
						  }
					  }
				});
	        });
	        $(".inputUserBlock .cancelBtn").click(function(){
	        	if($("div.inputUserBlock div.verifyStyle").length > 0){
	        		$("div.inputUserBlock div.verifyStyle").remove();
	        	}
	        	$(".inputUserTextarea").val($("#originUserRange").html());
	        	$(".inputUserTextarea").attr("readonly", "readonly");
	            $(".operInputUser.save").hide();
	            $(".operInputUser.edit").show();
	        });
		},
		initEditCoupon : function(){
	        $(".voucherInfoTable").on("click", ".edit", function(){
	        	var activityType= $("#type").val();
	        	if(activityType == "SYSTEMSEND"){
	        		$(this).parents("td").siblings("td").find("input.voucherPerNum").removeAttr("readonly");
	        		$(this).parents("tbody").find("input.voucherTotalNum").removeAttr("data-cv");
	        	}else{
	        		$(this).parents("td").siblings("td").find("input.voucherPerNum").removeAttr("readonly");
	        		$(this).parents("td").siblings("td").find("input.voucherTotalNum").attr("data-cv","required,positiveNum");
	        		$(this).parents("td").siblings("td").find("input.voucherTotalNum").removeAttr("readonly");
	        	}
	            $(this).hide();
	            $(this).siblings("a.save").show();
	        });
	        $(".voucherInfoTable").on("click", ".save", function(){
	        	activity.updateCouponNumInfo($(this));
	        });
	        $(".voucherInfoTable").on("click", ".terminateCoupon", function(){
	        	activity.terminateCoupon($(this).data("val"));
	        });
		},
		initValidate : function(){
				$('input[data-cv]:not(".datepicker")').each(function(){
			  		$(this).blur(function(){
			  			var validateResult = $(this).bookValidate({
			                validateAll: false
			            });
			  			console.log(validateResult);
			  		});
			  	});




			$('input.datepicker[data-cv]').each(function(){
				$(this).on('changeDate', function(){
					var validateResult = $(this).bookValidate({
		                validateAll: false
		            });
		  			console.log(validateResult);
				});
			});
			$("textarea").each(function(){
				$(this).blur(function(){
					var validateResult = $(this).bookValidate({
		                validateAll: false
		            });
		  			console.log(validateResult);
				});
			});
		},
		initCouponNumValidate : function(){
			$(".commonTab tbody input[data-cv]").each(function(i,ele){
				$(this).blur(function(){
		  			var validateResult = $(this).bookValidate({
		                validateAll: false
		            });
		  			console.log(validateResult);
		  		});
			});
		},
		validateActivity : function(){
			var lastResult = true;
			//校验input
			$('.activityTable input[data-cv]').not(".datepicker").each(function(i,ele){
				var r1 = $(ele).bookValidate({
	                validateAll: false
	            });
				console.log(r1);
				if(!r1){
					lastResult = false;
				};
			});
			$(".activityTable input.datepicker[data-cv]").each(function(i,ele){
				var r1 = $(ele).bookValidate({
	                validateAll: false
	            });
				console.log(r1);
				if(!r1){
					lastResult = false;
				};
			});
			$("div.inputUserBlock textarea").each(function(){
				var validateResult = $(this).bookValidate({
					validateAll: false
				});
				if(!validateResult){
					lastResult = false;
				}
				console.log(validateResult);
			});
			return lastResult;
		},
		validateCoupon : function(){
			var lastResult = true;
			//校验input
			///////////////////////////////////////////////////
				$('#voucherWindow input[data-cv]').each(function(i,ele){
					var r1 = $(ele).bookValidate({
		                validateAll: false
		            });
					console.log(r1);
					if(!r1){
						lastResult = false;
					};
				});

			/////////////////////////////////////////////////
			$('#voucherWindow input.datepicker[data-cv]').each(function(i,ele){
				var r1 = $(ele).bookValidate({
	                validateAll: false
	            });
				console.log(r1);
				if(!r1){
					lastResult = false;
				};
			});
			///////////////////////////////////////////////////
			$("#voucherWindow textarea").each(function(){
				var validateResult = $(this).bookValidate({
					validateAll: false
				});
				console.log(validateResult);
				if(!validateResult){
					lastResult = false;
				}
			});
			return lastResult;
		},
		validateCouponNum : function(){
			var lastResult = true;
			//校验input
			$(".commonTab tbody input[data-cv]").each(function(i,ele){
				var r1 = $(ele).bookValidate({
	                validateAll: false
	            });
				console.log(r1);
				if(!r1){
					lastResult = false;
				};
			});
			return lastResult;
		},
		save : function(){
			if($(".inputUserTextarea[readonly!='readonly']").length > 0){
				alert("先保存用户范围");
				clickSaveDraft = false;
				return;
			}
			var validateName = $("#name").bookValidate({
                validateAll: false
            });
			if(validateName){
				var params = activity.getParams();
				console.log(JSON.stringify(params));
				$.ajax({
					url: basepath + '/activity/save',
					dataType: 'json',
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : 'POST',
					data : JSON.stringify(params),
					success:  function(result) {
						if(result.res_code == "SO_ACTIVITY_1001"){
							var activityId = result.res_msg;
							$("#activityId").val(activityId);
							$("#draftWindow").modal("show");
							clickSaveDraft = false;
						}
						if(result.res_code == "FO_ACTIVITY_1002"){
							clickSaveDraft = false;
							alert(result.res_msg);
						}
						if(result.res_code == "FO_ACTIVITY_1022"){
							clickSaveDraft = false;
							alert(result.res_msg);
						}
						if(result.res_code == "FO_ACTIVITY_1025"){
							clickSaveDraft = false;
							alert(result.res_msg);
						}
					}
				});
			}else{
				clickSaveDraft = false;
			}
		},
		effectitve: function(){
			if($(".inputUserTextarea[readonly!='readonly']").length > 0){
				$("#effectWindow").modal("hide");
				alert("先保存用户范围");
				clickEffective = false;
				return;
			}
			if($("div.grantType label.active").data("val") == 'MANUALADD'){//手动输入用户
				$(".inputUserTextarea").attr("data-cv", "required,userRange");
			}
			if(activity.validateActivity()){
				var params = activity.getParams();
				console.log(JSON.stringify(params));
				var activityId = $("#activityId").val();
				$.ajax({
					  url: basepath + '/activity/effectitve',
					  dataType: 'json',
					  headers : {
						  'Accept' : 'application/json',
						  'Content-Type' : 'application/json'
					  },
					  type : 'POST',
					  data : JSON.stringify(params),
					  success:  function(result) {
						  if(result.res_code == "SO_ACTIVITY_1003"){
							  var activityId = $("#activityId").val();
							  window.location.href = basepath + "/activity/edit/" + result.res_msg;
						  }
						  if(result.res_code == "FO_ACTIVITY_1004"){
							  clickEffective = false;
							  alert(result.res_msg);
						  }
						  if(result.res_code == "FO_ACTIVITY_1022"){
							  clickEffective = false;
							  alert(result.res_msg);
							}
					  }
				});
			}else{
				 clickEffective = false;
				 $("#effectWindow").modal("hide");
			}
		},
		terminate: function(){
			var activityId = $("#activityId").val();
			$.ajax({
				  url: basepath + '/activity/terminate',
				  dataType: 'json',
				  headers : {
					  'Accept' : 'application/json',
					  'Content-Type' : 'application/json'
				  },
				  type : 'POST',
				  data : activityId,
				  success:  function(result) {
					  if(result.res_code == "SO_ACTIVITY_1005"){
						  var activityId = $("#activityId").val();
						  window.location.href = basepath + "/activity/edit/" + activityId ;
					  }
					  if(result.res_code == "FO_ACTIVITY_1006"){
						  clickTerminate = false;
						  alert(result.res_msg);
					  }else{
						  clickTerminate = false;
					  }
				  }
			});
		},
		grant:function(){
			if($(".voucherInfoTable").find("input[readonly!='readonly']").length > 0){
				alert("请先保存编辑状态的代金券");
				clickGrant = false;
				return;
			}

			var activityId = $("#activityId").val();

			$.ajax({
				  url: basepath + '/activity/grantCoupon/'+activityId,
				  dataType: 'json',
				  headers : {
					  'Accept' : 'application/json',
					  'Content-Type' : 'application/json'
				  },
				  type : 'POST',
				  success:  function(result) {
					  if(result.res_code == "SO_ACTIVITY_1017"){
						  $("#deliverWindow").modal("show");
						  activity.showCoupons();
						  clickGrant = false;
					  }
					  if(result.res_code == "FO_ACTIVITY_1018"){
						  alert(result.res_msg);
						  clickGrant = false;
					  }
				  }
			});
		},
		showCoupons : function(){
			var activityId = $("#activityId").val();
			$.ajax({
				type : "POST",
				url : basepath + '/activity/getCoupons',
				data : activityId,
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				dataType : "html",
				success : function(result) {
					$(".commonTab tbody").html(result);
					activity.initViewCoupon();
					activity.initCouponUseDetail();
					activity.initCouponNumValidate();
				}
			});
		},
		showCouponInfo : function(result){
			var result = $.parseJSON(result);
			$("#voucherInfoWindow td.couponName").html(result.name);
			$("#voucherInfoWindow td.amount").html(result.amount+"元");
			$("#infoprice").hide();
			if(result.price!=null){
				$("#infoprice").show();
				$("#voucherInfoWindow td.saleprice").html(result.price+"元");
			}

			$("#voucherInfoWindow td.orderLeast").html(result.orderLeast+"元");
			$("#voucherInfoWindow span.validTimeFrom").html(result.validTimeFrom);
			$("#validTimeToInfo").html(result.validTimeTo);
			$("#voucherInfoWindow td.description").html(result.description);
			if(result.productRangeType == "ALLPRODUCTS"){
				$("#productContent").html("所有产品");
			}else{
				$("#productContent").html(result.productRange);
			}
			$("#voucherInfoWindow").modal("show");
		},
		addCoupon : function(){
			if(!addCouponLock && activity.validateCoupon()){
				addCouponLock = true ;
				var params = activity.getCouponParams();
				$.ajax({
					url: basepath + '/activity/addCoupon',
					dataType: 'json',
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : 'POST',
					data : JSON.stringify(params),
					success:  function(result) {
						if(result.res_code == "SO_ACTIVITY_1007"){
							/////////////////////////////////
							$("#voucherWindow").modal("hide");
							activity.showCoupons();
						}else{
							alert(result.res_msg);
						}
						addCouponLock = false ;
					},error: function(){
						addCouponLock = false ;
					}
				});
			}
		},
		updateCouponNumInfo : function(obj){
			if(activity.validateCouponNum()){
				var params = {};
				var activityType = $("#type").val();
				if(activityType == "SYSTEMSEND"){
					params.unit =$(obj).parents("td").siblings("td").find("input.voucherPerNum").val();

				}else{
					params.totalNum = $(obj).parents("td").siblings("td").find("input.voucherTotalNum").val();
					params.unit =$(obj).parents("td").siblings("td").find("input.voucherPerNum").val();
				}
				params.activityId = $("#activityId").val();
				params.couponId = $(obj).data("val");
				console.log(JSON.stringify(params));
				$.ajax({
					url: basepath + '/activity/updateCouponNum',
					dataType: 'json',
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : 'POST',
					data:JSON.stringify(params),
					success:  function(result) {
						if(result.res_code == "SO_ACTIVITY_1011"){
							activity.showCoupons();
						}
						if(result.res_code == "FO_ACTIVITY_1012"){
							alert(result.res_msg);
						}
					}
				});
			}
		},
		deleteCoupon : function(couponId){
			var activityId = $("#activityId").val();
			$.ajax({
				url: basepath + '/activity/deleteCoupon/'+activityId + "/"+couponId,
				  dataType: 'json',
				  headers : {
					  'Accept' : 'application/json',
					  'Content-Type' : 'application/json'
				  },
				  type : 'POST',
				  success:  function(result) {
					  $("#deleteWindow").modal("hide");
					  if(result.res_code == "SO_ACTIVITY_1009"){
						  activity.showCoupons();
					  }
					  if(result.res_code == "FO_ACTIVITY_1010"){
						  alert(result.res_msg);
					  }
				  }
			});
		},
		terminateCoupon : function(couponId){
			var activityId = $("#activityId").val();
			$.ajax({
				url: basepath + '/activity/terminateCoupon/'+activityId + "/"+couponId,
				  dataType: 'json',
				  headers : {
					  'Accept' : 'application/json',
					  'Content-Type' : 'application/json'
				  },
				  type : 'POST',
				  success:  function(result) {
					  if(result.res_code == "SO_ACTIVITY_1015"){
						  activity.showCoupons();
					  }
					  if(result.res_code == "FO_ACTIVITY_1016"){
						  alert(result.res_msg);
					  }
				  }
			});
		},
		getParams : function(){
			var params = {};
			params.id = $("#activityId").val();
			params.type = $("#type").val();
			params.code = $("#activityCode").val();
			params.name = $("#name").val();
			params.remark = $("#remark").val();
			params.startTime = $("#startTime").val();
			params.startHourFrom = $("#startHourFrom").val();
			params.startMinFrom = $("#startMinFrom").val();
			params.endHourTo = $("#endHourTo").val();
			params.endMinTo = $("#endMinTo").val();
			params.endTime = $("#endTime").val();
			params.grantType = $("td.userRangeCont div.grantType label.active").data("val");
			if(params.grantType == 'BATCHCONFIG'){
				params.userRangType = $("td.userRangeCont div.selectUserBlock div.radioContent label.active").data("val");
				params.userRange = "";
			}else{
				params.userRangType = "MANUALADD";
				params.userRange = $("textarea.inputUserTextarea").val();
			}
			return params;
		},
		getCouponParams : function(){
			var params = {};
			params.activityId=$("#activityId").val();
			params.name = $("#couponName").val();
			params.amount = new Number($("#amount").val()).multiply(100);


			if($.trim($(".userRangeCont").html())=="大宁活动"){
				params.price=new Number($("#saleprice").val()).multiply(100);
			}


			params.orderLeast = new Number($("#orderLeast").val()).multiply(100);
			params.validTimeFrom = $("#validTimeFrom").val();
			params.validTimeTo = $("#validTimeTo").val();
			params.validHourFrom = $("#validHourFrom").val();
			params.validHourTo = $("#validHourTo").val();
			params.validMinFrom = $("#validMinFrom").val();
			params.validMinTo = $("#validMinTo").val();
			params.description = ($("#description").val().replace(/</g,"&lt")).replace(/>/g,"&gt");
			params.productRangeType = $("div.addCouponDiv label.active").data("val");

			if(params.productRangeType == "ALLPRODUCTS"){
				params.productRange = "ALL";
			}else{
				params.productRange = $("#productRange").val();
			}
			return params;
		},
		disableInput : function(){
			var activityStatus = $("#status").val();
			if(activityStatus != 'DRAFT'){
	            $(".activityTable input[type='text'], .activityTable textarea").attr("readonly", "readonly");
	            $(".activityTable").find("input.datepicker").attr("disabled", "disabled");
			}
		}
}
