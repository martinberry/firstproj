<#import "/common/opera/htmlIndex.ftl" as html/>

<@html.htmlIndex cssFiles=["css/jquery.tagsinput.css", "css/maintain/orderManagement.css"]
                                  jsFiles=["js/vendor/jquery.tagsinput.js", "order/back/freetravel/originalOrder.js"] curModule="订单管理" title="订单-${(orderDetail.products[0].productTitle)!''}">
        <#include "/order/back/freetravel/orderDetailHeader.ftl" />
        <div class="main-container changeMainContent">
            <section class="whiteBg">
                <#include "/order/back/freetravel/operationLog.ftl" />
                <div class="moduleContent">
                    <table class="noBorderTabStyle">
                        <colgroup>
                            <col width="90">
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
                                <!--原订单不需要操作-->
                            </td>
                        </tr>
                        <tr>
                            <td class="trTitle">供应商：</td>
                            <td>${(orderDetail.supplier)!''}</td>
                            <td rowspan="2">
                                <div class="backFillMoneyBlock">
                                    <table class="backFillMoney">
                                        <tbody>
                                            <tr>
                                                <th>原订单金额:</th>
                                                <td>${(orderDetail.originalTotalPrice)!}</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </td>
                        </tr>
                        <tr class="last">
                            <td class="trTitle">需求备注：</td>
                            <td colspan="2">${(orderDetail.requirementNotes)!''}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <#include "/order/back/freetravel/contactorsInfo.ftl" />
                <#include "/order/back/freetravel/travellersInfo.ftl" />
                <#include "/order/back/freetravel/productInfo.ftl" />
                <#include "/order/back/freetravel/feesDetail.ftl" />
                <#include "/order/back/freetravel/paymentInfo.ftl" />
                <#--include "/order/back/freetravel/remark.ftl" /-->
            </section>
        </div>
        <#--<#include "/order/back/freetravel/modalDialogs.ftl" />-->
</@html.htmlIndex>
