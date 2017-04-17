<div class="rightSide">
    <div class="feedback">
      <span class="feedbackbtn"></span>
      <div class="modal fade" id="ac-feedbackHintWindow">
        <div class="modal-dialog " style="width: 604px;height:346px;">
          <div class="modal-content">
          	<form action="" method="" id="feedback_form">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">意见反馈</h4>
            </div>
            <div class="modal-body">
                <textarea name="content" class="suggest" placeholder="请在此留下您的珍贵意见，产品经理一定字斟句酌，努力改善" maxlength="1000"></textarea>
                <input type="text" name="contact" class="contactor" placeholder="请留下您的手机号或邮箱，方便我们与您取得联系" maxlength="100">
            </div>
            <div class="modal-footer">
                <a href="javascript:void(0);" class="commonBtn blueBtn width125" onclick="submitFeedback();">提交</a>
                <a href="javascript:void(0);" class="commonBtn nocolorBtn width125" data-dismiss="modal" aria-label="Close">取消</a>
            </div>
            </form>
          </div>
        </div>
      </div>
    </div>
    <div class="wechat">
      <span class="wechatbtn"></span>
      <div class="wechatQR">微信客服<span><img src="${host}/images/client/wechat-qrcode.jpg"></span></div>
    </div>
    <div class="returnTop"></div>
  </div>

	<div id="msghint">
		<div class="modal fade" id="ac-msgHintWindow">
		    <div class="modal-dialog" style="width: 450px;">
		        <div class="modal-content">
		            <div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		                    <span aria-hidden="true">&times;</span>
		                </button>
		                <h4 class="modal-title">提示</h4>
		            </div>
		            <div class="modal-body">
		                <p></p>
		            </div>
		            <div class="modal-footer">
		                <a href="javascript:void(0);" class="commonBtn blueBtn width170" onclick="$('#ac-msgHintWindow').modal('hide');">关闭</a>
		            </div>
		        </div>
		    </div>
		</div>
	</div>


  <link rel="stylesheet" type="text/css" href="${host}/css/client/rightSide.css">
  <script type="text/javascript" src="${host}/js/client/rightSide.js"></script>
	<script type="text/javascript">
	$(function(){
		$("#msghint .modal").modal({
	        backdrop:"static",
	        keyboard: false,
	        show: false
	    });
	});
	function submitFeedback(){
		var params = {};
		var content = $('#feedback_form').find('textarea[name="content"]').val();
		var contact = $('#feedback_form').find('input[name="contact"]').val();
		if(!content){
			return;
		}else if(content.length > 1000 || contact.length >100){
			return;
		}
		$("#ac-feedbackHintWindow").modal("hide");
		$.ajax({
		    type: 'POST',
		    url: basepath +'/feedback/send' ,
		    data: "content="+content+"&contact="+contact ,
		    dataType: 'json' ,
		    success : function(data){
		    	$("#ac-msgHintWindow").find("div.modal-body p").html("您的回馈我们已经收到，感谢您为我们的成长做出贡献！")
				$("#ac-msgHintWindow").modal("show");
		    	$('#feedback_form').find('textarea[name="content"]').val('');
		    	$('#feedback_form').find('input[name="contact"]').val('');
		    }
		});
	}
	</script>