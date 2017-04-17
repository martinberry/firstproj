  <#if searchList??>
    <#list searchList as item>
      <tr>
      <td>2016元旦活动</td>
      <td>${item.nickName!}</td>
      <td>
      <#if item.awardCode ?? >
	      <#if item.awardCode == '包' || item.awardCode == '台历'>
	          <p>地址：<#if item.address ?? >${item.address!}</#if></p>
	          <p>收件人：<#if item.addressee ?? >${item.addressee!}</#if></p>
	          <p>手机号码：<#if item.mobile ?? >${item.mobile!}</#if></p>
	        </#if>
	         <p>${item.awardCode!}</p>
        </#if>
      </td>
  </tr>
    </#list>
  </#if>
       <-split->
    </tbody>
</table>
<#include "/common/opera/pagination.ftl"/>




