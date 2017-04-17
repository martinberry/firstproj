<#import "/common/weixin/htmlIndex.ftl" as html/>
<#import "/common/weixin/headerBar.ftl" as head/>
<@html.htmlIndex title="真旅行-${(weixinTopic.topicTitle)!''}"
		  	remoteCssFiles=["mstatic/css/topic/topic_discuss.css"]
		  	remoteJsFiles=["mstatic/js/vendor/jquery.lazyload.js"]
		  	localJsFiles=["front/topic/topicDetail.js"]>
<div class="viewport ui-page ui-page-theme-a ui-page-active" data-role="page" data-url="/真旅行移动端/话题/话题讨论.html" tabindex="0">
    <@head.headerBar title="真旅行-${(weixinTopic.topicTitle)!''}"></@head.headerBar>
    <div class="detail-content">
    <input type="hidden" id="openId" value="${openId!''}" />
    <input type="hidden" id="appId" value="${appId!''}" />
    <input type="hidden" id="scrollType" value="${scrollType!'none'}" />
    <input type="hidden" id="topicId" value="${(weixinTopic.topicId)!''}" />
    
        <div class="topicModel">
            <h1><i class="discussIcon"></i>${(weixinTopic.topicTitle)!''}</h1>
            <div class="topicIntro">
                <p>${(weixinTopic.topicContent)!''}</p>
            </div>
        </div>
        <#if (weixinTopic.giftImage)?? &&(weixinTopic.giftTitle)?? &&(weixinTopic.giftContent)??>
        <div class="topicModel topicModel2 gift">
            <h2 class="cyan"><span class="line" style="float:left;"></span>本期奖励<span class="line"></span></h2>
            <div class="topic_prize">
            <#if (weixinTopic.giftImage)??>           
                <img src=" ${mediaserver}imageservice?mediaImageId=${(weixinTopic.giftImage)!''}" alt=""/>
             </#if>   
                <h3 class="prize_title">${(weixinTopic.giftTitle)!''}</h3>
                <p>${(weixinTopic.giftContent)!''}</p>
                <a href="javascript:void(0);" data-role="none" class="unfold"><i class="unfoldIcon"></i><span>展开</span></a>
            </div>
        </div>
        </#if>
        <div class="topicModel topicModel2" id="commentAnchor">
            <h2 class="cyan"><span class="line" style="float:left;"></span>讨论区<span class="line"></span></h2>
            <div class="discuss_area">
                <ul>
                <#if commentList??>
                <#list commentList as comment>
                    <li class="discuss_list current" style="margin-top: 1rem;">
                    <input type='hidden' name="commentId" value="${(comment.commentId)!''}" />
                        <div class="avatar">
                        <#if (comment.anonymous)=='1'>
                        <span class="default"></span>
                        <#else>
                        <#if (comment.headImage)??>
                            <img style="border-radius: 59px;" src="${(comment.headImage)!''}">
                        </#if>  
                        </#if>  
                        </div>
                        <div class="rightDetails">
                            <div class="nameAndTime clearfix">
                               <#if (comment.anonymous)=='1'>
                                <span class="nameFonts">匿名</span>
                               <#else>
                                <span class="nameFonts">${(comment.nickName)!''}</span>
                                </#if>
                                <span class="timeFonts">${(comment.timeStatus)!''}</span>
                            </div>
                            <div class="secondEllipsis">
                            	<#noescape>
                                <p>${(comment.commentDetail)!''}</p>
                                </#noescape>
                            </div>
                        </div>
                        <div class="cancelLike" style="display: block">
                            <span></span>
                            <em>${(comment.praiseNum)!''}</em>
                        </div>
                    </li>
                   </#list> 
                  </#if> 
                </ul>
            </div>
        </div>
    </div>
    <div class="topicBar">
        <div style="width:100%;">
            <a class="disNum" href="javascript:void(0);" data-role="none"><i class="cyan">${(weixinTopic.discussNum)!''}</i> 人参与讨论</a>
            <div class="wantSay">我也想说</div>
        </div>
    </div>

<div class="ui-loader ui-corner-all ui-body-a ui-loader-default"><span class="ui-icon-loading"></span><h1>loading</h1></div>
</@html.htmlIndex>
