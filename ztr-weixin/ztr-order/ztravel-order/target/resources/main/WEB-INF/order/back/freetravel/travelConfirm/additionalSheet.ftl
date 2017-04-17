<#assign additionalProducts = (orderDetail.additionalProducts)!/>
<div class="commonOPOrderModel">
    <div class="commonSmallTitleModel blueFonts">
        <span class="leftTitleFonts">附加产品</span>
        <label class="commonEditBtn additionEditBtn" <#if (readOnly)!false > style="display: none;" </#if>>
            <span class="commonIcon editIcon"></span>
        </label>
    </div>
    <div class="commonStyle">
        <table class="commonLightBlueThead addiProTable" id="addiProTable">
            <colgroup>
                <col width="40%">
                <col width="15%">
                <col width="15%">
                <col width="20%">
            </colgroup>
            <thead>
            <tr>
                <th>名称</th>
                <th>单价</th>
                <th>数量</th>
                <#if (readOnly)!false >
                <th>合计</th>
                <#else>
                <th>
                    <div class="editable">
                        <span>合计</span>
                        <span class="commonAddDelete additionAddDelete">
                            <em class="commonAdd additionAdd"></em>
                        </span>
                    </div>
                </th>
                </#if>
            </tr>
            </thead>
            <tbody>
            <#list additionalProducts as addProduct>
            <tr>
                <td>
                    <span class="additionContent">${(addProduct.name)!}</span>
                    <input type="text" class="additionEditContent" name="nameInputer"  style="width: 98%;" placeholder="请输入名称" value="${(addProduct.name)!}" maxlength="100" onclick="focus();select()">
                    <div class="verifyStyle dropdown-error" style="display: none;"><i class="commonIcon errorIcon"></i>必填</div>
                </td>
                <td>
                    <span class="additionContent">${(addProduct.price)!}</span>
                    <input type="text" class="additionEditContent" name="priceInputer" style="width: 98%;" placeholder="请输入单价" value="${((addProduct.price)?number)/1}" maxlength="5" onclick="focus();select()">
                    <div class="verifyStyle price-error" style="display: none;"><i class="commonIcon errorIcon"></i>单价必须是1到99999之间的整数</div>
                    <div class="verifyStyle dropdown-error" style="display: none;"><i class="commonIcon errorIcon"></i>必填</div>
                </td>
                <td>
                    <span class="additionContent">${(addProduct.num)!'0'}</span>
                    <div class="additionEditContent">
                        <span class="addAndSubtract">
                            <a href="javascript:void(0);" class="subtractBtn">-</a>
                            <input type="text" class="numberFonts" name="numInputer" value="${(addProduct.num)!'0'}" maxlength="3" onclick="focus();select()">
                            <a href="javascript:void(0);" class="addBtn">+</a>
                            <div class="verifyStyle num-error" style="display: none;"><i class="commonIcon errorIcon"></i>数量必须是1到100之间的整数</div>
                        </span>
                    </div>
                </td>
                <td>
                    <div class="editable">
                        <span class="totalPrice">${(addProduct.totalPrice)!}</span>
                        <span class="commonAddDelete additionAddDelete">
                            <em class="commonDelete additionDelete"></em>
                        </span>
                    </div>
                </td>
            </tr>
            </#list>
            </tbody>
        </table>
        <div class="commonSave additionSave">
            <a href="javascript:void(0);" class="commonBtn blueBtn" id="saveAddiPro">保 存</a>
            <a href="javascript:void(0);" class="cancelBtn" id="cancelAddiPro">取消</a>
        </div>
    </div>
</div>

<!-- 附加产品信息模板 -->
<script id="addiProTemplate" type="text/x-jquery-tmpl">
    <tr>
        <td>
            <span class="additionContent">{{= name}}</span>
            <input type="text" class="additionEditContent" name="nameInputer" style="width: 98%;" placeholder="请输入名称" value="{{= name}}" maxlength="100" onclick="focus();select()">
            <div class="verifyStyle dropdown-error" style="display: none;"><i class="commonIcon errorIcon"></i>必填</div>
        </td>
        <td>
            <span class="additionContent">{{= price}}</span>
            <input type="text" class="additionEditContent" name="priceInputer" style="width: 98%;" placeholder="请输入单价" value="{{= price/1}}" maxlength="5" onclick="focus();select()">
            <div class="verifyStyle price-error" style="display: none;"><i class="commonIcon errorIcon"></i>价格必须是1到99999之间的整数</div>
            <div class="verifyStyle dropdown-error" style="display: none;"><i class="commonIcon errorIcon"></i>必填</div>
        </td>
        <td>
            <span class="additionContent">{{= num}}</span>
            <div class="additionEditContent">
                <span class="addAndSubtract">
                    <a href="javascript:void(0);" class="subtractBtn">-</a>
                    <input type="text" class="numberFonts" name="numInputer" value="{{= num}}" maxlength="3" onclick="focus();select()">
                    <a href="javascript:void(0);" class="addBtn">+</a>
                    <div class="verifyStyle num-error" style="display: none;"><i class="commonIcon errorIcon"></i>数量必须是1到100之间的整数</div>
                </span>
            </div>
        </td>
        <td>
            <div class="editable">
                <span class="totalPrice">{{= totalPrice}}</span>
                <span class="commonAddDelete additionAddDelete">
                    <em class="commonDelete additionDelete"></em>
                </span>
            </div>
        </td>
    </tr>
</script>

<!-- 附加产品添加产品模板 -->
<script type="text/html" id="addAdditionTr">
    <tr>
        <td>
            <span class="additionContent" style="display: none;"></span>
            <input type="text" class="additionEditContent" name="nameInputer" style="width: 98%;display: inline-block;" placeholder="请输入名称" maxlength="100" onclick="focus();select()">
            <div class="verifyStyle dropdown-error" style="display: none;"><i class="commonIcon errorIcon"></i>必填</div>
        </td>
        <td>
            <span class="additionContent" style="display: none;"></span>
            <input type="text" class="additionEditContent" name="priceInputer" style="width: 98%;display: inline-block;" placeholder="请输入单价" maxlength="5" onclick="focus();select()">
            <div class="verifyStyle price-error" style="display: none;"><i class="commonIcon errorIcon"></i>价格必须是1到99999之间的整数</div>
            <div class="verifyStyle dropdown-error" style="display: none;"><i class="commonIcon errorIcon"></i>必填</div>
        </td>
        <td>
            <span class="additionContent" style="display: none;"></span>
            <div class="additionEditContent" style="display: block;">
                <span class="addAndSubtract">
                    <a href="javascript:void(0);" class="subtractBtn">-</a>
                    <input type="text" class="numberFonts" name="numInputer" value="1" maxlength="3" onclick="focus();select()">
                    <a href="javascript:void(0);" class="addBtn">+</a>
                    <div class="verifyStyle num-error" style="display: none;"><i class="commonIcon errorIcon"></i>数量必须是1到100之间的整数</div>
                </span>
            </div>
        </td>
        <td>
            <div class="editable">
                <span class="totalPrice"></span>
                <span class="commonAddDelete additionAddDelete" style="display: inline-block;">
                    <em class="commonDelete additionDelete"></em>
                </span>
            </div>
        </td>
    </tr>
</script>