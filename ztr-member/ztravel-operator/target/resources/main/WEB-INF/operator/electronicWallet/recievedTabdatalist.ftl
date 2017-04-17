 <#if dataList ?? >
	<#list dataList as item>
	  <tr>
	        <td>${item.voucherCode!}</td>
	        <td>${item.voucherOrderId!}</td>
	        <td>${item.mid!}</td>
	        <td>${item.mobile!}</td>
	        <td><#if item.payType?? ><#if item.payType=='Alipay'>支付宝<#elseif item.payType=='WeChatpay'>微信</#if></#if></td>
	        <td><#if item.payAmount??><#if item.payAmount gt 0>${item.payAmount/100}</#if></#if></td>
	        <td>
	         	<#if item.voucherType ??>
	       		        ${(item.payTimeStr)!}
		        </#if>
	  		</td>
	        <td>
	        	<#if item.status ??>
			    	<#if item.status == 'USED'>
			    		已消费
	       		    <#elseif item.status == 'RECIEVED'>
	       		    	未消费
			        </#if>
		        </#if>
	        </td>
	        <td>${item.orderNo!}</td>
	        <td>${item.orderMid!}</td>
	        <td>
	        	<#if item.voucherType=='NORMAL'>
			    		系统未占用
	    		<#else>
       		    	系统占用
		        </#if>
	        </td>
	    </tr>
    </#list>
</#if>

<-split->


    </tbody>

</table>
<#include "/common/opera/pagination.ftl"/>