 <#if dataList?? >
 	<#list dataList as item>
	   <tr>
	        <td name="voucherCode">${item.voucherCode!}</td>
	        <td name="voucherType"><#if item.voucherType ?? ><#if item.voucherType=='SYSTEM'>占用中<#else>非占用</#if></#if></td>
	        <td><a class="blueFontsLink <#if isActivityExpired || (activityStatus=='TERMINATED' || activityStatus == 'EDN')>disabled</#if>" href="javascript:;" name="systemOperat"><#if item.voucherType ?? ><#if item.voucherType=='SYSTEM'>取消占用<#else>系统占用 </#if></#if></a></td>
	    </tr>
    </#list>
</#if>

	<-split->


    </tbody>

</table>
<#include "/common/opera/pagination.ftl"/>
