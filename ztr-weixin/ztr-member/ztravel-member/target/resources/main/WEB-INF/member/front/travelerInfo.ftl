<#import "/common/front/header/navHeader.ftl" as html/>
<#import "/member/front/travelerInfoAdd.ftl" as travelerInfoListAdd>
<@html.navHeader
title="真旅行-常用旅客信息"
currentMenu="个人中心"
remoteJsFiles=["js/vendor/bootstrap-datepicker.min.js","js/vendor/bootstrap-datepicker.zh-CN.min.js"]
localJsFiles=["/member/front/travelerInfo/list.js","/member/front/common_utils.js","common/typeahead.js","common/ZtrDropDown.js",	"common/ChinesePY.js","common/identityCodeValid.js"]
remoteCssFiles=["css/bootstrap-datepicker.min.css","css/client/userInfo.css"]
localCssFiles=[]>

		<div class="main-wrapper" id="main-wrapper">
			<div class="main-box" id="main-box" style="top:30px;">
				<div class="top-border"><span class="clip"></span></div>
				<div class="box-container">
					<div class="cont-block clearfix">
                        <span class="modelLine"></span>
						<aside class="leftMenuContent">
                            <ul>
                                <li>
                                    <a href="${basepath}/member/info">个人资料</a>
                                </li>
                                <li class="active">
                                    <a href="${basepath}/travelerInfo/list">常用旅客信息</a>
                                </li>
                            </ul>
						</aside>
                        <section class="rightContent travelerInfoListModel">
                            <div class="titleContent clearfix">
                                <b class="titleFonts addTravelerBtn"><i class="commonIcon addIcon"></i> 添加常用旅客</b>
                            </div>
                            <ul class="travelInfoContent clearfix">
							<#if travelerList??>
						    <#list travelerList as travelInfo>
						    	<#if travelInfo.isDefault == "true">
						    		<li class="active">
						    	<#else>
						    		<li>
						    	</#if>
						            <span class="leftImg">
						                <img src="${host}/images/client/avatar-01.jpg">
						            </span>
						            <span class="rightFonts">
						            	<#if (travelInfo.travelerNameCn)?? >
							                <div>
							                    <span class="commonIcon userIcon"></span>
							                    <span id="nameCnList"><#if (travelInfo.travelerNameCn)?length gt 6>${travelInfo.travelerNameCn ?substring(0,6)} ...<#else>${travelInfo.travelerNameCn}</#if></span>
							                </div>
										</#if>
						            	<#if (travelInfo.travelerNameEn)?? >
							                <div>
							                    <span class="commonIcon ENIcon"></span>
							                    <span id="nameEnList"><#if (travelInfo.travelerNameEn)?length gt 7>${travelInfo.travelerNameEn ?substring(0,7)} ...<#else>${travelInfo.travelerNameEn}</#if></span>
							                </div>
							            </#if>

							                <div>
							                    <span class="commonIcon phoneNumberIcon"></span>
							                    <#if (travelInfo.phoneNum)?? >
							                    <span>${travelInfo.phoneNum}</span>
							                    </#if>
							                </div>

						            </span>
						            <span class="defaultFonts">默认</span>
						            <div class="hoverContent">
						                <span class="hoverBtn" id="${travelInfo.travelerId}">
						                    <span class="commonIcon bigEditIcon"></span>
						                    <span class="commonIcon bigDeleteIcon"></span>
						                </span>
						                <span class="defaultFonts">默认</span>
						            </div>
						        </li>
						    </#list>
						    </#if>

                            </ul>

                        </section>

						<section style="float:left;width:80.3%">
	                        <@travelerInfoListAdd.add>
	                        </@travelerInfoListAdd.add>

	                       <div id="travelerInfoSync" >

						</div>
						</section>
					</div>
				</div>
			</div>


			    <!--   删除二次确认  模板 -->
    <div class="modal fade" data-backdrop="static" data-keyboard="false" id="delGuestWindow">
        <div class="modal-dialog" style="width: 400px;height:180px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">删除确认</h4>
                </div>
                <div class="modal-body">
                    <p>确认删除当前常旅客？</p>
                </div>
                <div class="modal-footer">
                    <a href="javascript:void(0);" class="commonBtn blueBtn delete">确认</a>
                    <a href="javascript:void(0);" class="commonBtn orangeBtn" data-dismiss="modal">取消</a>
                </div>
            </div>
        </div>
    </div>


</@html.navHeader>

