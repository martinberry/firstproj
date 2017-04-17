var topicTitleReg =    /^[\d\D]{0,100}$/;
var topicTitleErrorHint = "请输入正确话题标题";

var ProductTitleReg =    /^[\d\D]{0,100}$/;
var ProductTitleErrorHint = "请输入正确关联产品名称";
var offlinelock=0;
var releasedlock=0;
var deletelock=0;

var deleteobj={};
var releasedobj={};
var offlineobj={};
$(function(){
//	var criteria = $("form").serializeObject();
//	criteria.status = $("div.dropdown").find("ul li.active a").text();
//	criteria.sortType=$("input[name='sortType']").val();
	submitFunc();
	
    $(".leftMenu .menu").click(function(){
    	$(this).parents("li").removeClass("current");
    	$(this).parents("li").siblings("li").addClass("current");
    });
    
    $(".leftMenu .topic").click(function(){
    	$(this).parents("li").removeClass("current");
    	$(this).parents("li").siblings("li").addClass("current");
    });
    
    $("#searchBtn").click(function(){
	    $("input[name='pageNo']").val("1");
	    $("input[name='pageSize']").val("10");
	
  		$("input[name='sortType']").val("time");
    	submitFunc();
    })
   	
	$("#topicTitle").blur(function(){
		var topicTitle = $("#topicTitle").val().trim();
		if( !isTopicTitleValid(topicTitle) ){
			$(this).siblings(".verifyStyle").find(".verifyFonts").text(topicTitleErrorHint);
			showErrorHint(this);
		}else{
			hideErrorHint(this);
		}
	});
	
	$("#productTitle").blur(function(){
		var productTitle = $("#productTitle").val().trim();
		if( !isProductTitleValid(productTitle) ){
			$(this).siblings(".verifyStyle").find(".verifyFonts").text(ProductTitleErrorHint);
			showErrorHint(this);
		}else{
			hideErrorHint(this);
		}
	});
    
	  //初始化列表下线
    initlistoffline();
    $("#listoffline").delegate(".confirmOffline","click",function(){
    	if(offlinelock==0){
    	offlinelock=1;
    	var criteria={}
    	criteria.topicId=offlineobj.parents("tr").find("input[name='topicId']").val();   	
    	$.ajax({
			type : "POST",
			url : basepath + "/weixinTopic/listOffline",
			data : JSON.stringify(criteria),
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			success : function(data) {
               if(data.res_code=="offlineSuccess"){
            	   offlineobj.parents("tr").find(".status").html("关闭");
            	   offlineobj.parents("tr").find(".offline").removeClass("offline").addClass("released").html("上线");
            	   $("#listoffline").modal("hide");
               }else{
            	   alert("下线操作失败");
               }
				offlinelock=0;									
			}
		});
    }
    	
    })
    
     //初始化列表上线
    initlistreleased();
     $("#listreleased").delegate(".confirmReleased","click",function(){  
     	   if(releasedlock==0){
    		   releasedlock=1; 
    	var criteria={}
    	criteria.topicId=releasedobj.parents("tr").find("input[name='topicId']").val();   	
    	$.ajax({
			type : "POST",
			url : basepath + "/weixinTopic/listReleased",
			data : JSON.stringify(criteria),
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			success : function(data) {
               if(data.res_code=="releasedSuccess"){
            	   releasedobj.parents("tr").find(".status").html("上线");
            	   releasedobj.parents("tr").find(".releasedTime").html(data.res_msg);
            	   releasedobj.parents("tr").find(".released").removeClass("released").addClass("offline").html("关闭");
            	   $("#listreleased").modal("hide");
               }else{
            	   alert("上线操作失败");
               }
				releasedlock=0;									
			}
		});
       } 
     })
 
    	
    
    	
    //初始化列表删除
    initlistdelete();
    $("#listdelete").delegate(".confirmDelete","click",function(){   	
    	  if(deletelock==0){
	    		deletelock=1;  
	    	var criteria={}   	    	
	    	criteria.topicId=deleteobj.parents("tr").find("input[name='topicId']").val();   	
	    	$.ajax({
				type : "POST",
				url : basepath + "/weixinTopic/listDelete",
				data : JSON.stringify(criteria),
				headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
				success : function(data) {
	               if(data.res_code=="deleteSuccess"){
	            	   $("#listdelete").modal("hide");
	            	   window.location.href=window.location.href;	
	            	  
	               }else{
	            	   alert("删除操作失败");
	               }
	               deletelock=0;
														
				}
			});
	      }
    	
    })
     
    	  
    	 
    		     
    $(".discuss").click(function(){
    	 $("input[name='pageNo']").val("1");
  	    $("input[name='pageSize']").val("10");
  		$("input[name='sortType']").val("discuss");
      	submitFunc();
    })
    	
    
	
});

//初始化列表删除
   function initlistdelete(){
	   $(".commonTab").delegate(".delete","click",function(){
		 deleteobj=$(this);
		 $("#listdelete").modal("show");
	   })
   }

//初始化列表上线
   function initlistreleased(){
	   $(".commonTab").delegate(".released","click",function(){
			 releasedobj=$(this);
			 $("#listreleased").modal("show");
		   })
   }
   
 //初始化列表下线
   function initlistoffline(){
	   $(".commonTab").delegate(".offline","click",function(){
			 offlineobj=$(this);
			 $("#listoffline").modal("show");
		   })
   }


function submitFunc(){
	var criteria = $("form").serializeObject();
	criteria.topicTitle=$("#topicTitle").val().trim();
	criteria.productTitle=$("#productTitle").val().trim();
	criteria.status = $("div.dropdown").find("ul li.active a").text();
	criteria.sortType=$("input[name='sortType']").val();
	if( isSearchParamsValid(criteria) ){
		$.ajax({
			type : "POST",
			url : basepath + "/weixinTopic/search",
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
				
				$(".topicTitle").each(function(index){
					var topic=$(this).html().replace(new RegExp(" ",'gm'),"&nbsp;")
					$(this).html(topic);	
				})
				
				$(".productTitle").each(function(index){
					var topic=$(this).html().replace(new RegExp(" ",'gm'),"&nbsp;")
					$(this).html(topic);	
				})
			},
		});
	}	
}

function isSearchParamsValid(searchParams){
	if( isTopicTitleValid(searchParams.topicTitle)&&isProductTitleValid(searchParams.productTitle))
		return true;
	else
		return false;
}

function isTopicTitleValid(topicTitle){
	if( topicTitle != "" && !topicTitleReg.test(topicTitle) )
		return false;
	else
		return true;
}

function isProductTitleValid(productTitle){
	if( productTitle != "" && !ProductTitleReg.test(productTitle) )
		return false;
	else
		return true;
}


function showErrorHint(inputer){
	$(inputer).addClass("verifyBox");
	$(inputer).siblings(".verifyStyle").show();
}

function hideErrorHint(inputer){
	$(inputer).removeClass("verifyBox");
	$(inputer).siblings(".verifyStyle").hide();
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

