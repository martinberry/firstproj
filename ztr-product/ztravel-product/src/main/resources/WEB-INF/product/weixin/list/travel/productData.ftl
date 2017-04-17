		<#if productList??>
			<#list productList as product>
			 	<div class="freeWalk-box">
			           <a href="javascript:void(0);" onclick="jump2DetailPage('${(product.pid)!}')">
			               <div class="freeWalk-head">
			                   <img class="freeWalk-img" src="${mediaserver}imageservice?mediaImageId=s1${(product.imageId)!}" alt="自由行">
			                   <div class="freeWalk-til">${(product.subName)!}</div>
			               </div>
			               <div class="freeWalk-info">
			                   <div class="freeWalk-title">${(product.pName)!}</div>
			                   <div class="freeWalk-foot clearfix">
			                       <div class="foot-left">${(product.recommendWords)!}</div>
			                       <div class="foot-right">￥<span class="priceNum">${(product.lowestPrice)!}</span>起</div>
			                   </div>
			               </div>
			           </a>
			       </div>
			       <#if product.topics??>
                	<#list product.topics as topic>
				       <div class="freeWalk-box">
				           <div class="freeWalk-topic" data-tid="${(topic.topicId)!}">
				               <div class="discussTitle"><span>${(topic.topicTitle)!}</span></div>
				               <div class="discussNum"><em>${(topic.discussNum)!'0'}</em>人在讨论</div>
				           </div>
				       </div>
			         </#list>
                	</#if>
		       </#list>
	      </#if>