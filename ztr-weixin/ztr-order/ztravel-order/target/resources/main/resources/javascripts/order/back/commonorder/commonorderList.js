var orderNoReg =    /^[0-9a-zA-Z]{0,100}$/;
var orderNoErrorHint = "订单号应为0-100位数字或字母";

var orderIdReg =    /^[0-9a-zA-Z\-]{0,100}$/;
var orderIdErrorHint = "订单号应为0-100位数字或字母";

var memberIdReg =/^[0-9a-zA-Z]{0,100}$/;
var memberIdErrorHint = "会员ID应为0-100位数字或字母";


var passlock=0;
var unpasslock=0;
var passconfirm={};
var unpassconfirm={};

$(function(){
	submitFunc();

	$("#orderNo").blur(function(){
		var orderNo = trim(this);
		if( !isOrderNoValid(orderNo) ){
			$(this).siblings(".verifyStyle").find(".verifyFonts").text(orderNoErrorHint);
			showErrorHint(this);
		}else{
			hideErrorHint(this);
		}
	});



	$("#orderId").blur(function(){
		var orderId = trim(this);
		if( !isOrderIdValid(orderId) ){
			$(this).siblings(".verifyStyle").find(".verifyFonts").text(orderIdErrorHint);
			showErrorHint(this);
		}else{
			hideErrorHint(this);
		}
	});



	$("#memberId").blur(function(){
		var memberId = trim(this);
		if( !isMemberIdValid(memberId) ){
			$(this).siblings(".verifyStyle").find(".verifyFonts").text(memberIdErrorHint);
			showErrorHint(this);
		}else{
			hideErrorHint(this);
		}
	});

	$("#searchBtn").click(function(){
	    $("input[name='pageNo']").val("1");
	    $("input[name='pageSize']").val("10");
    	submitFunc();
    })


	  $(".table-tab li").click(function(){
	  $(".table-tab li").removeClass('current');
	  $(this).addClass("current");
	  if ($(this).hasClass("fill")) {
		    $(".commonTab colgroup").html("");
		    $(".commonTab thead").html("");
			var fillcolhtml=$(".fill-money-section .fill colgroup").html();
            $(".commonTab colgroup").append(fillcolhtml);
            var fillcolname=$(".fill-money-section .fill thead").html();
            $(".commonTab thead").append(fillcolname);
	      $("ul.dropdown-menu").find("a[name='UNPAY']").css('display','block');
	      $("ul.dropdown-menu").find("a[name='PAYED']").css('display','block');
	      $("ul.dropdown-menu").find("a[name='CANCELED']").css('display','block');
	      $("ul.dropdown-menu").find("a[name='REFUNDED']").css('display','none');
	      $("ul.dropdown-menu").find("a[name='REFUNDING']").css('display','none');
	      $("ul.dropdown-menu").find("a[name='UNPASS']").css('display','none');
	      $("ul.dropdown-menu").find("a[name='AUDIT']").css('display','none');
	      $("#orderNo").val("");
	      $("#orderId").val("");
	      $("#createDateFrom").val("");
	      $("#createDateTo").val("");
	      $("#memberId").val("");
	      $("div.dropdown").find("ul li.active").removeAttr("class");
	      $("div.dropdown").find("ul li").eq(0).addClass("active");
	      $(".activeFonts").text("全部");
	      $("input[name='pageNo']").val("1");
	      $("input[name='pageSize']").val("10");
	      submitFunc();
	  } else if ($(this).hasClass("back")) {
		    $(".commonTab colgroup").html("");
		    $(".commonTab thead").html("");
			var backcolhtml=$(".back-money-section .back colgroup").html();
            $(".commonTab colgroup").append(backcolhtml);
            var backcolname=$(".back-money-section .back thead").html();
            $(".commonTab thead").append(backcolname);

	      $("ul.dropdown-menu").find("a[name='UNPAY']").css('display','none');
	      $("ul.dropdown-menu").find("a[name='PAYED']").css('display','none');
	      $("ul.dropdown-menu").find("a[name='CANCELED']").css('display','block');
	      $("ul.dropdown-menu").find("a[name='REFUNDED']").css('display','block');
	      $("ul.dropdown-menu").find("a[name='REFUNDING']").css('display','block');
	      $("ul.dropdown-menu").find("a[name='UNPASS']").css('display','block');
	      $("ul.dropdown-menu").find("a[name='AUDIT']").css('display','block');
	      $("#orderNo").val("");
	      $("#orderId").val("");
	      $("#createDateFrom").val("");
	      $("#createDateTo").val("");
	      $("#memberId").val("");
	      $("div.dropdown").find("ul li.active").removeAttr("class");
	      $("div.dropdown").find("ul li").eq(0).addClass("active");
	      $("input[name='pageNo']").val("1");
	      $("input[name='pageSize']").val("10");
	      $(".activeFonts").text("全部");
	      submitFunc();
	      }
	  });




	$("#passWindow").delegate(".confirm","click",function(){
		if(passlock==0){
			passlock=1;
			 var commonorderchange={};
			commonorderchange.orderId=passconfirm.parents("tr").find(".orderId").html();
			commonorderchange.status="REFUNDING";
			  $.ajax({
					type : "POST",
					url : basepath + "/commonorder/opconfirm/changestatus",
					data : JSON.stringify(commonorderchange),
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					success : function(data) {
					if(data.res_code!="OrderCHANGED"&&data.res_code!="ChangeStatusFail"){
						passconfirm.parents("td").siblings(".status").html(data.res_code);
						passconfirm.parents("td").siblings(".statechangehistory").html(data.res_msg);
						passconfirm=passconfirm.parents("td");
						passconfirm.html("-- --");
						$("#passWindow").modal("hide");

					}else if(data.res_code=="OrderCHANGED"){
						$("#passWindow").modal("hide");
						alert("订单在操作中已改变，自动刷新后再操作");
						window.location.href=window.location.href;
					}else{
						$("#passWindow").modal("hide");
						alert("退款状态更改失败");
					}
					passlock=0;
					}
				});
		}
	});

	$("#ac-notPassWindow").delegate("#unpassconfirm","click",function(){
			  if(unpasslock==0){
				  unpasslock=1;
				 var commonorderchange={};
				commonorderchange.orderId=unpassconfirm.parents("tr").find(".orderId").html();
				commonorderchange.status="AUDIT_UNPASS";
			  var remark=$(".notPassTab").find("textarea").val().trim();
			  commonorderchange.remark=remark;
			  $.ajax({
					type : "POST",
					url : basepath + "/commonorder/opconfirm/changestatus",
					data : JSON.stringify(commonorderchange),
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					success : function(data) {
					if(data.res_code=="审核未通过"){
						$("#ac-notPassWindow").modal("hide");
						unpassconfirm.parents("td").siblings(".status").html("审核不通过");
						unpassconfirm.parents("td").siblings(".statechangehistory").html(data.res_msg);
						unpassconfirm=unpassconfirm.parents("td");
						unpassconfirm.html("-- --");
						unpassconfirm.siblings(".remark").text(remark);
					}else if(data.res_code=="OrderCHANGED"){
						$("#ac-notPassWindow").modal("hide");
						alert("订单在操作中已改变，自动刷新后再操作");
						window.location.href=window.location.href;
					}else{
						$("#ac-notPassWindow").modal("hide");
						alert("退款状态改变失败");
					}
					unpasslock=0;
					}
				});
			  }

	})

});





function submitFunc(){
	var criteria = $("form").serializeObject();
	criteria.status = $("div.dropdown").find("ul li.active a").text();
	var tab=$(".table-tab li.current").html();
	if(tab=="退款单"){
		criteria.orderType='OP_CONFIRM_REFUND';

	}else{
		criteria.orderType='OP_CONFIRM_REPAIR';

	}

	if( isSearchParamsValid(criteria) ){
		$.ajax({
			type : "POST",
			url : basepath + "/commonorder/opconfirm/search",
			data : JSON.stringify(criteria),
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			dataType : "html",
			success : function(result) {
				var resultArray = result.split("<-split->");
				var data = resultArray[0];
				var pagination = resultArray[1];
				$(".commonTab tbody").html(data);
				$("#pagination").html(pagination);
				if(tab=="退款单"){
					initNoPass();
					initPass();
				}else{

				}
			},
		});
	}

}


//未通过弹窗
function initNoPass(){
	$(".notPassBtn.able").click(function(){
			 unpassconfirm=$(this);
			  $("#ac-notPassWindow").modal("show");
			  $(".notPassTab").find("textarea").val("");
	});
}


//点击通过
function initPass(){
	$(".PassBtn.able").click(function(){
		 passconfirm=$(this);
		$("#passWindow").modal("show");
	})
}




function isSearchParamsValid(searchParams){
	if( isOrderNoValid(searchParams.orderNo)&&isOrderIdValid(searchParams.orderId) )
		return true;
	else
		return false;
}


function trim(selector){
	return $(selector).val($(selector).val().replace(/\ +/g,"")).val() ;
}

function showErrorHint(inputer){
	$(inputer).addClass("verifyBox");
	$(inputer).siblings(".verifyStyle").show();
}

function hideErrorHint(inputer){
	$(inputer).removeClass("verifyBox");
	$(inputer).siblings(".verifyStyle").hide();
}


$.fn.serializeObject = function(){
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};



function isOrderNoValid(orderNo){
	if( orderNo != "" && !orderNoReg.test(orderNo) )
		return false;
	else
		return true;
}

function isOrderIdValid(orderId){
	if( orderId != "" && !orderIdReg.test(orderId) )
		return false;
	else
		return true;
}


function isMemberIdValid(memberId){
	if( memberId != "" && !memberIdReg.test(memberId) )
		return false;
	else
		return true;
}








