<#import "/common/front/userHtmlBase.ftl" as html />
<#import "/product/front/detail/local_content.ftl" as mainContent/>
<#import "/product/front/detail/local_calendar.ftl" as priceCalendar/>
<#import "/product/front/detail/local_product_description.ftl" as productIntroduction/>
<#import "/product/front/detail/local_use_description.ftl" as useIntroduction/>
<#import "/product/front/detail/local_attention.ftl" as attention/>
<#import "/product/front/detail/local_fee_detail.ftl" as priceExplain/>
<#import "/product/front/detail/local_evaluation.ftl" as localEvaluation/>


<@html.htmlIndex remoteJsFiles=["js/base/jquery-1.11.2.min.js", "js/bootdist/bootstrap.min.js", "js/vendor/bootstrap-datepicker.min.js",
								"js/vendor/bootstrap-datepicker.zh-CN.min.js", "js/global/global.js","js/maintain/jquery-ui.min.js","js/vendor/jquery.cycle.all.js",
								"js/client/common.js", "js/vendor/underscore.js","js/client/workplatform.js","js/vendor/jquery.lazyload.min.js"]
								  localCssFiles=["member/front/round_image.css"]
                                  remoteCssFiles=["css/bootstrap.min.css", "css/client/bootstrap.reset.css", "css/common.css",
                                  "css/client/common.css", "css/client/flight_logo.css","css/client/workplatform.css","css/client/visaInfo.css"]
                                  localJsFiles=["product/front/localDetail.js","member/front/login.js","common/jquery.qrcode.min.js","common/caculate.js"]
                                  title="${title!((product.pName)!'')}" keywords="${keywords!''}" description="${description!''}">

<#include "/common/front/work_platform.ftl" />

	<header>
		<div class="head clearfix">
		    <a href="${memberServer}/home">
	  	        <div class="logo pull-left"></div>
	        </a>
            <div class="proHeadNavBlock pull-right">
                <a <#if prePid??>href="${basepath}/localproduct/front/detail/${prePid}"<#else>href="javascript:void(0);"</#if> title="上一个" class="commonIcon headLeftArrowIcon"></a>
                <a href="${memberServer}/home" title="返回首页" class="commonIcon headHomeIcon"></a>
                <a href="${memberServer}/product/list" title="返回列表" class="commonIcon headListIcon"></a>
                <a <#if posPid??>href="${basepath}/localproduct/front/detail/${posPid}"<#else>href="javascript:void(0);"</#if> title="下一个" class="commonIcon headRightArrowIcon"></a>
            </div>
		</div>
	</header>


<div class="visa-contain">
    <div class="visa-title">${(product.pName)!''}</div>
    <div class="visa-content clearfix">
    
        <div class="content-wrap">
              
      <@mainContent.mainContent></@mainContent.mainContent>
		<input id="selectedDay" type="hidden" value="${(selectedDay)!''}">
		<input id="adultNum" type="hidden" value="${adultNum!''}">
		<input id="childNum" type="hidden" value="${childNum!''}">
		<input id="costPriceId" type="hidden" value="${costPriceId!''}">
            <div class="visa-navbar" id="navbar">
                <ul class="nav fixedList clearfix">
                    <li>
                        <a href="#productDetail">产品介绍</a>
                    </li>
                    <li>
                        <a href="#instructor">使用说明</a>
                    </li>
                    <li>
                        <a href="#matters">预订须知</a>
                    </li>
                    <li>
                        <a href="#feeInstructor">费用说明</a>
                    </li>
                    <li>
                        <a href="#userJudge">用户评价</a>
                    </li>
                    <li class="lastBar">
                        <a href="javascript:void(0);" class="fixOrder">立即预订</a>                        
                    </li>
                </ul>
            </div>
            

            <div class="visa-mainContent">

           <@productIntroduction.productIntroduction></@productIntroduction.productIntroduction>           
            <@useIntroduction.useIntroduction></@useIntroduction.useIntroduction>
            <@attention.attention></@attention.attention>
            <@priceExplain.priceExplain></@priceExplain.priceExplain>
            <@localEvaluation.localEvaluation></@localEvaluation.localEvaluation>             
            </div>
            
           </div>   
           <@priceCalendar.priceCalendar></@priceCalendar.priceCalendar>               
        </div>
            
    </div>
    
        <#include "/common/front/right_side.ftl"/>
        <#include "/common/front/header/footer.ftl"/>

</@html.htmlIndex>