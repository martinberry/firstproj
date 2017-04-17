 <#import "/common/ftl/htmlBase.ftl" as html/> 
 
<@html.htmlBase cssFiles=[] jsFiles=["page/trade-date.js"] title="Trade Date Bill">
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
                    <col width="100">
                    <col width="80">
                    <col width="100">
                    <col width="80">
                    <col width="160">
                    <col width="80">
                </colgroup>
                <tr>
                    <th>商户号</th>
                    <th>支付机构</th>
                    <th>交易日期</th>
                    <th>是否已处理数据</th>
                    <th>数据处理信息</th>
                    <th>创建时间</th>
                </tr>
            </table>
            
		<script type="text/x-kendo-template" id="block2-grid-row-template">
		    <tr>
		<td>#=partner#</td>
		<td>#=gateType#</td>
		<td><a href="${basepath}/paygate/trade/page/trade-date-record?billId=#=dateBillId#"> #=kendo.toString(kendo.parseDate(transDate,"yyyyMMdd"),'yyyy-MM-dd')#</a></td>
		<td>#=(isChecked?"是":"否")#</td>
		<td>#=checkedMsg#</td>
		<td>#=kendo.toString(new Date(createTime),'yyyy-MM-dd HH:mm:ss')#</td>
		</script>

        </div>
</@html.htmlBase>