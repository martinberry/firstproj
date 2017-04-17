<#if productList?? && productList?size gt 0>
		<#list productList as product>
		<li>
		    <a class="detail"　href="javascript:toDetail(this);" data-pid="${(product.pid)!''}">
		        <div class="localtrip-box">
		            <img class="localTour" <#if (product.image)??>src="${mediaserver}imageservice?mediaImageId=${((product.image))!}"</#if>>
		            <div class="localtrip-info">
		                <div class="localtrip-title">${(product.basicInfo.pname)!''}</div>
		                <div class="localtrip-cnt ui-grid-a">
		                    <div class="ui-block-a">${(product.typeDesc)!''}</div>
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