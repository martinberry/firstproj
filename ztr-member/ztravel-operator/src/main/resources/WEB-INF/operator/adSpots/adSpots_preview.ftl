
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>广告位-预览</title>
  <meta http-equiv="X-UA-Compatible" content="IE=Edge">
  <meta name="renderer" content="webkit|ie-comp|ie-stand">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <link rel="stylesheet" type="text/css" href="${host!}/css/bootstrap.min.css">
  <link rel="stylesheet" type="text/css" href="${host!}/css/bootstrap.reset.css">
  <link rel="stylesheet" type="text/css" href="${host!}/css/client/bootstrap.reset.css">
  <link rel="stylesheet" type="text/css" href="${host!}/css/common.css">
  <link rel="stylesheet" type="text/css" href="${host!}/css/swiper.min.css">
  <link rel="stylesheet" type="text/css" href="${host!}/css/client/common.css">
  <link rel="stylesheet" type="text/css" href="${host!}/css/client/indexnewpage.css">
  <link rel="stylesheet" type="text/css" href="${host!}/css/client/workplatform.css">
  <link rel="stylesheet" type="text/css" href="${host!}/css/client/searchheader.css">
  <link rel="stylesheet" type="text/css" href="${host!}/css/client/rightSide.css">

  <script type="text/javascript" src="${host!}/js/base/jquery-1.11.2.min.js"></script>
  <script type="text/javascript" src="${host!}/js/bootdist/bootstrap.min.js"></script>
  <script type="text/javascript" src="${host!}/js/client/common.js"></script>
  <script type="text/javascript" src="${host!}/js/vendor/jquery.cycle.all.js"></script>
  <script type="text/javascript" src="${host!}/js/vendor/swiper.jquery.min.js"></script>
  <script type="text/javascript" src="${host!}/js/client/searchheader.js"></script>
  <script type="text/javascript" src="${host!}/js/client/rightSide.js"></script>

    <!--[if lte IE 9]>
    <script type="text/javascript" src="${host!}/js/base/html5.js"></script>
    <![endif]-->
</head>

  <body>

<div class="main-container">
    <div class="banner_v3">
        <!-- logo -->
        <a  class="index_logo"></a>
        <!-- login -->
        <div class="index_top-right-block">
            <div class="operate index_login-entrance" >
                <a class="login" href="javascript:;">登录</a><a class="register" href="javascript:;">注册</a>
            </div>
            <div class="index_after-login">
                                <span class="userName">
                                  <span class="commonIcon userIcon"></span>
                                  <em>Dorian zhou</em>
                                </span>
            </div>
        </div>
        <!-- searchBar -->
        <div class="indexSearchBar">
            <div class="searchModel index_searchModel">
                <div class="searchContent">
                    <table class="searchTab">
                        <colgroup>
                            <col width="98">
                            <col width="142">
                            <col width="100">
                            <col width="424">
                            <col width="">
                        </colgroup>
                        <tbody>
                        <tr>
                            <th class="addIcon"><span class="commonIcon startOffIcon"></span><span class="startPlace-title">出发地:</span></th>
                            <td>
                                <div class="startOffPlaceModel">
                                    <div class="startOffPlace">
                                        <input type="text" class="startOffInput" value="上海" style="width: 141px;" readonly="readonly">
                                        <span class="caret"></span>
                                    </div>
                                </div>
                            </td>
                            <th class="addIcon"><span class="commonIcon destinationIcon"></span><span class="destination-title">目的地:</span></th>
                            <td>
                                <div class="destinationModel">
                                    <div class="destination">
                                        <input type="text" class="destinationInput" value="" style="width:466px;" placeholder="看世界" readonly="readonly">
                                        <span class="caret"></span>
                                    </div>
                                </div>
                            </td>
                            <td class="searchbtn">
                                <a href="javascript:void(0);"></a>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

              <div id="scrollImg" style="height:620px;z-index:5;" >
                 <#if imageIds??>
	          	<#list imageIds as imageId>
	          		<div class="fullscreen indexTopBanner01" style="background: url(${mediaserver}imageservice?mediaImageId=${imageId!}) no-repeat 50% 50%"></div>
	          	</#list>
	          </#if>
              </div>
          </div>
</div>


  <div class="main-container">
      <div class="work-platform">
        <div class="workpla-login">
          <span class="logintext">您还未登录账号，请先登录 </span>
          <div class="componentInput"> <i class="commonIcon personHeaderIcon"></i>
            <input type="text" placeholder="手机号/邮箱" style="width:224px;">
          </div>
          <div class="componentInput forgetPW"> <i class="commonIcon lockIcon"></i>
            <input type="password" placeholder="请输入密码" style="width:142px;">
            <a href="javascript:void(0);" class="forgetPWFonts">忘记密码?</a>
          </div>
          <a href="javascript:void(0);" class="commonBtn blueBtn loginBtn" style="width:262px;">登 录</a>
          <div class="login-attach">
            <div class="checkboxContent">
              <label class="checkbox active">
                <span class="commonIcon checkboxIcon"></span>
                记住我
              </label>
            </div>
            <a class="register-link" href="javascript:;">免费注册</a>
          </div>
        </div>
        <div class="workpla-afterlogin">
          <div class="exit">
            <div class="exitbtn">
              <span class="commonIcon exitIcon"></span>
              <span>退出</span>
            </div>
          </div>
          <div class="userInfo">
            <span class="leftImg">
              <img src="${host!}/images/client/avatar-01.jpg">
            </span>
            <div class="rightUserInfo">
              <span class="rightUsername">太平洋的鲸鱼</span>
            </div>
          </div>
          <div class="columnModel">  <!-- 栏目模块 -->
            <ul>
              <li>
                <a href="javascrip:void(0);">
                  <span class="orderIcon"></span>
                  <span>我的订单</span>
                </a>
              </li>
              <li>
                <a href="javascrip:void(0);">
                  <span class="favorIcon"></span>
                  <span class="current">心愿清单</span>
                </a>
              </li>
              <li>
                <a href="javascrip:void(0);">
                  <span class="elecWalletIcon"></span>
                  <span>电子钱包</span>
                </a>
              </li>
              <li>
                <a href="javascrip:void(0);">
                  <span class="messageIcon"></span>
                  <span>消息中心</span>
                </a>
              </li>
            </ul>
          </div>
          <div class="tipTab">
            <div class="tab-toggle">
              <ul class="message-tip-tab clearfix">
                <li class="current">提醒<span></span></li>
                <li>私信<span></span></li>
                <li><div class="slidline"></div></li>
              </ul>
              <div class="tab-cont-block">
                <div class="tab-cont remind remind-cont-block">
                  <div class="remind-tab-cont tab-cont-height">
                    <ul>
                      <li class="unread"><span class="remind-conmenicon unread-ordericon"></span>您的订单 <a href="javascrip:void(0);"> 上海-巴黎5晚6天年轻人的第一次高 逼格欧洲行 (S6589421356870) </a> 已创建，请支付。</li>
                      <li ><span class="remind-conmenicon ordericon"></span>您的订单 <a href="javascrip:void(0);"> 上海-巴黎5晚6天年轻人的第一次高 逼格欧洲行 (S6589421356870) </a> 已创建，请支付。</li>
                      <li><span class="remind-conmenicon ordericon "></span>您的订单 <a href="javascrip:void(0);"> 上海-巴黎5晚6天年轻人的第一次高 逼格欧洲行 (S6589421356870) </a> 已创建，请支付。</li>
                    </ul>
                  </div>
                  <a class="more" href="javascript:void();">更多</a>
                </div>
                <div class="tab-cont private-letter privletter-cont-block">
                  <div class="privletter-tab-cont tab-cont-height">
                    <div class="privletter-empty-block">
                      <span class="letter-empty-img"></span>
                      <p>您暂时还不有私信哦！</p>
                    </div>
                  </div>
                  <a class="more" href="javascript:void();">更多</a>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="workplabtn"><span></span></div>
      </div>
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
	                      <a href="javascript:void(0);">
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
              	  </#if>
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
                  <li>
                      <a href="javascript:void(0);">
                          <img src="${host!}/images/client/line_article01.jpg"/>
                          <i class="">我在美奈海风里放个空我在美奈海风里放个空我在美奈海风里放个空我在美奈海风里放个空</i>
                      </a>
                  </li>
              </ul>
              <ul class="line_article">
                  <li>
                      <a href="javascript:void(0);">
                          <img src="${host!}/images/client/line_article02.jpg"/>
                          <!--<i class="">我在美奈海风里放个空我在美奈海风里放个空</i>-->
                      </a>
                  </li>
                  <li>
                      <a href="javascript:void(0);">
                          <img src="${host!}/images/client/line_article03.jpg"/>
                          <i class="rb">我在美奈海风里放个空</i>
                      </a>
                  </li>
                  <li>
                      <a href="javascript:void(0);">
                          <img src="${host!}/images/client/line_article04.jpg"/>
                          <i class="rb">我在美奈海风里放个空风里放个空我在美奈海风里放个空风里放个空</i>
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
                  <img src="${host!}/images/client/serveImg01.png">
                  <h3>只做优品</h3>
                  <div class="explainFonts">从目的地的选择，到产品的制定，品质是我们的关键词，正如您的生活</div>
              </li>
              <li>
                  <img src="${host!}/images/client/serveImg02.png">
                  <h3>体验升级</h3>
                  <div class="explainFonts">真旅管家保障旅途安心，线上线下真旅行打造体验完美升级</div>
              </li>
              <li>
                  <img src="${host!}/images/client/serveImg03.png">
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
  <div class="rightSide">
    <div class="feedback">
      <span class="feedbackbtn"></span>
      <div class="modal fade" id="ac-payHintWindow">
        <div class="modal-dialog " style="width: 604px;height:346px;">
          <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">意见反馈</h4>
            </div>
            <div class="modal-body">
              <form action="" method="">
                <textarea class="suggest" placeholder="感谢您的意见反馈，我们会把产品做到更好"></textarea>
                <input type="text" class="contactor" placeholder="留下您的手机号或邮箱，方便我们和您取得联系">
              </form>
            </div>
            <div class="modal-footer">
                <a href="javascript:void(0);" class="commonBtn blueBtn width125">提交</a>
                <a href="javascript:void(0);" class="commonBtn nocolorBtn width125" data-dismiss="modal" aria-label="Close">取消</a>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="wechat">
      <span class="wechatbtn"></span>
      <div class="wechatQR">真旅行官方微信<span><img src="${host!}/images/client/wechat-qrcode.jpg"></span></div>
    </div>
    <div class="returnTop"></div>
  </div>

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
  	</div>
  </div>
<script type="text/javascript">
$(function(){

    window.onload = function() {

        $(function(){
            $('#scrollImg').before('<div id="nav" class="slider_nav" style="left:47%;top:93%;">').cycle({fx:"fade",timeout: 2000,center:true,pause:true,pager:"#nav",pagerEvent: "click.cycle"});
        });

      //banner轮播
      $('.product').cycle({
        fx:'fade',
        timeout: 2000,
        speed: 1500,
        prev:".bannerbtn .leftbtn",
        next:".bannerbtn .rightbtn"
      });
      $('.cont-slide').cycle({
        fx:'fade',
        timeout: 2000,
        speed: 1500,
        prev:".bannerbtn .leftbtn",
        next:".bannerbtn .rightbtn"
      });

      $(".plus-icon-1").hover(function(){
        $('.cont-slide').cycle("pause");
        $('.product').cycle("pause");
      }, function(){
        $('.cont-slide').cycle("resume");
        $('.product').cycle("resume");
      });

      $(".plus-icon-2").hover(function(){
        $('.cont-slide').cycle("pause");
        $('.product').cycle("pause");
      }, function(){
        $('.cont-slide').cycle("resume");
        $('.product').cycle("resume");
      });
    };



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
    }
    resetPos();
});

$(function(){
    $(".innerContent").each(function(){
        if($(this).height() > 138){
            $(this).addClass("limitHeight");
            $(this).next(".ellipsis_P").show();
        };
    });
})
</script>

</body>
</html>

