
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>真旅行:职场人专属轻型在线旅行平台  轻松度假、深度体验、优质行程</title>
  <meta name="keywords" content="出境自由行，越南、西班牙、葡萄牙自由行，当地攻略，出国自助游_真旅行">
  <meta name="description" content="真旅行旅游产品订购平台 专注职场人群自由行方案 西班牙/葡萄牙/越南精品自由行 紧凑型线路,让你少请假;一地深度体验,有效游玩时间长;超详细攻略,出门就是旅行达人;全程服务保障,尽情投入在路上">
  <meta http-equiv="X-UA-Compatible" content="IE=Edge">
  <meta name="renderer" content="webkit|ie-comp|ie-stand">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <link rel="stylesheet" type="text/css" href="${host}/css/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="${host}/css/bootstrap.reset.css">
  <link rel="stylesheet" type="text/css" href="${host}/css/client/bootstrap.reset.css">
  <link rel="stylesheet" type="text/css" href="${host}/css/common.css">
  <link rel="stylesheet" type="text/css" href="${host}/css/swiper.min.css">
  <link rel="stylesheet" type="text/css" href="${host}/css/client/common.css">
  <link rel="stylesheet" type="text/css" href="${host}/css/client/indexnewpage.css">
  <link rel="stylesheet" type="text/css" href="${host}/css/client/workplatform.css">
  <link rel="stylesheet" type="text/css" href="${host}/css/client/searchheader.css">
  <link rel="stylesheet" type="text/css" href="${host}/css/client/rightSide.css">

  <script type="text/javascript" src="${host}/js/base/jquery-1.11.2.min.js"></script>
  <script type="text/javascript" src="${host}/js/bootdist/bootstrap.min.js"></script>
  <script type="text/javascript" src="${host}/js/client/common.js"></script>
  <script type="text/javascript" src="${host}/js/vendor/jquery.cycle.all.js"></script>
  <script type="text/javascript" src="${host}/js/vendor/swiper.jquery.min.js"></script>
  <script type="text/javascript" src="${host}/js/client/workplatform.js"></script>
  <script type="text/javascript" src="${host}/js/client/searchheader.js"></script>
  <script type="text/javascript" src="${host}/js/client/rightSide.js"></script>
  <script type="text/javascript" src="${basepath}/resources/javascripts/common/login_header.js"></script>
  <script type="text/javascript" src="${basepath}/resources/javascripts/member/front/login.js"></script>
  <script type="text/javascript" src="${basepath}/resources/javascripts/common/search_module.js"></script>

  <!--[if lte IE 9]>
  <script type="text/javascript" src="${host}/js/base/html5.js"></script>
  <![endif]-->

  <script type="text/javascript">
	var basepath = '${basepath}';
	var memberServer = '${memberServer}';
	var mediaServer = '${mediaserver}';
	var host = '${host}';
	var isLogin = '${(wpfv.isLogin)?c}';
	var ssoServer = '${ssoServer}' ;
  </script>
</head>
<body>
  <header>
	  <div class="banner_v3">
	  	<#include "/common/front/header/search_common_header_new.ftl"/>
	      <div id="scrollImg" style="height:620px;z-index:5;" >
	          <#if adSpots??>
	          	<#list adSpots as adSpot>
	          		<a href="${(adSpot.url)!}" target="_blank" class="fullscreen indexTopBanner01"
	          			style="<#if adSpot_index != 0>visibility:hidden;</#if>background: url(${mediaserver}imageservice?mediaImageId=${(adSpot.imageId)!}) no-repeat 50% 50%"></a>
	          	</#list>
	          </#if>
	      </div>
	  </div>
  </header>
  <div class="main-container">
      <#include "work_platform.ftl"/>
      <section>
          <div class="innerModel">
              <ul class="procuctOverview">
                  <li>
                      <i class="li_icon li_icon01"></i>
                      <div>
                          <p class="titleFonts">紧凑型线路</p>
                          <p>让你尽量少请假</p>
                      </div>
                  </li>
                  <li>
                      <i class="li_icon li_icon02"></i>
                      <div>
                          <p class="titleFonts">注重深度体验</p>
                          <p>保证有效游玩时间</p>
                      </div>
                  </li>
                  <li>
                      <i class="li_icon li_icon03"></i>
                      <div>
                          <p class="titleFonts">品味最精细</p>
                          <p>出门就是资深旅行家</p>
                      </div>
                  </li>
                  <li>
                      <i class="li_icon li_icon04"></i>
                      <div>
                          <p class="titleFonts">服务有保障</p>
                          <p>全身心投入在路上</p>
                      </div>
                  </li>
              </ul>
          </div>
      </section>
      <section>
          <div class="modelTitle">
              <span>热卖线路</span>
          </div>
          <div class="innerModel">

                  <#if recProducts??>
	          	    <#list recProducts as recProduct>
	          	    	<#if (recProduct_index)%3=0>
	          	    	    <ul class="hot_route">
	          	    	</#if>
	          	    	<li>
	                      <a href="${memberServer}/product/front/detail/${(recProduct.productId)!}" target="_blank">
	                          <img src="${mediaserver}imageservice?mediaImageId=${(recProduct.pictureId)!}" style="width:360px;height:289px;"/>
	                          <span class="priceTips">¥${(recProduct.lowestPrice)!}<em>起</em></span>
	                          <div class="bgShade hovercon">
	                              <h4>行程亮点</h4>
	                              <div class="innerContent">
	                              	  <#if (recProduct.highLights)??>
	                              	  	<#list recProduct.highLights as hl>
	                                  	<p>${hl!}</p>
	                                  	</#list>
	                                  </#if>
	                              </div>
	                              <div class="ellipsis_P" style="display: none;">...</div>
	                              <div class="minBox">
	                              	  <#if (recProduct.tags)??>
	                              	  	<#list recProduct.tags as tag>
	                                  	<i>${tag!}</i>
	                                  	</#list>
	                                  </#if>
	                              </div>
	                          </div>
	                      </a>
	                      <p class="product-descr">${(recProduct.mainTitle)!}</p>
	                      <p class="product-days">${(recProduct.viceTitle)!}</p>
	                  </li>
	                  <#if (recProduct_index+1)%3=0>
          	    	    </ul>
          	    	</#if>
	          	    </#list>
	          	    <a class="view-more" href="${(memberServer)!}/product/list?departure=上海&destination=世界&destLevel=1">查看更多+</a>
              	  </#if>

          </div>
      </section>
      
      <section>
          <div class="modelTitle">
              <span>签证中心</span>
              <div class="modelTil">快一点，便宜一点，安全一点，还有，不出签全退</div>
          </div>
          <div class="visaCenter">
                <ul class="visaWrap clearfix">
                    <li>
                        <div class="visaBox">
                            <img class="visaImg" src="${host}/images/client/visaImg01.jpg" alt="图片">
                            <div class="visaPrice">
                                <span class="priceNum">￥298</span>起
                            </div>
                            <div class="visaTil">日本</div>
                        </div>
                        <div class="visaFoot">
                            <div class="row visaFoot-row">
                                <div class="col-md-4"><a class="visaFoot-link" href="${memberServer}/product/visa/detail/S00001">日本单次</a></div>
                                <div class="col-md-4"><a class="visaFoot-link" href="${memberServer}/product/visa/detail/S00009">日本三年</a></div>
                                <div class="col-md-4"><a class="visaFoot-link" href="${memberServer}/product/visa/detail/S00012">日本五年</a></div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="visaBox">
                            <img class="visaImg" src="${host}/images/client/visaImg02.jpg" alt="图片">
                            <div class="visaPrice">
                                <span class="priceNum">￥299</span>起
                            </div>
                            <div class="visaTil">韩国</div>
                        </div>
                        <div class="visaFoot">
                            <div class="row visaFoot-row">
                                <div class="col-md-6"><a class="visaFoot-link" href="${memberServer}/product/visa/detail/S00004">韩国单次</a></div>
                                <div class="col-md-6"><a class="visaFoot-link" href="${memberServer}/product/visa/detail/S00002">韩国五年</a></div>
                            </div>
                        </div>
                    </li>
                </ul>
              <ul class="visaTrip clearfix">
                  <li>
                      <a href="${memberServer}/product/visa/detail/S00010">
                          <div class="visaTrip-box">
                              <img class="visaImg" src="${host}/images/client/visaImg03.jpg" alt="图片">
                              <div class="visaPrice">
                                  <span class="priceNum">￥1099</span>起
                              </div>
                              <div class="visaTil">美国</div>
                          </div>
                      </a>
                  </li>
                  <li>
                      <a href="${memberServer}/product/visa/detail/S00008">
                          <div class="visaTrip-box">
                              <img class="visaImg" src="${host}/images/client/visaImg04.jpg" alt="图片">
                              <div class="visaPrice">
                                  <span class="priceNum">￥999</span>起
                              </div>
                              <div class="visaTil">英国</div>
                          </div>
                      </a>
                  </li>
                  <li>
                      <a href="${memberServer}/product/visa/detail/S00003">
                          <div class="visaTrip-box">
                              <img class="visaImg" src="${host}/images/client/visaImg05.jpg" alt="图片">
                              <div class="visaPrice">
                                  <span class="priceNum">￥799</span>起
                              </div>
                              <div class="visaTil">德国</div>
                          </div>
                      </a>
                  </li>
                  <li>
                      <a href="${memberServer}/product/visa/detail/S00011">
                          <div class="visaTrip-box">
                              <img class="visaImg" src="${host}/images/client/visaImg06.jpg" alt="图片">
                              <div class="visaPrice">
                                  <span class="priceNum">￥799</span>起
                              </div>
                              <div class="visaTil">法国</div>
                          </div>
                      </a>
                  </li>
                  <li>
                      <a href="${memberServer}/product/visa/detail/S00013">
                          <div class="visaTrip-box">
                              <img class="visaImg" src="${host}/images/client/visaImg07.jpg" alt="图片">
                              <div class="visaPrice">
                                  <span class="priceNum">￥799</span>起
                              </div>
                              <div class="visaTil">西班牙</div>
                          </div>
                      </a>
                  </li>
                  <li>
                      <a href="${memberServer}/product/visa/detail/S00007">
                          <div class="visaTrip-box">
                              <img class="visaImg" src="${host}/images/client/visaImg10.jpg" alt="图片">
                              <div class="visaPrice">
                                  <span class="priceNum">￥799</span>起
                              </div>
                              <div class="visaTil">意大利</div>
                          </div>
                      </a>
                  </li>
                  <li>
                      <a href="${memberServer}/product/visa/detail/S00005">
                          <div class="visaTrip-box">
                              <img class="visaImg" src="${host}/images/client/visaImg08.jpg" alt="图片">
                              <div class="visaPrice">
                                  <span class="priceNum">￥258</span>起
                              </div>
                              <div class="visaTil">泰国</div>
                          </div>
                      </a>
                  </li>
                  <li>
                      <a href="${memberServer}/product/visa/detail/S00006">
                          <div class="visaTrip-box">
                              <img class="visaImg" src="${host}/images/client/visaImg09.jpg" alt="图片">
                              <div class="visaPrice">
                                  <span class="priceNum">￥320</span>起
                              </div>
                              <div class="visaTil">越南</div>
                          </div>
                      </a>
                  </li>
                  <li class="lastItem">
                      <a href="javascript:void(0);">
                          <div class="visaTrip-box">
                              <img class="visaImg" src="${host}/images/client/visaImg11.jpg" alt="图片">
                          </div>
                      </a>
                  </li>
              </ul>
          </div>
      </section>
      <section>
        <div class="modelTitle">
          <span class="big-quote left"></span>
          <span>最陌生的角落，也能变得熟悉</span>
          <span class="big-quote right"></span>
        </div>
          <div class="innerModel">
              <ul class="line_article">
                  <li onclick="javascript:$('#zhenGuide-hitWindow').modal('show');">
                  	<a href="javascript:void(0);">
                      <img src="${host}/images/client/line_article01.jpg"/>
                      <i class="">爱在里斯本的七件小事</i>
                    </a>
                  </li>
              </ul>
              <ul class="line_article">
                  <li >
                      <a href="javascript:void(0);">
                          <img src="${host}/images/client/line_article02.jpg"/>
                          <!--<i class="">我在美奈海风里放个空我在美奈海风里放个空</i>-->
                      </a>
                  </li>
                  <li onclick="javascript:$('#zhenGuide-hitWindow').modal('show');">
                      <a href="javascript:void(0);">
                          <img src="${host}/images/client/line_article03.jpg"/>
                          <i class="rb">午夜巴塞</i>
                      </a>
                  </li>
                  <li onclick="javascript:$('#zhenGuide-hitWindow').modal('show');">
                      <a href="javascript:void(0);">
                          <img src="${host}/images/client/line_article04.jpg"/>
                          <i class="rb">我在美奈海风里放个空</i>
                      </a>
                  </li>
              </ul>

          </div>
      </section>
      <section>
        <div class="modelTitle">
          <span class="big-quote left"></span>
          <span>在这里，旅行不是状态，而是生活</span>
          <span class="big-quote right"></span>
        </div>
        <div class="brandStory">
          <div class="brandStoryFonts">
            <h3 class="brandTitle">关于我们</h3>
            <div class="fontsDetails">
              <p style="margin-top:20px;">"旅行的意义从万物中浮现，沉默或者开口，充满我的灵魂。"</p>
              <p class="subTitle" style="width:205px;">
                <span class="left-border"></span>
                <span class="title">我们是谁？</span>
                <span class="right-border"></span>
              </p>
              <p>我们是一小撮热爱旅行、死磕旅行体验的小伙伴。<br>
              在与你相遇以前，我们去过不同的地方，有着各自的旅行故事，<br>也有着各自非常喜欢做的事情：音乐、文学、艺术、体育、科技……</p>

              <p class="subTitle" style="width: 340px;">
                <span class="left-border"></span>
                <span class="title">我们能为你的旅行带来什么?</span>
                <span class="right-border"></span>
              </p>
              <p>我们热衷分享旅行体验，让旅行变成生活方式：既不奢侈，也不廉价，让出行的每一天都富有新意、打动人心。<br>我们要为你提供亲身经历，体验完整，值得回味的旅行产品。</p>
              <p style="margin-top: 15px;">设计紧凑型线路 ， 让你尽量少请假。<br>
                注重一地深度体验 ，保证有效游玩时间。<br>
                品味最精细 ，第一次出门就是资深旅行家。<br>
                服务有保障 ，你可以全身心投入在路上。</p>
              <p class="bottom">这一切，都是为了不一样的深层旅行体验。</p>
            </div>
          </div>
        </div>
      </section>
      <section>
          <div class="modelTitle">
              <span>服务保障</span>
          </div>
          <ul class="serviceGuarantModel serveModel clearfix">
              <li>
                  <img src="${host}/images/client/serveImg01.png">
                  <h3>只做优品</h3>
                  <div class="explainFonts">从目的地的选择，到产品的制定，品质是我们的关键词，正如您的生活</div>
              </li>
              <li>
                  <img src="${host}/images/client/serveImg02.png">
                  <h3>体验升级</h3>
                  <div class="explainFonts">真旅管家保障旅途安心，线上线下真旅行打造体验完美升级</div>
              </li>
              <li>
                  <img src="${host}/images/client/serveImg03.png">
                  <h3>支付安全</h3>
                  <div class="explainFonts">专业技术团队保障支付安全</div>
              </li>
          </ul>
      </section>
        <div class="partnersModel">
          <a href="javascript:void(0);" class="partnerImg-1" title="丽莎航空"></a>
          <a href="javascript:void(0);" class="partnerImg-2" title="法国航空"></a>
          <a href="javascript:void(0);" class="partnerImg-3" title="Aroma Beach Resort and Spa"></a>
          <a href="javascript:void(0);" class="partnerImg-4" title="越南航空"></a>
          <a href="javascript:void(0);" class="partnerImg-5" title="H10 Hotels"></a>
          <a href="javascript:void(0);" class="partnerImg-6" title="Hotel Majestic Saigon"></a>
          <a href="javascript:void(0);" class="partnerImg-7" title="Pandanus Resort"></a>
          <a href="javascript:void(0);" class="partnerImg-8" title="Suites Alba Resort & Spa"></a>
        </div>
      </section>
  </div>
  <div class="modal fade" id="zhenGuide-hitWindow">
      <div class="modal-dialog" style="width: 450px;">
          <div class="modal-content">
              <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                      <span aria-hidden="true">&times;</span>
                  </button>
                  <h4 class="modal-title">提示</h4>
              </div>
              <div class="modal-body">
                  <p>开发小哥人手不够……模块马上上线，敬请期待！^.^</p>
              </div>
              <div class="modal-footer">
                  <a href="javascript:void(0);" class="commonBtn blueBtn width170" onclick="javascript:$('#zhenGuide-hitWindow').modal('hide');">OK</a>
              </div>
          </div>
      </div>
  </div>
  <#include "/common/front/right_side.ftl"/>
  <#include "/common/front/header/footer.ftl"/>
<script type="text/javascript">
	$(function(){

	    $("#zhenGuide-hitWindow").modal({
	        backdrop:"static",
	        keyboard: false,
	        show: false
	    });

    	window.onload = function() {
        	$(function(){
        		$('#scrollImg a').css('visibility','visible');
            	$('#scrollImg').before('<div id="nav" class="slider_nav" style="top:93%;">')
            		.cycle({fx:"fade",timeout: 7000,center:true,pause:true,pager:"#nav",pagerEvent: "click.cycle"});
            	$("#nav").css({
	              left: ($(window).width() - $("#nav").outerWidth()) / 2 + "px"
	            });
        	});
        } ;
	});

	$(function(){
	    $(".innerContent").each(function(){
	        if($(this).height() > 138){
	            $(this).addClass("limitHeight");
	            $(this).next(".ellipsis_P").show();
	        };
	    });
	})
	//logo login search 居中定位
	$(window).resize(function(){
	    resetPos();
	});

	function resetPos() {
	    var logo_left = ($(window).width()-1130)/2 + 1;
	    $(".index_logo").css({
	        left: logo_left + "px"
	    });
	    var login_right = ($(window).width()-1130)/2 + 1;
	    $(".index_top-right-block").css({
	        right: login_right + "px"
	    });
	    var s_left = ($(window).width() - $(".indexSearchBar").outerWidth()) / 2;
	    $(".indexSearchBar").css({
	        left: s_left + "px"
	    });
	    var nav_left = ($(window).width() - $("#nav").outerWidth()) / 2;
	    $("#nav").css({
	      left: nav_left + "px"
	    });
	}
	resetPos();
</script>
<#include "panguTracking.ftl"/>
</body>
</html>

