 <#if commentVoList??>
 <#list commentVoList as tcVo>
 <tr>
        <td>${(tcVo.nickName)!''}</td>
        <td>${(tcVo.commentTime)!''}</td>       
        <td>${(tcVo.praiseNum)!''}</td>
        <td>${(tcVo.commentDetail)!''}</td>        
        <td class="delete"><a class="greenFontsLink edit" href="javascript:void(0);">删除</a></td>
        <input type="hidden" name="commentId" class="commentId" value=${(tcVo.commentId)!''} />
</tr>
</#list>
</#if>
                    
<-split->

<#include "/common/opera/pagination.ftl" />