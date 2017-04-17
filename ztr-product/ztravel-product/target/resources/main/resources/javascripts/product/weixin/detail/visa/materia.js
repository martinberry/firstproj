$(function(){
        FastClick.attach(document.body);

        $(".acut-menu").click(function (event) {
            var pop = $(this).prev().find(".menu-pop-container");
            if (pop.is(":visible")) {
                pop.slideUp("fast");
            }
            $(this).find(".menu-list-container").toggle();
            if ($(".menu-list-container").is(":visible")) {
                $(document).click(function () {
                    $(".menu-list-container").fadeOut("fast");
                });
            }
            event.stopPropagation();
        });
        //查看样图
        $(".post-link").click(function(){
        	var imageId = $(this).attr("data-image");
     		var url = mediaServer+"imageservice?mediaImageId="+imageId;
     	    $(this).popupimg(url);
        });
    });
