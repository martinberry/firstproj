(function($){

    //备份jquery的ajax方法
    var _ajax=$.ajax;

    //重写jquery的ajax方法
    $.ajax=function(opt){
        //备份opt中error和success方法
        var fn = {
            error:function(XMLHttpRequest, textStatus, errorThrown){},
            success:function(data, textStatus){}
        }
        if(opt.error){
            fn.error=opt.error;
        }
        if(opt.success){
            fn.success=opt.success;
        }

        //扩展增强处理
        var _opt = $.extend(opt,{
            error:function(XMLHttpRequest, textStatus, errorThrown){
                //错误方法增强处理
                fn.error(XMLHttpRequest, textStatus, errorThrown);
            },
            success:function(data, textStatus,xhr){
                //成功回调方法增强处理
                fn.success(data, textStatus);
            },
            //传入statusCode对象，定义对状态码操作的方法
            statusCode: {
            	//900为服务器返回的自定义状态码，说明当前操作没有权限
             901 : function() {
            	 window.location.href = basepath + "/user/login";
             },
             902 : function() {
            	window.location.href = basepath + "/home";
             }
           }
        });
        _ajax(_opt);

    };

})(jQuery);