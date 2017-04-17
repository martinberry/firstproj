<#import "/common/weixin/htmlIndex.ftl" as html/>
<#import "/common/weixin/headerBar.ftl" as head/>
<#import "/product/weixin/book/progress.ftl" as progress/>
<#import "/product/weixin/book/contactor.ftl" as contactor/>
<#import "/product/weixin/book/flightInfo.ftl" as flightInfo/>
<#import "/product/weixin/book/hotelInfo.ftl" as hotelInfo/>
	<@html.htmlIndex
			title="真旅行"
		  	remoteCssFiles=["mstatic/css/payment.css"]
		  	localJsFiles=[
		  					"product/weixin/book/init.js"
		  					"common/cusDateFormat.js",
		  					"common/ChinesePY.js",
		  					"common/typeahead.js",
		  					"common/weixin/common_utils.js",
		  					"common/weixin/validate.js",
		  					"common/weixin/cityArea.js",
		  					"common/password_verify.js",
		  					"product/weixin/book/jquery.tmpl.js",
		  					"product/weixin/book/passenger.js",
		  					"product/weixin/book/contactor.js",
		  					"product/weixin/book/price.js",
		  					"product/weixin/book/caculate.js",
		  					"product/weixin/book/apply.js",
		  					"product/weixin/book/coupon.js",
		  					"product/weixin/book/bookProduct.js"
		  				 ]
		    remoteJsFiles=["mstatic/js/vendor/datepicker-scroll.js",
		    				"mstatic/js/vendor/underscore.js",
		    				"mstatic/js/vendor/addresspicker-scroll.js",
		    				"mstatic/js/vendor/cus-list-pop.js"]>


	<@head.headerBar title="填写旅客信息"></@head.headerBar>
	<!-- 订单进度信息开始 -->
	<@progress.bookProgress />
    <!-- 订单进度信息结束 -->

    <!-- 成人儿童数量开始 -->
    <section class="commonModule noborderTop">
        <div class="startTime">出发时间: <span>${(productBookInfo.departDay)!}</span></div>
        <span class="peopleNum"><em>${productBookInfo.adultNum!'0'}</em>成人<em>${productBookInfo.childNum!'0'}</em>儿童</span>
    </section>
    <!-- 成人儿童数量结束 -->

	<!-- 产品名称开始 -->
    <div class="order-til" ><span id="productTitle">${productBookInfo.productTitle!""}</span><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${(productBookInfo.costPriceName)!''}</span></div>
	<!-- 产品名称结束 -->

	<!-- 机票信息开始 -->
	<#if (productBookInfo.nature)?? && (productBookInfo.nature)=='PACKAGE' && (productBookInfo.nature)=='COMBINATION'>
    	<@flightInfo.bookFlight />
    </#if>
    <!-- 机票信息结束 -->

	<!-- 酒店信息开始 -->
	<#if (productBookInfo.nature)?? && (productBookInfo.nature)=='PACKAGE' && (productBookInfo.nature)=='COMBINATION'>
    	<@hotelInfo.bookHotel />
    </#if>
    <!-- 酒店信息结束 -->

    <!-- 联系人信息开始 -->
    <div id="contactorDiv">
    <@contactor.contactor />
    </div>
    <!-- 联系人信息结束 -->

	<!-- 乘客信息开始 -->
	<input type="hidden" id="adultNum" value="${productBookInfo.adultNum!'0'}"/>
	<input type="hidden" id="childNum" value="${productBookInfo.childNum!'0'}"/>
	<input type="hidden" id="isLogin" value="${isLogin?c}"/>
	<input type="hidden" id="isDomestic" value="${(productBookInfo.isDomestic)!}"/>
	<input type="hidden" id="firstImageId" value="${(productBookInfo.firstImageId)!}" />
	<input type="hidden" id="tripDays" value="${(productBookInfo.tripDays)!}" />
	<input type="hidden" id="productNo" value="${(productBookInfo.productNo)!}" />
	<input type="hidden" id="productId" value="${(productBookInfo.productId)!}"/>
    <input type="hidden" id="packageId" value="${(productBookInfo.packageId)!}"/>
    <input type="hidden" id="packageName" value="${(productBookInfo.packageName)!}"/>
	<input type="hidden" id="bookDate" value="${(productBookInfo.departDay)!}"/>
	<input type="hidden" id="productType" value="${(productBookInfo.productType)!}" />
	<input id="originTotalPrice" type="hidden" value=${(productBookInfo.totalPrice)!''}>
	<input type="hidden" id="nature" value="${(productBookInfo.nature)!}" />
 	<input type="hidden" id="costPriceId" value="${(productBookInfo.costPriceId)!}" />


		<input type="hidden" id="adultPrice" value="${productBookInfo.adultPrice!'0'}"/>
		<input type="hidden" id="childPrice" value="${productBookInfo.childrenPrice!'0'}"/>
		<input type="hidden" id="singlePrice" value="${productBookInfo.singleRoomDiff!'0'}"/>

	<div id="passengerContainer">
	 </div>
    <!-- 乘客信息结束 -->

    <!-- 需求备注信息开始 -->
    <div class="order-mark">
        <div class="order-mark-til"><i class="needsIcon"></i>特殊需求</div>
        <#if (productBookInfo.nature)?? && (productBookInfo.nature)=='PACKAGE' && (productBookInfo.nature)=='COMBINATION'>
	        <div class="order-hotel-bed">
	            <div class="order-hotel-bedTil">偏好床型</div>
	            <div class="order-hotel-op">
	                <label>
	                    <input type="radio" class="bigBed" name="bedType" value="big" data-role="none" checked="checked">
	                    <span>大床</span>
	                </label>
	                <label>
	                    <input type="radio"  name="bedType" value="double" data-role="none">
	                    <span>双床</span>
	                </label>
	                <#if (productBookInfo.isBedTips)?? && (productBookInfo.isBedTips)?c == 'true'>
	                <div class="hotel-tip" style="display:none;">由于日本酒店的特殊政策，双床房价格需补差价。下单后，客服人员会联系您补拍差价。</div>
	                </#if>
	            </div>
	        </div>
        </#if>
        <textarea class="mark-textEara remark" maxlength="200" data-cv="remark" data-ct="需求备注"  placeholder="如有特殊需求，请在此备注" data-role="none"></textarea>
    </div>
    <!-- 需求备注信息开始 -->

    <!-- 代金券信息开始 -->
	<div id="couponContainer"></div>

    <!-- 代金券信息结束 -->

    <!-- 预订协议开始 -->
    <div class="agreement">
        <label class="agreelabel">
            <input type="checkbox" name="agr" value="agreement" data-role="none">
            <label class="cus_checkbox"></label>
        </label><span class="agreespan">我阅读并同意遵守</span><span class="agr-txt">《真旅行产品预订协议书》</span>
         <!--协议书弹窗-->
        <div class="agr-dialog" id="agr-dialog" data-role="popup" data-history="false" data-transition="pop" data-position-to="window" data-theme="a" data-overlay-theme="b">
            <div data-role="header">
                <h3>真旅行产品预订协议书</h3>
            </div>
            <div class="agr-dialog-content">
            	<p>
                	在预订真旅行产品前，请仔细阅读本协议，真旅行产品页面中的条款也作为双方协议的补充内容。当您提交订单时，表明您已经仔细阅读并接受本协议和产品页面中提及的所有条款。
	            </p>
	            <h3>一、产品及价格信息</h3>
	            <p>
	                真旅行网站提供自由行产品，以“机票＋酒店＋个性化服务”为核心，在真旅行提供的范围内，您可以自由选择出行日期及其他可选配的附加服务，无领队、导游随行。真旅行订单中详细写明价格明细（费用包含、费用不含、赠送项目），自由行产品的最终包含具体内容以订单确认通知为准。
	            </p>
	            <p>
	                您确认填写预订信息无误提交订单后，需要在30分钟以内付清全款，否则订单则被取消。如您未按要求及时付清相关费用，而此时真旅行为您预留的产品的价格、内容或标准等发生变化，真旅行对此将不承担任何责任。
	            </p>
	            <p>
	                真旅行保留在不事先知会的情况下随时更改其网站上已公布的产品价格的权利。
	            </p>
	            <h3>二、订单生效</h3>
	            <p>
	                您在预订成功付清全款，收到真旅行发送的订单确认通知后，本协议及产品页面提及所有条款生效并产生法律效力。
	            </p>
	            <p>
	                订单生效后，您应按订单中约定的时间或时段准时出发或按时入住所订酒店。如您未准时出发或未按时入住所订酒店，视为您主动解约。
	            </p>
	            <h3>三、生效订单退改</h3>
	            <h4>1. 用户更改生效订单</h4>
	            <p>
	                订单生效后，但经真旅行同意后，您可更改产品信息，但应选择同一出发地和目的地并仍在可售卖日期内的相同产品，由此增加的服务费用由您承担。
	            </p>
	            <p>
	                订单生效后，在行程开始15天前，必须经真旅行同意后，您可以将其在协议中的权利和义务转让给符合出游条件的第三人，但第三人必须具备本次度假产品所必须的签证和签注，变更后如果有费用增加，由您全部承担，同时真旅行收取变更服务费500元/人/次。
	            </p>
	            <p>
	                特殊产品不接受任何更改。
	            </p>
	            <h4>2. 用户取消生效订单</h4>
	            <p>
	                订单生效后，您若主动提出解除已生效订单，则视为您主动放弃相关行程，您也需要在行程开始前通知真旅行放弃订单的内容：全部放弃、减少出行人数、减少酒店入住房间数或晚数等信息，并承担提出取消时产生的订单损失。
	            </p>
	            <p>
	                特殊产品不接受任何取消退订。
	            </p>
	            <h4>3. 真旅行更改生效订单</h4>
	            <p>
	                订单生效后，因不可控原因引起的更改，如房间被政府征用，航空公司临时取消、更改航班等由不可抗力导致的情形，真旅行会尽力协助您办理相关退改事宜，但不承担因此造成的损失。
	            </p>
	            <p>
	                因真旅行原因引起的更改，真旅行承担变更所产生的直接损失，但不承担变更产品所产生的间接损失。
	            </p>
	            <p>
	                参考产品中“预订须知”，若产品中已事先告知可能会产生变更的内容，则不属变更需违约补偿的范围。
	            </p>
	            <h4>4. 真旅行取消生效订单</h4>
	            <p>
	                订单生效后，因不可控原因引起的取消，如房间被政府征用，航空公司临时取消、更改航班等由不可抗力导致的情形，真旅行会尽力协助您办理相关退改事宜，但不承担因此造成的损失。
	            </p>
	            <p>
	            	订单生效后，因真旅行的原因，导致您不能成行而取消的，真旅行应当立即通知您，除无条件退还您已支付的所有费用外，还应按如下标准支付违约金，但不涉及其他赔偿：
	            </p>
	            <p>
	                1）行程开始前61天以上通知您取消行程的，除退还您全部产品款项以外，另外支付给您全部产品费用的2%做为违约金，每张有效订单上限500元。
	            </p>
	            <p>
	                2）行程开始前31天至60天通知您取消行程的，除退还您全部产品款项以外，另外支付给您全部产品费用的5%做为违约金，每张有效订单上限1000元。
	            </p>
	            <p>
	                3）行程开始前7天至30天通知您取消行程的，除退还您全部产品款项以外，另外支付给您全部产品费用的10%做为违约金。
	            </p>
	            <p>
	            	4）行程开始当天至开始前6天通知您取消行程的，除退还您全部产品款项以外，另外支付给您全部产品费用的20%做为违约金。
	            </p>
	            <p>
	            	5）若您在真旅行办理的签证，产生的签证费损失由真旅行承担。但真旅行有权利注销您的签证，如果您仍需要该签证，费用则由您承担。
	            </p>
	            <h3>四、用户义务</h3>
	            <p>
	                1. 您应确保您不属于中国政府限制出境的人员之列。
	            </p>
	            <p>
	                2. 您应确保自身身体条件适合外出旅游度假。
	            </p>
	            <p>
	                3. 您向真旅行提交的因私护照或者通行证有效期在半年以上，自办签证和签注者应当确保所持签证和签注在出游期间有效。
	            </p>
	            <p>
	                4. 您应当确保所提供的证件、资料及联系电话真实有效，因您提供材料存在问题或者自身其他原因被拒签、缓签、拒绝入境和出境的，相关责任和费用由您承担，如给真旅行造成损失的，您还应当承担赔偿责任。
	            </p>
	            <p>
	                5. 您应按约定向真旅行全额支付服务费用。
	            </p>
	            <p>
	                6. 在合法权益受到损害要求协助索赔时，您应提供合法有效的凭据。
	            </p>
	            <p>
	                7. 您应在旅行中注意人身和财产的安全，因自身原因致使人身意外、财产权益受到损害的，真旅行会积极协助处理，但无赔偿责任。
	            </p>
	            <h3>五、真旅行义务</h3>
	            <p>
	                1. 真旅行按照订单确认通知约定的内容和标准为您提供服务。
	            </p>
	            <p>
	                2. 真旅行应告知您抵达目的地的具体接洽事宜。
	            </p>
	            <h3>六、第三方责任</h3>
	            <p>
	                由于出入境管理局、各国领馆、航空公司、保险公司、及其他有权机构等不可归责于真旅行的原因，导致您人身、财产权益受到损害的，包括但不限于，航班延误或取消、护照到期、签证拒签或未按时出签、不得出入境等，应由您自行协商解决，真旅行除在力所能及的范围内予以协助外，不承担其他责任，如给真旅行造成损失的，真旅行保留一切追偿权。
	            </p>
	            <h3>七、不可抗力</h3>
	            <p>
	                不可抗力，指不能预见、不能避免并不能克服的客观情况，包括但不限于因自然原因和社会原因引起的，如自然灾害、战争、恐怖活动、动乱、骚乱、罢工、突发公共卫生事件、政府行为、黑客攻击、电信部门技术管制。
	            </p>
	            <p>
	                因不可抗力导致无法履行或者继续履行已生效订单内容的，经协商双方均可以解除合同，合同解除后，真旅行退还您未实际发生的费用后不再承担其他任何责任；经协商双方同意变更合同的，因此增加的费用由您承担，减少的费用真旅行退还给您。双方均不承担违约责任但法律另有规定的除外。
	            </p>
	            <h3>八、特别提示</h3>
	            <p>
	                1. 真旅行有权根据您的身体健康状况及相关条件决定是否接纳报名。
	            </p>
	            <p>
	                2. 真旅行有权拒绝您提出的超出合同约定的不合理要求。
	            </p>
	            <p>
	                3. 旅客信息应最晚在离出发时间15天之前向真旅行提供，包括证件类型、证件号码、证件有效期、出生日期、性别、国籍等，并应保证上述信息的真实性。
	            </p>
	            <p>
	                4. 婴儿，旅游者携带婴儿出行的，需自行购买婴儿机票及办理婴儿签证等，如真旅行可以协助的，需额外收取该部分费用。
	            </p>
	            <p>
	                5. 酒店入住：登记入住酒店的客人中至少要有1人超过18周岁，即未满18周岁的客人必须由18周岁以上的客人随行并帮其办理酒店入住手续。按照国际惯例，酒店的入住时间为14：00后，离店时间为正午12：00前（个别酒店会有差别，具体以酒店要求为准）。如提前入住或推迟离店，均须酌情加收一定的费用，具体以酒店当日价格为准。
	            </p>
	            <p>
	                6. 时差，产品行程手册和订单确认单书上提及的到达时间和起飞时间均为所在国或地区的当地时间，您应合理注意旅游目的国和国内的时差。
	            </p>
	            <p>
	                7. 用户年龄，用户系18周岁以下（不包括18周岁）或 70周岁以上（包括70周岁）参加旅游，应有亲属同意，非单人出行。
	            </p>
	            <p>
	                8. 安全提示，您参加高原地区旅游或风险旅游项目（包括但不限于：游泳、浮潜、冲浪、漂流等水上活动以及骑马、攀岩、登山等高风险的活动）或患有不宜出行旅游的病情（包括但不限于：恶性肿瘤、心血管病、高血压、呼吸系统疾病、癫痫、怀孕、精神疾病、身体残疾、糖尿病、传染性疾病、慢性疾病健康受损），须在报名前自行前往医疗机构体检后，确保自身身体条件能够完成本次旅游活动，并向真旅行提供体检报告复印件；您须保证提供的身体健康状况真实，如隐瞒由本人承担全部责任；委托人系70周岁以上（含70岁）参加旅游，应有亲属同意，且非单人出行，同时在出行前如实填写并提交《身体健康申报表》真旅行已经给予您出游安全提示，但如果您仍坚持参加旅游活动，由此造成任何人身意外及不良后果将由您本人全部承担。
	            </p>
	            <h3>九、争议解决</h3>
	            <p>
	                本合同履行过程中发生争议，由双方协商解决，协商不成的，任何一方有权依法向上海长寿国际旅行社有限公司所在地人民法院起诉。
	            </p>
	            <h3>十、旅行社经营资质</h3>
	            <p>
	                真旅行由上海长寿国际旅行社有限公司运营。
	            </p>
	            <p>
	                旅行社名称：上海长寿国际旅行社有限公司
	            </p>
	            <p>
	                法定代表人：施纪军
	            </p>
            </div>
            <div class="agr-dialog-foot">
                <a class="btn-com btn-confirm" data-role="none" href="javascript:void(0);">同意并继续</a><a class="btn-com btn-cancel" data-role="none" href="javascript:void(0);">取 消</a>
            </div>
        </div>
    </div>
    <!-- 预订协议结束 -->

    <!-- 订单金额信息开始 -->
    <div class="order-sub">
        <div class="ui-grid-b">
            <div class="ui-block-a">
                总价
                <span id="firstPrice">${productBookInfo.totalPrice!}</span>
            </div>
            <div class="ui-block-b"><a href="#order-detail" class="block-link" id="scan-detail" data-rel="popup">查看明细<label class="arr-up"></label></a></div>
            <div class="ui-block-c">
                <a class="sub-disable submenu" href="javascript:void(0);" data-role="none" data-transition="slidefade" id="applyBtn">去付款</a>
            </div>
        </div>
    </div>
    <!-- 订单金额信息结束 -->



    <!--产看明细弹窗开始-->

    <div class="order-detail" id="order-detail" data-role="popup" data-history="false" data-transition="slideup" data-position-to="window" data-theme="a" data-overlay-theme="b" data-corners="false">
        <div id="priceDetail">
	    </div>

        <div class="order-foot">
            <div class="ui-grid-b">
                <div class="ui-block-a">
                    总价
                    <span id="secondPrice">${productBookInfo.totalPrice!}<span>
                </div>
                <div class="ui-block-b"><a class="block-link" href="javascript:void(0);" data-rel="back">查看明细<label class="arr-down"></label></a></div>
                <div class="ui-block-c"><a class="sub-disable submenu"  href="javascript:void(0);" data-role="none" data-transition="slidefade" id="applyBtn">去付款</a></div>
            </div>
        </div>
    </div>


    <!--产看明细弹窗开始-->
    <#include "passenger.ftl" />
    <#include "tipTemplate.ftl" />
    <#include "addressTemplate.ftl" />
</@html.htmlIndex>