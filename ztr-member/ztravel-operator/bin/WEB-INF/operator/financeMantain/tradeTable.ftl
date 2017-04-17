  <#if searchList??>
    <#list searchList as item>
    <tr>
        <td>${item.memberName!}</td>
       <td>${item.mid!}</td>
         <td><a href="${basepath}/order/freetravel/detail/${item.orderId!}" class="greenFontsLink">${item.orderNo!}</a></td>
        <td>${item.productType!}</td>
        <td class="fs14">${item.tradeDate!}</td>
        <td><span class="fcf78c68">${item.orderAmount!}</span></td>
        <td>${item.tradeAmount!}</td>
        <td> ${item.paymentType!} </td>
        <td> ${item.tradeType!} </td>
        <td> ${item.tradeStatus!}</td>
        <td>${item.orderType}</td>
        <td>${item.traceNum!}</td>
    </tr>
    </#list>
  </#if>
       <-split->
    </tbody>
</table>
<#include "/common/opera/pagination.ftl"/>
