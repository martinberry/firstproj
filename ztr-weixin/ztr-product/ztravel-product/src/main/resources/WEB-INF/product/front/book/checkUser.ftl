    <!--提交订单前验证联系人信息-->
    <div class="modal fade" id="checkUser">
        <div class="modal-dialog" style="width:605px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">登录</h4>
                </div>
                <div class="modal-body clearfix">
                    <div class="chkUser-left">
                        <div style="margin: 20px 0px;">
                            <div class="componentInput">
                                <i class="commonIcon personHeaderIcon"></i>
                                <input type="text" id="userName" data-cv="required,userName" data-cp="right" placeholder="手机号/邮箱" style="width: 243px;">
                            </div>
                        </div>
                        <div style="margin: 20px 0px;">
                            <div class="componentInput forgetPW">
                                <i class="commonIcon lockIcon"></i>
                                <input type="password" id="passWord" data-cv="required"  data-cp="right" placeholder="请输入密码" style="width: 160px;">
                                <a href="javascript:void(0);" class="forgetPWFonts">忘记密码?</a>
                            </div>
                        </div>
                        <div style="margin-top: 20px;">
                            <a href="javascript:void(0);" class="commonBtn blueBtn" style="width:278px;">提交</a>
                        </div>
                    </div>
                    <div class="chkUser-right">
                        <div class="text-center" style="margin: 10px 0px;"><img src="${host}/images/client/changeUser.jpg" alt="换联系人" class="changeUser"/></div>
                        <div class="text-center">
                            <p>换个<span style="color: #11b9b7">联系人手机号？</span></p>
                        </div>
                    </div>
                    <div class="chkUser-center"></div>
                </div>
            </div>
        </div>

    </div>