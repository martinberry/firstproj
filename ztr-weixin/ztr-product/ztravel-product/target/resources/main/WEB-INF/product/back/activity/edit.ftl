<#import "/common/opera/htmlIndex.ftl" as html/>
<#import "activityMenu.ftl" as activityMenu/>
<@html.htmlIndex
	jsFiles=["js/vendor/jquery.tagsinput.js",
			 "js/vendor/bootstrap-datepicker.min.js",
			 "product/back/activity/activity.js",
			 "product/back/activity/edit.js",
			 "product/back/activity/caculate.js",
			 "product/back/activity/activityValidator.js",
    		 "js/vendor/bootstrap-datepicker.zh-CN.min.js"]
	cssFiles=["css/maintain/operatorManagement.css",
			  "css/jquery.tagsinput.css",
			  "css/bootstrap-datepicker.min.css"]
	curModule="运营管理"
	title="新增活动">
	<@activityMenu.activityMenu curStatus="${(activity.status)!}" operation="edit" activityType="${(activity.type)!}">
		 <div class="main-container changeMainContent">
            <section class="whiteBg">
                <table class="activityTable">
                    <colgroup>
                        <col width="90" />
                        <col width="875" />
                    </colgroup>
                    <tbody>
                        <tr>
                            <th><em class="redStar">*</em>活动名称</th>
                            <td>
                            	<input type="hidden" id="activityId" value="${(activity.id)!}">
                            	<input type="hidden" id="activityCode" value="${(activity.code)!}">
                            	<input type="hidden" id="status" value="${(activity.status)!}">
                            	<input type="hidden" id="type" value="${(activity.type)!}">
                                <input type="text" style="width:320px;" id="name" data-cv="required" data-cp="right" maxlength="20" value="${(activity.name)!}">
                                <span style="padding-left: 300px;">当前活动状态</span>
                                <span <#if (activity.status)?? && activity.status=='DRAFT'>class="activity-draft"</#if> <#if (activity.status)?? && activity.status=='EFFECTIVE'>class="activity-effect"</#if> <#if (activity.status)?? && activity.status=='TERMINATED'>class="activity-termination"</#if> >${(activity.statusDesc)!}</span>
                            </td>
                        </tr>
                        <tr>
                            <th class="verticalAlign">活动说明</th>
                            <td>
                                <textarea class="activity-description" data-cv="" placeholder="限500字以内" id="remark" maxlength="500">${(activity.remark)!}</textarea>
                            </td>
                        </tr>
                        <tr>
                            <th><em class="redStar">*</em>活动时间</th>
                            <td>
                                <input type="text" id="startTime"  value="${(activity.startTime)!}" style="width: 130px;" class="datepicker startDate hasIcon" readonly="readonly">
                                <input type="text" data-cv="hour" id="startHourFrom" value="${(activity.startHourFrom)!}" style="width: 40px;" maxlength="2"> 时
                                <input type="text" data-cv="minute" id="startMinFrom" value="${(activity.startMinFrom)!}" style="width: 40px;" maxlength="2"> 分
                                <em> 至 </em>
                                <input data-cv="required" type="text" id="endTime" style="width: 130px;" value="${(activity.endTime)!}" class="datepicker endDate hasIcon" readonly="readonly">
                                <input type="text" data-cv="hour" id="endHourTo" data-cv="required,number" value="${(activity.endHourTo)!}" style="width: 40px;" maxlength="2"> 时
                                <input type="text" data-cv="minute" id="endMinTo" data-cv="required,number" value="${(activity.endMinTo)!}" style="width: 40px;" maxlength="2"> 分
                            </td>
                        </tr>
                        <tr>
                            <th class="verticalAlign"><em class="redStar">*</em>用户范围</th>
                            <td class="userRangeCont">
                            <#if (activity.status)?? && activity.status=='DRAFT'>
                                <div class="radioContent grantType">
                                    <label class="radio <#if (activity.grantType)?? && activity.grantType == "BATCHCONFIG">active</#if> setUserBySelect" data-val="BATCHCONFIG">
                                        <span class="radioIcon"></span>
                                        <span class="labelFonts">按条件批量设置</span>
                                    </label>
                                    <label class="radio <#if (activity.grantType)?? && activity.grantType == "MANUALADD">active</#if> setUserByInput" data-val="MANUALADD">
                                        <span class="radioIcon"></span>
                                        <span class="labelFonts">手动添加设置</span>
                                    </label>
                                </div>
                                <div class="selectUserBlock" <#if (activity.grantType)?? && activity.grantType == "BATCHCONFIG">style="display;"<#else>style="display:none;"</#if>>
                                    <div class="radioContent">
                                        <label class="radio <#if (activity.userRangType)?? && activity.userRangType == "CURRENTALL">active</#if>" data-val="CURRENTALL">
                                            <span class="radioIcon"></span>
                                            <span class="labelFonts">现有用户</span>
                                        </label>
                                        <label class="radio <#if (activity.userRangType)?? && activity.userRangType == "NEWUSER">active</#if>" data-val="NEWUSER">
                                            <span class="radioIcon"></span>
                                            <span class="labelFonts">活动期间新增用户</span>
                                        </label>
                                        <label class="radio <#if (activity.userRangType)?? && activity.userRangType == "NEWANDSHARED">active</#if>" data-val="NEWANDSHARED">
                                            <span class="radioIcon"></span>
                                            <span class="labelFonts">活动期内新增且参与真旅行分享计划</span>
                                        </label>

                                         <label class="radio <#if (activity.userRangType)?? && activity.userRangType == "DN">active</#if>" data-val="ALL">
                                            <span class="radioIcon"></span>
                                            <span class="labelFonts">大宁活动</span>
                                        </label>



                                    </div>
                                </div>
                       			<span style="display:none;" id="originUserRange">${(activity.userRangeComplete)!''}</span>
	                            <div class="inputUserBlock" <#if (activity.grantType)?? && activity.grantType == "MANUALADD">style="display;"<#else>style="display:none;"</#if>>
	                                <textarea class="inputUserTextarea" data-cv="userRange" placeholder="输入用户登录名，用逗号隔开" readonly="readonly">${(activity.userRangeComplete)!}</textarea>
	                                <#if (activity.status)?? && activity.status=='DRAFT'>
	                                <span class="operInputUser edit">
	                                    <button class="commonButton orange-f79767Btn editBtn">编辑</button>
	                                </span>
	                                </#if>
	                                <span class="operInputUser save" style="display: none;">
	                                    <button class="commonButton red-fe6869Button saveBtn">保存</button>
	                                    <button class="commonButton gray-bbbButton cancelBtn">取消</button>
	                                </span>
	                            </div>
                               <#else>
                               		<#if (activity.grantType)?? && activity.grantType == "BATCHCONFIG">
                               			<#if (activity.userRangType)?? && activity.userRangType == "CURRENTALL">
                               				现有用户
                               			<#elseif (activity.userRangType)?? && activity.userRangType == "NEWUSER">
                               				活动期间新增用户
                               			<#elseif (activity.userRangType)?? && activity.userRangType == "NEWANDSHARED">
                               				活动期内新增且参与真旅行分享计划
                               			<#elseif (activity.userRangType)?? && activity.userRangType == "DN">
                               			    大宁活动
                               			</#if>
                               		<#else>
										<textarea class="inputUserTextarea" placeholder="输入用户登录名，用逗号隔开" readonly="readonly">${(activity.userRangeComplete)!}</textarea>
                               		</#if>
                               </#if>
                            </td>
                        </tr>
                        <tr>
                            <th>活动送券</th>
                            <td><button class="commonButton blue-45c8dcButton addVoucherBtn <#if activity.status != "EFFECTIVE">disabled</#if>" style="width:130px;"><i class="whiteAddIcon"></i>添加代金券</button></td>
                        </tr>
                    </tbody>
                </table>
                <#if activity.status != "DRAFT">
	                <table class="commonTab voucherInfoTable">
	                    <thead>
	                        <tr>
	                            <th>券码</th>
	                            <th>券名</th>
	                            <th>发放数量</th>
	                            <th>发行量</th>
	                            <th>发送情况</th>
	                            <th>操作</th>
	                        </tr>
	                    </thead>
	                    <tbody>
		                    <div id="pagination">
			                </div>
	                    </tbody>
	                </table>
                </#if>
            </section>
        </div>
    </div>
    <#include "template.ftl" />
	</@activityMenu.activityMenu>
	</@html.htmlIndex>