<#import "/common/opera/htmlIndex.ftl" as html/>
<#import "product_menu.ftl" as productMenu/>
<@html.htmlIndex jsFiles=["js/maintain/customValidator.js","product/back/hotel/jquery-form.js","product/back/recommend_trip.js","js/vendor/kindeditor.js","common/KEditor_insertFile.js","product/back/common_utils.js"]
	cssFiles=["css/maintain/productManagement.css"]
	curModule="产品管理"
	title="推荐行程">
	<@productMenu.productMenu curProductModule="recommendTrip">
    <div class="main-container changeMainContent">
    	<input type='hidden' id='productId_hidden' value='${(data.id)!}'/>
    	<input type='hidden' id='productPid_hidden' value='${(data.pid)!}'/>
    	<input id='status_hidden' type='hidden' value='${(data.status)!}'/>
        <input id='progress_hidden' type='hidden' value='${(data.progress)!}'/>
    	<input type='hidden' id='productName_hidden' value='${(data.productName)!}'/>
    	<input type='hidden' id='tripDays_hidden' value='${(data.tripDays)!}'/>
    	<input type='hidden' id='tripNights_hidden' value='${(data.tripNights)!}'/>
    	<input type='hidden' id="accessType_hidden" value='${(mode)!}'/>
        <section class="whiteBg routeBg">
            <!-- left sideNav -->
            <div class="routeLeftSideNav">
                <ul class="routeSideUl">
                    <li class="active"><i class="overview">概览</i><span></span></li>
                	<#list 1..(data.tripDays)! as index>
                    	<li><i class="routeDay">D${(index)!}</i><span>${(data.recommendTrips[index].title)!}</span></li>
                    </#list>
                    <div  id="routeDayModel" style="display:none;"><li><i class="routeDayModel">D<span></span></i><span></span></li></div>
                    <!--<li><i class="addDay">+</i><span></span></li>暂时不要添加按钮-->
                </ul>
            </div>

            <!-- overview 概览 -->
            <div class="routeRightContent" name="overviewTab">
                <div class="routeRightTit">
                    概览
                </div>
                <#assign overviewTrip = (data.recommendTrips[0])!/>
                <textarea class="overviewTextarea editorText" name="content" placeholder="概览内容">${(overviewTrip.content)!}</textarea>
                <div>（XXX*XXX）JPG/PNG图（限１张）</div>
                <ul class="hotelImgList clearfix" name="images" id="overviewImg">
                	<#if (overviewTrip.desItems[0].images)??>
	                    <li>
	                        <img src="${mediaserver}imageservice?mediaImageId=${(overviewTrip.desItems[0].images[0])!}" value="${(overviewTrip.desItems[0].images[0])!}" style="width:660px;height:440px;margin-left:30px;">
	                        <div class="hoverDelete" onclick="rmImgSelf(this);">
	                            <i class="delIcon"></i>删除
	                        </div>
	                    </li>
                    </#if>
	                    <li <#if ((overviewTrip.desItems[0].images)??)>style="display:none;"</#if>>
	                        <form action="${basepath}/upload/compress" method="post" enctype="multipart/form-data">
	                        	<input type="file" name="imageInputer" class="upLoading" onchange="uploadImg(this,1);">
	                        </form>
	                    </li>
                </ul>
            </div>

			<#list 1..(data.tripDays)! as index>
			<#assign trip = (data.recommendTrips[index])!/>

            <div class="routeRightContent" style="display: none;">
                <div class="routeRightTit">
                    第<span>${(index)!}</span>天
                </div>
                <table class="noBorderCommonTab">
                    <colgroup>
                        <col width="100">
                        <col width="">
                    </colgroup>
                    <tbody>
                    <tr>
                        <th>标  题</th>
                        <td>
                            <input type="text" name="title" value="${(trip.title)!}" style="width: 684px;"/>
                        </td>
                    </tr>
                    <tr>
                        <th class="verticalTop">行  程</th>
                        <td>
                            <textarea name="content" class="tripcontent editorText" style="width:684px;height: 210px;">${(trip.content)!}</textarea>
                        </td>
                    </tr>
                    <tr>
                        <th>用  餐</th>
                        <td>
                            <span class="marginLR">早餐</span><input type="text" name="breakfest" value="${(trip.breakfest)!}" style="width:173px;" maxlength=40 />
                            <span class="marginLR">午餐</span><input type="text" name="lunch" value="${(trip.lunch)!}" style="width:173px;" maxlength=40 />
                            <span class="marginLR">晚餐</span><input type="text" name="dinner" value="${(trip.dinner)!}" style="width:173px;" maxlength=40 />
                        </td>
                    </tr>
                     </tbody>


                    <#list (trip.desItems)! as imglist>
                   <tbody class="flag">
                   <tr>
                    <th class="verticalTop paddingTop10">图片</th>
                        <td>
                            <div>（XXX*XXX）JPG/PNG图（限2张）</div>
                            <ul class="hotelImgList clearfix desItem" name="images">
                            	<#list (imglist.images)! as img>
                                <li>
                                    <img src="${mediaserver}imageservice?mediaImageId=${img}" value="${img}">
									<div class="moveLeft"  onclick="moveLeft(this);">左移</div>
									<div class="moveRight"  onclick="moveRight(this);">右移</div>
                                    <div class="hoverDelete" onclick="rmImgSelf(this);">
                                        <i class="delIcon"></i>删除
                                    </div>
                                </li>
                                </#list>
                                <li  <#if (imglist.images)?? && ((imglist.images)?size) gt 1>style="display:none;"</#if>>
                                    <form action="${basepath}/upload/compress" method="post" enctype="multipart/form-data">
			                        	<input type="file" name="imageInputer" class="upLoading" onchange="uploadImg(this,2);">
			                        </form>
                                </li>
                            </ul>
                        </td>
                    </tr>
                    <tr>
                        <th class="verticalTop">${(imglist_index)+1}组图片说明</th>
                        <td>
                            <textarea name="content" class="desItem picdec" style="width:684px;height: 210px;">${(imglist.describe)!}</textarea>
                        </td>
                    </tr>
                      </tbody>
                    </#list>




                </table>



                <div class="oper-desc-block">
                <span class="add-btn"></span>
                <span class="remove-btn"></span>
                </div>

            </div>
            </#list>


        <!--图片和说明模板   -->
           <script type="text/html" id="pictemp">
           <tbody class="flag">
			        <tr>
			                    <th class="verticalTop paddingTop10">图片</th>
			                    <td>
			                        <div>（XXX*XXX）JPG/PNG图（限2张）</div>
			                        <ul class="hotelImgList clearfix desItem" name="images">
			                        <li>
			                        <form action="${basepath}/upload/compress" method="post" enctype="multipart/form-data">
					                        	<input type="file" name="imageInputer" class="upLoading" onchange="uploadImg(this,2);">
					                        </form>
					                 </li>

			                        </ul>
			                    </td>
			        </tr>
			        <tr>
			                    <th class="verticalTop"></th>
			                    <td>
			                        <textarea name="content" class="desItem picdec" style="width:684px;height: 210px;"></textarea>
			                    </td>
			       </tr>
			       </tbody>
		</script>

			<!--for copy-->
			<div id="routeRightContentModel" style="display:none;">
            <div class="routeRightContentModel" style="display: none;"><!-- the model day -->
                    <div class="routeRightTit">
                    第<span><span>天
                    </div>
                    <table class="noBorderCommonTab">
                        <colgroup>
                            <col width="55">
                            <col width="">
                        </colgroup>
                        <tbody>
                        <tr>
                            <th>标  题</th>
                            <td>
                                <input type="text" style="width: 684px;"/>
                            </td>
                        </tr>
                        <tr>
                            <th class="verticalTop">行  程</th>
                            <td>
                                <textarea style="width:684px;height: 210px;"></textarea>
                            </td>
                        </tr>
                        <tr>
                            <th>用  餐</th>
                            <td>
                                <span class="marginLR">早餐</span><input type="text" style="width:173px;" maxlength=40 />
                                <span class="marginLR">午餐</span><input type="text" style="width:173px;" maxlength=40 />
                                <span class="marginLR">晚餐</span><input type="text" style="width:173px;" maxlength=40 />
                            </td>
                        </tr>
                         </tbody>
                        <tbody class="flag">
                        <tr>
                            <th class="verticalTop paddingTop10">图片</th>
                            <td>
                                <div>（XXX*XXX）JPG/PNG图</div>
                                <ul class="hotelImgList clearfix">
                                    <li>
                                        <form action="${basepath}/upload/compress" method="post" enctype="multipart/form-data">
				                        	<input type="file" name="imageInputer" class="upLoading" onchange="uploadImg(this);">
				                        </form>
                                    </li>
                                </ul>
                            </td>
                        </tr>
                        </tbody>
                    </table>
            </div>
            </div>


        </section>
    </div>
	</@productMenu.productMenu>
</@html.htmlIndex>