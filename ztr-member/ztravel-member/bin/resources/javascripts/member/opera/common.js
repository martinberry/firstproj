    $(function(){
        $(".radioList .radio").click(function(){
            $(this).addClass("active");
            $(this).siblings().removeClass("active");
        });
//        $(".commonTab tbody .checkbox").click(function(){
//            $(this).toggleClass("active");
//            var $that = $(this);
//
//            if ($(".commonTab tbody .checkbox").not(".active").length > 0) {
//                $that.parents("table").find(".allCheckbox").removeClass("active");
//            } else {
//                $that.parents("table").find(".allCheckbox").addClass("active");
//            }
//        });

//        $(".commonTab tbody .checkbox").on("click", function() {
//            $(this).toggleClass("active");
//            var $that = $(this);
//
//            if ($(".commonTab tbody .checkbox").not(".active").length > 0) {
//                $that.parents("table").find(".allCheckbox").removeClass("active");
//            } else {
//                $that.parents("table").find(".allCheckbox").addClass("active");
//            }
//        });

        $(".allCheckbox").click(function(){
            $(this).toggleClass("active");
            if ($(this).hasClass("active")) {
                $(this).parents("table").find(".checkbox").addClass("active");
            } else {
                $(this).parents("table").find(".checkbox").removeClass("active");
            }
        });
    });

    function trim(selector){
    	return $(selector).val($(selector).val().replace(/\ +/g,"")).val() ;
    }

    function chooseRow(checkBox){
    	if($(checkBox).hasClass("active")){
    		$(checkBox).removeClass("active") ;
    	}else{
    		$(checkBox).addClass("active") ;
    	}
        var $that = $(checkBox);

        if ($(".commonTab tbody .checkbox").not(".active").length > 0) {
            $that.parents("table").find(".allCheckbox").removeClass("active");
        } else {
            $that.parents("table").find(".allCheckbox").addClass("active");
        }
    }