<#assign product = (orderDetail.product)!/>
<div class="commonOPOrderModel">
    <div class="commonSmallTitleModel blueFonts">
        <span class="leftTitleFonts">费用说明</span>
        <label class="commonEditBtn costContainEditBtn" <#if (readOnly)!false > style="display: none;" </#if>>
            <span class="commonIcon editIcon"></span>
        </label>
    </div>
    <div class="commonStyle">
        <table class="commonLightBlueThead leftTh costTab">
            <colgroup>
                <col width="170">
                <col width="">
            </colgroup>
            <tbody>
            <input type="hidden" name="feeInclude" value="${(product.additionalInfos['FEE_INCLUDE'])!}">
            <input type="hidden" name="feeNotInclude" value="${(product.additionalInfos['FEE_NOT_INCLUDE'])!}">
            <input type="hidden" name="giftItems" value="${(product.additionalInfos['GIFT_ITEMS'])!}">
            <input type="hidden" name="refundPolicy" value="${(product.additionalInfos['REFUND_POLICY'])!}">
            <tr>
                <th>费用包含</th>
                <td>
                    <div class="costContainContent">
                        <#noescape>${(product.additionalInfos['FEE_INCLUDE'])!}</#noescape>
                    </div>
                    <div class="costContainEdit">
                        <form>
                            <textarea name="content" id="feeIncludeInputer" class="editorText">${(product.additionalInfos['FEE_INCLUDE'])!}</textarea>
                            <div class="verifyStyle dropdown-error" style="display: none;"><i class="commonIcon errorIcon"></i>必填</div>
                        </form>
                    </div>
                </td>
            </tr>
            <tr>
                <th>费用不含</th>
                <td>
                    <div class="costContainContent">
                        <#noescape>${(product.additionalInfos['FEE_NOT_INCLUDE'])!}</#noescape>
                    </div>
                    <div class="costContainEdit">
                        <form>
                            <textarea name="content" id="feeNotIncludeInputer" class="editorText">${(product.additionalInfos['FEE_NOT_INCLUDE'])!}</textarea>
                            <div class="verifyStyle dropdown-error" style="display: none;"><i class="commonIcon errorIcon"></i>必填</div>
                        </form>
                    </div>
                </td>
            </tr>
            <tr>
                <th>赠送项目</th>
                <td>
                    <div class="costContainContent">
                        <#noescape>${(product.additionalInfos['GIFT_ITEMS'])!}</#noescape>
                    </div>
                    <div class="costContainEdit">
                        <form>
                            <textarea name="content" id="giftItemsInputer" class="editorText">${(product.additionalInfos['GIFT_ITEMS'])!}</textarea>
                            <div class="verifyStyle dropdown-error" style="display: none;"><i class="commonIcon errorIcon"></i>必填</div>
                        </form>
                    </div>
                </td>
            </tr>
            <tr>
                <th>退改政策</th>
                <td>
                    <div class="costContainContent">
                        <#noescape>${(product.additionalInfos['REFUND_POLICY'])!}</#noescape>
                    </div>
                    <div class="costContainEdit">
                        <form>
                            <textarea name="content" id="refundPolicyInputer" class="editorText">${(product.additionalInfos['REFUND_POLICY'])!}</textarea>
                            <div class="verifyStyle dropdown-error" style="display: none;"><i class="commonIcon errorIcon"></i>必填</div>
                        </form>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>
        <div class="commonSave costContainSave">
            <a href="javascript:void(0);" class="commonBtn blueBtn">保 存</a>
            <a href="javascript:void(0);" class="cancelBtn">取消</a>
        </div>
    </div>
</div>