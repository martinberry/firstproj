<a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false">
    <span id="city" class="activeFonts"></span>
    <span class="caret"></span>
</a>
<#if cityList??>
<ul class="dropdown-menu">
      <#list cityList as city>
      <#if city??><li><a href="javascript:void(0);">${city}</a></li></#if>
      </#list>
</ul>
</#if>