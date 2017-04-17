<#import "/common/opera/htmlIndex.ftl" as html/>
<#import "piece_menu.ftl" as pieceMenu/>
<@html.htmlIndex jsFiles=["js/vendor/bootstrap-datepicker.min.js",
    "js/vendor/bootstrap-datepicker.zh-CN.min.js","js/vendor/underscore.js","js/vendor/jquery.tagsinput.js","js/vendor/kindeditor.js","common/ZtrDropDown.js",
    "product/back/pieces/visa_additional.js","product/back/pieces/piece_image.js","product/back/common_utils.js", "product/back/hotel/jquery-form.js"]
    cssFiles=["css/bootstrap-datepicker.min.css","css/jquery.tagsinput.css","css/maintain/productManagement.css"]
    curModule="产品管理"
    title="附加信息">

    <@pieceMenu.pieceMenu curPieceModule="additionalInfo">

        <div class="main-container changeMainContent">
            <input id='productId_hidden' type='hidden' value='${(additionalInfo.id)!}'/>
            <input id='productPid_hidden' type='hidden' value='${(additionalInfo.pid)!}'/>
            <input id='progress_hidden' type='hidden' value='${(additionalInfo.progress)!}'/>
            <section class="whiteBg">
                <div class="additionInfoDiv">
                    <div class="localTips">
                        <h4 class="editorModelTitle">签证材料</h4>
                            <ul class="facilityList clearfix" style="margin-bottom: 0;">
                                <li class="active">在职</li>
                                <li class="">自由职业</li>
                                <li class="">在校学生</li>
                                <li class="">退休</li>
                                <li class="">学龄前儿童</li>
                            </ul>
                            <div class="visaMaterial" id="materialDiv">
                                <div class="sectionDiv" id="employedDiv">
                                    <#if additionalInfo.materias?? && (additionalInfo.materias['EMPLOYED'])?? >
                                    <#list additionalInfo.materias['EMPLOYED'] as materia>
                                    <table class="noBorderCommonTab extraInfo">
                                        <colgroup>
                                            <col width="100">
                                            <col>
                                        </colgroup>
                                        <tbody>
                                        <tr>
                                            <th>标 &nbsp; 题</th>
                                            <td style="position: relative;">
                                                <input name="title_input" type="text" style="width: 300px;" value="${(materia.title)!''}">
                                                <div class="commonLinkWrap">
                                                    <#if materia_index == 0>
                                                    <a class="commonLink confirm-link addrow" href="javascript:void(0);">添 加</a>
                                                    <#else>
                                                    <a class="commonLink cancel-link delrow" href="javascript:void(0);">删 除</a>
                                                    </#if>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>内 &nbsp; 容</th>
                                            <td>
                                                <div class="facilityContent">
                                                    <textarea class="editorText" name="content">${(materia.content)!''}</textarea>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th class="verticalTop paddingTop10">例 &nbsp; 图</th>
                                            <td colspan="3">
                                                <div>JPG/JPEG/PNG图片</div>
                                                <ul class="hotelImgList clearfix">
                                                    <#if materia.images??>
                                                    <#list materia.images as imageId>
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
                                                        <form id="employed_form" action="${basepath}/product/back/hotel/uploadPicture" method="post" enctype="multipart/form-data">
                                                            <div><input type="file" name="picture" onchange="uploadImg(this,1);" class="upLoading"></div>
                                                        </form>
                                                    </li>
                                                </ul>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    </#list>
                                    <#else>
                                    <table class="noBorderCommonTab extraInfo">
                                        <colgroup>
                                            <col width="100">
                                            <col>
                                        </colgroup>
                                        <tbody>
                                        <tr>
                                            <th>标 &nbsp; 题</th>
                                            <td style="position: relative;">
                                                <input name="title_input" type="text" style="width: 300px;">
                                                <div class="commonLinkWrap">
                                                    <a class="commonLink confirm-link addrow" href="javascript:void(0);">添 加</a>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>内 &nbsp; 容</th>
                                            <td>
                                                <div class="facilityContent">
                                                    <textarea class="editorText" name="content"></textarea>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th class="verticalTop paddingTop10">例 &nbsp; 图</th>
                                            <td colspan="3">
                                                <div>JPG/JPEG/PNG图片</div>
                                                <ul class="hotelImgList clearfix">
                                                    <li>
                                                        <form id="employed_form" action="${basepath}/product/back/hotel/uploadPicture" method="post" enctype="multipart/form-data">
                                                            <div><input type="file" name="picture" onchange="uploadImg(this,1);" class="upLoading"></div>
                                                        </form>
                                                    </li>
                                                </ul>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    </#if>
                                </div>
                                <div class="sectionDiv hidden" id="freeDiv">
                                    <#if additionalInfo.materias?? && (additionalInfo.materias['FREE'])?? >
                                    <#list additionalInfo.materias['FREE'] as materia>
                                    <table class="noBorderCommonTab extraInfo">
                                        <colgroup>
                                            <col width="100">
                                            <col>
                                        </colgroup>
                                        <tbody>
                                        <tr>
                                            <th>标 &nbsp; 题</th>
                                            <td style="position: relative;">
                                                <input name="title_input" type="text" style="width: 300px;" value="${(materia.title)!''}">
                                                <div class="commonLinkWrap">
                                                    <#if materia_index == 0>
                                                    <a class="commonLink confirm-link addrow" href="javascript:void(0);">添 加</a>
                                                    <#else>
                                                    <a class="commonLink cancel-link delrow" href="javascript:void(0);">删 除</a>
                                                    </#if>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>内 &nbsp; 容</th>
                                            <td>
                                                <div class="facilityContent">
                                                    <textarea class="editorText" name="content">${(materia.content)!''}</textarea>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th class="verticalTop paddingTop10">例 &nbsp; 图</th>
                                            <td colspan="3">
                                                <div>JPG/JPEG/PNG图片</div>
                                                <ul class="hotelImgList clearfix">
                                                    <#if materia.images??>
                                                    <#list materia.images as imageId>
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
                                                        <form id="free_form" action="${basepath}/product/back/hotel/uploadPicture" method="post" enctype="multipart/form-data">
                                                            <div><input type="file" name="picture" onchange="uploadImg(this,1);" class="upLoading"></div>
                                                        </form>
                                                    </li>
                                                </ul>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    </#list>
                                    <#else>
                                    <table class="noBorderCommonTab extraInfo">
                                        <colgroup>
                                            <col width="100">
                                            <col>
                                        </colgroup>
                                        <tbody>
                                        <tr>
                                            <th>标 &nbsp; 题</th>
                                            <td style="position: relative;">
                                                <input name="title_input" type="text" style="width: 300px;">
                                                <div class="commonLinkWrap">
                                                    <a class="commonLink confirm-link addrow" href="javascript:void(0);">添 加</a>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>内 &nbsp; 容</th>
                                            <td>
                                                <div class="facilityContent">
                                                    <textarea class="editorText" name="content"></textarea>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th class="verticalTop paddingTop10">例 &nbsp; 图</th>
                                            <td colspan="3">
                                                <div>JPG/JPEG/PNG图片</div>
                                                <ul class="hotelImgList clearfix">
                                                    <li>
                                                        <form id="free_form" action="${basepath}/product/back/hotel/uploadPicture" method="post" enctype="multipart/form-data">
                                                            <div><input type="file" name="picture" onchange="uploadImg(this,1);" class="upLoading"></div>
                                                        </form>
                                                    </li>
                                                </ul>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    </#if>
                                </div>
                                <div class="sectionDiv hidden" id="studentDiv">
                                    <#if additionalInfo.materias?? && (additionalInfo.materias['STUDENT'])?? >
                                    <#list additionalInfo.materias['STUDENT'] as materia>
                                    <table class="noBorderCommonTab extraInfo">
                                        <colgroup>
                                            <col width="100">
                                            <col>
                                        </colgroup>
                                        <tbody>
                                        <tr>
                                            <th>标 &nbsp; 题</th>
                                            <td style="position: relative;">
                                                <input name="title_input" type="text" style="width: 300px;" value="${(materia.title)!''}">
                                                <div class="commonLinkWrap">
                                                    <#if materia_index == 0>
                                                    <a class="commonLink confirm-link addrow" href="javascript:void(0);">添 加</a>
                                                    <#else>
                                                    <a class="commonLink cancel-link delrow" href="javascript:void(0);">删 除</a>
                                                    </#if>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>内 &nbsp; 容</th>
                                            <td>
                                                <div class="facilityContent">
                                                    <textarea class="editorText" name="content">${(materia.content)!''}</textarea>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th class="verticalTop paddingTop10">例 &nbsp; 图</th>
                                            <td colspan="3">
                                                <div>JPG/JPEG/PNG图片</div>
                                                <ul class="hotelImgList clearfix">
                                                    <#if materia.images??>
                                                    <#list materia.images as imageId>
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
                                                        <form id="student_form" action="${basepath}/product/back/hotel/uploadPicture" method="post" enctype="multipart/form-data">
                                                            <div><input type="file" name="picture" onchange="uploadImg(this,1);" class="upLoading"></div>
                                                        </form>
                                                    </li>
                                                </ul>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    </#list>
                                    <#else>
                                    <table class="noBorderCommonTab extraInfo">
                                        <colgroup>
                                            <col width="100">
                                            <col>
                                        </colgroup>
                                        <tbody>
                                        <tr>
                                            <th>标 &nbsp; 题</th>
                                            <td style="position: relative;">
                                                <input name="title_input" type="text" style="width: 300px;">
                                                <div class="commonLinkWrap">
                                                    <a class="commonLink confirm-link addrow" href="javascript:void(0);">添 加</a>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>内 &nbsp; 容</th>
                                            <td>
                                                <div class="facilityContent">
                                                    <textarea class="editorText" name="content"></textarea>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th class="verticalTop paddingTop10">例 &nbsp; 图</th>
                                            <td colspan="3">
                                                <div>JPG/JPEG/PNG图片</div>
                                                <ul class="hotelImgList clearfix">
                                                    <li>
                                                        <form id="student_form" action="${basepath}/product/back/hotel/uploadPicture" method="post" enctype="multipart/form-data">
                                                            <div><input type="file" name="picture" onchange="uploadImg(this,1);" class="upLoading"></div>
                                                        </form>
                                                    </li>
                                                </ul>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    </#if>
                                </div>
                                <div class="sectionDiv hidden" id="retireDiv">
                                    <#if additionalInfo.materias?? && (additionalInfo.materias['RETIRE'])?? >
                                    <#list additionalInfo.materias['RETIRE'] as materia>
                                    <table class="noBorderCommonTab extraInfo">
                                        <colgroup>
                                            <col width="100">
                                            <col>
                                        </colgroup>
                                        <tbody>
                                        <tr>
                                            <th>标 &nbsp; 题</th>
                                            <td style="position: relative;">
                                                <input name="title_input" type="text" style="width: 300px;" value="${(materia.title)!''}">
                                                <div class="commonLinkWrap">
                                                    <#if materia_index == 0>
                                                    <a class="commonLink confirm-link addrow" href="javascript:void(0);">添 加</a>
                                                    <#else>
                                                    <a class="commonLink cancel-link delrow" href="javascript:void(0);">删 除</a>
                                                    </#if>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>内 &nbsp; 容</th>
                                            <td>
                                                <div class="facilityContent">
                                                    <textarea class="editorText" name="content">${(materia.content)!''}</textarea>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th class="verticalTop paddingTop10">例 &nbsp; 图</th>
                                            <td colspan="3">
                                                <div>JPG/JPEG/PNG图片</div>
                                                <ul class="hotelImgList clearfix">
                                                    <#if materia.images??>
                                                    <#list materia.images as imageId>
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
                                                        <form id="retire_form" action="${basepath}/product/back/hotel/uploadPicture" method="post" enctype="multipart/form-data">
                                                            <div><input type="file" name="picture" onchange="uploadImg(this,1);" class="upLoading"></div>
                                                        </form>
                                                    </li>
                                                </ul>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    </#list>
                                    <#else>
                                    <table class="noBorderCommonTab extraInfo">
                                        <colgroup>
                                            <col width="100">
                                            <col>
                                        </colgroup>
                                        <tbody>
                                        <tr>
                                            <th>标 &nbsp; 题</th>
                                            <td style="position: relative;">
                                                <input name="title_input" type="text" style="width: 300px;">
                                                <div class="commonLinkWrap">
                                                    <a class="commonLink confirm-link addrow" href="javascript:void(0);">添 加</a>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>内 &nbsp; 容</th>
                                            <td>
                                                <div class="facilityContent">
                                                    <textarea class="editorText" name="content"></textarea>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th class="verticalTop paddingTop10">例 &nbsp; 图</th>
                                            <td colspan="3">
                                                <div>JPG/JPEG/PNG图片</div>
                                                <ul class="hotelImgList clearfix">
                                                    <li>
                                                        <form id="retire_form" action="${basepath}/product/back/hotel/uploadPicture" method="post" enctype="multipart/form-data">
                                                            <div><input type="file" name="picture" onchange="uploadImg(this,1);" class="upLoading"></div>
                                                        </form>
                                                    </li>
                                                </ul>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    </#if>
                                </div>
                                <div class="sectionDiv hidden" id="childDiv">
                                    <#if additionalInfo.materias?? && (additionalInfo.materias['CHILD'])?? >
                                    <#list additionalInfo.materias['CHILD'] as materia>
                                    <table class="noBorderCommonTab extraInfo">
                                        <colgroup>
                                            <col width="100">
                                            <col>
                                        </colgroup>
                                        <tbody>
                                        <tr>
                                            <th>标 &nbsp; 题</th>
                                            <td style="position: relative;">
                                                <input name="title_input" type="text" style="width: 300px;" value="${(materia.title)!''}">
                                                <div class="commonLinkWrap">
                                                    <#if materia_index == 0>
                                                    <a class="commonLink confirm-link addrow" href="javascript:void(0);">添 加</a>
                                                    <#else>
                                                    <a class="commonLink cancel-link delrow" href="javascript:void(0);">删 除</a>
                                                    </#if>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>内 &nbsp; 容</th>
                                            <td>
                                                <div class="facilityContent">
                                                    <textarea class="editorText" name="content">${(materia.content)!''}</textarea>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th class="verticalTop paddingTop10">例 &nbsp; 图</th>
                                            <td colspan="3">
                                                <div>JPG/JPEG/PNG图片</div>
                                                <ul class="hotelImgList clearfix">
                                                    <#if materia.images??>
                                                    <#list materia.images as imageId>
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
                                                        <form id="child_form" action="${basepath}/product/back/hotel/uploadPicture" method="post" enctype="multipart/form-data">
                                                            <div><input type="file" name="picture" onchange="uploadImg(this,1);" class="upLoading"></div>
                                                        </form>
                                                    </li>
                                                </ul>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    </#list>
                                    <#else>
                                    <table class="noBorderCommonTab extraInfo">
                                        <colgroup>
                                            <col width="100">
                                            <col>
                                        </colgroup>
                                        <tbody>
                                        <tr>
                                            <th>标 &nbsp; 题</th>
                                            <td style="position: relative;">
                                                <input name="title_input" type="text" style="width: 300px;">
                                                <div class="commonLinkWrap">
                                                    <a class="commonLink confirm-link addrow" href="javascript:void(0);">添 加</a>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th>内 &nbsp; 容</th>
                                            <td>
                                                <div class="facilityContent">
                                                    <textarea class="editorText" name="content"></textarea>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <th class="verticalTop paddingTop10">例 &nbsp; 图</th>
                                            <td colspan="3">
                                                <div>JPG/JPEG/PNG图片</div>
                                                <ul class="hotelImgList clearfix">
                                                    <li>
                                                        <form id="child_form" action="${basepath}/product/back/hotel/uploadPicture" method="post" enctype="multipart/form-data">
                                                            <div><input type="file" name="picture" onchange="uploadImg(this,1);" class="upLoading"></div>
                                                        </form>
                                                    </li>
                                                </ul>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    </#if>
                                </div>
                            </div>
                    </div>
                    <div class="sectionDiv localTips">
                        <h4 class="editorModelTitle">办理流程</h4>
                        <div class="visaProcess" id="processDiv">
                        <#if additionalInfo.processes??>
                            <div class="sectionDiv">
                            <#list additionalInfo.processes as process>
                                <table class="noBorderCommonTab extraInfo">
                                    <colgroup>
                                        <col width="100">
                                        <col>
                                    </colgroup>
                                    <tbody>
                                    <tr>
                                        <th>标 &nbsp; 题</th>
                                        <td style="position: relative;">
                                            <input name="title_input" type="text" style="width: 300px;" value="${(process.title)!''}">
                                            <div class="commonLinkWrap">
                                                <#if process_index == 0>
                                                <a class="commonLink confirm-link addrow" href="javascript:void(0);">添 加</a>
                                                <#else>
                                                <a class="commonLink cancel-link delrow" href="javascript:void(0);">删 除</a>
                                                </#if>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>内 &nbsp; 容</th>
                                        <td>
                                            <form>
                                                <textarea name="content" class="editorText">${(process.content)!''}</textarea>
                                            </form>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th class="verticalTop paddingTop10">例 &nbsp; 图</th>
                                        <td colspan="3">
                                            <div>JPG/JPEG/PNG图片</div>
                                            <ul class="hotelImgList clearfix">
                                                <#if process.images??>
                                                <#list process.images as imageId>
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
                                                        <div><input type="file" name="picture" onchange="uploadImg(this,1);" class="upLoading"></div>
                                                    </form>
                                                </li>
                                            </ul>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </#list>
                            </div>
                        <#else>
                            <div class="sectionDiv">
                                <table class="noBorderCommonTab extraInfo">
                                    <colgroup>
                                        <col width="100">
                                        <col>
                                    </colgroup>
                                    <tbody>
                                    <tr>
                                        <th>标 &nbsp; 题</th>
                                        <td style="position: relative;">
                                            <input name="title_input" type="text" style="width: 300px;">
                                            <div class="commonLinkWrap">
                                                <a class="commonLink confirm-link addrow" href="javascript:void(0);">添 加</a>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>内 &nbsp; 容</th>
                                        <td>
                                            <form>
                                                <textarea name="content" class="editorText"></textarea>
                                            </form>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th class="verticalTop paddingTop10">例 &nbsp; 图</th>
                                        <td colspan="3">
                                            <div>JPG/JPEG/PNG图片</div>
                                            <ul class="hotelImgList clearfix">
                                                <li>
                                                    <form id="form" action="${basepath}/product/back/hotel/uploadPicture" method="post" enctype="multipart/form-data">
                                                        <div><input type="file" name="picture" onchange="uploadImg(this,1);" class="upLoading"></div>
                                                    </form>
                                                </li>
                                            </ul>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </#if>
                        </div>
                    </div>
                    <div class="editorCommonModel">
                        <h4 class="editorModelTitle">预订须知</h4>
                        <form>
                            <textarea name="content" class="editorText">${(additionalInfo.additionalInfos['BOOKING'])!}</textarea>
                        </form>
                    </div>
                    <div class="editorCommonModel">
                        <h4 class="editorModelTitle">费用说明</h4>
                        <form>
                            <textarea name="content" class="editorText">${(additionalInfo.additionalInfos['FEE_DETAIL'])!}</textarea>
                        </form>
                    </div>
                    <div class="editorCommonModel">
                        <h4 class="editorModelTitle">退改政策</h4>
                        <form>
                            <textarea name="content" class="editorText">${(additionalInfo.additionalInfos['REFUND_POLICY'])!}</textarea>
                        </form>
                    </div>
                </div>
            </section>
        </div>
        <script type="text/html" id="template">
            <table class="noBorderCommonTab extraInfo">
                <colgroup>
                    <col width="100">
                    <col>
                </colgroup>
                <tbody>
                <tr>
                    <th>标 &nbsp; 题</th>
                    <td style="position: relative;">
                        <input name="title_input" type="text" style="width: 300px;">
                        <div class="commonLinkWrap">
                            <a class="commonLink cancel-link delrow" href="javascript:void(0);">删 除</a>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>内 &nbsp; 容</th>
                    <td>
                        <form class="ke-textra">
                            <textarea class="editorBox" name="content"></textarea>
                        </form>
                    </td>
                </tr>
                <tr>
                    <th class="verticalTop paddingTop10">例 &nbsp; 图</th>
                    <td colspan="3">
                        <div>JPG/JPEG/PNG图片</div>
                        <ul class="hotelImgList clearfix">
                            <li>
                                <form id="form" action="${basepath}/product/back/hotel/uploadPicture" method="post" enctype="multipart/form-data">
                                    <div><input type="file" name="picture" onchange="uploadImg(this,1);" class="upLoading"></div>
                                </form>
                            </li>
                        </ul>
                    </td>
                </tr>
                </tbody>
            </table>
        </script>
    </@pieceMenu.pieceMenu>
</@html.htmlIndex>