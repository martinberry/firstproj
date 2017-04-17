<#import "/common/front/header/navHeader.ftl" as html/>
<@html.navHeader
title="真旅行-网站私信"
currentMenu="网站消息"
remoteJsFiles=[]
remoteCssFiles=["css/client/notice.css"]
localJsFiles=["member/front/private_letter_detail.js"]
localCssFiles=[]>
<input type="hidden" id="letter_id" value="${(letter.id)!}"/>
<input type="hidden" id="author_id" value="${(author.id)!}"/>
<input type="hidden" id="another_id" value="${(another.id)!}"/>

    <div class="main-wrapper" id="main-wrapper">
        <div class="main-box" id="main-box" style="top: 30px;">
            <div class="top-border"><span class="clip"></span></div>
            <div class="box-container">
                <div class="cont-block clearfix">
                    <span class="modelLine"></span>
                    <aside class="leftMenuContent">
                        <ul>
                            <li class="active">
                                <a href="${(basepath)!}/privateletter/list">返回</a>
                            </li>
                        </ul>
                    </aside>
                    <section class="rightContent editHeadPortrait">
                        <div class="detail-title clearfix">
                            <div class="pull-left">
                            	与＂${(another.nickName)!}＂对话
                            </div>
                            <div class="pull-right"><span class="detail-del-icon" onclick="deleteLetter('${(letter.id)!}')"></span></div>
                        </div>
                        <#if (letter.msgs)??>
                    	<#assign dateLine=""/>
						<#list letter.msgs as msg>
                        <div class="detail-column clearfix">
                        	<#if msg.dateTime.toString("yyyy-MM-dd") != dateLine>
                        		<#assign dateLine=msg.dateTime.toString("yyyy-MM-dd")/>
	                            <div class="clearfix">
	                                <hr class="line-left"/><span class="line-txt">创建于${(dateLine)!}</span><hr class="line-right"/>
	                            </div>
                        	</#if>
                            <div class="detail-cnt clearfix">
                                <div class="detail-cnt-left">
                                    <img class="msg-img" src="${mediaserver}imageservice?mediaImageId=<#if msg.memberId == letter.authorId>${(author.headImageId)!}<#else>${(another.headImageId)!}</#if>">
                                </div>
                                <div class="detail-cnt-right">
                                    <div class="detail-name clearfix">
                                        <div class="pull-left">
                                        <#if msg.memberId == letter.authorId>
                                        ${(author.nickName)!}
                                        <#else>
                                        ${(another.nickName)!}
                                        </#if>
                                        </div>
                                        <div class="pull-right">
	                                        <span class="detail-time">${(msg.dateTime.toString("HH:mm"))!}</span>
	                                        <span class="detail-del-icon" onclick="deleteMsg('${(msg.id)!}')"></span>
                                        </div>
                                    </div>
                                    <p>${(msg.content)!}</p>
                                </div>
                            </div>
                        </div>
                        </#list>
                        </#if>

                        <div class="detail-column clearfix">
                            <div class="detail-cnt clearfix">
                                <div class="detail-cnt-left">
                                <img class="msg-img" src="${mediaserver}imageservice?mediaImageId=${author.headImageId!}">
                                </div>
                                <div class="detail-cnt-right">
                                    <form class="form-horizontal" role="form" method="post" action="">
                                        <textarea name="msgContent" id="msgContent" class="form-control"  <#if (letter.msgs)?? && (letter.msgs)?size gt 0>placeholder="对Ta说"<#else>placeholder="hi，${(another.nickName)!}，这个地方很赞，我们一起去吧"</#if> cols="20" rows="5"></textarea>
                                        <p><a href="javascript:void(0);" class="commonBtn blueBtn subBtn" id="submitMsgBtn">发送</a></p>
                                    </form>
                                </div>
                            </div>
                        </div>

                    </section>
                </div>
            </div>
        </div>
        <div class="modal fade" id="ac-HintWindow">
	        <div class="modal-dialog" style="width: 450px;height:180px;">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	                        <span aria-hidden="true">X</span>
	                    </button>
	                    <h4 class="modal-title">提示</h4>
	                </div>
	                <div class="modal-body">
	                    <p></p>
	                </div>
	                <div class="modal-footer">
	                </div>
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