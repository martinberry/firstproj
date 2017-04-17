<#macro header>
    <header class="main-header">
        <div class="stairMenuContent">
            <div class="wrap clearfix">
                <div class="navLeft">
                    <a class="logo" href="/"></a>
                    <ul class="stairMenu">
                        <li class="first-level-nav">
                            <a href="javascript:void(0);">用户评论</a>
                        </li>
                    </ul>
                </div>
                <#include "/common/opera/header_right.ftl" />
            </div>
        </div>
        <div class="headSecondBlank clearfix">
            <span>
                <a class="backBtn" href="${basepath}/comment/list">&lt;&nbsp;返回</a>
            </span>
            <#if ((comment.statusEnum)!'') == "COMMITED">
            <span class="head-btn">
                <a href="javascript:void(0);" class="commonJudgeBtn redBtn" id="passBtn">通过并发布</a>
                <a href="javascript:void(0);" class="commonJudgeBtn greyBtn" id="rejectBtn">未通过</a>
            </span>
            </#if>
        </div>
    </header>
</#macro>