<#if roles??>
    <#list roles as role>
    <tr>
    <input type="hidden" id="roleId" value="${(role.roleId)!}" />
        <td>${(role.roleName)!}</td>
        <td>${(role.count)!}</td>
        <td><a href="${basepath}/rbac/role/copy/${(role.roleId)!}" class="greenFontsLink">复制</a>
               <a href="${basepath}/rbac/role/edit/${(role.roleId)!}" class="greenFontsLink">编辑</a>
               <a href="javascript:void(0);" class="greenFontsLink deleRole">删除</a></td>
    </tr>
    </#list>
</#if>


<-split->

<#include "/common/opera/pagination.ftl"/>