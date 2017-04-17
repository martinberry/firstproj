<#import "/common/opera/main_header.ftl" as main_header/>

<#import "/common/opera/htmlIndex.ftl" as html/>
<@html.htmlIndex jsFiles=["common/pagination.js","member/opera/wishDetail.js"] cssFiles=["css/maintain/favourList.css"]
curModule="会员心愿清单" title="真旅行心愿清单列表">

	<@main_header.header  currentMenu="会员管理" currentSubMenu="心愿清单">
	</@main_header.header>

	    <div class="main-container clearfix">
        <aside class="leftMenu">  <!--leftMenu 开始-->
            <a href="${basepath}/member/opera/wish/list/all" class="returnFonts">
                <i class="returnIcon"></i>清单列表
            </a>
        </aside> <!--leftMenu 结束-->
        <section class="rightModelContent padding20">
        <input type="hidden" id="productId" value="${(product.productId)!}" />
            <div class="titleContent clearfix">
                <h3 class="titleFonts18">${(product.pName)!}</h3>
            </div>
            <section class="clearfix" id="searchField">
                <table class="commonTab">
                    <colgroup>
                        <col width="15%">
                        <col width="12%">
                        <col width="10%">
                        <col width="15%">
                        <col width="12%">
                        <col width="18%">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>会员ID</th>
                        <th>昵称</th>
                        <th>真实姓名</th>
                        <th>手机号</th>
                        <th>地区</th>
                        <th>收藏时间</th>
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