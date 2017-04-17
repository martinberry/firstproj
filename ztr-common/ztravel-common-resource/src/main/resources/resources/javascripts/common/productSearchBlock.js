$(function(){
	//出发地浮层
    $(".selectStartOffPlace li").click(function(){
        $(this).addClass("active");
        $(this).siblings("li").removeClass("active");
        $(".searchTab .startOffPlace input").val($(this).html());
        $(".selectStartOffPlace").hide();
    });
    $(".searchTab .startOffPlace").click(function(event){
        $(this).siblings(".selectStartOffPlace").toggle();
        $(".selectDestination").hide();
        event.stopPropagation();
    });
    $("body").click(function(){
        $(".selectStartOffPlace").hide();
    });

    //目的地
    $(".destinationModel .destination").click(function(event){
        $(this).siblings(".selectDestination").toggle();
        $(".selectStartOffPlace").hide();
        event.stopPropagation();
    });

    initSetActiveDest();

    $(".destSelect-first-level").mouseover(function(){
        $(this).siblings("li").find(".second-level").hide();
        $(this).find(".second-level").show();
    });

    $(".destSelect-first-level").click(function(){
        $(this).siblings("li").removeClass("active");
        $(this).addClass("active");
        $(".sl-destName").removeClass("active");
        $(".destinationInput").val($(this).find(".fl-destName").html());
        $("#selectedDestLevel").val(1);
    });

    $(".ul-dest-first-level").mouseleave(function(){
        initSetActiveDest();
    });

    $(".sl-destName").click(function(event){
        $(".destinationInput").val($(this).html());
        $("#selectedDestLevel").val(2);
        $(".sl-destName").removeClass("active");
        $(this).addClass("active");
        $("li.destSelect-first-level").removeClass("active");
        $(this).parents(".destSelect-first-level").addClass("active");
        $(".selectDestination").hide();
        event.stopPropagation();
    });

    $("body").click(function(){
        $(".selectDestination").hide();
    });

    //搜索按钮点击
	$("#searchBtn").click(function(){
		var departure = $("#departure").val();
		var destination = $("#destination").val();
		var destLevel = $("#selectedDestLevel").val();

		var searchParam = "?departure=" + departure + "&destination=" + destination + "&destLevel=" + destLevel;
		window.location.href = basepath + "/product/list" + searchParam;
	});

});

function initSetActiveDest() {
    var destText = $(".destinationInput").val();

    if (destText == "") {
        $(".cityList .second-level").hide();
        $(".cityList .second-level").eq(0).show();
        return;
    } else {
        $(".fl-destName").each(function(){
            if ($(this).html() == destText) {
                $(".destSelect-first-level").removeClass("active");

                $(this).closest('li').addClass("active");

                $(".cityList .second-level").hide();
                $(this).closest('li').find(".second-level").show();
            }
        });

        $(".sl-destName").each(function(){
            if ($(this).html() == destText) {
                $(".sl-destName").removeClass("active");
                $(".destSelect-first-level").removeClass("active");

                $(this).addClass("active");
                $(this).parents("li.destSelect-first-level").addClass("active");

                $(".cityList .second-level").hide();
                $(this).closest("ul.second-level").show();
            }
        });
    }
}