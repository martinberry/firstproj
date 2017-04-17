<#import "/common/opera/main_header.ftl" as main_header/>
<#import "/member/opera/memberManage/memberMaintain/leftMenu.ftl" as left/>

<#import "/common/opera/htmlIndex.ftl" as html/>
<@html.htmlIndex jsFiles=["js/vendor/bootstrap-datepicker.min.js","js/vendor/bootstrap-datepicker.zh-CN.min.js","common/pagination.js","operator/financeMantain/accountSummary.js"] cssFiles=["css/maintain/finance.css","css/bootstrap-datepicker.min.css"]
curModule="财务&供应商系统" title="票券账户明细">
<@main_header.header currentMenu="财务&供应商系统" currentSubMenu="票券账户明细">
	</@main_header.header>
    <div class="main-container">
        <section class="whiteBg">
            <ul class="searchList clearfix">
                <li>
                     <form id="search-form" data-role="validator" novalidate="novalidate">
                    		 <input type="hidden" name="pageNo" value="1" />
								<input type="hidden" name="pageSize" value="10" />
                    <table class="searchTab">
                        <colgroup>
                            <col width="80">
                            <col width="220">
                        </colgroup>
                        <tbody>
                        <tr>
                            <th>会员ID</th>
                            <td>
                                <input type="text" id = "mid" name ="mid"/>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </li>
                <li>
                    <table class="searchTab">
                        <colgroup>
                            <col width="80">
                            <col width="220">
                        </colgroup>
                        <tbody>
                        <tr>
                            <th>账户类型</th>
                            <td>
                                <div class="dropdown" style="width: 200px;">
                               		 <input type = "hidden" name= "accountType" class="searchFormField">
                                    <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                        <span class="activeFonts" id= "accountType">全部</span>
                                        <span class="caret"></span>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li class="active"><a href="javascript:void(0);">全部</a></li>
                                        <li><a href="javascript:void(0);">积分</a></li>
                                        <li><a href="javascript:void(0);">代金券</a></li>
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
                            <col width="220">
                        </colgroup>
                        <tbody>
                        <tr>
                            <th>冻结状态</th>
                            <td>
                                <div class="dropdown" style="width: 200px;">
                              		<input type = "hidden" name= "accountStatus" class="searchFormField">
                                    <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                        <span class="activeFonts" id ="accountStatus">全部</span>
                                        <span class="caret"></span>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li class="active"><a href="javascript:void(0);">全部</a></li>
                                        <li><a href="javascript:void(0);">已冻结</a></li>
                                        <li><a href="javascript:void(0);">未冻结</a></li>
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
                            <col width="225">
                        </colgroup>
                        <tbody>
                        <tr>
                            <th>截止时间</th>
                            <td>
                                <input type="text" style="width: 200px;" class="datepicker default hasIcon" readonly="readonly" name="updated" id="updated">
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
                        <tr style="height:46px;">
                            <td>
                                <a href="javascript:void(0);" class="lightBlueBtn"><i class="searchIcon"></i>查 询</a>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                       </form>
                </li>
            </ul>
            <div class="outTab"><a href="javascript:void(0);" id="exportExcel">导出Excel</a></div>
            <section class="clearfix">
                <table class="commonTab productTab">
                    <colgroup>
                        <col width="8%">
                        <col width="7%">
                        <col width="12%">
                        <col width="8%">
                        <col width="8%">
                        <col width="6%">
                        <col width="6%">
                        <col width="9%">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>昵称</th>
                         <th>会员ID</th>
                        <th>账户类型</th>
                        <th>账户余额</th>
                        <th>可用余额</th>
                        <th>冻结余额</th>
                        <th>账户冻结</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                     </tbody>
                    </table>
                          <div id="searchField">
                   </div>

            </section>
        </section>
    </div>
</div>

  <div class="modal fade" id="ac-frozen">
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
                        <span class="popupContainer-fonts">确认将冻结账户吗？</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button" onClick="frozenAccount();">确定</button>
                    <button type="button" class="commonButton blue-45c8dcButton" >取消</button>
                </div>
                <input type="hidden" id="frozenMemberId">
                <input type ="hidden" id="frozenAccountType">
            </div>
        </div>
    </div>
<div class="modal fade" id="ac-unfrozen">
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
                        <span class="popupContainer-fonts">确认解冻账户吗？</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button" onClick="unFrozenAccount();">确定</button>
                    <button type="button" class="commonButton blue-45c8dcButton" >取消</button>
                </div>
                <input type="hidden" id="unFrozenMemberId">
                <input type ="hidden" id="unFrozenAccountType">
            </div>
        </div>
    </div>

<script>
    $(function(){
        $(".radioList .radio").click(function(){
            $(this).addClass("active");
            $(this).siblings().removeClass("active");
        });

        $(".allCheckbox").click(function(){
            $(this).toggleClass("active");
            if ($(this).hasClass("active")) {
                $(this).parents("table").find(".checkbox").addClass("active");
            } else {
                $(this).parents("table").find(".checkbox").removeClass("active");
            }
        });

        $(".ac-online").click(function(){
            $("#ac-online").modal();
        });

        $(".ac-issue").click(function(){
            $("#ac-issue").modal();
        });

    });
</script>

</@html.htmlIndex>