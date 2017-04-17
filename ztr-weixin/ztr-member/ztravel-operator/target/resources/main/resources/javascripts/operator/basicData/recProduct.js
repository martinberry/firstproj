var mainTitleReg =    /^[^/^{^}^^^*^>^\\]{0,18}$/;
var viceTitleReg=  /^[^/^{^}^^^*^>^\\]{0,20}$/;
var productIdReg=/^[0-9a-zA-Z]{0,10}$/;

$(function(){
//初始显示

showFunc();

//添加推荐产品组
    $(".addContentItem").click(function(){
    	var groupnum=$(".material-info-list-container .material-info-item").length;
    	if(groupnum<5){
        var TemplateHtml =$(".material-info-item-addgroup .groupListContent").html()+$(".material-info-item-addgroup .groupListContent").html()
		+$(".material-info-item-addgroup .groupListContent").html();
        var titleHtml=$(".material-info-item-addgroup .groupTitle").html();
        $(".material-info-list-container").append("<div class='material-info-item changeWidth clearfix'>"+"<div class='groupTitle clearfix'>"+titleHtml+"</div>"+"<div class='groupListContent'>"+TemplateHtml+"</div>"+"</div>");
        getOrderNum();
        getGroupNum();


 //拖拽效果
        $(".material-info-item .groupListContent").sortable({
            placeholder: "material-tr-sortable-placeholder",
            stop: function(){ // 拖拽效果结束时，重新生成排序数字
                getOrderNum();
            }
        });

    }else{
    	clickActionPopup("groupMaxLengthTip");
    }

});


  //删除组并二次弹窗确认

$("body").delegate(".material-info-item .deleteBtn","click",function(){
	var num=$(this).parents(".groupTitle").find(".groupNum").html();
	$("#delgrpriority").val(num);
	 $("#deletegroupWindow").modal("show");
});

//confirm group
$("body").delegate(".confirm","click",function(){
	 var nums=$("#delgrpriority").val();
	 $("span:contains('"+nums+"').groupNum").each(function(){
		 $(this).parents(".groupTitle").parents(".material-info-item").remove();
	 })
	 getGroupNum();
	 $("#deletegroupWindow").modal("hide");

 });








//	清空内容
$("body").delegate(".material-info-item .clearContent", "click", function(){
	var num=$(this).parents(".groupList").find(".number.commonStyle").html();
	var grnum=$(this).parents(".groupList").parents(".groupListContent").siblings(".groupTitle").find(".groupNum").html();
	$("#deldepriority").val(num);
	$("#delgrpriority").val(grnum);
	var mainTitle=$(this).parents(".commonStyle").siblings(".row02").find(".mainTitle").val();
	var viceTitle=$(this).parents(".commonStyle").siblings(".row03").find(".viceTitle").val();
	var productId=$(this).parents(".commonStyle").siblings(".row04").find(".productId").val();
	var pictureId=$(this).parents(".commonStyle").siblings(".row05").find("[name-value='pictureId']").val();

	if((mainTitle==null||mainTitle=="")&&(viceTitle==null||viceTitle=="")&&(productId==null||productId=="")&&(pictureId==null||pictureId==""))
	{
	}else{
		 $("#deletedetailWindow").modal("show");
	}
});

//confirm detail
$("body").delegate(".confirmd","click",function(){
	var nums=$("#deldepriority").val();
    var grnums=$("#delgrpriority").val();

    $("span:contains('"+grnums+"').groupNum").each(function(){
	 $(this).parents(".groupTitle").siblings(".groupListContent").find("div:contains('"+nums+"').number.commonStyle").each(function(){
		 var clearUpLoad = $("#instantUploadTemp").html();
		 var $row05 = $(this).siblings(".commonStyle").prev(".row05");
		 $row05.removeClass("upLoadProductImg").addClass("instantUpload");
		 $(this).siblings(".commonStyle").siblings(".row02,.row03,.row04").find("input").val("");
		 $(this).siblings(".commonStyle").siblings(".row05").html(clearUpLoad);
		 $("#deletedetailWindow").modal("hide");
	 })
    })

});






blurvalidate();




//保存
$(".save").click(function(){
var groupnum=$(".material-info-list-container").find(".material-info-item").size();
if(groupnum==0){
	clickActionPopup("group-fail");
	return false;
}

	/////整体校验
if(!validateAll()){
	return false;
}

///校验是否有产品Id,若有保存

searchProductIds();
////



})


//预览
$(".main-container").on("click","#preview",function(){
	var groupnum=$(".material-info-list-container").find(".material-info-item").size();
	if(groupnum==0){
		clickActionPopup("group-fail");
		return false;
	}
	if(!validateAll()){
		return false;
	}
	window.open('','newWin','');
	searchProductId();
})

})




//检验产品Id,若有提交表单预览
function searchProductId(){
	var productIds=buildProductId();
	$.ajax({
		type:"POST",
		url:basepath+"/recProduct/searchproductId",
		data : JSON.stringify(productIds),
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		dataType : "json",
		success : function(data) {
			if(data.res_code=="empty"||data.res_code=="fail"){
				clickActionPopup("searchId-fail");
				window.open('error','newWin');
		        return false;
			}
			else{
				// window.open('','newWin','');
				var recproductlist=[];
			       recproductlist=buildRecproduct();
			       var recproductlists=JSON.stringify(recproductlist);
			       var url = basepath +"/recProduct/preview";

			       $("#previewform").attr("action", url);
			       $("#previewform").attr("method", "post");
			       $("#previewform").attr("target", "newWin");
				   $("#recproductlist").val(recproductlists);

				$("#previewform").submit();
			}
		}
	});
}






///校验是否有产品Id,若有保存
function searchProductIds(){
	var productIds=buildProductId();
	$.ajax({
		type:"POST",
		url:basepath+"/recProduct/searchproductId",
		data : JSON.stringify(productIds),
		headers : {
			'Accept' : 'application/json',
			'Content-Type' : 'application/json'
		},
		dataType : "json",
		success : function(data) {
			if(data.res_code=="empty"||data.res_code=="fail"){
				clickActionPopup("searchId-fail");
		        return false;
			}
			else{
				var recproduct = buildRecproduct();
				$.ajax({
					type : "POST",
					url : basepath + "/recProduct/save",
					data : JSON.stringify(recproduct),
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					dataType : "json",
					success : function(data) {
				       if(data.res_code=="success"){
				    	   clickActionPopup("save-success");
				    	   return true;
				       }
				       else{
				    	   clickActionPopup("save-failed");
				    	   return false;
				       }
					}
				});

			}
		}
	});
}




function buildProductId(){
	var array=[];
	$(".material-info-list-container .material-info-item").each(function(){
		$(this).find(".groupListContent").find(".groupList").each(function(){
			var RecProductEntity={}
			RecProductEntity.productId=$(this).find("[name='productId']").val();
			array.push(RecProductEntity);
		})
	})
	return array;
}



function buildRecproduct(){
	var recproduct =[];
	$(".material-info-list-container .material-info-item").each(function(){
		$(this).find(".groupListContent").find(".groupList").each(function(){
			var RecProductEntity={}
			RecProductEntity.priority = $(this).find(".number.commonStyle").html();
			RecProductEntity.mainTitle = $(this).find("[name='mainTitle']").val();
			RecProductEntity.viceTitle =$(this).find("[name='viceTitle']").val();
			RecProductEntity.productId =$(this).find("[name='productId']").val();
			RecProductEntity.pictureId =$(this).find("[name-value='pictureId']").val();
			recproduct.push(RecProductEntity);
		})


	})
	return recproduct;
}







function ismainTitleValid(mainTitle){
	if( mainTitle != "" && !mainTitleReg.test(mainTitle) )
		return false;
	else
		return true;
}

function isviceTitleValid(viceTitle){
	if( viceTitle != "" && !viceTitleReg.test(viceTitle) )
		return false;
	else
		return true;
}



function isproductIdValid(productId){
	if( productId != "" && !productIdReg.test(productId) )
		return false;
	else
		return true;
}

//计算模块排序
function getOrderNum() {
    $(".material-info-item .groupList").each(function(index){
        $(this).find(".number").html(index%3 + 1);
    });
}
//计算组排序
function getGroupNum() {
    $(".material-info-item").each(function(index){
        $(this).find(".groupNum").html(index%5 + 1);
    });
}

function buildRecproduct(){
	var recproduct =[];
	$(".material-info-list-container .material-info-item").each(function(){
		$(this).find(".groupListContent").find(".groupList").each(function(){
			var RecProductEntity={}
			RecProductEntity.priority = $(this).find(".number.commonStyle").html();
			RecProductEntity.mainTitle = $(this).find("[name='mainTitle']").val();
			RecProductEntity.viceTitle =$(this).find("[name='viceTitle']").val();
			RecProductEntity.productId =$(this).find("[name='productId']").val();
			RecProductEntity.pictureId =$(this).find("[name-value='pictureId']").val();
			recproduct.push(RecProductEntity);
		})


	})
	return recproduct;
}


//edit by zpf
function showFunc(){
	var firstTemplateHtml='';
	var time = 0;
       for(var i=0;i< $(".material-info-item-showgroup .groupList").length;i++){
    	   if((i)%3 != 0 || i == 0){
    		   var templateHtml= "<div class='groupList clearfix'>"+$(".material-info-item-showgroup .groupList").eq(i%3 + time*3).html()+"</div>";
    		   firstTemplateHtml=firstTemplateHtml+templateHtml;
    		   if(i == $(".material-info-item-showgroup .groupList").length - 1){//最后一组
    			   var titleHtml= "<div class='groupTitle clearfix'>"+$(".material-info-item-showgroup .groupTitle").html()+"</div>";
        		   $(".material-info-list-container").append("<div class='material-info-item changeWidth clearfix'>"+titleHtml+"<div class='groupListContent'>"+firstTemplateHtml+"</div>"+"</div>");
        		   getOrderNum();
        		   getGroupNum();
    		   }
    	   }else{
    		   var titleHtml= "<div class='groupTitle clearfix'>"+$(".material-info-item-showgroup .groupTitle").html()+"</div>";
    		   $(".material-info-list-container").append("<div class='material-info-item changeWidth clearfix'>"+titleHtml+"<div class='groupListContent'>"+firstTemplateHtml+"</div>"+"</div>");
    		   getOrderNum();
    		   getGroupNum();
    		   firstTemplateHtml = "<div class='groupList clearfix'>"+$(".material-info-item-showgroup .groupList").eq(i).html()+"</div>";
    		   time ++;
    		   continue;
    	   }
       }
       
       $(".material-info-item .groupListContent").sortable({
            placeholder: "material-tr-sortable-placeholder",
            stop: function(){ // 拖拽效果结束时，重新生成排序数字
                getOrderNum();
            }
        });
}



//function showFunc(){
//if ($(".material-info-item-showgroup .groupList").length==6)
//				{
//				//var firstTemplateHtml;
//             //  for(var i=0;i<3;i++){
//            	var   div1firstTemplateHtml= "<div class='groupList clearfix'>"+$(".material-info-item-showgroup .groupList").eq(0).html()+"</div>";
//            	var   div2firstTemplateHtml= "<div class='groupList clearfix'>"+$(".material-info-item-showgroup .groupList").eq(1).html()+"</div>";
//            	var   div3firstTemplateHtml= "<div class='groupList clearfix'>"+$(".material-info-item-showgroup .groupList").eq(2).html()+"</div>";
//              // }
//            	var   firstTemplateHtml=div1firstTemplateHtml+div2firstTemplateHtml+div3firstTemplateHtml;
//            var titleHtml= "<div class='groupTitle clearfix'>"+$(".material-info-item-showgroup .groupTitle").html()+"</div>";
//            $(".material-info-list-container").append("<div class='material-info-item changeWidth clearfix'>"+titleHtml+"<div class='groupListContent'>"+firstTemplateHtml+"</div>"+"</div>");
//        	getOrderNum();
//			getGroupNum();
//
//
//
//				//var secondTemplateHtml;
//               //for(var i=3;i<6;i++){
//            	 var div1secondTemplateHtml= "<div class='groupList clearfix'>"+$(".material-info-item-showgroup .groupList").eq(3).html()+"</div>";
//             	 var div2secondTemplateHtml= "<div class='groupList clearfix'>"+$(".material-info-item-showgroup .groupList").eq(4).html()+"</div>";
//             	 var div3secondTemplateHtml= "<div class='groupList clearfix'>"+$(".material-info-item-showgroup .groupList").eq(5).html()+"</div>";
//             	var secondTemplateHtml=div1secondTemplateHtml+div2secondTemplateHtml+div3secondTemplateHtml;
//               //}
//            var titleHtml= "<div class='groupTitle clearfix'>"+$(".material-info-item-showgroup .groupTitle").html()+"</div>";
//            $(".material-info-list-container").append("<div class='material-info-item changeWidth clearfix'>"+titleHtml+"<div class='groupListContent'>"+secondTemplateHtml+"</div>"+"</div>");
//        	getOrderNum();
//			getGroupNum();
//
//
//		      $(".material-info-item .groupListContent").sortable({
//		            placeholder: "material-tr-sortable-placeholder",
//		            stop: function(){ // 拖拽效果结束时，重新生成排序数字
//		                getOrderNum();
//		            }
//		        });
//
//				}
//			else if($(".material-info-item-showgroup .groupList").length==3){
//				var TemplateHtml="<div class='material-info-item changeWidth clearfix'>"+$(".material-info-item-showgroup").html()+"</div>";
//				$(".material-info-list-container").append(TemplateHtml);
//				getOrderNum();
//				getGroupNum();
//			      $(".material-info-item .groupListContent").sortable({
//			            placeholder: "material-tr-sortable-placeholder",
//			            stop: function(){ // 拖拽效果结束时，重新生成排序数字
//			                getOrderNum();
//			            }
//			        });
//
//			}
//			else{
//				var firstTemplateHtml='';
//				var time = 0;
//	               for(var i=0;i< $(".material-info-item-showgroup .groupList").length;i++){
//	            	   if((i)%3 != 0 || i == 0){
//	            		   var templateHtml= "<div class='groupList clearfix'>"+$(".material-info-item-showgroup .groupList").eq(i%3 + time*3).html()+"</div>";
//	            		   firstTemplateHtml=firstTemplateHtml+templateHtml;
//	            		   if(i == $(".material-info-item-showgroup .groupList").length - 1){
//	            			   var titleHtml= "<div class='groupTitle clearfix'>"+$(".material-info-item-showgroup .groupTitle").html()+"</div>";
//		            		   $(".material-info-list-container").append("<div class='material-info-item changeWidth clearfix'>"+titleHtml+"<div class='groupListContent'>"+firstTemplateHtml+"</div>"+"</div>");
//		            		   getOrderNum();
//		            		   getGroupNum();
//	            		   }
//	            	   }else{
//	            		   var titleHtml= "<div class='groupTitle clearfix'>"+$(".material-info-item-showgroup .groupTitle").html()+"</div>";
//	            		   $(".material-info-list-container").append("<div class='material-info-item changeWidth clearfix'>"+titleHtml+"<div class='groupListContent'>"+firstTemplateHtml+"</div>"+"</div>");
//	            		   getOrderNum();
//	            		   getGroupNum();
//	            		   firstTemplateHtml = "<div class='groupList clearfix'>"+$(".material-info-item-showgroup .groupList").eq(i).html()+"</div>";
//	            		   time ++;
//	            		   continue;
//	            	   }
//	               }
//	               
//	               $(".material-info-item .groupListContent").sortable({
//			            placeholder: "material-tr-sortable-placeholder",
//			            stop: function(){ // 拖拽效果结束时，重新生成排序数字
//			                getOrderNum();
//			            }
//			        });
//			}
//
//}


function blurvalidate(){
///////////////////////校验
$("body").delegate(".material-info-item .mainTitle","blur",function(){
var mainTitle=$(this).val();
mainTitle =mainTitle.trim();
$(this).val(mainTitle);
if(mainTitle!=null&&mainTitle!=""){
if( !ismainTitleValid(mainTitle)){
clickActionPopup("mainTitleTip");
 }
}

});


$("body").delegate(".material-info-item .viceTitle","blur",function(){
var viceTitle=$(this).val();
viceTitle =viceTitle.trim();
$(this).val(viceTitle);
if(viceTitle!=null&&viceTitle!=""){
if( !isviceTitleValid(viceTitle)){
	clickActionPopup("viceTitleTip");
     }
}
});


$("body").delegate(".material-info-item .productId","blur",function(){
var productId=$(this).val();
productId =productId.trim();
$(this).val(productId);
if(productId!=null&&productId!=""){
if( !isproductIdValid(productId)){
	clickActionPopup("productIdTip");
     }
}
});
}

 function validateAll(){
	 var result=true;
try{
	$(".material-info-item.clearfix").find("input[name='mainTitle']").each(function(){
		  var mainTitle = $(this).val();

   		if(mainTitle == null || mainTitle ==""){
   			clickActionPopup("mainTitleTip");
   			throw("主标题有错");

   		}
   		 if(mainTitle != null && mainTitle != ""){
   			mainTitle = mainTitle.trim();
   			if(!mainTitleReg.test(mainTitle)){
   				clickActionPopup("mainTitleTip");
   				throw("主标题有错");
   			}
   		}

	})



	 $(".material-info-item.clearfix").find("input[name='viceTitle']").each(function(){
		  var viceTitle = $(this).val();

   		if(viceTitle == null || viceTitle ==""){
   			clickActionPopup("viceTitleTip");
   			throw("副标题有错");
   		}
   		if(viceTitle != null && viceTitle != ""){
   			viceTitle = viceTitle.trim();
   			if(!viceTitleReg.test(viceTitle)){
   				clickActionPopup("viceTitleTip");
   				throw("副标题有错");
   			}
   		}
	})


			$(".material-info-item.clearfix").find("input[name='productId']").each(function(){
			  var productId = $(this).val();

	    		if(productId == null || productId ==""){
	    			clickActionPopup("productIdTip");
	    			throw("产品Id有错");
	    		}
	    		 if(productId != null && productId != ""){
	    			productId = productId.trim();
	    			if(!productIdReg.test(productId)){
	    				clickActionPopup("productIdTip");
	    				throw("产品Id有错");
	    			}
	    		}
		})



	$(".material-info-item.clearfix").find("input[name-value='pictureId']").each(function(){
		  var picture = $(this).val();
  		if(picture == null || picture ==""){
  			clickActionPopup("imageTip");
  			throw("上传图片有错");
  		}

	})


}catch(e){
	result =false;
	return result;
}
return result;

 }



