<#macro orderPrice>
<!-- 订单价格明细信息开始 -->
    <div class="row-til">
        <div class="borderB">
            <i class="priceIcon"></i>价格明细
        </div>
    </div>
    <div class="order-price-detail">
    	<#if priceDetailVo?? >
	    <div class="order-price-box">
	            <div class="order-price-type">必选产品</div>
	            <div class="ui-grid-b">
	               <#if priceDetailVo.packageId??>
	                <div class="ui-block-a">
	                    <p><#if priceDetailVo.packageName??>${priceDetailVo.packageName!}</#if></p>
	                </div>
	                <div class="ui-block-b">
	                    <p></p>
	                </div>
	                <div class="ui-block-c">
	                    <p><#if priceDetailVo.totalPackagePrice ?? >¥${priceDetailVo.totalPackagePrice!}</#if></p>
	                </div>
	                <#else>
	                	<div class="ui-block-a">
	                	<#if (priceDetailVo.adultNum)?? && (priceDetailVo.adultNum) gt 0 >
	                    <p>成人</p>
	                    </#if>
	                    <#if (priceDetailVo.childNum)?? && (priceDetailVo.childNum) gt 0 >
	                    <p>儿童</p>
	                    </#if>
	                    <#if (priceDetailVo.singleNum)?? && (priceDetailVo.singleNum) gt 0 >
	                    <p>单房差</p>
	                    </#if>
	                </div>
	                <div class="ui-block-b">
	                	<#if (priceDetailVo.adultNum)?? && (priceDetailVo.adultNum) gt 0 >
	                    <p>¥${(priceDetailVo.adultPrice)!}×${(priceDetailVo.adultNum)!}人</p>
	                    </#if>
	                    <#if (priceDetailVo.childNum)?? && (priceDetailVo.childNum) gt 0 >
	                    <p>¥${(priceDetailVo.childrenPrice)!}×${(priceDetailVo.childNum)!}人</p>
	                    </#if>
	                    <#if (priceDetailVo.singleNum)?? && (priceDetailVo.singleNum) gt 0 >
	                    <p>¥${(priceDetailVo.singleRoomDiff)!}×${(priceDetailVo.singleNum)!}</p>
	                    </#if>
	                </div>
	                <div class="ui-block-c">
	                	<#if (priceDetailVo.adultNum)?? && (priceDetailVo.adultNum) gt 0 >
	                    <p>¥${(priceDetailVo.totalAdultPrice)!}</p>
	                    </#if>
	                    <#if (priceDetailVo.childNum)?? && (priceDetailVo.childNum) gt 0 >
	                     <p>¥${(priceDetailVo.totalChildPrice)!}</p>
	                    </#if>
	                    <#if (priceDetailVo.singleNum)?? && (priceDetailVo.singleNum) gt 0 >
	                    <p>¥${(priceDetailVo.totalSingleDiff)!}</p>
	                    </#if>
	                </div>
	                </#if>
	            </div>
           </div> 
           
           <#if additionalProducts??>
	           <#if additionalProducts?size gt 0>
	            <div class="order-price-box">
	             <div class="order-price-type">附加产品</div>
	            	<div class="ui-grid-b">
	            		<div class="ui-block-a">
	            			<#list additionalProducts as item>
	      					<p>${item.name!}</p>
	      					</#list>
	                	</div>
	                	<div class="ui-block-b">
	      					<#list additionalProducts as item>
	      					<p>¥${(item.price)!}×${(item.num)!}人</p>
	      					</#list>
	                	</div>
	                	<div class="ui-block-c">
	      					<#list additionalProducts as item>
	      					<p>¥${item.totalPrice!}</p>
	      					</#list>
	                	</div>
                	</div>
                	</div>
	            </#if>
	            </#if>

	            <#if (priceDetailVo.couponId)?? && (priceDetailVo.couponId != "")>
		        <div class="order-price-box">
		            <div class="order-price-type">网站优惠</div>
		            <div class="ui-grid-b">
		                <div class="ui-block-a">
		                    <p>${(priceDetailVo.couponName)!}</p>
		                </div>
		                <div class="ui-block-b">
		                    <p>¥${(priceDetailVo.couponSub)!}×1张</p>
		                </div>
		                <div class="ui-block-c">
		                    <p>¥${(priceDetailVo.couponSub)!}</p>
		                </div>
		            </div>
		        </div>
	        </#if>
		</#if>
	</div>
    <!-- 订单价格明细信息结束 -->
</#macro>