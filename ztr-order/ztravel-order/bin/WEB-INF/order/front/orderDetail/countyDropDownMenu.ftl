
<#if countyList??>
    <#list countyList as county>
    <li><a href="javascript:void(0);">${county.areaName!''}</a></li>
    </#list>
</#if>