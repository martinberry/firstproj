
     			<input type="hidden" id="havailableCount" value="${availableCount!}">
	  		<input type="hidden" id="husedCount" value="${usedCount!}">
		  <input type="hidden" id="hexpiredCount" value="${expiredCount!}">
		   <input type="hidden" id="hdonateCount" value="${donateCount!}">
		    <input type="hidden" id="hstatus" value="${status!}">
    <div class="without-wallet-cnt">
        <img class="without-img" src="${host}/mstatic/images/without-wallet.png">
        <div class="without-txt">没有<#if status== 'AVAILABLE'>可使用</#if><#if status== 'USED'>已使用</#if><#if status== 'EXPIRED'>已失效</#if><#if status== 'SHARED'>已赠送</#if>的代金券</div>
    </div>

    <script>
      $(function(){
            $("#availableCount").html($("#havailableCount").val());
	       	$("#usedCount").html($("#husedCount").val());
	       	$("#expiredCount").html($("#hexpiredCount").val());
	       	$("#donateCount").html($("#hdonateCount").val());
       })

    </script>