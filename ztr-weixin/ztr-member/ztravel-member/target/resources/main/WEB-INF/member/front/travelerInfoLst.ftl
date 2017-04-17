<#macro list infoList=[]>

    <#list infoList as travelInfo>
        <li 
	        <#if travelInfo.isDefault = "true"> 
	        	class="active"
			</#if> 
        >
            <span class="leftImg">
                <img src="${host}/images/client/avatar-01.jpg">
            </span>
            <span class="rightFonts">

            	<#if (travelInfo.travelerNameCn)?? >
	                <div>
	                    <span class="commonIcon userIcon"></span>
	                    <span>${travelInfo.travelerNameCn}</span>
	                </div>
				</#if> 
            	<#if (travelInfo.travelerNameEn)?? >  
	                <div>
	                    <span class="commonIcon ENIcon"></span>
	                    <span>${travelInfo.travelerNameEn}</span>
	                </div>
	            </#if> 
            	<#if (travelInfo.phoneNum)?? >  	               
	                <div>
	                    <span class="commonIcon phoneNumberIcon"></span>
	                    <span>${travelInfo.phoneNum}</span>
	                </div>
	            </#if>    
            </span>
            <span class="defaultFonts">默认</span>
            <div class="hoverContent">
                <span class="hoverBtn" id="${travelInfo.travelerId}">
                    <span class="commonIcon bigEditIcon"></span>
                    <span class="commonIcon bigDeleteIcon"></span>
                </span>
                <span class="defaultFonts">默认</span>
            </div>
        </li>   	
    </#list>    

</#macro>