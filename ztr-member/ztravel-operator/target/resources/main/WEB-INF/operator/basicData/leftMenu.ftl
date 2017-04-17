<#macro leftMenu curLeftModule="">
    <aside class="leftMenu">  <!--leftMenu 开始-->
        <ul class="menuChange">
            <li <#if curLeftModule=="POI_INFO">class="current"</#if>>
                <a href="${basepath}/operation/basicData/poi">系统POI信息</a>
                <i class="rightArrow"></i>
            </li>
            <li <#if curLeftModule=="PRODUCT_PROPERTY">class="current"</#if>>
                <a href="${basepath}/operation/basicData/productProperty">产品属性</a>
                <i class="rightArrow"></i>
            </li>
            <li <#if curLeftModule=="MEMBER_INFO">class="current"</#if>>
                <a href="${basepath}/operation/basicData/memberInfo">用户信息数据</a>
                <i class="rightArrow"></i>
            </li>
            <li <#if curLeftModule=="HEAD_IMAGE">class="current"</#if>>
                <a href="${basepath}/operation/basicData/headImage">头像维护</a>
                <i class="rightArrow"></i>
            </li>
            <li <#if curLeftModule=="GAME_SETTINGS">class="current"</#if>>
                <a href="${basepath}/happy2016/getActivityAwardOptions">游戏设置</a>
                <i class="rightArrow"></i>
            </li>
        </ul>
    </aside> <!--leftMenu 结束-->
</#macro>