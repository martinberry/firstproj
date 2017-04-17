<!-- 订单基本信息开始 -->
<#macro orderBasic>
	<#if orderVo??>
    <div class="order-sum">
        <div class="order-til">
            <span class="order-num">订单号:${(orderVo.orderCode)!}</span><span class="order-date">${(orderVo.createDate)!}</span>
        </div>
        <div class="order-row">
            <span class="order-name">${(orderVo.productName)!}<#if (orderVo.costPriceName)??>--${(orderVo.costPriceName)!''}</#if></span>
            <div class="right-price">
            <span class="order-price"><span class="rmb">￥</span>${(orderVo.payment)!}</span>
              <#if commonOrderStatus?? && commonOrderStatus == "UNPAY" && orderVo.commonOrderPrice ??>
            	 <span class="order-fill-price"><span class="rmb">待补款: ￥</span>${orderVo.commonOrderPrice!}</span>
             </#if>
             </div>
        </div>
        <input type="hidden" id="orderStatus" value="${(orderVo.status)!}">
        <div class="order-row clearfix nopaddingt">
            <span class="order-start">${(orderVo.departDate)!} 出发</span>
            <#if orderVo.status == "UNPAY">
            	<a class="order-pay ui-link toPay" data-order="${(orderVo.orderId)!}" href="javascript:void(0);">去支付</a>
            </#if>
            <#if orderVo.status == "COMPLETED" && (orderVo.isComment)?c == "true">
            	<a class="order-judge toEvaluate" data-order="${(orderVo.orderId)!}" href="javascript:void(0);">去评价</a>
            </#if>
            <#if commonOrderStatus?? && commonOrderStatus == "UNPAY">
            	<a class="order-pay ui-link toPay" data-order="${commonOrderId!}" href="javascript:void(0);">去补款</a>
            </#if>
        </div>
		<@progress.orderProgress />        
    </div>
    </#if>
 <!-- 订单基本信息结束 -->
 </#macro>