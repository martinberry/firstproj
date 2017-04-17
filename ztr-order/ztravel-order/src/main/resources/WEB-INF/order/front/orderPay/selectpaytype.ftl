<#import "/common/front/htmlIndex.ftl" as html/>
<@html.htmlIndex
	title="真旅行-订单支付"
  	jsFiles=["order/front/orderPay/selectpaytype.js","order/front/orderPay/caculate.js"]
>

		<div class="main-wrapper" id="main-wrapper">
            <div class="orderFlowBar">
                <ul class="clearfix">

                    <li><i class="pastTenseIcon"></i><span class="pastTenseFonts">选择产品</span></li>
                    <li><i class="pastTenseIcon"></i><span class="pastTenseFonts">填写订单</span></li>
                    <li class="currentStatus"><i class="nowTenseIcon"><em>3</em></i><span class="nowTenseFonts">在线支付</span></li>
                    <li><i class="futureTenseIcon"><em>4</em></i><span class="futureTenseFonts">支付完成</span></li>
                </ul>
            </div>
            <section class="onLinePayContent">
                <div class="top-border"></div>
                <div class="commonOrderModel">
                    <div class="aboutDivision clearfix">
                        <div class="titleLeft">
                        	<input type="hidden" id="checkSum" value="${checkSum!}">
                        	<input type="hidden" id="orderId" value="${orderPayVo.orderId!}">
                        	<input type="hidden" id="orderIdOrigin" value="${orderPayVo.orderIdOrigin!}">
                        	<input type ="hidden" id="productType" value="${orderPayVo.productType!}">
                            <div class="orderNumberFonts">订单号：<#if orderPayVo?exists ><span id="orderNo">${orderPayVo.orderCode!}</span></#if> </div>
                            <h3>${orderPayVo.title!}</h3>
                        </div>

                        <div class="titleRight">
                            <div class="rental">
                                <b class="fonts18">应付总额</b>
								<input type="hidden" id="payAmount" value="${orderPayVo.payAmount!}">
                                <b class="orangeFonts33" >￥<span id="payAmountPrecision"></span></b>
                            </div>
							<input type="hidden" id="couponItemId" value="${orderPayVo.couponItemId!}">
						   <input type="hidden" id="discountCoupon" value="${orderPayVo.discountCoupon!}">
						     <input type="hidden" id="integral" value="${orderPayVo.useRewardPoint!}">
						     <input type="hidden" id="totalPrice" value="${orderPayVo.totalPrice!}">
						<#if orderPayVo.productType??>
                            <#if orderPayVo.productType != 'OPCONFIRM'>
                            <div class="rentalDetail">
                                <label>产品总额: <span class="orangeFonts" id="totalPricePrescision">￥${orderPayVo.totalPrice!}</span></label>
                                <label>代金券:<span class="orangeFonts" id="discountCouponPrescision"></span></label>
                                <label>积分: <span class="orangeFonts" id="integralPrecision"></span></label>
                            </div>
                            </#if>
                          </#if>
                        </div>
                    </div>
	         <#if orderPayVo.productType??>
                   <#if orderPayVo.productType != 'OPCONFIRM'>
                    <div class="payHintExplain">
                        <i class="borderRadius left"></i>
                        <span class="payHintFonts">请于30分钟内完成付款，否则订单将被取消</span>
                        <i class="borderRadius right"></i>
                    </div>
                  </#if>
              </#if>
                    <i class="left-semicircle"></i>
                    <i class="right-semicircle"></i>
                </div>
                <div class="commonOrderModel">
                    <div class="titleModel padding30">
                        <i class="payTitleIcon"></i>选择支付方式
                    </div>
                    <div class="payWayContent">
                        <div class="radioContent">
                            <label class="radio active">
                                <span class="commonIcon radioIcon" name="Alipay"></span>
                                <span class="commonPayWayIcon payWayIcon01"></span>
                            </label>
                           <label class="radio">
                                <span class="commonIcon radioIcon" name="WeChatpay"></span>
                                <span class="commonPayWayIcon payWayIcon03"></span>
                            </label>
                        </div>
                    </div>
                    <i class="left-semicircle"></i>
                    <i class="right-semicircle"></i>
                </div>
                <div class="btnCenter">
                    <a href="javascript:void(0);" class="commonBtn orangeBtn width170 ac-payHintWindow" id="jumpToPay">去支付</a>
                </div>
            </section>
		</div>



    <div class="modal fade" id="ac-payHintWindow">
        <div class="modal-dialog" style="width: 450px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">支付提示</h4>
                </div>
                <div class="modal-body">
                    <p>请在新打开的页面中完成支付，支付完成前请不要关闭本窗口</p>
                </div>
                <div class="modal-footer">
                    <a href="javascript:void(0);" class="commonBtn blueBtn width170">支付成功</a>
                    <a href="javascript:void(0);" class="commonBtn orangeBtn width170">支付失败,继续支付</a>
                </div>
            </div>
        </div>
    </div>

  <div class="modal fade" id="ac-payErrorHintWindow">
        <div class="modal-dialog" style="width: 450px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">支付失败提示</h4>
                </div>
                <div class="modal-body" style="text-align:center">
                    <p　style="text-align:center" id="orderPayErrorMsg"></p>
                </div>
                <div class="modal-footer">
                    <a href="javascript:void(0);" class="commonBtn blueBtn width170" id="jumpTip">跳转订单详情页</a>
                </div>
            </div>
        </div>
    </div>

      <div class="modal fade" id="ac-payErrorHintWindow-suspend">
        <div class="modal-dialog" style="width: 450px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">支付失败提示</h4>
                </div>
                <div class="modal-body" style="text-align:center">
                    <p　style="text-align:center" id="orderPayErrorMsg">账号异常，请与客服联系</p>
                </div>
                <div class="modal-footer">
                    <a href="${memberServer!}/home" class="commonBtn blueBtn width170" id="jumpTip">确定</a>
                </div>
            </div>
        </div>
    </div>

</@html.htmlIndex>

<script>
	$(function(){
		var orderId = $("#orderId").val();
		console.log("wxServer:"+wxServer);
		var h5 =  wxServer + "/weixin/orderPay/selectPayType/"+orderId;
		if (navigator.userAgent.match(/iphone|android|phone|mobile|wap|netfront|java|opera mobi|opera mini|ucweb|windows ce|symbian|symbianos|series|webos|sony|blackberry|dopod|nokia|samsung|palmsource|xda|pieplus|meizu|midp|cldc|motorola|foma|docomo|up.browser|up.link|blazer|helio|hosin|huawei|novarra|coolpad|webos|techfaith|palmsource|alcatel|amoi|ktouch|nexian|ericsson|philips|sagem|wellcom|bunjalloo|maui|smartphone|iemobile|spice|bird|zte-|longcos|pantech|gionee|portalmmm|jig browser|hiptop|benq|haier|^lct|320x320|240x320|176x220/i) != null) {
			window.location.href = h5;
		}
	})
</script>

