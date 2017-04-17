<#if visaOrderList??>
<#list visaOrderList as visa>
    <tr>
        <td class="status">${(visa.status)!''}</td>
        <td class="orderNo">${(visa.orderNo)!''}</td>
        <td class="productTitle">${(visa.productTitle)!''}</td>
        <td class="costPrice">${(visa.costPrice)!''}</td>
        <td class="contactorName">${(visa.contactorName)!''}</td>
        <td class="payTime">${(visa.createDate)!''}</td>
        <td class="text-center"><a href="${basepath}/visa/detail/${(visa.orderId)!''}">查看</a></td>
        
    </tr>
</#list>
</#if>

<-split->

<#include "/common/opera/pagination.ftl" />

