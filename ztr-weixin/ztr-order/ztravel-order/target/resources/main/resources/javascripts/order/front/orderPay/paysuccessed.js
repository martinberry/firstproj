  $(function () {

        setWrapperHeight();

        $(".main-nav .right-nav-icon").click(function () {
        var $ele = $(this);
        $(".box-relative-location").css({
            "top": $ele.position().top + "px"
        }).show();
    });


        //返回首页
        $(".paybox-bottom").delegate(".payBtn-color01","click",function(){
        	window.location.href=memberServer+"/home";
        })

        //进入订单详情页面
          $(".paybox-bottom").delegate(".payBtn-color02","click",function(){
        	 var orderId = $("#realOrderId").val();
        	 var nature = $("#nature").val();
        	 if(typeof(nature) != undefined && nature != ''){
	    		 if(nature == 'VISA' || nature == 'visa'){
	     			window.location.href = memberServer + "/visaorder/front/detail/" + orderId ;
	    		 }else if(nature == 'UNVISA' || nature== 'TICKET' || nature== 'LOCAL' || nature== 'TRAFFIC' || nature== 'WIFI' || nature== 'HOTELUP' || nature== 'CHARTER' || nature== 'INTELTAXI'){
         			window.location.href = memberServer + "/localorder/front/detail/" + orderId ;
	         	 }else{
         			window.location.href = memberServer + "/order/front/detail/" + orderId ;
         		 }
        	 }else{
        		 window.location.href=memberServer+"/order/front/detail/"+orderId;
        	 }
        })

       setDetailBtnSecondsDisabled();//调用
});

function setWrapperHeight() {
    if ($(".main-wrapper").length !== 0) {
        $(".main-wrapper").eq(0).css({
            "min-height": (document.documentElement.clientHeight - 142) + "px"
        });
    }
}

var wait = 2;
function setDetailBtnSecondsDisabled() {

    if (wait == 0) {
    	$("#enterOrderDetail").attr("disabled",false);
    } else {
    	$("#enterOrderDetail").attr("disabled", true);
        wait--;
        setTimeout(function () {
        	setDetailBtnSecondsDisabled()
        },
1000)
    }
}


