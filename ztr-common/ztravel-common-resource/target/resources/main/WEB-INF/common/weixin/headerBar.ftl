<#macro headerBar title="">
<div class="headerBar">
	<div class="header">
        <a data-role="none" class="header-logo" href="javascript:jump2List();"><span class="logo"></span></a>
		<#include "navigate.ftl"/>
        <span class="title">${(title)!}</span>
	</div>
</div>
<script>
    function jump2List(){
        window.location.href = wxServer + "/weixin/product/list" ;
    }
</script>
</#macro>