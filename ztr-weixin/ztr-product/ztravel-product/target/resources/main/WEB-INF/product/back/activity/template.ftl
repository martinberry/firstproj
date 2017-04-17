 <!--   活动生效弹窗   -->
    <div class="modal fade" data-backdrop="static" data-keyboard="false" id="effectWindow">
        <div class="modal-dialog" style="width:500px;height:215px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">操作提示</h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <i class="warnIcon"></i>
                        <span class="popupContainer-fonts">活动生效后可激活代金券发放，已填写活动信息将不能更改，确定执行活动生效？</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button sureEffect">确认</button>
                    <button type="button" class="commonButton blue-45c8dcButton cancleEffect">取消</button>
                </div>
            </div>
        </div>
    </div>

    <!--   强制终止弹窗   -->
    <div class="modal fade" data-backdrop="static" data-keyboard="false" id="terminationWindow">
        <div class="modal-dialog" style="width:500px;height:215px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">操作提示</h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <i class="warnIcon"></i>
                        <span class="popupContainer-fonts">确认终止当前活动？</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton sureTerminate red-fe6869Button">确认</button>
                    <button type="button" class="commonButton blue-45c8dcButton cancleTerminate">取消</button>
                </div>
            </div>
        </div>
    </div>

    <!--   删除操作弹窗   -->
    <div class="modal fade" data-backdrop="static" data-keyboard="false" id="deleteWindow">
        <div class="modal-dialog" style="width:500px;height:215px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">操作提示</h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <i class="warnIcon"></i>
                        <span class="popupContainer-fonts">要删除选中的代金券？</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton sureDelete red-fe6869Button" data-val="">确认</button>
                    <button type="button" class="commonButton blue-45c8dcButton cancleDelete">取消</button>
                </div>
            </div>
        </div>
    </div>

    <!--   保存草稿弹窗   -->
    <div class="modal fade" data-backdrop="static" data-keyboard="false" id="draftWindow">
        <div class="modal-dialog" style="width:500px;height:215px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">操作提示</h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <i class="passIcon"></i>
                        <span class="popupContainer-fonts">保存草稿成功！</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton successSaveDraft red-fe6869Button">确认</button>
                </div>
            </div>
        </div>
    </div>

    <!--   送券操作弹窗   -->
    <div class="modal fade" data-backdrop="static" data-keyboard="false" id="deliverWindow">
        <div class="modal-dialog" style="width:500px;height:215px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">操作提示</h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <i class="passIcon"></i>
                        <span class="popupContainer-fonts">成功发放代金券！</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton successGrant red-fe6869Button">确认</button>
                </div>
            </div>
        </div>
    </div>

    <!--   新增代金券模板 弹窗   -->
    <div class="modal fade" data-backdrop="static" data-keyboard="false" id="voucherWindow">
        <div class="modal-dialog" style="width:680px;height:480px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">新增代金券</h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <table class="newVoucherTable">
                            <colgroup>
                                <col width="90">
                                <col width="">
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th>券名</th>
                                    <td><input type="text" id="couponName" data-cv="required,couponName"   placeholder="10字以内" maxlength="10" style="width: 285px;"></td>
                                </tr>
                                <tr>
                                    <th>面额</th>
                                    <td>
                                        <input type="text" id="amount" data-cv="required,positiveNum" maxlength="4"> 元
                                        <span class="grayFonts" id="common">9999元以内</span>
                                        <span class="grayFonts" id="special" style="display:none;">999999元以内</span>
                                    </td>
                                </tr>


                                <tr class="salepriceBlocks">
                                    <th>售价</th>
                                    <td>
                                        <input type="text" id="saleprice" data-cv="required,number" maxlength="6"> 元
                                        <span class="grayFonts">999999元以内</span>
                                    </td>
                                </tr>


                                <tr>
                                    <th>使用条件</th>
                                    <td>
                                        <input type="text" id="orderLeast" data-cv="required,number" maxlength="5"> 元
                                        <span class="grayFonts">满多少元可用</span>
                                    </td>
                                </tr>
                                 <tr>
                                    <th>有效期</th>
                                    <td>
                                        <input type="text" id="validTimeFrom"  style="width: 130px;" class="form-control startDate hasIcon">
                                        <input type="text" data-cv="hour" id="validHourFrom" style="width: 40px;" maxlength="2" value="00" readonly="readonly" > 时
                                        <input type="text" data-cv="minute" id="validMinFrom" style="width: 40px;" maxlength="2" value="00" readonly="readonly" > 分
                                        <em style="margin-left:5px;"> 至 </em>
                                        <input type="text" id="validTimeTo" data-cv="required" style="width: 130px;" class="datepicker endDate hasIcon">
                                        <input type="text" data-cv="hour" id="validHourTo" data-cv="required,number" style="width: 40px;" maxlength="2" value="23" readonly="readonly"></input> 时
                                        <input type="text" data-cv="minute" id="validMinTo" data-cv="required,number" style="width: 40px;" maxlength="2" value="59" readonly="readonly"></input> 分
                                    </td>
                                </tr>
                                <tr>
                                    <th>适用说明</th>
                                    <td><input type="text" data-cv="required" placeholder="全场通用" style="width: 285px;" maxlength="30" id="description"></td>
                                </tr>
                                <tr>
                                    <th class="verticalAlign">产品范围</th>
                                    <td>
                                        <div class="radioContent addCouponDiv">
                                            <label class="radio active addAllProduct" data-val="ALLPRODUCTS">
                                                <span class="radioIcon"></span>
                                                <span class="labelFonts">添加所有产品</span>
                                            </label>
                                            <label class="radio addInputProduct" data-val="MANUALADD">
                                                <span class="radioIcon"></span>
                                                <span class="labelFonts">手动添加产品</span>
                                            </label>
                                        </div>
                                        <textarea class="productRange" data-cv="productRange" placeholder="输入产品ID，以逗号隔开" id="productRange"></textarea>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton sureAddCoupon red-fe6869Button">确认</button>
                    <button type="button" class="commonButton blue-45c8dcButton cancleAddCoupon">取消</button>
                </div>
            </div>
        </div>
    </div>




    <!--   查看代金券内容 弹窗   -->
    <div class="modal fade" data-backdrop="static" data-keyboard="false" id="voucherInfoWindow">
        <div class="modal-dialog" style="width:560px;height:350px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">查看代金券</h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <table class="newVoucherTable">
                            <colgroup>
                                <col width="90">
                                <col width="">
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th>券名：</th>
                                    <td class="couponName"></td>
                                </tr>
                                <tr>
                                    <th>面额：</th>
                                    <td class="amount"></td>
                                </tr>
                                    <tr id="infoprice" style="display:none;">
                                    <th>售价：</th>
                                    <td class="saleprice"></td>
                                </tr>
                                <tr>
                                    <th>使用条件：</th>
                                    <td class="orderLeast"></td>
                                </tr>
                                <tr>
                                    <th>有效期：</th>
                                    <td><span class="validTimeFrom"></span> 至<span id="validTimeToInfo" clss="validTimeTo"></span></td>
                                </tr>
                                <tr>
                                    <th>适用说明：</th>
                                    <td class="description">全场通用</td>
                                </tr>
                                <tr>
                                    <th class="verticalAlign">产品范围：</th>
                                    <td><span id="productContent"></span></td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="modal-footer">
                </div>
            </div>
        </div>
    </div>
