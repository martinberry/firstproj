<#import "/common/opera/htmlIndex.ftl" as html />
<#import "/common/opera/main_header.ftl" as main_header />

<@html.htmlIndex jsFiles=["activity/newYear/gameRecord.js", "common/jquery-form.js","common/pagination.js"]
cssFiles=["css/maintain/operatorManagement.css"] curModule="" title="查看游戏数据">
    <@main_header.header currentMenu="运营管理" currentSubMenu="查看游戏数据">
    </@main_header.header>
        <div class="main-container clearfix">
            <section class="whiteBg">
                <form id="searchConditionForm">
                    <ul class="searchList clearfix">
                        <li>
                            <table class="searchTab">
                               		 <input type="hidden" name="pageNo" value="1" />
										<input type="hidden" name="pageSize" value="10" />
                                <colgroup>
                                    <col width="80">
                                    <col width="">
                                </colgroup>
                                <tbody>
                                <tr>
                                    <th>活动名称</th>
                                    <td>
                                        <input type="text" id="activityName">
                                        <a href="javascript:void(0);" class="lightBlueBtn"><i class="searchIcon"></i>搜 索</a>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </li>
                    </ul>
                </form>
                <section class="clearfix" style="width:1000px;">
                    <table class="commonTab">
                        <colgroup>
                            <col width="250px">
                            <col width="120px">
                            <col width="450px">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>活动名称</th>
                            <th>用户昵称</th>
                            <th>用户填写信息</th>
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
</@html.htmlIndex>