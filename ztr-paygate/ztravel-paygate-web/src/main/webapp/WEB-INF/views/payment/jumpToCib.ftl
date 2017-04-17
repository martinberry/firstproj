<#import "/spring.ftl" as spring />
<html>
<head>
</head>
<body>
	<form name="sendOrder" action='${requestUrl}' method="post">	
		<input type="hidden" name="NetPaymentMerchantID" value="${(cibRequest.mchntId)!}" />
  		<input type="hidden" name="NetPaymentMerchantTraceNo" value="${(cibRequest.mchntTraceNo)!}" />
  		<input type="hidden" name="orderDate" value="${(cibRequest.orderDate)!}" />
  		<input type="hidden" name="AmountNumber" value="${(cibRequest.orderAmount)!}" />
  		<input type="hidden" name="Cjrxx" value="${(cibRequest.passengerInfo)!}" />
  		<input type="hidden" name="merchantURL" value="${(cibRequest.mchntUrl)!}" />
  		<input type="hidden" name="Userid" value="${(cibRequest.userId)!}" />
  		<input type="hidden" name="payUserName" value="${(cibRequest.payUserName)!}" />
  		<input type="hidden" name="payUserType" value="${(cibRequest.payUserType)!}" />
  		<input type="hidden" name="payUserId" value="${(cibRequest.payUserId)!}" />
  		<input type="hidden" name="Paytype" value="${(cibRequest.payType)!}" />
  		<input type="hidden" name="merchantMac" value="${(cibRequest.mchntMac)!}" />
	</form>
    
    <script language="javascript">
    
		function sub() {
			document.sendOrder.submit();
		}

		function toMax() {
			try {
				var a = screen.availWidth;
				var b = screen.availHeight;
				window.moveTo(0, 0);
				window.resizeTo(a, b);
			} catch (e) {
				;
			}

		}
		toMax();
		setTimeout('sub();', 0);
	</script>
    
</body>
</html>