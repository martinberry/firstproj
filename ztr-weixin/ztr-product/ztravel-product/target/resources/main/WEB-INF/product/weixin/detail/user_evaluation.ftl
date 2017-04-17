<#macro userEvaluation>
	<div id="judgePoint"></div>
    <section class="commonModule">
        <h2 class="commonTitleH2 haveIcon"><i class="evaluateIcon"></i>用户评价</h2>
        <div class="evaluateDetail">
        <#if commentList??>
            <#list commentList as comment>
            <div class="judge-cnt clearfix <#if (comment_index > 4)> hidden</#if>">
                <div class="judge-top">
                    <img class="pop-img" src="${mediaserver}imageservice?mediaImageId=${(comment.headImgId)!}">
                    <span class="pop-name">${(comment.memNickName)!}</span>
                    <span class="fr judge-date">${(comment.date)!}</span>
                </div>
                <div class="judge-stars">
                <#list 1..(comment.stars)?number as count>
                    <span class="yellow-star"></span>
                </#list>
                </div>
                <p class="judge-txt">${(comment.comment)!}</p>
            </div>
            </#list>
        </#if>
        </div>
        <#if (commentNum gt 5)>
        <div class="commonBottomStyle judgeNotice">
          <h3 class="commonBottomTil"><i class="bAndTArrow btmAndTopArrow"></i>查看更多评论</h3>
         </div>
         <div class="commonBottomStyle closeJudgeNotice">
           <h3 class="commonBottomTil"><i class="bAndTArrow btmArrow"></i>收起</h3>
        </div>
        <!--<div class="judge-more">
            <a class="judge-link ui-link" href="${wxServer}/product/weixin/detail/getAllComment/${(product.pid!)}">查看更多评论</a>
        </div>-->
        <#else>
        <div class="prd-space"></div>
        </#if>
    </section>

</#macro>