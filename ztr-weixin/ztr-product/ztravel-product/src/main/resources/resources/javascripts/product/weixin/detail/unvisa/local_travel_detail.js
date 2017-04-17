$(function () {

    FastClick.attach(document.body);

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

	$("#preOrder").on("click","",function(){
		var pid = $("#pid").val();
		window.location.href = basepath+"/local/product/weixin/chooseTrip/"+pid;
	});
});
