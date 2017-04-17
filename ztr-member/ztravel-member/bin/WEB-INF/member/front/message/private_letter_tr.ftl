<#if dataList??>
<#list dataList as letter>
<div class="msg-column <#if !(letter.hasRead)>remind-column-bg</#if> clearfix">
    <div class="msg-left">
    	<img class="msg-img" src="${mediaserver}imageservice?mediaImageId=${(letter.anotherHead)!}">
    </div>
    <div class="msg-right">
    	<span class="remind-color2">${(letter.updateTime)!}</span>
        <p><span class="msg-del-icon" onclick="deleteLetter('${(letter.id)!}')"></span></p>
    </div>
    <div class="msg-center" onclick="window.location.href='${(basepath)!}/privateletter/talk/${(letter.anotherId)!}'">
        <p>${(letter.anotherNickname)!}<span class="msg-cnt-num"></span></p>
        <p>
        	<a href="javascript:void(0);">
		        ${(letter.msgContent)!}
	        </a>
        </p>
    </div>
</div>
</#list>
</#if>
<-split->

<#include "/product/common/pagination.ftl"/>