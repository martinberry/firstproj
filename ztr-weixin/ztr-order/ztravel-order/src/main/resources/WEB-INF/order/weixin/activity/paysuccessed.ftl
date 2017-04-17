<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no, email=no">
    <link rel="stylesheet" type="text/css" href="${host}/mstatic/css/jqm/jquery.mobile-1.4.5.min.css">
    <link rel="stylesheet" type="text/css" href="${host}/mstatic/css/jqm.reset.css">
    <link rel="stylesheet" type="text/css" href="${host}/mstatic/css/common.css">
    <link rel="stylesheet" type="text/css" href="${host}/mstatic/css/myWallet.css">
    <link rel="stylesheet" type="text/css" href="${host}/mstatic/css/myCoupon.css">

    <script type="text/javascript" src="${host}/mstatic/js/base/jquery.min.js"></script>
    <script type="text/javascript" src="${host}/mstatic/js/jqm/jquery.mobile-1.4.5.min.js"></script>
    <!-- <script type="text/javascript" src="${host}/mstatic/js/iscroll/iscroll.js"></script> -->
    <script type="text/javascript" src="${host}/mstatic/js/iscroll/iscroll-probe.js"></script>
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
    <div data-role="page">
        <div class="headerBar">
            <div class="header">
                <span class="title">交易详情</span>
            </div>
        </div>
        <div class="wrapper">
            <div class="successPayment"><img class="payImg" src="${host}/mstatic/images/payoff.png">支付成功</div>

            <div class="couponOrderCon">
                <div class="couponOrderTitle clearfix">
                    <h2>恭喜您已成功购买${num!}张代金券</h2>
                    <span class="viewDetailSpan">查看明细<i class="arrowDown"></i></span>
                </div>
                <div class="detailBox">
                     <div class="orderView">
                        <#if couponSnap.amount?? && couponSnap.amount gt 0 && couponSnap.amount lte 10>
                        <div class="voucherItem voucher-bg-0">
                            <div class="flag"></div>
                            <div class="com-voucher rightBorder-green"></div>
                        <#elseif couponSnap.amount gt 10 && couponSnap.amount lte 20>
                         <div class="voucherItem voucher-bg-1">
                            <div class="flag"></div>
                            <div class="com-voucher rightBorder-blue"></div>
                        <#elseif couponSnap.amount gt 20 && couponSnap.amount lte 50>
                        <div class="voucherItem voucher-bg-2">
                            <div class="flag"></div>
                            <div class="com-voucher rightBorder-pink"></div>
                        <#else>
                        <div class="voucherItem voucher-bg-3">
                            <div class="flag"></div>
                            <div class="com-voucher rightBorder-yellow"></div>
                        </#if>
                            <div class="top">
                                <span class="title">${couponSnap.name!}</span>
                                <span class="star-icon"></span>
                            </div>
                            <div class="horizontal-white-split-line"></div>
                            <div class="middle">
                                <div class="privilege">满${couponSnap.orderLeast/100!}元减</div>
                                <div class="validity">${couponSnap.validDateFrom!} - ${couponSnap.validDateTo!}</div>
                                <div class="applicability">适用范围：<#noescape>${couponSnap.description!}</#noescape></div>
                                <span class="price"><span class="price-number">${couponSnap.amount/100!}</span><em>元</em></span>
                            </div>
                        </div>
                    </div>
                    <div class="totalPriceBlock clearfix" style="border-top:none;">
                        <div class="priceDiv">总计<span><i>¥</i>${payAmount!}</span></div>
                        <div class="amountDiv">数量 <span>×${num!}</span></div>
                    </div>
                </div>
            </div>
            <div class="viewMyWalletBlock clearfix" href="javascript:myWallet();" >
                <h4>前往我的钱包查看</h4><span></span>
            </div>
            <div class="operBlock">
                <a class="payoffLink" href="javascript:couponBuy();" data-role="none" data-transition="slidefade">继续购买代金券</a>
                <a class="payoffLink01 ui-link" href="javascript:list();">看看旅游线路</a>
            </div>

        </div>


    </div>

<script type="text/javascript">
    var wxServer = '${wxServer}' ;
    $(function(){
        $(".viewDetailSpan").click(function(){
            // $(".detailBox").slideToggle();
            $(".detailBox").toggleClass("hide");
            // $(".couponOrderTitle").toggleClass('hideBorder');
        });
        $('.viewMyWalletBlock').click(function () {
            window.location.href = wxServer + "/accountBalance/index";
        });
        FastClick.attach(document.body);
    });

    function couponBuy(){
        window.location.href = wxServer + "/activity/coupon/weixin/couponList";
    }

    function list(){
        window.location.href = wxServer + "/weixin/product/list" ;
    }
</script>
</body>
</html>