<a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false">
    <span id="nation" class="activeFonts"></span>
    <span class="caret"></span>
</a>
<#if nationList??>
<ul class="dropdown-menu">
     <#list nationList as nation>
     <#if nation??><li><a href="javascript:void(0);">${nation}</a></li></#if>
     </#list>
</ul>
</#if>