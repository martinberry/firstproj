<#import "/common/front/htmlIndex.ftl" as html/>
<@html.htmlIndex
	title="真旅行-订单支付结果"
  	jsFiles=["order/front/orderPay/paysuccessed.js"]
>
<div class="main-wrapper" id="main-wrapper">
        <section class="paySuccess-box">
            <span class="pin"></span>

            <div class="top-border"></div>
            <div class="payBox-panel">
                <div class="paybox-up">
                    <div class="paybox-up-cnt">
                        <div class="paybox-up-img"><span class="payImg"></span></div>
                        <div class="paybox-up-tip"><#if orderPayResult.orderStatus ??> <#if orderPayResult.orderStatus == 'PAYED' || orderPayResult.orderStatus == 'UNPAY'>付款成功！</#if><#if orderPayResult.orderStatus == 'CANCELED'>该订单已取消，请联系客服退款!</#if></#if></div>
                        <div class="paybox-up-txt">
                            <span class="paybox-up-border">本次付款金额：<span class="span-color01"><#if orderPayResult.payAmount ?? >￥${orderPayResult.payAmount!}</#if></span></span><span
                                class="paybox-up-right">订单号：<span class="span-color02" id="orderId">${orderPayResult.orderId!}</span></span>
                        </div>
                    </div>
                    <i class="left-semicircle left-bg01"></i>
                    <i class="right-semicircle left-bg01"></i>
                </div>
                <div class="paybox-center">
                    <div class="paybox-center-cnt clearfix">
                        <div class="pull-left"><span class="paybox-center-img"></span></div>
                        <div class="paybox-center-right clearfix">
                            <ul>
                                <li>关注真旅行微信号，</li>
                                <li>与真旅出行管家实时沟通，</li>
                                <li>保障无忧出行！</li>
                            </ul>
                        </div>
                    </div>
                    <i class="left-semicircle left-bg02"></i>
                    <i class="line"></i>
                    <i class="right-semicircle left-bg02"></i>
                </div>
                <div class="paybox-bottom">
                    <button type="button" class="btn btn-back payBtn-color01">返回首页</button>
                    <input type="hidden" id="realOrderId" value=${orderPayResult.realOrderId!}>
                    <input type="hidden" id="nature" value=${(orderPayResult.productNature)!''}>
                    <button type="button" class="btn btn-back payBtn-color02" id="enterOrderDetail">进入订单详情</button>
                </div>
            </div>
        </section>
    </div>
</@html.htmlIndex>
