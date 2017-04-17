<#macro additioanlProductInfo>
	<#if additionalProducts?? && additionalProducts?size gt 0>
	    <div class="row-til">附加产品</div>
	    <div class="row-content">
	        <ul class="addi-prod-list">
	       		<#list additionalProducts as additionalProduct>
	       			<li class="item">
	       				<span>
	       					${additionalProduct.name!}
	       				</span>
	       				<span>
	       					X ${additionalProduct.num!}
	       				</span>
	       				
	       			</li>
	       		</#list>
	        </ul>
	    </div>
	 </#if>
    <!-- 订单附加产品信息结束 -->
</#macro>