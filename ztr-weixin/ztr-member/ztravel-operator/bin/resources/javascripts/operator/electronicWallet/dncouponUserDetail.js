
$(function(){
		submitFunc();
        $(".ticketStatusTab li").each(function(index){
            $(this).click(function(){
                $(".ticketStatusTab li").removeClass("current");
                $(this).addClass("current");
                var currentTab = $(this).html();
                dnSearchByParams();
                $(".ticketStatusList .ticketStatusItem").hide();
                $(".ticketStatusList").find(".ticketStatusItem").eq(index).show();
            });
        });

      	 $("body").on("blur","input[type='text']",function(){
       		 $("#systemLock_voucherCode").val($.trim($("#systemLock_voucherCode").val()));
       		$("#recieved_voucherCode").val($.trim($("#recieved_voucherCode").val()));
       		$("#recieved_reciever_mid").val($.trim($("#recieved_reciever_mid").val()));
       		$("#recieved_consumOrderId").val($.trim($("#recieved_consumOrderId").val()));
       		$("#recieved_consum_mid").val($.trim($("#recieved_consum_mid").val()));
       		$("#recieved_recieveOrderId").val($.trim($("#recieved_recieveOrderId").val()));
       		$("#unrefunded_voucherCode").val($.trim($("#unrefunded_voucherCode").val()));
       		$("#unrefunded_recieve_mid").val($.trim($("#unrefunded_recieve_mid").val()));
       		$("#unrefunded_recieve_orderId").val($.trim($("#unrefunded_recieve_orderId").val()));
       		$("#refunded_voucherCode").val($.trim($("#refunded_voucherCode").val()));
       		$("#refunded_recieve_mid").val($.trim($("#refunded_recieve_mid").val()));
       		$("#refunded_recieve_orderId").val($.trim($("#refunded_recieve_orderId").val()));
       	 })


        $("body").on("click",".blueFontsLink",function(){
        	  $("#confirmWindow").modal("show");
        })


        //系统占用opera
        $("body").on("click"," [name='systemOperat']", function(){
        	if($(this).hasClass("disabled")){
        		return false;
        	}
        	var obj=$(this);
        	var obj1=$(this).parents("td").siblings("[name='voucherType']");
        	var voucherCode = $(this).parents("td").siblings("[name='voucherCode']").html();
        	$("#updateVoucherCode").val(voucherCode);
        	$("#voucherTypeHide").val("");
        	var voucherType = $(this).parents("td").siblings("[name='voucherType']").html();
        	if(voucherType == '占用中'){
        		$("#confirmUpdateTip").html("确认取消系统占用操作？");
        		$("#voucherTypeHide").val("SYSTEM");
        	}else if(voucherType == '非占用'){
        		$("#confirmUpdateTip").html("确认系统占用操作？");
        		$("#voucherTypeHide").val("NORMAL");
        	}
        	$("#confirmUpdateVoucherTypeWindow").modal("show");


        })

        //通过审核
        $("body").on("click","a[name='auditedBtn']", function(){
        	  var obj=$(this).parents("td");
        	  var voucherOrderId =$(this).parents("td").siblings("[name='voucherOrderId']").html();
        	  var paymentType = $(this).parents("tr").find("[name='tabPayTypeHide']").val();
        	  var voucherCode =$(this).parents("td").siblings("[name='voucherCode']").html();
        	  $("#voucherOrderIdHide").val(voucherOrderId);
        	  $("#paymentTypeHide").val(paymentType);
        	  $("#voucherCodeAuditHide").val(voucherCode);
        	  $("#confirmAuditWindow").modal("show");
        	  $("#confirmPassAuditBtn").click(function(){
        		  confirmPassAudit();
              })
        })



         $("#cancelUpdateVoucherTypeBtn").click(function(){
        	  $("#confirmUpdateVoucherTypeWindow").modal("hide");
        })

        $("#lockAll").click(function(){
         	if($(this).hasClass("disabled")){
        		return false;
        	}
        	$("#confirmUpdateVoucherTypeAllWindow").modal("show");
        })

        $("#confirmUpdateVoucherTypeAllBtn").click(function(){
        	updateVoucherTypeAll();
        })

        $("#cancelUpdateVoucherTypeAllBtn").click(function(){
          	$("#confirmUpdateVoucherTypeAllWindow").modal("hide");
        })


        $("#updatedVoucherTypeOkBtn").click(function(){
        	$("#updateVoucherTypeSuccessWindow").modal("hide");
        })

          $("#UpdateVoucherTypeAllOkBtn").click(function(){
        	$("#updateVoucherTypeAllSuccessWindow").modal("hide");
//        	window.location.reload();
        })

        $("#passAuditOkBtn").click(function(){
        	$("#refundingConfirmWindow").modal("hide");
        })

        //
  		$("#recieved_payTimeFrom").datepicker('setStartDate', '2015-01-01');
      	$("#recieved_payTimeFrom").datepicker('setEndDate', '2099-12-31');
      	$("#recieved_payTimeTo").datepicker('setStartDate', '2015-01-01');
    	$("#recieved_payTimeTo").datepicker('setEndDate', '2099-12-31');
      	$("#unrefunded_payTime_from").datepicker('setStartDate', '2015-01-01');
      	$("#unrefunded_payTime_from").datepicker('setEndDate', '2099-12-31');
    	$("#unrefunded_payTime_to").datepicker('setStartDate', '2015-01-01');
    	$("#unrefunded_payTime_to").datepicker('setEndDate', '2099-12-31');
      	$("#unrefunded_applyRefundTime_from").datepicker('setStartDate', '2015-01-01');
      	$("#unrefunded_applyRefundTime_from").datepicker('setEndDate', '2099-12-31');
    	$("#unrefunded_applyRefundTime_to").datepicker('setStartDate', '2015-01-01');
    	$("#unrefunded_applyRefundTime_to").datepicker('setEndDate', '2099-12-31');
    	$("#refunded_payTime_from").datepicker('setStartDate', '2015-01-01');
    	$("#refunded_payTime_from").datepicker('setEndDate', '2099-12-31');
    	$("#refunded_payTime_to").datepicker('setStartDate', '2015-01-01');
    	$("#refunded_payTime_to").datepicker('setEndDate', '2099-12-31');
    	$("#refunded_refundTime_from").datepicker('setStartDate', '2015-01-01');
    	$("#refunded_refundTime_from").datepicker('setEndDate', '2099-12-31');
    	$("#refunded_refundTime_to").datepicker('setStartDate', '2015-01-01');
    	$("#refunded_refundTime_to").datepicker('setEndDate', '2099-12-31');

       $("#updatedVoucherTypeErrorBtn") .click(function(){
    	   $("#updateVoucherTypeErrorWindow").modal("hide");
       })
    });


function updateVoucherType(){
	var voucherCode = $("#updateVoucherCode").val();
	console.log("voucherCode: "+ voucherCode)
	var activityId = $("#activityId").val();

	$.ajax({
		type:"POST",
		url: basepath+"/operator/couponUserDetail/dn/changeVoucherTypeInit",
		data:{
			voucherCode:voucherCode,
			activityId:activityId
		},
		success:function(result){
			if(result.res_code == "SO_VOUCHER_1001"){
				$("#confirmUpdateVoucherTypeWindow").modal("hide");
				var voucherType = $("#voucherTypeHide").val();
	        	if(voucherType == 'SYSTEM'){
	        			$("#lockAll").html("全部占用");

	        		$("#updatedVoucherTypeTip").html("取消成功");
	        	}else if(voucherType == 'NORMAL'){
	        		$("#updatedVoucherTypeTip").html("该代金券已被系统占用成功");
	        	}
				$("#updateVoucherTypeSuccessWindow").modal("show");
			}else if(result.res_code == 'voucherRecieved'){
				$("#confirmUpdateVoucherTypeWindow").modal("hide");
				$("#updateVoucherTypeErrorWindow").modal("show");
			}
			 dnSearchByParams();
		},
		error:function(result){

		}
	})
}

function updateVoucherTypeAll(){
	var activityId = $("#activityId").val();
	var couponId =$("#couponCode").val();

	var currentVoucherType = $("#lockAll").html()
	var updateVoucherType ="";
	console.log("currentVoucherType: "+currentVoucherType);
	if(!isNullOrEmpty(currentVoucherType)){
		if(currentVoucherType=='全部占用'){
			updateVoucherType ="SYSTEM"
		}else if(currentVoucherType == '全部取消占用'){
			updateVoucherType="NORMAL"
		}
	}

	$.ajax({
		type:"POST",
		url: basepath+"/operator/couponUserDetail/dn/changeVoucherTypeAll",
		data:{
			activityId:activityId,
			couponId:couponId,
			voucherType:updateVoucherType
		},
		success:function(result){
			if(result.res_code == "SO_VOUCHER_1001"){
				$("#confirmUpdateVoucherTypeAllWindow").modal("hide");
				$("#updateVoucherTypeAllSuccessWindow").modal("show");
				if(updateVoucherType !=""){
					if(updateVoucherType == 'SYSTEM'){
						$("#lockAll").html("全部取消占用");
					}else if(updateVoucherType == 'NORMAL'){
						$("#lockAll").html("全部占用");
					}
				}
				 dnSearchByParams();

			}
		}
	})
}


function confirmPassAudit(){
//	var voucherOrderId =  $("#voucherOrderIdHide").val();
//	var paymentType =$("#paymentType").val();
//
//
//	console.log("voucherOrderId: "+ voucherOrderId);
//	console.log("paymentType:"+paymentType);
//	if(isNullOrEmpty(voucherOrderId) || isNullOrEmpty(paymentType)){
//		return false;
//	}

	var voucherCode = $("#voucherCodeAuditHide").val();

	$.ajax({
		type:"POST",
		url: basepath+"/operator/couponUserDetail/dn/passAudit",
		data:{
			voucherCode:voucherCode
		},
		success:function(result){
			if(result.res_code == "success"){
				$("#confirmAuditWindow").modal("hide");
				$("#refundingConfirmWindow").modal("show");
				dnSearchByParams();
			}
		}
	})
}

function isNullOrEmpty(strVal) {
		if (strVal == '' || strVal == null || strVal == undefined) {
		return true;
		} else {
		return false;
		}
	}

   //系统占用TAB
   $(function(){
	   //点击
	   $("#systemLockSearch").click(function(){

		   dnSearchByParams();
	   })

      $("#recievedSearch").click(function(){

    	  dnSearchByParams();
	   })

      $("#unrefundedSearch").click(function(){

    	  dnSearchByParams();
	   })

	      $("#refundedSearch").click(function(){

	    	  dnSearchByParams();
	   })

   })



function submitFunc(){
	   var criteria = buildCriteria();
		$.ajax({
			type : "POST",
			url : basepath+"/operator/couponUserDetail/dn/search",
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
				showTableData(tableData);
				$("#searchField").html(paginationData);
			},
		})



}



   function dnSearchByParams(){
	  $("input[name=pageNo]").val(1);
	  $("input[name=pageSize]").val(10);
	   var criteria = buildCriteria();
		$.ajax({
			type : "POST",
			url : basepath+"/operator/couponUserDetail/dn/search",
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
				showTableData(tableData);
				$("#searchField").html(paginationData);
			},
		})
   }


	function showTableData(tableData){
		   var currentTab =  $(".ticketStatusTab li.current").html();
		   if(currentTab == '初始'){
/*			   $("#systemLock_voucherCode").val("");
			   $("#systemLock_status").find("li").removeClass("active");
			   $("#systemLock_status").find("li").not($("li[name]")).addClass("active");*/
			   $("#systemLock_tbody").html(tableData);
	       }else if(currentTab == '已领取'){
/*	     	  $("#recieved_voucherCode").val("");
	    	  $("#recieved_reciever_mid").val("");
	    	  $("#recieved_payTimeFrom").val("");
	    	  $("#recieved_payTimeTo").val("");
	    	  $("#recieved_consumption").find("li").removeClass("active");
	   	      $("#recieved_consumption").find("li").not($("li[name]")).addClass("active");
	   	      $("#recieved_consumOrderId").val("");
	   	      $("#recieved_consum_mid").val("");
	   	      $("#recieved_systemLock_condition").find("li").removeClass("active");
	   	      $("#recieved_systemLock_condition").find("li").not($("li[name]")).addClass("active");
	          $("#recieved_recieveOrderId").val("");*/
	    	   $("#recieved_tbody").html(tableData);
	       }else if(currentTab == '待退款'){
/*	     	  $("#unrefunded_voucherCode").val("");
	    	  $("#unrefunded_recieve_mid").val("");
	    	  $("#unrefunded_payTime_from").val("");
	    	  $("#unrefunded_payTime_to").val("");
	    	  $("#unrefunded_recieve_orderId").val("");
	    	  $("#unrefunded_applyRefundTime_from").val("");
	    	  $("#unrefunded_applyRefundTime_to").val("");*/
	    	   $("#unrefunded_tbody").html(tableData);
	       }else if(currentTab == '已退款'){
/*		    	  $("#refunded_voucherCode").val("");
		    	  $("#refunded_recieve_mid").val("");
		    	  $("#refunded_payTime_from").val("");
		    	  $("#refunded_payTime_to").val("");
		    	  $("#refunded_recieve_orderId").val("");
		    	  $("#refunded_refundTime_from").val("");
		    	  $("#refunded_refundTime_to").val("");*/
	    	   $("#refunded_tbody").html(tableData);
	       }
	}


   function buildCriteria(){
	   var criteria = {};

	   //获取当前tab
	   var currentTab =  $(".ticketStatusTab li.current").html();
	   if(currentTab == '初始'){
		   criteria = buildInitCriteria();
		   criteria.searchTab = "INIT";
       }else if(currentTab == '已领取'){
    	   criteria = buildRecievedCriteria();
    	   criteria.searchTab="RECIEVED";
       }else if(currentTab == '待退款'){
    	   criteria = buildUnrefundedCriteria();
    	   criteria.searchTab="AUDITING";
       }else if(currentTab == '已退款'){
    	   criteria = buildRefundedCriteria();
    	   criteria.searchTab="REFUNDED";
       }
       else{
       }
	   criteria.pageNo = $("input[name=pageNo]").val();
		criteria.pageSize=$("input[name=pageSize]").val();
		criteria.activityId = $("#activityId").val();
	   return criteria;
   }

   function buildInitCriteria(){
	   var criteria ={};
	   criteria.voucherCode = $("#systemLock_voucherCode").val();
		var voucherType = $("#systemLock_status .active a").html();
		if(voucherType=='非占用'){
			criteria.voucherType ="NORMAL";
		}else if(voucherType =='占用中'){
			criteria.voucherType = "SYSTEM";
		}
		criteria.couponId = $("#couponCode").val();

		return criteria;
   }

   function buildRecievedCriteria(){
	   var criteria ={};
	   criteria.voucherCode = $("#recieved_voucherCode").val();
		criteria.memberId = $("#recieved_reciever_mid").val();
		var status = $("#recieved_consumption .active a").html();
		if(status != null && status !=''){
		if(status == '已消费'){
				criteria.statusList=['USED'];
			}else if(status == '未消费'){
				criteria.statusList=['RECIEVED'];
			}
		}

		criteria.orderId =$("#recieved_consumOrderId").val();
		criteria.ordermemberId = $("#recieved_consum_mid").val();
		var voucherType = $("#recieved_systemLock_condition .active a").html();
		if(voucherType == '是'){
			criteria.voucherType = 'SYSTEM';
		}else if(voucherType=='否'){
			criteria.voucherType = 'NORMAL';
		}
		criteria.voucherOrderId = $("#recieved_recieveOrderId").val();
		criteria.payTimeFrom = $("#recieved_payTimeFrom").val();
		criteria.payTimeTo = $("#recieved_payTimeTo").val();
		criteria.couponId = $("#couponCode").val();

		return criteria;
   }

   function buildUnrefundedCriteria(){
	   var criteria ={};
		criteria.voucherCode = $("#unrefunded_voucherCode").val();
		criteria.memberId = $("#unrefunded_recieve_mid").val();
		criteria.voucherOrderId = $("#unrefunded_recieve_orderId").val();
		criteria.payTimeFrom = $("#unrefunded_payTime_from").val();
		criteria.payTimeTo =$("#unrefunded_payTime_to").val();
		criteria.prefundTimeFrom=$("#unrefunded_applyRefundTime_from").val();
		criteria.prefundTimeTo = $("#unrefunded_applyRefundTime_to").val();
		criteria.couponId = $("#couponCode").val();
		return criteria;
   }

   function buildRefundedCriteria(){
	   var criteria ={};
		criteria.voucherCode = $("#refunded_voucherCode").val();
		criteria.memberId = $("#refunded_recieve_mid").val();
		criteria.voucherOrderId = $("#refunded_recieve_orderId").val();
		criteria.payTimeFrom =$("#refunded_payTime_from").val();
		criteria.payTimeTo = $("#refunded_payTime_to").val();
		criteria.refundTimeFrom = $("#refunded_refundTime_from").val();
		criteria.refundTimeTo = $("#refunded_refundTime_to").val();
		criteria.couponId = $("#couponCode").val();
		return criteria;
   }


$(function(){
	   $("#confirmUpdateVoucherTypeBtn").click(function(){
      	updateVoucherType();
      })
})



