<#import "/common/front/header/navHeader.ftl" as html/>
<@html.navHeader
title="真旅行-网站提醒"
currentMenu="网站消息"
remoteJsFiles=[]
remoteCssFiles=["css/client/notice.css"]
localJsFiles=["common/pagination.js","member/front/system_notice.js"]
localCssFiles=[]>

    <div class="main-wrapper" id="main-wrapper">
        <div class="main-box" id="main-box" style="top: 30px;">
            <div class="top-border"><span class="clip"></span></div>
            <div class="box-container">
                <div class="cont-block clearfix">
                    <span class="modelLine"></span>
                    <aside class="leftMenuContent">
                        <ul>
                            <li class="active">
                                <a href="javascript:void(0);">提醒</a>
                            </li>
                            <li>
                                <a href="${(basepath)!}/privateletter/list">私信</a>
                            </li>
                        </ul>
                    </aside>
                    <section class="rightContent editHeadPortrait" id="searchField">
                        <div class="remind-column remind-title clearfix">
                            <div class="remind-left remind-news">消息<span class="remind-left-num">(<span id="unreadNum">0</span>未读)</span></div>
                            <div class="remind-right"><a href="javascript:void(0);" id="readAllBtn">全部标记为已读</a></div>
                        </div>
                        <div id="noticelist">
						<div class="remind-column clearfix">
                            <div class="remind-left">
                            	<span class="remind-icon pressed-icon1"></span>
                            	暂无消息！
                            </div>
                            <div class="remind-right">
                            	<span class="remind-color2"></span>
                            </div>
                        </div>
                        </div>
                        <div id="pagination"></div>
	                    <input type="hidden" name="pageNo" value="1" />
					    <input type="hidden" name="pageSize" value="10" />
                    </section>
                </div>
            </div>
        </div>
    </div>
</@html.navHeader>