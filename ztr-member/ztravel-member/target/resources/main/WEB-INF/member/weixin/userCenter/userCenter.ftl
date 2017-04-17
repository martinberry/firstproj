<#import "/common/weixin/htmlIndex.ftl" as html />
<#import "/common/weixin/headerBar.ftl" as head/>

<@html.htmlIndex remoteJsFiles=["mstatic/js/common.js"] remoteCssFiles=["mstatic/css/userCenter.css"] localCssFiles=[]
                                  localJsFiles=["member/weixin/deleteTraveller.js"] title="真旅行">
<div class="viewport" data-role="page">
   <@head.headerBar title="个人中心"></@head.headerBar>
    <div class="content-box">
        <div class="account divBg">
            <div class="innerAccount">
                <img class="account-img" title="" alt="用户头像" src="${mediaserver}imageservice?mediaImageId=${(member.headImgId)!}"/>
                <p class="userName">${(member.nickName)!}</p>
            </div>
            <div class="clearFlt"></div>
        </div>
        <div class="ui-grid-a">
            <div class="ui-block-a"><a class="edt-info" onclick="window.location.href='${basepath}/usercenter/weixin/editMember'" data-transition="slidefade">编辑信息</a></div>
            <div class="ui-block-b"><a class="edt-info" onclick="window.location.href='${basepath}/usercenter/weixin/changePassword'" data-transition="slidefade">修改密码</a></div>
        </div>
    </div>
    <div class="some-traveler borderTop borderBottom">
            <p>常用旅客信息</p>
            <#if (member.travellers)??>
            <#list member.travellers as traveller>
            <div class="traveler-box noPaddingLF">
                <div class="traveler-info">
                    <img class="passengerUserImg" src="${host}/mstatic/images/passengerUser01.png" alt=""/>
                	<p class="trl-name"><#if (traveller.travelerNameCn)?length gt 8>${(traveller.travelerNameCn) ?substring(0,8)} ...<#else>${(traveller.travelerNameCn)!}</#if></p>
                	<p class="trl-name">${(traveller.mobile)!}</p>
                </div>
                <div class="trl-edt">
                	<a href="javascript:void(0);" onclick="jump2EditPage('${(traveller.id)!}')" data-transition="slidefade" class="ui-link"><img class="u-edt_modify" src="${host}/mstatic/images/u-edit.png"></a>
                    <a href="#alert-dialog" data-rel="popup" data-transition="pop" data-position-to="window" class="delTraveller" value="${(traveller.id)!}"><img class="u-edt_modify" src="${host}/mstatic/images/passengerUserDel.png"></a>
                </div>
            </div>
            </#list>
            </#if>
        </div>
        <!--点击删除按钮弹窗-->
        <div class="ui-content confirm-ui-content" data-role="popup" id="alert-dialog" data-history="false" data-theme="a" data-overlay-theme="b">
            <p class="dlg-cnt">删除常用旅客信息吗？</p>
            <div class="dlg-foot">
                <a class="btn-com btn-confirm" data-role="none" href="javascript:void(0);" data-rel="back" id="delTravellerBtn" value="">确 定</a><a class="btn-com btn-cancel" data-role="none" href="javascript:void(0);" data-rel="back">取 消</a>
            </div>
        </div>


    </div>
</div>
<div class="ui-content" data-role="popup" id="alert-dialog-usercenter" data-history="false" data-theme="a" data-overlay-theme="b"  data-transition="pop" data-position-to="window" data-dismissible="false">
        <p class="dlg-cnt" id="tipHintUserCenter"></p>
        <div class="dlg-foot">
            <a class="btn-confirm" data-role="none" href="javascript:void(0);" data-rel="back">好的</a>
      </div>
</div>
<script type="text/javascript">
    $(function(){
        window.onload = function() {
            setTimeout(function(){
                setTipMessage();
            }, 100);
        }

    });

    function setTipMessage() {
       if (${showTip?c}) {
            $("#tipHintUserCenter").html("亲~，快去“编辑”绑定手机号呗！小行等您哦~");
            $("#alert-dialog-usercenter").popup();
            $("#alert-dialog-usercenter").popup("open");
        }
    }
    
    function jump2EditPage(travellerId){
    	window.location.href= wxServer + '/usercenter/weixin/editTraveller/' + travellerId ;
    }

</script>
</@html.htmlIndex>