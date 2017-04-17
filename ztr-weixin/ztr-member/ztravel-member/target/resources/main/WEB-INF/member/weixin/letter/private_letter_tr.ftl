<#if dataList??>
<#list dataList as letter>

<li class="privateLetterInfo <#if !(letter.hasRead)>current</#if>"  data-role="${(letter.anotherId)!}">
    <div class="leftPhoto">
        <img style="border-radius: 59px;" src="${mediaserver}imageservice?mediaImageId=${(letter.anotherHead)!}">
    </div>
    <div class="rightDetails">
        <div class="nameAndTime clearfix">
            <span class="nameFonts">${(letter.anotherNickname)!}</span>
            <span class="timeFonts">${(letter.updateTime)!}</span>
        </div>
        <div class="secondEllipsis">
            <p><#if (letter.msgContent)?? && (letter.msgContent)?length gt 30>
	        	${(letter.msgContent)?substring(0,30)}...
	        <#else>
		        ${(letter.msgContent)!}
	        </#if></p>
        </div>
    </div>
</li>
</#list>
</#if>

<script>
	$(function(){
		toDetail();
	})

	function toDetail(){
	$(".privateLetterInfo").on("tap",function(){
		var anotherId = $(this).attr("data-role");
		window.location.href = wxServer + "/privateletter/talk/"+anotherId ;
	})
}

</script>