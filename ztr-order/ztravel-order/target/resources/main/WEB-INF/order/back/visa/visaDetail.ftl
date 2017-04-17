<#import "/common/opera/htmlIndex.ftl" as html/>
<@html.htmlIndex cssFiles=["css/jquery.tagsinput.css", "css/maintain/orderManagement.css","css/bootstrap-datepicker.min.css"]
                                  jsFiles=["js/vendor/jquery.tagsinput.js", "order/back/visa/visaDetail.js","js/vendor/bootstrap-datepicker.min.js","js/vendor/bootstrap-datepicker.zh-CN.min.js"]
                                  curModule="订单管理" title="签证订单-详情">
        <#include "visaorderDetailHeader.ftl" />

<div class="main-container changeMainContent">
<section class="whiteBg">

 <#include "visaoperationLog.ftl" />

<div class="moduleContent">
    <table class="noBorderTabStyle">
        <colgroup>
            <col width="90">
            <col width="200">
            <col width="90">
            <col width="200">
            <col width="">
        </colgroup>
        <input type="hidden" name="orderId" id="orderId" value="${(visaDetail.orderId)!''}" />
        <thead>
        <tr>
            <th colspan="4">
                <label class="orderFonts">
                    <span class="orderNumFonts">订单号</span>
                    <span>${(visaDetail.orderNo)!''}</span>
                </label>
            </th>
            <th class="textAlignRight">
                <label class="statusFonts">
                    <span>当前状态：</span>
                    <span class="orangeFonts" id="visaStatus" value="${(visaDetail.statusEnum)!''}">${(visaDetail.status)!''}</span>
                </label>
            </th>
        </tr>
        </thead>
        <tbody>

        <tr class="first">
            <td class="trTitle">产品名称:</td>

            <#if (visaDetail.products) ??>
            <#list (visaDetail.products) as product>
            <td class="visaType" value="${(product.productTitle)!''}">${(product.productTitle)!''}</td>
            </#list>
            </#if>
            <td colspan="3" class="textAlignRight">
                         <span class="orderSpan">
                          <a href="javascript:;" class="cancelOrder ac-cancelOrder" id="cancelOrderOperaBlock">取消订单</a>
                           <button class="commonButton blue-45c8dcButton ac-OPSure opSure" style="display:none;">OP确认</button>
                           <button class="commonButton blue-45c8dcButton makedbutton" style="display:none;">制作完成</button>
                           <button class="commonButton blue-45c8dcButton materialsendbutton" style="display:none;">材料送回</button>                         
                          </span>
            </td>
        </tr>

        <tr>
            <td class="trTitle">购买类型:</td>
              <#if (visaDetail.products) ??>
            <#list (visaDetail.products) as product>
            <td>${(product.costPriceName)!''}</td>
            </#list>
            </#if>
            <td colspan="3"></td>
        </tr>
        <tr>
            <td class="trTitle">出游日期:</td>
               <#if (visaDetail.products) ??>
            <#list (visaDetail.products) as product>
            <td>${(product.departureDate)!''}</td>
            </#list>
            <#else>
             <td></td>
            </#if>
            <td>下单会员:</td>
            <td>${(visaDetail.creatorMid)!''}</td>
            <td></td>
        </tr>
        <tr class="last">
            <td class="trTitle">需求备注:</td>
            <td  colspan="4">${(visaDetail.requirementNotes)!''}</td>
        </tr>
        </tbody>
    </table>
</div>

<#include "visacontactorInfo.ftl" />
<#include "visapriceInfo.ftl" />
<#include "visaremarkerInfo.ftl" />

</section>
<#include "visamodal.ftl" />
</div>
</@html.htmlIndex>



