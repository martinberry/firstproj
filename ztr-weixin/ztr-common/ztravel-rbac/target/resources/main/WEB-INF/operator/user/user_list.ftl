<#import "/common/opera/main_header.ftl" as main_header/>
<#import "/common/opera/htmlIndex.ftl" as html/>

<@html.htmlIndex jsFiles=["js/vendor/bootstrap-datepicker.min.js", "js/vendor/bootstrap-datepicker.zh-CN.min.js",
                                          "operator/userValidate.js","operator/userList.js", "common/pagination.js"]
                                 cssFiles=["css/bootstrap-datepicker.min.css","css/maintain/authority.css"] curModule="" title="真旅行组织构架">
    <@main_header.header currentMenu="权限管理" currentSubMenu="组织架构">
        <div class="operationDiv"><i class="plusIcon"></i>添加员工</div>
	</@main_header.header>

<div class="main-container">
        <section class="whiteBg">
            <ul class="searchList clearfix">
                <li>
                    <table class="searchTab">
                        <colgroup>
                            <col width="70">
                            <col width="230">
                        </colgroup>
                        <tbody>
                        <tr>
                            <th>员工姓名</th>
                            <td>
                                <input id="realName" type="text" placeholder=""/>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </li>
                <li>
                    <table class="searchTab">
                        <colgroup>
                            <col width="50">
                            <col width="230">
                        </colgroup>
                        <tbody>
                        <tr>
                            <th>工&nbsp;&nbsp;号</th>
                            <td>
                                <input id="employeeCode" type="text" placeholder=""/>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </li>
                <li>
                    <table class="searchTab">
                        <colgroup>
                            <col width="50">
                            <col width="250">
                        </colgroup>
                        <tbody>
                        <tr>
                            <th>角&nbsp;&nbsp;色</th>
                            <td>
                                <div class="dropdown" style="width: 200px;" id="search_role">
                                    <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                        <span class="activeFonts role" roleId="all">全部</span>
                                        <span class="caret"></span>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li class="active"  roleId="all"><a href="javascript:void(0);">全部</a></li>
                                        <#if roles??>
                                        <#list roles as role>
                                            <li  roleId="${role.roleId}"><a href="javascript:void(0);">${role.roleName}</a></li>
                                        </#list>
                                        </#if>
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
                            <col width="103">
                        </colgroup>
                        <tbody>
                        <tr>
                            <td style="height:46px;">
                                <a href="javascript:void(0);" class="lightBlueBtn"><i class="searchIcon"></i>查 询</a>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </li>
            </ul>
            <section class="clearfix" id="searchField">
                <table class="commonTab productTab">
                    <colgroup>
                        <col width="130">
                        <col width="124">
                        <col width="140">
                        <col width="174">
                        <col width="263">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>员工姓名</th>
                        <th>工号</th>
                        <th>用户名</th>
                        <th>角色</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
                <input type="hidden" name="pageNo" value="1" />
			    <input type="hidden" name="pageSize" value="10" />
                <div id="pagination"></div>
            </section>
        </section>
    </div>

<!--删除操作-->
<div class="modal fade" id="deleModal">
    <div class="modal-dialog" style="width: 450px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">操作提示</h4>
            </div>
            <div class="modal-body">
                <p style="text-align: center"><span class="judge-icon"></span>删除所选的员工？</p>
            </div>
            <div class="modal-footer">
                <a href="javascript:void(0);" class="commonJudgeBtn confirmBtn" onclick="deleteUser();">确认</a>
                <a href="javascript:void(0);" class="commonJudgeBtn cancelBtn" data-dismiss="modal">取消</a>
            </div>
            <input type="hidden" id="delUserId" value="">
        </div>
    </div>
</div>

<!--添加员工 弹窗-->
<div class="modal fade" data-backdrop="static" data-keyboard="false" id="addUser">
    <div class="modal-dialog" style="width:812px;height:480px;">
        <div class="modal-content">
                <form id="fmAddUser">
                <@userDetails/>
                </form>
        </div>
    </div>
</div>

<!--编辑员工信息-->
<div class="modal fade" data-backdrop="static" data-keyboard="false" id="edtUser">
    <div class="modal-dialog" style="width:812px;height:480px;">
        <div class="modal-content">
            <form id="fmEditUser">
            <@userDetails isEditor=true/>
            </form>
        </div>
    </div>
</div>


	</@html.htmlIndex>

<#macro userDetails isEditor = false>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">&times;</span>
    </button>
    <h4 class="modal-title"><#if isEditor>编辑员工信息<#else>添加员工</#if></h4>
</div>
<div style="left:255px;width: 300px;display:none;" class="verifyStyle">
     <i class="verifyIcon"></i>
    <span class="verifyFonts"></span>
</div>
<div class="modal-body">
    <div class="popupContainer">
    <div class="roleInfo">
	    <table class="newVoucherTable">
	        <colgroup>
	            <col width="90">
	            <col width="150">
	            <col width="90">
	            <col width="220">
	            <col width="70">
	            <col width="150">
	        </colgroup>
	        <tbody>
	        <tr>
	            <th><em class="redStar">*</em>员工姓名</th>
	            <td><input name="realName" type="text" style="width: 130px;" maxlength="10"></td>
	            <th>手机号码</th>
	            <td><input name="mobile" type="text" style="width: 200px;" maxlength="11"></td>
	            <th>工&nbsp;&nbsp;号</th>
	            <td><input name="employeeCode" type="text" style="width: 130px;" maxlength="10"></td>
	        </tr>
	        </tbody>
	    </table>
	</div>
	<table class="newVoucherTable">
	    <colgroup>
	        <col width="90">
	        <col width="">
	    </colgroup>
	    <tbody id="edtVoucherTable">
	        <tr>
	            <th><em class="redStar">*</em>用&nbsp;&nbsp;户&nbsp;&nbsp;名</th>
	            <td><input name="userName" type="text" style="width: 285px;" <#if isEditor>readonly<#else>maxlength="20"</#if>></td>
	        </tr>
	        <tr>
	            <th><em class="redStar">*</em>登录密码</th>
	            <td><input name="password" type="password" style="width: 285px;"></td>
	        </tr>
	        <tr>
	            <th><em class="redStar">*</em>确认密码</th>
	            <td><input name="confirmPassword" type="password" style="width: 285px;"></td>
	        </tr>
	        <tr>
	            <th>邮件</th>
	            <td><input name="email" type="text" maxlength="50" style="width: 285px;"></td>
	        </tr>
	        <tr>
	            <th class="verticalAlign"><em class="redStar">*</em>角色选择</th>
	            <td>
	                <span class="dropdown" style="width: 285px;">
	                </span>
	            </td>
	        </tr>
	    </tbody>
	</table>
    </div>
</div>
<div class="modal-footer">
    <button type="button" class="commonButton red-fe6869Button save" style="margin-right:10px;">确认</button>
    <button type="button" class="commonButton blue-45c8dcButton" data-dismiss="modal">取消</button>
    <input type="hidden" name="userId" value="" />
</div>

</#macro>