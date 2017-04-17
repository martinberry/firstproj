var topicTitleReg = /^[\d\D]{1,100}$/;
var topicTitleErrorHint = "话题标题为1-100个字符";

var giftTitleReg= /^[\d\D]{1,100}$/;
var giftTitleErrorHint = "礼品标题为1-100个字符";

var topicContentReg= /^[\d\D]{1,500}$/;
var topicContentErrorHint = "话题内容为1-500个字符";

var relatedProductErrorHint ="请选择关联产品";

var giftImageErrorHint ="请上传礼品图片";

var giftContentReg=/^[\d\D]{1,500}$/;
var giftContentErrorHint ="礼品内容为1-500个字符";

var deletelock=0; 
var deleteobj={};


 

$(function(){
	
	
	var imageHtml=
		'<div class="instantUpload upload">'+'<form>'+'</form>'
		+'<form name="pictureform" action="'+basepath+'/product/back/hotel/uploadPicture" method="post" enctype="multipart/form-data">'
		+'<input type ="hidden" name-value="pictureId" class="giftImage"/>'
		+'<input type="file" class="instantUploadFile" name ="picture" />'
		+'<span class="instantUploadStyle changeImgWidth">'
		+'<span class="instantUploadIcon">'
		+'</span>'
		+'<span style="padding-top: 47px;">立即上传</span>'
		+'</span>'
		+'</form>'
		+'</div>'
	
	submitFunc();
	////文件引入问题追加的
        $(".checkboxContent label").click(function(){
            if($(".checkboxContent label").hasClass("active")){
            	$(".checkboxContent label").removeClass("active");
            	//$(".giftTitle").val("");
            	//$(".giftImage").parents(".upload").replaceWith(imageHtml);
            	//$(".giftContent").text("");
                $(".giftBox").hide();
            }else{
            	$(".checkboxContent label").addClass("active");
                $(".giftBox").show();
            }
        });
        
        $(".dropdownBtn").click(function(){
        	if($(this).attr("aria-expanded")=="false"){
        		$(this).attr("aria-expanded",true);
        		$(this).parents(".dropdown").addClass("open");        		
        	}else{
        		$(this).attr("aria-expanded",false);
        		$(this).parents(".dropdown").removeClass("open");
        	}
        });
        
        
        topicdetailGiftshow();

      
        
 //保存       
        $(".save").click(function(){
          //var topicTitle=$(".topicTitle").val().trim();
          var status=$("input[name='status']").val();
          if(status=='RELEASED'){
        	  alert("话题已发布，无法保存，请直接发布");
              return false;
          }
          
          if(!saveValid()){
        	  if($(".checkboxContent label").hasClass("active")){
      			topicTitleErrorNotify();
      			topicSaveContentErrorNotify();      			
      			giftSaveTitleErrorNotify();
      			giftSaveContentErrorNotify();      			 			
      		}else{
      			topicTitleErrorNotify();       			
      			topicSaveContentErrorNotify(); 
      		 
      		}         	  
          }
          
          if(!$(".checkboxContent label").hasClass("active")){
      		$(".giftTitle").val("");
      		$(".giftImage").parents(".upload").replaceWith(imageHtml);
           	$(".giftContent").text("");
      	}
          
          if(saveValid()){
        	  var criteria = $("form[name!='commentcondition']").serializeObject();   
        	  
        		  if($("div.dropdown").find("ul li.active a").text()!="选择关联上线产品"){
        			  criteria.productTitle=$("div.dropdown").find("ul li.active a").siblings("input[name='productTitle']").val();
        			  criteria.pid=$("div.dropdown").find("ul li.active a").siblings("input[name='pid']").val();
        		  }else{
        			  criteria.productTitle=null;
        			  criteria.pid=null;        			  
        		  }
        		  
        		  if(criteria.topicTitle!=null && criteria.topicTitle!=""){
        			  criteria.topicTitle=criteria.topicTitle.trim(); 
        		  }
        		  if(criteria.topicContent!=null && criteria.topicContent!=""){
        			  criteria.topicContent=criteria.topicContent.trim(); 
        		  }
        		  if(criteria.giftTitle!=null && criteria.giftTitle!=""){
        			  criteria.giftTitle=criteria.giftTitle.trim(); 
        		  }
        		  if(criteria.giftContent!=null && criteria.giftContent!=""){
        			  criteria.giftContent=criteria.giftContent.trim(); 
        		  }
       	  
      		$.ajax({
    			type : "POST",
    			url : basepath + "/weixinTopic/save",
    			data : JSON.stringify(criteria),
    			headers : {
    				'Accept' : 'application/json',
    				'Content-Type' : 'application/json'
    			},
    			success : function(data) {
    		    if(data.res_code=="saveSuccess"){
    		    	$(".topicId").val(data.res_msg);
    		    	var topicId=$(".topicId").val();
    		    	window.location.href=basepath+"/weixinTopic/topicDetail/"+topicId;
    		    	
    		    	
    		    	
    		    	alert("保存成功");
    		    }else{
    		    	alert("保存失败");
    		    }
    													
    			}
    		});
          }
       	
        });
       
 //发布
        $(".released").click(function(){  
        	if(!releasedValid()){
        		if($(".checkboxContent label").hasClass("active")){
        			topicTitleErrorNotify();
        			topicContentErrorNotify();
        			relatedProductErrorNotify();
        			giftTitleErrorNotify();
        			giftContentErrorNotify();
        			giftImageErrorNotify();       			
        		}else{
        			topicTitleErrorNotify();       			
        			topicContentErrorNotify();
        			relatedProductErrorNotify();
        		}
        	}
        	
        	if(!$(".checkboxContent label").hasClass("active")){
        		$(".giftTitle").val("");
        		$(".giftImage").parents(".upload").replaceWith(imageHtml);
             	$(".giftContent").text("");
        	}
        	
        	
            if(releasedValid()){
          	  var criteria = $("form[name!='commentcondition']").serializeObject();        	 
          		  if($("div.dropdown").find("ul li.active a").text()!="选择关联上线产品"){
          			criteria.productTitle=$("div.dropdown").find("ul li.active a").siblings("input[name='productTitle']").val();
          			criteria.pid=$("div.dropdown").find("ul li.active a").siblings("input[name='pid']").val();
          		  }
          		 if(criteria.topicTitle!=null && criteria.topicTitle!=""){
       			  criteria.topicTitle=criteria.topicTitle.trim(); 
       		  }
       		  if(criteria.topicContent!=null && criteria.topicContent!=""){
       			  criteria.topicContent=criteria.topicContent.trim(); 
       		  }
       		  if(criteria.giftTitle!=null && criteria.giftTitle!=""){
       			  criteria.giftTitle=criteria.giftTitle.trim(); 
       		  }
       		  if(criteria.giftContent!=null && criteria.giftContent!=""){
       			  criteria.giftContent=criteria.giftContent.trim(); 
       		  }
         	  
        		$.ajax({
      			type : "POST",
      			url : basepath + "/weixinTopic/released",
      			data : JSON.stringify(criteria),
      			headers : {
      				'Accept' : 'application/json',
      				'Content-Type' : 'application/json'
      			},
      			success : function(data) {
      		    if(data.res_code=="releasedSuccess"){
      		    	$(".topicId").val(data.res_msg);
      		    	$("input[name='status']").val("RELEASED");
      		    	alert("发布成功");
      		    	window.location.href=basepath+"/weixinTopic/index";
      		    }else{
      		    	alert("发布失败");
      		    }
      													
      			}
      		});
            }
         	
          });
        
  //删除评论
        initdelete();
        $("#deleteComment").delegate(".deleteConfirm","click",function(){
        	if(deletelock==0){
        		deletelock=1;  
        	var criteria={};
        	criteria.commentId=deleteobj.parents("tr").find("input[name='commentId']").val(); 	
        	$.ajax({
    			type : "POST",
    			url : basepath + "/weixinTopic/deleteComment",
    			data : JSON.stringify(criteria),
    			headers : {
    				'Accept' : 'application/json',
    				'Content-Type' : 'application/json'
    			},
    			success : function(data) {
                   if(data.res_code=="deleteSuccess"){
                	   $("#deleteComment").modal("hide");
                	   window.location.href=window.location.href;	
                	
                   }else{
                	   alert("删除操作失败");
                   }
                   deletelock=0;
    													
    			}
    		});
          }       	
        });
        
        
        
        //按点赞排序
        $(".praisesort").click(function(){
        	$("input[name='pageNo']").val("1");
       	    $("input[name='pageSize']").val("10");
    		$("input[name='sortType']").val("praisenum");
    	    submitFunc();
       	    
        });
        
        //按评论时间排序
        $(".commentsort").click(function(){
        	$("input[name='pageNo']").val("1");
       	    $("input[name='pageSize']").val("10");
    		$("input[name='sortType']").val("time");
    	    submitFunc();
       	    
        });
        
        if($(".topicId").val()!=''){
        	$(".newtopic").html("话题");
        }
             
        
})

//初始化删除评论
function initdelete(){
	$(".commonTab").delegate(".delete","click",function(){
		deleteobj=$(this);
		$("#deleteComment").modal("show");
	})
}


function submitFunc(){
	var criteria = $("form[name='commentcondition']").serializeObject();
	criteria.sortType=$("input[name='sortType']").val();
	criteria.topicId=$("input[name='topicId']").val();
		$.ajax({
			type : "POST",
			url : basepath + "/weixinTopic/topicComment",
			data : JSON.stringify(criteria),
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			dataType : "html",
			success : function(result) {
				var resultArray = result.split("<-split->");
				var data = resultArray[0];
				var pagination = resultArray[1];
				$(".commonTab tbody").html(data);
				$("#pagination").html(pagination);
													
			},
		});
		
}

function topicdetailGiftshow(){
	  if($(".giftTitle").val()!=""||$(".giftImage").val()!=""||$(".giftContent").text()!=""){
      	$(".checkboxContent label").addClass("active");
          $(".giftBox").show();
      }
}

function isTopicTitleValid(topicTitle){
	if( topicTitle == "" || !topicTitleReg.test(topicTitle) )
		return false;
	else
		return true;
}


function isTopicContentValid(topicContent){
	if(topicContent == "" || !topicContentReg.test(topicContent))
		return false;
	else
		return true;
}

function isSaveTopicContentValid(topicContent){
	if(topicContent==""){
		return true;
	}else if(!topicContentReg.test(topicContent)){
		return false;
	}else{
		return true;
	}
}

function isRelatedProductValid(relatedProduct){
	if(relatedProduct == "选择关联上线产品")
		return false;
	else 
		return true;
}

function isGiftTitleValid(giftTitle){
	if(giftTitle =="" || !giftTitleReg.test(giftTitle))
		return false;
	else
		return true;
}


function isSaveGiftTitleValid(giftTitle){
	if(giftTitle==""){
		return true;
	}else if(!giftTitleReg.test(giftTitle)){
		return false;
	}else{
		return true;
	}
}


function isGiftImageValid(giftImage){
	if(giftImage=="")
		return false;
	else
		return true;
}

function isGiftContentValid(giftContent){
	if(giftContent=="" || !giftContentReg.test(giftContent))
		return false;
	else 
		return true;
	
}

function isSaveGiftContentValid(giftContent){
	if(giftContent==""){
		return true;
	}else if(!giftContentReg.test(giftContent)){
		return false;
	}else{
		return true;
	}
}

//
//function saveValid(){
//	var topicTitle=$(".topicTitle").val().trim();
//	var topicContent=$(".topicContent").val().trim();
//	var topicTitleErrorhObj=$(".topicTitle").siblings(".verifyStyle").find(".verifyFonts");
//	if(isTopicTitleValid(topicTitle)){
//		hideErrorHint(topicTitleErrorhObj);	
//		return true;
//	}else{	
//		topicTitleErrorhObj.text(topicTitleErrorHint);
//		showErrorHint(topicTitleErrorhObj);
//		return false;
//	}
//}


function releasedValid(){
    var topicTitle=$(".topicTitle").val().trim();
    var topicContent=$(".topicContent").val().trim();
    var relatedProduct=$(".dropdown").find("ul li.active a").html();
    var giftTitle=$(".giftTitle").val().trim();
    var giftImage=$(".giftImage").val().trim();
    var giftContent=$(".giftContent").val().trim();
    
    if($(".checkboxContent label").hasClass("active")){
    	if(isTopicTitleValid(topicTitle)&&isTopicContentValid(topicContent)&&isRelatedProductValid(relatedProduct)&&isGiftTitleValid(giftTitle)&&isGiftImageValid(giftImage)&&isGiftContentValid(giftContent))
    		return true;
    	else{
        	return false;  
    	}
      	
    }else{
    	if(isTopicTitleValid(topicTitle)&&isTopicContentValid(topicContent)&&isRelatedProductValid(relatedProduct))
    		return true;
    	else{
        	return false;
    	}
    	
    }
}

function saveValid(){
	   var topicTitle=$(".topicTitle").val().trim();
	    var topicContent=$(".topicContent").val().trim();
	    var relatedProduct=$(".dropdown").find("ul li.active a").html();
	    var giftTitle=$(".giftTitle").val().trim();
	    var giftImage=$(".giftImage").val().trim();
	    var giftContent=$(".giftContent").val().trim();
	    if($(".checkboxContent label").hasClass("active")){
	    	if(isTopicTitleValid(topicTitle)&&isSaveTopicContentValid(topicContent)&&isSaveGiftTitleValid(giftTitle)&&isSaveGiftContentValid(giftContent))
	    		return true;
	    	else{
	        	return false;  
	    	}
	      	
	    }else{
	    	if(isTopicTitleValid(topicTitle)&&isSaveTopicContentValid(topicContent))
	    		return true;
	    	else{
	        	return false;
	    	}
	    	
	    }
	    
	    
}




function topicTitleErrorNotify(){
	var obj=$(".topicTitle").siblings(".verifyStyle").find(".verifyFonts");	
	var topicTitle =$(".topicTitle").val().trim();
		if( !isTopicTitleValid(topicTitle)){
			$(".topicTitle").siblings(".verifyStyle").find(".verifyFonts").text(topicTitleErrorHint);
			showErrorHint(obj);
		}else{
			hideErrorHint(obj);
		}	
}

function topicContentErrorNotify(){
	    var obj=$(".topicContent").siblings(".verifyStyle").find(".verifyFonts");
		var topicContent = $(".topicContent").val().trim();
		if( !isTopicContentValid(topicContent)){
			$(".topicContent").siblings(".verifyStyle").find(".verifyFonts").text(topicContentErrorHint);			
			showErrorHint(obj);
		}else{
			hideErrorHint(obj);
		}
	
}

function topicSaveContentErrorNotify(){
    var obj=$(".topicContent").siblings(".verifyStyle").find(".verifyFonts");
	var topicContent = $(".topicContent").val().trim();
	if( !isSaveTopicContentValid(topicContent)){
		$(".topicContent").siblings(".verifyStyle").find(".verifyFonts").text(topicContentErrorHint);			
		showErrorHint(obj);
	}else{
		hideErrorHint(obj);
	}

}






function relatedProductErrorNotify(){
	var obj=$("#relatedProduct").siblings(".verifyStyle").find(".verifyFonts");	
		if($(".dropdown").find("ul li.active a").html()=="选择关联上线产品"){
			$("#relatedProduct").siblings(".verifyStyle").find(".verifyFonts").text(relatedProductErrorHint);
			showErrorHint(obj);
		}else{
			hideErrorHint(obj);
		}

}

function giftTitleErrorNotify(){
	var obj=$(".giftTitle").siblings(".verifyStyle").find(".verifyFonts");	
		var giftTitle = $(".giftTitle").val().trim();
		if( !isGiftTitleValid(giftTitle)){
			$(".giftTitle").siblings(".verifyStyle").find(".verifyFonts").text(giftTitleErrorHint);
			showErrorHint(obj);
		}else{
			hideErrorHint(obj);
		}
}



function giftSaveTitleErrorNotify(){
	var obj=$(".giftTitle").siblings(".verifyStyle").find(".verifyFonts");	
		var giftTitle = $(".giftTitle").val().trim();
		if( !isSaveGiftTitleValid(giftTitle)){
			$(".giftTitle").siblings(".verifyStyle").find(".verifyFonts").text(giftTitleErrorHint);
			showErrorHint(obj);
		}else{
			hideErrorHint(obj);
		}
}


function giftContentErrorNotify(){
	var obj=$(".giftContent").siblings(".verifyStyle").find(".verifyFonts");	
		var giftContent = $(".giftContent").val().trim();
		if( !isGiftContentValid(giftContent)){
			$(".giftContent").siblings(".verifyStyle").find(".verifyFonts").text(giftContentErrorHint);
			showErrorHint(obj);
		}else{
			hideErrorHint(obj);
		}

}


function giftSaveContentErrorNotify(){
	var obj=$(".giftContent").siblings(".verifyStyle").find(".verifyFonts");	
		var giftContent = $(".giftContent").val().trim();
		if( !isSaveGiftContentValid(giftContent)){
			$(".giftContent").siblings(".verifyStyle").find(".verifyFonts").text(giftContentErrorHint);
			showErrorHint(obj);
		}else{
			hideErrorHint(obj);
		}

}

function giftImageErrorNotify(){
	var obj=$(".upload").siblings(".verifyStyle").find(".verifyFonts");	
		var giftImage = $(".giftImage").val().trim();
		if( !isGiftImageValid(giftImage)){
			$(".upload").siblings(".verifyStyle").find(".verifyFonts").text(giftImageErrorHint);
			showErrorHint(obj);
		}else{
			hideErrorHint(obj);
		}

}



function showErrorHint(inputer){
	$(inputer).addClass("verifyBox");
	$(inputer).parents(".verifyStyle").show();
}

function hideErrorHint(inputer){
	$(inputer).removeClass("verifyBox");
	$(inputer).parents(".verifyStyle").hide();
}

function trim(selector){
	return $(selector).val($(selector).val().replace(/\ +/g,"")).val() ;
}

$.fn.serializeObject = function(){
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

    
  