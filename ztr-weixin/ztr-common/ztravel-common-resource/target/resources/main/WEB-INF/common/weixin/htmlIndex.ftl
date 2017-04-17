<#macro htmlIndex remoteJsFiles=[] remoteCssFiles=[] localCssFiles=[] localJsFiles=[] title="">
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no, email=no">
    <meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
    <title>${title!'真旅行'}</title>

    <link rel="stylesheet" type="text/css" href="${host}/mstatic/css/jqm/jquery.mobile-1.4.5.min.css">
    <link rel="stylesheet" type="text/css" href="${host}/mstatic/css/jqm.reset.css">
    <link rel="stylesheet" type="text/css" href="${host}/mstatic/css/common.css">
    <#list remoteCssFiles as css>
		<link rel="stylesheet" type="text/css" href="${host}/${css}"/>
	</#list>

	<#list localCssFiles as localCss>
		<link rel="stylesheet" type="text/css" href="${basepath}/resources/css/${localCss}"/>
	</#list>
	<script type="text/javascript" src="${host}/mstatic/js/base/jquery.min.js${version}"></script>
	<script type="text/javascript" src="${host}/mstatic/js/jqm/jquery.mobile-1.4.5.min.js${version}"></script>
	<script type="text/javascript" src="${host}/mstatic/js/iscroll/iscroll.js${version}"></script>
	<script type="text/javascript" src="${host}/mstatic/js/base/fastclick.js${version}"></script>
	<script type="text/javascript" src="${host}/mstatic/js/common.js${version}"></script>
<#list remoteJsFiles as js>
	<script type="text/javascript" src="${host}/${js}${version}"></script>
</#list>

<#list localJsFiles as js>
	<script type="text/javascript" src="${basepath}/resources/javascripts/${js}${version}"></script>
</#list>
<script type="text/javascript" src="${basepath}/resources/javascripts/common/ajax_ext.js${version}"></script>
<script>
var _hmt = _hmt || [];
(function() {
  var hm = document.createElement("script");
  hm.src = "//hm.baidu.com/hm.js?92caacb90dbd758697054ab2f707834b";
  var s = document.getElementsByTagName("script")[0]; 
  s.parentNode.insertBefore(hm, s);
})();
</script>
<script type="text/javascript">
    $(function(){
        var root;
        var _w;
        var w;
        screenchange();
        window.addEventListener('orientationchange', function(event){
            /*旋转至竖屏*/
           if ( (window.orientation == 180 || window.orientation==0)&&(sessionStorage.s_fontsize==undefined)) {/*初始横屏加载切换至竖屏*/
                _w = window.innerWidth <= window.screen.width ? window.innerWidth : window.screen.width;
                w = _w >= 640 ? 640 : _w;
                root.style.fontSize = (w / 320) * 20 + "px";
                sessionStorage.s_fontsize = root.style.fontSize;
            }
            root.style.minHeight = window.innerHeight + "px";
        });

        function screenchange(){
            root = document.getElementsByTagName("html")[0];
            if(window.orientation==undefined){
                _w = window.innerWidth <= window.screen.width ? window.innerWidth : window.screen.width;
                w = _w >= 640 ? 640 : _w;
                root.style.fontSize = (w / 320) * 20 + "px";
            }else{
                if ( window.orientation == 180 || window.orientation==0 ) {  /*竖屏加载*/
                    _w = window.innerWidth <= window.screen.width ? window.innerWidth : window.screen.width;
                    w = _w >= 640 ? 640 : _w;

                    root.style.fontSize = (w / 320) * 20 + "px";
                    sessionStorage.s_fontsize = root.style.fontSize;
                }
                
                if( window.orientation == 90 || window.orientation == -90 ) {  /*横屏加载*/ 
                    if(sessionStorage.s_fontsize==undefined){   /*初始横屏加载*/
                        var w1 = window.innerHeight<= window.screen.height ? window.innerHeight : window.screen.height;
                        var w2 = w1 >= 640 ? 640 : w1;

                        root.style.fontSize = (w2 / 320) * 20 + "px";

                    }else{
                        root.style.fontSize = sessionStorage.s_fontsize;
                    }
                }
            }
            root.style.minHeight = window.innerHeight + "px";
            $(".viewport").eq(0).css("minHeight", window.innerHeight + "px");
        }
		FastClick.attach(document.body);
    });
    var basepath = '${basepath}';
    var mediaServer = '${mediaserver}';
    var wxServer = '${wxServer}' ;
    var ssoServer = '${ssoServer}';

    function popNotActive(){
		alert("咦？账号异常，请与客服联系~") ;
	};

	function popNotLogin(){
		alert("请重新登录，系统将在３秒后跳回主页") ;
		setTimeout("relogin();",3000);
	};
	function relogin(){
		window.location.href = wxServer + "/rl/torl" ;
	}
</script>
</head>
<body>

    <#nested/>

<#include "/common/weixin/panguTracking.ftl"/>
</body>
</html>
</#macro>