
    <#if provinceList??>
        <#list provinceList as province>
        <#if province??><li><a href="javascript:void(0);">${province.areaName!}</a></li></#if>
        </#list>
    </#if>