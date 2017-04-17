<#import "/common/weixin/htmlIndex.ftl" as html />
<#import "/common/weixin/headerBar.ftl" as head/>

				<@html.htmlIndex
								remoteJsFiles=[]
								remoteCssFiles=["mstatic/css/publishEvaluate.css"]
								localCssFiles=[]
                                localJsFiles=[
                                "order/weixin/orderComment/comment.js",
                                "order/weixin/orderComment/init.js"
                                ]
                                title="发布评价">
<body>
	<div data-role="page">
		<!--订单列表菜单开始-->
		 <@head.headerBar title="发布评价"></@head.headerBar>
		<!--订单列表菜单结束-->

		<div class="submit-tip tips-cuccess" id="sucTips">评论成功！</div>
		<div class="submit-tip tips-fail" id="errTips">评论失败！</div>

		<div class="wrapper" id="commentForm">
			<div class="evaluateHeader">
				<span class="evaluateTitle"><a href="javascript:void(0);" data-role="none">${productTitle!''}</a></span>
			</div>
			<div class="evaluateFrame">
				<div class="evaluateStars">
					<span class="evalScore">评星：<i>5</i> 分</span><em class="active"></em><em class="active"></em><em class="active"></em><em class="active"></em><em class="nomargin active"></em>
				</div>
				<textarea placeholder="您的评价对其他小伙伴会有极大帮助哦" maxlength="500" id="commentTextarea" draggable="false" data-role="none"></textarea>
				<span class="wordNum">还可输入 <i>500</i> 字</span>
			</div>
			<div class="submitbtn"><a href="javascript:void(0);" id="submitCommentBtn" onclick="comment.submitComment()">提交</a></div>
		</div>
		 <input type="hidden" id="orderId" value="${orderId!''}" />
	</div>


</body>
</@html.htmlIndex>