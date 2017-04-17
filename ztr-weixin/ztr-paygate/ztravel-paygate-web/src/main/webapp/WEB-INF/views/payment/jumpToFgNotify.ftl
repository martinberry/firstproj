<html>
<head>
</head>
<body>
	<#if fgNotifyUrl ?? >
	
	<form name="notifyOrder" action='${fgNotifyUrl}' method="post">
  		<input type="hidden" name="clientId" value="${(resultBean.clientId)!}" />
  		<input type="hidden" name="gateType" value="${(resultBean.gateType)!}" />
  		<input type="hidden" name="retCode" value="${(resultBean.retCode)!}" />
  		<input type="hidden" name="retMsg" value="${(resultBean.retMsg)!}" />
  		<input type="hidden" name="orderNum" value="${(resultBean.orderNum)!}" />
  		<input type="hidden" name="payState" value="${(resultBean.payState)!}" />
  		<input type="hidden" name="bankPaymentTime" value="${(resultBean.bankPaymentTime)!}" />
  		<input type="hidden" name="traceNum" value="${(resultBean.traceNum)!}" />
  		<input type="hidden" name="amount" value="${(resultBean.amount)!}" />
	</form>
	
	<script language="javascript">

	
		function sub() {
			document.notifyOrder.submit();
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
	<#else>
	<h1>订单支付情况</h1>
		客户端ID:${resultBean.clientId!}<br/>
		支付平台:${resultBean.gateType!}<br/>
		返回码:${resultBean.retCode!}<br/>
		返回信息:${resultBean.retMsg!}<br/>
		订单号:${resultBean.orderNum!}<br/>
		支付状态:${resultBean.payState!}<br/>
		银行确认的支付时间:${resultBean.bankPaymentTime!}<br/>
		支付平台流水号:${resultBean.traceNum!}<br/>
		支付金额:${resultBean.amount!}<br/>
	
	</#if>
</body>
</html>