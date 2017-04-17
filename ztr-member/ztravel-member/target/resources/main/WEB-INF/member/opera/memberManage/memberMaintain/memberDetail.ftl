<#import "/common/opera/main_header.ftl" as main_header/>
<#import "/common/opera/htmlIndex.ftl" as html/>
<#import "leftMenu.ftl" as left/>

<@html.htmlIndex jsFiles=[] localCssFiles=["member/opera/round_image.css"]
                                  cssFiles=["css/maintain/memberManagement.css"] curModule="会员管理" title="真旅行会员详情">
    <@main_header.header currentMenu="会员管理" currentSubMenu="会员维护">
	</@main_header.header>
	<@left.leftMenu curLeftModule="memberDetail" memberId="${member.id}">
            <section class="rightModelContent">
                <div class="detailsModel">
                    <h3 class="titleFonts">用户基本详情</h3>
                    <div class="headPortrait">
                        <#if member.headImgId??>
                        <img src="${mediaserver}imageservice?mediaImageId=${(member.headImgId)!}" class="headPortraitImg round_photo">
                        <div>头 像</div>
                        </#if>
                    </div>
                    <table class="detailsTab">
                        <colgroup>
                            <col width="12%">
                            <col width="88%">
                        </colgroup>
                        <tbody>
                        <tr>
                            <td>会员 ID：</td>
                            <td><#if member.memberId??>${member.memberId}</#if></td>
                        </tr>
                        <tr>
                            <td><span class="adjaceney">昵称</span>：</td>
                            <td><#if member.nickName??>${member.nickName}</#if></td>
                        </tr>
                        <tr>
                            <td><span class="adjaceney">性别</span>：</td>
                            <td><#if member.gender??><#if member.gender == 'M'>男<#elseif member.gender == 'F'>女</#if></#if></td>
                        </tr>
                        <tr>
                            <td>手机号：</td>
                            <td class="pinkFonts"><#if member.mobile??>${member.mobile}</#if></td>
                        </tr>
                        <tr>
                            <td><span class="adjaceney">邮箱</span>：</td>
                            <td class="pinkFonts"><#if member.email??>${member.email}</#if></td>
                        </tr>
                        <tr>
                            <td>真实姓名：</td>
                            <td><#if member.realName??>${member.realName}</#if></td>
                        </tr>
                        <tr>
                            <td>微信昵称：</td>
                            <td><#if wxNickName??>${wxNickName}</#if></td>
                        </tr>
                        <tr>
                            <td>通信地址：</td>
                            <td><#if member.province??>${member.province}</#if> <#if member.city??>${member.city}</#if> <#if member.area??>${member.area}</#if> <#if member.detailAddress??>${member.detailAddress}</#if></td>
                        </tr>
                        <tr>
                            <td>注册时间：</td>
                            <td><#if member.registerTime??>${member.registerTime}</#if></td>
                        </tr>
                        <tr>
                            <td>最近登录时间：</td>
                            <td><#if member.latestLoginDate??>${member.latestLoginDate}</#if> <#if member.latestLoginTime??>${member.latestLoginTime}</#if></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="lightGrayLine"></div>
                <div class="detailsModel">
                    <h3 class="titleFonts">其他</h3>
                    <table class="detailsTab">
                        <colgroup>
                            <col width="11%">
                            <col width="11%">
                            <col width="78%">
                        </colgroup>
                        <tbody>
                        <tr>
                            <td><span class="adjaceney">积 分</span>：</td>
                            <td>0</td>
                            <td>
                                <a href="javascript:void(0);" class="lookLink">查看”积分”使用日志</a>
                            </td>
                        </tr>
                        <tr>
                            <td>代金券总额：</td>
                            <td>0</td>
                            <td>
                                <a href="javascript:void(0);" class="lookLink">查看”代金券”使用日志</a>
                            </td>
                        </tr>
                        <tr>
                            <td>消费总额：</td>
                            <td>￥0.00</td>
                            <td>
                                <a href="javascript:void(0);" class="lookLink">查看”消费”使用日志</a>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </section>
    </@left.leftMenu>
</@html.htmlIndex>