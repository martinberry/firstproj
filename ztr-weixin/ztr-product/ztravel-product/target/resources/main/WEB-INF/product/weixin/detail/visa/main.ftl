<#import "/common/weixin/htmlIndex.ftl" as html/>
<#import "/common/weixin/headerBar.ftl" as head/>
<@html.htmlIndex title="${(product.basicInfo.pname)!''}"
		  	remoteCssFiles=["mstatic/css/product.css", "mstatic/css/swiper.min.css"]
		  	remoteJsFiles=["mstatic/js/vendor/swiper.jquery.min.js", "mstatic/js/base/fastclick.js","mstatic/js/vendor/jquery.lazyload.min.js"]
		  	localCssFiles=[]
		  	localJsFiles=["product/weixin/detail/visa/main.js"]>

<div class="viewport" data-role="page">
	<@head.headerBar title="${(product.basicInfo.pname)!''}"></@head.headerBar>
    <section class="main">
    <input id="pid" type="hidden" value="${(product.pid)!''}">
        <div class="productBanner">
            <img src="${mediaserver}imageservice?mediaImageId=${(product.firstImage)!}">
        </div>
        <#if (product.detailInfo)??>
	        <section class="commonModule noBorderTop">
	            <h2 class="titleH2">${(product.basicInfo.pname)!''}</h2>
	            <ul class="localGroup clearfix">
	                <li>面试要求<span><#if product.detailInfo.isInterview?c == "true">是<#else>否</#if></span></li>
	                <li>可停留日期<span><#if product.detailInfo.stayTime??>${(product.detailInfo.stayTime)!}</#if></span></li>
	                <li>可入境次数<span><#if product.detailInfo.times??>${(product.detailInfo.times)!}</#if>次</span></li>
	                <li>签证有效期<span><#if product.detailInfo.validate??>${(product.detailInfo.validate)!}</#if></span></li>
	            </ul>
	        </section>
		</#if>
        <section class="commonModule">
            <h2 class="commonHead haveIcon">受理范围</h2>
            <ul class="handleList">
                <#if (product.detailInfo.scope)??>
                	<pre>${(product.detailInfo.scope)!}</pre>
                </#if>
            </ul>
        </section>
        
        <!-- 签证材料start -->
        <section class="hotelModule">
            <div class="hotelModule-head">
                <h2 class="commonTitleH2 haveIcon"><i class="localvisa"></i>签证材料</h2>
            </div>
            <#if (product.materias)??>
	            <ul class="visaMaterial">
	            	<li>
	                    <a class="visaElem ui-link" data-val="EMPLOYED" data-transition="slidefade">
	                        <span>在职</span><i class="rightArrow"></i>
	                    </a>
	                </li>
	                <li>
	                    <a class="visaElem ui-link" data-val="FREE" data-transition="slidefade">
	                        <span>自由职业</span><i class="rightArrow"></i>
	                    </a>
	                </li>
	                <li>
	                    <a class="visaElem ui-link" data-val="STUDENT" data-transition="slidefade">
	                        <span>在校学生</span><i class="rightArrow"></i>
	                    </a>
	                </li>
	                <li>
	                    <a class="visaElem ui-link" data-val="RETIRE" data-transition="slidefade">
	                        <span>退休</span><i class="rightArrow"></i>
	                    </a>
	                </li>
	                <li>
	                    <a class="visaElem ui-link" data-val="CHILD" data-transition="slidefade">
	                        <span>学龄前儿童</span><i class="rightArrow"></i>
	                    </a>
	                </li>
	            </ul>
	             </#if>
        </section>
		<!-- 签证材料end -->
		
		<!-- 签证流程start -->
        <section class="hotelModule">
            <div class="hotelModule-head">
                <h2 class="commonTitleH2 haveIcon"><i class="localProcess"></i>办理流程</h2>
            </div>
            <#if (product.processes)?? && (product.processes)?size gt 0>
	            <#list product.processes as process>
		            <div class="visaProcess">
		                <h2 class="visaHead">
		                    <span class="processEle">${(process_index + 1)}</span>${(process.title)!''}
		                </h2>
		                <div class="visaContent"><#noescape>${(process.content)!''}</#noescape></div>
		            </div>
		            <#if (process.images)?? && (process.images)?size gt 0>
		            	<#list process.images as image>	
				            <div class="visaFoot">
				                <a class="chkimg post-link" href="javascript:;" data-image="${image!}">查看样图</a>
				            </div>
			            </#list>
		            </#if>
	            </#list>
	          </#if>
        </section>
        <!-- 签证流程end -->
		
		<!-- 费用说明start -->
        <section class="commonModule">
            <h2 class="commonTitleH2 haveIcon"><i class="costIcon"></i>费用说明</h2>

            <div class="costDetails">
                <h3 class="costTitle">费用说明</h3>
                <ul class="costList">
                    <#noescape>${(product.additional['FEE_DETAIL'])!}</#noescape>
                </ul>
            </div>
            <div class="costDetails">
                <h3 class="costTitle">退改政策</h3>
                <ul class="costList">
                    <#if product.additional['REFUND_POLICY'] ?? >
              			<#noescape>
              				${(product.additional['REFUND_POLICY'])!}
              			</#noescape>
          			</#if>
                </ul>
            </div>
        </section>
        <!-- 费用说明end -->
        
        <!-- 预定须知start -->
        <div id="orderPoint"></div>
        <section class="commonModule">
            <h2 class="commonTitleH2 haveIcon"><i class="noticeIcon"></i>预定须知</h2>

            <div class="costDetails">
                <ul class="costList maxHeight" id="orderWrap">
	                <#if product.additional['BOOKING'] ?? >
	                	<#noescape>${(product.additional['BOOKING'])!}</#noescape>
	                </#if>
                </ul>
            </div>
            <div class="commonBottomStyle orderNotice">
                <h3 class="commonBottomTil"><i class="bAndTArrow btmAndTopArrow"></i>查看更多</h3>
            </div>
            <div class="commonBottomStyle closeOrderNotice">
                <h3 class="commonBottomTil"><i class="bAndTArrow btmArrow"></i>收起</h3>
            </div>
        </section>
        <!-- 预定须知end -->
        
        <!-- 用户评价start -->
        <div id="judgePoint"></div>
        <section class="commonModule">
            <h2 class="commonTitleH2 haveIcon"><i class="evaluateIcon"></i>用户评价</h2>
			<#if commentList?? && commentList?size gt 0>
	            <div class="evaluateDetail">
	            	<#list commentList as comment>
		                <div class="judge-cnt clearfix">
		                    <div class="judge-top">
		                        <img class="pop-img" <#if comment.headImgId??>src="${mediaserver}imageservice?mediaImageId=${(comment.headImgId)!}"</#if>>
		                        <span class="pop-name">${(comment.memNickName)!''}</span>
		                        <span class="fr judge-date">${(comment.date)!''}</span>
		                    </div>
		                    <div class="judge-stars">
		                        <span class="yellow-star"></span>
		                        <span class="yellow-star"></span>
		                        <span class="yellow-star"></span>
		                        <span class="yellow-star"></span>
		                    </div>
		                    <p class="judge-txt">${(comment.comment)!''}</p>
		                </div>
	                </#list>
	            </div>
            <div class="commonBottomStyle judgeNotice">
                <h3 class="commonBottomTil"><i class="bAndTArrow btmAndTopArrow"></i>查看更多评论</h3>
            </div>
            <div class="commonBottomStyle closeJudgeNotice">
                <h3 class="commonBottomTil"><i class="bAndTArrow btmArrow"></i>收起</h3>
            </div>
            </#if>
        </section>
        <!-- 用户评价end  -->

        <div class="bottomFixed clearfix">
            <div class="leftPrice">
                <div class="priceFonts">价格 <strong>¥${(product.lowest)!''}起</strong></div>
            </div>
            <a class="rightBtn" href="javascript:void(0);">下一步</a>
        </div>
    </section>

</div>

<script type="text/javascript">
        $(function () {
            var root;
            var _w;
            var w;
            screenchange();
            window.addEventListener('orientationchange', function (event) {
                /*旋转至竖屏*/
                if ((window.orientation == 180 || window.orientation == 0) && (sessionStorage.s_fontsize == undefined)) {/*初始横屏加载切换至竖屏*/
                    _w = window.innerWidth <= window.screen.width ? window.innerWidth : window.screen.width;
                    w = _w >= 640 ? 640 : _w;

                    root.style.fontSize = (w / 320) * 20 + "px";
                    sessionStorage.s_fontsize = root.style.fontSize;
                }
                root.style.minHeight = window.innerHeight + "px";
            });
            function screenchange() {
                root = document.getElementsByTagName("html")[0];

                if (window.orientation == undefined) {
                    _w = window.innerWidth <= window.screen.width ? window.innerWidth : window.screen.width;
                    w = _w >= 640 ? 640 : _w;

                    root.style.fontSize = (w / 320) * 20 + "px";
                } else {
                    if (window.orientation == 180 || window.orientation == 0) {  /*竖屏加载*/
                        _w = window.innerWidth <= window.screen.width ? window.innerWidth : window.screen.width;
                        w = _w >= 640 ? 640 : _w;

                        root.style.fontSize = (w / 320) * 20 + "px";
                        sessionStorage.s_fontsize = root.style.fontSize;
                    }

                    if (window.orientation == 90 || window.orientation == -90) {  /*横屏加载*/
                        if (sessionStorage.s_fontsize == undefined) {   /*初始横屏加载*/
                            var w1 = window.innerHeight <= window.screen.height ? window.innerHeight : window.screen.height;
                            var w2 = w1 >= 640 ? 640 : w1;

                            root.style.fontSize = (w2 / 320) * 20 + "px";

                        } else {
                            root.style.fontSize = sessionStorage.s_fontsize;
                        }
                    }
                }
                root.style.minHeight = window.innerHeight + "px";
            }
        });
    </script>

<script>
    $(function () {

        FastClick.attach(document.body);
    });
</script>
</@html.htmlIndex>