<#macro header currentMenu="" currentSubMenu="">
   <header class="main-header">
            <div class="stairMenuContent">
                <div class="wrap clearfix">
                    <div class="navLeft">
                        <a class="logo" href="/"></a>
                        <ul class="stairMenu">
                        <#if getMenu()??>
                        <#list getMenu() as menu>
                            <li class="first-level-nav <#if (menu.permissionName)?? && currentMenu=menu.permissionName>currentStairMenu</#if>">
                                <a href="javascript:void(0);">${(menu.permissionName)!}</a>
                                <div class="second-level-nav">
                                    <ul class="secondLevelList clearfix">
                                    <#if (menu.permissions)??>
                                    <#list menu.permissions as subMenu>
                                        <#if (subMenu.permissionName)?? && subMenu.permissionName !="订单消息通知">
                                        <li <#if (subMenu.permissionName)?? && currentSubMenu=subMenu.permissionName>class="currentSecondLevelMenu"</#if>>
                                            <a href="${basepath}${(subMenu.url)!}">${(subMenu.permissionName)!}</a>
                                        </li>
                                        </#if>
                                    </#list>
                                    </#if>
                                    </ul>
                                </div>
                            </li>
                            </#list>
                            </#if>
                        </ul>
                    </div>
                    <#include "/common/opera/header_right.ftl" />
                </div>
            </div>

            <div class="headSecondLevel">
                <#nested/>
            </div>

        </header>
<script>
    $(function () {
	    $('.first-level-nav').each(function(){
	        $(this).children('a').attr("href",$(this).children('.second-level-nav').find('a').first().attr("href"));
		});
    });
</script>
</#macro>