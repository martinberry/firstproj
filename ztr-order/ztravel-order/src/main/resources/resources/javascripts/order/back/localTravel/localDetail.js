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
       var status=$("#localStatus").attr("value");
       
       if(status=="PAYED"){
    	   $(".ac-OPSure").removeAttr("style");
       }
       if(status=="CONFIRM"){
    	   $(".cancelOrder").attr("style","display:none");
       }
       if(status=="CANCELED"){
    	   $(".cancelOrder").attr("style","display:none");
       }
       if(status=="REFUNDING"){
    	   $(".cancelOrder").attr("style","display:none");
       }  
       
       //取消订单
       $(".ac-cancelOrder").click(function(){
    	   $("#cancelOrderDlg").modal("show");  	   
       });
       
       $(".main-container.changeMainContent").delegate("#cancelOrderBtn","click",function(){
    	   var orderId=$("#orderId").val();
    	   $.ajax({    		   
         		url : basepath + "/localorder/travel/cancelOrder",           	
         		data : {orderId:orderId},
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
       $(".ac-OPSure").click(function(){
    	      $("#opConfirmDlg").modal("show");
       });
       
       $(".main-container.changeMainContent").delegate("#opConfirmBtn","click",function(){
           var orderId=$("#orderId").val();
    	   $.ajax({    		   
         		url : basepath + "/localorder/travel/opConfirm",           	
         		data : {orderId:orderId},
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
 	
    
     
       //保存
       $("#saveBtn").click(function(){
    	   var orderId=$("#orderId").val();
    	   var remark=$("#remarkInputer").val();
    	   $.ajax({
    		   url:basepath + "/localorder/travel/saveLog",
    		   data:{orderId:orderId,remark:remark},
    		   dataType:"json",
    		   success:function(data){
    			   if(data.res_code=="SO_ORDR_1011"){
    				   window.location.href=window.location.href;
    			   }else{
    				   alert("保存操作失败，请刷新页面重试");
    			   }
    			   
    		   }	   
    	   })
       });
       

       
       
   	


})
    



