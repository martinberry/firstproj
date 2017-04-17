<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>真旅行推荐人奖励计划</title>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" type="text/css" href="${host}/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/bootstrap.reset.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/bootstrap-datepicker.min.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/common.css">
    <link rel="stylesheet" type="text/css" href="${host}/css/client/common.css">
    <link rel="stylesheet" href="${host}/css/client/refereeReward.css">

    <script type="text/javascript" src="${host}/js/base/jquery-1.11.2.min.js"></script>
    <script type="text/javascript" src="${host}/js/bootdist/bootstrap.min.js"></script>
    <script type="text/javascript" src="${host}/js/vendor/bootstrap-datepicker.min.js"></script>
    <script type="text/javascript" src="${host}/js/vendor/bootstrap-datepicker.zh-CN.min.js"></script>
    <script type="text/javascript" src="${host}/js/client/common.js"></script>
    <script type="text/javascript" src="${host}/js/client/adjust.js"></script>

    <!--[if lte IE 9]>
    <script type="text/javascript" src="${host}/js/base/html5.js"></script>
    <![endif]-->
</head>
<body>

<header>
    <div class="head clearfix">
    	<a href="${memberServer}">
        	<div class="logoImg"></div>
        </a>
	</div>
</header>

<div class="main-contain">
    <div class="reward-banner">
    </div>
    <div class="reward-contain">
        <p class="reward-contain-th">为了奖励旅行分享家，真旅行设置“真旅分享计划”</p>
        <div class="reward-detail clearfix">
            <div class="reward-detail-left"><i class="reward-icon01"></i><span>新注册用户只要填写老会员手机号,<br>即可获得100元代金礼券</span></div>
            <div class="reward-detail-right"><i class="reward-icon02"></i><span>同时老会员也会获得百元话费奖励<br>待新用户成功出行，即可兑换</span></div>
        </div>
        <div class="reward-box">
            <p class="reward-box-th">这里有一群温暖的人</p>
            <div class="reward-box-cnt clearfix">
                <i class="left-quo"></i>
                <i class="right-quo"></i>
                <div class="reward-box-txt">
                    <p>我们感谢世界上每一位旅行分享家</p>
                    <p>因为你们，地球上的每一个旅行故事都历久弥新</p>
                    <p>有你们的世界更精彩</p>
                </div>
            </div>
            <p class="reward-name">一 真旅行 一</p>
            <h1>推荐流程</h1>
            <div class="flowsheet clearfix">
                <div class="flowsheet-left">
                    <div class="flowsheet-title clearfix" style="padding-top:20px;">
                        <div class="flowsheet-icon"><i class="num-seq01"></i></div>
                        <div class="flowsheet-txt">向朋友介绍真旅行网站</div>
                    </div>
                    <p class="flowsheet-img flowsheet-img01"></p>
                </div>
                <div class="flowsheet-right">
                    <div class="flowsheet-title clearfix">
                        <div class="flowsheet-icon" style="padding-top:20px;"><i class="num-seq02"></i></div>
                        <div class="flowsheet-txt">请朋友在注册真旅行账号时<br>在推荐人栏填写您的手机号</div>
                    </div>
                    <p class="flowsheet-img flowsheet-img02"></p>
                </div>
            </div>
            <div class="flowsheet clearfix" style="padding-bottom: 40px;border-bottom:1px #afafaf dashed;">
                <div class="flowsheet-left">
                    <div class="flowsheet-title clearfix">
                        <div class="flowsheet-icon" style="padding-top:20px;"><i class="num-seq03"></i></div>
                        <div class="flowsheet-txt">朋友注册成功即可获得真旅行代金<br>券,同时您自己获得百元话费奖励</div>
                    </div>
                    <p class="flowsheet-img flowsheet-img03"></p>
                </div>
                <div class="flowsheet-right">
                    <div class="flowsheet-title clearfix">
                        <div class="flowsheet-icon" style="padding-top:20px;"><i class="num-seq04"></i></div>
                        <div class="flowsheet-txt">朋友购买真旅行产品成功出游<br>您获得的百元话费奖励即可兑换</div>
                    </div>
                    <p class="flowsheet-img flowsheet-img04"></p>
                </div>
            </div>
            <p class="reward-page">这是一个朋友间的承诺，你告诉我一个故事，我报之以独家回忆。</p>
        </div>
    </div>
    <#include "/common/front/right_side.ftl"/>
   	<#include "/common/front/header/footer.ftl"/>
</div>
    </div>
</div>

</body>
<#include "/common/front/panguTracking.ftl"/>
</html>