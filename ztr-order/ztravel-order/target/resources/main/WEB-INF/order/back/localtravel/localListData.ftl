<#if localOrderList??>
<#list localOrderList as local>
    <tr>
        <td class="status">${(local.status)!''}</td>
        <td class="orderNo">${(local.orderNo)!''}</td>
        <td class="productTitle">${(local.productTitle)!''}</td>
        <td class="costPrice">${(local.costPrice)!''}</td>
        <td class="travellerNames">${(local.travellerNames)!''}</td>
        <td class="payTime">${(local.createDate)!''}</td>
        <td class="text-center"><a href="${basepath}/localorder/travel/detail/${(local.orderId)!''}">查看</a></td>
        
    </tr>
</#list>
</#if>

<-split->

<#include "/common/opera/pagination.ftl" />

