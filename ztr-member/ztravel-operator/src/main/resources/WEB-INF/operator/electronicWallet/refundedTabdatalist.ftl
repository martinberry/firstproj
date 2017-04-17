 <#if dataList ?? >
 	<#list dataList as item>
		  <tr>
		    <td>${item.voucherCode!}</td>
		    <td>${item.voucherOrderId!}</td>
		    <td>${item.memberId!}</td>
		    <td>
		    	<#if item.payType?? >
		    		<#if item.payType =='Alipay'>支付宝<#elseif item.payType =='WeChatpay'>微信</#if>
	    		</#if>
	    	</td>
	    	<td>
		    	<#if item.payAmount ??>
		    	${item.payAmount/100}
		    	</#if>
		    </td>
		    <td>${(item.payTimeStr)!}</td>
		    <td>
		    	<#if item.payType ?? >
		    		<#if item.payType =='Alipay'>支付宝<#elseif item.payType =='WeChatpay'>微信</#if>
	    		</#if>
		    </td>
		     <td>
		     	<#if item.payAmount ??>
		    	${item.payAmount/100}
		    	</#if>
		     </td>
		    <td>${(item.refundTimeStr)!}</td>
		</tr>
	</#list>
</#if>

<-split->


    </tbody>

</table>
<#include "/common/opera/pagination.ftl"/>