<#import "/common/weixin/htmlIndex.ftl" as html/>
<#import "/common/weixin/headerBar.ftl" as header/>
		<@html.htmlIndex
			title="私信详情"
		  	remoteCssFiles=["mstatic/css/privateLetterList.css"]
		  	localJsFiles=["member/weixin/letter/letterDetail.js","common/weixin/common_utils.js"]
		  				  remoteJsFiles=["mstatic/js/iscroll/iscroll-probe.js"]
		>

<body>
	<div data-role="page">
		<@header.headerBar title="私信"></@header.headerBar>
		<input type="hidden" id="letter_id" value="${(letter.id)!}"/>
		<input type="hidden" id="author_id" value="${(author.id)!}"/>
		<input type="hidden" id="another_id" value="${(another.id)!}"/>
		<div class="wrapper backGauge">
            <div class="dialogue clearfix">
                <span class="dialogueTitle">与＂${(another.nickName)!}＂对话</span>
                <a class="blueBigBtn deleteAllInfo" data-rel="popup" data-history="false" href="#confirmWindow">删除对话</a>
                <div class="ui-content" data-role="popup" id="confirmWindow" data-history="false" data-transition="pop" data-position-to="window" data-theme="a" data-overlay-theme="b">
                    <p class="dlg-cnt">删除全部对话吗？</p>
                    <div class="dlg-foot">
                        <a class="btn-com btn-confirm" data-role="none" href="javascript:deleteLetter();">确 定</a><a class="btn-com btn-cancel" data-role="none" href="javascript:void(0);" data-rel="back">取 消</a>
                    </div>
                </div>
                <div class="ui-content" data-role="popup" id="alert-dialog" data-history="false" data-transition="pop" data-position-to="window" data-theme="a" data-overlay-theme="b">
			        <p class="dlg-cnt"></p>
			        <div class="dlg-foot">
			            <a class="btn-com btn-cancel" data-role="none" href="javascript:void(0);" data-rel="back">关 闭</a>
			        </div>
			    </div>
            </div>
            <ul class="listContent changeList">
            	<#if (letter.msgs)??>
				<#list letter.msgs as msg>
                <li>
                    <div class="leftPhoto">
                        <img style="border-radius: 59px;"
                         <#assign headImageId="${(another.headImageId)!}" />
                         <#if msg.memberId == letter.authorId>
                            <#assign headImageId="${(author.headImageId)!}" />
                         </#if>
                            src="${mediaserver}imageservice?mediaImageId=${headImageId}"
                        >
                    </div>
                    <div class="rightDetails">
                        <div class="nameAndTime clearfix">
                            <span class="nameFonts">
                            	<#if msg.memberId == letter.authorId>
	                            ${(author.nickName)!}
	                            <#else>
	                            ${(another.nickName)!}
	                            </#if>
                            </span>
                            <span class="timeFonts">${(msg.dateTime.toString("yyyy-MM-dd HH:mm"))!}</span>
                        </div>
                        <div>
                            <p>${(msg.content)!}</p>
                        </div>
                        <div class="deleteInfo">
                            <span class="clickArea">
                                <span class="deleteIcon" onclick="deleteMsg('${(msg.id)!}')"></span>
                            </span>
                        </div>
                    </div>
                </li>
                </#list>
                </#if>
            </ul>
		</div>
        <div class="bottomFixModel">
            <input type="text" class="bottomFixInput" data-role="none" placeholder="请输入……">
            <a href="javascript:void(0);" class="blueSolidBtn">发送</a>
        </div>
	</div>

<script type="text/javascript">
	$(function(){

        $(".rightDetails .secondEllipsis").each(function(){
            var divH = $(this).height();
            var $p = $("p", $(this)).eq(0);
            while ($p.outerHeight() > divH) {
                $p.text($p.text().replace(/(\s)*([a-zA-Z0-9]+|\W)(\.\.\.)?$/, "..."));
            }
        });

        $("#confirmWindow").popup({
            theme: 'a',
            dismissible: false,
            shadow: false,
            afteropen: function(){
            $(document).bind("touchmove", function(){  //  弹窗弹出时，禁止页面滚动
                    return false;
                });
            },
            afterclose: function(){
                $(document).unbind("touchmove");   // 解除禁止滚动绑定
            }
        });

        $(".deleteAllInfo").click(function(){
            $("#confirmWindow").popup("open");
        });

        $(".leftBtn, .rightBtn").click(function(){   // 使用tap方法有穿透现象
            $("#confirmWindow").popup("close");
        });

        $(".dlg-foot .btn-confirm").click(function(){
            $(".listContent li").hide("fast");
            $("#confirmWindow").popup("close");
        });

	});


</script>
</body>
</@html.htmlIndex>