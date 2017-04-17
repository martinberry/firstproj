<#import "/common/opera/htmlIndex.ftl" as html/>

<@html.htmlIndex cssFiles=["css/bootstrap-datepicker.min.css",
                                "css/jquery.tagsinput.css",
                                "css/client/common.css",
                                "css/client/flight_logo.css",
                                "css/client/orderInfo.css",
                                "css/maintain/orderManagement.css",
                                "css/maintain/productManagement.css",
                                "css/bootstrap-datepicker.min.css"]
                                jsFiles=["js/vendor/bootstrap-datepicker.min.js",
                                "js/vendor/bootstrap-datepicker.zh-CN.min.js",
                                "js/vendor/jquery.tagsinput.js",
                                "js/vendor/jquery.tmpl.js",
                                "js/vendor/kindeditor.js",
                                "common/jquery-form.js",
                                "common/pagination.js",
                                "order/back/freetravel/travelConfirm/mainSheet.js",
                                "order/back/freetravel/travelConfirm/contactorSheet.js",
                                "order/back/freetravel/travelConfirm/travellerSheet.js",
                                "order/back/freetravel/travelConfirm/flightSheet.js",
                                "order/back/freetravel/travelConfirm/hotelSheet.js",
                                  "order/back/freetravel/travelConfirm/additionalSheet.js",
                                  "order/back/freetravel/travelConfirm/costDescriptionSheet.js",
                                  "order/back/freetravel/travelConfirm/feesDetailSheet.js",
                                  "order/back/freetravel/travelConfirm/otherSheet.js",
                                  "order/back/freetravel/travelConfirm/validation.js",
                                  "common/typeahead.js"] curModule="订单管理" title="真旅行 订单管理 OP确认单查看">
    <#include "/order/back/freetravel/travelConfirm/confirmSheetHeader.ftl" />

    <input type="hidden" id="orderId" value="${orderDetail.order.orderId}">

    <div class="main-container changeBg" style="margin-top: 110px;">
    <div class="main-wrapper" id="main-wrapper" style="margin-bottom: 20px;">
        <section class="onLinePayContent">
            <div class="top-border"></div>
             <div class="commonOrderModel">
                <i class="left-semicircle"></i>
                <i class="right-semicircle"></i>
                <div class="bigTitleModel">行程确认单</div>
            </div>
            <div class="orderNoBorderTab">
                <table>
                    <colgroup>
                        <col width="13%">
                        <col width="27%">
                        <col width="10%">
                        <col width="24%">
                        <col width="10%">
                        <col width="16%">
                    </colgroup>
                    <tbody>
                        <tr>
                            <th>订单号:</th>
                            <td>${orderDetail.order.orderNo!}</td>
                            <th>下单日期:</th>
                            <td>${orderDetail.order.createDate!}</td>
                            <th>出游日期:</th>
                            <td>${orderDetail.product.bookDate!}</td>
                        </tr>
                        <tr>
                            <th>产品标题:</th>
                            <td>${orderDetail.product.productTitle!}</td>
                            <#if (orderDetail.product.packageName)??>
                            <th>套餐名称:</th>
                            <td>${orderDetail.product.packageName!}</td>
                            </#if>
                            <th> </th>
                            <td> </td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <#include "/order/back/freetravel/travelConfirm/contactorSheet.ftl" />
            <#include "/order/back/freetravel/travelConfirm/travellerSheet.ftl" />
            <#include "/order/back/freetravel/travelConfirm/flightSheet.ftl" />
            <#include "/order/back/freetravel/travelConfirm/hotelSheet.ftl" />
            <#include "/order/back/freetravel/travelConfirm/additionalSheet.ftl" />
            <#include "/order/back/freetravel/travelConfirm/costDescriptionSheet.ftl" />
            <#include "/order/back/freetravel/travelConfirm/feesDetailSheet.ftl" />
            <#include "/order/back/freetravel/travelConfirm/otherSheet.ftl" />

        </section>
    </div>
</div>

<!--OP受理完成对话框-->
<div class="modal fade commonInitialize" id="opAcceptDlg">
    <div class="modal-dialog" style="width: 500px;height:185px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="closeOpAcceptBtn">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">操作确认</h4>
            </div>
            <div class="modal-body">
                <div class="popupContainer">
                    <i class="warnIcon"></i>
                    <span class="popupContainer-fonts">是否完成op受理操作？</span>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="commonButton red-fe6869Button" data-dismiss="modal" id="opAcceptBtn">确 认</button>
                <button type="button" class="commonButton blue-45c8dcButton" data-dismiss="modal" id="cancelOpAcceptBtn">取 消</button>
            </div>
        </div>
    </div>
</div>

<!--查看行程确认单发送邮件对话框-->
<div class="modal fade commonInitialize" id="sendEmailDlg">
    <div class="modal-dialog" style="width: 500px;height:185px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="closeSendEmailBtn">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">操作确认</h4>
            </div>
            <div class="modal-body">
                <div class="popupContainer">
                    <i class="warnIcon"></i>
                    <span class="popupContainer-fonts">是否确认发送邮件操作？</span>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="commonButton red-fe6869Button" data-dismiss="modal" id="confirmSendBtn">确 认</button>
                <button type="button" class="commonButton blue-45c8dcButton" data-dismiss="modal" id="cancelSendBtn">取 消</button>
            </div>
        </div>
    </div>
</div>

</@html.htmlIndex>