<#import "/common/opera/htmlIndex.ftl" as html/>
<#import "piece_menu.ftl" as pieceMenu/>
<@html.htmlIndex jsFiles=["js/vendor/bootstrap-datepicker.min.js",
    "js/vendor/bootstrap-datepicker.zh-CN.min.js","js/vendor/underscore.js","js/vendor/jquery.tagsinput.js","common/ZtrDropDown.js",
    "product/back/pieces/piece_base.js","product/back/common_utils.js"]
    cssFiles=["css/bootstrap-datepicker.min.css","css/jquery.tagsinput.css","css/maintain/productManagement.css"]
    curModule="产品管理"
    title="基本信息">

    <@pieceMenu.pieceMenu curPieceModule="basicInfo">
    <script type="text/javascript">
        var ADDRESS = eval("(${(ADDRESS)!})");
    </script>
        <div class="main-container changeMainContent">
            <section class="whiteBg">
                <table class="noBorderCommonTab">
                    <colgroup>
                        <col width="110">
                        <col width="850">
                    </colgroup>
                    <tbody>
                        <input id='productId_hidden' type='hidden' value='${(basicInfo.id)!}'/>
                        <input id='productPid_hidden' type='hidden' value='${(basicInfo.pid)!}'/>
                        <input id='progress_hidden' type='hidden' value='${(basicInfo.progress)!}'/>
                        <input id='productNature_hidden' type='hidden' value='${(basicInfo.nature)!}'/>
                        <input id='productType_hidden' type='hidden' value='${(basicInfo.type)!}'/>
                    <tr>
                        <th>标 &nbsp; 题</th>
                        <td>
                            <input  id='pieceName_input' type="text" style="width: 700px;" value="${(basicInfo.pname)!}" maxlength=100>
                        </td>
                    </tr>
                    <tr>
                        <th class="verticalTop" style="padding-top: 15px;">目的地</th>
                        <td class="clearfix">
                            <div class="pull-left">
                                <div class="dropdown" style="width: 110px;" id='address_continent'>
                                    <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false" title='全部'>
                                        <span class="activeFonts">全部</span>
                                        <span class="caret"></span>
                                    </a>
                                    <ul class="dropdown-menu">
                                    </ul>
                                </div>
                                <div class="dropdown" style="width: 110px;" id='address_country'>
                                    <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false" title='全部'>
                                        <span class="activeFonts">全部</span>
                                        <span class="caret"></span>
                                    </a>
                                    <ul class="dropdown-menu">
                                    </ul>
                                </div>
                                <div class="dropdown" style="width: 110px;" id='address_region'>
                                    <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn" aria-expanded="false" title='全部'>
                                        <span class="activeFonts">全部</span>
                                        <span class="caret"></span>
                                    </a>
                                    <ul class="dropdown-menu">
                                    </ul>
                                </div>
                                <button class="commonButton blue-45c8dcButton" id='addCity_button' style="height:36px;">添 加</button>
                            </div>
                            <div class="smallTagModel" id='to_labels'>
                            <#list (basicInfo.toCity)! as city>
                                <label class="smallTagStyle">
                                    <span class="closeFonts" continent="${(basicInfo.toContinent[city_index])!}" country="${(basicInfo.toCountry[city_index])!}">${(city)!}</span>
                                    <span class="closeIcon"  onclick="closeSelf(this);"></span>
                                </label>
                            </#list>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th>产品种类</th>
                        <td>
                            <div class="dropdown" style="width: 110px;">
                                <a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">
                                    <span class="activeFonts" id="productTypeName"></span>
                                    <span class="caret"></span>
                                </a>
                                <ul class="dropdown-menu" id='pieceType_dropdown'>
                                    <li value="TICKET"><a href="javascript:void(0);">门票</a></li>
                                    <li value="LOCAL"><a href="javascript:void(0);">当地酷玩</a></li>
                                    <li value="TRAFFIC"><a href="javascript:void(0);">交通接驳</a></li>
                                    <li value="WIFI"><a href="javascript:void(0);">wifi通讯</a></li>
                                    <li value="HOTELUP"><a href="javascript:void(0);">酒店升级</a></li>
                                    <li value="CHARTER"><a href="javascript:void(0);">包车服务</a></li>
                                    <li value="INTELTAXI"><a href="javascript:void(0);">国际租车</a></li>
                                    <li value="VISA"><a href="javascript:void(0);">旅游签证</a></li>
                                </ul>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </section>
        </div>
</@pieceMenu.pieceMenu>
</@html.htmlIndex>