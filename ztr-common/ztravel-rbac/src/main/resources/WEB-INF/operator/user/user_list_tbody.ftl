<#if users??>
    <#list users as user>
    <tr>
    <input type="hidden" id="userId" value="${(user.userId)!}" />
    <input type="hidden" id="mobile" value="${(user.mobile)!}" />
    <input type="hidden" id="email" value="${(user.email)!}" />
    <input type="hidden" id="roleId" value="${(user.role.roleId)!}" />
        <td>${(user.realName)!}</td>
        <td>${(user.employeeCode)!}</td>
        <td>${(user.userName)!}</td>
        <td>${(user.role.roleName)!}</td>
        <td>
        <!--<a href="javascript:void(0);" class="greenFontsLink offline"><#if (user.isActive)?? && user.isActive>挂起<#else>解挂</#if></a>-->
               <a href="javascript:void(0);" class="greenFontsLink edtLink">编辑</a>
               <a href="javascript:void(0);" class="greenFontsLink deleUser">删除</a></td>
    </tr>
    </#list>
</#if>

<-split->

<#include "/common/opera/pagination.ftl"/>