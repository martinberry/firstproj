<#import "/common/opera/htmlIndex.ftl" as html/>

<@html.htmlIndex cssFiles=["css/jquery.tagsinput.css", "css/maintain/orderManagement.css"]
                                  jsFiles=["js/vendor/jquery.tagsinput.js", "order/back/freetravel/orderDetail.js"] curModule="订单管理" title="订单-${(orderDetail.products[0].productTitle)!''}">
        <#include "/order/back/freetravel/orderDetailHeader.ftl" />
        <div class="main-container changeMainContent">
            <section class="whiteBg">
                <#include "/order/back/freetravel/operationLog.ftl" />
                <div class="moduleContent">
                    <table class="noBorderTabStyle">
                        <colgroup>
                            <col width="110">
                            <col width="300">
                            <col width="">
                        </colgroup>
                        <thead>
                        <tr>
                            <th colspan="2">
                                <label class="orderFonts">
                                    <span class="orderNumFonts">订单号</span>
                                    <span>${(orderDetail.orderNo)!''}</span>
                                </label>
                            </th>
                            <th class="textAlignRight">
                                <label class="statusFonts">
                                    <span>当前状态：</span>
                                    <span class="orangeFonts" id="orderStatus" value="${(orderDetail.statusEnum)!''}">${(orderDetail.status)!''}</span>
                                </label>
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr class="first">
                            <td class="trTitle">订单类型：</td>
                            <td id="orderType" value="${(orderDetail.typeEnum)!''}">${(orderDetail.type)!''}</td>
                            <td class="textAlignRight">
                                <span class="orderSpan cancelOrderTd" id="cancelOrderOperaBlock" style="display:none;">
                                    <a href="javascript:void(0);" class="cancelOrder ac-cancelOrder" value="${(orderDetail.orderId)!''}" onclick="popupCancelOrderDialog(this)">取消订单</a>
                                </span>
                                <span class="orderSpan" id="OPconfirmOperaBlock" style="display:none;">
                                	<input type="hidden" id="commonOrderStatus" value="${(orderDetail.commonOrderStatus.toString())!}"/>
                                	<input type="hidden" id="commonOrderType" value="${(orderDetail.commonOrderType)!}"/>
                                    <a href="${(basepath)!}/order/travelConfirm/show/${(orderDetail.orderId)!}" class="travelConfirm" target="_blank">查看行程确认单</a>
                                    <a href="javascript:void(0);" class="cancelOrder ac-cancelOrder" value="${(orderDetail.orderId)!''}" onclick="popupCancelOrderDialog(this)">取消订单</a>
                                    <button class="commonButton blue-45c8dcButton" id="opAccept" onclick="javascript:window.location.href='${(basepath)!}/order/travelConfirm/edit/${(orderDetail.orderId)!}';" style="display:none;">OP 受理</button>
                                    <button class="commonButton blue-45c8dcButton ac-OPSure" id="opConfirm" value="${(orderDetail.orderId)!''}" style="display:none;">OP 确认</button>
                                </span>
                                <span class="orderSpan giftBoxNotesTd" id="giftSendOperaBlock" style="display: none; position: relative;">
                                    <a href="${(basepath)!}/order/travelConfirm/show/${(orderDetail.orderId)!}" class="travelConfirm" target="_blank">查看行程确认单</a>
                                    <span>礼盒发放须知</span>
                                    <input type="text" class="orderInput" placeholder="您的礼盒已经在途中，查询号是00544" id="sendGiftInputer">
                                    <div class="verifyStyle" style="width: 400px; display: none; left: 98px; text-align: left;">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts"></span>
                                    </div>
                                    <button class="commonButton blue-45c8dcButton ac-distributionBox" id="sendGift" value="${(orderDetail.orderId)!''}">发放礼盒</button>
                                </span>
                                <span class="orderSpan travelToInformTd" id="outNoticeOperaBlock" style="display: none; position: relative;">
                                    <a href="${(basepath)!}/order/travelConfirm/show/${(orderDetail.orderId)!}" class="travelConfirm" target="_blank">查看行程确认单</a>
                                    <span>出行通知须知</span>
                                    <input type="text" class="orderInput" placeholder="请在出发前备好您的相关证件，未来目的地的天气...." id="outingNoticeInputer">
                                    <div class="verifyStyle" style="width: 400px; display: none; left: 98px; text-align: left;">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts"></span>
                                    </div>
                                    <button class="commonButton blue-45c8dcButton ac-travelToInform" id="sendOutingNotice" value="${(orderDetail.orderId)!''}">出行通知</button>
                                </span>
                                <span class="orderSpan" id="canceledOperaBlock" style="display:none;">
                                	<#if (orderDetail.commonOrderStatus)?? || (orderDetail.commonOrderType)??>
                                    <a href="${(basepath)!}/order/travelConfirm/show/${(orderDetail.orderId)!}" class="travelConfirm" target="_blank">查看行程确认单</a>
                                    </#if>
                                </span>
                            </td>
                        </tr>
                        <tr>
                            <td class="trTitle">供应商：</td>
                            <td>${(orderDetail.supplier)!''}</td>
                            <td rowspan="2">
                            	<#if (orderDetail.commonOrderStatus)?? || (orderDetail.commonOrderType)??>
                                <div class="backFillMoneyBlock">
                                    <table class="backFillMoney">
                                        <tbody>
                                            <tr>
                                                <th><a href="${(basepath)!}/order/freetravel/originalOrder/${(orderDetail.orderId)!}" target="_blank">原订单</a>金额:</th>
                                                <td>${(orderDetail.originalTotalPrice)!}</td>
                                            </tr>
                                            <tr>
                                                <th>现订单金额:</th>
                                                <td>${(orderDetail.orderTotalPrice)!}</td>
                                            </tr>
                                            <tr>
                                                <th>差额:</th>
                                                <td>
                                                	<span id="minusedTotalPrice">${(orderDetail.minusedTotalPrice)!}</span>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <div class="status">
									  <div>
									  	<#if orderDetail.commonOrderType == "OP_CONFIRM_REPAIR">
									  	补款单&nbsp;&nbsp;
									  	<#elseif orderDetail.commonOrderType == "OP_CONFIRM_REFUND">
									  	退款单&nbsp;&nbsp;
									  	</#if>
									  	<#if (orderDetail.commonOrderStatus.toString())! == "INIT">
									  	待审核
									  	<#else>
									  	${(orderDetail.commonOrderStatus.getDescription())!}
									  	</#if>
									  </div>
									  <div class="remark">
										  <#if (orderDetail.commonOrderRemark)?? && orderDetail.commonOrderType == "OP_CONFIRM_REFUND">
										  ${(orderDetail.commonOrderRemark)!}
										  </#if>
									  </div>
									</div>
                                </div>
                                </#if>
                            </td>
                        </tr>
                        <tr>
                            <td class="trTitle">需求备注：</td>
                            <td colspan="2">${(orderDetail.requirementNotes)!''}</td>
                        </tr>
                        <tr class="last">
                            <td class="trTitle">建单会员ID：</td>
                            <td colspan="2">${(orderDetail.creatorMid)!''}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <#include "/order/back/freetravel/contactorsInfo.ftl" />
                <#include "/order/back/freetravel/travellersInfo.ftl" />
                <#include "/order/back/freetravel/productInfo.ftl" />
                <#include "/order/back/freetravel/feesDetail.ftl" />
                <#include "/order/back/freetravel/paymentInfo.ftl" />
                <#include "/order/back/freetravel/remark.ftl" />
            </section>
        </div>
        <#include "/order/back/freetravel/modalDialogs.ftl" />
</@html.htmlIndex>
