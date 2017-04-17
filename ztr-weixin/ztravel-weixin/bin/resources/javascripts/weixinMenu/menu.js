//var urlRegex ="^[A-Za-z]+://[A-Za-z0-9-_]+\\.[A-Za-z0-9-_%&\?\/.=]+$";
var urlRegex = '(http[s]?|ftp):\/\/[^\/\.]+?';
$(function(){
        $(".modal").modal({
            backdrop: "static",
            keyboard: false,
            show: false
        });

        
        
        $(".leftMenu .menu").click(function(){
        	$(this).parents("li").removeClass("current");
        	$(this).parents("li").siblings("li").addClass("current");
        });
        
        $(".leftMenu .topic").click(function(){
        	$(this).parents("li").removeClass("current");
        	$(this).parents("li").siblings("li").addClass("current");
        })
        
        
        //删除文本
        $(".body-menu").on("click", ".wechat-close", function(event){
            var beforeLink = $(this).parents("td").find(".teleText");
            if(beforeLink.hasClass("hidden")){
                beforeLink.removeClass("hidden");
                $(this).parent().remove();
            }
            event.stopPropagation();
        });

        //删除行
        $(".body-menu").on("click", ".first-menu-op", function(event){
        	var delLineId = $(this).parents("tr").attr("del-value");
        	$("#delLineId").val(delLineId);
        	//获取删除level
        	var levelClass = $(this).parents("tr").attr("class");
        	if(levelClass != null && levelClass !=""){
        		if(levelClass == "first-menu"){
        			$("#delLineLevel").val("first");
        		}else if(levelClass == "second-menu"){
        			$("#delLineLevel").val("second");
        		}
        	}
            $("#deleModal").modal("show");
            event.stopPropagation();
        });

        $(".main-container").on("click", "#revokeMenu", function(event){
            $("#cancelConfirm").modal("show");
            event.stopPropagation();
        });

        $("body").on("click", "#cancelMenu_confirm", function(event){
        	  $("#cancelConfirm").modal("hide");
        	  $.ajax({
    			  type:"GET",
    			  url : "../weixinMenu/cancelMenu",
    			  success: function(result){
    				  if(result.res_code == "success"){
    					  clickActionPopup("dlg-cancelMenu");
    			            event.stopPropagation();
    					  event.stopPropagation();
    				  }else{
    					  clickActionPopup("dlg-cancelMenu-failed");
    					  event.stopPropagation();
    				  }
    			  }
    		  })

        });

        $("#confirm_del").delegate("","click",function(){
        	var delLineId = $("#delLineId").val();
        	var delLineLevel = $("#delLineLevel").val();
        	var parentItem = $("[del-value = '"+delLineId+"']");
        	if(delLineLevel != null && delLineId != null){
        		if(delLineLevel =='second' ){
        		     var prevSndItem = parentItem.prev(".second-menu");
                     var nextSndItem = parentItem.next(".second-menu");
                     if(prevSndItem.length != 0 && nextSndItem.length == 0){
                         prevSndItem.find(".second-item-line").removeClass("second-item-line").addClass("second-last-line");
                     }
                     if((parentItem.prev().hasClass("first-menu")) && (parentItem.next().hasClass("first-menu"))){
                         parentItem.prev().find(".first-menu-action").removeClass("hidden");
                         parentItem.prev().find(".first-menu-response").removeClass("hidden");

                         parentItem.prev().find(".dropdownBtn").removeClass("hidden");
                         parentItem.prev().find(".cuRadioTxt").removeClass("hidden");
                         parentItem.prev().find(".teleText").removeClass("hidden");
                     }
                     if((parentItem.prev().is(".first-menu")) && (parentItem.next().length == 0)){
                         parentItem.prev().find(".first-menu-action").removeClass("hidden");
                         parentItem.prev().find(".first-menu-response").removeClass("hidden");
                         parentItem.prev().find(".dropdownBtn").removeClass("hidden");
                         parentItem.prev().find(".cuRadioTxt").removeClass("hidden");
                         parentItem.prev().find(".teleText").removeClass("hidden");
                     }
        			$("[del-value = '"+delLineId+"']").remove();

        		}else if(delLineLevel == "first"){
        			$("[del-value *= '"+delLineId+"']").remove();
        		}
        	}
        	 $("#deleModal").modal("hide");

        })

        //添加一级菜单行
        $(".wechat-addMainBtn").click(function(){
            var cntFirstMenu = $(".wechat-menuTab > tbody").children(".first-menu").length;
            if(cntFirstMenu == 3){
            	clickActionPopup("firstLevelLengthTip");
                return;
            }
            var lastNum = 0;
            var firstMenus =　[];
            firstMenus = $(".wechat-menuTab > tbody").find(".first-menu")
            if(firstMenus != null && firstMenus.size() >0){
            	var lastFirstMenu = $(firstMenus).last();
            	lastNum = parseInt($(lastFirstMenu).find(".first-menu-num").val());
            }
            var addTabRow = '<tr class="first-menu" del-value="del_'+(lastNum+1)+'">' +
                    '<td><input class="first-menu-num" type="text" value='+(lastNum+1)+'></td>' +
                    '<td><input class="first-menu-name" type="text" ><a class="addBtn01" href="javascript:void(0);"></a></td>' +
                    '<td>' +
                    '<div class="dropdown first-menu-action">' +
                    '<a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">' +
                    '<span class="activeFonts">跳转链接</span>' +
                    '<span class="caret"></span>' +
                    '</a>' +
                    '<ul class="dropdown-menu">' +
                    '<li class="active"><a href="javascript:void(0);">跳转链接</a></li>' +
                    '<li><a href="javascript:void(0);">发送消息</a></li>' +
                    '</ul>' +
                    '</div>' +
                    '</td>' +
                    '<td><input type="text" class="first-menu-response" ></td>' +
                    '<td>' +
                    '<div class="checkboxAction">' +
                    '<label class="checkboxInfo active">' +
                    '<span class="checkboxIcon"></span>' +
                    '</label>' +
                    '</div>' +
                    '</td>' +
                    '<td style="text-align: center;">' +
                    '<a class="first-menu-op" href="javascript:void(0);">删除</a>' +
                    '</td>' +
                    '</tr>';
            var cntFirstMenu = $(".wechat-menuTab > tbody").children(".first-menu").length;
            if(cntFirstMenu == 3){
                return;
            }
            $(".wechat-menuTab > tbody").append(addTabRow);
        });

        $("body").delegate(".teleTxt-left .radio","click",function(){
            $(".teleTxt-left .radio").removeClass("active");
            $(this).addClass("active");
        });

        //添加二级菜单
        $(".body-menu").on("click", ".addBtn01", function(event){
            var theParent = $(this).parents(".first-menu");
            var nextParent = theParent.nextAll(".first-menu:first");
            var delValue = theParent.attr("del-value");
            var sndItem = theParent.siblings("[del-value*='"+delValue+"']");
            if(sndItem.length>=5){
            	clickActionPopup("secondLevelLengthTip");
                return false;
            }
            var nextSndNum = 0;
            if(nextParent.length == 0 && sndItem.length != 0){
                nextSndNum = parseInt(sndItem.last().find(".second-menu-num").val());
            }
            if(nextParent.length != 0 && sndItem.length != 0){
                nextSndNum = parseInt(nextParent.prev().find(".second-menu-num").val());
            }
            var sndMenuNum = (nextParent.length == 0 && sndItem.length == 0)?0:nextSndNum;
            var firstLevelNum = $(this).parents("tr").attr("del-value");

            var secondMenu = '<tr class="second-menu" del-value="'+firstLevelNum+'_'+sndMenuNum+'">' +
                    '<td><input class="second-menu-num" type="text" value="'+(sndMenuNum+1)+'" ></td>' +
                    '<td>' +
                    '<i class="second-last-line"></i><input class="second-menu-name" type="text" >' +
                    '</td>' +
                    '<td>' +
                    '<div class="dropdown first-menu-action">' +
                    '<a href="javascript:void(0);" data-toggle="dropdown" class="dropdownBtn">' +
                    '<span class="activeFonts">跳转链接</span>' +
                    '<span class="caret"></span>' +
                    '</a>' +
                    '<ul class="dropdown-menu">' +
                    '<li class="active"><a href="javascript:void(0);">跳转链接</a></li>' +
                    '<li ><a href="javascript:void(0);">发送消息</a></li>' +
                    '</ul>' +
                    '</div>' +
                    '</td>' +
                    '<td><input type="text" class="first-menu-response" ></td>' +
                    '<td>' +
                    '<div class="checkboxAction">' +
                    '<label class="checkboxInfo active">' +
                    '<span class="checkboxIcon"></span>' +
                    '</label>' +
                    '</div>' +
                    '</td>' +
                    '<td style="text-align: center;">' +
                    '<a class="first-menu-op" href="javascript:void(0);">删除</a>' +
                    '</td>' +
                    '</tr>';

            if(nextParent.length == 0){
                if(sndItem.length == 0){
                    $(".wechat-menuTab > tbody").append(secondMenu);
                }else if(sndItem.length < 5){
                    var lastItem = theParent.nextAll(".second-menu:last").find(".second-last-line");
                    lastItem.removeClass("second-last-line").addClass("second-item-line");
                    $(".wechat-menuTab > tbody").append(secondMenu);
                }
            }else{
                var lastLine = nextParent.prev().find(".second-last-line");
                lastLine.removeClass("second-last-line").addClass("second-item-line");
                $(nextParent).before(secondMenu);
            }
            theParent.find(".first-menu-action").addClass("hidden");
            theParent.find(".first-menu-response").addClass("hidden");
            theParent.find(".dropdownBtn").addClass("hidden");
            theParent.find(".cuRadioTxt").addClass("hidden");
            theParent.find(".teleText").addClass("hidden");
        });



        //改变响应动作
        $(".body-menu").on("click", ".dropdown-menu li", function(){
        	   var dropItem = $(this).children("a").html();
               var firstItem = $(this).parent().find("li:first");
               var secondItem = $(this).parent().find("li:last");
               var linkItem = '<input type="text" class="first-menu-response">';
               var teleItem = '<a href="javascript:void(0);" class="teleText">选择图文信息</a>';
               var nextItem = $(this).parents("td").next();

               $(this).closest(".first-menu-action").find(".activeFonts").html(dropItem);
               $(this).addClass("active");
               $(this).siblings().removeClass("active");
               if($(this).is(firstItem)){
                   if(nextItem.children().is("input[class='first-menu-response']")){
                       return;
                   }
                   nextItem.empty().html(linkItem);
               }
               if($(this).is(secondItem)){
                   if(nextItem.children().is("a[class='teleText']")){
                       return;
                   }
                   nextItem.empty().html(teleItem);
               }
        });
    });

 $(function(){

    $(".body-menu").on("blur", ".first-menu-name,.second-menu-name,.first-menu-response", function(){
    	$(this).val($(this).val().trim().replace(/</g,"&lt").replace(/>/g,""));
	  })

	 $(".main-container").delegate("#saveBtn","click",function(){
	 	var menuVo = {}
	 	if(!validaeMenu()){
	 		return false;
	 	}
	 	menuVo = buildMenuVo();
		 $.ajax({
			 type:"POST",
			 url:"../weixinMenu/saveMenu",
			 data:JSON.stringify(menuVo),
			 contentType:"Application/Json",
			 success:function(result){
				 console.log("result:"+result);
				 if(result.res_code == "success"){
					  clickActionPopup("dlg-saveMenu");
					 event.stopPropagation();
				 }else{
					  clickActionPopup("dlg-saveMenu-failed");
						 event.stopPropagation();
				 }
			 },
			 error: function(XMLHttpRequest, textStatus, errorThrown) {
				  clickActionPopup("dlg-saveMenu-failed");
				 event.stopPropagation();
			 }
		 })
	 })

	  $(".main-container").delegate("#customMenu","click",function(){
		  $.ajax({
			  type:"POST",
			  url:"../weixinMenu/createMenu",
			  success: function(result){
					 if(result.res_code == "success"){
						 clickActionPopup("dlg-bliudMenu");
						event.stopPropagation();
					 }else{
						 clickActionPopup("dlg-bliudMenu-failed");
						 event.stopPropagation();
					 }
			  }
		  })
	 })



     //"启用"复选框的选中与取消
     $(".body-menu").on("click", ".first-menu .checkboxAction .checkboxInfo", function(event){
    	 var firstLevelCheckBox = $(this);
         $(this).toggleClass("active");
         var delValue = $(this).parents("tr").attr("del-value");
         $(".second-menu[del-value*='"+delValue+"']").find(".checkboxAction .checkboxInfo").each(function(){
        	 if($(firstLevelCheckBox).hasClass("active")){
        		 $(this).addClass("active");
        	 }else{
        		 $(this).removeClass("active");
        	 }

         })
         event.stopPropagation();
     });

    $(".body-menu").on("click", ".second-menu .checkboxAction .checkboxInfo", function(event){
        $(this).toggleClass("active");
        event.stopPropagation();
    });


 })

 function buildMenuVo(){
	 var menuVo = {};
	 var firstLevelButtons = [];
	 $(".first-menu").each(function(){
		 var firstLevelButton = {};
		 var delLineId =  $(this).attr("del-value");
		 firstLevelButton.name=$(this).find(".first-menu-name").val();
		 var firstLevelPriority = $(this).find(".first-menu-num").val();
		 firstLevelButton.priority = firstLevelPriority;
		 firstLevelButton.viewState = firstLevelPriority;
		var stateClass = $(this).find(".checkboxAction label").attr("class");
	 	if($(this).find(".checkboxAction label").hasClass("active")){
	 			firstLevelButton.state = 1;
	 	}else{
	 		firstLevelButton.state = 0;
	 	}

		 var secondMenu =  $(".second-menu[del-value *= '"+delLineId+"']");
			 if(secondMenu.size()>0){
				 var subMenuButtonVos = [];
			 $(secondMenu).each(function(){
					 var subMenuButtonVo ={}
					 subMenuButtonVo.priority = $(this).find(".second-menu-num").val();
					 subMenuButtonVo.name=$(this).find(".second-menu-name").val();
					 subMenuButtonVo.viewState = firstLevelPriority + $(this).find(".second-menu-num").val();
					 var type = $(this).find(".dropdown .activeFonts").html().trim();
					 if(type != null && type != ""){
						 if(type == "跳转链接"){
							 subMenuButtonVo.type = "view";
							 subMenuButtonVo.url = $(this).find(".first-menu-response").val();
						 }else if(type =="发送消息"){
							 subMenuButtonVo.type = "click";
							 subMenuButtonVo.mediaId = $(this).find("input[name='mediaId']").val();
						 }
					 }

				 	if($(this).find(".checkboxAction label").hasClass("active")){
				 		subMenuButtonVo.state = 1;
				 	}else{
				 		subMenuButtonVo.state = 0;
				 	}

					 subMenuButtonVos.push(subMenuButtonVo);
			 })
			 firstLevelButton.subMenuButtonVos = subMenuButtonVos;
		 }else{
			  var type = $(this).find(".dropdown .activeFonts");
			  if(type != null && type !=""){
				  console.log("type:" +type.html())
				  type = $(type).html().replace(" ","")
			  }
			 if(type != null && type != ""){
				 if(type == "跳转链接"){
					 firstLevelButton.type = "view";
					 firstLevelButton.url = $(this).find(".first-menu-response").val();
				 }else if(type =="发送消息"){
					 firstLevelButton.type = "click";
					 firstLevelButton.mediaId =$(this).find("input[name='mediaId']").val();
				 }
			 }
		 }

		 firstLevelButtons.push(firstLevelButton);

	 })
	 menuVo.firstLevelButtons=firstLevelButtons;
	 return menuVo;
 }


 function validaeMenu(){
	 var result = true;

	 //校验一级菜单名称
	 try{
	    $(".body-menu").find(".first-menu-name").each(function(){
	    	var name = $(this).val();
    		if(name != null ){
    			name  = name.trim();
    			var byteLength = name.getBytesLength()
    			if(byteLength>16 || byteLength<=0){
    				clickActionPopup("firstLevelTip");
    				throw "validate error"
    			}
    		}
	     });

	 //校验二级菜单名称
	    $(".body-menu").find(".second-menu-name").each(function(){
	    	var name = $(this).val();
    		if(name != null ){
    			name  = name.trim();
    			var byteLength = name.getBytesLength()
    			if(byteLength>40 || byteLength<=0){
    				clickActionPopup("secondLevelTip");
    				throw "validate error"
    			}
    		}
	     });

	 //校验响应动作
	 $(".teleText").each(function(){
		 if(!$(this).hasClass("hidden")){
			 clickActionPopup("chooseNewsMessageTip");
			 throw "validate error"
		 }
	 })

	 $(".first-menu-response").each(function(){
		 if(!$(this).hasClass("hidden")){
			 var jumpUrl = $(this).val().trim();
			 var re=new RegExp(urlRegex);
			 if(re.test(jumpUrl) || jumpUrl.endWith(";zhenlx.com")){
		    	}else{
		    		 clickActionPopup("jumpUrlTip");
		    		 throw "validate error"
		    	}
		 }
	 })


//校验一级菜单序号
    $(".body-menu").find(".first-menu-num").each(function(){
     	var currentPriority = Number($(this).val().trim());
	    	if(isPriority(currentPriority)){
		    	var delValue = $(this).parents("tr").attr("del-value");
		    	$(this).parents("tr").siblings().each(function(){
		    		var priority = $(this).find(".first-menu-num").val();
		    		if(priority == currentPriority){
		    			clickActionPopup("firstLevelPriorityNumRepeatTip");
		    			throw "validate error"
		    		}
		    	})
	    	}else{
	    		 clickActionPopup("priorityNumTIp");
	        	throw "validate error"
	    	}
	     });


//校验二级菜单序号
	    $(".body-menu").find(".second-menu-num").each(function(){
	     	var currentPriority = Number($(this).val().trim());
	    	if(isPriority(currentPriority)){
	    		var delValue = $(this).parents("tr").attr("del-value");
		    	delValue= delValue.substring(0,delValue.length-1);
		    	$(this).parents().siblings("[del-value*='"+delValue+"']").each(function(){
		    		var priority = $(this).find(".second-menu-num").val();
		    		if(priority != null && priority.trim() !=""){
		    			priority = Number(priority);
		    			if(priority == currentPriority){
			    			clickActionPopup("secondLevelPriorityNumRepeatTip");
			    			throw "validate error"
			    		}
		    		}
		    	})
	    	}else{
	    		 clickActionPopup("priorityNumTIp");
	        	throw "validate error"
	    	}
	     });
	 }catch(e){
		 result =false;
		 return result;
	 }
	return result;
 }

 function isPriority(priorityNum){
	 var result = true;
	 var   reg =/^[1-9]$/
     if(!reg.test(priorityNum)){
    	 clickActionPopup("priorityNumTip");
    	 result= false;
    	 return result;
     }
	 return result;

 }

 $(function(){

	 String.prototype.getBytesLength = function() {
		    var totalLength = 0;
		    var charCode;
		    for (var i = 0; i < this.length; i++) {
		        charCode = this.charCodeAt(i);
		        if (charCode < 0x007f)  {
		            totalLength++;
		        } else if ((0x0080 <= charCode) && (charCode <= 0x07ff))  {
		            totalLength += 2;
		        } else if ((0x0800 <= charCode) && (charCode <= 0xffff))  {
		            totalLength += 3;
		        } else{
		            totalLength += 4;
		        }
		    }
		    return totalLength;
		}

	 String.prototype.endWith=function(str){
		 if(str==null||str==""||this.length==0||str.length>this.length)
		   return false;
		 if(this.substring(this.length-str.length)==str)
		   return true;
		 else
		   return false;
		 return true;
		 }

	    $(".body-menu").on("blur", ".first-menu-name", function(event){
	    		var name = $(this).val();
	    		if(name != null ){
	    			name  = name.trim();
	    			var byteLength = name.getBytesLength()
	    			if(byteLength>16 || byteLength<=0){
	    				clickActionPopup("firstLevelTip");
	    			}
	    		}
	     });

	    $(".body-menu").on("blur", ".second-menu-name", function(event){
	    	var name = $(this).val();
	    	if(name != null){
	    		name = name.trim();
	    		var byteLength = name.getBytesLength();
	    		if(byteLength>40 || byteLength<=0 ){
	    			clickActionPopup("secondLevelTip");
		    	}
	    	}

	     });

	    $(".body-menu").on("blur", ".second-menu-num", function(event){
	     	var currentPriority = Number($(this).val().trim());
	    	if(isPriority(currentPriority)){
	    		var delValue = $(this).parents("tr").attr("del-value");
		    	delValue= delValue.substring(0,delValue.length-1);
		    	$(this).parents().siblings("[del-value*='"+delValue+"']").each(function(){
		    		var priority = $(this).find(".second-menu-num").val();
		    		if(priority != null && priority.trim() !=""){
		    			priority = Number(priority);
		    			if(priority == currentPriority){
			    			clickActionPopup("secondLevelPriorityNumRepeatTip");
			    			return false;
			    		}
		    		}
		    	})
	    	}else{
	    		 clickActionPopup("priorityNumTIp");
	         	 return false;
	    	}
	     });

	    $(".body-menu").on("blur", ".first-menu-num", function(event){
	    	var currentPriority = Number($(this).val().trim());
	      	if(isPriority(currentPriority)){
		    	var delValue = $(this).parents("tr").attr("del-value");
		    	$(this).parents("tr").siblings().each(function(){
		    		var priority = $(this).find(".first-menu-num").val();
		    		if(priority == currentPriority){
		    			clickActionPopup("firstLevelPriorityNumRepeatTip");
		    		}
		    	})
	      	}else{
	      		 clickActionPopup("priorityNumTIp");
	      	}
	     });

	    $(".body-menu").on("blur", ".first-menu-response", function(event){
	    	if(!$(this).hasClass("hidden")){
	    		var jumpUrl =  $(this).val().trim();
	    		 var re=new RegExp(urlRegex);
				 if(re.test(jumpUrl) || jumpUrl.endWith(";zhenlx.com")){
		    	}else{
		    		 clickActionPopup("jumpUrlTip");
		    	}
	    	}

	    })

 })



