

      <div class="ui-content confirm-ui-content" data-role="popup" id="confirm-dialog" data-history="false" data-theme="a" data-overlay-theme="b" data-transition="pop" data-position-to="window" data-dismissible="false">
            <p class="dlg-cnt">删除常用旅客信息吗？</p>
            <div class="dlg-foot">
                <a class="btn-com btn-confirm" data-role="none" href="javascript:void(0);" data-rel="back">确 定</a>
                <a class="btn-com btn-cancel" data-role="none" href="javascript:void(0);" data-rel="back">取 消</a>
            </div>
        </div>
        <div class="ui-content confirm-ui-content" data-role="popup" id="priceChangeTip" data-history="false" data-theme="a" data-overlay-theme="b" data-transition="pop" data-position-to="window" data-dismissible="false">
            <p class="dlg-cnt">您的订单价格有变动，现价为：<br>¥<span id="newPriceInfo"></span></p>
            <div class="dlg-foot">
                <a class="btn-com btn-confirm" data-role="none" href="javascript:void(0);" id="priceChangeTipConfirm" >确 定</a>
            </div>
        </div>

        <div class="ui-content" data-role="popup" id="orderFailed" data-history="false" data-theme="a" data-overlay-theme="b"  data-transition="pop" data-position-to="window" data-dismissible="false">
	        <p class="dlg-cnt">建单失败,请稍后再试!</p>
	        <div class="dlg-foot">
	            <a class="btn-confirm" data-role="none" href="javascript:void(0);" data-rel="back">好的</a>
	        </div>
	    </div>

	    <div class="ui-content" data-role="popup" id="productOver" data-history="false" data-theme="a" data-overlay-theme="b"  data-transition="pop" data-position-to="window" data-dismissible="false">
	        <p class="dlg-cnt">产品库存不足!</p>
	        <div class="dlg-foot">
	            <a class="btn-confirm" data-role="none" href="javascript:void(0);">好的</a>
	        </div>
	    </div>

	    <div class="ui-content" data-role="popup" id="accountError" data-history="false" data-theme="a" data-overlay-theme="b"  data-transition="pop" data-position-to="window" data-dismissible="false">
	        <p class="dlg-cnt">咦?账号异常,请与客服联系~</p>
	        <div class="dlg-foot">
	            <a class="btn-confirm" data-role="none" href="javascript:void(0);" id="accountErrorConfirm">好的</a>
	        </div>
	    </div>

	    <div class="ui-content" data-role="popup" id="commonError" data-history="false" data-theme="a" data-overlay-theme="b"  data-transition="pop" data-position-to="window" data-dismissible="false">
	        <p class="dlg-cnt" id="commonErrorCont"></p>
	        <div class="dlg-foot">
	            <a class="btn-confirm" data-role="none" href="javascript:void(0);" data-rel="back">好的</a>
	        </div>
	    </div>

	    <div class="ui-content" data-role="popup" id="regisError" data-history="false" data-theme="a" data-overlay-theme="b"  data-transition="pop" data-position-to="window" data-dismissible="false">
	        <p class="dlg-cnt" id="regisErrorCont"></p>
	        <div class="dlg-foot">
	            <a class="btn-confirm" data-role="none" href="javascript:void(0);" data-rel="back">好的</a>
	        </div>
	    </div>

	    <div class="ui-content" data-role="popup" id="couponUsed" data-history="false" data-theme="a" data-overlay-theme="b"  data-transition="pop" data-position-to="window" data-dismissible="false">
	        <p class="dlg-cnt">该代金券已失效，请重新选择</p>
	        <div class="dlg-foot">
	            <a class="btn-confirm" data-role="none" href="javascript:void(0);" id="couponInvalidConfirm">好的</a>
	        </div>
	    </div>


	     <!--提交弹窗-->
	    <div class="submit-dlg" id="submit-dlg" data-role="popup" data-history="false" data-transition="pop" data-theme="a" data-overlay-theme="b">
            <div class="ui-grid-a submit-dlg-title">该账号已注册，请登录</div>
	        <div class="ui-grid-a">
	            <div class="ui-block-a"><i class="person-icon"></i></div>
	            <div class="ui-block-b"><input class="submit-dlg-val" type="text" id="accountInputer" data-cv="required,userName" data-ct="用户名" placeholder="手机号" data-role="none"></div>
	        </div>
	        <div class="ui-grid-a">
	            <div class="ui-block-a"><i class="lock-icon"></i></div>
	            <div class="ui-block-b"><input class="submit-dlg-val" type="password" id="password" placeholder="密码" data-cv="required" data-ct="用户密码" data-role="none"></div>
	        </div>
	        <div class="dlg-foot">
	            <a class="submit-dlg-confirm" data-role="none" href="javascript:void(0);">继续预订</a>
	        </div>
	    </div>
	    <!--校验提示-->
	    <div class="ui-content" data-role="popup" id="validateTip" data-history="false" data-theme="a" data-overlay-theme="b"  data-transition="pop" data-position-to="window" data-dismissible="false">
	        <p class="dlg-cnt" id="tipMsg"></p>
	        <div class="dlg-foot">
	            <a class="btn-confirm" style="background:transparent;" data-role="none" href="javascript:void(0);" data-rel="back">好的</a>
	        </div>
	    </div>

	       <!--快速注册-->
	    <div class="submit-dlg" id="regist-dlg" data-role="popup" data-history="false" data-transition="pop" data-position-to="window" data-theme="a" data-overlay-theme="b">
	    <div class="ui-grid-a submit-dlg-title">请完成注册，以便查阅订单信息</div>
	        <div class="ui-grid-a">
	            <div class="ui-block-a"><i class="person-icon"></i></div>
	            <div class="ui-block-b"><input class="submit-dlg-val" type="text" id="newMobilePhone" placeholder="手机号" data-role="none"></div>
	        </div>
	        <div class="verify-block clearfix">
	            <input class="input-txt" type="text" id="newVerifyCode" placeholder="验证码" data-role="none"><a class="btn-vld" data-role="none" href="javascript:void(0);">获取短信验证码</a>
	        </div>
	        <div class="ui-grid-a">
	            <div class="ui-block-a"><i class="lock-icon"></i></div>
	            <div class="ui-block-b"><input class="submit-dlg-val" id="newPassWord" type="password" placeholder="密码" data-role="none"></div>
	        </div>
	        <div class="dlg-foot">
	            <a class="regist-dlg-common regist-dlg-confirm" data-role="none" id="orderContinue"  href="javascript:void(0);">继续预订</a>
	            <a class="regist-dlg-common regist-dlg-back" data-role="none" id="backToEditBtn"  href="javascript:void(0);">返回修改预订信息</a>
	        </div>
	    </div>
	</div>
