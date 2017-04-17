<#list products as product>
<input type="hidden" id="productId" value="${(product.productId)!}" />
<tr>
    <td class="contentEllipsis">${(product.pName)!}</td>
    <td>${(product.pid)!}</td>
    <td>${(product.toContinent)!}</td>
    <td>${(product.status)!}</td>
    <td>${(product.recentTime)!}</td>
    <td>
        <a href="${basepath}/member/opera/wish/detail/${(product.productId)!}" class="yellowFontLink">${(product.count)!}</a>
    </td>
</tr>
</#list>


<-split->

<#include "/common/opera/pagination.ftl"/>