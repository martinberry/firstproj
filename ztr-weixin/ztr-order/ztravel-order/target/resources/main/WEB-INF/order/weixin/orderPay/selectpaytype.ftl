<#import "/common/weixin/htmlIndex.ftl" as html />
<#import "/common/weixin/headerBar.ftl" as header />

<@html.htmlIndex remoteJsFiles=[] remoteCssFiles=["mstatic/css/payment.css"] localCssFiles=[]
                                  localJsFiles=["order/weixin/orderPay/selectpaytype.js","common/jquery.cookie.js"] title="在线支付">
<body>
<div class="viewport" data-role="page">
    <@header.headerBar title="在线支付"></@header.headerBar>
    <div class="progressBar">
        <div class="prgbar-img">
            <img class="prgbar" src="${host}/mstatic/images/prgbar-after01.png"><span class="prgline01"></span><img
                class="prgbar" src="${host}/mstatic/images/prgbar-after02.png"><span class="prgline01"></span><img
                class="prgbar" src="${host}/mstatic/images/prgbar-after03.png"><span class="prgline02"></span><img
                class="prgbar" src="${host}/mstatic/images/prgbar-before04.png">
        </div>
        <div class="prgbar-txt">
            <span class="prgtxt-box">选择产品</span><span class="prgtxt-box">填写订单</span><span class="prgtxt-box">在线支付</span><span
                class="prgtxt-box01">支付完成</span>
        </div>
    </div>
<#if orderPayVo.productType??>
	<#if orderPayVo.productType !="OPCONFIRM">
	    <div class="prgtips">
	        <div class="prg-price"></div>
	        <input type="hidden" id="countDown" value= ${countDown!}>
	        <p class="prg-val">后订单将失效，请尽快完成支付！</p>
    	</div>
	</#if>
</#if>

    <div class="ui-content confirm-ui-content" data-role="popup" data-history="false" id="confirm-dialog" data-theme="a" data-overlay-theme="b" data-transition="pop" data-position-to="window" data-dismissible="false">
    	<p class="dlg-cnt">请在新打开的页面中完成支付，支付完成前请不要关闭本窗口</p>
	    <div class="dlg-foot">
	        <a class="btn-com btn-confirm" data-role="none" onclick="javascript:paysuccess();">支付成功</a>
	        <a class="btn-com btn-cancel" data-role="none" onclick="javascript:payfail();">支付失败</a>
	    </div>
    </div>
    <div class="prg-space"></div>
    <input type="hidden" id="checkSum" value="${(checkSum)!}">
	<input type="hidden" id="orderId" value="${(orderPayVo.orderId)!}">
	<input type="hidden" id="orderNo" value="${(orderPayVo.orderCode)!}">
	<input type ="hidden" id="productType" value="${(orderPayVo.productType)!}">
	<input type = "hidden" id="discountCoupon" value="${(orderPayVo.discountCoupon)!}">
	<input type = "hidden" id="totalPrice" value="${(orderPayVo.totalPrice)!}">
	<input type = "hidden" id="couponItemId" value="${(orderPayVo.couponItemId)!}">
	<input type = "hidden" id="integral" value="${(orderPayVo.useRewardPoint)!}">
	<input type = "hidden" id="payAmount" value="${(orderPayVo.payAmount)!}">

	<input type = "hidden" id="appId">
	<input type = "hidden" id="timeStamp">
	<input type = "hidden" id="nonceStr">
	<input type = "hidden" id="package">
	<input type = "hidden" id="signType">
	<input type = "hidden" id="paySign">

    <div class="prg-order clearfix">
        <span class="prg-left" id="orderNo">订单号:${(orderPayVo.orderCode)!}</span><span class="prg-right">${(orderPayVo.createDate)!}</span>
    </div>
    <div class="prg-til">${orderPayVo.title!}</div>
    <div class="ui-grid-a prg-grid">
        <div class="ui-block-a prgblock-a">${(orderPayVo.departDate)!} 出发</div>
        <div class="ui-block-b prgblock-b">应付金额<span class="rmb">￥</span><span class="prg-cash" id="">${(orderPayVo.payAmount/100)!}</span></div>
    </div>
    <div class="prg-space01"></div>
    <div class="prg-pay">
        <div class="prg-paytil">支付方式</div>
        <div class="prg-payType">
        	<input type="hidden" id ="openId" value="${openId!}">
        	<#if openId??>
            <div class="prg-payOp clearfix choice-bed" id="wechatPayType">
                <img class="prg-payImg" src="${host}/mstatic/images/wechat.png"><span class="prg-payName" >微信支付</span>
                <label>
                    <input type="radio" name="type" value="WeChatpay" data-role="none" checked id="weChatRadio">
                </label>
            </div>
            </#if>
            <div class="prg-payOp clearfix choice-bed" id="aliPayType">
                <img class="prg-payImg" src="${host}/mstatic/images/mypay.png"><span class="prg-payName" >支付宝支付</span>
                <label>
                    <input type="radio" name="type" value="Alipay" data-role="none" id="aliRadio" >
                </label>
            </div>
        </div>
    </div>
    <div class="goPay">
        <a class="pay-link" href="javascript:void(0);">立即支付</a>
    </div>
</div>

</body>
</@html.htmlIndex>