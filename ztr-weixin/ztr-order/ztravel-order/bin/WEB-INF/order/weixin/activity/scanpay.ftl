
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no, email=no">
    <link rel="stylesheet" type="text/css" href="../../mstatic/css/jqm/jquery.mobile-1.4.5.min.css">
    <!-- <link rel="stylesheet" type="text/css" href="../../mstatic/css/jqm/jquery.mobile.theme-1.4.5.css"> -->
    <link rel="stylesheet" type="text/css" href="../../mstatic/css/jqm.reset.css">
    <link rel="stylesheet" type="text/css" href="../../mstatic/css/common.css">
    <link rel="stylesheet" type="text/css" href="../../mstatic/css/payment.css">
    <script type="text/javascript" src="../../mstatic/js/base/jquery.min.js"></script>
    <script type="text/javascript" src="../../mstatic/js/jqm/jquery.mobile-1.4.5.min.js"></script>
    <script type="text/javascript" src="../../mstatic/js/iscroll/iscroll.js"></script>
    <script type="text/javascript" src="../../mstatic/js/base/fastclick.js"></script>
    <title>扫码支付</title>

    <script type="text/javascript">
        var root = document.getElementsByTagName("html")[0],
                w = window.innerWidth >= 640 ? 640 : window.innerWidth;

        root.style.fontSize = (w / 320) * 20 + "px";
        root.style.minHeight = window.innerHeight + "px";
    </script>
</head>
<body>
<div class="viewport" data-role="page">
    <div class="headerBar">
        <div class="header">
            <span class="back">
                <i class="back-arrow"></i>
                <em class="back-border"></em>
            </span>
            <span class="title">扫码支付</span>
        </div>
    </div>
    <div class="QRCode">
        <img class="QRCode-img" src="../../mstatic/images/alipay.jpg">
    </div>
    <div class="QRCode-box">
        <div class="QRCode-block">
            <p class="QRCode-tip">友情提示：请在30分钟内完成支付</p>
            <p class="QRCode-guide">支付宝二维码支付方法</p>
            <p class="QRCode-txt">1.保存此二维码到手机相册</p>
            <p class="QRCode-txt">2.打开支付宝客户端扫描此二维码</p>
        </div>
    </div>
    <div class="QR-href">
        <a class="QR-link" href="javascript:void(0);">保存二维码到手机相册</a>
    </div>
</div>

<script>
    $(function () {
        FastClick.attach(document.body);

    });
</script>
</body>
</html>