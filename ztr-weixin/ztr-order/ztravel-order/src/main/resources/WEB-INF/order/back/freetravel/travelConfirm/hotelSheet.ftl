<div class="commonOPOrderModel">
    <div class="commonSmallTitleModel blueFonts">
        <span class="leftTitleFonts">酒店信息</span>
        <label class="commonEditBtn hotelEditBtn" <#if (readOnly)!false > style="display: none;" </#if>>
            <span class="commonIcon editIcon"></span>
        </label>
    </div>
    <div class="commonStyle changeCommonStyle">
        <#list (orderDetail.product.hotelList)! as hotel>
        <table class="hotelInfoTab">
            <colgroup>
                <col width="180">
                <col width="180">
                <col width="350">
                <col width="200">
                <col width="">
            </colgroup>
            <tbody>
            <tr>
                <td>
                    <em class="solidLine hotelFirstLine"></em>
                    <em class="solidLine hotelSecondLine"></em>
                    <em class="solidLine hotelThirdLine"></em>

                    <span class="blueBgFonts">入住</span>
                    <div class="blueFontsWeight">${(hotel.checkInDate)!}</div>
                </td>
                <td>
                    <span class="orangeBgFonts">离店</span>
                    <div class="blueFontsWeight">${(hotel.checkOutDate)!}</div>
                </td>
                <td class="textAlignLeft">${(hotel.hotelName)!}</td>
                <td>
                    <div>${(hotel.roomType)!}</div>
                </td>
                <td>
                    <div>${(hotel.hotelType)!}</div>
                </td>
            </tr>
            </tbody>
        </table>
        </#list>
    </div>
</div>

<!--  添加酒店弹窗   酒店列表  -->
<div class="modal hasHeaderBtn" id="hotel-list-popup" style="position: absolute;">
    <div class="modal-dialog" style="width: 1040px;height:687px;">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-header-oper">
                    <button class="commonButton gray-bbbButton hotellist cancel">取消</button>
                </div>
                <h4 class="modal-title">选择酒店</h4>
            </div>
            <div class="modal-body" style="height:635px;overflow-y: auto;">
                <div class="section-cont-wrapper">
                    <section class="clearfix" id="searchField">
                        <div class="search-block">
                            <span>酒店名称</span>
                            <input id="hotelNameInputer" type="text" style="width:395px;" />
                            <button class="commonButton blue-45c8dcButton haveIconButton"><i class="searchIcon"></i>搜索</button>
                            <input type="hidden" name="pageNo" value="1" />
                            <input type="hidden" name="pageSize" value="10" />
                        </div>
                        <table class="commonTab hotel-list-table">
                            <colgroup>
                                <col width="227">
                                <col width="112">
                                <col width="116">
                                <col width="108">
                                <col width="95">
                                <col width="216">
                                <col width="">
                            </colgroup>
                            <thead>
                            <tr>
                                <th>酒店名称</th>
                                <th>国家</th>
                                <th>城市/景点</th>
                                <th>酒店类型</th>
                                <th>星级</th>
                                <th>特色</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                        <div id="pagination">
                        </div>
                    </section>
                </div>
            </div>
        </div>
    </div>
</div>

<!--  添加酒店弹窗   酒店表单  -->
<div class="modal hasHeaderBtn" id="add-hotel-popup">
    <div class="modal-dialog" style="width: 1040px;height:687px;">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-header-oper">
                    <button class="commonButton red-fe6869Button hotel save">保存</button>
                    <button class="commonButton gray-bbbButton hotel cancel">取消</button>
                </div>
                <h4 class="modal-title">添加酒店</h4>
            </div>
            <div class="modal-body">
                <div class="section-cont-wrapper">
                    <section class="hotel-form-module">
                        <div class="table-top-block clearfix">
                            <ul class="table-tab-switch hotel edit">
                                <li onclick='hotelSearchListPage();'><span>+ 添加酒店</span></li>
                            </ul>
                        </div>
                    </section>
                </div>
            </div>
        </div>
    </div>
</div>