<#import "/common/front/header/navHeader.ftl" as html/>
<#import "/order/front/orderDetail/orderStatusFlow.ftl" as orderStatus/>
<#import "/order/front/orderDetail/productInfo.ftl" as productInfo/>
<#import "/order/front/orderDetail/contactorInfo.ftl" as contactorInfo/>
<#import "/order/front/orderDetail/passengerInfo.ftl" as passengerInfo/>
<#import "/order/front/orderDetail/flightInfo.ftl" as flightInfo/>
<#import "/order/front/orderDetail/hotelsInfo.ftl" as hotelsInfo/>
<#import "/order/front/orderDetail/additionalInfos.ftl" as additionalInfos/>
<#import "/order/front/orderDetail/priceInfo.ftl" as priceInfo/>
<#import "/order/front/orderDetail/additionalProducts.ftl" as additionalProducts/>

<@html.navHeader
title="真旅行-订单详情"
currentMenu="我的订单"
remoteJsFiles=["js/vendor/bootstrap-datepicker.min.js","js/vendor/bootstrap-datepicker.zh-CN.min.js"]
remoteCssFiles=["css/client/orderInfo.css","css/bootstrap-datepicker.min.css"]
localJsFiles=["order/front/orderDetail/detail.js","common/typeahead.js","order/front/orderDetail/validation.js"]
localCssFiles=[]>

    <div class="main-wrapper" id="main-wrapper">
    <input type="hidden" name="orderId" value="${(orderDetail.order.orderId)!}">
    <@orderStatus.orderStatus />

        <section class="onLinePayContent">
            <div class="top-border"></div>
            <!-- <div class="commonOrderModel">
                <i class="left-semicircle"></i>
                <i class="right-semicircle"></i>
                <div class="titleModel noBorderBottom">订单详情</div>
            </div> -->

            <@productInfo.productInfo />
            <@contactorInfo.contactorInfo />
            <@passengerInfo.passengerInfo />
            <@flightInfo.flightInfo />
            <@hotelsInfo.hotelsInfo />
            <@additionalProducts.additionalProducts/>
            <@additionalInfos.additionalInfos />
            <@priceInfo.priceInfo />

        </section>
    </div>

    <script type="text/html" id="whiteOrder_template">
    <div class="statusHint">请仔细填写游客信息</div>
</script>
<script type="text/html" id="payOrder_template">
    <div class="statusHint">打开荷包，来一场说走就走的旅行吧</div>
</script>
<script type="text/html" id="paySuccess_template">
    <div class="statusHint">成功啦！请准备开展自由之旅吧</div>
</script>
<script type="text/html" id="box_template">
    <div class="statusHint">我们将于出发前两周给您邮寄一本行程手册和一份旅行福袋</div>
</script>
<script type="text/html" id="travelInform_template">
    <div class="statusHint">真旅管家将于您出发前48小时发送出行提醒</div>
</script>
<script type="text/html" id="steward_template">
    <div class="statusHint">我们为您提供7*24小时的旅行管家服务，旅行中无论何时何地您都可以联系到旅行管家获得协助</div>
</script>
<script type="text/html" id="evaluate_template">
    <div class="statusHint">您的评价是我们不断改进的动力，请将您的旅途故事分享给更多人，如果可以成功推荐新的游客，我们将有现金券大礼相送</div>
</script>

<script>
    $(function(){
        //导航状态浮层
        $(".orderStatus .whiteOrder").attr("title", $("#whiteOrder_template").html());
        $(".orderStatus .payOrder").attr("title", $("#payOrder_template").html());
        $(".orderStatus .paySuccess").attr("title", $("#paySuccess_template").html());
        $(".orderStatus .box").attr("title", $("#box_template").html());
        $(".orderStatus .travelInform").attr("title", $("#travelInform_template").html());
        $(".orderStatus .steward").attr("title", $("#steward_template").html());
        $(".orderStatus .evaluate").attr("title", $("#evaluate_template").html());
        $(".green-border-tooltip").tooltip({
            html: true,
            placement: 'bottom',
            template: '<div class="tooltip green-border-tooltip" role="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>'
        });

    });
</script>
</@html.navHeader>