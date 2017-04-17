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
    <link rel="stylesheet" type="text/css" href="${host}/css/common.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/client/common.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/swiper.min.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/client/indexnewpage.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/client/workplatform.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/client/searchheader.css">
    <link rel="stylesheet" type="text/css" href="${basepath}/resources/css/member/front/round_image.css">

    <script type="text/javascript" src="${host}/js/base/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="${host}/js/bootdist/bootstrap.min.js"></script>
    <script type="text/javascript" src="${host}/js/client/common.js"></script>
    <script type="text/javascript" src="${host}/js/client/workplatform.js"></script>
    <script type="text/javascript" src="${host}/js/client/searchheader.js"></script>
    <script type="text/javascript" src="${host}/js/vendor/swiper.jquery.min.js"></script>
    <script type="text/javascript" src="${host}/js/vendor/jquery.cycle.all.js"></script>
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

  <#include "/common/front/header/search_common_header.ftl"/>

  <div class="main-container">
      <div class="banner">
        <ul class="product">
          <li class="banner-image-1">
              <!-- <div class="banner-image-cont cont-1">
                <div class="image-cont-cover"></div>
              </div> -->
              <div class="image-bottom-text"></div>
          </li>
          <li class="banner-image-2">
              <!-- <div class="banner-image-cont cont-2">
                <div class="image-cont-cover"></div>
              </div> -->
              <div class="image-bottom-text"></div>
          </li>
          <li class="banner-image-3">
            <!-- <div class="banner-image-cont cont-3">
              <div class="image-cont-cover"></div>
            </div> -->
            <div class="image-bottom-text"></div>
          </li>
          <li class="banner-image-4">
              <!-- <div class="banner-image-cont cont-4">
                <div class="image-cont-cover"></div>
              </div> -->
              <div class="image-bottom-text"></div>
          </li>
          <li class="banner-image-5">
            <!-- <div class="banner-image-cont cont-5">
              <div class="image-cont-cover"></div>
            </div> -->
            <div class="image-bottom-text"></div>
          </li>
        </ul>
        <ul class="cont-slide">
          <li class="cont-slide-place">
            <div class="plus-icon-1"><i class="icon"></i></div>
            <div class="plus-icon-2"><i class="icon"></i></div>
            <div class="banner-image-cont cont-1">
              <div class="image-cont-cover"></div>
            </div>
          </li>
          <li class="cont-slide-place">
            <div class="plus-icon-1"><i class="icon"></i></div>
            <div class="plus-icon-2"><i class="icon"></i></div>
            <div class="banner-image-cont cont-2">
              <div class="image-cont-cover"></div>
            </div>
          </li>
          <li class="cont-slide-place">
            <div class="plus-icon-1"><i class="icon"></i></div>
            <div class="plus-icon-2"><i class="icon"></i></div>
            <div class="banner-image-cont cont-3">
              <div class="image-cont-cover"></div>
            </div>
          </li>
          <li class="cont-slide-place">
            <div class="plus-icon-1"><i class="icon"></i></div>
            <div class="plus-icon-2"><i class="icon"></i></div>
            <div class="banner-image-cont cont-4">
              <div class="image-cont-cover"></div>
            </div>
          </li>
          <li class="cont-slide-place">
            <div class="plus-icon-1"><i class="icon"></i></div>
            <div class="plus-icon-2"><i class="icon"></i></div>
            <div class="banner-image-cont cont-5">
              <div class="image-cont-cover"></div>
            </div>
          </li>
        </ul>
        <ul class="bannerbtn">
          <li class="leftbtn"></li>
          <li class="rightbtn"></li>
        </ul>
      </div>
      <#include "work_platform.ftl"/>
      <section>
        <div class="modelTitle">
          <span class="big-quote left"></span>
          <span>在这里，整个世界都是用来分享的</span>
          <span class="big-quote right"></span>
        </div>
         <ul class="shareModel clearfix">
          <li>
            <div class="imgTransformation">
              <div class="shareImage">
                <span class="shareImageCover"></span>
                <img src="${host}/images/client/shareImage-01.jpg">
              </div>
              <span class="shareIcon01"></span>
            </div>
            <h3>代金券分享</h3>
            <div class="explainFonts">可以分享给朋友圈的“真实货币”，<br>兑现蠢蠢欲动的愿望</div>
          </li>
          <li class="special">
            <div class="imgTransformation">
              <div class="shareImage">
                <span class="shareImageCover"></span>
                <img src="${host}/images/client/shareImage-02.jpg">
              </div>
              <span class="shareIcon02"></span>
              <span class="erweima"></span>
            </div>
            <h3>真旅故事</h3>
            <div class="explainFonts">每周旅行达人故事分享会，<br>用一句话击中内心</div>
            <div class="scanQR">
              <img src="${host}/images/client/wechat-qrcode.jpg">
              <h3>扫描二维码</h3>
              <div class="explainFonts">加入温馨的真旅行大家庭</div>
            </div>
          </li>
          <li>
          	<a href="${memberServer}/member/recommenderRewardPlan" target="_blank">
	            <div class="imgTransformation">
	              <div class="shareImage">
	                <span class="shareImageCover"></span>
	                <img src="${host}/images/client/shareImage-03.jpg">
	              </div>
	              <span class="shareIcon03"></span>
	            </div>
            	<h3>推荐人奖励计划</h3>
            </a>
            <div class="explainFonts">我们邀请你们，你们推荐他们，<br>分甘同味，人人尝鲜</div>
          </li>
        </ul>
      </section>
      <section>
        <div class="modelTitle">
          <span>服务保障</span>
        </div>
        <ul class="serviceGuarantModel serveModel clearfix">
          <li>
            <img src="${host}/images/client/serveImg01.png">
            <h3>只做优品</h3>
            <div class="explainFonts">从目的地的选择，到产品的制定，品质是我们的关键词，正如您的生活选择</div>
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
          <span>真旅行团队</span>
        </div>
        <div class="titleExplain">这里是我们萌萌哒的团队成员，每位都为真旅行努力付出着，可能还是会有很多不足，但我们不会错过每一个进步的机会。</div>
        <div class="aboutUsModel">
        <div class="aboutUsModel">
          <ul class="aboutUs">
	        <li>
	          <ul class="aboutUsImg">
	            <li>
	              <div class="workmate-img img-1"></div>
	              <div>酷兔</div>
	            </li>
	            <li>
	              <div class="workmate-img img-2"></div>
	              <div>军师</div>
	            </li>
	            <li>
	              <div class="workmate-img img-3"></div>
	              <div>sukie</div>
	            </li>
	            <li>
	              <div class="workmate-img img-4"></div>
	              <div>Dorian</div>
	            </li>
	            <li>
	              <div class="workmate-img img-5"></div>
	              <div>xyuner</div>
	            </li>
	            <li>
	              <div class="workmate-img img-6"></div>
	              <div>更帅</div>
	            </li>
	            <li>
	              <div class="workmate-img img-7"></div>
	              <div>方致格</div>
	            </li>
	          </ul>
	        </li>
	        <li class="secondLi">
	          <ul class="aboutUsImg">
	            <li>
	              <div class="workmate-img img-8"></div>
	              <div>Zoey Liu</div>
	            </li>
	            <li>
	              <div class="workmate-img img-9"></div>
	              <div>小龙人</div>
	            </li>
	            <li>
	              <div class="workmate-img img-10"></div>
	              <div>佳哥</div>
	            </li>
	            <li>
	              <div class="workmate-img img-11"></div>
	              <div>晶桦</div>
	            </li>
	            <li>
	              <div class="workmate-img img-12"></div>
	              <div>ziqi</div>
	            </li>
	            <li>
	              <div class="workmate-img img-13"></div>
	              <div>NAN</div>
	            </li>
	          </ul>
	        </li>
	        <li class="thirdLi">
	          <ul class="aboutUsImg">
	            <li>
	              <div class="workmate-img img-14"></div>
	              <div>Abby</div>
	            </li>
	            <li>
	              <div class="workmate-img img-15"></div>
	              <div>楠童鞋</div>
	            </li>
	            <li>
	              <div class="workmate-img img-16"></div>
	              <div>tengteng</div>
	            </li>
	            <li>
	              <div class="workmate-img img-17"></div>
	              <div>iminright</div>
	            </li>
	            <li class="addAboutUsImg">
	              <div class="workmate-img img-18"></div>
	              <div>YOU</div>
	            </li>
	          </ul>
	        </li>
	      </ul>
      </div>
      </section>
      <section>
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

  <#include "/common/front/right_side.ftl"/>
  <#include "/common/front/header/footer.ftl"/>

<script type="text/javascript">
    $(function(){
    	window.onload = function() {
	      $('.cont-slide').cycle({
	        fx:'fade',
	        timeout: 7000,
	        speed: 1500,
	        prev:".bannerbtn .leftbtn",
	        next:".bannerbtn .rightbtn"
	      });
	      //banner轮播
	      $('.product').cycle({
	        fx:'fade',
	        timeout: 7000,
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
	    }

		window.onresize = function(){
	      resizeBannerTextPos();
	    }

        $(".image-cont-cover").click(function(){
	      window.location.href = memberServer + "/product/list?departure=上海&destination=&destLevel=1";
	    });

	    $(".special .erweima").hover(function(){
	      $('.special .scanQR').stop().animate({'top':'0'},400);
	    },function(){
	      $('.special .scanQR').stop().animate({'top':'445px'},400);
	    });
	    resizeBannerTextPos() ;
	});

	function resizeBannerTextPos() {
	  if (window.innerHeight < 725) {   //  小屏情况，重新调整banner图片位置
	    $(".product").addClass("small");
	    $(".cont-slide").addClass("small");
	  } else {
	    $(".product").removeClass("small");
	    $(".cont-slide").removeClass("small");
	  }
	}
</script>
<#include "panguTracking.ftl"/>
</body>
</html>