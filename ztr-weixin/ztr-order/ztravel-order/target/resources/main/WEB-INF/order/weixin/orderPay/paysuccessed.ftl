
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no, email=no">
    <link rel="stylesheet" type="text/css" href="${host}/mstatic/css/jqm/jquery.mobile-1.4.5.min.css">
    <!-- <link rel="stylesheet" type="text/css" href="${host}/mstatic/css/jqm/jquery.mobile.theme-1.4.5.css"> -->
    <link rel="stylesheet" type="text/css" href="${host}/mstatic/css/jqm.reset.css">
    <link rel="stylesheet" type="text/css" href="${host}/mstatic/css/common.css">
    <link rel="stylesheet" type="text/css" href="${host}/mstatic/css/payment.css">
    <script type="text/javascript" src="${host}/mstatic/js/base/jquery.min.js"></script>
    <script type="text/javascript" src="${host}/mstatic/js/jqm/jquery.mobile-1.4.5.min.js"></script>
    <script type="text/javascript" src="${host}/mstatic/js/iscroll/iscroll.js"></script>
    <script type="text/javascript" src="${host}/mstatic/js/base/fastclick.js"></script>
    <title>支付成功</title>

    <script type="text/javascript">
        var root = document.getElementsByTagName("html")[0],
                w = window.innerWidth >= 640 ? 640 : window.innerWidth;

        root.style.fontSize = (w / 320) * 20 + "px";
        root.style.minHeight = window.innerHeight + "px";
    </script>
</head>
<body>
<div class="payoff-content" data-role="page">
    <div class="payoff-detail">
        <img class="payoff-img" src="${host}/mstatic/images/payoff.png">
        <p class="payoff-tip"><#if orderStatus ??> <#if orderStatus == 'PAYED'>支付成功</#if><#if orderStatus == 'UNPAY'>支付成功 </#if><#if orderStatus == 'CANCELED'>该订单已取消，请联系客服退款!</#if></#if></p>
        <span class="payoff-pri">￥${payAmount!}</span>
    </div>
    <div class="payoff-tab">
        <div class="payoff-row clearfix">
            <div class="payoff-left">支付方式</div>
            <div class="payoff-right">${payType!}</div>
        </div>
        <div class="payoff-row clearfix">
            <div class="payoff-left">订 单 号</div>
            <div class="payoff-right">${orderId!}</div>
        </div>
    </div>
    <div>
    	<input type="hidden" id="nature" value="${(productNature)!''}">
        <a class="payoff-link" href="javascript:detail(${realOrderId!});">查看订单详情</a>
        <a class="payoff-link01" href="javascript:list();">返回产品列表</a>
    </div>
</div>

<script>
	var wxServer = '${wxServer}' ;
    $(function () {
        FastClick.attach(document.body);

    });

    function detail(orderId){
    	var nature = $("#nature").val();
    	if(typeof(nature) != undefined && nature != ''){
    		if(nature == 'VISA' || nature == 'visa'){
    			window.location.href = wxServer + "/visaorder/weixin/detail/" + orderId ;
    		}else if(nature == 'UNVISA' || nature== 'TICKET' || nature== 'LOCAL' || nature== 'TRAFFIC' || nature== 'WIFI' || nature== 'HOTELUP' || nature== 'CHARTER' || nature== 'INTELTAXI'){
    			window.location.href = wxServer + "/localorder/weixin/detail/" + orderId ;
    		}else{
    			window.location.href = wxServer + "/order/weixin/detail/" + orderId ;
    		}
    	}else{
	    	window.location.href = wxServer + "/order/weixin/detail/" + orderId ;
    	}
    }

    function list(){
    	window.location.href = wxServer + "/weixin/product/list" ;
    }
</script>
</body>
</html>