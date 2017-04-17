<tr>
    <th>费用包含</th>
    <td>
        <div class="costContainContent">
            <#noescape>${(additionalInfos['FEE_INCLUDE'])!}</#noescape>
        </div>
        <div class="costContainEdit">
            <form>
                <textarea name="content" id="feeIncludeInputer" class="editorText">${(additionalInfos['FEE_INCLUDE'])!}</textarea>
                <div class="verifyStyle dropdown-error" style="display: none;"><i class="commonIcon errorIcon"></i>必填</div>
            </form>
        </div>
    </td>
</tr>
<tr>
    <th>费用不含</th>
    <td>
        <div class="costContainContent">
            <#noescape>${(additionalInfos['FEE_NOT_INCLUDE'])!}</#noescape>
        </div>
        <div class="costContainEdit">
            <form>
                <textarea name="content" id="feeNotIncludeInputer" class="editorText">${(additionalInfos['FEE_NOT_INCLUDE'])!}</textarea>
                <div class="verifyStyle dropdown-error" style="display: none;"><i class="commonIcon errorIcon"></i>必填</div>
            </form>
        </div>
    </td>
</tr>
<tr>
    <th>赠送项目</th>
    <td>
        <div class="costContainContent">
            <#noescape>${(additionalInfos['GIFT_ITEMS'])!}</#noescape>
        </div>
        <div class="costContainEdit">
            <form>
                <textarea name="content" id="giftItemsInputer" class="editorText">${(additionalInfos['GIFT_ITEMS'])!}</textarea>
                <div class="verifyStyle dropdown-error" style="display: none;"><i class="commonIcon errorIcon"></i>必填</div>
            </form>
        </div>
    </td>
</tr>
<tr>
    <th>退改政策</th>
    <td>
        <div class="costContainContent">
            <#noescape>${(additionalInfos['REFUND_POLICY'])!}</#noescape>
        </div>
        <div class="costContainEdit">
            <form>
                <textarea name="content" id="refundPolicyInputer" class="editorText">${(additionalInfos['REFUND_POLICY'])!}</textarea>
                <div class="verifyStyle dropdown-error" style="display: none;"><i class="commonIcon errorIcon"></i>必填</div>
            </form>
        </div>
    </td>
</tr>
