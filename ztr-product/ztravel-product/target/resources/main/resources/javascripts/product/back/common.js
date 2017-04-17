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

//        $(".allCheckbox").click(function(){
//            $(this).toggleClass("active");
//            if ($(this).hasClass("active")) {
//                $(this).parents("table").find(".checkbox").addClass("active");
//            } else {
//                $(this).parents("table").find(".checkbox").removeClass("active");
//            }
//        });
    });

    function trim(selector){
    	return $(selector).val($(selector).val().replace(/\ +/g,"")).val() ;
    }
    
    function trimHtml(selector){
    	return $(selector).html($(selector).html().replace(/\ +/g,"")).html() ;
    }
    
    function html2Escape(sHtml) {
	    return sHtml.replace(/[<>&"]/g,function(c){return {'<':'&lt;','>':'&gt;','&':'&amp;','"':'&quot;'}[c];});
	}
    
    function escape2Html(str) {
    	var arrEntities={'lt':'<','gt':'>','nbsp':' ','amp':'&','quot':'"'};
    	return str.replace(/&(lt|gt|nbsp|amp|quot);/ig,function(all,t){return arrEntities[t];});
	}
    
    function isEmpty(val){
    	return val == null || val.length == 0 ;
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

    function strToJson(str){
    	var json = eval('(' + str + ')');
    	return json;
    }

    function getStandardDate(date){
    	var y = date.getFullYear(),
        m = (date.getMonth() + 1) < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1),
        d = date.getDate() < 10 ? '0' + date.getDate() : date.getDate(),
        str = y + "-" + m + '-' + d ;
        return str ;
    }
    
    function isPositiveNum(s){//是否为整数 
    	var re = /^[1-9][0-9]*$/ ; 
    	var re2 = /^0$/ ;
    	return re.test(s) || re2.test(s) ;
	}
    
    function isZero(s){//是否为0
    	var re = /^0$/ ;
    	return re.test(s) ;
	}

    //处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外
    function banBackSpace(e){
    	//alert(event.keyCode)
        var ev = e || window.event;//获取event对象
        var obj = ev.target || ev.srcElement;//获取事件源
        var t = obj.type || obj.getAttribute('type');//获取事件源类型
        //获取作为判断条件的事件类型
        var vReadOnly = obj.readOnly;
        var vDisabled = obj.disabled;
        //处理undefined值情况
        vReadOnly = (vReadOnly == undefined) ? false : vReadOnly;
        vDisabled = (vDisabled == undefined) ? true : vDisabled;
        //当敲Backspace键时，事件源类型为密码或单行、多行文本的，
        //并且readOnly属性为true或disabled属性为true的，则退格键失效
        var flag1 = ev.keyCode == 8 && (t == "password" || t == "text" || t == "textarea") && (vReadOnly == true || vDisabled == true);
        //当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效
        var flag2 = ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea";
        //判断
        if (flag2 || flag1)
            event.returnValue = false;//这里如果写 return false 无法实现效果
    }
