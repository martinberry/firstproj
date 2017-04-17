	<#if orderListVo?? && (orderListVo.success)?c == "true">
		<#if (orderListVo.orderVoList)?? && (orderListVo.orderVoList)?size gt 0>
			<#list orderListVo.orderVoList as order>
			
			<li class="orderinfo" data-num="${(order.orderId)!}">
			
				<div class="orderHeader">
					<span class="orderNum">订单号:<em>${(order.orderCode)!}</em></span>
					<span class="orderDate">${(order.createDate)!}</span>
					<input type='hidden' name="productNature" class="productNature" value="${order.productNature!''}"/>					
				</div>
				<div class="orderDetail clearfix">
					<div class="travelInfo">
						<span class="travelTitle">${(order.productName)!}</span>
						<span class="travelDate"><em>${(order.departDate)!}</em>出发</span>		
						<input type='hidden' name="productNature" class="productNature" value="${order.productNature!''}"/>				
					</div>
					
					<div class="payInfo">
						<span class="travelPrice">￥<em>${(order.payment)!}</em></span>
						<#if order.commonOrderStatus ??>
							<#if order.commonOrderStatus == "UNPAY">
								<span class="fillPrice">待补款:￥<em>${order.commonOrderPrice!}</em></span>
							</#if>
						</#if>
						<#if order.status == "UNPAY">
							<a href="javascript:void(0);" data-order="${(order.orderId)!}" class="pay" data-role="none">去支付</a>
						</#if>
						<#if order.status == "PAYED">
							<#if order.commonOrderStatus??>
								<#if order.commonOrderStatus != "UNPAY">
									<a href="javascript:void(0);" class="completed" data-role="none">支付成功</a>
								</#if>
							<#else>
								<a href="javascript:void(0);" class="completed" data-role="none">支付成功</a>
							</#if>
						</#if>
						<#if order.status == "REFUNDING">
							<a href="javascript:void(0);" class="completed" data-role="none">退款中</a>
						</#if>
						<#if order.status == "CONFIRM" || order.status == "GIFTSEND" || order.status == "OUTNOTICE">
							<a href="javascript:void(0);" class="completed" data-role="none">已确认</a>
						</#if>
						<#if order.status == "OUTTING">
							<a href="javascript:void(0);" class="completed" data-role="none">出行中</a>
						</#if>
						<#if (order.isComment)?c == "true" && order.status == "COMPLETED">
							<a href="javascript:void(0);" data-order="${(order.orderId)!}" class="evaluate" data-role="none">去评价</a>
						</#if>
						<#if (order.isComment)?c == "false" && order.status == "COMPLETED">
							<a href="javascript:void(0);" class="completed" data-role="none">已完成</a>
						</#if>
						<#if order.status == "CANCELED">
							<a href="javascript:void(0);" class="completed" data-role="none">已取消</a>
						</#if>
						<#if order.commonOrderStatus ??>
							<#if order.commonOrderStatus == "UNPAY">
								<a href="javascript:void(0);" data-order="${(order.commonOrderId)!}" class="pay" data-role="none">去补款</a>
							</#if>
						</#if>
					</div>
				</div>
			</li>
			</#list>
		</#if>
	</#if>