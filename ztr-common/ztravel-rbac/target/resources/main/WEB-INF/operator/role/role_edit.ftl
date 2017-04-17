<#import "/common/opera/htmlIndex.ftl" as html/>

<@html.htmlIndex jsFiles=["js/vendor/bootstrap-datepicker.min.js", "js/vendor/bootstrap-datepicker.zh-CN.min.js", "operator/roleEdit.js"]
                                 cssFiles=["css/bootstrap-datepicker.min.css","css/maintain/authority.css"] curModule="" title="真旅行后台-权限管理-角色列表">


<header class="main-header">
        <div class="stairMenuContent">
            <div class="wrap clearfix">
                <div class="navLeft">
                    <a class="logo" href="/"></a>
                    <ul class="stairMenu">
                        <li class="first-level-nav">
                            <a href="javascript:void(0);">添加角色/角色维护</a>
                        </li>
                    </ul>
                </div>
                <#include "/common/opera/header_right.ftl" />
            </div>
        </div>
        <div class="headSecondBlank clearfix">
            <span>
                <a class="backBtn" href="${basepath}/rbac/role/list">&lt;&nbsp;返回</a>
            </span>
            <span class="head-btn">
                <a href="javascript:void(0);" class="commonJudgeBtn redBtn" id="redBtn">保存</a>
                <a href="${basepath}/rbac/role/list" class="commonJudgeBtn greyBtn">取消</a>
            </span>
        </div>
    </header>
    <div class="judge-container">
        <section class="contentBg">
            <input type="hidden" id="roleId" value="${(role.roleId)!}" />
            <div class="roleMaintain">
                <span class="roleName"><em class="redStar">*</em>角色名称</span>
                <input type="text" id="roleName" placeholder="" value="${(role.roleName)!}" style="width: 300px;" maxlength="10">
                <div style="left:124px;width: 300px;display: none;" class="verifyStyle">
                    <i class="verifyIcon"></i>
                    <span class="verifyFonts"></span>
                </div>
            </div>
            <div class="roleOptions">
                <h2>选择权限</h2>
                <table class="addSpecilTable table-bordered">
                    <colgroup>
                        <col width="157">
                        <col width="180">
                        <col width="436">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>权限模块</th>
                        <th colspan="2">细化权限</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#if allPerms??>
                    <#list allPerms as allPerm>
                    <#if (allPerm.permissions)??>
                    <#list allPerm.permissions as perm>
                    <tr>
                        <#if perm_index==0><th rowspan="${(allPerm.permissions)?size}">${(allPerm.permissionName)!}</th></#if>
                        <input type="hidden" id="permissionId" value="${(perm.permissionId)!}" />
                        <td>
                            <div class="checkboxContent">
                                <label class="checkboxInfo <#if permissions?? && permissions?seq_contains(perm.permissionId)>active</#if>">
                                    <span class="checkboxIcon"></span>${(perm.permissionName)!}
                                </label>
                            </div>
                        </td>
                        <td></td>
                    </tr>
                    </#list>
                    </#if>
                    </#list>
                    </#if>
                    </tbody>
                </table>
            </div>

        </section>
    </div>
<!--保存选择权限模态框-->
<div class="modal fade" id="conserveMaintain">
    <div class="modal-dialog" style="width: 450px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">操作提示</h4>
            </div>
            <div class="modal-body">
                <p style="text-align: center"><span class="conserve-icon"></span>保存成功！</p>
            </div>
            <div class="modal-footer">
                <a href="javascript:void(0);" class="commonJudgeBtn confirmBtn" id="confirmBtn" data-dismiss="modal">确认</a>
            </div>
        </div>
    </div>
</div>

</@html.htmlIndex>