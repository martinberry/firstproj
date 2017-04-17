<#if members??>
<#list members as member>
<tr>
    <td>${(member.mId)!}</td>
    <td>${(member.nickName)!}</td>
    <td>${(member.realName)!}</td>
    <td>${(member.mobile)!}</td>
    <td>${(member.province)!} ${(member.city)!}</td>
    <td>${(member.recentTime)!}</td>
</tr>
</#list>
</#if>


<-split->

<#include "/common/opera/pagination.ftl"/>