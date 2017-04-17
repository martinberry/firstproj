
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>圆蛋养成计划</title>
    <link rel="stylesheet" href="${host}/mstatic/css/demotree.css"/>

    <script src="${host}/mstatic/js/base/jquery.min.js"></script>
    <script src="${host}/mstatic/js/base/fastclick.js"></script>


    <script type="text/javascript">

        var basepath = '${wxServer}';

        if(basepath.substr(basepath.length-1,basepath.length) == '/'){
            basepath = basepath.substr(0,basepath.length-1) ;
        }

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
    </script>
</head>
<body style="background-color: #e6d6bd;">
    <div class="game">
        <div id="tooManyPeople">
            <span class="manypeople_text"></span>
            <div class="getprizeBtn">
                <a class="replay_btn" href="${wxServer}/happy2016/goToGame"></a><br/>
                <a class="other_getprize_btn" id="other_getprize_btn" href="javascript:void(0);"></a>
            </div>
        </div>
        <div class="clickShare" id="clickShare">
            <img src="${host}/mstatic/images/activity/shareImg.png" class="shareImg">
        </div>
    </div>

    <script type="text/javascript">

        var btn = document.getElementById("other_getprize_btn"),
            clickShare = document.getElementById("clickShare");

        btn.addEventListener('click',function(){
            clickShare.style.display = 'block';
        },false);

        clickShare.addEventListener('click',function(){
            clickShare.style.display = 'none';
        },false);
    </script>

    <script type="text/javascript">

        if (location.href.indexOf('from=') != -1) {    //  从朋友圈点开的链接默认会加参数 “？”是分隔符
            window.location.href= basepath + "/happy2016/error";
        }

         var shareTitle = "圆蛋养成计划——手快者得大奖！来试试你的手速呗，种出圆蛋100%有奖！";
         var nickName = $("#nickName").val();

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

    <script type="text/javascript">
        var _paq = _paq || [];
        (function(){ var u=(("https:" == document.location.protocol) ? "https://" : "http://");
            _paq.push(['setSiteId', 4]);
            _paq.push(['setTrackerUrl', u+'pangu.tdxinfo.com/tracker.do']);
            _paq.push(['setCookieDomain', '*.zhenlx.com']);
            _paq.push(['setDomains', '*.zhenlx.com']);
            var account = '';
            if(account)_paq.push(['setUserId', account]);
            _paq.push(['trackPageView']);
            _paq.push(['enableLinkTracking']);
            var d=document, g=d.createElement('script'), s=d.getElementsByTagName('script')[0];
            g.type='text/javascript';
            g.defer=true;
            g.async=true;
            g.src=u+'scpg.tdxinfo.com/js/pangumini.js?v=1.0.6';
            s.parentNode.insertBefore(g,s); })();
    </script>

</body>
</html>