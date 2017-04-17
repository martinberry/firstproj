<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>圆蛋养成计划</title>
    <link rel="stylesheet" href="${host}/mstatic/css/demotree.css"/>

    <script src="${host}/mstatic/js/base/jquery.min.js"></script>
    <script src="${host}/mstatic/js/base/fastclick.js"></script>
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>

    <script type="text/javascript">
          var basepath = '${wxServer}';
          if(basepath.substr(basepath.length-1,basepath.length) == '/'){
            basepath = basepath.substr(0,basepath.length-1) ;
        }
          var mobileReg= /^(?:13\d|14\d|15\d|17\d|18\d)\d{8}$/ ;
        var root = document.getElementsByTagName("html")[0],
        w = window.innerWidth >= 750 ? 750 : window.innerWidth,
        h = window.innerHeight >= 1334 ? 1334 : window.innerHeight,
        base_ratio = 750 / 1334,
        actual_ratio = window.innerWidth / window.innerHeight;

        if (actual_ratio >= base_ratio) {  // 宽屏，按高度算
            root.style.fontSize = (h / 667) * 20 + "px";
        } else if (actual_ratio < base_ratio) {  //  长屏，按宽度算
            root.style.fontSize = (w / 375) * 20 + "px";
        }

        root.style.height = window.innerHeight + "px";
        
        var _hmt = _hmt || [];
		(function() {
		  var hm = document.createElement("script");
		  hm.src = "//hm.baidu.com/hm.js?92caacb90dbd758697054ab2f707834b";
		  var s = document.getElementsByTagName("script")[0]; 
		  s.parentNode.insertBefore(hm, s);
		})();

        window.onload = function(){

            FastClick.attach(document.body);

            var getprize_btn_0 = document.getElementsByClassName('getprize_btn')[0];
            var getprize_btn_1 = document.getElementsByClassName('getprize_btn')[1];
            var popwindow = document.getElementById('getpricePopWindow');


            getprize_btn_0.addEventListener('click',function(){
                popwindow.style.display = 'block';
            document.getElementById("address").value="";
            document.getElementById("name").value="";
            document.getElementById("mobile").value="";
             	 submitGuestInfo();

            },false);

            getprize_btn_1.addEventListener('click',function(){
                popwindow.style.display = 'block';
           document.getElementById("address").value="";
            document.getElementById("name").value="";
            document.getElementById("mobile").value="";
                   submitGuestInfo();
            },false);

            var submitebtn = document.getElementsByClassName('submitebtn')[0];
            submitebtn.addEventListener('click',function(){
                popwindow.style.display = 'none';
            },false);

            var blackcover = document.getElementById('blackcover');
            blackcover.addEventListener('click',function(){
                popwindow.style.display = 'none';
            },false);

            var closebtn = document.getElementsByClassName('closebtn')[0];
            closebtn.addEventListener('click',function(){
                popwindow.style.display = 'none';
            },false);

            var other_getprize_btn = document.getElementsByClassName('other_getprize_btn');
            var clickShare = document.getElementsByClassName('clickShare')[0];
            var redbg = document.getElementsByClassName('redbg')[0];
            for(var i = 0;i < other_getprize_btn.length;i++){
                other_getprize_btn[i].addEventListener('click',function(){
                    clickShare.style.display = 'block';
                },false);
            }

            clickShare.addEventListener('click',function(){
                clickShare.style.display = 'none';
            },false);

            var awardCode = $("#awardCode").val();
            if (awardCode == '') {
            } else if (awardCode == 'COUPON1' || awardCode == 'COUPON2' || awardCode == 'COUPON3') {
                var voucherCode = $("#recordId").val();
                $("#voucherCode").html(voucherCode);
                $("#getPrizeVoucher").css('display','block');
            } else if (awardCode == 'BAG') {
               $("#getPrizeBag").css('display','block');
            } else if (awardCode == 'CALENDARY') {
                $("#getPrizeCalendar").css('display','block');
            }
        }



    function submitGuestInfo(){
     var submitInfo=document.getElementsByClassName('submitebtn')[0];
            submitInfo.addEventListener('click',function(){
             var detailAddress=document.getElementById("address").value;
       		 var realName=document.getElementById("name").value;
       		 var mobile=document.getElementById("mobile").value;
       		  var popwindow = document.getElementById('getpricePopWindow');
       		if(detailAddress!=""&&realName!=""&&mobileReg.test(mobile)){
       		var activityUserVo={};
       		activityUserVo.detailAddress=document.getElementById("address").value;
       		activityUserVo.realName=document.getElementById("name").value;
       		activityUserVo.mobile=document.getElementById("mobile").value;
       		activityUserVo.openId=document.getElementById("openId").value;
       		activityUserVo.recordId=document.getElementById("recordId").value;

       $.ajax({
       type:"POST",
       url:basepath+"/happy2016/saveGuestinfo",
       data : JSON.stringify(activityUserVo),
       headers : {
       	'Accept' : 'application/json',
       	'Content-Type' : 'application/json'
       },
       dataType : "json",
       success : function(data) {
       	if(data.res_code=="savesuccess"){
       	popwindow.style.display="none";
       	}
       	else if(data.res_code=="outtime"){
            window.location.href= basepath + "/happy2016/goToGame";
       }
       	else{
       	 window.location.href= basepath + "/happy2016/error";
       	}
       }
         });
       		}
       		else{
       			popwindow.style.display="block";
       		}
                },false);
        }
    </script>
</head>
<body class="redbg">
    <input type="hidden" id="openId" value="${(activityUserVo.openId)!''}">
    <input type="hidden" id="recordId" value="${(activityUserVo.recordId)!''}">
    <input type="hidden" id="awardCode" value="${(activityUserVo.awardCode)!''}">
    <input type="hidden" id="nickName" value="${(activityUserVo.nickName)!''}">
    <div id="getPrizeVoucher" style="display: none;">
        <span class="voucher_title"></span>
        <div class="exchange">
            <#if (activityUserVo.awardCode)?? && activityUserVo.awardCode == 'COUPON1'>
            <img class="voucher" src="${host}/mstatic/images/activity/voucher50.png" alt=""/><br/>
            <#elseif (activityUserVo.awardCode)?? && activityUserVo.awardCode == 'COUPON2'>
            <img class="voucher" src="${host}/mstatic/images/activity/voucher100.png" alt=""/><br/>
            <#elseif (activityUserVo.awardCode)?? && activityUserVo.awardCode == 'COUPON3'>
            <img class="voucher" src="${host}/mstatic/images/activity/voucher800.png" alt=""/><br/>
            </#if>
            <span class="exchange_code" id="voucherCode"></span>
        </div>
        <span class="exchange_method"></span><br/>
        <a class="other_getprize_btn" href="javascript:void(0);"></a>
    </div>
    <div id="getPrizeBag" style="display:none;">
        <img class="prize_bag" src="${host}/mstatic/images/activity/prize_bag.png" alt=""/>
        <div class="getprizeBtn">
            <a class="getprize_btn" href="javascript:void(0);"></a><br/>
            <a class="other_getprize_btn" href="javascript:void(0);"></a>
        </div>
    </div>
    <div id="getPrizeCalendar" style="display:none;">
        <img class="prize_calendar" src="${host}/mstatic/images/activity/prize_calender.png" alt=""/>
        <div class="getprizeBtn">
            <a class="getprize_btn" href="javascript:void(0);"></a><br/>
            <a class="other_getprize_btn" href="javascript:void(0);"></a>
        </div>
    </div>
    <div class="clickShare">
        <img src="${host}/mstatic/images/activity/shareImg.png" class="shareImg">
    </div>
    <div id="getpricePopWindow">
        <div id="blackcover"></div>
        <div class="getpricecontent">
            <div class="greybg">
                <img class="getprize_qr" src="${host}/mstatic/images/activity/getprize_qr.png" alt=""/>
                <div class="message">
                    <form class="message_form" action="">
                         <input id="address" type="text"/>
                        <input id="name" type="text"/>
                        <input id="mobile" type="text"/>
                    </form>
                    <a class="submitebtn" href="javascript:void(0);"></a>
                </div>
                <a class="closebtn" href="javascript:void(0);"></a>
            </div>

        </div>
    </div>
        <script type="text/javascript">

       if (location.href.indexOf('from=') != -1){    //  从朋友圈点开的链接默认会加参数 “？”是分隔符
            window.location.href= basepath + "/happy2016/goToGameResult";
        }

         var nickName = $("#nickName").val();
         var shareTitle = "呦嚯！" + nickName + "手速${activityUserVo.clickNum!''}，种出一颗圆蛋。来试试你的手速呗，种出圆蛋100%有奖！";

        $(function(){

            var link = "/happy2016/goToGame";
            var selfUrl = escape(location.href);
            var imgUrl = 'http://static.zhenlx.com/mstatic/images/activity/yuandan_thumb.jpg';

            $.ajax({
               type: "GET",
               dataType: 'jsonp',
               jsonp: 'callback',
               url: basepath + "/weixinShareServlet?url="+selfUrl,
               success: function(data){
                 wx.config({
                     debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                     appId: data.appId, // 必填，公众号的唯一标识
                     timestamp: data.timestamp, // 必填，生成签名的时间戳
                     nonceStr: data.nonceStr, // 必填，生成签名的随机串
                     signature: data.signature,// 必填，签名，见附录1
                     jsApiList: ['onMenuShareTimeline','onMenuShareAppMessage'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
                 });
               }
            });

            wx.ready(function(){
                wx.onMenuShareTimeline({
                    title:shareTitle, // 分享标题
                    link: basepath + link, // 分享链接
                    imgUrl: imgUrl, // 分享图标
                    success: function () {
                        // 用户确认分享后执行的回调函数
                        _paq.push(['trackEvent', 'weixinShare', '2016yuandan_game', 'timeLine']);
                    },
                    cancel: function () {
                        // 用户取消分享后执行的回调函数
                        _paq.push(['trackEvent', 'weixinShareCancel', '2016yuandan_game', 'timeLine']);
                    }
                });

                wx.onMenuShareAppMessage({
                    title: shareTitle, // 分享标题
                    desc: '圆！蛋！快！乐！', // 分享描述
                    link: basepath + link, // 分享链接
                    imgUrl: imgUrl, // 分享图标
                    success: function () {
                        // 用户确认分享后执行的回调函数
                         _paq.push(['trackEvent', 'weixinShare', '2016yuandan_game', 'appMessage']);
                    },
                    cancel: function () {
                        // 用户取消分享后执行的回调函数
                        _paq.push(['trackEvent', 'weixinShareCancel', '2016yuandan_game', 'appMessage']);
                    }
                });
            });
        });
    </script>
</body>
</html>