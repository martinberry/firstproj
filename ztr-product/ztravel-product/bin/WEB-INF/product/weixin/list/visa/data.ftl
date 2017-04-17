	<#if productList?? && productList?size gt 0>
		<#list productList as product>
			<li>
		       <a class="detail" href="javascript:toDetail(this);" data-pid="${(product.pid)!''}">
		           <div class="visaTrip-inner">
		               <img class="visaTrip-img" <#if (product.image)??>src="${mediaserver}imageservice?mediaImageId=${((product.image))!}"</#if> alt="图片">
		               <div class="visaTrip-info">
		               <#if (product.toCountry)??>
		               		<#if product.toCountry?index_of("日本") != -1 >
		               			<span class="flagIcon flag-japan"></span>
		               		</#if>
		               		<#if product.toCountry?index_of("英国") != -1 >
		               			<span class="flagIcon flag-uk"></span>
		               		</#if>
		               		<#if product.toCountry?index_of("美国") != -1 >
		               			<span class="flagIcon flag-usa"></span>
		               		</#if>
		               		<#if product.toCountry?index_of("德国") != -1 >
		               			<span class="flagIcon flag-germ"></span>
		               		</#if>
		               		<#if product.toCountry?index_of("西班牙") != -1 >
		               			<span class="flagIcon flag-spain"></span>
		               		</#if>
		               		<#if product.toCountry?index_of("泰国") != -1 >
		               			<span class="flagIcon flag-thail"></span>
		               		</#if>
		               		<#if product.toCountry?index_of("越南") != -1 >
		               			<span class="flagIcon flag-viet"></span>
		               		</#if>
		               		<#if product.toCountry?index_of("韩国") != -1 >
		               			<span class="flagIcon flag-korea"></span>
		               		</#if>
		               		<#if product.toCountry?index_of("意大利") != -1 >
		               			<span class="flagIcon flag-icon02"></span>
		               		</#if>
		               		<#if product.toCountry?index_of("法国") != -1 >
		               			<span class="flagIcon flag-icon01"></span>
		               		</#if>
		               </#if>
		                   <div class="visaTrip-title">${(product.basicInfo.pname)!''}</div>
		                   <div class="visaTrip-foot ui-grid-a">
                                <div class="ui-block-a">签证</div>
                                <div class="ui-block-b">
                                    <span class="localtrip-price">￥<span class="priceNum">${(product.lowest)!''}</span>起</span>
                                </div>
                            </div>
		               </div>
		           </div>
		       </a>
		   </li>
	   </#list>
	  </#if>