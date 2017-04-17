  <#if searchList??>
    <#list searchList as item>
	    <tr>
	    	<input type = "hidden" name = "memberId" value =${item.memberId!}>
	        <td>${item.memberName!}</td>
	        <td>${item.mid!}</td>
	        <td class ="accountType">${item.accountType!}</td>
	        <td>${item.amount!}</td>
	        <td>${item.availableAmount!}</td>
	        <td>${item.frozenAmount!}</td>
	        <td>${item.isAccountFrozen!}</td>
	        <td><a href="javascript:void(0);" class="greenFontsLink"><#if item.isAccountFrozen??><#if item.isAccountFrozen == '否'>冻结</#if><#if item.isAccountFrozen == '是'>解冻</#if></#if></a></td>
	    </tr>
    </#list>
  </#if>
       <-split->
    </tbody>
</table>
<#include "/common/opera/pagination.ftl"/>

