<#import "/common/front/header/navHeader.ftl" as html/>
<@html.navHeader
title="真旅行-账户余额"
currentMenu="我的钱包"
remoteJsFiles=[]
localJsFiles=["/member/front/electronicWallet/accountBalance/index.js","common/pagination.js","/member/front/common_utils.js"]
remoteCssFiles=["/css/client/wallet.css"]
localCssFiles=[]>

		<div class="main-wrapper" id="main-wrapper">
			<div class="main-box" id="main-box" style="top:30px;">
				<div class="top-border"><span class="clip"></span></div>
				<div class="box-container">
					<div class="cont-block clearfix">
                        <span class="modelLine"></span>
						<aside class="leftMenuContent">
                            <ul>
                                <li>
                                    <a href= "${basepath}/electronicWallet/coupon/index">代金券</a>
                                </li>
                                <li class="active">
                                   <a href="${basepath}/electronicWallet/accountBalance/index">账户余额</a>
                                </li>
                            </ul>
						</aside>
                        <section class="rightContent accountBalance">
                        	<div class="subTitle">我的余额</div>
							<div class="accountInfo">
								<span><img style="width:67px;height:67px;" class="round_photo"
                                src="${mediaserver}imageservice?mediaImageId=${headImageId!}" alt="" id="headImage"></span>
									<input type = "hidden" id = "accountVo" value = "${account!}">
								  <span class="money" id = "account" value="${account!}">￥${account?string("0.00")}</span>
								<a href="javascript:;" class="commonBtn orangeBtn" id="convert" >去兑换</a>
							</div>

                        	<div class="subTitle">好友帮赚钱
                        		<a href='${memberServer}/member/recommenderRewardPlan' target='_blank'>
									<span class="aboutMakeMoney green-border-tooltip"><i class="question-mark-icon"></i>关于赚钱</span>
								</a>
                        	</div>
							<table class="person-reward-table">
								<colgroup>
									<col width="25%"/>
									<col width="25%"/>
									<col width="25%"/>
									<col width="25%"/>
								</colgroup>
								<thead>
									<tr>
										<th>我的好友</th>
										<th>状态</th>
										<th>获得奖金</th>
										<th>奖金状态</th>
									</tr>
								</thead>
								<tbody>
								</tbody>
                    </table>

                   <div id="searchField">
                   </div>

	    			 <input type="hidden" name="pageNo" value="1" />
					<input type="hidden" name="pageSize" value="5" />

                        </section>
					</div>
				</div>
			</div>
		</div>


	<!---兑换模板--->
	    <div class="modal fade" data-backdrop="static" data-keyboard="false" id="exchangeModal">
        <div class="modal-dialog" style="width: 800px;height:350px;">
            <div class="modal-content" style="height:350px;">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title"></h4>
                </div>
                <div class="modal-body">
                    <div class="exchangeBlance">本期兑换的是<span>电话充值卡</span>哦，你的账户余额为<em class="balance" id="accountLeft">${account!'0'}</em>元，你本次可兑换
						<select>
							 <option class="ten" style="display;">10</option>
							<option class="thirty" style="display;">30</option>
							<option class="fifty" style="display;">50</option>
							<option class="onehunderd" style="display;">100</option>
						</select> 元
                    </div>
                    <div class="tellNum">
                    	<p><span>请确认您的手机号</span><input type="text" id="convert_phone" /><span class="phoneTips">手机号码填错啦，再试试吧</span></p>
						<p class="c999">请仔细核对充值手机号，已经提交不可更改；提交操作后，充值金额将在24小时内到账</p>
                    </div>
                </div>
                <div class="modal-footer">
                    <a href="javascript:void(0);" class="commonBtn blueBtn goExchange" style="width: 150px;" id="confirmToConvert">好哒，去兑换</a>
                    <a href="javascript:void(0);" class="commonBtn orangeBtn" style="width: 150px;" data-dismiss="modal">不用了，我是土豪</a>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" data-backdrop="static" data-keyboard="false" id="exchangeSuccesModal">
        <div class="modal-dialog" style="width:400px;height:200px;">
            <div class="modal-content" style="height:200px;">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title"></h4>
                </div>
                <div class="modal-body">
                    <p class="exchangeSucces">兑换成功，请注意查收</p>
                </div>
                <div class="modal-footer">
                    <a href="javascript:void(0);" class="commonBtn blueBtn goExchange" style="width: 125px;" data-dismiss="modal">嗯呢，知道了</a>
                </div>
            </div>
        </div>
    </div>


		<!--提示账户余额小于10元-->
	<div class="modal fade" data-backdrop="static" data-keyboard="false" id="notconvert">
		<div class="modal-dialog" style="width:400px;height:200px;">
			<div class="modal-content" style="height:200px;">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title"></h4>
				</div>
				<div class="modal-body">
					<p class="exchangeSucces">10块钱以上才能兑换电话卡，攒攒呗</p>
				</div>
				<div class="modal-footer">
					<a href="javascript:void(0);" class="commonBtn blueBtn goExchange" style="width: 125px;" data-dismiss="modal">知道了</a>
				</div>
			</div>
		</div>
	</div>


		<!--兑换成功模态框-->
 <div class="modal fade" data-backdrop="static" data-keyboard="false" id="convertsuccess">
		        <div class="modal-dialog" style="width: 510px;height:405px;">
		            <div class="modal-content">

		                <div class="modal-header" style="background-color:#DDDDDD">
		                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		                        <span aria-hidden="true">&times;</span>
		                    </button>
		                </div>
		                <div class="modal-body" style="text-align:center">
		                   兑换成功，请注意查收
		                    </div>
		                <div class="modal-footer">
		                    <a href="javascript:void(0);" class="commonBtn blueBtn width170"  data-dismiss="modal" id="cancelConvert">嗯呢，知道了</a>
		                </div>
		            </div>
		        </div>
		    </div>

</@html.navHeader>