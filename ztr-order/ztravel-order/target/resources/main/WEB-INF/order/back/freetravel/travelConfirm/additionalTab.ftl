<#assign additionalProducts = (orderDetail.additionalProducts)!/>
<#list additionalProducts as addProduct>
<tr>
    <td>
        <span class="additionContent">${(addProduct.name)!}</span>
        <input type="text" class="additionEditContent" name="nameInputer" style="width: 98%;" placeholder="请输入名称" value="${(addProduct.name)!}" maxlength="100" onclick="focus();select()">
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