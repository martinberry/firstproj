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
    <!--发放礼盒对话框-->
    <div class="modal fade commonInitialize" id="sendGiftDlg">
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
                        <span class="popupContainer-fonts">是否对当前订单进行发放礼盒操作？</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button" data-dismiss="modal" id="sendGiftBtn">确 认</button>
                    <button type="button" class="commonButton blue-45c8dcButton" data-dismiss="modal">取 消</button>
                </div>
            </div>
        </div>
    </div>
    <!--发送出行通知对话框-->
    <div class="modal fade commonInitialize" id="sendOutingNoticeDlg">
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
                        <span class="popupContainer-fonts">是否对当前订单进行发送出行通知操作？</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button" data-dismiss="modal" id="sendOutingNoticeBtn">确 认</button>
                    <button type="button" class="commonButton blue-45c8dcButton" data-dismiss="modal">取 消</button>
                </div>
            </div>
        </div>
    </div>

    <!----------订单操作成功提示---------->
    <div class="modal fade commonInitialize" id="ac-OPSure">
        <div class="modal-dialog" style="width: 500px;height:185px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">操作提示</h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <i class="passIcon"></i>
                        <span class="popupContainer-fonts">该订单状态已变更为［OP确认，待发放礼盒］！</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button" data-dismiss="modal">确 认</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade commonInitialize" id="ac-distributionBox">
        <div class="modal-dialog" style="width: 500px;height:185px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">操作提示</h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <i class="passIcon"></i>
                        <span class="popupContainer-fonts">该订单状态已变更为［发放礼盒，待出行通知］！</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button" data-dismiss="modal">确 认</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade commonInitialize" id="ac-travelToInform">
        <div class="modal-dialog" style="width: 500px;height:185px;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">操作提示</h4>
                </div>
                <div class="modal-body">
                    <div class="popupContainer">
                        <i class="passIcon"></i>
                        <span class="popupContainer-fonts">该订单状态已变更为［已完成］！</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="commonButton red-fe6869Button" data-dismiss="modal">确 认</button>
                </div>
            </div>
        </div>
    </div>