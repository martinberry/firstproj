$(function(){
	//初始化确认兑换按钮
	$("#confirmToConvert").click(function(){
		var memberId =$("#memberId").attr("value"); //会员ID;
		$.ajax({
				type : "get",
				url : memberServer + "/electronicWallet/accountBalance/membersIsActive",
				data : {
					destMembeId: memberId
				},
				dataType : "json",
				success : function(data) {
					if(data.isActive=='no'){
						window.location.href= memberServer+"/electronicWallet/accountBalance/index";
					}else if(data.isActive=='yes'){
						var phone = $("#convert_phone").val();
						if(!isPhone(phone)){
							$(".phoneTips").fadeIn("normal", function(){
								setTimeout(function(){
									$(".phoneTips").fadeOut("fast");
								}, 2000);
							});
							return;
						}else{
							dhSubmit();
						}
					}else{
						return;
					}
				}
			});
	});

	$("#convert_phone").blur(function(){
		 var phone = $(this).val();
		 if(!isPhone(phone)){
			 $(".phoneTips").fadeIn("normal", function(){
					setTimeout(function(){
						$(".phoneTips").fadeOut("fast");
					}, 2000);
			 });
			 return false;
			 }
	});

	submitFunc();

	$("#cancelConvert").click(function(){
		window.location.href= memberServer + "/electronicWallet/accountBalance/index";
	});
	$("#convert").click(function(){
			selectmodal();
	});

/////////////////////////////////////////
		$(".green-border-tooltip").tooltip({
	            html: true,
	            placement: 'bottom',
	            template: '<div class="tooltip green-border-tooltip" role="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>'
	        })

})

//判断余额大小选择模态框
       function selectmodal(){
			var account=$("#account").attr("value");
			if (account<10){
				$.ajax({
					type : "post",
					url : memberServer + "/electronicWallet/accountBalance/getlogin",
					dataType : "json",
					success : function(data) {
						if(data.login=='out'){
							window.location.href= memberServer+"/home";
						}else if(data.login=='in'){
							 $("#notconvert").modal("show");
					}
						else{}
					}
				});

			}else{
				getAccountMoney();
				 getphonenum();
				  $("#exchangeModal").modal("show");
			}
       	}

//判断兑换者是否挂起，如果挂起回到首页
function active(){
	var memberId =$("#memberId").attr("value"); //会员ID;
$.ajax({
		type : "get",
		url : memberServer + "/electronicWallet/accountBalance/membersIsActive",
		data : {
			destMembeId: memberId
		},
		dataType : "json",
		success : function(data) {
			if(data.isActive=='no'){
				window.location.href= memberServer+"/electronicWallet/accountBalance/index";
			}else if(data.isActive=='yes'){
				var phone = $("#convert_phone").val();
				if(!isPhone(phone)){
					$(".phoneTips").fadeIn("normal", function(){
						setTimeout(function(){
							$(".phoneTips").fadeOut("fast");
						}, 2000);
					});
					return false;
				}else{
					dhSubmit();
				}
			}else{
				return false;
			}
		}
	});

}



//判断兑换者是否退出登陆












//检测电话号码格式是否符合要求
function isPhone(phoneNum){
	var r= /^(?:13\d|14\d|15\d|17\d|18\d)\d{8}$/ ;
	if(r.test(phoneNum)){
	return true;
	}else{
	return false;
	}
	}



//提交兑换数据
function dhSubmit(){

	var dhMoney=	$("div.exchangeBlance").find("select").val();             //兑换金额,问题
    var dhPhonenum= $("#convert_phone").val();         //兑换手机号
	$.ajax({
		type : "POST",
		url : "../accountBalance/dh",
		data :{
                   "dhMoney":dhMoney,
			   "dhPhonenum":dhPhonenum
		},
		success : function(data) {
        if(data.res_code=='SO_MEMBDH_1001')
        $("#exchangeModal").modal("hide");
        $("#convertsuccess").modal("show");
		}
	})

}

function submitFunc(){
	searchFriendsMakeMoney();
}

function searchFriendsMakeMoney(){
	var criteria = {};
	criteria.pageNo = $("input[name=pageNo]").val();
 	criteria.pageSize = $("input[name=pageSize]").val();
	$.ajax({
		type : "POST",
		url : "../accountBalance/search",
		data :JSON.stringify(criteria),
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		dataType : "html",
		success : function(result) {
			var dataArray = result.split("<-split->");
			var tableData = dataArray[0];
			var paginationData = dataArray[1];
			$(".person-reward-table tbody").html(tableData);
			$("#searchField").html(paginationData);
			$(window).trigger("resize");
		},
	})
}

function getAccountMoney(){
	 $.ajax({
		   type: "POST",
		   url: "../accountBalance/getAccountMoney",
		   headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
		   dataType: "json",
		   success: function(resp){
			   if (resp.res_code == 'SO_MEMB_1005') {
				   if(resp.res_msg < 100 && resp.res_msg >=50 ){
					   $("option.onehunderd").attr("style","display:none;");
				   }else if(resp.res_msg < 50 && resp.res_msg >=30){
					   $("option.onehunderd").attr("style","display:none;");
					   $("option.fifty").attr("style","display:none;");
				   }else if(resp.res_msg < 30 && resp.res_msg >=10){
					   $("option.onehunderd").attr("style","display:none;");
					   $("option.fifty").attr("style","display:none;");
					   $("option.thirty").attr("style","display:none;");
				   }else if(resp.res_msg < 10){
					   $("option.onehunderd").attr("style","display:none;");
					   $("option.fifty").attr("style","display:none;");
					   $("option.thirty").attr("style","display:none;");
					   $("option.ten").attr("style","display:none;");
				   }
				   $("#accountLeft").html(resp.res_msg);
				}else if (resp.res_code == 'FO_MEMB_1004') {
					alert(resp.res_msg);
				}
				 }
		});
}


function getphonenum(){
		$.ajax({
		type: "POST",
		url : "../accountBalance/getphonenum",
	   headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
	async:false,
	   dataType: "json",
		success : function(resp) {
        if(resp.res_code=='SO_MEMB_1005')
        $("#convert_phone").val(resp.res_msg);
		}
	});
}
