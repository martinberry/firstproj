/**
 * 
 */

$(function(){
	//查看样图
    $(".post-link").click(function(){
    	var imageId = $(this).attr("data-image");
    	var url = mediaServer+"imageservice?mediaImageId="+imageId;
        $(this).popupimg(url);
    });
    
    //展开收起初始化
    $("body").delegate(".commonBottomStyle", "click", function(e){
        if($(this).is(".orderNotice")){
            $("#orderWrap").removeClass("maxHeight");
            $(".orderNotice").css("display", "none");
            $(".closeOrderNotice").css("display", "block");
        }else if($(this).is(".closeOrderNotice")){
            $("#orderWrap").addClass("maxHeight");
            $(".orderNotice").css("display", "block");
            $(".closeOrderNotice").css("display", "none");
            $("body").animate({scrollTop: $("#orderPoint").offset().top}, 300);
        }else if($(this).is(".judgeNotice")){
            $(".evaluateDetail").css({
                height: "auto",
                overflow: "visible"
            });
            $(".judgeNotice").css("display", "none");
            $(".closeJudgeNotice").css("display", "block");
        }else if($(this).is(".closeJudgeNotice")){
            $(".evaluateDetail").css({
                height: "7.25rem",
                overflow: "hidden"
            });
            $(".judgeNotice").css("display", "block");
            $(".closeJudgeNotice").css("display", "none");
            $("body").animate({scrollTop: $("#judgePoint").offset().top}, 300);
        }
    });
    
    //next
    $("div.bottomFixed a.rightBtn").click(function(){
    	var pid = $("#pid").val();
    	if(typeof(pid) != undefined && pid != ''){
    		window.location.href = wxServer + "/weixin/product/visa/daySelect/" + pid ;
    	}else{
    		
    	}
    	
    });
    //查看签证材料
    toMateria();
});

function toMateria(){
	$("ul.visaMaterial li").click(function(){
		var type = $(this).find("a.visaElem").attr("data-val");
		var pid = $("#pid").val();
		window.location.href = wxServer+"/weixin/product/visa/getMateria/"+type+"/"+pid;
	});
}
