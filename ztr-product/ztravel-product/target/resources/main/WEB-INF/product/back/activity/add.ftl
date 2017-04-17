<#import "/common/opera/htmlIndex.ftl" as html/>
<#import "activityMenu.ftl" as activityMenu/>
<@html.htmlIndex
	jsFiles=["js/vendor/jquery.tagsinput.js",
			 "js/vendor/bootstrap-datepicker.min.js",
    		 "js/vendor/bootstrap-datepicker.zh-CN.min.js",
    		 "product/back/activity/activity.js",
    		 "product/back/activity/activityValidator.js",
			 "product/back/activity/add.js"]
	cssFiles=["css/maintain/operatorManagement.css",
			  "css/jquery.tagsinput.css",
			  "css/bootstrap-datepicker.min.css"]
	curModule="运营管理"
	title="新增活动">
	<@activityMenu.activityMenu curStatus="DRAFT" operation="add" activityType="${type!}">
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
                            	<input type="hidden" id="activityId" value="">
                            	<input type="hidden" id="type" value="${type!}">
                            	<input type="hidden" id="activityCode" value="">
                                <input type="text" style="width:320px;" id="name" value="" data-cv="required" data-cp="right" maxlength="20" />
                            </td>
                        </tr>
                        <tr>
                            <th class="verticalAlign">活动说明</th>
                            <td>
                                <textarea class="activity-description" data-cv="" placeholder="限500字以内" id="remark" maxlength="500"></textarea>
                            </td>
                        </tr>
                        <tr>
                            <th><em class="redStar">*</em>活动时间</th>
                            <td>
                                <input type="text" id="startTime"  data-cp="top" style="width: 130px;" class="datepicker startDate hasIcon" readonly="readonly">
                                <input type="text" data-cv="hour" id="startHourFrom" style="width: 40px;" maxlength="2"> 时
                                <input type="text" data-cv="minute" id="startMinFrom" style="width: 40px;" maxlength="2"> 分
                                <em> 至 </em>
                                <input type="text" data-cv="required" id="endTime" data-cp="top" style="width: 130px;" class="datepicker endDate hasIcon" readonly="readonly">
                                <input type="text" data-cv="hour" id="endHourTo" data-cv="required,number" style="width: 40px;" maxlength="2"> 时
                                <input type="text" data-cv="minute" id="endMinTo" data-cv="required,number" style="width: 40px;" maxlength="2"> 分
                            </td>
                        </tr>
                        <tr>
                            <th class="verticalAlign"><em class="redStar">*</em>用户范围</th>
                            <td class="userRangeCont">
                                <div class="radioContent grantType">
                                    <label class="radio active setUserBySelect" data-val="BATCHCONFIG">
                                        <span class="radioIcon"></span>
                                        <span class="labelFonts">按条件批量设置</span>
                                    </label>
                                    <label class="radio setUserByInput" data-val="MANUALADD">
                                        <span class="radioIcon"></span>
                                        <span class="labelFonts">手动添加设置</span>
                                    </label>
                                </div>
                                <div class="selectUserBlock">
                                    <div class="radioContent">
                                        <label class="radio active" data-val="CURRENTALL">
                                            <span class="radioIcon"></span>
                                            <span class="labelFonts">现有用户</span>
                                        </label>
                                        <label class="radio" data-val="NEWUSER">
                                            <span class="radioIcon" ></span>
                                            <span class="labelFonts">活动期间新增用户</span>
                                        </label>
                                        <label class="radio" data-val="NEWANDSHARED">
                                            <span class="radioIcon" ></span>
                                            <span class="labelFonts">活动期内新增且参与真旅行分享计划</span>
                                        </label>

                                    <#if type=="CUSTOMERGET">
                                        <label class="radio" data-val="DN">
                                            <span class="radioIcon"></span>
                                            <span class="labelFonts">大宁活动</span>
                                        </label>
                                 </#if>


                                    </div>
                                </div>
                                <div class="inputUserBlock" style="display: none;">
                                    <textarea class="inputUserTextarea" data-cv="userRange" placeholder="输入用户登录名，用逗号隔开" readonly="readonly"></textarea>
                                    <span class="operInputUser edit">
                                        <button class="commonButton orange-f79767Btn editBtn">编辑</button>
                                    </span>
                                    <span class="operInputUser save" style="display: none;">
                                        <button class="commonButton red-fe6869Button saveBtn">保存</button>
                                        <button class="commonButton gray-bbbButton cancelBtn">取消</button>
                                    </span>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <th>活动送券</th>
                            <td><button class="commonButton blue-45c8dcButton addVoucherBtn disabled" style="width:130px;"><i class="whiteAddIcon"></i>添加代金券</button></td>
                        </tr>
                    </tbody>
                </table>
            </section>
        </div>
    </div>
    <#include "template.ftl" />
	</@activityMenu.activityMenu>
	</@html.htmlIndex>