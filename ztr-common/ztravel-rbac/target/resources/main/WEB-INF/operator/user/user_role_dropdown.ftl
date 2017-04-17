        <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
            <span class="activeFonts role" roleId="none">角色选择</span>
            <span class="caret"></span>
        </a>
        <#if roles??>
        <ul class="dropdown-menu" id="secondMenu">
            <li roleId="none"><a href="javascript:void(0);">角色选择</a></li>
            <#list roles as role>
            <li roleId="${role.roleId}"><a href="javascript:void(0);">${role.roleName}</a></li>
            </#list>
        </ul>
        </#if>