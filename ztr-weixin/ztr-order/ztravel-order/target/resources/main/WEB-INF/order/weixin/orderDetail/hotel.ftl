<#macro orderHotel>
<!-- 订单酒店信息开始 -->
	<#if hotelInfoListVo??>
		<div class="row-til">
            <div class="borderB">
                <i class="hotelIcon"></i>酒店信息
            </div>
        </div>
	    <div class="order-hotel-box noborder">
	    	<#if (hotelInfoListVo.productHotelInfoVoLsit)?? && (hotelInfoListVo.productHotelInfoVoLsit)?size gt 0>
	    		<#list hotelInfoListVo.productHotelInfoVoLsit as productHotelInfoVo>
		        <div class="order-hotel-til">${(productHotelInfoVo.hotelName)!}</div>
		        <div class="ui-grid-b">
		            <div class="ui-block-a order-hotel-left">
		                <p><span class="order-lab03">入住</span></p>
		                <p><span class="order-lab04">离店</span></p>
		            </div>
		            <div class="ui-block-b order-hotel-center">
		                <p class="class-hotel-time">${(productHotelInfoVo.checkInDate)!}</p>
		                <p class="class-hotel-time01">${(productHotelInfoVo.checkOutDate)!}</p>
		            </div>
		            <div class="ui-block-c order-hotel-right">
		                <p class="order-hotel-txt">共<span class="hotel-num">${(productHotelInfoVo.tripNights)!}</span>晚</p>
		                <p class="order-hotel-name">${(productHotelInfoVo.roomType)!}</p>
		            </div>
		        </div>
		        </#list>
	        </#if>
	        
	        <div id="bedPreferContainer">
	        </div>
        </#if>
    </div>
    <!-- 订单酒店信息结束 -->
</#macro>