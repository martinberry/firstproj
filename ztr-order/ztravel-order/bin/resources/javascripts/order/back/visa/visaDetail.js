var contactorReg =    /^[\d\D]{1,100}$/;
var phoneReg= /^(?:13\d|14\d|15\d|17\d|18\d)\d{8}$/ ;
var emailReg=/^((\w-*\.*)+@(\w-?)+(\.\w{2,})+){0,50}$/;
var addressReg=/^[\d\D]{1,500}$/;
var messageReg= /^[\d\D]{1,500}$/;

$(function(){
        //操作日志伸缩
        $(".titleRight .unfold").click(function(){
            $(this).hide();
            $(this).siblings(".packUp").show();
            $(".flexibleContent").show();
        });
        $(".titleRight .packUp").click(function(){
            $(this).hide();
            $(this).siblings(".unfold").show();
            $(".flexibleContent").hide();
        });
        
      //初始状态操作按钮显示
       var status=$("#visaStatus").attr("value");

       if(status=="PAYED"){
    	   $(".opSure").removeAttr("style");
       }
       if(status=="CONFIRM"){
    	   $(".makedbutton").removeAttr("style");
    	   $("#cancelOrderOperaBlock").attr("style","display:none;");
       }
       if(status=="MAKED"){
    	   $(".materialsendbutton").removeAttr("style");
    	   $("#cancelOrderOperaBlock").attr("style","display:none;");
       }
       if(status=="MATERIALSEND"){
    	   $("#cancelOrderOperaBlock").attr("style","display:none;");          
       }
       
       //取消订单
       $("#cancelOrderOperaBlock").click(function(){
    	   $("#cancelOrderDlg").modal("show");  	   
       });
       
       $(".main-container.changeMainContent").delegate("#cancelOrderBtn","click",function(){
    	   var orderId=$("#orderId").val();
    	   $.ajax({    		   
         		url : basepath + "/visa/cancelOrder",           	
         		data : {visaOrderId:orderId},
         		dataType : "json",
         		success : function(result){
             			if(result.res_code=='SO_ORDR_1009'){
             				$("#cancelOrderDlg").modal("hide");  
             				window.location.href=window.location.href;
             			}else{
             				$("#cancelOrderDlg").modal("hide");  
             				alert("取消订单操作失败，请刷新页面重试");
             			}
             		}
    	   }); 
       })
   	 
       
       //op确认
       $(".opSure").click(function(){
    	      $("#opConfirmDlg").modal("show");
       });
       
       $(".main-container.changeMainContent").delegate("#opConfirmBtn","click",function(){
           var orderId=$("#orderId").val();
    	   $.ajax({    		   
         		url : basepath + "/visa/opConfirm",           	
         		data : {visaOrderId:orderId},
         		dataType : "json",
         		success : function(result){
             			if(result.res_code=='SO_ORDR_1005'){
             				$("#opConfirmDlg").modal("hide");
             				window.location.href=window.location.href;
             			}else{
             				$("#opConfirmDlg").modal("hide");
             				alert("op确认操作失败，请刷新页面重试");
             			}
             		}
    	   });  
       })
 	
       
      //制作完成
       $(".makedbutton").click(function(){
    	  	   $("#makedOrderDlg").modal("show");
       });
       
       
       $(".main-container.changeMainContent").delegate("#makedOrderBtn","click",function(){
    	   var orderId=$("#orderId").val();
    	   $.ajax({    		   
         		url : basepath + "/visa/maked",           	
         		data : {visaOrderId:orderId},
         		dataType : "json",
         		success : function(result){
             			if(result.res_code=='MAKEDSUCCESS'){
             			   $("#makedOrderDlg").modal("hide");
             				window.location.href=window.location.href;
             			}else{
             			   $("#makedOrderDlg").modal("hide");
             				alert("制作完成操作失败，请刷新页面重试");
             			}
             		}
    	   }); 
    	   
       })
      
     //材料送回弹窗
       $(".materialsendbutton").click(function(){
    	   var orderId=$("#orderId").val();
    	   $.ajax({    		   
         		url : basepath + "/visa/materialshow",           	
         		data : {visaOrderId:orderId},
         		dataType : "json",
         		success : function(result){
             			if(result.res_code=='SUCCESS'){
             				$("#materialSendDlg").modal("show");
             				$("#contactorname").val(result.res_msg.contactor);
             				$("#contactorphone").val(result.res_msg.phone);
             				$("#contactoremail").val(result.res_msg.email);
             				$("#contactoraddress").val(result.res_msg.address);
             				$(".message").html("您的签证办理已经完毕，我们已将相关文件递出。快递公司****，运单号***。如有任何问题，请与微信客服真小行（微信号zhenlx2）联系。");
             			}else{
             				alert("材料送回操作失败，请刷新页面重试");
             			}
             		}
    	   });   	   
       });
      
     //材料送回信息确认
       $(".main-container.changeMainContent").delegate("#materialsendBtn","click",function(){  	
    	   var criteria={};
    	   criteria.orderId=$("#orderId").val().trim();
    	   criteria.contactor=$("#contactorname").val().trim();
    	   criteria.phone=$("#contactorphone").val().trim();
    	   criteria.email=$("#contactoremail").val().trim();
    	   criteria.address=$("#contactoraddress").val().trim();
    	   criteria.message=$(".message").val().trim();
    	   if(!isParamsValid(criteria)){
    		   if(!isContactorValid(criteria.contactor)){
    				$(".nameTips").fadeIn("normal", function(){
						setTimeout(function(){
							$(".nameTips").fadeOut("fast");
						}, 2000);
					});
    		   }else if(!isPhoneValid(criteria.phone)){
    			   $(".phoneTips").fadeIn("normal", function(){
						setTimeout(function(){
							$(".phoneTips").fadeOut("fast");
						}, 2000);
					});
    			   
    		   }else if(!isEmailValid(criteria.email)){
    			   $(".emailTips").fadeIn("normal", function(){
						setTimeout(function(){
							$(".emailTips").fadeOut("fast");
						}, 2000);
					});
    			   
    		   }else if(!isAddressValid(criteria.address)){
    			   $(".addressTips").fadeIn("normal", function(){
						setTimeout(function(){
							$(".addressTips").fadeOut("fast");
						}, 2000);
					});
    			   
    		   }else{
    			   $(".messageTips").fadeIn("normal", function(){
						setTimeout(function(){
							$(".messageTips").fadeOut("fast");
						}, 2000);
					});
    			   
    		   }
    	   }   	   
    	   if(isParamsValid(criteria)){
    		   $.ajax({
       		    type : "POST",
       			url : basepath + "/visa/materialsend",
       			data : JSON.stringify(criteria),
       			headers : {
       				'Accept' : 'application/json',
       				'Content-Type' : 'application/json'
       			},
       			success:function(data){
       				if(data.res_code=="MaterialSendSUCCESS"){
       					$("#materialSendDlg").modal("hide");
       					window.location.href=window.location.href;
       				}
       				else{
       					$("#materialSendDlg").modal("hide");
       					alert("材料送回失败，请刷新页面重试");
       				}
       			}
       	   })
    	   }	   
    	      	
       });
       
       //保存
       $("#saveBtn").click(function(){
    	   var orderId=$("#orderId").val();
    	   var remark=$("#remarkInputer").val();
    	   $.ajax({
    		   url:basepath + "/visa/saveLog",
    		   data:{visaOrderId:orderId,remark:remark},
    		   dataType: "json",
    		   success:function(data){
    			   if(data.res_code=="SO_ORDR_1011"){
    				   window.location.href=window.location.href;
    			   }else{
    				   alert("保存操作失败，请刷新页面重试");
    			   }
    			   
    		   }	   
    	   })
       });
       
       
       

    	 

   $(".main-container.changeMainContent").delegate("#contactorname","blur",function(){
   		var contactorname = $("#contactorname").val().trim();
   		if( !isContactorValid(contactorname) ){
   			$(".nameTips").fadeIn("normal", function(){
   				setTimeout(function(){
   					$(".nameTips").fadeOut("fast");
   				}, 2000);
   			});
   			return false;
   		}  
	});
  
   $(".main-container.changeMainContent").delegate("#contactorphone","blur",function(){
		var contactorphone = $("#contactorphone").val().trim();
		if( !isPhoneValid(contactorphone) ){
			$(".phoneTips").fadeIn("normal", function(){
				setTimeout(function(){
					$(".phoneTips").fadeOut("fast");
				}, 2000);
			});
			return false;
		}  
	});
       
 
   $(".main-container.changeMainContent").delegate("#contactoremail","blur",function(){
		var contactoremail = $("#contactoremail").val().trim();
		if( !isEmailValid(contactoremail) ){
			$(".emailTips").fadeIn("normal", function(){
				setTimeout(function(){
					$(".emailTips").fadeOut("fast");
				}, 2000);
			});
			return false;
		}  	
	});
       

   $(".main-container.changeMainContent").delegate("#contactoraddress","blur",function(){
		var contactoraddress = $("#contactoraddress").val().trim();
		if( !isAddressValid(contactoraddress) ){
			$(".addressTips").fadeIn("normal", function(){
				setTimeout(function(){
					$(".addressTips").fadeOut("fast");
				}, 2000);
			});
			return false;
		}  
	});
       
  
   $(".main-container.changeMainContent").delegate(".message","blur",function(){
		var message = $(".message").val().trim();
		if( !isMessageValid(message) ){
			$(".messageTips").fadeIn("normal", function(){
				setTimeout(function(){
					$(".messageTips").fadeOut("fast");
				}, 2000);
			});
			return false;
		}
	});
       
       
   	


})
    
function isParamsValid(searchParams){
	if(isContactorValid(searchParams.contactor)&&isPhoneValid(searchParams.phone)&&isEmailValid(searchParams.email)&&isAddressValid(searchParams.address)&&isMessageValid(searchParams.message))
		return true;
	else
		return false;
}

function isContactorValid(contactor){
	if(!contactorReg.test(contactor) )
		return false;
	else
		return true;
}

function isPhoneValid(phone){
	if(!phoneReg.test(phone))
		return false;
	else
		return true;
}

function isEmailValid(email){
	if(!emailReg.test(email))
		return false;
	else
		return true;
}
function isAddressValid(address){
	if(!addressReg.test(address))
		return false;
	else
		return true;
}

function isMessageValid(message){
	if(!messageReg.test(message))
		return false;
	else
		return true;
}

