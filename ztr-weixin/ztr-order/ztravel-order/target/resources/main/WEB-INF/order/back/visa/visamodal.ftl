    <!--取消订单对话框-->
    <div class="modal fade commonInitialize" id="cancelOrderDlg">
        <div class="modal-dialog" style="width: 500px;height:185px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">操作确认</h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <i class="warnIcon"></i>
                        <span class="popupContainer-fonts">是否取消当前订单？</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button" data-dismiss="modal" id="cancelOrderBtn">确 认</button>
                    <button type="button" class="commonButton blue-45c8dcButton" data-dismiss="modal">取 消</button>
                </div>
            </div>
        </div>
    </div>




    <!--OP确认对话框-->
    <div class="modal fade commonInitialize" id="opConfirmDlg">
        <div class="modal-dialog" style="width: 500px;height:185px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">操作确认</h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <i class="warnIcon"></i>
                        <span class="popupContainer-fonts">是否对当前订单进行OP确认操作？</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button" data-dismiss="modal" id="opConfirmBtn">确 认</button>
                    <button type="button" class="commonButton blue-45c8dcButton" data-dismiss="modal">取 消</button>
                </div>
            </div>
        </div>
    </div>
    
    
        <!--制作完成对话框-->
    <div class="modal fade commonInitialize" id="makedOrderDlg">
        <div class="modal-dialog" style="width: 500px;height:185px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">操作确认</h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <i class="warnIcon"></i>
                        <span class="popupContainer-fonts">是否制作完成？</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button" data-dismiss="modal" id="makedOrderBtn">确 认</button>
                    <button type="button" class="commonButton blue-45c8dcButton" data-dismiss="modal">取 消</button>
                </div>
            </div>
        </div>
    </div>
    
    
     <!--材料送回对话框-->
    <div class="modal fade commonInitialize" id="materialSendDlg">
        <div class="modal-dialog" style="width: 500px;height:185px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">材料送回联系人信息</h4>
                </div>
                <div class="modal-body">
                     <div class="contactorName">
                    	<p><span>请确认用户姓名</span><input type="text" id="contactorname" /><span class="nameTips" style="display:none;">姓名填写错误</span></p>			
                    </div>
                    
                     <div class="contactorPhone">
                    	<p><span>请确认用户手机</span><input type="text" id="contactorphone" /><span class="phoneTips" style="display:none;">手机号码填写错误</span></p>			
                    </div>
                    
                    <div class="contactorEmail">
                    	<p><span>请确认用户邮箱</span><input type="text" id="contactoremail" /><span class="emailTips" style="display:none;">邮箱填写错误</span></p>			
                    </div>
                    
                    <div class="contactorAddress">
                    	<p><span>请确认用户地址</span><input type="text" id="contactoraddress" /><span class="addressTips" style="display:none;">地址填写错误</span></p>			
                    </div>
                    
                    <textarea style="width:470px;height:140px;" class="message"></textarea><span class="messageTips" style="display:none;">信息内容填写错误</span>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button"  id="materialsendBtn">确 认</button>
                    <button type="button" class="commonButton blue-45c8dcButton" data-dismiss="modal">取 消</button>
                </div>
            </div>
        </div>
    </div>
    
    
