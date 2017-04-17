<#import "/common/opera/main_header.ftl" as main_header/>
<#import "/common/opera/htmlIndex.ftl" as html/>

<@html.htmlIndex jsFiles=["js/vendor/bootstrap-datepicker.min.js", "js/vendor/bootstrap-datepicker.zh-CN.min.js", "operator/roleList.js", "common/pagination.js"]
                                 cssFiles=["css/bootstrap-datepicker.min.css","css/maintain/authority.css"] curModule="" title="真旅行角色维护">
    <@main_header.header currentMenu="权限管理" currentSubMenu="角色维护">
        <div class="operationDiv"><a href="${basepath}/rbac/role/add"><i class="plusIcon"></i>添加角色</a></div>
	</@main_header.header>

    <div class="main-container">
        <section class="whiteBg clearfix" id="searchField">
            <table class="commonTab productTab" style="margin-top:10px;">
                <colgroup>
                    <col width="393">
                    <col width="307">
                    <col width="235">
                </colgroup>
                <thead>
                <tr>
                    <th>角色名称</th>
                    <th>使用用户数</th>
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
    </div>

<!--删除-->
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
                <p style="text-align: center"><span class="judge-icon"></span>删除所选的角色？</p>
            </div>
            <div class="modal-footer">
                <a href="javascript:void(0);" class="commonJudgeBtn confirmBtn" onclick="deleteRole();">确认</a>
                <a href="javascript:void(0);" class="commonJudgeBtn cancelBtn" data-dismiss="modal">取消</a>
            </div>
            <input type="hidden" id="delRoleId" value="">
        </div>
    </div>
</div>


</@html.htmlIndex>