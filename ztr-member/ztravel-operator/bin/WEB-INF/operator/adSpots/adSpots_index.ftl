<#import "/common/opera/htmlIndex.ftl" as html />
<#import "/common/opera/main_header.ftl" as main_header />
<@html.htmlIndex jsFiles=["operator/basicData/adSpots.js","operator/basicData/upload.js","js/maintain/jquery-ui.min.js","common/jquery-form.js"] cssFiles=["css/maintain/operatorManagement.css"] curModule="运营管理" title="真旅行广告位管理">
    <@main_header.header currentMenu="运营管理" currentSubMenu="广告位管理">
 	</@main_header.header>
    <div class="main-container">
            <section class="whiteBg">
                <div  class="btnDiv clearfix">
                    <div class="leftBtn">
                        <button class="commonButton blue-45c8dcButton changeBtn addContentItem"><i class="whiteAddIcon"></i>添加首页Banner</button>
                    </div>
                    <div class="rightBtn">
                        <button class="commonButton red-fe6869Button" id="preScan">预 览</button>
                        <button class="commonButton blue-45c8dcButton" id="saveAndPublish">保存并发布</button>
                    </div>
                </div>
                <div class="material-table-container">
                    <div class="tabTitle clearfix">
                        <span class="title01">显示顺序</span>
                        <span class="title02">Banner标题</span>
                        <span class="title03">跳转页面</span>
                        <span class="title04">Banner</span>
                        <span class="title05">操作</span>
                    </div>

			 <div class="material-info-list-container">
				<#if adSpotList ?? >
            		<#list adSpotList as adSpot>
            			<div class="material-info-item clearfix">
                        <div class="number commonStyle">${adSpot.priority!}</div>
                        <div class="commonStyle row02">
                            <input type="text" value="${adSpot.title!}" style="width: 98%;" name="title">
                        </div>
                        <div class="commonStyle row03">
                            <input type="text" value="${adSpot.url!}" style="width: 98%;" name="url">
                        </div>
                           <div class="commonStyle row04 instantUpload">
                        <#if adSpot.imageId ??>
                            <img src="${mediaserver}imageservice?mediaImageId=${adSpot.imageId!}" class="uploadImg" />
                       		<form name="imageform" action="${basepath!}/product/back/hotel/uploadPicture" method="post" enctype="multipart/form-data">
                                <div class="showUpload">
                                    <input type="file" class="againUpload"  name="picture">
                                    <input type ="hidden" name-value="imageId" value="${adSpot.imageId!}"/>
                                    <a href="javascript:void(0);" class="againUploadFonts">重新上传</a>
                                </div>
                            </form>
                        <#else>
                        	<form name="imageform" action="${basepath!}/product/back/hotel/uploadPicture" method="post" enctype="multipart/form-data">
                              <input type="file" class="instantUploadFile" name ="picture">
                                    <span class="instantUploadStyle">
                                       <input type ="hidden" name-value="imageId" />
                                        <span class="instantUploadIcon"></span>
                                        <span>立即上传</span>
                                    </span>
                            </form>
                        </#if>
                         </div>
                         <div class="commonStyle row05">
                            <a href="javascript:void(0);" class="deleteBtn">删除</a>
                        </div>
                    </div>
            		</#list>
				</#if>
        	   		 <div class="material-info-item clearfix copyTemplate" style="display: none;">
                        <div class="number commonStyle"></div>
                        <div class="commonStyle row02">
                            <input type="text" value="" style="width: 98%;" name="title">
                        </div>
                        <div class="commonStyle row03">
                            <input type="text" value="" style="width: 98%;" name ="url">
                        </div>
                        <div class="commonStyle row04 instantUpload">
                          <form name="imageform" action="${basepath!}/product/back/hotel/uploadPicture" method="post" enctype="multipart/form-data">
                                <input type="file" class="instantUploadFile" name="picture">
                                <input type ="hidden" name-value="imageId" />
                                <span class="instantUploadStyle">
                                    <span class="instantUploadIcon"></span>
                                    <span>立即上传</span>
                                </span>
                            </form>
                        </div>
                        <div class="commonStyle row05">
                            <a href="javascript:void(0);" class="deleteBtn">删除</a>
                        </div>
                    </div>


           </div>
          			<div class="lineBetween"></div>

                    <div class="tabHintContent">
                        <div class="redHintFonts">注意：</div>
                        <ul class="hintList">
                            <li>1.显示顺序可手动调整；</li>
                            <li>2.每次编辑banner后请先点击预览查看前台页面展示情况，确认无误后点击发布，即可发布新banner。如不点击发布，则关闭页面后本次修改失效。</li>
                        </ul>
                    </div>

                </div>
			<form id="preViewForm">
			<input type="hidden" id="imageIds" name ="imageIds"/>
			</form>
            </section>
        </div>
    <!--   删除操作弹窗   -->
    <div class="modal fade" data-backdrop="static" data-keyboard="false" id="deleteWindow">
        <div class="modal-dialog" style="width:400px;height:215px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">操作提示</h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <i class="warnIcon"></i>
                        <span class="popupContainer-fonts">确认删除所选Banner？</span>
                    </div>
                </div>
                <input type="hidden" id="delPriority" >
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button" id="confirmDel">确认</button>
                    <button type="button" class="commonButton blue-45c8dcButton" id="cancelDel">取消</button>
                </div>
            </div>
        </div>
    </div>


        <div class="popup-saveBox" id="save-success">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-success"></span><span>保存并发布成功！</span>
            </div>
        </div>
    </div>

            <div class="popup-saveBox" id="imageUploadSuccessTip">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-success"></span><span>上传成功！</span>
            </div>
        </div>
    </div>

      <div class="popup-saveBox" id="save-failed">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-cancel"></span><span>保存并发布失败！</span>
            </div>
        </div>
    </div>

     <div class="popup-saveBox" id="bannerMaxLengthTip">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-cancel"></span><span>最多只能六个banner！</span>
            </div>
        </div>
    </div>

         <div class="popup-saveBox" id="bannerMinLengthTip">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-cancel"></span><span>最少有一个banner！</span>
            </div>
        </div>
    </div>

         <div class="popup-saveBox" id="bannerTitleTip">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-cancel"></span><span>标题输入有误</span>
            </div>
        </div>
    </div>

      <div class="popup-saveBox" id="urlTip">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-cancel"></span><span>跳转链接输入有误</span>
            </div>
        </div>
    </div>

       <div class="popup-saveBox" id="bannerPreviewTip">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-cancel"></span><span>banner预览失败</span>
            </div>
        </div>
    </div>

      <div class="popup-saveBox" id="imageTip">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-cancel"></span><span>立即上传图片！</span>
            </div>
        </div>
    </div>

          <div class="popup-saveBox" id="imageSizeTip">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-cancel"></span><span>仅可上传2M以下jpg、png、jpeg格式图片</span>
            </div>
        </div>
    </div>

       <div class="popup-saveBox" id="imageUploadFailedTip">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-cancel"></span><span>上传失败，请稍后重试</span>
            </div>
        </div>
    </div>

    </@html.htmlIndex>

