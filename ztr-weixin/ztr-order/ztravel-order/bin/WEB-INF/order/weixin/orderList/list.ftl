<#import "/common/weixin/htmlIndex.ftl" as html/>
<#import "/common/weixin/headerBar.ftl" as head/>
		<@html.htmlIndex
			title="真旅行"
		  	remoteCssFiles=["mstatic/css/orderList.css"]
		  	localJsFiles=["order/weixin/orderList/init.js",
						  "order/weixin/orderList/scroll.js",
		  				  "order/weixin/orderList/order.js",
		  				  "order/weixin/orderList/list.js"]
		  				  remoteJsFiles=["mstatic/js/iscroll/iscroll-probe.js"]
		>

	<!--pageNum,总页数初始化-->
	<input type="hidden" id="totalPage" name="totalPage" value=${(orderListVo.totalPage)!'0'}>
	<input type="hidden" id="pageNum" name="pageNum" value='0'>

		<!--订单列表菜单开始-->
		<@head.headerBar title="我的订单"></@head.headerBar>
		<!--订单列表菜单结束-->

			<!--订单列表内容开始-->
			<div class="wrapper">
				<div class="orderNumber">
					<span class="orderSum"><em>${(orderListVo.totalNum)!}</em>个订单</span>
					<span class="orderNumberDetail">（待支付<em>${(orderListVo.totalUnpayNum)!}</em>待评价<em>${(orderListVo.totalUnRecNum)!}</em>)</span>				
				<div class="orderList" id="wrapper">
					<!--scroll start-->
					<div id="scroller">
						<!--头部加载提示信息开始-->
						<div id="pullDown" style="display:none;">
							<span class="pullDownIcon"></span><span class="pullDownLabel">下拉刷新...</span>
						</div>
						<!--头部加载提示信息结束-->
						<ul id="thelist">
						</ul>
						<!--底部加载提示信息开始-->
						<div id="pullUp">
							<span class="pullUpIcon"></span><span class="pullUpLabel">上拉加载更多...</span>
						</div>
						<!--底部加载提示信息结束-->
					</div>
					<!--scroll end-->
				</div>
			</div>
</@html.htmlIndex>