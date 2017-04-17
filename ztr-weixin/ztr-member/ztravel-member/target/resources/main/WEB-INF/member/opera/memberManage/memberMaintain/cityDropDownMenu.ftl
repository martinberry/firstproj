
<a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
    <span class="activeFonts">全部</span>
    <span class="caret"></span>
</a>
<#if cityList??>
<ul class="dropdown-menu">
    <li class="active"><a href="javascript:void(0);">全部</a></li>
    <#list cityList as city>
        <li><a href="javascript:void(0);">${city.areaName}</a></li>
    </#list>
</ul>
</#if>