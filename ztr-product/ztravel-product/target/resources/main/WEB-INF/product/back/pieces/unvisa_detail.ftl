<#import "/common/opera/htmlIndex.ftl" as html/>
<#import "piece_menu.ftl" as pieceMenu/>
<@html.htmlIndex jsFiles=["js/vendor/bootstrap-datepicker.min.js",
    "js/vendor/bootstrap-datepicker.zh-CN.min.js","js/vendor/underscore.js","js/vendor/jquery.tagsinput.js","common/ZtrDropDown.js",
    "product/back/pieces/unvisa_detail.js","product/back/pieces/piece_image.js","product/back/common_utils.js", "product/back/hotel/jquery-form.js"]
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
                    <input id='languageType_hidden' type='hidden' value='${(detailInfo.languageType)!}'/>
                    <tr>
                        <th>服务语言</th>
                        <td>
                            <div class="dropdown" style="width: 110px;">
                                <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                    <span class="activeFonts" id="languageTypeName"></span>
                                    <span class="caret"></span>
                                </a>
                                <ul class="dropdown-menu lang" id='languageType_dropdown'>
                                    <li value="CHINESE" class="active"><a href="javascript:void(0);">中文</a></li>
                                    <li value="ENGLISH"><a href="javascript:void(0);">英语</a></li>
                                    <li value="FRENCH"><a href="javascript:void(0);">法语</a></li>
                                    <li value="PORTUGUESE"><a href="javascript:void(0);">葡萄牙语</a></li>
                                    <li value="SPANISH"><a href="javascript:void(0);">西班牙语</a></li>
                                    <li value="JAPANESE"><a href="javascript:void(0);">日语</a></li>
                                    <li value="KOREAN"><a href="javascript:void(0);">韩语</a></li>
                                    <li value="VIETNAMESE"><a href="javascript:void(0);">越南语</a></li>
                                    <li value="THAI"><a href="javascript:void(0);">泰语</a></li>
                                </ul>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>服务时间</th>
                        <td>
                            <input id="serviceTime_input" type="text" style="width: 110px;" value="${(detailInfo.serviceTime)!}">
                        </td>
                    </tr>
                    <tr>
                        <th class="verticalTop paddingTop10">产品图片</th>
                        <td colspan="3">
                            <div>可上传1-4张高清（XXX*XXX）JPG/PNG图</div>
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
                                        <div><input type="file" id="file" name="picture" onchange="uploadImg(this,4);" class="upLoading"></div>
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