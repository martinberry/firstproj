<div class="moduleContent">
    <div class="titleContent clearfix">
        <h3 class="titleFonts blue2fb0c4">联系人信息</h3>
    </div>
    <table class="grayThead">
        <colgroup>
            <col width="80">
            <col width="110">
            <col width="190">
            <col width="270">
        </colgroup>
        <thead>
        <tr>
            <th>姓名</th>
            <th>电话</th>
            <th>邮箱</th>
            <th>地址</th>
        </tr>
        </thead>
        <tbody>
        <#if visaDetail ??>
        <#list visaDetail.contactors as contactor>
        <tr>
            <td class="fontSize16">${(contactor.name)!''}</td>
            <td>${(contactor.phone)!''}</td>
            <td class="fontSize16">${(contactor.email)!''}</td>
            <td>${(contactor.address)!''}</td>
        </tr>
        </#list>
        </#if>
        </tbody>
    </table>
</div>
