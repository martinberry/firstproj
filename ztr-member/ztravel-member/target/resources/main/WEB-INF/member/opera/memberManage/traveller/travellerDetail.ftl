
              <section class="rightModelContent padding20">
                <div class="titleContent clearfix">
                    <h3 class="titleFonts18">常用旅客详情</h3>
                    <a href="" class="returnTravelerList">< 返回常旅客列表</a>
                </div>
                <table class="detailsTab noMargin">
                    <colgroup>
                        <col width="10%">
                        <col width="90%">
                    </colgroup>
                    <tbody>
                    <tr>
                        <td>姓名：</td>
                        <td>${(detail.travelerNameCn)!''}</td>
                    </tr>
                    <tr>
                        <td>拼音名：</td>
                        <td>${(detail.travelerNameEn)!''}</td>
                    </tr>
                    <tr>
                        <td>手 机 号：</td>
                        <td>${(detail.phoneNum)!''}</td>
                    </tr>
                    <tr>
                        <td><span class="adjaceney">国籍</span>：</td>
                        <td>${(detail.nationality)!''}</td>
                    </tr>
                    <tr>
                        <td><span class="adjaceney">性别</span>：</td>
                        <td><#if (detail.gender)??><#if detail.gender=='MALE' || detail.gender=='male'>男 <#elseif detail.gender == 'FEMALE' || detail.gender=='female'>女</#if></#if></td>
                    </tr>
                    <tr>
                        <td>乘客类型：</td>
                        <td><#if (detail.travelType)??><#if detail.travelType == 'ADULT'> 成人<#elseif detail.travelType == 'CHILD'>儿童 </#if></#if></td>
                    </tr>
                    <tr>
                        <td>出生日期：</td>
                        <td>${(detail.birthday)!''}</td>
                    </tr>
                    <tr>
                        <td><span class="adjaceney">邮箱</span>：</td>
                        <td>${(detail.email)!''}</td>
                    </tr>
                    </tbody>
                </table>
                <div class="lightGrayLine"></div>
                <div class="titleContent clearfix">
                    <h3 class="titleFonts18">证件信息</h3>
                </div>
                <table class="grayTheadTab">
                    <colgroup>
                        <col width="180">
                        <col width="240">
                        <col width="170">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>证件类型</th>
                        <th>证件号</th>
                        <th>有效期</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#if (detail.credentials)??>
                    <#list detail.credentials as credential>
                    <tr>
                        <td><#if (credential.type)??><#if credential.type == 'IDCARD'>身份证<#elseif credential.type == 'PASSPORT'>护照<#elseif credential.type == 'GANGAOPASS'>港澳通行证</#if></#if></td>
                        <td>${(credential.number)!''}</td>
                        <td>${(credential.deadLineDay)!''}</td>
                    </tr>
                    </#list>
                </#if>
                    </tbody>
                </table>
                <div class="lightGrayLine"></div>
                <div class="titleContent clearfix">
                    <h3 class="titleFonts18">邮寄地址</h3>
                </div>
                <div class="addressFonts">${(detail.address)!''}</div>
                </section>
