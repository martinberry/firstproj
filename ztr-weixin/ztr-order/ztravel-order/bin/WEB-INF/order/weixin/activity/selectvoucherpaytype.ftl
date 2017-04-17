<#import "/common/weixin/htmlIndex.ftl" as html />
<#import "/common/weixin/headerBar.ftl" as header />

<@html.htmlIndex remoteJsFiles=[] remoteCssFiles=["mstatic/css/payment.css","mstatic/css/myWallet.css","mstatic/css/myCoupon.css"] localCssFiles=[]
                                  localJsFiles=["order/weixin/activity/selectvoucherpaytype.js","common/jquery.cookie.js"] title="代金券购买">
<body>
    <div data-role="page">
        <div class="headerBar">
            <div class="header">
                <a href="javascript:;" class="back">
                    <i class="back-arrow"></i>
                    <em class="back-border"></em>
                </a>
                <span class="title">确认订单</span>
            </div>
        </div>
    <!--<@header.headerBar title="确认订单" backURL="${wxServer}/weixin/voucherOrderPay/searchCouponList?combineOrderId=${(orderPayVo.orderId)!}&state=back"></@header.headerBar>-->
        <div class="wrapper">
            <div class="ui-content confirm-ui-content" data-role="popup" data-history="false" id="confirm-dialog" data-theme="a" data-overlay-theme="b" data-transition="pop" data-position-to="window" data-dismissible="false">
                <p class="dlg-cnt">请在新打开的页面中完成支付，支付完成前请不要关闭本窗口</p>
                <div class="dlg-foot">
                    <a class="btn-com btn-confirm" data-role="none" onclick="javascript:paysuccess();">支付成功</a>
                    <a class="btn-com btn-cancel" data-role="none" onclick="javascript:payfail();">支付失败</a>
                </div>
            </div>
            <div class="couponOrderCon">

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

                <div class="orderView">
                    <#if orderPayVo.couponSnapShot.amount?? && orderPayVo.couponSnapShot.amount gt 0 && orderPayVo.couponSnapShot.amount lte 10>
                    <div class="voucherItem voucher-bg-0">
                        <div class="flag"></div>
                        <div class="com-voucher rightBorder-green"></div>
                    <#elseif orderPayVo.couponSnapShot.amount gt 10 && orderPayVo.couponSnapShot.amount lte 20>
                     <div class="voucherItem voucher-bg-1">
                        <div class="flag"></div>
                        <div class="com-voucher rightBorder-blue"></div>
                    <#elseif orderPayVo.couponSnapShot.amount gt 20 && orderPayVo.couponSnapShot.amount lte 50>
                    <div class="voucherItem voucher-bg-2">
                        <div class="flag"></div>
                        <div class="com-voucher rightBorder-pink"></div>
                    <#else>
                    <div class="voucherItem voucher-bg-3">
                        <div class="flag"></div>
                        <div class="com-voucher rightBorder-yellow"></div>
                    </#if>
                        <div class="top">
                            <span class="title">${orderPayVo.couponSnapShot.name!}</span>
                                <span class="star-icon"></span>
                        </div>
                        <div class="horizontal-white-split-line"></div>
                        <div class="middle">
                            <div class="privilege">满${orderPayVo.couponSnapShot.orderLeast/100!}元减</div>
                            <div class="validity">${orderPayVo.couponSnapShot.validDateFrom!} - ${orderPayVo.couponSnapShot.validDateTo!}</div>
                            <div class="applicability">适用范围：<#noescape>${orderPayVo.couponSnapShot.description!}</#noescape></div>
                            <span class="price"><span class="price-number">${orderPayVo.couponSnapShot.amount/100!}</span><em>元</em></span>
                        </div>
                    </div>
                </div>

                <div class="totalPriceBlock clearfix">
                    <div class="priceDiv">总计<span><i>¥</i>${(orderPayVo.payAmount/100)!}</span></div>
                    <div class="amountDiv">数量 <span>×${orderPayVo.num!}</span></div>
                </div>
            </div>
            <div class="paymentCon">
                <h3 class="paymentTitle">支付方式</h3>
                <div class="prg_payType" style="margin-bottom: 3rem;">
                    <#if openId??>
                    <label class="prg-payOp clearfix" style="display:block;">
                        <img class="prg-payImg" src="${host}/mstatic/images/wechat.png"><span class="prg-payName">微信支付</span>
                        <label>
                            <input type="radio" name="type" value="WeChatpay" data-role="none" checked>
                        </label>
                    </label>
                    </#if>
                    <label class="prg-payOp choice-bed clearfix" style="display:block;">
                        <img class="prg-payImg" src="${host}/mstatic/images/mypayNew.png"><span class="prg-payName">支付宝支付</span>
                        <label>
                            <input type="radio" name="type" value="Alipay" data-role="none" <#if !openId??>checked</#if>>
                        </label>
                    </label>
                </div>
            </div>
        </div>
        <div class="payment-foot">
            <a class="pay-link" href="javascript:void(0);">立即支付</a>
        </div>
        <!-- 提示弹窗 -->
        <div class="ui-content" data-role="popup" id="backPageWindow" data-transition="none" data-history="false" data-position-to="window" data-theme="a" data-overlay-theme="b" data-dismissible="true">
            <div class="dlg-cnt tip-win-cnt">
                <p>交易快要完成了，你确定要放弃吗？</p>
            </div>
            <div class="dlg-foot tip-foot">
                <a class="btn btn-cancel" data-role="none" data-rel="back" href="javascript:void(0);">取消</a>
                <a class="btn btn-confirm" data-role="none" href="javascript:void(0);">确定</a>
            </div>
        </div>
    </div>

<script type="text/javascript">
    $(function(){
        FastClick.attach(document.body);

        $(".back").click(function(){
            $("#backPageWindow").popup("open");

            $(".btn-confirm").click(function(){
                window.location.href = wxServer + "/weixin/voucherOrderPay/searchCouponList?combineOrderId=${(orderPayVo.orderId)!}&state=back";
            });
        });
    });

</script>
</body>
</@html.htmlIndex>