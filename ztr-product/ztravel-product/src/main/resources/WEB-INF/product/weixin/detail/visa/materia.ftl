<#import "/common/weixin/htmlIndex.ftl" as html/>
<#import "/common/weixin/headerBar.ftl" as head/>
<@html.htmlIndex title="${(title)!''}"
		  	remoteCssFiles=["mstatic/css/product.css", "mstatic/css/swiper.min.css"]
		  	remoteJsFiles=["mstatic/js/vendor/swiper.jquery.min.js", "mstatic/js/base/fastclick.js","mstatic/js/vendor/jquery.lazyload.min.js"]
		  	localCssFiles=[]
		  	localJsFiles=["product/weixin/detail/visa/materia.js"]>
<div class="viewport" data-role="page">
	<@head.headerBar title="${(title)!''}"></@head.headerBar>
    <#if mases?? && mases?size gt 0>
	    <section class="main visaMater-wrap">
	        <#list mases as mas>
		        <div class="visaMaterBox">
		            <div class="visaMater-title">${(mas.title)!''}</div>
		            <div class="visaMater-content"><#noescape>${(mas.content)!''}</#noescape></div>
		        </div>
		        <#if mas.images?? && mas.images?size gt 0>
				   <#list mas.images as image>	
			        <div class="visaFoot">
	            		<a class="chkimg post-link" href="javascript:;" data-image="${image!}">查看样图</a>
	        		</div>
	        		</#list>
				</#if>
		    </#list>
	    </section>
    </#if>
    <a  class="bottom-visaBack" data-role="none" onclick="javascript:window.open(wxServer+'/weixin/product/visa/detail/${pid!}','_self');" >返回签证详情</a>
</div>
</@html.htmlIndex>