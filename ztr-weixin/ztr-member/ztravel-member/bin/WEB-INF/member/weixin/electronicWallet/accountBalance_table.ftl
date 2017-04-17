  <#if balanceDetailList ??>
	<#list balanceDetailList as friend>
		<li class="accountinfo">
		  <div class="accountUser">
			 <span class="username">${friend.friend!}</span>
		     <span class="isRegister"><#if friend.fStatus?exists>${friend.fStatus.value!} </#if></span>
		   </div>
		  <div class="accountBlance">
			<span class="accountNum">￥<em>${friend.bonusStr!}</em></span>
			<span class="isGiveOut"><#if friend.bStatus?exists>${friend.bStatus.value!}</#if></span>
		 </div>
	   </li>
	</#list>
  </#if>

