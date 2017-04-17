<#import "/common/opera/htmlIndex.ftl" as html/>
<#import "/order/back/commentManage/commentDetailHeader.ftl" as commentDetailHeader/>

<@html.htmlIndex jsFiles=["order/back/orderComment/commentDetail.js"]
                                  cssFiles=["css/maintain/judgeManagement.css"] title="真旅行评价详情">
    <@commentDetailHeader.header>
    </@commentDetailHeader.header>
    <div class="judge-container">
        <section class="contentBg">
            <div class="content-box">
                <div class="content-row clearfix">
                    <div class="row-left row-title">用户名称</div>
                    <div class="row-left">${(comment.nickName)!''}</div>
                    <div class="row-right">
                        <span>当前评价状态</span>
                        <span class="btnSize <#if ((comment.statusEnum)!'') == "PUBLISHED">successBtn<#elseif ((comment.statusEnum)!'') == "RETURN">failBtn</#if>">${(comment.status)!''}</span>
                    </div>
                </div>
                <div class="content-row clearfix">
                    <div class="row-left row-title">用户评星</div>
                    <div class="row-left">
                        <div class="commonStarLevelIcon starLevelIcon starLevel-${(comment.stars)!''} allStar5">
                            <span class="commonStarLevelIcon"></span>
                        </div>
                    </div>
                </div>
                <div class="content-row">
                    <div class="row-left row-title">产 品ID</div>
                    <div class="row-left">${(comment.pid)!''}</div>
                </div>
                <div class="content-row">
                    <div class="row-left row-title">评价内容</div>
                    <div class="row-left" style="width:790px;">
                        <textarea readonly="readonly" class="txt-type form-control" rows="10">${(comment.content)!''}</textarea>
                    </div>
                </div>
            </div>
        </section>
    </div>

    <!--通过并发布模态框-->
    <div class="modal fade" id="successAndPublish">
        <div class="modal-dialog" style="width: 450px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">操作提示</h4>
                </div>
                <div class="modal-body">
                    <p style="text-align: center"><span class="judge-icon"></span>确定审核通过并发布评论？</p>
                </div>
                <div class="modal-footer">
                    <a href="javascript:void(0);" class="commonJudgeBtn confirmBtn" data-dismiss="modal" id="passCommentBtn" value="${(comment.id)!''}">确认</a>
                    <a href="javascript:void(0);" class="commonJudgeBtn cancelBtn" data-dismiss="modal">取消</a>
                </div>
            </div>
        </div>
    </div>

    <!--未通过模态框-->
    <div class="modal fade" id="failModal">
        <div class="modal-dialog" style="width: 450px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">操作提示</h4>
                </div>
                <div class="modal-body">
                    <p style="text-align: center"><span class="judge-icon"></span>确定审核不通过？</p>
                </div>
                <div class="modal-footer">
                    <a href="javascript:void(0);" class="commonJudgeBtn confirmBtn" data-dismiss="modal" id="rejectCommentBtn" value="${(comment.id)!''}">确认</a>
                    <a href="javascript:void(0);" class="commonJudgeBtn cancelBtn" data-dismiss="modal">取消</a>
                </div>
            </div>
        </div>
    </div>
</@html.htmlIndex>