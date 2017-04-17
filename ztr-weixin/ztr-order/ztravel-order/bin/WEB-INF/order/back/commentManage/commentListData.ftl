<#if commentList??>
<#list commentList as comment>
    <tr>
        <td><div class="contentEllipsis">${(comment.content)!''}</div></td>
        <td><div class="titleEllipsis">${(comment.productTitle)!''}</div></td>
        <td>${(comment.mid)!''}</td>
        <td>${(comment.date)!''}</td>
        <td>${(comment.status)!''}</td>
        <td><a href="${basepath}/comment/detail/${(comment.id)!''}" class="greenFontsLink">查看</a></td>
    </tr>
</#list>
</#if>

<-split->

<#include "/common/opera/pagination.ftl" />