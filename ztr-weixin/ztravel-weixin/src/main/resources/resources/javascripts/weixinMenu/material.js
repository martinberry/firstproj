$(function(){
	$("body").delegate(".teleText","click",function(){
	    $("#dlg-teleText").modal("show");
		$("input[name='pageNo']").val(1);
		$("input[name='pageSize']").val(20);
		$(".toolbars-til").val("");
		var selectMenuButtonId = $(this).parents("tr").attr("del-value");
		$("#selectMenuButtonId").val(selectMenuButtonId);
		$("input[name='type']").val("modalshow");
		//submitFunc();
		$(".commonTab tbody").html("");
		$("#pagination").html("");
	});

	$(".toolbars-search").click(function(){
		$("input[name='pageNo']").val(1);
		$("input[name='type']").val("search");
		var temptitle=$(".toolbars-til").val();
		var title=temptitle.replace(new RegExp(" ","gm"),"");
		$(".toolbars-til").val(title);
	    submitFunc();
	});

	 //同步微信素材
	var verifycode_lock = false ;


		$(".commonTab").delegate(".teleText-header-load","click",function(){
			if( !verifycode_lock) {
				verifycode_lock=true;
				var host=$("#host").val();
	        $(".teleText-header-load .wechat-load").attr("src", host+"/images/loading.gif");
	        $(".teleText-header-load .wechat-load-txt").html("正在同步数据···");
	        $.ajax({
	    		//type:"POST",
	    	 url:basepath+"/weixinMaterialController/collectMaterial",
	    	 headers:{
	    		   'Accept' : 'application/json',
	    			'Content-Type' : 'application/json' ,
	    	   },
	    	   dataType:"json",
	    	   success:function(data){
	    		   if(data.res_code=="ok"){
	    			   verifycode_lock = false ;
	    			   $("input[name='pageNo']").val(1);
	    				$("input[name='pageSize']").val(20);
	    				$(".toolbars-til").val("");
	    			   submitFunc();
	    		   }
	    		   else{}
	    	   }
	    	});
			}
	    });



	$(".wechat-dlg-confirm").click(function(){

		var checkedRadio =$(".radio.active");
		if(checkedRadio == null || checkedRadio.size()==0){
			 clickActionPopup("choose_material_tip")
	    	  return false;
		}
		var title =  $(".radio.active").parents("li").find(".teleTxt-detail:first").html();
        var mediaId=$(".radio.active").attr("value");
	    var selectMenuButtonId = $("#selectMenuButtonId").val();
	    if(mediaId != null && mediaId != "" && title != null && title != ""){
	    	if(title != null && title.length > 15){
	    		title = title.substring(0,15)+"...";
	    	}
	    	if(selectMenuButtonId != null && selectMenuButtonId != ""){
	    		var delButton = $("[del-value='"+selectMenuButtonId+"']");
	    		var newsInfoHtml =  "<a href='javascript:void(0);' class='teleText hidden'>选择图文信息</a>"
	    			+"<input type='hidden' name='mediaId' value='"+mediaId+"'/>"
	    			+"<div class='cuRadioTxt clearfix'><span>"+title+"</span><a href='javascript:void(0);' class='wechat-close'></a></div>";
	    		$(delButton).find(".teleText").parents("td").html(newsInfoHtml);
	    	}
	    }else{
	    	  clickActionPopup("choose_material_tip")
	    	  return false;
	    }
	    $("#dlg-teleText").modal("hide");
	});
});



//查询素材列表
function submitFunc(){
	var showparam = $("form").serializeObject();
		$.ajax({
			type : "POST",
			url : basepath + "/weixinMaterialController/showMaterial",
			data : JSON.stringify(showparam),
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			dataType : "html",
			success : function(data) {
				 var resultArray = data.split("<-split->");
					var data = resultArray[0];
					var pagination = resultArray[1];
					$(".commonTab tbody").html(data);
					$("#pagination").html(pagination);
					var host=$("#host").val();
					$(".teleText-header-load .wechat-load").attr("src", host+"/images/wechat-loading.png");
			        $(".teleText-header-load .wechat-load-txt").html("同步微信素材");

			},
		});
}




//同步素材列表
function synFunc(){
	$.ajax({
		//type:"POST",
	 url:basepath+"/weixinMaterialController/collectMaterial",
	 headers:{
		   'Accept' : 'application/json',
			'Content-Type' : 'application/json' ,
	   },
	   dataType:"json",
	   success:function(data){
		   if(data.res_code=="ok"){
			   verifycode_lock = false ;
			   $("input[name='pageNo']").val(1);
				$("input[name='pageSize']").val(20);
				$(".toolbars-til").val("");
			   submitFunc();
		   }
		   else{}
	   }
	});
}

/*//确定选择查询具体素材
function searchdetailFunc(mediaId,title){
		$.ajax({
			type:"POST",
			url:basepath+"/weixinMaterialController/confirm",
			data :{
				"mediaId":mediaId,
				"title":title
			},
			   async:false,
			   dataType:"json",
			   success:function(data){

			   }
		});
}*/



$.fn.serializeObject = function(){
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
}




