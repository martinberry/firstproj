<!DOCTYPE html>
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
    	var eggLock = false ;
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

        var _hmt = _hmt || [];
		(function() {
		  var hm = document.createElement("script");
		  hm.src = "//hm.baidu.com/hm.js?92caacb90dbd758697054ab2f707834b";
		  var s = document.getElementsByTagName("script")[0];
		  s.parentNode.insertBefore(hm, s);
		})();

        // loading 加载动画（假的）
        function loadingBar() {
            document.getElementById("loadingInnerBar").className = "loaded";

             document.getElementById("loadingInnerBar").addEventListener("webkitTransitionEnd", function(){
                    document.getElementsByClassName("loading")[0].style.display = "none";
                     document.getElementsByClassName("gameDescribe")[0].style.display = "block";
              }, false);
        }

        // 全局参数
        var leafnum = 40,   //  出现总叶子数
            flag = false,   // 叶子出现标志
            clicknum = 0,   //点击次数计数
            num = 10,       //点击次数
            timecanvas_W,
            gameTimeEndFlag = false,
            startTouchFlag = false;   //  游戏时间结束表示

        // 重置游戏
        function resetGame() {

            leafnum = 40;
            flag = false;
            clicknum = 0;
            gameTimeEndFlag = false;
            startTouchFlag = false;   //  游戏时间结束表示

            $("#treebox").find(".time").html(0).hide();
            $("#treebox").find("#timecanvas").css("width", timecanvas_W + "px");
            $("#treebox").find(".timeprogress").hide();
            $("#treebox").find(".tree").removeClass().addClass("tree");
            $("#treebox").find(".leaf").removeClass().addClass("leaf").hide();

            document.getElementById("gamerule").style.display = "none";
            document.getElementById("success").style.display = "none";
            document.getElementById("success_noprize").style.display = "none";
            document.getElementById("success_takedprize").style.display = "none";
            document.getElementById("fail").style.display = "none";
            document.getElementById("tooManyPeople").style.display = "none";

            $("#gamerule").show();
            document.getElementById("treebox").style.display = "block";

            document.getElementById("water").style.display = 'none';

            document.getElementById("count-down-3s").style.display = "none";
            document.getElementById("cd-3").style.opacity = 0;
            document.getElementById("cd-3").className = "";
            document.getElementById("cd-2").style.opacity = 0;
            document.getElementById("cd-2").className = "";
            document.getElementById("cd-1").style.opacity = 0;
            document.getElementById("cd-1").className = "";
        }

        $(function(){
            var openId = $("#openId").val();
            if (openId == "") {
                /*发起微信授权请求*/
                var fromUrl = location.href;
                var url='https://open.weixin.qq.com/connect/oauth2/authorize?appid=${appId!''}&redirect_uri='+encodeURIComponent(fromUrl)+'&response_type=code&scope=snsapi_userinfo&state=happy2016#wechat_redirect';
                location.href=url;
            }

            window.onload = function(){

                $("body").delegate(".replay_btn", "click", function(){
                    resetGame();
                });

                /*游戏说明页 获奖人员名单*/
                var speed = 15;
                var liheight = 0;
                if (document.getElementsByTagName('li')[0] != undefined) {
                    liheight = document.getElementsByTagName('li')[0].offsetHeight;
                }
                var listtranslatey = 0;
                function scroll(){
                        if(listtranslatey <= (-(getprize_list.scrollHeight-getprize_list.offsetHeight-1))){
                            /*getprize_list.style.webkitTransform = 'translateY(0)';*/
                            listtranslatey = -10;
                        }else{
                            listtranslatey -- ;
                        }
                    getprize_list.style.webkitTransform  = 'translate3D(0,'+listtranslatey+'px,0)';
                }

                /*游戏说明页*/
                var getprize_list = document.getElementsByClassName('getprize_list')[0];
                var lis = document.getElementsByTagName('li');
                if(lis.length > 5) {
                    for(var i = 0;i < 5; i++){
                        var li = document.createElement('li');
                        li.innerHTML = lis[i].innerHTML;
                        getprize_list.appendChild(li);
                    }

                    var timer = setInterval(function(){
                        scroll();
                    },speed);
                }

                /*游戏*/
                /*开始给小树施肥按钮点击*/
                var startgame = document.getElementById('startgame');
                var gamerule = document.getElementById('gamerule');
                var timecanvascontainer = document.getElementById("timecanvascontainer");
                var timecanvas = document.getElementById("timecanvas");
                var timeprogress = document.getElementsByClassName('timeprogress')[0];
                var time = document.getElementsByClassName('time')[0];
                var success = document.getElementById('success');
                var fail = document.getElementById('fail');
                var countdown = document.getElementById("count-down-3s");
                var water = document.getElementById("water");

                // 倒计时变量
                var timerNumInterval;

                /*游戏中屏幕点击*/
                var treebox = document.getElementById('treebox');
                for(var i=0;i< 100;i++){
                    var spanleaf = document.createElement('span');
                    spanleaf.setAttribute('class','leaf');
                    treebox.appendChild(spanleaf);
                    spanleaf.style.display = "none";
                }

                var spanleafs = document.getElementsByClassName('leaf');
                var tree = document.getElementsByClassName('tree')[0];
                var body = document.getElementsByTagName('body')[0];
                var codeFinished = true;

                water.addEventListener('touchstart',function(){

                    startTouchFlag = true;

                    if (codeFinished) {

                        codeFinished = false;

                        if (clicknum < num) {

                            if (!gameTimeEndFlag) {

                                var treeclass = tree.className + ' transition big' + (clicknum + 1);
                                tree.setAttribute('class',treeclass);
                                clicknum++;
                            } else {

                                time.style.display = 'none';
                                timeprogress.style.display = 'none';
                                fail.style.display = 'block';
                            }
                        }else{

                            if(!gameTimeEndFlag){
                                if (time.innerHTML > 0 && time.style.display == 'none') {
                                    time.style.display = 'block';
                                }

                                // body.className = "";
                                // body.className = "shake";

                                spanleafs[clicknum - num].style.display = 'inline-block';
                                spanleafs[clicknum - num].className = 'leaf leaf' + (clicknum - ( num - 1 ));
                                time.innerHTML = clicknum - num + 1;

                                clicknum++;

                            } else if (gameTimeEndFlag && (clicknum - num + 1) >= leafnum) {

                                setTimeout(function(){
                                    timeprogress.style.display = 'none';
                                    success.style.display = 'block';
                                    shareTitle = "呦嚯！" + nickName + "手速" + (clicknum -10) + "，种出一颗圆蛋。来试试你的手速呗，种出圆蛋100%有奖！";
                                    shareData(shareTitle);
                                }, 1000);
                            } else if (gameTimeEndFlag && (clicknum - num + 1) < leafnum) {

                                time.style.display = 'none';
                                timeprogress.style.display = 'none';
                                fail.style.display = 'block';
                                shareTitle = "妈淡！" + nickName + "手都戳抖了连颗蛋都种不出来。看什么看，不服你来~";
                                shareData(shareTitle);
                            }
                        }
                        codeFinished = true;
                    }

                },false)

                var timeWidth;

                // 开始游戏逻辑
            startgame.addEventListener('click',function(){
                gamerule.style.display = 'none';
                timeprogress.style.display = 'block';
                countdown.style.display = 'block';

                var cd1 = document.getElementById("cd-1"),
                    cd2 = document.getElementById("cd-2"),
                    cd3 = document.getElementById("cd-3");

                setTimeout(function(){

                    timecanvas_W = timecanvas.clientWidth;
                    cd3.className = 'start';
                    water.style.display = 'block';

                    cd3.addEventListener("webkitAnimationEnd", function(){
                        cd2.className = "start";
                    }, false);

                    cd2.addEventListener("webkitAnimationEnd", function(){
                        cd1.className = "start";
                    }, false);

                    var timeWidthStep = timecanvas.clientWidth / 5;

                    cd1.addEventListener("webkitAnimationEnd", function(){

                        countdown.style.display = 'none';

                        setTimeout(function(){
                            gameTimeEndFlag = true;
                            if (!startTouchFlag || (clicknum - num + 1) < leafnum) {

                                time.style.display = 'none';
                                timeprogress.style.display = 'none';
                                fail.style.display = 'block';

                                shareTitle = "妈淡！" + nickName + "手都戳抖了连颗蛋都种不出来。看什么看，不服你来~";
                                shareData(shareTitle);

                            } else if (startTouchFlag && (clicknum - num + 1) >= leafnum) {

                                setTimeout(function(){
                                    timeprogress.style.display = 'none';
                                    success.style.display = 'block';

                                    shareTitle = "呦嚯！" + nickName + "手速" + (clicknum - 10) + "，种出一颗圆蛋。来试试你的手速呗，种出圆蛋100%有奖！";
                                    shareData(shareTitle);
                                }, 1000);
                            }
                        }, 5000);

                        if (timerNumInterval == undefined) {

                            timerNumInterval = setInterval(function(){

                                if (timecanvas.style.width == "") {
                                    timeWidth = timecanvas.clientWidth;
                                } else {
                                    timeWidth = parseFloat(timecanvas.style.width);
                                }
                                timecanvas.style.width = (timeWidth - timeWidthStep) + "px";

                                if (gameTimeEndFlag) {
                                    clearInterval(timerNumInterval);
                                    timerNumInterval = undefined;
                                }
                            }, 1000);
                        }

                    }, false);

                }, 10);
            },false);

                var egg = document.getElementById('egg');
                var success_noprize = document.getElementById('success_noprize');
                egg.addEventListener('click',function(){
                    setTimeout(function(){
                       // success.style.display = 'none';
                      //  success_noprize.style.display = 'block';

                        var params = {};
                        params.openId = $("#openId").val();
                        params.nickName = $("#nickName").val();
                        params.headImageUrl = $("#headImageUrl").val();
                        if(!eggLock){
                        	eggLock = true ;
	                        $.ajax({
	                            type : "POST",
	                            url : basepath + "/happy2016/achieveAward",
	                              headers : {
	                                  'Accept' : 'application/json',
	                                  'Content-Type' : 'application/json'
	                              },
	                            data :  JSON.stringify(params),
	                            dataType : "json",
	                            success : function(result) {
	                                if ( result.res_code == "AA_1601" ) {
	                                    $("#success").css('display','none');
	                                    $("#success_noprize").css('display','block');
	                                } else if ( result.res_code == "AA_1602" ) {
	                                    $("#success").css('display','none');
	                                    $("#success_takedprize").css('display','block');
	                                } else if ( result.res_code == "AA_1603" ) {
	                                    $("#success").css('display','none');
	                                    $("#fail").css('display','block');
	                                } else if ( result.res_code == "AA_1604" ) {
	                                    var data = JSON.parse(result.res_msg);
	                                    var url = basepath + "/happy2016/goToGameResult";
	                                    var $form = $('<form action="'+ url +'" method="post" style="display:none;"></form>');
	                                    $form.append('<input name="openId" value="'+ data.openId +'"/>');
	                                    $form.append('<input name="recordId" value="'+ data.recordId +'"/>');
	                                    $form.append('<input name="awardCode" value="'+ data.awardCode +'"/>');
	                                    $form.append('<input name="nickName" value="'+ nickName +'"/>');
                                        $form.append('<input name="clickNum" value="'+ (clicknum - 10) +'"/>');
	                                    $form.appendTo('body');
	                                    $form.submit();
	                                    //window.location.href = result.res_msg;
	                                } else {
	                                    $("#success").css('display','none');
	                                    $("#tooManyPeople").css('display','block');
	                                }
	                                eggLock = false ;
	                            },
	                            error:function(){
	                            	eggLock = false ;
	                            }
	                        });
                        }
                    },1000);
                },false);

                var playgamebtn = document.getElementsByClassName('playgamebtn')[0];
                var gameDescribe = document.getElementsByClassName('gameDescribe')[0];
                var game = document.getElementsByClassName('game')[0];
                playgamebtn.addEventListener('click',function(){
                    gameDescribe.style.display = 'none';
                    game.style.display = 'block';
                },false);

                var other_getprize_btn = document.getElementsByClassName('other_getprize_btn');
                var clickShare = document.getElementsByClassName('clickShare')[0];
                for(var i = 0;i < other_getprize_btn.length;i++){
                    other_getprize_btn[i].addEventListener('click',function(){
                        clickShare.style.display = 'block';
                    },false);
                }

                clickShare.addEventListener('click',function(){
                    clickShare.style.display = 'none';
                },false);

            }

        });
    </script>
</head>
<body class="redbg">
    <input type="hidden" id="openId" value="${(activityUser.openId)!''}">
    <input type="hidden" id="nickName" value="${(activityUser.nickName)!''}">
    <input type="hidden" id="headImageUrl" value="${(activityUser.headImageUrl)!''}">
    <div class="loading" style="display:block;">
        <img class="loadingpagebg" src="${host}/mstatic/images/activity/loadingpagebg.png" alt="" onload="loadingBar()"/>
        <div class="loadingprogress">
            <div id="loadingContainer"><span id="loadingInnerBar"></span></div>
            <img class="lodingtext" src="${host}/mstatic/images/activity/loading.png" alt=""/>
        </div>
    </div>
    <div class="gameDescribe" style="display:none;">
        <div class="game_describe" style="margin-top: 2rem;"></div>
        <div class="getprize_list_contain">
            <ul class="getprize_list">
            <#if records??>
                <#list records as record>
                <li>
                    <div class="user_getprize">
                        <img class="avater_user_getprize" src="${(record.headImageUrl)!}"><br/>
                        <span class="name_user_getprize">${(record.nickName)!}</span>
                    </div>
                    <#if (record.awardCode)?? && record.awardCode == 'COUPON1'>
                    <span class="prize_kinds_voucher50"></span>
                    <#elseif (record.awardCode)?? && record.awardCode == 'COUPON2'>
                    <span class="prize_kinds_voucher100"></span>
                    <#elseif (record.awardCode)?? && record.awardCode == 'COUPON3'>
                    <span class="prize_kinds_voucher800"></span>
                    <#elseif (record.awardCode)?? && record.awardCode == 'BAG'>
                    <span class="prize_kinds_bag"></span>
                    <#elseif (record.awardCode)?? && record.awardCode == 'CALENDARY'>
                    <span class="prize_kinds_calender"></span>
                    </#if>
                </li>
                </#list>
            </#if>
            </ul>
        </div>
        <div class="playgame">
            <a class="playgamebtn" href="javascript:void(0);"></a>
        </div>
    </div>
    <div class="game" style="display:none;">
        <div id="treebox" class="gamebg">
            <span class="time" style="display:none;">0</span>
            <div class="timeprogress" style="display:none;">
                <div id="timecanvasContainer">
                    <span id="timecanvas"></span>
                </div>
            </div>
            <div id="water"></div>
            <span class="tree"></span>
        </div>
        <div id="count-down-3s">
            <p id="cd-3">3</p>
            <p id="cd-2">2</p>
            <p id="cd-1">1</p>
        </div>
        <div id="gamerule" style="display:block;">
            <img class="rule" src="${host}/mstatic/images/activity/gamerule.png" alt=""/><br/>
            <a id="startgame" href="javascript:void(0);"></a>
        </div>
        <div id="success" style="display:none;">
            <div id="egg_container">
                <img id="egg" src="${host}/mstatic/images/activity/egg.png" alt=""/>
            </div>
            <span class="takeprize"></span>
        </div>
        <div id="success_noprize" style="display:none;">
            <span class="noprize_text"></span>
            <span class="noprize_qr"><img src="${host}/mstatic/images/activity/no_prizeqr.png" alt=""/></span>
        </div>
        <div id="success_takedprize" style="display:none;">
            <span class="takedprize_text"></span>
            <div class="getprizeBtn">
                <a class="replay_btn" href="javascript:void(0);"></a><br/>
                <a class="other_getprize_btn" href="javascript:void(0);"></a>
            </div>
        </div>
        <div id="fail" style="display:none;">
            <span class="fail_text"></span>
            <div class="getprizeBtn">
                <a class="replay_btn" href="javascript:void(0);"></a><br/>
                <a class="other_getprize_btn" href="javascript:void(0);"></a>
            </div>
        </div>
        <div id="tooManyPeople" style="display:none;">
            <span class="manypeople_text"></span>
            <div class="getprizeBtn">
                <a class="replay_btn" href="javascript:void(0);"></a><br/>
                <a class="other_getprize_btn" href="javascript:void(0);"></a>
            </div>
        </div>
        <div class="clickShare">
            <img src="${host}/mstatic/images/activity/shareImg.png" class="shareImg">
        </div>
    </div>

    <script type="text/javascript">

        if (location.href.indexOf('from=') != -1) {    //  从朋友圈点开的链接默认会加参数 “？”是分隔符
            window.location.href= basepath + "/happy2016/goToGame";
        }

        var shareTitle = "圆蛋养成计划——手快者得大奖！来试试你的手速呗，种出圆蛋100%有奖！";
        var nickName = $("#nickName").val();
        var link = "/happy2016/goToGame";
        var selfUrl = escape(location.href);
        var imgUrl = 'http://static.zhenlx.com/mstatic/images/activity/yuandan_thumb.jpg';

        var shareAppMessageData = {
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
         };

        var shareTimelineData = {
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
         }

         function shareData(shareTitle) {
             shareTimelineData.title = shareTitle;
             shareAppMessageData.title = shareTitle;
             wx.onMenuShareTimeline(shareTimelineData);
             wx.onMenuShareAppMessage(shareAppMessageData);
         }

        $(function(){

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