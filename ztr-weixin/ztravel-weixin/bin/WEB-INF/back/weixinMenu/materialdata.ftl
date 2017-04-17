               <div class="wechat-teleText">
                        <div class="teleText-header clearfix">共有<span>${(searchItemCount)!}</span>条符合条件<div class="teleText-header-load"><img class="wechat-load" src="${host}/images/wechat-loading.png" /><span class="wechat-load-txt">同步微信素材</span></div></div>
                     <input type="hidden" id="host" name="host" value="${host}" />
                        <div class="teleText-content">
                        <#if showlist??>
                            <ul>
                            <#list showlist as show>
                            <li>
                           <div class="teleTxt-left"><span class="teleTxt-cell">
                                   <label class="radio" name="mediaId" value="${(show.mediaId)!}">
                                            <span class="radioIcon">

                                            </span>
                                          </label>
                                    </span></div>
                                    <div class="teleTxt-right">
                                      <#if (show.type)=="news"||(show.type)=="image">
                                        <img src=${(show.picUrl)!} class="teleTxt-img" data-original="" alt="" />
                                      </#if>
                                        <div class="teleTxt-centent">
                                               <div>
                                               <#if (show.title)??>

                                               <#list (show.title) as title>

                                                <div class="teleTxt-detail">${title!}</div>

                                                </#list>

                                               </#if>
                                             </div>
                                        </div>
                                    </div>
                                </li>
                             </#list>
                            </ul>
                            </#if>
                        </div>
                    </div>
                    <-split->
            <#include "/common/opera/pagination.ftl" />