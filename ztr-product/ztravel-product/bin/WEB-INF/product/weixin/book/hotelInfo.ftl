<#macro bookHotel>
<!-- 酒店信息开始 -->
<#if (productBookInfo.hotelList)?? && (productBookInfo.hotelList)?size gt 0>
    <div class="order-row">
        <div class="borderB">
            <i class="hotelIcon"></i>酒店信息
        </div>
    </div>
    <#list productBookInfo.hotelList as hotel>
    <div class="order-hotel-box">
        <div class="order-hotel-til">${(hotel.hotelName)!''}</div>
        <div class="ui-grid-b order-hotel-grid">
            <div class="ui-block-a order-hotel-left">
                <p><span class="order-lab03">入住</span></p>
                <p><span class="order-lab04">离店</span></p>
            </div>
            <div class="ui-block-b order-hotel-center">
                <p class="class-hotel-time">${(hotel.checkInDate)!''}</p>
                <p class="class-hotel-time01">${(hotel.checkOutDate)!''}</p>
            </div>
            <div class="ui-block-c order-hotel-right">
                <p class="order-hotel-txt">共<span class="hotel-num">${(hotel.tripNights)!''}</span>晚</p>
                <p class="order-hotel-name">${(hotel.roomType)!''}</p>
            </div>
        </div>
    </div>
    </#list>
</#if>
    <!-- 酒店信息结束 -->
</#macro>