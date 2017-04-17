 <#import "/common/ftl/htmlBase.ftl" as html/> 
 
<@html.htmlBase cssFiles=[] jsFiles=["page/trade-date-record.js"] title="Trade Date Bill Record">
<script>
var dateBillId="${RequestParameters.billId!}";
</script>
	<div class="block1 block block-query clearfix">
            <div class="col col-l">
                <table>
                    <colgroup>
                        <col width="80">
                        <col width="100">
                        <col width="80">
                        <col width="100">
                        <col width="80">
                        <col width="230">
                    </colgroup>
                    <tr>
                        <th>支付机构</th>
                        <td>
                            <select id="gateType" style="width:90px;" data-role="dropdownlist" name="">
                                <option value="">全部</option>
                                <option value="AliPay">支付宝</option>
                                <option value="Tenpay">财付通</option>
                                <option value="Chinapnr">汇付天下</option>
                            </select>
                        </td>
                        <th>交易日期(yyyyMMdd)</th>
                        <td>
                            <input id="transDate" name="transDate">
                        </td>
                        <th>交易类型</th>
                        <td>
                            <select id="tradeType" style="width:90px;" data-role="dropdownlist" name="">
                                <option value="">全部</option>
                                <option value="PAY">交易付款</option>
                                <option value="REFUND">交易退款</option>
                                <option value="FEE">收费</option>
                                <option value="OTHER">其他</option>
                            </select>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="col col-r">
                <button class="btns button_2_1" id="buttonQuery">查 询</button>
            </div>
        </div>
        <div class="block2 block block-query-result">
            <div class="block-header">
                <button class="btns btn-ac-blue">导出Excel</button> 
            </div> 
            <table id="tableQueryResult">
                <colgroup>
                    <col width="150">
                    <col width="180">
                    <col width="100">
                    <col width="100">
                    <col width="180">
                    <col width="80">
                    <col width="160">
                    <col width="100">
                    <col width="100">
                    <col width="100">
                    <#--
                    <col width="100">
                    -->
                    <col width="150">
                </colgroup>
                <tr>
                    <th>交易时间</th>
                    <th>订单号</th>
                    <th>交易收入</th>
                    <th>交易支出</th>
                    <th>流水号</th>
                    <th>退单号</th>
                    <th>银行单号</th>
                    <th>交易类型</th>
                    <th>交易类型(网支)</th>
                    <th>交易备注</th>
                    <#--
                    <th>交易状态信息</th>
                     -->
                    <th>创建时间</th>
                </tr>
            </table>
            
		<script type="text/x-kendo-template" id="block2-grid-row-template">
		    <tr>
		<td>#=kendo.toString(new Date(transTime),'yyyy-MM-dd HH:mm:ss')#</td>
		<td>#=orderNum#</td>
		<td>#=kendo.toString(tradeIncome/100,"n")#</td>
		<td>#=kendo.toString(tradeExpend/100,"n")#</td>
		<td>#=traceNum#</td>
		<td>#=(refundNum||"")#</td>
		<td>#=bankNum#</td>
		<td>#=tradeType#</td>
		<td>#=originTradeType#</td>
		<td>#=transComment#</td>
		<#--
		<td>#=transStatusMsg#</td>
		-->
		<td>#=kendo.toString(new Date(createTime),'yyyy-MM-dd HH:mm:ss')#</td>
		</script>

        </div>
</@html.htmlBase>