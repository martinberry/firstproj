<#if searchList??>
<#assign chosen_non_flag = true/>
<#list searchList as item>
    <li class="menu-item">
        <input type='hidden' name='pkgId' value="${item.pkgId!}">
        <input type='hidden' name='sumNum' value="${item.sumNum!}">
        <#if item.sumNum?? && stock?? && item.sumNum gt stock>
        <div class="menu-info clearfix disabled">
            <label class="fl disabled">
                <input type="radio" name="menu-type" value="0" data-role="none" disabled>
                <span class="menu-name">${item.name!}</span>
            </label>
            <span class="menu-price fr">¥${item.price!}</span>
        </div>
        <div class="menu-short-desc">${item.desc!}</div>
        <#else>
            <#if chosen_non_flag>
            <#assign chosen_non_flag = false />
            <div class="menu-info clearfix">
                <label class="fl">
                    <input type="radio" name="menu-type" value="0" checked data-role="none">
                    <span class="menu-name">${item.name!}</span>
                </label>
                <span class="menu-price fr">¥${item.price!}</span>
            </div>
            <div class="menu-short-desc" style="display: block;">${item.desc!}</div>
            <#else>
            <div class="menu-info clearfix">
                <label class="fl">
                    <input type="radio" name="menu-type" value="0" data-role="none">
                    <span class="menu-name">${item.name!}</span>
                </label>
                <span class="menu-price fr">¥${item.price!}</span>
            </div>
            <div class="menu-short-desc">${item.desc!}</div>
            </#if>
        </#if>
    </li>
</#list>
</#if>