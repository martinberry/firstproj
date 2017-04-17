<#import "/common/front/header/navHeader.ftl" as html/>
<@html.navHeader
title="真旅行-网站私信"
currentMenu="网站消息"
remoteJsFiles=[]
remoteCssFiles=["css/client/notice.css"]
localJsFiles=["common/pagination.js","member/front/private_letter.js"]
localCssFiles=[]>

    <div class="main-wrapper" id="main-wrapper">
        <div class="main-box" id="main-box" style="top: 30px;">
            <div class="top-border"><span class="clip"></span></div>
            <div class="box-container">
                <div class="cont-block clearfix">
                    <span class="modelLine"></span>
                    <aside class="leftMenuContent">
                        <ul>
                            <li>
                                <a href="${(basepath)!}/systemnotice/list">提醒</a>
                            </li>
                            <li  class="active">
                                <a href="javascript:void(0);">私信</a>
                            </li>
                        </ul>
                    </aside>
                    <section class="rightContent editHeadPortrait"  id="searchField">
                        <div class="msg-column remind-title clearfix">
                            <div class="msg-left msg-news">私信<span class="msg-left-num">(<span id="unreadNum">0</span>)</span></div>
                        </div>
                        <div id="msglist"></div>
                        <div id="pagination"></div>
	                    <input type="hidden" name="pageNo" value="1" />
					    <input type="hidden" name="pageSize" value="10" />
                    </section>
                </div>
            </div>
        </div>
    </div>

<!--   删除二次确认  模板 -->
    <div class="modal fade" data-backdrop="static" data-keyboard="false" id="delletterWindow">
        <div class="modal-dialog" style="width: 400px;height:180px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">删除确认</h4>
                </div>
                <div class="modal-body">
                    <p>确认删除该私信？</p>
                </div>
                <div class="modal-footer">
                    <a href="javascript:void(0);" class="commonBtn blueBtn delete">确认</a>
                    <a href="javascript:void(0);" class="commonBtn orangeBtn"  data-dismiss="modal" >取消</a>
                </div>
            </div>
        </div>
    </div>

</@html.navHeader>