<#import "/common/opera/htmlIndex.ftl" as html/>
<#import "product_menu.ftl" as productMenu/>
<@html.htmlIndex jsFiles=["js/vendor/jquery.tagsinput.js","js/maintain/customValidator.js","product/back/hotel/jquery-form.js",
	"product/back/basic_info.js","product/back/common_utils.js","common/ZtrDropDown.js"]
	cssFiles=["css/maintain/productManagement.css","css/jquery.tagsinput.css",
	"css/bootstrap-datepicker.min.css"]
	curModule="产品管理"
	title="基本信息">
	<@productMenu.productMenu curProductModule="basicInfo">
	<script type="text/javascript">
	var ADDRESS = eval("(${(ADDRESS)!})");
	</script>
    <div class="main-container changeMainContent">
        <section class="whiteBg">
            <table class="noBorderCommonTab">
                <colgroup>
                    <col width="110">
                    <col width="850">
                </colgroup>
                <tbody>
                <tr>
                    <th>主 &nbsp; 标 &nbsp; 题</th>
                    <td>
                        <input id='productName_input' type="text" style="width: 700px;" value="${(basicInfo.productName)!}" maxlength=60>
                        <input id='productId_hidden' type='hidden' value='${(basicInfo.id)!}'/>
                        <input id='productPid_hidden' type='hidden' value='${(basicInfo.pid)!}'/>
                        <input id='status_hidden' type='hidden' value='${(basicInfo.status)!}'/>
                        <input id='progress_hidden' type='hidden' value='${(basicInfo.progress)!0}'/>
                    </td>
                </tr>
                <tr>
                    <th>副 &nbsp; 标 &nbsp; 题</th>
                    <td>
                        <input id='productSubName_input' type="text" style="width: 700px;" value="${(basicInfo.productSubName)!}" maxlength=60>
                    </td>
                </tr>
                <tr>
                    <th>推 &nbsp; 荐 &nbsp; 语</th>
                    <td>
                        <input id='recommendWords' type="text" style="width: 700px;" value="${(basicInfo.recommendWords)!}" maxlength=60>
                    </td>
                </tr>
                <tr>
                    <th>主 &nbsp; 题</th>
                    <td>
                        <div class="dropdown" style="width: 110px;">
                            <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false">
                                <span class="activeFonts">${(basicInfo.theme)!}</span>
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu" id='productTheme_dropdown'>
                                <#if themes??>
                                <#list themes as theme>
                                <li <#if (basicInfo.theme)! == theme!>class="active"</#if>><a href="javascript:void(0);">${theme!''}</a></li>
                                </#list>
                                </#if>
                            </ul>
                        </div>
                        <span class="replaceTh">天 &nbsp; 数</span>
                        <span>
                            <input type="text" id='tripNights_input' value="${(basicInfo.tripNights)!}" style="width:70px;" maxlength=2>晚&nbsp;
                        </span>
                        <span>
                            <input type="text" id='tripDays_input' value="${(basicInfo.tripDays)!}" style="width:70px;" maxlength=2> 天
                        </span>
                    </td>
                </tr>
                <tr>
                    <th>标 &nbsp;签</th>
                    <td>
                        <input type="text" id="form_tags_input" class="tagInput">
                        <script>
                        var tagsArray = [];
                        <#list (basicInfo.tags)! as t>
                        	tagsArray.push("${t}");
                        </#list>
                        </script>
                    </td>
                </tr>
                <tr>
                    <th></th>
                    <td>
                        <div class="hotLabel clearfix">
                            <div class="catalog">热门标签</div>
                            <ul class="hotTag">
                                <#if hotTags??>
                                <#list hotTags as hotTag>
                                <li class="lightTag-${hotTag_index%4}">${hotTag!''}</li>
                                </#list>
                                </#if>
                            </ul>
                            <div id="hotTagSlideBtn-arrow" class="hotTagSlideShow">
                                <i class="hotTagSlideBtn"></i>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>出 发 地</th>
                    <td>
                        <div class="dropdown" style="width: 110px;">
                            <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false">
                                <span class="activeFonts">${(basicInfo.from)!}</span>
                                <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu" id='from_input'>
                                <#if departurePlaces??>
                                <#list departurePlaces as depart>
                                <li <#if (basicInfo.from)! == depart!>class="active"</#if>><a href="javascript:void(0);">${depart!''}</a></li>
                                </#list>
                                </#if>
                            </ul>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th class="verticalTop" style="padding-top: 15px;">目 的 地</th>
                    <td class="clearfix">
                        <div class="pull-left">
                            <div class="dropdown" style="width: 110px;" id='address_continent'>
                                <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false" title='全部'>
                                    <span class="activeFonts">全部</span>
                                    <span class="caret"></span>
                                </a>
                                <ul class="dropdown-menu">
                                </ul>
                            </div>
                            <div class="dropdown" style="width: 110px;" id='address_country'>
                                <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false" title='全部'>
                                    <span class="activeFonts">全部</span>
                                    <span class="caret"></span>
                                </a>
                                <ul class="dropdown-menu">
                                </ul>
                            </div>
                            <div class="dropdown" style="width: 110px;" id='address_region'>
                                <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false" title='全部'>
                                    <span class="activeFonts">全部</span>
                                    <span class="caret"></span>
                                </a>
                                <ul class="dropdown-menu">
                                </ul>
                            </div>
                            <button class="commonButton blue-45c8dcButton addcity" id='addCity_button'><i class="whiteAddIcon"></i>添 加</button>
                        </div>
                        <div class="smallTagModel" id='to_labels'>
                        <#list (basicInfo.to)! as city>
                            <label class="smallTagStyle">
                                <span class="closeFonts" continent="${(basicInfo.toContinent[city_index])!}" country="${(basicInfo.toCountry[city_index])!}">${(city)!}</span>
                                <span class="closeIcon"  onclick="closeSelf(this);"></span>
                            </label>
                        </#list>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>目的地类型</th>
                    <td>
                        <div class="radioContent" id='destination_radio'>
                            <label class="radio <#if (basicInfo.destinationType)! =='DOMESTIC'>active</#if>" value="DOMESTIC">
                                <span class="radioIcon"></span>
                                <span class="labelFonts">国内</span>
                            </label>
                            <label class="radio <#if (basicInfo.destinationType)! =='INTERNATIONAL'>active</#if>" value="INTERNATIONAL">
                                <span class="radioIcon"></span>
                                <span class="labelFonts">国际</span>
                            </label>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>产品性质</th>
                    <td>
                        <div class="radioContent" id='nature_radio'>
                            <label class="radio <#if (basicInfo.nature)! =='PACKAGE'>active</#if>" value="PACKAGE">
                                <span class="radioIcon"></span>
                                <span class="labelFonts">打包</span>
                            </label>
                            <label class="radio <#if (basicInfo.nature)! =='COMBINATION'>active</#if>" value="COMBINATION">
                                <span class="radioIcon"></span>
                                <span class="labelFonts">自组</span>
                            </label>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>产品内容</th>
                    <td>
                        <div class="checkboxContent" id='contents_checkbox'>
                            <label class="checkboxInfo <#list ((basicInfo.contents)!) as co><#if (co)! == 'FLIGHT'>active</#if></#list>" value="FLIGHT">
                                <span class="checkboxIcon"></span>
                                <span class='checkboxFonts'>机票</span>
                            </label>
                            <label class="checkboxInfo <#list ((basicInfo.contents)!) as co><#if (co)! == 'HOTEL'>active</#if></#list>" value="HOTEL">
                                <span class="checkboxIcon"></span>
                                <span class='checkboxFonts'>酒店</span>
                            </label>
                            <label class="checkboxInfo <#list ((basicInfo.contents)!) as co><#if (co)! == 'SHUTTLE'>active</#if></#list>" value="SHUTTLE">
                                <span class="checkboxIcon"></span>
                                <span class='checkboxFonts'>接送机</span>
                            </label>
                            <label class="checkboxInfo <#list ((basicInfo.contents)!) as co><#if (co)! == 'ZENBOOK'>active</#if></#list>" value="ZENBOOK">
                                <span class="checkboxIcon"></span>
                                <span class='checkboxFonts'>真旅本子</span>
                            </label>
                            <label class="checkboxInfo <#list ((basicInfo.contents)!) as co><#if (co)! == 'WIFI'>active</#if></#list>" value="WIFI">
                                <span class="checkboxIcon"></span>
                                <span class='checkboxFonts'>WIFI</span>
                            </label>
                            <label class="checkboxInfo <#list ((basicInfo.contents)!) as co><#if (co)! == 'OTHER'>active</#if></#list>" value="OTHER">
                                <span class="checkboxIcon"></span>
                                <span class='checkboxFonts'>其他</span>
                            </label>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th class="verticalTop paddingTop10">体验亮点</th>
                    <td class="marginBottom10" id='highLights_inputs'>
                    	<input type="text" style="width: 700px;" value="${(basicInfo.highLightTitles[0])!}" maxlength="20">
                        <textarea style="width: 700px;height:140px;">${(basicInfo.highLights[0])!}</textarea>
                        <input type="text" style="width: 700px;" value="${(basicInfo.highLightTitles[1])!}" maxlength="20">
                        <textarea style="width: 700px;height:140px;">${(basicInfo.highLights[1])!}</textarea>
                        <input type="text" style="width: 700px;" value="${(basicInfo.highLightTitles[2])!}" maxlength="20">
                        <textarea style="width: 700px;height:140px;">${(basicInfo.highLights[2])!}</textarea>
                        <input type="text" style="width: 700px;" value="${(basicInfo.highLightTitles[3])!}" maxlength="20">
                        <textarea style="width: 700px;height:140px;">${(basicInfo.highLights[3])!}</textarea>
                    </td>
                </tr>
                <tr>
                    <th>亮点配色</th>
                    <td>
                        <input type="text" id="lightColor_input" value="${(basicInfo.lightColor)!}" style="width:100px;" maxlength=6 >
                    </td>
                </tr>
                <tr <#if (basicInfo.images)??>style="display:none;"</#if>>
                    <th class="verticalTop paddingTop10">高清大图</th>
                    <td>
                        <div>（1920*1200）JPG/PNG图</div>
                    	<form action="${basepath}/upload/image" method="post" enctype="multipart/form-data">
                            <ul class="hotelImgList clearfix">
	                            <li>
	                                <input type="file" name="imageInputer" class="upLoading" onchange="uploadImg(this);" imgStyle="width:640px;height:400px;margin-left:30px;">
	                            </li>
	                        </ul>
                        </form>
                    </td>
                </tr>
                <tr <#if !((basicInfo.images)??)>style="display:none;"</#if>>
                    <th class="verticalTop paddingTop10">高清大图</th>
                    <td>
                        <div>（1920*1200）JPG/PNG图</div>
                        <ul class="hotelImgList bigImgList clearfix" id="imgUl">
                            <#list (basicInfo.images)! as im>
	                        	<li>
		                        	<img src="${mediaserver}imageservice?mediaImageId=${im}" value="${im}"  style="width:640px;height:400px;margin-left:30px;">
			    					<div class="hoverDelete" onclick="rmImgSelf(this);"><i class="delIcon"></i>删除</div>
		    					</li>
	                        </#list>
                        </ul>
                    </td>
                </tr>
                <!--列表标题图层-->
                <tr <#if (basicInfo.titleImages)??>style="display:none;"</#if>>
                    <th class="verticalTop paddingTop10">列表标题图层</th>
                    <td>
                        <div>（1920*450）JPG/PNG图</div>
                    	<form action="${basepath}/upload/image" method="post" enctype="multipart/form-data">
                            <ul class="hotelImgList clearfix">
	                            <li>
	                                <input type="file" name="imageInputer" class="upLoading" onchange="uploadImg(this);" imgStyle="width:640px;height:150px;margin-left:30px;">
	                            </li>
	                        </ul>
                        </form>
                    </td>
                </tr>
                <tr <#if !((basicInfo.titleImages)??)>style="display:none;"</#if>>
                    <th class="verticalTop paddingTop10">列表标题图层</th>
                    <td>
                        <div>（1920*450）JPG/PNG图</div>
                        <ul class="hotelImgList bigImgList clearfix" id="titleImgUl">
                            <#list (basicInfo.titleImages)! as im>
	                        	<li>
		                        	<img src="${mediaserver}imageservice?mediaImageId=${im}" value="${im}"  style="width:640px;height:150px;margin-left:30px;">
			    					<div class="hoverDelete" onclick="rmImgSelf(this);"><i class="delIcon"></i>删除</div>
		    					</li>
	                        </#list>
                        </ul>
                    </td>
                </tr>
                <!--详情标题图层-->
                <tr <#if (basicInfo.detailTitleImages)??>style="display:none;"</#if>>
                    <th class="verticalTop paddingTop10">详情标题图层</th>
                    <td>
                        <div>（1920*450）JPG/PNG图</div>
                    	<form action="${basepath}/upload/image" method="post" enctype="multipart/form-data">
                            <ul class="hotelImgList clearfix">
	                            <li>
	                                <input type="file" name="imageInputer" class="upLoading" onchange="uploadImg(this);" imgStyle="width:640px;height:150px;margin-left:30px;">
	                            </li>
	                        </ul>
                        </form>
                    </td>
                </tr>
                <tr <#if !((basicInfo.detailTitleImages)??)>style="display:none;"</#if>>
                    <th class="verticalTop paddingTop10">详情标题图层</th>
                    <td>
                        <div>（1920*450）JPG/PNG图</div>
                        <ul class="hotelImgList bigImgList clearfix" id="detaiTitleImgUl">
                            <#list (basicInfo.detailTitleImages)! as im>
	                        	<li>
		                        	<img src="${mediaserver}imageservice?mediaImageId=${im}" value="${im}"  style="width:640px;height:150px;margin-left:30px;">
			    					<div class="hoverDelete" onclick="rmImgSelf(this);"><i class="delIcon"></i>删除</div>
		    					</li>
	                        </#list>
                        </ul>
                    </td>
                </tr>
                </tbody>
            </table>
        </section>

    </div>
</@productMenu.productMenu>
</@html.htmlIndex>