<#macro userHeader currentMenu="">
	<header>
		<div class="head clearfix">
			<div class="logo"></div>
            <div class="top-nav">
                <ul class="top-nav-list clearfix">
                    <li class="top-nav-item <#if currentMenu='个人中心'>current</#if>" onClick="window.location.href='${basepath}/member/info'">个人中心</li>
                    <li class="top-nav-item <#if currentMenu='我的订单'>current</#if>" onClick="window.location.href='${basepath}/order/front/list'">我的订单</li>
                    <li class="top-nav-item <#if currentMenu='心愿清单'>current</#if>" onClick="window.location.href='${basepath}/member/wish/list'">心愿清单</li>
                    <li class="top-nav-item <#if currentMenu='电子钱包'>current</#if>" onClick="window.location.href='${basepath}/electronicWallet/coupon/index'">电子钱包</li>
                    <li class="top-nav-item <#if currentMenu='会员信息'>current</#if>">会员信息</li>
                    <li class="top-nav-item <#if currentMenu='网站消息'>current</#if>" onClick="window.location.href='${basepath}/systemnotice/list'">网站消息</li>
                </ul>
            </div>
		</div>
	</header>
</#macro>