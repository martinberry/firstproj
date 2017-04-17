 <#if wtVoList??>
 <#list wtVoList as wtVo>

 <tr>
        <input type="hidden" name="topicId" class="topicId" value=${(wtVo.topicId)!''} />
        <td class="topicTitle">${(wtVo.topicTitle)!''}</td>
        <td class="productTitle">${(wtVo.productTitle)!''}</td>
        <#if (wtVo.giftTitle)??>
        <td>${(wtVo.giftTitle)!''}</td>
        <#else>
        <td>-- --</td>
        </#if>
        <td class="releasedTime">${(wtVo.releasedTime)!''}</td>
        <td>${(wtVo.discussNum)!''}</td>
        <td class="status">${(wtVo.status)!''}</td>
        <td>
            <a href="${basepath}/weixinTopic/topicDetail/${(wtVo.topicId)!''}" class="greenFontsLink">查看</a>
            <#if (wtVo.status)=="关闭">
            <a href="javascript:void(0);" class="greenFontsLink released">上线</a>
            <#elseif (wtVo.status)=="上线">
            <a href="javascript:void(0);" class="greenFontsLink offline">关闭</a>
            <#else>
            </#if>
            <a href="javascript:void(0);" class="greenFontsLink delete">删除</a>
        </td>
</tr>
</#list>
</#if>
                    
<-split->

<#include "/common/opera/pagination.ftl" />