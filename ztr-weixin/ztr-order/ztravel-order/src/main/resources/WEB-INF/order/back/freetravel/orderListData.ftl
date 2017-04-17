<#if orderList??>
<#list orderList as order>
    <tr>
        <td>${(order.status)!''}</td>
        <td>${(order.orderNo)!''}</td>
        <td>${(order.productNo)!''}</td>
        <td><div class="product-name">${(order.productTitle)!''}</div></td>
        <td style="max-width:183px;">${(order.packageName)!''}</td>
        <td>${(order.createDate)!''}</td>
        <td>${(order.travellerNames)!''}</td>
        <td class="orangefa7f1f">${(order.orderTotalPrice)!''}</td>
        <td>${(order.source)!''}</td>
        <td class="text-center">
            <a href="${basepath}/order/freetravel/detail/${(order.orderId)!''}">查看</a>
        </td>
        <td>${(order.commonOrderStatus)!''}</td>
    </tr>
</#list>
</#if>

<-split->

<#include "/common/opera/pagination.ftl" />

