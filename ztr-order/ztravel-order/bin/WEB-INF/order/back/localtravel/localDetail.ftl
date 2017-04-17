<#import "/common/opera/htmlIndex.ftl" as html/>
<@html.htmlIndex cssFiles=["css/jquery.tagsinput.css", "css/maintain/orderManagement.css","css/bootstrap-datepicker.min.css"]
                                  jsFiles=["js/vendor/jquery.tagsinput.js", "order/back/localTravel/localDetail.js","js/vendor/bootstrap-datepicker.min.js","js/vendor/bootstrap-datepicker.zh-CN.min.js"]
                                  curModule="订单管理" title="当地游订单-详情">
        <#include "/order/back/localtravel/localDetailHeader.ftl" />

<div class="main-container changeMainContent">
<section class="whiteBg">

 <#include "/order/back/localtravel/localoperationLog.ftl" />

<div class="moduleContent">
    <table class="noBorderTabStyle">
        <colgroup>
            <col width="90">
            <col width="200">
            <col width="90">
            <col width="200">
            <col width="">
        </colgroup>
        <input type="hidden" name="orderId" id="orderId" value="${(localDetail.orderId)!''}" />
        <thead>
        <tr>
            <th colspan="4">
                <label class="orderFonts">
                    <span class="orderNumFonts">订单号</span>
                    <span>${(localDetail.orderNo)!''}</span>
                </label>
            </th>
            <th class="textAlignRight">
                <label class="statusFonts">
                    <span>当前状态：</span>
                    <span class="orangeFonts" id="localStatus" value="${(localDetail.statusEnum)!''}">${(localDetail.status)!''}</span>
                </label>
            </th>
        </tr>
        </thead>
        <tbody>

        <tr class="first">
            <td class="trTitle">产品名称:</td>

            <#if (localDetail.products) ??>
            <#list (localDetail.products) as product>
            <td class="visaType" value="${(product.productTitle)!''}">${(product.productTitle)!''}</td>
            </#list>
            </#if>
            <td colspan="3" class="textAlignRight">
                        

                           <span class="orderSpan">
                            <a href="javascript:;" class="cancelOrder ac-cancelOrder">取消订单</a>
                                    <button class="commonButton blue-45c8dcButton ac-OPSure" style="display:none;">OP确认</button>
                          </span>                         
            </td>
        </tr>

        <tr>
            <td class="trTitle">购买类型:</td>
              <#if (localDetail.products) ??>
            <#list (localDetail.products) as product>
            <td>${(product.costPriceName)!''}</td>
            </#list>
            </#if>
            <td colspan="3"></td>
        </tr>
        <tr>
            <td class="trTitle">出游日期:</td>
               <#if (localDetail.products) ??>
            <#list (localDetail.products) as product>
            <td>${(product.departureDate)!''}</td>
            </#list>
            <#else>
             <td></td>
            </#if>
            <td>下单会员:</td>
            <td>${(localDetail.creatorMid)!''}</td>
            <td></td>
        </tr>
        <tr class="last">
            <td class="trTitle">需求备注:</td>
            <td  colspan="4">${(localDetail.requirementNotes)!''}</td>
        </tr>
        </tbody>
    </table>
</div>

 <#include "/order/back/localtravel/localcontactorInfo.ftl" />
<#include "/order/back/localtravel/localtravellerInfo.ftl" />
<#include "/order/back/localtravel/localpriceInfo.ftl" />
<#include "/order/back/localtravel/localremarkerInfo.ftl" />

</section>
<#include "/order/back/localtravel/localmodal.ftl" />
</div>

</@html.htmlIndex>



