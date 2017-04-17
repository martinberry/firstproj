<#import "/common/weixin/htmlIndex.ftl" as html />

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>真旅行推荐人奖励计划</title>
	 <input type="hidden" id= "wxServer" value="${wxServer!}">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
	<link rel="stylesheet" href="${host}/mstatic/css/swiper.min.css">
	<link rel="stylesheet" href="${host}/mstatic/css/animate.min.css">
	<link rel="stylesheet" href="${host}/mstatic/css/sharePlanH5.css">

	<script type="text/javascript" src="${host}/mstatic/js/base/jquery.min.js"></script>
	<script type="text/javascript" src="${host}/mstatic/js/vendor/swiper.jquery.min.js"></script>
	<script type="text/javascript" src="${host}/mstatic/js/vendor/swiper.animate.min.js"></script>
	<script>
	window.onload = function(){
		var mySwiper = new Swiper ('.swiper-container', {
		direction : 'vertical',
		mousewheelControl : true,
		onInit: function(swiper){
			swiperAnimateCache(swiper);
			swiperAnimate(swiper);
		},
		onSlideChangeEnd: function(swiper){
			swiperAnimate(swiper);
		},
		onTransitionEnd: function(swiper){
			swiperAnimate(swiper);
			if (swiper.activeIndex !== 2) {
				$(".slide3-lineBox").fadeOut();
				$(".slide3-lineBox").removeClass('active');
			}
			if (swiper.activeIndex !== 3) {
				$(".slide4-cloudBox").fadeOut()/*.children('img').removeClass('active')*/;
			}
			if (swiper.activeIndex !== 4) {
				$(".slide5-cloudBox").fadeOut()/*.children('img').removeClass('active')*/;
			}
			if (swiper.activeIndex !== 5) {
				$(".slide6-wildgoose1").removeClass('active');
				$(".slide6-wildgoose2").removeClass('active');
			}
		},
		watchSlidesProgress: true,
		onProgress: function(swiper){
			for (var i = 0; i < swiper.slides.length; i++){
				var slide = swiper.slides[i];
				var progress = slide.progress;
				var translate = progress*swiper.height/4;
					scale = 1 - Math.min(Math.abs(progress * 0.5), 1);
				var opacity = 1 - Math.min(Math.abs(progress/2),0.5);
					slide.style.opacity = opacity;
					es = slide.style;
					es.webkitTransform = es.MsTransform = es.msTransform = es.MozTransform = es.OTransform = es.transform = 'translate3d(0,'+translate+'px,-'+translate+'px) scaleY(' + scale + ')';
			}
		},
		onSetTransition: function(swiper, speed) {
			for (var i = 0; i < swiper.slides.length; i++){
				es = swiper.slides[i].style;
				es.webkitTransitionDuration = es.MsTransitionDuration = es.msTransitionDuration = es.MozTransitionDuration = es.OTransitionDuration = es.transitionDuration = speed + 'ms';
			}
		},
		onTransitionStart: function(swiper) {
			if (swiper.activeIndex == 2) {
				setTimeout(function(){
					$(".slide3-lineBox").fadeIn();
					$(".slide3-lineBox").addClass('active');
				},2000);
			}
			if (swiper.activeIndex == 3) {
				setTimeout(function(){
					$(".slide4-cloudBox").fadeIn();
				},2000);
			}
			/*if (swiper.activeIndex == 4) {
				setTimeout(function(){
					$(".slide5-cloudBox").fadeIn()/*.children('img').addClass('active')*/;
			/*	},4000);
			}*/
			if (swiper.activeIndex == 5) {
				$(".slide6-wildgoose1").addClass('active');
				$(".slide6-wildgoose2").addClass('active');
			}
		}
	});
}
	</script>
</head>
<body>
	<div class="swiper-container">
	  <div class="swiper-wrapper">
	    <div class="swiper-slide slide1">
	    	<div class="slide1-container1">
		    	<img class="hot-balloon ani" src="${host}/mstatic/images/hot-balloon.png" alt="" swiper-animate-effect="slideInDown" swiper-animate-duration="0.5s" swiper-animate-delay="0.3s">
		    	<div class="airplaneBox">
		    		<img class="airplane ani" src="${host}/mstatic/images/airplane.png" alt="" swiper-animate-effect="slideInRight" swiper-animate-duration="0.5s" swiper-animate-delay="1.2s">
		    	</div>
		    	<div class="cloudBox">
		    		<img class="cloudRT ani" src="${host}/mstatic/images/cloudRT.png" alt=""  swiper-animate-effect="slideInRight" swiper-animate-duration="3s" swiper-animate-delay="1.5s">
		    	</div>
		    	<div class="cloudBox">
			    	<img class="cloudB  ani" src="${host}/mstatic/images/cloudB.png" alt=""   swiper-animate-effect="slideInRight" swiper-animate-duration="3s" swiper-animate-delay="1.8s">
			    </div>
			    <div class="cloudBox" >
			    	<img class="cloudLC ani" src="${host}/mstatic/images/cloudLC.png" alt=""  swiper-animate-effect="slideInLeft" swiper-animate-duration="3s" swiper-animate-delay="1.7s">
		    	</div>
	    	</div>
	    	<div class="slide1-container2">
		    	<img class="slide1dialog ani" src="${host}/mstatic/images/slide1dialog.png" alt="" swiper-animate-effect="bounceInLeft" swiper-animate-duration="0.3s" swiper-animate-delay="3s">
		    	<img class="slide1dialog2 ani" src="${host}/mstatic/images/slide1dialog2.png" alt="" swiper-animate-effect="bounceInLeft" swiper-animate-duration="0.3s" swiper-animate-delay="3.5s">
		    	<img class="slide1dialog3 ani" src="${host}/mstatic/images/slide1dialog3.png" alt="" swiper-animate-effect="bounceInLeft" swiper-animate-duration="0.3s" swiper-animate-delay="4s">
	    	</div>
		    <img class="dolphin" src="${host}/mstatic/images/dolphin.png" alt="">
	    </div>
	    <div class="swiper-slide slide2">
	    	<div class="slide2-container1">
	    		告诉朋友真旅行网站
		    	<!-- <img class="title2" src="${host}/mstatic/images/title2.png" alt=""> -->
	    	</div>
	    	<div class="slide2-container2">
		    	<img class="slide2dialog1 ani" src="${host}/mstatic/images/slide2dialog1.png" alt="" swiper-animate-effect="fadeIn" swiper-animate-duration="0.1s" swiper-animate-delay="3.2s">
		    	<img class="slide2dialog2 ani" src="${host}/mstatic/images/slide2dialog2.png" alt="" swiper-animate-effect="fadeIn" swiper-animate-duration="0.1s" swiper-animate-delay="2.2s">
		    	<div class="person-net">
		    		<img class="person-net1 ani" src="${host}/mstatic/images/person-net1.png" alt="" swiper-animate-effect="bounceInLeft" swiper-animate-duration="0.5s" swiper-animate-delay="1.3s">
		    		<img class="person-net2 ani" src="${host}/mstatic/images/person-net2.png" alt="" swiper-animate-effect="bounceInRight" swiper-animate-duration="0.5s" swiper-animate-delay="0.5s">
		    	</div>
		    	<div class="pig-box">
			    	<img class="pig ani" src="${host}/mstatic/images/pig.png" alt="" swiper-animate-effect="bounceInDown" swiper-animate-duration="0.8s" swiper-animate-delay="3.2s">
			    	<img class="star ani" src="${host}/mstatic/images/star.png" alt="" swiper-animate-effect="bounceInDown" swiper-animate-duration="0.8s" swiper-animate-delay="3.2s">
			    	<img class="purse1 ani" src="${host}/mstatic/images/purse1.png" alt="" swiper-animate-effect="bounceInDown" swiper-animate-duration="0.8s" swiper-animate-delay="3.2s">
			    	<img class="purse2 ani" src="${host}/mstatic/images/purse2.png" alt="" swiper-animate-effect="bounceInDown" swiper-animate-duration="0.8s" swiper-animate-delay="3.3s">
			    	<img class="purse3 ani" src="${host}/mstatic/images/purse3.png" alt="" swiper-animate-effect="bounceInDown" swiper-animate-duration="0.8s" swiper-animate-delay="3.3s">
			    	<img class="money1 ani" src="${host}/mstatic/images/money1.png" alt="" swiper-animate-effect="bounceInDown" swiper-animate-duration="1s" swiper-animate-delay="3.4s">
			    	<img class="money2 ani" src="${host}/mstatic/images/money2.png" alt="" swiper-animate-effect="bounceInDown" swiper-animate-duration="1s" swiper-animate-delay="3.4s">
			    	<img class="money3 ani" src="${host}/mstatic/images/money3.png" alt="" swiper-animate-effect="bounceInDown" swiper-animate-duration="1s" swiper-animate-delay="3.4s">
		    	</div>
	    	</div>
	    </div>
	    <div class="swiper-slide slide3">
	    	<div class="slide3-container1">
		    	注册填写推荐人手机号
	    	</div>
	    	<div class="slide3-container2">
			    <img class="slide3dialog1 ani" src="${host}/mstatic/images/slide3dialog1.png" alt="" swiper-animate-effect="fadeIn" swiper-animate-duration="0.3s" swiper-animate-delay="0.5s">
			    <img class="slide3person1" src="${host}/mstatic/images/slide3person1.png" alt="" swiper-animate-effect="bounceInLeft" swiper-animate-duration="0.5s" swiper-animate-delay="0.3s">
	    	</div>
	    	<div class="slide3-container3">
			    <img class="slide3-pc " src="${host}/mstatic/images/slide3-pc.png" alt="" swiper-animate-effect="bounceInLeft" swiper-animate-duration="0.3s" swiper-animate-delay="1s">
			    <div class="slide3-lineBox">
			    	<img class="slide3-line" src="${host}/mstatic/images/slide3-line.png" alt="">
			    </div>
			    <div class="slide3dialog2 ani" swiper-animate-effect="fadeIn" swiper-animate-duration="0.5s" swiper-animate-delay="2.5s">
			    	<img class="slide3-lamp" src="${host}/mstatic/images/slide3-lamp.png" alt="">新会员注册，此处填写老会员的手机号
			    </div>
	    	</div>
	    	<div class="slide3-container4">
			    <img class="slide3dialog3 ani" src="${host}/mstatic/images/slide3dialog3.png" alt="" swiper-animate-effect="fadeIn" swiper-animate-duration="0.1s" swiper-animate-delay="3.5s">
			    <img class="slide3person2 " src="${host}/mstatic/images/slide3person2.png" alt="" swiper-animate-effect="bounceInRight" swiper-animate-duration="0.5s" swiper-animate-delay="3s">
	    	</div>
	    </div>
	    <div class="swiper-slide slide4">
        	<div class="slide4-container1">
		    	Get√ 券到手<br>
			    <img class="slide4title2" src="${host}/mstatic/images/slide4title2.png" alt="">
	    	</div>
	    	<div class="slide4-container2">
			    <img class="slide4person1 ani" src="${host}/mstatic/images/slide4person1.png" alt="" swiper-animate-effect="bounceInRight" swiper-animate-duration="1s" swiper-animate-delay="0.5s">
			    <img class="slide4-coupon ani" src="${host}/mstatic/images/slide4-coupon.png" alt="" swiper-animate-effect="bounceInRight" swiper-animate-duration="1s" swiper-animate-delay="0.5s">
			    <div class="slide4-cloudBox">
			    	<img class="slide4-cloud1" src="${host}/mstatic/images/slide4-cloud1.png" alt="">
			    	<img class="slide4-cloud2" src="${host}/mstatic/images/slide4-cloud2.png" alt="">
			    	<img class="slide4-cloud3" src="${host}/mstatic/images/slide4-cloud3.png" alt="">
			    </div>
	    	</div>
	    	<div class="slide4-container3">
			    <img class="slide4-globle ani" src="${host}/mstatic/images/slide4-globle.png" alt="" swiper-animate-effect="bounceInLeft" swiper-animate-duration="1s" swiper-animate-delay="2s">
			    <img class="slide4-star1" src="${host}/mstatic/images/slide4-star1.png" alt="">
			    <img class="slide4-star2" src="${host}/mstatic/images/slide4-star2.png" alt="">
			    <img class="slide4-star3" src="${host}/mstatic/images/slide4-star3.png" alt="">
	    	</div>
	    </div>
        <div class="swiper-slide slide5">
        	<div class="slide5-container1">
		    	新会员成功注册并出游<br><img class="slide5title2" src="${host}/mstatic/images/slide5title2.png" alt="">
	    	</div>
	    	<div class="slide5-container2">
			    <img class="slide5-person1 ani" src="${host}/mstatic/images/slide5-person1.png" alt="" swiper-animate-effect="bounceInUp" swiper-animate-duration="0.5s" swiper-animate-delay="0.2s">
			    <img class="slide5money1 ani" src="${host}/mstatic/images/slide5money1.png" alt="" swiper-animate-effect="bounceInDown" swiper-animate-duration="0.5s" swiper-animate-delay="0.8s">
			    <img class="slide5money2 ani" src="${host}/mstatic/images/slide5money2.png" alt="" swiper-animate-effect="bounceInDown" swiper-animate-duration="0.5s" swiper-animate-delay="0.6s">
			    <img class="slide5money3 ani" src="${host}/mstatic/images/slide5money3.png" alt="" swiper-animate-effect="bounceInDown" swiper-animate-duration="0.5s" swiper-animate-delay="0.6s">
			    <img class="slide5money4 ani" src="${host}/mstatic/images/slide5money4.png" alt="" swiper-animate-effect="bounceInDown" swiper-animate-duration="0.5s" swiper-animate-delay="0.5s">
			    <img class="slide5money5 ani" src="${host}/mstatic/images/slide5money5.png" alt="" swiper-animate-effect="bounceInDown" swiper-animate-duration="0.5s" swiper-animate-delay="0.7s">
			    <img class="slide5goldcoin1 ani" src="${host}/mstatic/images/slide5goldcoin1.png" alt="" swiper-animate-effect="bounceInDown" swiper-animate-duration="0.5s" swiper-animate-delay="1s">
			    <img class="slide5goldcoin2 ani" src="${host}/mstatic/images/slide5goldcoin2.png" alt="" swiper-animate-effect="bounceInDown" swiper-animate-duration="0.5s" swiper-animate-delay="1.2s">
			    <img class="slide5goldcoin3 ani" src="${host}/mstatic/images/slide5goldcoin3.png" alt="" swiper-animate-effect="bounceInDown" swiper-animate-duration="0.5s" swiper-animate-delay="1s">
			    <img class="slide5-bubble ani" src="${host}/mstatic/images/slide5-bubble.png" alt="" swiper-animate-effect="fadeIn" swiper-animate-duration="0.5s" swiper-animate-delay="1.8s">
			    <img class="slide5-thinkimg ani" src="${host}/mstatic/images/slide5-thinkimg.png" alt="" swiper-animate-effect="fadeIn" swiper-animate-duration="0.5s" swiper-animate-delay="2.5s">
			    <!-- <div class="slide5-cloudBox"></div> -->
			    	<img class="slide5-cloud1 active" src="${host}/mstatic/images/slide5-cloud1.png">
				    <img class="slide5-cloud2 active" src="${host}/mstatic/images/slide5-cloud2.png">
				    <img class="slide5-cloud3 active" src="${host}/mstatic/images/slide5-cloud3.png" alt="">
	    	</div>
        </div>
        <div class="swiper-slide slide6">
        	<div class="slide6-container1">
			   	加入真旅行
	    	</div>
	    	<div class="slide6-container2">
			    <img class="slide6-wildgoose1" src="${host}/mstatic/images/slide6-wildgoose1.png" alt="">
			    <img class="slide6-wildgoose2" src="${host}/mstatic/images/slide6-wildgoose2.png" alt="">
			    <img class="slide6-cloud1" src="${host}/mstatic/images/slide6-cloud1.png" alt="">
			    <img class="slide6-cloud2" src="${host}/mstatic/images/slide6-cloud2.png" alt="">
			    <img class="slide6-cloud3" src="${host}/mstatic/images/slide6-cloud3.png" alt="">
		    	<span class="ani" swiper-animate-effect="fadeIn" swiper-animate-duration="0.5s" swiper-animate-delay="0.5s">我们感谢世界上每一位旅行分享家<br>因为你们，<br>地球上的每一个旅行故事<br>都历久弥新</span>
		    	<a href="javascript:void(0);" class="register ani" swiper-animate-effect="zoomIn" swiper-animate-duration="0.5s" swiper-animate-delay="1.8s" id="registNow">立即注册</a>
	    	</div>
        </div>
	  </div>
	 </div>
	<script type="text/javascript">
		var root = document.getElementsByTagName("html")[0],
			w = window.innerWidth >= 640 ? 640 : window.innerWidth,
			h = window.innerHeight >= 1136 ? 1136 : window.innerHeight,
			base_ratio = 640 / 1136,
			actual_ratio = window.innerWidth / window.innerHeight;

		if (actual_ratio > base_ratio) {  // 宽屏，按高度算

			root.style.fontSize = (h / 568) * 20 + "px";

		} else if (actual_ratio < base_ratio) {  //  长屏，按宽度算

			root.style.fontSize = (w / 320) * 20 + "px";
		}

		root.style.height = window.innerHeight + "px";


		$(function(){
		$("#registNow").click(function(){
		var wxServer =$("#wxServer").val();
		window.location.href= wxServer+"/rl/torl"
		})
	})
	</script>
<#include "/common/weixin/panguTracking.ftl"/>
</body>
</html>