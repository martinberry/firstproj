 <#macro userEvaluation>
    <section class="commonModule">
        <h2 class="commonTitleH2 haveIcon"><i class="evaluateIcon"></i>用户评价</h2>
        <#if commentList??>
        <div class="evaluateDetail">
            <#list commentList as comment>
            <div class="judge-cnt clearfix">
                <div class="judge-top">
                    <img class="pop-img" src="${mediaserver}imageservice?mediaImageId=${(comment.headImgId)!''}">
                    <span class="pop-name">${(comment.memNickName)!''}</span>
                    <span class="fr judge-date">${(comment.date)!''}</span>
                </div>
                <div class="judge-stars">
                 <#list 1..(comment.stars)?number as count>
                <span class="yellow-star"></span>
                </#list>
                </div>
                <p class="judge-txt">${(comment.comment)!''}</p>
            </div>
            </#list>
        </div>
        </#if>
        <#if commentNum?? &&(commentNum gt 5)>
        <div class="commonBottomStyle judgeNotice">
            <h3 class="commonBottomTil"><i class="bAndTArrow btmAndTopArrow"></i>查看更多评论</h3>
        </div>
        <div class="commonBottomStyle closeJudgeNotice">
            <h3 class="commonBottomTil"><i class="bAndTArrow btmArrow"></i>收起</h3>
        </div>
        <#else>
        <div class="prd-space"></div>
        </#if>
    </section>
</#macro>
