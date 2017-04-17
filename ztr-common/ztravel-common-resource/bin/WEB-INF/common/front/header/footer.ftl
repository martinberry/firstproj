<div class="footer-wrap">
	<div class="foot clearfix">
		<div class="footer-nav">
			<div class="erweima">
				<img src="${host}/images/client/erweima.jpg" alt="">
				<div class="contact-info">
					<span class="info-title">关注我们</span><br>
					<span>微信公众号: zhenlx2015</span><br>
					<span class="info-title">服务热线</span><br>
					<span class="info-tell">400-620-6266 转6</span><br>
					<span>service@zhenlx.com</span>
				</div>
			</div>
			<div class="quick-nav">
				<dl>
	                <dt>常见问题</dt>
	                <dd><a href="javascript:toabout('before_travel','常见问题-出行前','出行前');">出行前</a></dd>
	                <dd><a href="javascript:toabout('traveling','常见问题-出行时','出行时');">出行时</a></dd>
	                <dd><a href="javascript:toabout('after_travel','常见问题-出行后','出行后');">出行后</a></dd>
	            </dl>
				<dl>
					<dt>关于真旅行</dt>
					<dd><a href="javascript:toabout('about_us','关于真旅行','关于我们');">关于我们</a></dd>
					<dd><a href="javascript:toabout('call_me','联系我们','联系我们');">联系我们</a></dd>
				</dl>
				<dl>
					<dt>服务说明</dt>
					<dd><a href="javascript:toabout('user_protocol','真旅行用户协议','用户协议');">用户协议</a></dd>
				</dl>
				<dl style="margin-right:45px;">
					<dt>网站条款</dt>
					<dd><a href="javascript:toabout('reserve_protocol','真旅行产品预订协议','预订协议');">预定协议</a></dd>
				</dl>
			</div>
		</div>
		<div class="footer-copyright">
			<span class="copyright">Copyright © 2015 真旅行 All Rights Reserved. 沪ICP备08004120号-5</span>
			<span class="service-tel">
				<span class="tellIcon"></span>服务热线：
				<span class="telnum">400-620-6266 转6</span>
			</span>
		</div>

		<div class="modal fade" id="ac-not-active">
		    <div class="modal-dialog" style="width: 450px;">
		        <div class="modal-content">
		            <div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		                    <span aria-hidden="true">&times;</span>
		                </button>
		                <h4 class="modal-title">提示</h4>
		            </div>
		            <div class="modal-body">
		                <p></p>
		            </div>
		            <div class="modal-footer">
		                <a href="javascript:void(0);" class="commonBtn blueBtn width170" onclick="$('#ac-not-active').modal('hide');">关闭</a>
		            </div>
		        </div>
		    </div>
		</div>
		
		<div class="modal fade" id="ac-not-login">
		    <div class="modal-dialog" style="width: 450px;">
		        <div class="modal-content">
		            <div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		                    <span aria-hidden="true">&times;</span>
		                </button>
		                <h4 class="modal-title">提示</h4>
		            </div>
		            <div class="modal-body">
		                <p></p>
		            </div>
		            <div class="modal-footer">
		                <a href="javascript:void(0);" class="commonBtn blueBtn width170" onclick="javascript:relogin();">重新登录</a>
		            </div>
		        </div>
		    </div>
		</div>
	</div>
</div>
<script type="text/javascript" src="${basepath}/resources/javascripts/common/ajax_ext.js"></script>
<script>
	var _hmt = _hmt || [];
	(function() {
	  var hm = document.createElement("script");
	  hm.src = "//hm.baidu.com/hm.js?bcf5ba51430753b5b7138a1ff3f8b534";
	  var s = document.getElementsByTagName("script")[0]; 
	  s.parentNode.insertBefore(hm, s);
	})();
</script>
<script type="text/javascript">
	var memberServer = "${memberServer}" ;
	function toabout(selector, title, menuName){
		window.open(memberServer + "/about?selector="+selector+"&title="+encodeURI(encodeURI(title))+"&menuName="+encodeURI(encodeURI(menuName))) ;
	}
	
	function relogin(){
		$('#ac-not-login').modal('hide');
		window.location.href = memberServer + "/home" ;
	}
	
	function popNotActive(){
		pop("#ac-not-active","咦？账号异常，请与客服联系~") ;
	};
	
	function popNotLogin(){
		pop("#ac-not-login","请重新登录，系统将在３秒后跳回主页") ;
		setTimeout("relogin();",3000);
	};
	
	function pop(winId,msg){
		$(winId).find("div.modal-body p").html(msg) ;
		$(winId).modal("show");
	}

	$(function(){
		$("#ac-not-active").modal({
	        backdrop:"static",
	        keyboard: false,
	        show: false
	    });
	    $("#ac-not-login").modal({
	        backdrop:"static",
	        keyboard: false,
	        show: false
	    });
	});
</script>