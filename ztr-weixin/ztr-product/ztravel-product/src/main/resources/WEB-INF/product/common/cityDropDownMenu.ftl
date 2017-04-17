<a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
    <span id="city" class="activeFonts">不限</span>
    <span class="caret"></span>
</a>
<#if cityList??>
<ul class="dropdown-menu">
    <li class="active"><a href="javascript:void(0);">不限</a></li>
    <#list cityList as city>
    <li><a href="javascript:void(0);">${city!''}</a></li>
    </#list>
</ul>
</#if>