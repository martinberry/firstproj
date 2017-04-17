<#import "/common/opera/htmlIndex.ftl" as html/>
<#import "newTopic.ftl" as newTopic/>
<@html.htmlIndex
	jsFiles=["js/base/jquery-1.11.2.min.js",
	         "js/bootdist/bootstrap.min.js",
	         "js/vendor/bootstrap-datepicker.min.js",
    		 "js/vendor/bootstrap-datepicker.zh-CN.min.js",
    		 "js/maintain/common.js",
    		 "common/jquery-form.js",
    		 "js/maintain/jquery-ui.min.js",   		 
    		 "back/topic/newTopic.js",
    		 "back/topic/uploadgiftImage.js",
    		 "common/pagination.js"
			 ]			 
	cssFiles=["css/common.css",	
	          "css/maintain/common.css",
	          "css/bootstrap.min.css",
	          "css/bootstrap.reset.css",
	          "css/bootstrap-datepicker.min.css",	                    
	          "css/maintain/operatorManagement.css"			  
			  ]			
	curModule="运营管理"
	title="新增话题">
		
	<@newTopic.newTopic operation="newTopic">
        <div class="main-container changeMainContent">
         <form id="inform">
         <#if topicDetail ??>
         <input type="hidden" value="${(topicDetail.topicId)!''}" name="topicId" class="topicId" />
         <input type="hidden" value="${(topicDetail.status)!''}" name="status" class="status" />
         <#else>
         <input type="hidden" name="topicId" class="topicId" />
         </#if>       
            <section class="whiteBg spaceBottom15">
                <table class="activityTable">
                    <colgroup>
                        <col width="90" />
                        <col width="875" />
                    </colgroup>                   
                    <tbody>
                        <tr>
                            <th>标题</th>
                            <td>
                            <#if topicDetail ??>
                                <input type="text" class="topicTitle" value="${(topicDetail.topicTitle)!''}" name="topicTitle" style="width:860px;"/>
                            <#else>
                                <input type="text" class="topicTitle" name="topicTitle" style="width:860px;" placeholder="100字符以内"/>
                            </#if>      
                                  <div class="verifyStyle" style="width: 255px; display: none">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts"></span>
                                  </div>
                            </td>
                        </tr>
                        <tr>
                            <th class="verticalAlign">正文</th>
                            <td>
                            <#if topicDetail ??>
                             		<textarea class="activity-description topicContent" name="topicContent">${(topicDetail.topicContent)!''}</textarea>
                            <#else>
                            		<textarea class="activity-description topicContent" placeholder="限500字以内" name="topicContent"></textarea>
                            </#if>      
                                  <div class="verifyStyle" style="width: 255px; display: none">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts"></span>
                                  </div>
                            </td>
                        </tr>
                        <tr>
                            <th>关联产品</th>
                            <td>
                                <div class="dropdown" id="relatedProduct" style="width: 520px;">
                                    <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false">
                                       <#if selectProduct ??>
                                       <span class="activeFonts">${(selectProduct)!''}</span>
                                       <#else>
                                       <span class="activeFonts">选择关联上线产品</span>
                                       </#if>                
                                        <span class="caret"></span>
                                    </a>
                                    <ul class="dropdown-menu">
                                    <#if selectProduct ??>                                
                                     <li><a href="javascript:void(0);">选择关联上线产品</a></li>    
                                     <#else>
                                     <li class="active"><a href="javascript:void(0);">选择关联上线产品</a></li>
                                     </#if>    
                                     <#if relatedProductList ??>
									<#list relatedProductList as relatedProduct>
									<#if selectProduct?? && selectProduct == (relatedProduct.combinetitle)> 
									<li class="active">									
									<a href=javascript:void(0);">${(relatedProduct.combinetitle)!''}</a>									
									<input type="hidden" name="pid" value="${(relatedProduct.pid)!''}" />
									<input type="hidden" name="productTitle" value="${(relatedProduct.pName)!''}" />
									</li>
									<#else>
								    <li>
									<a href=javascript:void(0);">${(relatedProduct.combinetitle)!''}</a>									
									<input type="hidden" name="pid" value="${(relatedProduct.pid)!''}" />
									<input type="hidden" name="productTitle" value="${(relatedProduct.pName)!''}" />
									</li>
									</#if>
									</#list>
									</#if>
                                    </ul>
                                </div>
                                
                                 <div class="verifyStyle" style="width: 255px; display: none">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts"></span>
                                  </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </section>
            <section class="whiteBg spaceBottom15">
                <div class="checkboxContent">
                    <label class="checkboxInfo">
                        <span class="checkboxIcon"></span>话题礼品
                    </label>
                    <span class="tips_orangeFonts">选中下方展开</span>
                </div>
                <div class="giftBox" style="display: none;">
                    <table class="activityTable">
                        <colgroup>
                            <col width="90" />
                            <col width="875" />
                        </colgroup>
                        <tbody>
                        <tr>
                            <th>礼品标题</th>
                            <td>
                            <#if topicDetail ??>
                                <input type="text" class="giftTitle" value="${(topicDetail.giftTitle)!''}" name="giftTitle" style="width:860px;"/>
                            <#else>
                                 <input type="text" class="giftTitle" name="giftTitle" style="width:860px;" placeholder="100字符以内"/>
                            </#if>     
                                  <div class="verifyStyle" style="width: 255px; display: none">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts"></span>
                                  </div>
                            </td>
                        </tr>
                        <tr class="verticalAlign">
                            <th>礼品图片</th>
                            <td>                     
                       
          
                        <#if (topicDetail.giftImage) ??>
                        
                        <div class="upLoadProductImg upload">
                        <form></form>
                            <img src="${mediaserver}imageservice?mediaImageId=${(topicDetail.giftImage)!}" class="uploadImg" />
                       		<form name="pictureform" action="${basepath}/product/back/hotel/uploadPicture" method="post" enctype="multipart/form-data">
                                <div class="showUpload">
                                    <input type="file" class="againUpload" name="picture" />
                                    <input type ="hidden" name-value="pictureId" value="${(topicDetail.giftImage)!}" class="giftImage" name="giftImage"/>
                                    <a href="javascript:void(0);" class="againUploadFonts">重新上传</a>
                                </div>
                            </form>
                        </div> 
                       
                      
                        <#else>
                         <div class="instantUpload upload">
                         <form></form>
                        	<form name="pictureform" action="${basepath}/product/back/hotel/uploadPicture" method="post" enctype="multipart/form-data">
                              <input type ="hidden" name-value="pictureId" class="giftImage"/>
                              <input type="file" class="instantUploadFile" name ="picture" />
                                    <span class="instantUploadStyle changeImgWidth">                                       
                                        <span class="instantUploadIcon"></span>
                                        <span style="padding-top: 47px;">立即上传</span>
                                    </span>                                 
                            </form>
                        </div>    
                        </#if>
                        
                           
                      <div class="verifyStyle" style="width: 255px; display: none">
                            <i class="verifyIcon"></i>
                            <span class="verifyFonts"></span>
                      </div>
                      
                      </td>
                        </tr>      

                        <tr>
                            <th class="verticalAlign">礼品正文</th>
                            <td>
                            <#if topicDetail ??>
                                <textarea class="activity-description giftContent" name="giftContent">${(topicDetail.giftContent)!''}</textarea>
                            <#else>
                                <textarea class="activity-description giftContent" name="giftContent" placeholder="限500字以内"></textarea>
                            </#if>     
                                 <div class="verifyStyle" style="width: 255px; display: none">
                                        <i class="verifyIcon"></i>
                                        <span class="verifyFonts"></span>
                                 </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </section>
            </form>
            
            <form name="commentcondition">
              <input type="hidden" name="pageNo" value="1" />
			  <input type="hidden" name="pageSize" value="10" /> 
			</form>  
			<input type="hidden" name="sortType" value="time" />
            <section class="whiteBg clearfix" id="searchField">
                <h3 class="sectionTitle">用户评论</h3>
                <table class="commonTab voucherInfoTable">
                    <colgroup>
                        <col width="140">
                        <col width="180">
                        <col width="100">
                        <col width="400">
                        <col width="">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>昵称</th>
                        <th>
                            评论时间
                            <i class="sortArrow upGray commentsort"></i>
                            <!--<i class="sortArrow downGray"></i>
                            <i class="sortArrow allGray"></i>
                            <i class="sortArrow allOrange"></i>-->
                        </th>
                        <th>
                            获赞数
                            <i class="sortArrow upGray praisesort"></i>
                            <!--<i class="sortArrow downGray"></i>
                            <i class="sortArrow allGray"></i>
                            <i class="sortArrow allOrange"></i>-->
                        </th>
                        <th>详情</th>
                        <th>评论管理</th>
                    </tr>
                    </thead>
                    <tbody>
                    
                    </tbody>
                </table>
                <div id="pagination">
                </div>
            </section>
        </div>
    </div>
    </@newTopic.newTopic>
    
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
    
    
      <div class="modal fade" id="deleteComment">
        <div class="modal-dialog" style="width: 500px;">
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
                        <span class="popupContainer-fonts">确认要删除该评论吗？</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button deleteConfirm">确定</button>
                    <button type="button" class="commonButton blue-45c8dcButton" data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </div>


	</@html.htmlIndex>

   

