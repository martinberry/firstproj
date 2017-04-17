<#macro mainHeader currentMenu="">
	<header>
		<div class="head clearfix">
			<div class="logo"></div>
			<div class="top-right-block">
				<#if isPreview?? && isPreview>
				<#else>
				<#assign memberBean=getMember()>
				</#if>
                <div class="login-entrance" <#if (memberBean.isLogin)!false>style="display: none;"<#else>style="display: ;"</#if>>
                    <a class="login" href="javascript:;">登录</a><em>|</em><a class="register" href="javascript:;">注册</a>
                    <div class="login-box">
                        <div class="arrow"></div>
                    </div>
                </div>
                <div class="after-login" <#if (memberBean.isLogin)!false > style="display: ;" <#else> style="display: none;" </#if>>
                    <span class="userName">
                        <span class="commonIcon userIcon"></span>
                        <em>${(memberBean.nickName)!''}</em>
                    </span>
                    <div class="userInfo-box">
                        <div class="arrow"></div>
                        <div class="userInfo-block">
                            <div class="user-avatar">
                            	<#if (memberBean.imageId)?? && memberBean.imageId != ''>
                            		<img src="${mediaserver}imageservice?mediaImageId=${(memberBean.imageId)!}" style="width:95px;height:95px;" class="round_photo">
                            	</#if>
                                <div class="user-name">${(memberBean.nickName)!''}</div>
                            </div>
                            <div class="oper-btn">
                                <a class="userCenterBtn" href="javascript:void(0);">用户中心</a>
                                <a class="logoutBtn" href="javascript:void(0);">退出登录</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
			<div class="top-nav">
				<ul class="top-nav-list clearfix">
				    <li class="top-nav-item <#if currentMenu='目的地'>current</#if>">目的地</li>
				    <li class="top-nav-item <#if currentMenu='主题旅行'>current</#if>">主题旅行</li>
				    <li class="top-nav-item <#if currentMenu='特别推荐'>current</#if>">特别推荐</li>
				    <li class="top-nav-item <#if currentMenu='服务说明'>current</#if>">服务说明</li>
				    <li class="top-nav-item <#if currentMenu='真旅社区'>current</#if>">真旅社区</li>
				</ul>
			</div>
		</div>
	</header>
</#macro>