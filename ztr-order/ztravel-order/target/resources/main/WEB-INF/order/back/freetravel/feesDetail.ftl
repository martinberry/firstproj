                <div class="moduleContent">
                    <div class="titleContent clearfix">
                        <h3 class="titleFonts blue2fb0c4">费用详情</h3>
                    </div>
                    <div class="costDetails">
                        <div class="titleFonts">费用包含</div>
                       <div class="contText"><#noescape>${(orderDetail.feesInclude)!''}</#noescape></div>
                    </div>
                    <div class="costDetails">
                        <div class="titleFonts">费用不含</div>
                        <div class="contText"><#noescape>${(orderDetail.feesExclude)!''}</#noescape></div>
                    </div>
                    <div class="costDetails">
                        <div class="titleFonts">赠送项目</div>
                        <div class="contText"><#noescape>${(orderDetail.giftItems)!''}</#noescape></div>
                    </div>
                 <div class="costDetails">
                        <div class="titleFonts">退改政策</div>
                        <div class="contText" style="padding-bottom:30px;"><#noescape>${(orderDetail.refundPolicy)!''}</#noescape></div>
                    </div>


                    <div class="costDetails">
                    	<div class="titleFonts">价格明细</div>
                    	<div class="contText">
                    	<table class="leftGrayTh" id="feeDetailTable">
                        	<colgroup>
	                            <col width="150">
	                            <col width="140">
	                            <col width="140">
	                            <col width="140">
                        	</colgroup>
	                        <tbody>

	                        <#if (orderDetail.feesDetail.packageName)??>
	                        <tr>
	                            <th rowspan="2">必选产品</th>
	                            <td class="textAlignRight">套餐名称</td>
	                            <td colspan="2">${(orderDetail.feesDetail.packageName)!''} </td>
	                        </tr>
	                        <tr>
	                            <td class="textAlignRight">套餐价格</td>
	                            <td>￥${(orderDetail.feesDetail.packagePrice)!''} X ${(orderDetail.feesDetail.packageNum)!''}</td>
	                            <td class="textAlignRight orangeFonts">￥<span>${(orderDetail.feesDetail.packageTotalPrice)!''}</span></td>
	                        </tr>
	                        <@listAdditional lists=(orderDetail.additionalProducts)![]></@listAdditional>
	                        <#else>
	                        <tr>
	                            <th rowspan="3">必选产品</th>
	                            <td class="textAlignRight">成人价</td>
	                            <td>￥${(orderDetail.feesDetail.adultPrice)!''} X ${(orderDetail.feesDetail.adultNum)!''}</td>
	                            <td class="textAlignRight orangeFonts">￥<span>${(orderDetail.feesDetail.totalAdultPrice)!''}</span></td>
	                        </tr>
	                        <tr>
	                            <td class="textAlignRight">儿童价</td>
	                            <td>￥${(orderDetail.feesDetail.childPrice)!''} X ${(orderDetail.feesDetail.childNum)!''}</td>
	                            <td class="textAlignRight orangeFonts">￥<span>${(orderDetail.feesDetail.totalChildPrice)!''}</span></td>
	                        </tr>
	                        <tr>
	                            <td class="textAlignRight">单房差</td>
	                            <td>￥${(orderDetail.feesDetail.singleRoomSupplement)!''} X ${(orderDetail.feesDetail.singleRoomNum)!''}</td>
	                            <td class="textAlignRight orangeFonts">￥<span>${(orderDetail.feesDetail.totalSingleSupplement)!''}</span></td>
	                        </tr>
							<@listAdditional lists=(orderDetail.additionalProducts)![]></@listAdditional>
	                        </#if>
	                        <tr>
	                            <th>网站优惠</th>
	                            <td class="textAlignRight">代金券优惠</td>
	                            <td>${(orderDetail.feesDetail.couponName)!''}</td>
	                            <td class="textAlignRight orangeFonts">-￥<span>${(orderDetail.feesDetail.couponPrice)!''}</span></td>
	                        </tr>
	                        <tr>
	                            <th>应付金额</th>
	                            <td colspan="3" class="textAlignRight orangeFonts">
	                                <b class="fontsWeight16">￥${(orderDetail.feesDetail.payPrice)!''}</b>
	                            </td>
	                        </tr>
	                        </tbody>
	                    </table>
                    </div>
                    </div>
                </div>

                <#macro listAdditional lists=[]>
                	<#if lists??>
                	<#list lists as item>
						<tr>
                            <#if item_index == 0><th rowspan="${lists?size}">附加产品</th></#if>
                            <td class="textAlignRight">${(item.name)!}</td>
                            <td>${((item.price/100)?string.currency)!} X ${(item.num)!}</td>
                            <td class="textAlignRight orangeFonts">${((item.totalPrice/100)?string.currency)!}</td>
                        </tr>
                	</#list>
                    </#if>
                </#macro>