 <#if dataList ?? >
 	<#list dataList as item>
		<tr>
		    <td name="voucherCode">${item.voucherCode!}</td>
		    <td name="voucherOrderId">${item.voucherOrderId!}</td>
		    <td>${item.memberId!}</td>
		    <td><#if item.payType?? ><#if item.payType=='Alipay'>支付宝<#elseif item.payType=='WeChatpay'>微信</#if></#if></td>
		    <td>
		    	<#if item.payAmount ??>
		    	${item.payAmount/100}
		    	</#if>
		    </td>
		    <td>${(item.payTimeStr)!}</td>
		    <input type="hidden" name = "tabPayTypeHide" value="${item.payType!}">
		    <td><#if item.payType?? ><#if item.payType=='Alipay'>支付宝<#elseif item.payType=='WeChatpay'>微信</#if></#if></td>
		    <td>
		    	<#if item.payAmount ??>
		    	${item.payAmount/100}
		    	</#if>
		    </td>
<td>${(item.prefundTimeStr)!}</td>
			<td>
			    <#if item.status ??>
			    	<#if item.status == 'AUDITING'>
			       		 <a class="blueFontsLink" href="javascript:;"name="auditedBtn">通过审核</a>
		       		 <#elseif item.status== 'REFUNDING'>
		       		  	<a class="blueFontsLink disabled" href="javascript:;">审核通过(退款中)</a>
		        	</#if>
		        </#if>
		    </td>
		</tr>
	</#list>
</#if>


<-split->

    </tbody>

</table>
<#include "/common/opera/pagination.ftl"/>