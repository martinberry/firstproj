<#import "/common/opera/htmlIndex.ftl" as html />
<#import "/common/opera/main_header.ftl" as main_header />
<#import "/operator/basicData/leftMenu.ftl" as left />

<@html.htmlIndex jsFiles=["activity/newYear/activityAwardSettings.js", "common/jquery-form.js"] cssFiles=["css/maintain/operatorManagement.css"] localCssFiles=["member/front/round_image.css"] curModule="" title="真旅行基础数据">
    <@main_header.header currentMenu="运营管理" currentSubMenu="基础数据">
    </@main_header.header>
    <div class="main-container clearfix">
            <@left.leftMenu curLeftModule="GAME_SETTINGS">
            </@left.leftMenu>
            <section class="rightModelContent">
                <div class="titleContent clearfix">
                    <h3 class="titleFonts18">2016元旦游戏参数设置</h3>
                </div>
                <div class="selectClassify clearfix">
                    <div class="leftClassifyBtn">
                        <div class="modifiedDeparture" style="display: block;">
                            <button class="commonButton blue-45c8dcButton">修改参数</button>
                        </div>
                        <div class="saveCancelBtn" style="display: none;">
                            <button class="commonButton orange-f79767Btn save">确 认</button>
                            <button class="commonButton gray-bbbButton cancel">取 消</button>
                        </div>
                    </div>
                    <div class="rightClassifyInfo">
                        <table class="commonTab setGameParams">
                            <input type="hidden" id="oldCoupon1Weight" value="${(records.COUPON1.weight)!'0'}">
                            <input type="hidden" id="oldCoupon2Weight" value="${(records.COUPON2.weight)!'0'}">
                            <input type="hidden" id="oldCoupon3Weight" value="${(records.COUPON3.weight)!'0'}">
                            <input type="hidden" id="oldBagWeight" value="${(records.BAG.weight)!'0'}">
                            <input type="hidden" id="oldCalendaryWeight" value="${(records.CALENDARY.weight)!'0'}">
                            <colgroup>
                                <col width="100">
                                <col width="100">
                                <col width="100">
                                <col width="100">
                                <col width="100">
                                <col width="100">
                            </colgroup>
                            <thead>
                                <tr>
                                    <th>奖品类别</th>
                                    <th>50元代金券</th>
                                    <th>100元代金券</th>
                                    <th>800元代金券</th>
                                    <th>包包</th>
                                    <th>台历</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>奖品总数量</td>
                                    <td class="noEdit">${(records.COUPON1.totalNum)!'0'}</td>
                                    <td class="noEdit">${(records.COUPON2.totalNum)!'0'}</td>
                                    <td class="noEdit">${(records.COUPON3.totalNum)!'0'}</td>
                                    <td class="noEdit">${(records.BAG.totalNum)!'0'}</td>
                                    <td class="noEdit">${(records.CALENDARY.totalNum)!'0'}</td>
                                </tr>
                                <tr>
                                    <td>剩余数量</td>
                                    <td class="noEdit">${(records.COUPON1.leftNum)!'0'}</td>
                                    <td class="noEdit">${(records.COUPON2.leftNum)!'0'}</td>
                                    <td class="noEdit">${(records.COUPON3.leftNum)!'0'}</td>
                                    <td class="noEdit" id="bagLeftNum">${(records.BAG.leftNum)!'0'}</td>
                                    <td class="noEdit" id="calendaryLeftNum">${(records.CALENDARY.leftNum)!'0'}</td>
                                </tr>
                                <tr>
                                    <td>余量增减</td>
                                    <td class="noEdit">-</td>
                                    <td class="noEdit">-</td>
                                    <td class="noEdit">-</td>
                                    <td><input type="text" name="bagChangeNum" id="bagChangeNum" data-cv="required,positiveNum" value="" readonly="readonly" style="width:50px;"></td>
                                    <td><input type="text" name="calendaryChangeNum" id="calendaryChangeNum" value="" readonly="readonly" style="width:50px;"></td>
                                </tr>
                                <tr>
                                    <td>出现权重</td>
                                    <td><input type="text" name="coupon1weight" id="coupon1Weight" value="${(records.COUPON1.weight)!'0'}" readonly="readonly" style="width:50px;"></td>
                                    <td><input type="text" name="coupon2Weight" id="coupon2Weight" value="${(records.COUPON2.weight)!'0'}" readonly="readonly" style="width:50px;"></td>
                                    <td><input type="text" name="coupon3Weight" id="coupon3Weight" value="${(records.COUPON3.weight)!'0'}" readonly="readonly" style="width:50px;"></td>
                                    <td><input type="text" name="bagWeight" id="bagWeight" value="${(records.BAG.weight)!'0'}" readonly="readonly" style="width:50px;"></td>
                                    <td><input type="text" name="calendaryWeight" id="calendaryWeight" value="${(records.CALENDARY.weight)!'0'}" readonly="readonly" style="width:50px;"></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </section>
    </div>
    <div class="modal fade commonInitialize" id="messageDlg">
        <div class="modal-dialog" style="width: 350px;height:185px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">操作提示</h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <i></i>
                        <span class="popupContainer-fonts"></span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button" data-dismiss="modal">确 认</button>
                </div>
            </div>
        </div>
    </div>
</@html.htmlIndex>