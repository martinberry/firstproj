<a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
    <span id="nation" class="activeFonts">不限</span>
    <span class="caret"></span>
</a>
<#if countryList??>
<ul class="dropdown-menu">
    <li class="active"><a href="javascript:void(0);">不限</a></li>
    <#list countryList as country>
    <li><a href="javascript:void(0);">${country!''}</a></li>
    </#list>
</ul>
</#if>