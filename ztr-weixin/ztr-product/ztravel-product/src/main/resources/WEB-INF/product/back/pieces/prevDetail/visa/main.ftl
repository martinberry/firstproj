<#import "/common/front/userHtmlBase2.ftl" as html />
<@html.htmlIndex remoteJsFiles=["js/base/jquery-1.11.2.min.js", "js/bootdist/bootstrap.min.js","js/maintain/jquery-ui.min.js","js/client/common.js", "js/global/global.js","js/vendor/underscore.js","js/vendor/jquery.lazyload.min.js"]
								  localCssFiles=["member/front/round_image.css"]
                                  remoteCssFiles=["css/bootstrap.min.css", "css/client/bootstrap.reset.css", "css/common.css","css/client/common.css", "css/client/flight_logo.css", "css/client/visaInfo.css"]
                                  localJsFiles=["common/caculate.js","product/back/pieces/prevDetail/visa_prev_detail.js","common/jquery.qrcode.min.js"]
                                  title="碎片化产品详情预览">
	<!-- header start -->
    <header class="visa-head">
        <div class="head clearfix">
            <div class="logo pull-left"></div>
            <div class="proHeadNavBlock pull-right">
                <a href="javascript:void(0);" title="上一个" class="commonIcon headLeftArrowIcon"></a>
                <a href="javascript:void(0);" title="返回首页" class="commonIcon headHomeIcon"></a>
                <a href="javascript:void(0);" title="返回列表" class="commonIcon headListIcon"></a>
                <a href="javascript:void(0);" title="下一个" class="commonIcon headRightArrowIcon"></a>
            </div>
        </div>
    </header>
	<!-- header end -->

	<div class="visa-contain">
		<input id="pid" type="hidden" value="${(product.pid)!''}">
		<input id="prodId" type="hidden" value="${(product.proId)!''}">
		<input id="nature" type="hidden" value="${(product.natureType)!''}">
	    <div class="visa-title">${(product.basicInfo.pname)!''}</div>
	    <div class="visa-content clearfix">
	        <div class="content-wrap">
         		<#include "basic.ftl"/>
	            <div class="visa-mainContent">
		            <#include "materia.ftl"/>
	            	<#include "processes.ftl"/>
	           		<#include "fee.ftl"/>
	              	<#include "comment.ftl"/>
            	</div>
	        </div>
        	<#include "plan.ftl"/>
	    </div>
	</div>
	<#include "/common/front/right_side.ftl"/>
	<#include "/common/front/header/footer.ftl"/>
	<#include "tips.ftl"/>
</@html.htmlIndex>