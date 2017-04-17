<#import "/spring.ftl" as spring />
<html>
<head>
</head>
<body>
	<form name="sendOrder" action='${confirmBean.payURL}' method="post">	
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