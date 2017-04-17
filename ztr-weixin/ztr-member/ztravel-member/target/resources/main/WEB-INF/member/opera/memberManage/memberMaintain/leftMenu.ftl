<#macro leftMenu curLeftModule="" memberId="">
	<div class="main-container clearfix">
            <aside class="leftMenu">  <!--leftMenu 开始-->
                <a href="${basepath}/member/memberMaintain/list" class="returnFonts">
                    <i class="returnIcon"></i>会员列表
                </a>
                <ul class="menuChange">
                    <li <#if curLeftModule=="memberDetail">class="current"</#if>>
                        <a href="${basepath}/member/memberMaintain/detail/${memberId}">基本详情</a>
                        <i class="rightArrow"></i>
                    </li>
                    <li <#if curLeftModule=="wishList">class="current"</#if>>
                        <a href="${basepath}/member/opera/wish/list/${memberId}">心愿清单</a>
                        <i class="rightArrow"></i>
                    </li>
                    <!--<li <#if curLeftModule=="memberOrder">class="current"</#if>>
                        <a href="javascript:void(0);">会员订单</a>
                        <i class="rightArrow"></i>
                    </li>-->
                    <li <#if curLeftModule=="travellerInfo">class="current"</#if>>
                        <a href="${basepath}/member/opera/traveller/list/${memberId}" id="traveller_list">常旅客信息</a>
                        <i class="rightArrow"></i>
                    </li>
                    <!--<li <#if curLeftModule=="contactorInfo">class="current"</#if>>
                        <a href="javascript:void(0);">联系人信息</a>
                        <i class="rightArrow"></i>
                    </li>-->
                </ul>
            </aside> <!--leftMenu 结束-->
            <input type="hidden" id="memberId" value="${memberId}" />
            <#nested/>
    </div>
</#macro>