<#if hotelList??>
<#list hotelList as hotel>
<tr>
    <td><#if hotel.hotelNameCn??>${hotel.hotelNameCn}</#if></td>
    <td><#if hotel.country??>${hotel.country}</#if></td>
    <td><#if hotel.cityOrAttraction??>${hotel.cityOrAttraction}</#if></td>
    <td><#if hotel.hotelType??>${hotel.hotelType}</#if></td>
    <td><#if hotel.rating??>${hotel.rating}</#if></td>
    <td><#if hotel.highLights??>${hotel.highLights}</#if></td>
    <td>
        <a href="javascript:addHotel('${hotel.id}');" class="greenFontsLink">选择</a>
    </td>
</tr>
</#list>
</#if>

<-split->

<#include "/product/common/pagination.ftl"/>