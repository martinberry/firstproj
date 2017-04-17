<#import "/common/opera/main_header.ftl" as main_header/>
<#import "/member/opera/memberManage/memberMaintain/leftMenu.ftl" as left/>

<#import "/common/opera/htmlIndex.ftl" as html/>
<@html.htmlIndex jsFiles=["member/opera/traveller.js"] cssFiles=["css/maintain/memberManagement.css"]
curModule="会员心愿清单" title="真旅行心愿清单列表">

	<@main_header.header  currentMenu="会员管理" currentSubMenu="会员维护">
	</@main_header.header>

	<@left.leftMenu curLeftModule="wishList" memberId="${memberId!}">

    <section class="rightModelContent padding20">
                <div class="titleContent clearfix">
                    <h3 class="titleFonts18">心愿清单</h3>
                </div>
                <table style="width:100%;">
                    <tbody>
                        <tr>
                            <td style="width:8%; padding-left:10px; vertical-align: text-top;">收藏产品</td>
                            <td style="width:92%;">
                            <textarea name="" class="form-control" placeholder="北京-上海飞机6天" cols="20" rows="10" readonly><#list wishs as wish>${(wish.pName)!}
                            </#list>
                            </textarea></td>
                        </tr>
                    </tbody>
                </table>
            </section>

    </@left.leftMenu>

</@html.htmlIndex>