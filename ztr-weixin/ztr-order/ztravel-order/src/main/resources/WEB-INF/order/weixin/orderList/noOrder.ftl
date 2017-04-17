<#import "/common/weixin/htmlIndex.ftl" as html/>
<#import "/common/weixin/headerBar.ftl" as head/>

		<@html.htmlIndex
			title="无订单"
		  	remoteCssFiles=["mstatic/css/orderList.css"]
		>
<body>
<div class="without-order" data-role="page">
    <@head.headerBar title="无订单"></@head.headerBar>
    <div class="without-order-cnt">
        <img class="without-img" src="${host}/mstatic/images/without-order.png">
        <div class="without-txt">您现在还没有订单,去创建一个？</div>
    </div>
</div>
</body>

</@html.htmlIndex>