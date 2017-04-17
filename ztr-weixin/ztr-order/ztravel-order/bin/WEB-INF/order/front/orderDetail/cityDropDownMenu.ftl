
<#if cityList??>
    <#list cityList as city>
    <li><a href="javascript:void(0);">${city.areaName!''}</a></li>
    </#list>
</#if>