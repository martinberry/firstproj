var urlRegx ="^[A-Za-z]+://[A-Za-z0-9-_]+\\.[A-Za-z0-9-_%&\?\/.=]+$";
var bannerTitleRegx = /(?!.*\/|.*\{|.*}|.*\^|.*\*|.*\\|.*>)^.*$/;
$(function(){

        //  删除弹窗提示
    	　　$("body").on("click",".material-info-item .deleteBtn",function(){
    		var bannerSize = $(".material-info-item.clearfix").not(".copyTemplate").size();
        	if(bannerSize != null && bannerSize <=1){
        		 clickActionPopup("bannerMinLengthTip");
				 return false;
        	}
    		$("#delPriority").val('');
    		var delPriority = $(this).parents(".material-info-item").find(".number.commonStyle").html();
    		$("#delPriority").val(delPriority);
	　  		$("#deleteWindow").modal("show");
		　　});
    	//取消
    	　　$("body").on("click","#cancelDel",function(){
	　  		$("#deleteWindow").modal("hide");
		　　});
    	//确认
	   	　　$("body").on("click","#confirmDel",function(){
	   			var delPriority = $("#delPriority").val();
	   			$("div:contains('"+delPriority+"').number.commonStyle").each(function(){
	   				$(this).parents(".material-info-item.clearfix").remove();
	   			})
	   			getOrderNum();
	　  			$("#deleteWindow").modal("hide");
		　　});


        /* 添加Banner模块 */
        $(".addContentItem").click(function(){
        	var bannerSize = $(".material-info-item.clearfix").not(".copyTemplate").size();
        	if(bannerSize != null && bannerSize >=6){
        		 clickActionPopup("bannerMaxLengthTip");
				 return false;
        	}
            $(".material-info-list-container").append("<div class='material-info-item clearfix'>" + $(".material-info-item.copyTemplate").html() + "</div>");
            getOrderNum();
        });


        //  拖拽效果
        $(".material-info-list-container").sortable({
            placeholder: "material-tr-sortable-placeholder",
            stop: function(){ // 拖拽效果结束时，重新生成排序数字
                getOrderNum();
            }
        });

    });

    // 重新计算模块排序
    function getOrderNum() {
        $(".material-info-item").not($(".material-info-item.copyTemplate")).each(function(index){
            $(this).find(".number").html(index + 1);
        });
    }

    $(function(){
    	blurValiate();
    	//保存并发布
    	$(".main-container").on("click","#saveAndPublish",function(){

    		if(!validateBanners()){
    			return false;
    		}
    		var banners = [];
    		banners = buildBanners();
	   		 $.ajax({
	   			 type:"POST",
	   			 url:"../adSpots/save",
	   			 data:JSON.stringify(banners),
	   			 contentType:"Application/Json",
	   			 success:function(result){
	   				 if(result.res_code == "success"){
	   					  clickActionPopup("save-success");
	   				 }else{
	   					  clickActionPopup("save-failed");
	   				 }
	   			 },
	   			 error: function(XMLHttpRequest, textStatus, errorThrown) {
	   				  clickActionPopup("save-failed");
	   			 }
	   		 })
    	})
    	//预览
		$(".main-container").on("click","#preScan",function(){
			var imageIds=[];
			imageIds = getImageIds();
//			var url = basepath +"/adSpots/preView?imageIds="+imageIds;
//			 window.open(url);
			var url = basepath +"/adSpots/preView";
			   $("#preViewForm").attr("action", url);
		       $("#preViewForm").attr("method", "post");
		       $("#preViewForm").attr("target", "newWin");
			   $("#imageIds").val(imageIds);
			window.open('','newWin','');
			$("#preViewForm").submit();

    	})
    })

    function getImageIds(){
    	var imageIds=[];
    	$(".main-container .instantUpload .uploadImg").each(function(){
    		var picUrl = $(this).attr("src");
    		if(picUrl != null && picUrl !=""){
    			picUrl = picUrl.trim();
    			var imageIdIndex = picUrl.trim().indexOf("=")+1;
    			var imageId = picUrl.substring(imageIdIndex);
    			if(imageId != null && imageId != ""){
    				imageIds.push(imageId);
    			}
    		}
    	})
    	return imageIds;
    }

    function buildBanners(){
    	var banners =[];
    	$(".material-info-item.clearfix").not(".copyTemplate").each(function(){
    		var adSpotEntity={}
    		adSpotEntity.priority = $(this).find(".number.commonStyle").html();
    		adSpotEntity.title = $(this).find("[name='title']").val();
    		adSpotEntity.url =$(this).find("[name='url']").val();
    		adSpotEntity.imageId =$(this).find("[name-value='imageId']").val();
    		banners.push(adSpotEntity);
    	})
    	return banners;
    }

    function validateBanners(){
    	var result = true;
    	try{
    		//标题
    		$(".material-info-item.clearfix").not(".copyTemplate").find("input[name='title']").each(function(){
    			  var title = $(this).val();
    				var title = $(this).val();
    	    		if(title == null || title ==""){
    	    			  clickActionPopup("bannerTitleTip");
    	    			  throw "title error";
    	    		}
    	    		if(title != null && title != ""){
    	    			title = title.trim();
    	    			if(!bannerTitleRegx.test(title) || title.length>50 || title.length<=0){
    	    				  clickActionPopup("bannerTitleTip");
    	    				  throw "title error ";
    	    			}
    	    		}
    		})

    		//url
			$(".material-info-item.clearfix").not(".copyTemplate").find("input[name='url']").each(function(){
				var url = $(this).val();
	    		if(url == null || url ==""){
	    			  clickActionPopup("urlTip");
	    			  throw "url error";
	    		}
	    		if(url != null && url != ""){
	    			url = url.trim();
	    			var reg = new RegExp(urlRegx);
	    			if(!reg.test(url) || url.length>200 || url.length<=0){
	    				  clickActionPopup("urlTip");
	    				  throw "url error";
	    			}
	    		}

    		})

    		//banner
			$(".material-info-item.clearfix").not(".copyTemplate").find("input[name-value='imageId']").each(function(){
				var imageId = $(this).val();
	    		if(imageId == null || imageId ==""){
	    			  clickActionPopup("imageTip");
	    			  throw "picture error";
	    		}
    		})
    	}catch(e){
		 result =false;
		 return result;
	   }
    	return result;
    }

    function blurValiate(){
    	$(".main-container").on("blur","input[name='title']",function(){
    		var title = $(this).val();
    		if(title == null || title ==""){
    			  clickActionPopup("bannerTitleTip");
    		}
    		if(title != null && title != ""){
    			title = title.trim();
    			$(this).val(title.replace(/</g,"&lt").replace(/>/g,""));
    			if(!bannerTitleRegx.test(title) || title.length>50 || title.length<=0){
    				  clickActionPopup("bannerTitleTip");
    			}
    		}
    	})

     	$(".main-container").on("blur","input[name='url']",function(){
    		var url = $(this).val();
    		if(url == null || url ==""){
    			  clickActionPopup("urlTip");
    		}
    		if(url != null && url != ""){
    			url = url.trim();
    			$(this).val(url.replace(/</g,"&lt").replace(/>/g,""));
    			var reg = new RegExp(urlRegx);
    			if(!reg.test(url) || url.length>200 || url.length<=0){
    				  clickActionPopup("urlTip");
    			}

    		}

    	})

    }