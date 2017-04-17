                        <#if couponList?? && couponList?size gt 0>
                        <div class="order-conBlock">
                            <table class="order-conBlockTab order-favorableTable">
                                <colgroup>
                                    <col width="180"/>
                                    <col width="645"/>
                                </colgroup>
                                <tbody>
                                <tr>
                                    <th class="adjustpadding">
                                        <div class="checkboxContent coupon">
                                            <label class="checkbox active" id="couponCheckBox">
                                                <span class="commonIcon checkboxIcon"></span>使用代金券
                                            </label>
                                        </div>
                                    </th>
                                    <td class="adjustpadding">
                                        <div class="dropdown additional" style="width: 185px;">
                                        	<#list couponList as coupon>
                                        		<#if coupon_index == 0>
	                                            <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false">
	                                                <span class="activeFonts">${(coupon.amount)!'0'}元（${(coupon.name)!''}）</span>
	                                                <span class="caret"></span>
	                                            </a>
	                                            </#if>
                                            </#list>
                                            <ul class="dropdown-menu">
                                            	<#list couponList as coupon>
                                                	<li data-val="${(coupon.amount)!'0'}" <#if coupon_index == 0>class='active'</#if>data-couponid="${(coupon.accountCouponId)!}" data-poname="${(coupon.name)!}"><a href="javascript:void(0);">${(coupon.amount)!'0'}元（${(coupon.name)!'0'}）</a></li>
                                                </#list>
                                            </ul>
                                        </div>
                                        <span class="deductionFonts">抵扣 ￥<span id="equalTo"><#list couponList as coupon><#if coupon_index == 0>${(coupon.amount)?string("0.00")}</#if></#list></span></span>
                                    </td>
                                </tr>
                                <!--
                                <tr>
                                    <th>
                                        <div class="checkboxContent">
                                            <label class="checkbox active">
                                                <span class="commonIcon checkboxIcon"></span>使用积分
                                            </label>
                                        </div>
                                    </th>
                                    <td>
                                        <input type="text" style="width: 185px;" placeholder="8600"/>
                                        <span class="deductionFonts">抵扣 ￥86</span>
                                    </td>
                                </tr>
                                <tr>
                                    <th></th>
                                    <td class="integralTipsFonts">可用积分<span>8600</span>点,可抵扣<span>86</span>元</td>
                                </tr>
                                -->
                                </tbody>
                            </table>
                            <i class="left-semicircle"></i>
                            <i class="right-semicircle"></i>
                        </div>
                        </#if>

                        <-split->

                        <#if couponList?? && couponList?size gt 0>
		                    <div class="orderPriceItem" id="couponPrice" style="display;">
		                        <div class="orderPriceItemTit">网站优惠</div>
		                        <table>
		                            <colgroup>
		                                <col width="99"/>
		                                <col width="129"/>
		                                <col width="69"/>
		                            </colgroup>
		                            <tbody>
		                            <tr>
		                                <td>代金券抵扣</td>
		                                <#list couponList as coupon>
		                                	<#if coupon_index == 0>
				                                <td id="couponName">${(coupon.name)!''}</td>
				                                <td class="colorTd"><b>-￥<span id="couponSub">${(coupon.amount)?string('0.00')}</span></b></td>
			                                </#if>
		                                </#list>
		                            </tr>
		                            <!--
		                            <tr>
		                                <td>积分抵扣</td>
		                                <td>1000积分</td>
		                                <td class="colorTd"><b>-￥10</b></td>
		                            </tr>
		                            -->
		                            </tbody>
		                        </table>
		                    </div>
                    	</#if>