<#if hotelList??>
<#list hotelList as hotel>
<tr>
    <td>${(hotel.hotelId)!}</td>
    <!-- <td><#noescape>${hotel.hotelNameCn?replace(" ","&nbsp;")}</#noescape></td> -->
    <td>${(hotel.hotelNameCn)!}</td>
    <td>${(hotel.country)!}</td>
    <td>${(hotel.cityOrAttraction)!}</td>
    <td>${(hotel.hotelType)!}</td>
    <td>${(hotel.rating)!}</td>
    <td>${(hotel.highLights)!}</td>
    <td>${(hotel.phoneNumber)!}</td>
    <td>${(hotel.status)!}</td>
    <td class="fs14">${(hotel.address)!}</td>
    <td>
        <a href="${basepath}/product/back/hotel/edit/${(hotel.id)!}" class="greenFontsLink">编辑</a>
        <a href="javascript:void(0);" class="greenFontsLink" onclick="deleteHotel('${(hotel.id)!}')">删除</a>
    </td>
</tr>
</#list>
</#if>

<-split->

<#include "/product/common/pagination.ftl"/>