   <#if makeMoneyFriends??>
        <#list makeMoneyFriends as friend>
				<tr>
					<td>${friend.fMobile!}</td>
					<td>${friend.fStatus.value!}</td>
					<td class="money">${friend.bonus!}</td>
					<td class="status">${friend.bStatus.value!}</td>
				</tr>
        </#list>
 </#if>
   <-split->
 </tbody>
</table>
   <#include "/common/front/pagination.ftl"/>
   <script>
   $(function(){
    $("tr").find(".money").each(function(){
		    var amount = $(this).html();
    		$(this).html("￥"+new Number(amount/100).toFixed(2));
    })
   })
   </script>