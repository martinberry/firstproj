                <div class="moduleContent">
                    <div class="titleContent clearfix">
                        <h3 class="titleFonts">操作日志</h3>
                        <div class="titleRight">
                            <span class="flexible unfold">展开</span>
                            <span class="flexible packUp">收起</span>
                        </div>
                    </div>
                    <table class="grayThead flexibleContent">
                        <colgroup>
                            <col width="180">
                            <col width="110">
                            <col width="110">
                            <col width="500">
                        </colgroup>
                        <thead>
                        <tr>
                            <th>操作日期</th>
                            <th>操作内容</th>
                            <th>操作员</th>
                            <th>操作备注</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#if (orderDetail.operationLogs)??>
                        <#list orderDetail.operationLogs as log>
                        <tr>
                            <td>${(log.operateTime)!''}</td>
                            <td class="fontSize16">${(log.content)!''}</td>
                            <td class="fontSize16">${(log.operator)!''}</td>
                            <td>${(log.remark)!''}</td>
                        </tr>
                        </#list>
                        </#if>
                        </tbody>
                    </table>
                </div>