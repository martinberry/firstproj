<#if dataList??>
<#list dataList as msg>
<tr value="${(msg.id)!}">
    <td>
	    ${(msg.type)!}
	    <#if msg.hasRead == false><i class="hot-icon"></i></#if>
    </td>
    <td>${(msg.title)!}</td>
    <td><a href="${(msg.link)!}" target="_blank">${(msg.content)!}</a></td>
    <td>${(msg.time)!}</td>
    <td><a href="javascript:deleteOne('${(msg.id)!}');" class="greenFontsLink deleteNotice">删除</a></td>
</tr>
</#list>
</#if>
<-split->

<#include "/common/opera/pagination.ftl"/>