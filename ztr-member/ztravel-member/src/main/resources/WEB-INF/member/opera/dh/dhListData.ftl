<#if DHList??>
<#list DHList as dh>
    <tr>
        <td>${(dh.dhId)!''}</td>
        <td>${(dh.memberId)!''}</td>
        <td>${(dh.pledhTime)!''}</td>
        <td><div class="${(dh.dhId)!''}">${(dh.statusDesc)!''}</div></td>
        <td class="orangefa7f1f">${((dh.dhMoney)/100)!''}</td>
         <td>${(dh.contentDesc)!''}</td>


         <td class="text-center">
         <#if dh.dhStatus=="UNCONVERT">
          <a href="javascript:void(0);"   class="confirmConvert ${(dh.dhId)!''}_confirm" data-id="${(dh.dhId)!''}" >确认兑换</a>
           <a href="javascript:void(0);"  class="look ${(dh.dhId)!''}_view" data-id="${(dh.dhId)!''}" style="display:none;" >查看</a>
          <#elseif dh.dhStatus=="CONVERTED">
           <a href="javascript:void(0);"   class="confirmConvert ${(dh.dhId)!''}_confirm" data-id="${(dh.dhId)!''}" style="display:none;" >确认兑换</a>
            <a href="javascript:void(0);"  class="look ${(dh.dhId)!''}_view" data-id="${(dh.dhId)!''}">查看</a>
            <#else>
        </#if>
        </td>
           <td class="${(dh.dhId)!''}_operator">${(dh.lastOperator)!''}</td>
    </tr>
</#list>
</#if>

<-split->
<#include "/common/opera/pagination.ftl" />




