<#macro mainContent>
	<input type="hidden" id="lightColor_hidden" name="lightColor" value="${(product.lightColor)!}" />
   	<div class="productDetailModel">
            <div class="productDetailBanner">
                <div class="pro-detail-cont">
			        <img <#if product.titleImages[0]??>src="${mediaserver}imageservice?mediaImageId=${(product.titleImages[0])!}"</#if> alt="">
			    </div>
                <div class="pro-detail-img">
			        <img <#if product.images[0]??>src="${mediaserver}imageservice?mediaImageId=${(product.images[0])!}"</#if>>
			    </div>
            </div>
            <div class="bannerBottomList">
                <div class="middleListContent">
                    <ul class="bannerBottomHint clearfix">
                    <#list (product.highLights)! as highLight>
                        <li class="clearfix">
                            <span class="leftHintIcon icon-${highLight_index + 1}"></span>
                            <div class="hintContent">
                                <p class="title">${(product.highLightTitles[highLight_index])!}</p>
                                <p class="cont">${highLight!}</p>
                            </div>
                        </li>
                    </#list>
                    </ul>
                </div>
            </div>
        </div>
</#macro>