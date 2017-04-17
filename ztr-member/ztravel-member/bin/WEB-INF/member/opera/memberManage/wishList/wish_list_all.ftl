<#import "/common/opera/main_header.ftl" as main_header/>

<#import "/common/opera/htmlIndex.ftl" as html/>
<@html.htmlIndex jsFiles=["common/pagination.js","member/opera/wishList.js"] cssFiles=["css/maintain/favourList.css"]
curModule="会员心愿清单" title="真旅行心愿清单列表">

	<@main_header.header  currentMenu="会员管理" currentSubMenu="心愿清单">
	</@main_header.header>

	    <div class="main-container clearfix">
        <aside class="leftMenu">  <!--leftMenu 开始-->
            <ul class="menuChange">
                <li class="current">
                    <a href="${basepath}/member/opera/wish/list/all">旅行产品</a>
                    <i class="rightArrow"></i>
                </li>
            </ul>
        </aside> <!--leftMenu 结束-->
        <section class="rightModelContent padding20">
            <ul class="searchList clearfix">
                <li>
                    <table class="searchTab">
                        <colgroup>
                            <col width="70">
                            <col width="320">
                        </colgroup>
                        <tbody>
                        <tr>
                            <th>产品标题</th>
                            <td>
                                <input type="text" id="pName" maxlength="30">
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </li>
                <li>
                    <table class="searchTab">
                        <colgroup>
                            <col width="110">
                        </colgroup>
                        <tbody>
                        <tr>
                            <td>
                                <a href="javascript:void(0);" class="lightBlueBtn"><i class="searchIcon"></i>搜 索</a>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </li>
            </ul>
            <div class="lightGrayLine"></div>
            <section class="clearfix" id="searchField">
                <table class="commonTab fixedTab">
                    <colgroup>
                        <col width="15%">
                        <col width="12%">
                        <col width="10%">
                        <col width="15%">
                        <col width="18%">
                        <col width="14%">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>产品名称</th>
                        <th>产品ID</th>
                        <th>目的地区域</th>
                        <th>产品状态</th>
                        <th>最近收藏时间</th>
                        <th>收藏数量(点击查看)</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
                <input type="hidden" name="pageNo" value="1" />
				<input type="hidden" name="pageSize" value="10" />
                <div id="pagination">
                </div>
            </section>

        </section>
    </div>


	</@html.htmlIndex>