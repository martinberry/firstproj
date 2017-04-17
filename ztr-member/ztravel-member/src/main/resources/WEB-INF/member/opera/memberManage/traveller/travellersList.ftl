<#import "/common/opera/main_header.ftl" as main_header/>
<#import "/member/opera/memberManage/memberMaintain/leftMenu.ftl" as left/>

<#import "/common/opera/htmlIndex.ftl" as html/>
<@html.htmlIndex jsFiles=["member/opera/traveller.js"] cssFiles=["css/maintain/memberManagement.css"]
curModule="常旅客" title="B2C 真旅行后台 常旅客信息">

	<@main_header.header  currentMenu="会员管理" currentSubMenu="会员维护">
	</@main_header.header>

	<@left.leftMenu curLeftModule="travellerInfo" memberId="${memberId!}">

         <div id="traveller">
            <section class="rightModelContent padding20">
                <div class="titleContent clearfix">
                    <h3 class="titleFonts18">常用旅客详情</h3>
                </div>
                <ul class="searchList clearfix">
                    <li>
                        <table class="searchTab">
                            <colgroup>
                                <col width="80">
                                <col width="230">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>姓名</th>
                                <td>
                                    <input id="travelerNameCn" type="text"　maxLenght='20'>
                                    <div style="width: 200px; display: none;" class="verifyStyle" id="error_name" >
					                    <i class="verifyIcon"></i>
					                    <span class="verifyFonts" id="verify_name"></span>
					                </div>
                                </td>
                                 <th>拼音名</th>
                                  <td>
                                    <input id="travelerNameEn" type="text" maxLenght='40'>
                                    <div style="width: 200px; display: none;" class="verifyStyle" id="error_nameEn" >
					                    <i class="verifyIcon"></i>
					                    <span class="verifyFonts" id="verify_nameEn"></span>
					                </div>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </li>
                    <li>
                        <table class="searchTab">
                            <colgroup>
                                <col width="70">
                                <col width="320">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th>手机号</th>
                                <td>
                                    <input id="phoneNum" type="text">
                                    <div style="width: 200px; display: none;" class="verifyStyle" id="error_phone">
                    <i class="verifyIcon"></i>
                    <span class="verifyFonts" id="verify_phone"></span>
                </div>
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
                                    <a href="javascript:;" class="lightBlueBtn"><i class="searchIcon"></i>搜 索</a>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </li>
                </ul>
                <div class="lightGrayLine"></div>
                <section class="clearfix">
                    <table class="commonTab" id="traveller_list">
                        <colgroup>
                            <col width="10%">
                            <col width="10%">
                            <col width="12%">
                            <col width="10%">
                            <col width="15%">
                            <col width="18%">
                            <col width="14%">
                            <col width="">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>姓名</th>
                            <th>拼音名</th>
                            <th>旅客类型</th>
                            <th>性别</th>
                            <th>证件类型</th>
                            <th>证件号码</th>
                            <th>手机号</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#if list??>
                        <#list list as item>
                        <tr>
                            <td>${(item.travelerNameCn)!}</td>
                            <td>${(item.travelerNameEn)!}</td>
                            <td><#if (item.travelType)??><#if item.travelType == 'ADULT'>成人<#elseif item.travelType == 'CHILD'>儿童</#if></#if></td>
                            <td><#if (item.gender)??><#if item.gender=='MALE' || item.gender=='male'>男<#elseif item.gender=='FEMALE' || item.gender=='female'>女</#if></#if></td>
                            <td><#if (item.credentials)??><#if item.credentials[0].type == 'IDCARD'>身份证</#if><#if item.credentials[0].type == 'PASSPORT'>护照</#if><#if item.credentials[0].type == 'GANGAOPASS'>港澳通行证</#if></#if></td>
                            <td><#if (item.credentials)??>${(item.credentials[0].number)!}</#if></td>
                            <td>${(item.phoneNum)!}</td>
                            <td>
                                <input type="hidden"  value="${(item.travelerId)!}">
                                <a href="javascript:;"  class="greenFontsLink" onclick="getDetail(this)">查看</a>
                            </td>
                        </tr>
                        </#list>
                        </#if>

                        </tbody>
                    </table>

                    <div class="pagination">
                    </div>
                </section>
            </section>

            </div>


	</@left.leftMenu>

</@html.htmlIndex>