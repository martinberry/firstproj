<#if CoList??>
<#list CoList as order>
    <tr>
        <td class="orderId">${(order.orderId)!''}</td>
        <td class="orderNoOrigin"><a href="${basepath}/order/freetravel/detail/${(order.orderIdOrigin)!''}">${(order.orderNoOrigin)!''}</a></td>
        <td class="orderOriginStatus">${(order.orderOriginStatus)!''}</td>
        <td class="createDate">${(order.createDate)!''}</td>
        <td class="travllerNames">${(order.travellerNames)!''}</td>
        <#if (order.price)??>
        <td class="price">${((order.price)/100)?string('0.00')}</td>
        <#else>
        <td class="price">0.00</td>
        </#if>
        <td class="status">${(order.status)!''}</td>
        <#if (order.orderType)=='OP_CONFIRM_REFUND'>
        <td class="text-center operation">
        <#if (order.status)?? &&(order.status) =="待审核">
        <a href="javascript:void(0);" class="PassBtn able">通过</a>
        <a href="javascript:void(0);" class="notPassBtn able">未通过</a>
        <#else>
         -- --
        </#if>
        </td>
        <td class="remark">${(order.remark)!''}</td>
        <#else>
        </#if>
        <td class="statechangehistory">${(order.stateChangeHistory)!''}</td>
    </tr>
</#list>
</#if>

<-split->

<#include "/common/opera/pagination.ftl" />

