<#import "/common/front/header/navHeader.ftl" as html/>

<@html.navHeader
	title="真旅行-填写订单"
	remoteCssFiles=[
				"css/bootstrap.min.css",
				"css/client/bootstrap.reset.css",
				"css/bootstrap-datepicker.min.css",
				"css/common.css",
				"css/client/common.css",
				"css/client/flight_logo.css",
			 	"css/client/userOrder.css",
			 	"css/client/navHeader.css",
			 	"css/client/workplatform.css"
			 		]
	remoteJsFiles=[
 				"js/vendor/bootstrap-datepicker.min.js",
    			"js/vendor/bootstrap-datepicker.zh-CN.min.js",
				"js/vendor/jquery.color-2.1.2.min.js",
    			"js/global/global.js",
   				"js/client/adjust.js",
   				"js/vendor/jquery.tmpl.js",
   				"js/client/workplatform.js"
					]

	localJsFiles = [
					"product/front/book/applyOrder.js",
					"product/front/book/bookLogin.js",
					"product/front/book/bookPrice.js",
					"product/front/book/bookProduct.js",
					"product/front/book/bookValidator.js",
					"product/front/book/bookPassenger.js",
					"product/front/book/bookInit.js",
					"product/front/book/caculate.js",
					"product/front/book/coupon.js",
					"product/front/book/typeahead.js",
					"product/front/book/tipsTemplate.js",
					"common/front/common_utils.js",
					"common/ZtrDropDown.js",
					"common/ChinesePY.js",
					"common/password_verify.js",
					"member/front/login.js",
					"product/front/book/contactor.js"
					]
	currentMenu="我的订单"
	>

	<script type="text/javascript">
		var isLogin = '${(wpfv.isLogin)?c}';
		var mediaServer = '${mediaserver!}';
    </script>
		<div class="main-wrapper" id="main-wrapper">
            <div class="orderFlowBar">
                <ul class="clearfix">
                    <li><i class="pastTenseIcon"></i><span class="pastTenseFonts">选择产品</span></li>
                    <li class="currentStatus"><i class="nowTenseIcon"><em>2</em></i><span class="nowTenseFonts">填写订单</span></li>
                    <li><i class="futureTenseIcon"><em>3</em></i><span class="futureTenseFonts">在线支付</span></li>
                    <li><i class="futureTenseIcon"><em>4</em></i><span class="futureTenseFonts">支付完成</span></li>
                </ul>
            </div>
            <div style="display:none">
            	<input type="hidden" id="isFirst" value="yes">
            	<input type="hidden" id="login" value="${(login)?c}">
            	<input type="hidden" id="firstImageId" value="${(productBookInfo.firstImageId)!}" />
            	<input type="hidden" id="productId" value="${(productBookInfo.productId)!}" />
                <input type="hidden" id="packageId" value="${(productBookInfo.packageId)!}" />
                <input type="hidden" id="packageName" value="${(productBookInfo.packageName)!}" />
            	<input type="hidden" id="tripDays" value="${(productBookInfo.tripDays)!}" />
            	<input type="hidden" id="productNo" value="${(productBookInfo.productNo)!}" />
            	<input type="hidden" id="productType" value="${(productBookInfo.productType)!}" />
            	<input type="hidden" id="productTitle" value="${(productBookInfo.productTitle)!}" />
             	<input type="hidden" id="bookDate" value="${(productBookInfo.departDay)!}" />
             	<input type="hidden" id="isDomestic" value="${(productBookInfo.isDomestic)!}" />
             	<input type="hidden" id="nature" value="${(productBookInfo.nature)!}" />
             	<input type="hidden" id="costPriceId" value="${(productBookInfo.costPriceId)!}" />
            </div>
            <div class="orderContent" style="margin-bottom: 50px;">
            <#include "/common/front/work_platform.ftl"/>
                <div class="left-orderContent">
                    <div class="top-border"></div>
                    <div class="box-container">
                     <div class="order-titleBlock clearfix">
                            <table class="order-title-table">
                                <colgroup>
                                    <col width="70">
                                    <col width="660">
                                    <col width="">
                                </colgroup>
                                <tbody>
                                    <tr>
                                        <td class="rightBorder">
                                            <a href="javascript:;" class="back-lineArrow">
                                                <i></i>
                                                <span>返回</span>
                                            </a>
                                        </td>
                                        <td class="main-cont">
                                            <div class="pro-basic-info">
                                                <h3>${productBookInfo.productTitle!""}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${(productBookInfo.costPriceName)!''}</h3>
                                                <h4>${productBookInfo.packageName!""}</h4>
                                                <table class="order-titleTable">
                                                <colgroup>
                                                    <col width="80"/>
                                                    <col width="120"/>
                                                    <col width="80"/>
                                                    <col width="120"/>
                                                </colgroup>
                                                <tbody>
                                                <tr>
                                                    <th>出行日期：</th>
                                                    <td><span>${productBookInfo.departDay!""}</span></td>
                                                    <#if (productBookInfo.nature)?? && ((productBookInfo.nature)=='PACKAGE'||(productBookInfo.nature)=='COMBINATION') >
	                                                    <th>回程日期：</th>
	                                                    <td><span>${productBookInfo.returnDay!""}</span></td>
                                                    </#if>
                                                </tr>
                                                </tbody>
                                                </table>
                                            </div>
                                        </td>
                                        <td>
	                                        <div class="person-count-block">
				                                <div class="adult-count">
				                                    <div class="count" id="orderAdultNum">${(productBookInfo.adultNum)!'1'}</div>
				                                    <div class="person-type">成人</div>
				                                </div>
				                                <div class="child-count">
				                                    <div class="count" id="orderChildNum">${(productBookInfo.childNum)!'1'}</div>
				                                    <div class="person-type">儿童</div>
				                                </div>
	                            			</div>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            <i class="left-semicircle"></i>
                            <i class="right-semicircle"></i>
                        </div>
						<#include "productFlight.ftl"/>
                        <#include "productHotel.ftl"/>
						<#include "passenger.ftl" />
						<div class="contactorInfo"></div>
						<#include "remark.ftl" />
						<div class="coupons"></div>
                        <div class="submitOrderBlock">
                            <div class="checkboxContent">
                                <label class="checkbox" id="agreeProtocol">
                                    <span class="commonIcon checkboxIcon order-protocol-btn"></span><span class="fc484848">我阅读并同意遵守</span>
                                </label>
                                <span id="readProBtn" style="font-size: 14px;color: #11b9b7; cursor:pointer">《真旅行产品预定协议书》</span>
                            </div>
                            <div class="buttonDiv">
                                <a href="javascript:void(0);" class="bigOrangeBtn checkUser" style="width:220px;">提交订单</a>
                            </div>
                        </div>

                    </div>
                </div>
                <#include "priceDetail.ftl" />
                <div class="adjustDiv" style="clear: both;"></div>
            </div>
		</div>

	<#include "checkUser.ftl" />
	<#include "tips.ftl" />
    <script type="text/javascript">
        $(function(){
            $(".top-nav-list").slideNav({
                fx: "swing",
                speed: 300,
                changeTextColor: "#fff"
            });

 //         $(".stoppingPoint.transfer").attr("title", $("#transfer_flight_template").html());

            $(".stoppingPoint.transfer").each(function() {
            	$(this).attr("title",$(this).parent(".adjustSpace").find(".transfer_flight_template").html());
            });

//            $(".stoppingPoint.stop").attr("title", $("#stop_flight_template").html());

            $(".green-border-tooltip").tooltip({
                html: true,
                placement: 'bottom',
                template: '<div class="tooltip green-border-tooltip" role="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>'
            });

            $(".right-orderPriceDetail").fixedBar({
                offsetY: 3,
                // endAt: ".orderContent"
            });

        });

    </script>


</@html.navHeader>

