<#macro userEvaluation>
                <div class="commonModel">
                    <div id="userEvaluation" class="anchor"></div>
                    <div class="commonTitle">
                        <i class="commonIcon userEvaluationIcon"></i>用户评价
                        <label class="fonts14">( 共<span class="blueFonts">${commentNum!0}</span>人点评 )</label>
                    </div>
                    <ul class="userEvaluationList clearfix">
                        <#if commentList??>
                        <#list commentList as comment>
                        <li class="clearfix">
                            <div class="userEvaluationLeft">
                                <div>
                                    <img <#if comment.headImgId??>src="${mediaserver}imageservice?mediaImageId=${(comment.headImgId)!}"</#if> class="round_photo">
                                </div>
                                <div class="blueFonts">${(comment.memNickName)!''}</div>
                                <div class="color9f9f9fFonts">${(comment.date)!''}</div>
                            </div>
                            <div class="userEvaluationRight">
                                <div>
                                    <div class="commonStarLevelIcon starLevelIcon starLevel-${(comment.stars)!''} allStar5">
                                        <span class="commonStarLevelIcon"></span>
                                    </div>
                                </div>
                                <pre>${(comment.comment)!''}<pre>
                            </div>
                        </li>
                        </#list>
                        </#if>
                    </ul>
                </div>
</#macro>