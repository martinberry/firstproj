<#import "/common/opera/htmlIndex.ftl" as html />
<#import "/common/opera/main_header.ftl" as main_header />
<#import "/operator/basicData/leftMenu.ftl" as left />

<@html.htmlIndex jsFiles=["operator/basicData/headImageMaintain.js", "common/jquery-form.js"] cssFiles=["css/maintain/operatorManagement.css"] localCssFiles=["member/front/round_image.css"] curModule="" title="真旅行基础数据">
    <@main_header.header currentMenu="运营管理" currentSubMenu="基础数据">
    </@main_header.header>
    <div class="main-container clearfix">
            <@left.leftMenu curLeftModule="HEAD_IMAGE">
            </@left.leftMenu>
            <section class="rightModelContent">
                <div class="titleContent clearfix">
                    <h3 class="titleFonts18">头像维护</h3>
                </div>
                <div class="uploadData">
                    <button class="commonButton blue-45c8dcButton">上传头像</button>
                    <form id="headImgForm" method="POST" enctype="multipart/form-data" action="${basepath}/operation/basicData/uploadDefaultHeadImage">
                        <input type="file" id="headImgInputer" name="headImgFile" />
                    </form>
                    <ul class="upload-image-list">
                        <#if headImgIdList??>
                        <#list headImgIdList as headImgId>
                        <li class="upload-image-item">
                            <img src="${mediaserver}imageservice?mediaImageId=${headImgId!}" class="round_photo" alt="">
                            <!-- <span class="image-name">123123.png</span> -->
                            <a href="javascript:void(0);" class="delHeadImgBtn" value="${headImgId!''}">删除</a>
                        </li>
                        </#list>
                        </#if>
                    </ul>
                    <!-- <img src="" alt=""> -->
                </div>
            </section>
    </div>

    <div class="modal fade commonInitialize" id="messageDlg">
        <div class="modal-dialog" style="width: 350px;height:185px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">操作提示</h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <i></i>
                        <span class="popupContainer-fonts"></span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button" data-dismiss="modal">确 认</button>
                </div>
            </div>
        </div>
    </div>
</@html.htmlIndex>