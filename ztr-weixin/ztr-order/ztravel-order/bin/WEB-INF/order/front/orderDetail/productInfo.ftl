<#macro productInfo>

            <#assign order = (orderDetail.order)!/>
            <#assign product = (orderDetail.product)!/>
            <div class="commonOrderModel">
                <i class="left-semicircle"></i>
                <i class="right-semicircle"></i>
                <div class="titleModel blueFonts">
                    <span>产品信息</span>
                    <i class="left-semicircle"></i>
                    <i class="right-semicircle"></i>
                </div>
                <div class="commonStyle productInfoModel">

                    <div class="productInfoTitle"><span class="blueFonts fontSize16">订单号:${order.orderNo!}</span>${product.productTitle!}   <#if product.costPriceName??>--${product.costPriceName!}</#if></div>

                    <table class="productInfoTab">
                        <colgroup>
                            <col width="160">
                            <col width="150">
                            <col width="150">
                            <col width="150">
                            <col width="130">
                            <col width="130">
                            <col width="150">
                        </colgroup>
                        <tbody>
                        <tr>
                            <td class="noLeftBorder">
                                <img class="productInfoImg" src="${mediaserver}imageservice?mediaImageId=${product.imageId!}">
                            </td>
                            <td class="noLeftBorder">
                                <div class="orderInfoTitles">下单日期</div>
                                <div>${order.createDate!}</div>
                            </td>
                            <td>
                                <div class="orderInfoTitles">出游日期</div>
                                <div>${product.bookDate!}</div>
                            </td>
                            <td>
                                <div class="orderInfoTitles">订单金额</div>
                                <div class="orangeFonts">￥${order.totalPrice!}</div>
                            </td>
                            <td>
                                <div class="orderInfoTitles">应付金额</div>
                                <div class="orangeFonts">
                                    <div>￥${order.payAmount!}</div>
                                    <#if (order.discountTotalSub)??>
                                    	<div class="fontSize12">已优惠&nbsp; ￥${order.discountTotalSub!}</div>
                                	</#if>
                                </div>
                            </td>
                            <td>
                                <div class="orderInfoTitles">状态</div>
                                <div class="orangeFonts">${order.frontState!}</div>

                            </td>
                            <td style="vertical-align: middle;">
                            <#if order.nextStep?? && order.nextStep=="去支付">
                            <a href="javascript:toSelectPay();" class="commonBtn orangeBtn">去支付</a>
                            <#elseif order.nextStep?? && order.nextStep=="评价" && order.canComment==true>
                            <a href="${basepath}/order/comment/edit/${(order.orderId)!}" class="commonBtn orangeBtn">评价</a>
                            <#elseif order.frontState?? && order.frontState=="已取消">
                            <a href="${basepath}/home" class="commonBtn orangeBtn">查看其他</a>
                            </#if>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <script type="text/javascript">
				function toSelectPay(){
					_paq.push(['trackEvent', 'myorderpage', 'ztrselectpayorder']);
					window.location.href = "${basepath}/orderPay/selectPayType/${(order.orderId)!}";
				}

				function toBackPay(){
					_paq.push(['trackEvent', 'myorderpage', 'ztrselectpayorder']);
					window.location.href = "${basepath}/orderPay/selectPayType/${commonOrderId!}";
				}
			</script>

</#macro>