
    <#if searchList??>
    <#list searchList as item>
    <tr>
      <#--  <#if item.travelerNameCn?? && item.travelerNameCn != ''><td>${item.travelerNameCn!}</td>
        <#else><td>${item.travelerNameEn!}</td></#if>
        -->
        <td>${(item.travelerNameCn)!}</td>
         <td>${(item.travelerNameEn)!}</td>
        <td><#if (item.travelType)??><#if item.travelType == 'ADULT'>成人<#elseif item.travelType == 'CHILD'>儿童</#if></#if></td>
        <td><#if (item.gender)??><#if item.gender=='MALE' || item.gender=='male'>男<#elseif item.gender== 'FEMALE' || item.gender=='female'>女</#if></#if></td>
        <td><#if (item.credentials)??><#if item.credentials[0].type == 'IDCARD'>身份证</#if><#if item.credentials[0].type == 'PASSPORT'>护照</#if><#if item.credentials[0].type == 'GANGAOPASS'>港澳通行证</#if></#if></td>
        <td><#if (item.credentials)??>${(item.credentials[0].number)!}</#if></td>
        <td>${(item.phoneNum)!}</td>
        <td>
            <input type="hidden"  value="${(item.travelerId)!}">
            <a href="javascript:;"  class="greenFontsLink" onclick="getDetail(this)">查看</a>
        </td>
    </tr>
    </#list>
  </#if>
                   <#if size==0>
                    <p style="color: #F71A3D;" class="no_data">暂无数据</p>
                    </#if>
