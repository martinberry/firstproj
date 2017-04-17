<#if (hasRecomProd)!false >
	<div class="recom-path">
	    <div class="recom-content">
	        <p>${(searchCriteria.departure)!}-${(searchCriteria.destination)!} 线路还未上线</p>
	        <p>推荐您以下路线</p>
	        <p><label class="path-img02"></label></p>
	        <span class="path-img01"></span>
	    </div>
	    <div class="path-space"></div>
	    <div class="path-til">推荐路线</div>
	</div>
</#if>
<#if productList??>
	<ul>
		<#list productList as product>
	        <li>
	            <div class="productL">
                        <div class="prdList" onclick="jump2DetailPage('${(product.pid)!}')">
                            <a class="prdlist-link" rel="external" data-role="none">
                                <img class="prdlist-img" src="${mediaserver}imageservice?mediaImageId=s1${(product.imageId)!}">
                            </a>
                            <div class="imgIntro">
                                <span class="imgTitle"><em></em>${(product.theme)!}<em></em></span><br>
                                <span class="imgIntroduce">${(product.subName)!}</span>
                            </div>
                        </div>
                        <div class="productInfo">
                            <p class="prdlist-tips">${(product.pName)!}</p>
                            <p class="prdlist-intro">${(product.recommendWords)!}</p>
                        </div>
                        <div class="proPrice">￥ <span>${(product.lowestPrice)!}</span>起</div>
                </div>
                <#if product.topics??>
                	<#list product.topics as topic>
			            <div class="discussContain">
		                    <div class="discuss" data-tid="${(topic.topicId)!}">
		                        <div class="discussTitle"><span>${(topic.topicTitle)!}</span></div>
		                        <div class="discussNum"><em>${(topic.discussNum)!'0'}</em>人在讨论</div>
		                    </div>
		                </div>
	                </#list>
                </#if>
	        </li>
	    </#list>
	</ul>
</#if>