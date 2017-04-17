<#import "/common/opera/htmlIndex.ftl" as html/>
<#import "/common/opera/main_header.ftl" as main_header/>
<@html.htmlIndex jsFiles=[ "operator/basicData/uploadProductimg.js", "common/jquery-form.js","operator/basicData/recProduct.js","js/maintain/jquery-ui.min.js"] cssFiles=["css/maintain/operatorManagement.css"]
curModule="运营管理" title="推荐产品管理">
    <@main_header.header currentMenu="运营管理" currentSubMenu="推荐产品管理">
	</@main_header.header>

	<div class="main-container">
           <section class="whiteBg">

                <div  class="btnDiv clearfix">
                    <div class="leftBtn">
                        <button class="commonButton blue-45c8dcButton changeBtn addContentItem"><i class="whiteAddIcon"></i>添加推荐产品组</button>
                    </div>
                    <div class="rightBtn">

                     <form id="previewform">
                      <input type="hidden" id="recproductlist" name="recproductlist"/>
                      </form>

                        <button class="commonButton red-fe6869Button" id="preview">预 览</button>
                        <button class="commonButton blue-45c8dcButton save">保存并发布</button>
                    </div>
                </div>

                <div class="material-table-container">

                    <div class="tabTitle clearfix changeWidth">
                        <span class="title01">显示顺序</span>
                        <span class="title02">主标题</span>
                        <span class="title03">副标题</span>
                        <span class="title04">产品ID</span>
                        <span class="title05">产品图片</span>
                        <span class="title06">操作</span>
                    </div>


                    <div class="material-info-list-container">


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
            </section>
       </div>


<!-- 添加组模板     -->
                <div class="material-info-item-addgroup changeWidth clearfix" style="display:none">

                            <div class="groupTitle clearfix">
                                <div class="leftTitle">第<span class="groupNum">1</span>组：推荐产品</div>
                                <a href="javascript:void(0);" class="rightBtn deleteBtn">删除该组</a>
                            </div>
                            <div class="groupListContent">

							<div class="groupList clearfix">
                                    <div class="number commonStyle">1</div>
                                    <div class="commonStyle row02">
                                        <input type="text" class="mainTitle" name="mainTitle" value="${(RecProductEntity.mainTitle)!}" style="width: 98%;">

                                    </div>
                                    <div class="commonStyle row03">
                                        <input type="text" class="viceTitle" name="viceTitle" value="${(RecProductEntity.viceTitle)!}" style="width: 98%;">

                                    </div>
                                    <div class="commonStyle row04">
                                        <input type="text" class="productId" name="productId" value="${(RecProductEntity.productId)!}" style="width: 98%;">

                                    </div>

                   <#if (RecProductEntity.pictureId)??>
                   <div class="commonStyle row05 upLoadProductImg">
                                    <img src="${mediaserver}imageservice?mediaImageId=${RecProductEntity.pictureId!}" class="uploadImg">
                                     <form name="pictureform" action="${basepath}/product/back/hotel/uploadPicture" method="post" enctype="multipart/form-data">
                                            <div class="showUpload">
                                                <input type="file" class="againUpload" name="picture">
                                                 <input type="hidden" name-value="pictureId" value="${(RecProductEntity.pictureId)!}" >
                                                <a href="javascript:void(0);" class="againUploadFonts">重新上传</a>
                                            </div>
                                        </form>
                                        </div>
                            <#else>
                            <div class="commonStyle row05 instantUpload">
                                <form name="pictureform" action="${basepath}/product/back/hotel/uploadPicture" method="post" enctype="multipart/form-data">
										<input type ="hidden" name-value="pictureId" />
                                         <input type="file"  name="picture" class="instantUploadFile">
                                        <span class="instantUploadStyle changeImgWidth">
                                            <span class="instantUploadIcon"></span>
                                            <span style="padding-top: 23px;">立即上传</span>
                                        </span>
                                </form>
                                </div>
                                </#if>

                                    <div class="commonStyle row06">
                                        <a href="javascript:void(0);" class="clearContent">清空内容</a>
                                    </div>
                                </div>


                         </div>
                </div>



		<!--   显示模板   -->
		<div class="material-info-item-showgroup changeWidth clearfix" style="display:none">

                            <div class="groupTitle clearfix">
                                <div class="leftTitle">第<span class="groupNum">1</span>组：推荐产品</div>
                                <a href="javascript:void(0);" class="rightBtn deleteBtn">删除该组</a>
                            </div>
                            <div class="groupListContent">



							 <#list recproductlist as RecProductEntity>

                               <div class="groupList clearfix">
                                    <div class="number commonStyle">1</div>
                                    <div class="commonStyle row02">
                                        <input type="text" class="mainTitle" name="mainTitle" value="${(RecProductEntity.mainTitle)!}" style="width: 98%;">

                                    </div>
                                    <div class="commonStyle row03">
                                        <input type="text" class="viceTitle" name="viceTitle" value="${(RecProductEntity.viceTitle)!}" style="width: 98%;">

                                    </div>
                                    <div class="commonStyle row04">
                                        <input type="text" class="productId" name="productId" value="${(RecProductEntity.productId)!}" style="width: 98%;">

                                    </div>
                                    <#if (RecProductEntity.pictureId)??>
                   <div class="commonStyle row05 upLoadProductImg">
                                    <img src="${mediaserver}imageservice?mediaImageId=${RecProductEntity.pictureId!}" class="uploadImg">
                                     <form name="pictureform" action="${basepath}/product/back/hotel/uploadPicture" method="post" enctype="multipart/form-data">
                                            <div class="showUpload">
                                                <input type="file" class="againUpload" name="picture">
                                                 <input type="hidden" name-value="pictureId" value="${(RecProductEntity.pictureId)!}" >
                                                <a href="javascript:void(0);" class="againUploadFonts">重新上传</a>
                                            </div>
                                        </form>
                                        </div>
                            <#else>
                            <div class="commonStyle row05 instantUpload">
                                <form name="pictureform" action="${basepath}/product/back/hotel/uploadPicture" method="post" enctype="multipart/form-data">
										<input type ="hidden" name-value="pictureId" />
                                         <input type="file"  name="picture" class="instantUploadFile">
                                        <span class="instantUploadStyle changeImgWidth">
                                            <span class="instantUploadIcon"></span>
                                            <span style="padding-top: 23px;">立即上传</span>
                                        </span>
                                </form>
                                </div>
                                </#if>
                                    <div class="commonStyle row06">
                                        <a href="javascript:void(0);" class="clearContent">清空内容</a>
                                    </div>
                                </div>
                     </#list>


                           </div>
       </div>

       <!--   立即上传内容模板   -->
       <script type="text/html" id="instantUploadTemp">
 			<form name="pictureform" action="${basepath}/product/back/hotel/uploadPicture" method="post" enctype="multipart/form-data">
					<input type ="hidden" name-value="pictureId" />
                     <input type="file"  name="picture" class="instantUploadFile">
                    <span class="instantUploadStyle changeImgWidth">
                        <span class="instantUploadIcon"></span>
                        <span style="padding-top: 23px;">立即上传</span>
                    </span>
            </form>
       </script>


    <!--   删除组操作弹窗   -->
    <div class="modal fade" data-backdrop="static" data-keyboard="false" id="deletegroupWindow">
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
                        <span class="popupContainer-fonts">确认删除？</span>
                       <input type="hidden" id="delgrpriority" >
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button confirm">确认</button>
                    <button type="button" class="commonButton blue-45c8dcButton" data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </div>


    <!--   删除行操作弹窗   -->
    <div class="modal fade" data-backdrop="static" data-keyboard="false" id="deletedetailWindow">
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
                        <span class="popupContainer-fonts">确认删除？</span>
                       <input type="hidden" id="deldepriority" >
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button confirmd">确认</button>
                    <button type="button" class="commonButton blue-45c8dcButton" data-dismiss="modal">取消</button>
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


      <div class="popup-saveBox" id="save-failed">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-cancel"></span><span>保存并发布失败！</span>
            </div>
        </div>
    </div>

     <div class="popup-saveBox" id="groupMaxLengthTip">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-cancel"></span><span>最多只能5组！</span>
            </div>
        </div>
    </div>



         <div class="popup-saveBox" id="mainTitleTip">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-cancel"></span><span>主标题输入有误</span>
            </div>
        </div>
    </div>

      <div class="popup-saveBox" id="viceTitleTip">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-cancel"></span><span>副标题输入有误</span>
            </div>
        </div>
    </div>


      <div class="popup-saveBox" id="productIdTip">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-cancel"></span><span>产品Id输入有误</span>
            </div>
        </div>
    </div>

       <div class="popup-saveBox" id="imageTip">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-cancel"></span><span>未选择上传图片！</span>
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


                <div class="popup-saveBox" id="imageUploadSuccessTip">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-success"></span><span>上传成功！</span>
            </div>
        </div>
    </div>

        <div class="popup-saveBox" id="searchId-fail">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-cancel"></span><span>无此上线产品Id信息,请重新填写产品Id!</span>
            </div>
        </div>
    </div>

        <div class="popup-saveBox" id="group-fail">
        <div class="saveBox-wrapper">
            <div class="saveBox-wrapperCnt">
                <span class="popupCommonImg popupCommonImg-cancel"></span><span>未添加任何组!</span>
            </div>
        </div>
    </div>


</@html.htmlIndex>








