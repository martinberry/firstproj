<#import "/common/front/userHtmlBase2.ftl" as html />
<#import "local_content.ftl" as mainContent/>
<#import "local_calendar.ftl" as priceCalendar/>
<#import "local_product_description.ftl" as productIntroduction/>
<#import "local_use_description.ftl" as useIntroduction/>
<#import "local_attention.ftl" as attention/>
<#import "local_fee_detail.ftl" as priceExplain/>
<#import "local_evaluation.ftl" as localEvaluation/>


<@html.htmlIndex remoteJsFiles=["js/base/jquery-1.11.2.min.js", "js/bootdist/bootstrap.min.js", "js/vendor/bootstrap-datepicker.min.js",
								"js/vendor/bootstrap-datepicker.zh-CN.min.js", "js/global/global.js","js/maintain/jquery-ui.min.js","js/vendor/jquery.cycle.all.js",
								"js/client/common.js", "js/vendor/underscore.js","js/vendor/jquery.lazyload.min.js"]
								  localCssFiles=["member/front/round_image.css"]
                                  remoteCssFiles=["css/bootstrap.min.css", "css/client/bootstrap.reset.css", "css/common.css",
                                  "css/client/common.css", "css/client/flight_logo.css","css/client/visaInfo.css"]
                                  localJsFiles=["product/back/pieces/prevDetail/unvisa_prev_detail.js","common/jquery.qrcode.min.js"]
                                  title="${title!((product.pName)!'')}">

    <header>
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

<div class="visa-contain">
    <div class="visa-title">${(product.pName)!''}</div>
    <div class="visa-content clearfix">

        <div class="content-wrap">

      <@mainContent.mainContent></@mainContent.mainContent>

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