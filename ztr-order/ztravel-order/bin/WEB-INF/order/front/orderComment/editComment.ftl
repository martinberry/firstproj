<#import "/common/front/header/navHeader.ftl" as html/>
<@html.navHeader
title="发布评价"
currentMenu="我的订单"
remoteJsFiles=[]
remoteCssFiles=["css/client/orderInfo.css"]
localJsFiles=["order/front/orderComment/editComment.js"]
localCssFiles=[]>

        <div class="main-wrapper" id="main-wrapper">
            <section class="publish-content main-box" style="width:1060px;">
                <span class="pin"></span>
                <div class="top-border"></div>
                <div class="panel panel-default publish-judgeBox">
                    <div class="panel-body">
                        <div class="judge-content">
                            <div class="judge-title">发布评论</div>
                            <div class="judge-row"><img class="judge-img" src="${mediaserver}imageservice?mediaImageId=${imageId!''}"><span>${productTitle!''}</span>
                            </div>
                            <form style="margin-top:15px;" id="commentForm">
                                <div class="judge-tab clearfix">
                                    <div class="judge-left">评星：</div>
                                    <div class="judge-right">
                                        <ul class="list-inline" id="stars">
                                            <li class="greyStar yellowStar"></li>
                                            <li class="greyStar yellowStar"></li>
                                            <li class="greyStar yellowStar"></li>
                                            <li class="greyStar yellowStar"></li>
                                            <li class="greyStar yellowStar"></li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="judge-tab clearfix">
                                    <div class="judge-left">评价：</div>
                                    <div class="judge-right">
                                        <textarea id="commentTextarea" rows="5" class="form-control txtArea" placeholder="酒店状况和航班安排的怎么样，目的地是否让你流连忘返，行程建议是否满意……"></textarea>
                                    </div>
                                </div>
                                <div class="judge-tip">还可以输入<span id="remainNum" style="color:#11b9b7;">500</span>个字</div>
                                <div style="margin-left:61px;"><a href="javascript:void(0);" class="commonBtn blueBtn" id="submitCommentBtn" onclick="submitComment()">发布评论</a></div>
                                <input type="hidden" name="orderId" value="${orderId!''}" />
                            </form>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    <!--发布评论成功提示-->
    <div class="publish-success-tips hidden" id="sucTips"></div>
    <!--发布评论错误提示-->
    <div class="publish-error-tips hidden" id="errTips"></div>
</@html.navHeader>