var titleReg = /^([a-zA-Z0-9|\u4e00-\u9fa5|\.|（|）|【|】|——|,|，|、|。|\/|\\|:|;|：|；|“|”|’]){4,60}$/;
var contentReg = /^([a-zA-Z0-9|\u4e00-\u9fa5|\.|（|）|【|】|—|,|，|、|。|\/|\\|:|;|：|；|“|”|’|\r|\n]){4,1000}$/;
var foodReg = /^([a-zA-Z0-9|\u4e00-\u9fa5|\.|（|）|【|】|——|,|，|、|。|\/|\\|:|;|：|；|“|”|’]){2,40}$/;
var KEDITOR0 = [];
var KEDITOR1= [];

var isReadonly = true;
$(function(){
	var pdd= $('.routeRightContent[name!="overviewTab"]').length;//二维数组总数
	var KEDITOR2 =[];
	for(var k=0;k<pdd;k++){
		   KEDITOR2[k]=[];
	}

	if($('#accessType_hidden').val() == 'edit'){
		isReadonly= false;
	}

	KindEditor.options.items = ['forecolor', 'bold', 'insertorderedlist', 'insertfile','justifyleft', 'justifycenter', 'justifyright'];

	var kindEditorOptions = {
			allowFileManager : false,
    		allowFileUpload : true,
    		uploadJson : operaServer+'/upload/kindFile',
            afterCreate : function() {
                this.loadPlugin('autoheight');
                if(isReadonly)this.readonly();
            },
	};

	kedit(KEDITOR2);

	/*//默认显示两个
	var currentnum=$(".routeRightContent[style!='display: none;'] tbody ul.desItem").length;
	if(currentnum==1){
		//添加空白组
	//	$("script th").text("2组图片说明");
		var templates=$("#pictemp").html();
		$(".routeRightContent[style!='display: none;'] table").append(templates);
		$(".routeRightContent[style!='display: none;'] tbody.flag").eq(1).find("tr").eq(1).find(".verticalTop").html("2组图片说明");
		var indec=$(".routeRightContent[style='display: none;'] textarea.picdec").length-1;
		var addin=$(".routeSideUl li.active").index()-1;
		$(".routeRightContent[style!='display: none;'] textarea.picdec").eq(indec).each(function(){
			var ed = KindEditor.create(this, kindEditorOptions);
			KEDITOR2[addin].push(ed);
		});
	}
*/

	//添加组
	$(".add-btn").click(function(){
		var groupnum=$(".routeRightContent[style!='display: none;'] tbody ul.desItem").length;
		if(groupnum<10){
			var addnum=groupnum+1;
			var addnumdesc=addnum+"组图片说明";
			var template=$("#pictemp").html();
			$(".routeRightContent[style!='display: none;'] table").append(template);
			$(".routeRightContent[style!='display: none;'] tbody.flag").eq(groupnum).find("tr").eq(1).find(".verticalTop").html(addnumdesc);
			var indec=$(".routeRightContent[style!='display: none;'] textarea.picdec").length-1;
			var addin=$(".routeSideUl li.active").index()-1;
			$(".routeRightContent[style!='display: none;'] textarea.picdec").eq(indec).each(function(){
				var ed = KindEditor.create(this, kindEditorOptions);
				KEDITOR2[addin].push(ed);
			});


		}else{
			//不能添加提示
			alert("不能添加超过10组");

		}



	});
	//删除组
	$(".remove-btn").click(function(){
		//var groupnum=$(".routeRightContent[style='display: inline-block;'] tbody ul.desItem").length;
		var groupnum=$(".routeRightContent[style!='display: none;'] tbody ul.desItem").length;
		if(groupnum!=1){
			//var delindex=$(".routeRightContent[style='display: inline-block;'] tbody.flag").length;
			var delindex=$(".routeRightContent[style!='display: none;'] tbody.flag").length;
			//$(".routeRightContent[style='display: inline-block;'] tbody.flag").eq(delindex-1).remove();
			$(".routeRightContent[style!='display: none;'] tbody.flag").eq(delindex-1).remove();
		}else{
			//不能删除提示
           alert("不能删除少于1组");
		}



	});






	bindTabClick(KEDITOR2,kindEditorOptions);
	$('.commonButton.red-fe6869Button.top').click(function(){
		submitForm(false,KEDITOR2);
	});
	$('.commonButton.whiteBtn.top').click(function(){
		submitForm(true,KEDITOR2);
	});
	$('button.commonButton.blue-45c8dcButton.top').click(function(){
		window.location.href = basepath + "/product/recommendTrip/edit/"+$('#productId_hidden').val();
	});
	if($('#accessType_hidden').val() != 'edit'){
		setReadonly();
	}else{
	    //点击概览时动态取tab的值,编辑页才需要
	    $(".routeSideUl li i.overview").parent().click(function(){
	    	var titles = [];
	    	var contents = [];
	    	$('.routeRightContent[name!="overviewTab"]').each(function(){
	    		titles.push($(this).find('input[name="title"]').val());
	    		contents.push($(this).find('textarea[name="content"]').val().substring(0,50));
	    	});
	    	$('.routeRightContent[name="overviewTab"] table tr').each(function(index){
	    		if(index >=titles.length)return;
	    		$(this).find('p.fs16 b').html(titles[index]);
	    		$(this).find('p.fs14').html(contents[index]);
	    	});
	    });
	}
});

//富文本框
function kedit(KEDITOR2){
	KindEditor.ready(function(K) {
       // K.options.items = ['forecolor', 'bold', 'insertorderedlist', 'insertfile'];
        $('textarea.overviewTextarea.editorText').each(function(){
           	var ed = K.create(this, {
           		allowFileManager : false,
           		allowFileUpload : true,
           		uploadJson : operaServer+'/upload/kindFile',
                   afterCreate : function() {
                       this.loadPlugin('autoheight');
                       if(isReadonly)this.readonly();
                   },
               });
           	  KEDITOR0.push(ed);
           });
        $('textarea.tripcontent.editorText').each(function(){
        	var ed = K.create(this, {
        		allowFileManager : false,
        		allowFileUpload : true,
        		uploadJson : operaServer+'/upload/kindFile',
                afterCreate : function() {
                    this.loadPlugin('autoheight');
                    if(isReadonly)this.readonly();
                },
            });
        	  KEDITOR1.push(ed);
        });


 	  $('.routeRightContent[name!="overviewTab"]').each(function(index){
    	   $(this).find('textarea.picdec').each(function(){
           	var ed = K.create(this, {
           		allowFileManager : false,
           		allowFileUpload : true,
           		uploadJson : operaServer+'/upload/kindFile',
                   afterCreate : function() {
                       this.loadPlugin('autoheight');
                       if(isReadonly)this.readonly();
                   },
               });
           	KEDITOR2[index].push(ed);
           });
       })



    });
}




function bindTabClick(KEDITOR2,kindEditorOptions){
	$(".routeSideUl li").each(function(index){
        $(this).click(function(){
            $(".routeRightContent").hide();
            $(".routeRightContent:eq(" + index + ")").show();
            $(".routeSideUl li").removeClass("active");
            $(this).addClass("active");
        	//默认显示两个
        	var currentnum=$(".routeRightContent[style!='display: none;'] tbody ul.desItem").length;
            if(currentnum==0) {
            	var templates=$("#pictemp").html();
            	$(".routeRightContent[style!='display: none;'] table").append(templates);
            	$(".routeRightContent[style!='display: none;'] tbody.flag").find("tr").eq(1).find(".verticalTop").html("1组图片说明");
            	var indec=$(".routeRightContent[style!='display: none;'] textarea.picdec").length-1;
        		var addin=$(".routeSideUl li.active").index()-1;
        		$(".routeRightContent[style!='display: none;'] textarea.picdec").eq(indec).each(function(){
        			var ed = KindEditor.create(this, kindEditorOptions);
        			KEDITOR2[addin].push(ed);
        		});


         		 templates=$("#pictemp").html();
        		$(".routeRightContent[style!='display: none;'] table").append(templates);
        		$(".routeRightContent[style!='display: none;'] tbody.flag").eq(1).find("tr").eq(1).find(".verticalTop").html("2组图片说明");
        		 indec=$(".routeRightContent[style!='display: none;'] textarea.picdec").length-1;
        		 addin=$(".routeSideUl li.active").index()-1;
        		$(".routeRightContent[style!='display: none;'] textarea.picdec").eq(indec).each(function(){
        			 ed = KindEditor.create(this, kindEditorOptions);
        			KEDITOR2[addin].push(ed);
        		});
            }

        	if(currentnum==1){
        		//添加空白组
        	//	$("script th").text("2组图片说明");
        		var templates=$("#pictemp").html();
        		$(".routeRightContent[style!='display: none;'] table").append(templates);
        		$(".routeRightContent[style!='display: none;'] tbody.flag").eq(1).find("tr").eq(1).find(".verticalTop").html("2组图片说明");
        		var indec=$(".routeRightContent[style!='display: none;'] textarea.picdec").length-1;
        		var addin=$(".routeSideUl li.active").index()-1;
        		$(".routeRightContent[style!='display: none;'] textarea.picdec").eq(indec).each(function(){
        			var ed = KindEditor.create(this, kindEditorOptions);
        			KEDITOR2[addin].push(ed);
        		});
        	}


        });
    });

    $(".routeSideUl li i.addDay").each(function(){
    	$(this).parent().unbind('click');
    	$(this).parent().click(function(){
    		createRouteTab();
    	});
    });
}

/*新增一天的行程*/
function createRouteTab(){
	var tripDays = parseInt($('#tripDays_hidden').val());
	var tabNums = $(".routeSideUl li i.routeDay").length;
	if(tabNums >= tripDays){
		alert("总共只有"+tripDays+"行程,请勿再添加！");
		return;
	}
	var dayIndex = $("#routeDayModel").prevAll().length;
	var $leftHtml = $($('#routeDayModel').html());
	$leftHtml.find('i.routeDayModel').attr('class', 'routeDay');
	$leftHtml.find('i.routeDay span').html(dayIndex);
	$('#routeDayModel').before($leftHtml);

	var $rightHtml = $($('#routeRightContentModel').html());
	$rightHtml.attr('class', 'routeRightContent');
	$rightHtml.find('div.routeRightTit span').html(dayIndex);
	$('#routeRightContentModel').before($rightHtml);
	bindTabClick(KEDITOR2,kindEditorOptions);
	$('#routeDayModel').prev().click();
}
/**/
function submitForm(withNext,KEDITOR2){
	var params = buildParams(KEDITOR2);
	if(withNext){
		if(!checkParams(params))return;
		params.withNext = true;
	}else{
		if(!checkBlank(params))return;
		params.withNext = false;
	}
	$.ajax({
	    type: 'POST',
	    url: basepath + '/product/recommendTrip/save' ,
	    data: JSON.stringify(params) ,
	    contentType : 'application/json',
	    dataType: 'json' ,
	    success: function(result){
	    	if(result.res_code == 'success'){
	    		if(withNext === undefined || !withNext){
	    			window.location.href = basepath + "/product/recommendTrip/edit/"+params.id;
	    		}else{
	    			window.location.href = basepath + "/product/additionalInfo/edit/"+params.id;
	    		}
	    	}else{
	    		alert(result.res_msg);
	    	}
	    },
	    error: function(e){
	    	console.log(e);
	    }
	});
}
/**/
function buildParams(KEDITOR2){
	var params = {};
	params.id = $('#productId_hidden').val();
	params.status = $('#status_hidden').val();
	params.progress = parseInt($('#progress_hidden').val());
	params.recommendTrips = [];
	//概览
	$('.routeRightContent[name="overviewTab"]').each(function(){
		var overview = {};
		overview.index = 0;
		//overview.content = $(this).find('textarea[name="content"]').val();
		overview.content =KEDITOR0[0].html();
		overview.desItems = [];
		var item = {};
		item.images = [];
		$(this).find('ul[name="images"] li img').each(function(){
			if($(this).attr('value') != ""){
				item.images.push($(this).attr('value'));
			}
		});
		overview.desItems.push(item);
		params.recommendTrips.push(overview);
	});
	//行程
	$('.routeRightContent[name!="overviewTab"]').each(function(index){
		var outindex=index;
		var trip = {};
		trip.index = parseInt($(this).find('.routeRightTit span').html());
		trip.title = $(this).find('input[name="title"]').val();
		trip.content=KEDITOR1[index].html();
		trip.breakfest = $(this).find('input[name="breakfest"]').val();
		trip.lunch = $(this).find('input[name="lunch"]').val();
		trip.dinner = $(this).find('input[name="dinner"]').val();
		trip.desItems = [];
		var unitnum=$(this).find("tbody.flag").length;
        $(this).find("tbody.flag").each(function(index){
        	var desItem = {};
			desItem.images = [];
			desItem.describe=[];
			$(this).find('ul.desItem li img').each(function(){
				if($(this).attr('value') != ""){
					desItem.images.push($(this).attr('value'));
				}
			});

			if(KEDITOR2[outindex][index].html() !="<br />"){
				desItem.describe=KEDITOR2[outindex][index].html();
			}else{
				desItem.describe="";
			}
			if(unitnum==1){
				trip.desItems.push(desItem);
			}
			else if(desItem.images.length!=0||desItem.describe.length!=0){
				trip.desItems.push(desItem);
			}

        });
		params.recommendTrips.push(trip);
	});
	return params;
}
/*保存/下一步　验证*/
function checkParams(params){
	if(params.id == ''){
		alert("产品ID出错");
		return false;
	}
	if(isNaN(params.progress)){
		alert("产品进度出错");
		return false;
	}
	if(params.recommendTrips.length < parseInt($('#tripDays_hidden').val())+1){
		alert("请完善概览和总共"+$('#tripDays_hidden').val()+"天的推荐行程");
		return false;
	}else{
		//概览
		var overview = params.recommendTrips[0];
		if(strB(overview.content)){
			alert("概览内容为空");
			return false;
		}else if(overview.content.length < 4 || overview.content.length >1000){
			alert("概览内容仅可输入4-1000位字符数以内的中文、字母、数字及常用字符");
			return false;
		}
		if(overview.desItems[0].images === undefined || overview.desItems[0].images.length == 0){
			alert("概览要求上传一张图片");
			return false;
		}
		//行程
		for(var i =1; i <params.recommendTrips.length; i++){
			var trip = params.recommendTrips[i];
			var dayStr = "第"+trip.index+"天";
			if(strB(trip.title)){
				alert(dayStr+"标题为空");
				return false;
			}else if(trip.title.length < 4 || trip.title.length > 60){
				alert(dayStr + "标题仅可输入4-60位字符数以内的中文、字母、数字及常用字符");
				return false;
			}
			if(strB(trip.content)){
				alert(dayStr+"行程内容为空");
				return false;
			}else if(trip.content.length < 4 || trip.content.length > 1000){
				alert(dayStr + "行程内容仅可输入4-1000位字符数以内的中文、字母、数字及常用字符");
				return false;
			}
			if(strNB(trip.breakfest) && (trip.breakfest.length < 1 || trip.breakfest.length > 40)){
				alert(dayStr + "早餐仅可输入1-40位字符数以内的中文、字母、数字及常用字符");
				return false;
			}
			if(strNB(trip.lunch) && (trip.lunch.length < 1 || trip.lunch.length > 40)){
				alert(dayStr + "午餐仅可输入1-40位字符数以内的中文、字母、数字及常用字符");
				return false;
			}
			if(strNB(trip.dinner) && (trip.dinner.length < 1 || trip.dinner.length > 40)){
				alert(dayStr + "晚餐仅可输入1-40位字符数以内的中文、字母、数字及常用字符");
				return false;
			}
			if(trip.desItems){
				var items = trip.desItems;
				for(index in items){
					var item = items[index];
					if(strNB(item.describe) && (item.describe.length <4 || item.describe.length >1000)){
						alert(dayStr+"-第"+(parseInt(index)+1)+"组的图片描述仅可输入4-1000位字符数以内的中文、字母、数字及常用字符");
						return false;
					}
				}
			}
		}
	}
	return true;
}
/*保存　验证*/
function checkBlank(params){
	if(params.id == ''){
		alert("产品ID出错");
		return false;
	}
	if(isNaN(params.progress)){
		alert("产品进度出错");
		return false;
	}
	//概览
	var overview = params.recommendTrips[0];
	if(strNB(overview.content) && (overview.content.length < 4 || overview.content.length >1000)){
		alert("概览内容仅可输入4-1000位字符数以内的中文、字母、数字及常用字符");
		return false;
	}
	//行程
	for(var i =1; i <params.recommendTrips.length; i++){
		var trip = params.recommendTrips[i];
		var dayStr = "第"+trip.index+"天";
		if(strNB(trip.title) && (trip.title.length < 4 || trip.title.length > 60)){
			alert(dayStr + "标题仅可输入4-60位字符数以内的中文、字母、数字及常用字符");
			return false;
		}
		if(strNB(trip.content) && (trip.content.length < 4 || trip.content.length > 1000)){
			alert(dayStr + "行程内容仅可输入4-1000位字符数以内的中文、字母、数字及常用字符");
			return false;
		}
		if(strNB(trip.breakfest) && (trip.breakfest.length < 1 || trip.breakfest.length > 40)){
			alert(dayStr + "早餐仅可输入1-40位字符数以内的中文、字母、数字及常用字符");
			return false;
		}
		if(strNB(trip.lunch) && (trip.lunch.length < 1 || trip.lunch.length > 40)){
			alert(dayStr + "午餐仅可输入1-40位字符数以内的中文、字母、数字及常用字符");
			return false;
		}
		if(strNB(trip.dinner) && (trip.dinner.length < 1 || trip.dinner.length > 40)){
			alert(dayStr + "晚餐仅可输入1-40位字符数以内的中文、字母、数字及常用字符");
			return false;
		}
		if(trip.desItems){
			var items = trip.desItems;
			for(index in items){
				var item = items[index];
				if(strNB(item.describe) && (item.describe.length <4 || item.describe.length >1000)){
					alert(dayStr+"-第"+(parseInt(index)+1)+"组的图片描述仅可输入4-1000位字符数以内的中文、字母、数字及常用字符");
					return false;
				}
			}
		}
	}
	return true;
}

/*上传图片*/
function uploadImg(obj, limitNum){
	var imgNums = $(obj).parents('li').prevAll().length;
	if(limitNum === undefined)limitNum = 4;
	if(imgNums >= limitNum){
		alert("上传图片限制"+ limitNum +"张！");
		$(obj).val("") ;
		return;
	}
	var varAllImgExt="image/jpeg,image/png";//全部图片格式类型
	var varAllImgMaxSize = 2 * 1024 * 1024 ;
	if (obj.files && obj.files[0]){
		if(varAllImgExt.indexOf(obj.files[0].type) == -1){
			alert('非法格式图片') ;
			$(obj).val("") ;
			return ;
		}
		if(obj.files[0].size > varAllImgMaxSize){
			alert('图片大小限制2M') ;
			$(obj).val("") ;
			return ;
		}
	}
	$(obj).parent().ajaxSubmit({
		success: function(data){
	    	if(data.res_code == 'success'){
	    		var imgLi = '<li><img src="'+ mediaserver +'imageservice?mediaImageId='+ data.res_msg +'" value="'+data.res_msg+ '">';
	    		if(limitNum != 1){
	    			imgLi += '<div class="moveLeft"  onclick="moveLeft(this);">左移</div>';
	    			imgLi += '<div class="moveRight"  onclick="moveRight(this);">右移</div>';
	    		}
	    		imgLi += '<div class="hoverDelete" onclick="rmImgSelf(this);"><i class="delIcon"></i>删除</div></li>';
	    		$(obj).parents('li').before(imgLi);
	    		if(limitNum == 1){
	    			$(obj).parents('ul').find('li img').attr('style', 'width:660px;height:440px;margin-left:30px;');
	    		}
	    		if(imgNums+1 >= limitNum){
	    			$(obj).parents('li').css("display", "none");
	    		}
	    		var $li = $(obj).parents('li');
	    		var newForm = $li.find('form').prop('outerHTML');
	    		$li.find('form').remove();
	    		$li.append(newForm);
	    	}else{
	    		alert(data.res_msg);
	    		$(obj).val("") ;
	    	}
	    },
	    async:false,
	});
}
/*图片自我删除*/
function rmImgSelf(obj){
	$(obj).parents("ul").find('li:last').css('display', '');
	$(obj).parent().remove();
}
function moveLeft(obj){
	var $sli = $(obj).parent();
	if ($sli.prev("li:has(img)").size() < 1) {
		return;
	}
	$sli.clone(true).insertBefore($sli.prev("li"));
	$sli.remove();
}
function moveRight(obj){
	var $sli = $(obj).parent();
	if ($sli.next("li:has(img)").size() < 1) {
		return;
	}
	$sli.clone(true).insertAfter($sli.next("li"));
	$sli.remove();
}
/*设置页面readonly*/
function setReadonly(){
	$('.routeRightContent[name!="overviewTab"]').each(function(){
		$(this).find('input[name="title"]').prop('readonly',true);
		$(this).find('textarea[name="content"]').prop('readonly',true);
		$(this).find('input[name="breakfest"]').prop('readonly',true);
		$(this).find('input[name="lunch"]').prop('readonly',true);
		$(this).find('input[name="dinner"]').prop('readonly',true);
		$(this).find('ul[name="images"] li img').each(function(){
			$(this).next().hide();
		});
		$(this).find('ul[name="images"] li form').parent().hide();
	});
}