
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
    <title>支付失败</title>

    <script type="text/javascript">
        var root = document.getElementsByTagName("html")[0],
                w = window.innerWidth >= 640 ? 640 : window.innerWidth;

        root.style.fontSize = (w / 320) * 20 + "px";
        root.style.minHeight = window.innerHeight + "px";
    </script>
</head>
<body>
<div class="payoff-content" data-role="page">
    <div class="payFail-detail">
        <img class="payFail-img" src="${host}/mstatic/images/pay-fail.png">
        <p class="payFail-txt">支付失败</p>
        <p class="payFail-tip">您可以继续支付或者</p>
        <p class="payFail-tip">拨打客服电话<span class="tip-num">400-620-6266 转6</span></p>
    </div>
    <div>
        <a class="payoff-link" href="javascript:continuePay();" data-role="none" data-transition="slidefade">继续支付</a>
        <a class="payoff-link01" href="javascript:list();">返回产品列表</a>
    </div>
</div>

<script>
	var wxServer = '${wxServer}' ;
	var orderId = "${orderId!''}" ;
    $(function () {
        FastClick.attach(document.body);
    });
    
    function continuePay(){
    	if(wxServer.charAt(wxServer.length - 1) == '/'){
			window.location.href = wxServer + "weixin/orderPay/selectPayType?orderId=" + orderId;
		}else{
			window.location.href = wxServer + "/weixin/orderPay/selectPayType?orderId=" + orderId;
		}
    }
    
    function list(){
    	window.location.href = wxServer + "/weixin/product/list" ;
    }
</script>
</body>
</html>