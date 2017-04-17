<#import "/common/opera/htmlIndex.ftl" as html/>
<#import "piece_menu.ftl" as pieceMenu/>
<@html.htmlIndex jsFiles=["js/vendor/bootstrap-datepicker.min.js",
    "js/vendor/bootstrap-datepicker.zh-CN.min.js","js/vendor/underscore.js","js/vendor/jquery.tagsinput.js","common/ZtrDropDown.js",
    "product/back/pieces/visa_detail.js","product/back/pieces/piece_image.js","product/back/common_utils.js", "product/back/hotel/jquery-form.js"]
    cssFiles=["css/bootstrap-datepicker.min.css","css/jquery.tagsinput.css","css/maintain/productManagement.css"]
    curModule="产品管理"
    title="详情信息">

    <@pieceMenu.pieceMenu curPieceModule="detailInfo">

        <div class="main-container changeMainContent">
            <section class="whiteBg">
                <table class="noBorderCommonTab">
                    <colgroup>
                        <col width="110">
                        <col width="850">
                    </colgroup>
                    <tbody>
                    <input id='productId_hidden' type='hidden' value='${(detailInfo.id)!}'/>
                    <input id='productPid_hidden' type='hidden' value='${(detailInfo.pid)!}'/>
                    <input id='progress_hidden' type='hidden' value='${(detailInfo.progress)!}'/>
                    <tr>
                        <th>面试要求</th>
                        <td>
                            <div class="radioContent" id="isInterview_radio">
                                <label class="radio <#if !(detailInfo.isInterview)?? || (detailInfo.isInterview)>active</#if>" value="NEED">
                                    <span class="radioIcon"></span>
                                    <span class="labelFonts">是</span>
                                </label>
                                <label class="radio <#if (detailInfo.isInterview)?? && !(detailInfo.isInterview)>active</#if>" value="NOTNEED">
                                    <span class="radioIcon"></span>
                                    <span class="labelFonts">否</span>
                                </label>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>签证有效期</th>
                        <td>
                            <input id="validate_input" type="text" style="width:130px;" value="${(detailInfo.validate)!''}">
                        </td>
                    </tr>
                    <tr>
                        <th>可停留日期</th>
                        <td>
                            <input id="stayTime_input" type="text" style="width:130px;" value="${(detailInfo.stayTime)!''}">
                        </td>
                    </tr>
                    <tr>
                        <th>可入境次数</th>
                        <td>
                            <input id="times_input" type="text" style="width: 130px;" value="${(detailInfo.times)!''}">&nbsp;次
                        </td>
                    </tr>
                    <tr>
                        <th>受理范围</th>
                        <td>
                            <textarea id="scope_input" style="width:700px;height: 140px;">${(detailInfo.scope)!''}</textarea>
                        </td>
                    </tr>
                    <tr>
                        <th class="verticalTop paddingTop10">图片上传</th>
                        <td colspan="3">
                            <div>至少上传1张JPG/JPEG/PNG图片</div>
                            <ul class="hotelImgList clearfix">
                                <#if detailInfo.images??>
                                <#list detailInfo.images as imageId>
                                <li>
                                    <img src="${mediaserver}imageservice?mediaImageId=${imageId!}">
                                    <input name="pictureId" value="${imageId!}" type="hidden">
                                    <div class="moveLeft">左移</div>
                                    <div class="moveRight">右移</div>
                                    <div class="hoverDelete">
                                        <i class="delIcon"></i>删除
                                    </div>
                                </li>
                                </#list>
                                </#if>
                                <li>
                                    <form id="form" action="${basepath}/product/back/hotel/uploadPicture" method="post" enctype="multipart/form-data">
                                        <div><input type="file" id="file" name="picture" onchange="uploadImg(this);" class="upLoading"></div>
                                    </form>
                                </li>
                            </ul>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </section>
        </div>
</@pieceMenu.pieceMenu>
</@html.htmlIndex>