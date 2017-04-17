<#if (contactor)??>
<input type="hidden" name="contactorIdTd" value="${(contactor.id)!}">
<tr>
    <td id="contactorTd">${contactor.contactor!}</td>
    <td id="phoneTd">${contactor.phone!}</td>
    <td id="emailTd">${contactor.email!}</td>
    <td><span id="provinceTd">${contactor.province!}</span>
    <span id="cityTd">${contactor.city!}</span>
    <span id="countyTd">${contactor.county!}</span>
    <span id="addressTd">${contactor.address!}</span></td>
</tr>
</#if>