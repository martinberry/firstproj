<#import "/common/front/header/navHeader.ftl" as html/>
<@html.navHeader
title="真旅行-心愿清单"
currentMenu="心愿清单"
remoteJsFiles=[]
localCssFiles=["member/front/round_image.css"]
remoteCssFiles=["css/client/favorList.css"]
localJsFiles=["member/front/wish/wishList.js"]
>


		<div class="main-wrapper" id="main-wrapper" style="margin-bottom:50px;">
			<div class="main-box" id="main-box" style="top:30px;">
				<div class="top-border">
					<span class="clip"></span>
				</div>
				<div class="box-container">
					<div class="cont-block clearfix">
						<span class="modelLine"></span>
						<aside class="leftMenuContent">
							<ul>
								<li class="active">
									<a href="javascript:void(0);">旅行产品</a>
								</li>
								<!-- <li>
									<a href="常旅客信息.html">常用旅客信息</a>
								</li>
								<li>
									<a href="联系人信息.html">联系人信息</a>
								</li> -->
							</ul>
						</aside>
						<section class="rightContent favorList">
							<div class="sec-tab">
                                <span class="favorList-title">我的心愿单</span>
                                <span class="favorListNum">(${wishs?size})</span>
                                <!-- <span class="pickupBtn">选择头像</span> -->
                                <div class="slidline"></div>
                            </div>
                            <div class="travelpro">
                            	<#if (wishs??) && (wishs?size > 0)>
                            		<ul class="travelpro-list">
	                            	<#list wishs as wish>
	                            		<li>
	                            		    <input type="hidden" name="wishId" value="${(wish.id)!}">
	                            			<div class="product-images">
	                            				<a <#if (wish.status)?? && wish.status!="过期" && wish.status!="关闭">href="${basepath}/product/front/detail/${(wish.pid)!}"<#else>class="cursorDefault" href="javascript:void(0);"</#if>>
	                            				<img src="${mediaserver}imageservice?mediaImageId=${(wish.image)!}" alt=""><span class="imagecover"></span></a>
	                            				<#if (wish.hasGo)?? && wish.hasGo><span class="traveledMark"></span></#if>
	                            				<span class="favorNum">${(wish.count)!}</span>
	                            				<!-- <span class="traveledMark"></span> -->
	                            			</div>
	                            			<div class="protitle">
	                            			    <h4><#if (wish.status)?? && wish.status!="过期" && wish.status!="关闭"><a href="${basepath}/product/front/detail/${(wish.pid)!}">${(wish.pName)!}</a><#else>${(wish.pName)!}</#if></h4>
	                            			</div>
	                            			<div class="partner">
	                            				<span>这些小伙伴们也想去</span>
	                            				<ul class="partner-avatar">
	                            				<#list wish.members as member>
	                                                <li><a href="javascript:void(0);">
                                                        <img src="${mediaserver}imageservice?mediaImageId=${(member.headImageId)!}">
	                                                </a></li>
	                                            </#list>
	                                            <#if wish.count &gt; 5><li>...</li></#if>
	                                            </ul>
	                                            <ul class="private-letter">
	                                            <#list wish.members as member>
	                                                <li class="private-letter-01">
	                                                    <div class="private-letter-box">
	                                                        <span class="avatar">
                                                            <img src="${mediaserver}imageservice?mediaImageId=${(member.headImageId)!}">
	                                                        </span>
	                                                        <span class="username">${(member.nickName)!}</span>
	                                                        <a href="${basepath}/privateletter/talk/${(member.id)!}">发私信</a>
	                                                    </div>
	                                                </li>
	                                                </#list>
	                                                <!-- <li class="private-letter-01"></li> -->
	                                            </ul>
	                            			</div>
	                            			<div class="related">
	                            			    <span class="expired"><#if (wish.status)?? && wish.status=="过期">已过期<#elseif (wish.status)?? && wish.status=="关闭">已下线</#if></span>
	                            				<span class="deletebtn"></span>
	                                            <div class="isdelete">
	                                                <span class="prompt">真的不要我了吗？</span>
	                                                <button class="confirm">是的</button><button class="cancel">好吧,不删了</button>
	                                            </div>
	                            			</div>
	                            		</li>
	                            		</#list>
	                            	</ul>
                            	<#else>
                            		<div class="favor-empty-block">
	                                    <span class="favor-empty-img"></span>
	                                    <p class="tip-text">您还没有心愿单旅行产品哦！</p>
	                                    <a href="javascript:toProductList();">看看这里也许有你喜欢的线路</a>
	                                </div>
                            	</#if>
                            </div>
						</section>
					</div>
				</div>
			</div>
		</div>


</@html.navHeader>