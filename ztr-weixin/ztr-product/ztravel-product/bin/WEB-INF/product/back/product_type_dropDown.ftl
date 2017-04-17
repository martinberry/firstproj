  <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
        <span class="activeFonts" id="subject">全部</span>
        <span class="caret"></span>
    </a>
    <#if typeList??>
	    <ul class="dropdown-menu">
	        <li class="active"><a href="javascript:void(0);">全部</a></li>
	        <#list typeList as type>
	        <li><a href="javascript:void(0);">${type!}</a></li>
	        </#list>
	    </ul>
    </#if>
