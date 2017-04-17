	<#if (result.activities)?? && (result.activities)?size gt 0>
		<#list (result.activities) as activity>
 					<tr>
                            <td>${(activity.code)!}</td>
                            <td>${(activity.name)!}</td>
                            <td class="orangefa7f1f">${(activity.typeDesc)!}</td>
                            <td>${(activity.couponNames)!}</td>
                            <td>${(activity.userRange)!}</td>
                            <td>${(activity.startTime)!}至 ${(activity.endTime)!}</td>
                            <td class="orangefa7f1f">${(activity.statusDesc)!}</td>
                            <td>${(activity.operator)!}</td>
                            <td>
                                <a href="${basepath}/activity/edit/${(activity.id)!}" class="greenFontsLink">查看</a>
                            </td>
                        </tr>
                        </#list>
                 </#if>
                 <-split->
				<#include "/common/opera/pagination.ftl"/>