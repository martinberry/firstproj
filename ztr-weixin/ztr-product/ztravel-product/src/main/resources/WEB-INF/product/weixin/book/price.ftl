	<!--产品价格明细-->
	<div class="order-detail-cnt">
        <div class="order-detail-til">必选产品</div>
        <#if priceInfo.packageId?? && priceInfo.packageId !=''>
     	   <div class="ui-grid-b">

	            <div class="ui-block-a">${(priceInfo.packageName)!''}</div>
	            <div class="ui-block-b"><span class="rmb-size">¥</span>${(priceInfo.packagePrice)!''}×1份</div>
	            <div class="ui-block-c"><span class="rmb-size">¥</span>${(priceInfo.totalPackagePrice)!''}</div>
            <#else>
				<#if (priceInfo.adultNum)?? && (priceInfo.adultNum) gt 0>
		         <div class="ui-grid-b">
		           <div class="ui-block-a">成人价</div>
		            <div class="ui-block-b"><span class="rmb-size">¥</span>${(priceInfo.adultPrice)!}×${(priceInfo.adultNum)!}人</div>
		            <div class="ui-block-c"><span class="rmb-size">¥</span>${(priceInfo.totalAdultPrice)!}</div>
		         </div>
		        </#if>
		        <#if (priceInfo.childNum)?? && (priceInfo.childNum) gt 0>
		        <div class="ui-grid-b">
		            <div class="ui-block-a">儿童价</div>
		            <div class="ui-block-b"><span class="rmb-size">¥</span>${(priceInfo.childPrice)!}×${(priceInfo.childNum)!}人</div>
		            <div class="ui-block-c"><span class="rmb-size">¥</span>${(priceInfo.totalChildPrice)!}</div>
		        </div>
		        </#if>
		        <#if (priceInfo.singleNum)?? && (priceInfo.singleNum) gt 0 >
		        <div class="ui-grid-b">
		            <div class="ui-block-a">单房差</div>
		            <div class="ui-block-b"><span class="rmb-size">¥</span>${(priceInfo.singlePrice)!}×${(priceInfo.singleNum)!}人</div>
		            <div class="ui-block-c"><span class="rmb-size">¥</span>${(priceInfo.totalSinglePrice)!}</div>
		        </div>
        </#if>
            </#if>
        </div>
    </div>

	<#if (priceInfo.containCoupon)?? &&(priceInfo.containCoupon)?c == "true">
	    <div class="order-detail-cnt">
	        <div class="order-detail-til">网站优惠</div>
	        <div class="ui-grid-b">
	            <div class="ui-block-a">${(priceInfo.couponName)!}</div>
	            <div class="ui-block-b"><span class="rmb-size">¥</span>${(priceInfo.couponAmount)?string("0.00")}/张</div>
	            <div class="ui-block-c"><span class="rmb-size">¥</span>${(priceInfo.couponAmount)?string("0.00")}</div>
	        </div>
	    </div>
    </#if>

    <!--
    <div class="order-detail-cnt">
        <div class="order-detail-til">附加产品</div>
        <div class="ui-grid-b">
            <div class="ui-block-a">欧洲WIFI</div>
            <div class="ui-block-b"><span class="rmb-size">¥</span>100×12天</div>
            <div class="ui-block-c"><span class="rmb-size">¥</span>1200</div>
        </div>
        <div class="ui-grid-b">
            <div class="ui-block-a">保险</div>
            <div class="ui-block-b"><span class="rmb-size">¥</span>1500×1人</div>
            <div class="ui-block-c"><span class="rmb-size">¥</span>1500</div>
        </div>
    </div>
    -->
	<-split->
	<!--订单总价-->
	<p class="price-icon">￥<span class="price-num">${(priceInfo.totalPrice)!}</span></p>
