<#if dataList??>
<#list dataList as notice>
<div class="remind-column <#if !(notice.hasRead)>remind-column-bg</#if> clearfix">
    <div class="remind-left">
    	<span class="remind-icon <#if !(notice.hasRead)>pressed<#else>formal</#if>-icon${(notice.type.getIndex())!}"></span>
    	<input type="hidden" name="noticeId" value="${(notice.id)!}"/>
    	<#noescape>${(notice.content)!}</#noescape>
    </div>
    <div class="remind-right">
    	<span class="remind-color2">${(notice.dateTime).toString('yyyy-MM-dd HH:mm')}</span>
    </div>
</div>
</#list>
</#if>
<-split->

<#include "/product/common/pagination.ftl"/>