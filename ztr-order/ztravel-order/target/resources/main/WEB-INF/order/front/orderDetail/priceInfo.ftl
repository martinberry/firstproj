<#macro priceInfo>

            <#assign price = (orderDetail.price)!/>
            <div class="commonOrderModel">
                <i class="left-semicircle"></i>
                <i class="right-semicircle"></i>
                <div class="titleModel blueFonts">
                    <span>价格明细</span>
                    <i class="left-semicircle"></i>
                    <i class="right-semicircle"></i>
                </div>
                <div class="commonStyle">
                    <table class="commonLightBlueThead leftTh">
                        <colgroup>
                            <col width="160">
                            <col width="230">
                            <col width="320">
                            <col width="310">
                        </colgroup>
                        <tbody>
                       
                        <#if price.packageId??>
                         <tr>
                        <th rowspan="${priceRowSpan!}">必选产品</th>
	               		         <td colspan="2">${price.packageName!}</td>
	                            <td class="orangeWeight16">¥ ${price.totalPackagePrice!}</td>
	                        </tr>
                        <#else>
 							<tr>
                            <th rowspan="${priceRowSpan!}">必选产品</th>
                            <td>成人价</td>
                            <td>¥ ${(price.adultPrice)!''} * ${(price.adultNum)!}人</td>
                            <td class="orangeWeight16">¥ ${(price.totalAdultPrice)!}</td>
                        </tr>
                        <#if (price.childrenPrice)?? && (price.childNum)?? && price.childNum != 0>
                        <tr>
                            <td>儿童价</td>
                            <td>¥ ${(price.childrenPrice)!''} * ${(price.childNum)!}人</td>
                            <td class="orangeWeight16">¥ ${(price.totalChildPrice)!}</td>
                        </tr>
                        </#if>
                        <#if (price.singleRoomDiff)?? && (price.singleNum)?? && price.singleNum != 0>
                        <tr>
                            <td>单房差</td>
                            <td>¥ ${(price.singleRoomDiff)!''} * ${(price.singleNum)!}人</td>
                            <td class="orangeWeight16">¥ ${(price.totalSingleDiff)!''}</td>
                        </tr>
                        </#if>
                        </#if>
 
						<#if orderDetail.additionalProducts??>
							<#list orderDetail.additionalProducts as item>
							<tr>
							    <#if item_index == 0>
		                         <th rowspan="${orderDetail.additionalProducts?size}">附加产品</th>
	                            </#if>
		                            <td>${item.name!}</td>
		                            <td>¥ ${item.price!} * ${(item.num)!}人</td>
		                            <td class="orangeWeight16">¥ ${item.totalPrice!}</td>
		                        </tr>
							</#list>
						</#if>

                        <#if (price.couponName)?? && (orderDetail.order.discountTotalSub)?? && orderDetail.order.discountTotalSub != ".00">
                        <tr>
                            <th>网站优惠</th>
                            <td>代金券</td>
                            <td>${(price.couponName)!''}</td>
                            <td class="orangeWeight16">-¥ ${(orderDetail.order.discountTotalSub)!''}</td>
                        </tr>
                        </#if>
                        <tr>
                            <th>应付金额</th>
                            <td colspan="3" class="orangeWeight22">¥ ${(orderDetail.order.payAmount)!''}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>

</#macro>