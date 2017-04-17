<#if memberList??>
<#list memberList as mem>
<tr>
    <td>
        <span class="checkbox" name="${mem.id}"></span>
    </td>
    <td><#if mem.memberId??>${mem.memberId}</#if></td>
    <td><#if mem.nickName??>${mem.nickName}</#if></td>
    <td><#if mem.realName??>${mem.realName}</#if></td>
    <td><#if mem.mobile??>${mem.mobile}</#if></td>
    <td><#if mem.province??>${mem.province}</#if><#if mem.city??>${mem.city}</#if></td>
    <td>${mem.purchaseAmount}</td>
    <td>
        <a href="${basepath}/member/opera/wish/list/${(mem.id)!}" class="greenFontsLink">查看</a>
    </td>
    <td>
        <#if mem.openId?? && mem.openId != ''>是<#else>否</#if>
    </td>
    <td>
        <div><#if mem.latestLoginDate??>${mem.latestLoginDate}</#if></div>
        <div><#if mem.latestLoginTime??>${mem.latestLoginTime}</#if></div>
    </td>
    <td>
        <a href="${basepath}/member/memberMaintain/detail/${mem.id}" class="greenFontsLink">查看</a>
        <#if mem.isActive==true>
            <a href="javascript:void(0);" class="grayFontsLink" id="suspendOne">挂起</a>
        <#else>
            <a href="javascript:void(0);" class="grayFontsLink" id="unlockOne">解挂</a>
        </#if>
    </td>
</tr>
</#list>
</#if>

<-split->

<#include "/common/opera/pagination.ftl"/>
