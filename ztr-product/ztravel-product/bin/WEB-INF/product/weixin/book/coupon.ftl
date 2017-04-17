<#if couponList?? && couponList?size gt 0>
	<div class="order-coupon clearfix">
	        <div class="fl order-coupon-left">
		        <label>
		            <input type="checkbox" name="agr" value="agreement" data-role="none" checked>
		            <label class="cus_checkbox"></label>
		        </label>
		        <span class="couponspan">选择代金券</span>
		      </div>
		      <#list couponList as coupon>
                <#if coupon_index == 0>
	        		<div class="couponList fr order-coupon-right" id="currentCoupon" data-couponid="${(coupon.accountCouponId)!}">￥<span id="couponAmount">${(coupon.amount)?string("0.00")}</span>（<span id="couponName">${(coupon.name)!''}</span>）<label class="arr-btm"></label></div>
	        	</#if>
              </#list>
	  </div>
</#if>