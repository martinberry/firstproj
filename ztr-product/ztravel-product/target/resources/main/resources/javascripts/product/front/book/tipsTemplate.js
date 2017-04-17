/**
 *
 */

var tipWindow = {
		/***初始化提示框内信息
		 * title:提示窗标题
		 * tipMsg:提示窗内提示的文本信息
		 * confirmBtnTitle:提示窗的确认操作按钮的标题
		 * unconfirmBtnTitle:提示窗的＂不确认操作按钮＂的标题
		 * confirmBtnUrl:点击左边确认操作按钮时跳转的连接
		 * unconfrimBtnUrl:点击右边按钮时跳转的链接
		 * **/
		initWin : function(title,tipMsg,confirmBtnTitle,unconfirmBtnTitle,confirmBtnUrl,unconfrimBtnUrl){
			if(typeof(title) != undefined && title != '' && title != null){
				$("#tipTitle").html(title);
			}else{
				$("#tipTitle").html('提示');
			}

			if(typeof(tipMsg) != undefined && tipMsg != '' && tipMsg != null){
				$("#tipMsg").html(tipMsg);
			}else{
				$("#tipMsg").html('');
			}

			if(typeof(confirmBtnTitle) != undefined && confirmBtnTitle != '' && confirmBtnTitle != null){
				$("#confirmBtn").html(confirmBtnTitle);
			}else{
				$("#confirmBtn").html('确认');
			}

			if(typeof(unconfirmBtnTitle) != undefined && unconfirmBtnTitle != '' && unconfirmBtnTitle != null){
				$("#unconfirmBtn").html(unconfirmBtnTitle);
			}else{
				$("#confirmBtn").html('取消');
			}

			if(typeof(confirmBtnUrl) != undefined && confirmBtnUrl != '' && confirmBtnUrl != null){
				$("#confirmBtn").attr("href",confirmBtnUrl);
			}else{
				$("#confirmBtn").attr("href","javascript:tipWindow.hideWin()");
			}

			if(typeof(unconfrimBtnUrl) != undefined && unconfrimBtnUrl != '' && unconfrimBtnUrl != null){
				$("#unconfirmBtn").attr("href",unconfrimBtnUrl);
			}else{
				$("#unconfirmBtn").attr("href","javascript:tipWindow.hideWin()");
			}
		},
		hideWin : function(){
			$("#ac-payHintWindow").modal('hide');
		}
}