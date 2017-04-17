<#macro orderFooter>
<!-- 订单详情底部信息开始 -->
<div class="order-foot">
        <div class="ui-grid-a">
        <#if orderVo??>
            <div class="ui-block-a">
                <!--<div>应付金额：</div>-->
                <div class="order-head">总价 <span class="rmb-num">￥${(orderVo.payment)!}</span></div>
            </div>
            <#if orderVo.status == "UNPAY">
	            <div class="ui-block-b">
	            	<a class="order-link toPay" data-order="${(orderVo.orderId)!}" href="javascript:void(0);">去支付</a>
	            </div>
            </#if>
            <#if orderVo.status == "COMPLETED" && (orderVo.isComment)?c == "true">
	            <div class="ui-block-b">
	            	<a class="order-judge toEvaluate" data-order="${(orderVo.orderId)!}" href="javascript:void(0);">去评价</a>
	            </div>
            </#if>
        </#if>
        </div>
    </div>
    <!-- 订单详情底部信息结束 -->
</#macro>