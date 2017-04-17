<#import "/common/opera/main_header.ftl" as main_header/>
<#import "/common/opera/htmlIndex.ftl" as html/>

<@html.htmlIndex
	jsFiles=["js/vendor/bootstrap-datepicker.min.js",
			 "js/vendor/bootstrap-datepicker.zh-CN.min.js",
			 "product/back/activity/activity.js",
			 "product/back/activity/list.js",
			 "common/pagination.js"]
    cssFiles=["css/bootstrap-datepicker.min.css",
    	 	  "css/maintain/operatorManagement.css",
    	 	  "css/maintain/common.css"]
    curModule="会员管理" title="真旅行网站活动列表页">
    <@main_header.header currentMenu="运营管理" currentSubMenu="网站活动">
        <a href="javascript:;" class="operationDiv" id="addCustomerGet"><i class="plusIcon"></i>新增领券活动</a>
	    <a href="javascript:;" class="operationDiv" id="addSystemSend"><i class="plusIcon"></i>新增送券活动</a>
	</@main_header.header>

	<div class="main-container clearfix">
	            <section class="whiteBg">
	                <form id="searchConditionForm">
	                <input type="hidden" name="pageNo" value="1" />
					<input type="hidden" name="pageSize" value="10" />
	                    <ul class="searchList clearfix">
	                        <li>
	                            <table class="searchTab">
	                                <colgroup>
	                                    <col width="80">
	                                    <col width="220">
	                                </colgroup>
	                                <tbody>
	                                <tr>
	                                    <th>活动名称</th>
	                                    <td>
	                                        <input type="text" name="activityName" placeholder="关键字" maxlength="20">
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th>活动状态</th>
	                                    <td>
	                                        <div class="dropdown" style="width: 200px;">
	                                            <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
	                                                <span class="activeFonts">全部</span>
	                                                <span class="caret"></span>
	                                            </a>
	                                            <ul class="dropdown-menu" id="status">
	                                                <li class="active" data-val=""><a href="javascript:void(0);">全部</a></li>
	                                                <li data-val="DRAFT"><a href="javascript:void(0);">草稿</a></li>
	                                                <li data-val="EFFECTIVE"><a href="javascript:void(0);">已生效</a></li>
	                                                <li data-val="EDN"><a href="javascript:void(0);">已结束</a></li>
	                                                <li data-val="TERMINATED"><a href="javascript:void(0);">已终止</a></li>
	                                            </ul>
	                                        </div>
	                                    </td>
	                                </tr>
	                                </tbody>
	                            </table>
	                        </li>
	                        <li>
	                            <table class="searchTab">
	                                <colgroup>
	                                    <col width="90">
	                                    <col width="280">
	                                </colgroup>
	                                <tbody>
	                                <tr>
	                                    <th>券名</th>
	                                    <td>
	                                        <input type="text" style="width:245px;" name="couponName" placeholder="关键字" maxlength="10">
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th>活动有效期</th>
	                                    <td>
	                                        <input type="text" style="width: 110px;" class="datepicker startDate hasIcon" id="startTime" name="startTime">
	                                        <em> 至 </em>
	                                        <input type="text" style="width: 110px;" class="datepicker endDate hasIcon" id="endTime" name="endTime">
	                                    </td>
	                                </tr>
	                                </tbody>
	                            </table>
	                        </li>
	                        <li>
	                            <table class="searchTab">
	                                <colgroup>
	                                    <col width="80">
	                                    <col width="200">
	                                </colgroup>
	                                <tbody>
	                                <tr>
	                                    <th>活动类型</th>
	                                    <td>
	                                        <div class="dropdown" style="width: 200px;">
	                                            <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
	                                                <span class="activeFonts">全部</span>
	                                                <span class="caret"></span>
	                                            </a>
	                                            <ul class="dropdown-menu" id="type">
	                                                <li class="active" data-val=""><a href="javascript:void(0);">全部</a></li>
	                                                <li data-val="SYSTEMSEND"><a href="javascript:void(0);">系统送券活动</a></li>
	                                                <li data-val="CUSTOMERGET"><a href="javascript:void(0);">用户领券活动</a></li>
	                                            </ul>
	                                        </div>
	                                    </td>
	                                </tr>
	                                <tr>
	                                    <th>活动对象</th>
	                                    <td>
	                                        <div class="dropdown" style="width: 200px;">
	                                            <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
	                                                <span class="activeFonts">全部</span>
	                                                <span class="caret"></span>
	                                            </a>
	                                            <ul class="dropdown-menu" id="grantType">
	                                                <li class="active" data-val=""><a href="javascript:void(0);">全部</a></li>
	                                                <li data-val="CURRENTALL"><a href="javascript:void(0);">当前用户</a></li>
	                                                <li data-val="NEWUSER"><a href="javascript:void(0);">新增用户</a></li>
	                                                <li data-val="MANUALADD"><a href="javascript:void(0);">指定用户</a></li>
	                                                <li data-val="NEWANDSHARED"><a href="javascript:void(0);">新增且参与真旅行分享</a></li>
	                                                <li data-val="DN"><a href="javascript:void(0);">大宁活动用户</a></li>
	                                            </ul>
	                                        </div>
	                                    </td>
	                                </tr>
	                                </tbody>
	                            </table>
	                        </li>

	                         <li>
	                            <table class="searchTab">
	                                <colgroup>
	                                    <col width="80">
	                                    <col width="200">
	                                </colgroup>
	                                <tbody>
	                                <tr>
	                                </tr>
	                                <tr>
	                                    <th></th>
	                                    <td style="text-align: right;">
	                                        <a href="javascript:void(0);" class="lightBlueBtn" id="searchBtn"><i class="searchIcon"></i>搜 索</a>
	                                    </td>
	                                </tr>
	                                </tbody>
	                            </table>
	                        </li>
	                    </ul>
	                </form>
	                <section class="clearfix" id="searchField">
	                    <table class="commonTab">
	                        <colgroup>
	                            <col width="9%">
	                            <col width="13%">
	                            <col width="9%">
	                            <col width="13%">
	                            <col width="11%">
	                            <col width="15%">
	                            <col width="9%">
	                            <col width="9%">
	                            <col width="">
	                        </colgroup>
	                        <thead>
	                        <tr>
	                            <th>活动ID</th>
	                            <th>活动名称</th>
	                            <th>活动类型</th>
	                            <th>券名</th>
	                            <th>活动对象</th>
	                            <th>活动时间</th>
	                            <th>活动状态</th>
	                            <th>操作人</th>
	                            <th>操作</th>
	                        </tr>
	                        </thead>
	                        <tbody>
	                        </tbody>
	                    </table>
                        <div id="pagination">
                    	</div>
	                </section>
	            </section>
	        </div>
 </@html.htmlIndex>