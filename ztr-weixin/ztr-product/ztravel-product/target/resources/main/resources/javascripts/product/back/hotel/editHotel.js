/**
 *
 */
var isEdit = false;

$(function(){

	$(".type").html($("#type").children("li[class='active']").children().html());
	$(".rating").html($("#rating").children("li[class='active']").children().html());

	$("#ac-hangUp").modal({
		backdrop:"static",
		keyboard: false,
		show: false
		});

	$(".complete").click(function(){
		if (preserveValiation() && completeValiation()) {
			saveHotel(true);
		}
	});

	$(".red-fe6869Button").click(function(){
		if (preserveValiation()) {
			saveHotel(false);
		}
		$("#ac-hangUp").modal("hide");
	});

	$("#no").click(function(){
		$("#ac-hangUp").modal("hide");
		window.location.href = basepath+'/product/hotelResource/list';
	});

	$(".leftBtn .returnBtn").click(function(){
		 if(isEdit){
			 $("#ac-hangUp").modal("show");
		 }else{
			 window.location.href = basepath+'/product/hotelResource/list';
		 }
    });

    $(".rightBtn .lightBlue-b6d7e7Btn").click(function(){
   	 if(isEdit){
			 $("#ac-hangUp").modal("show");
		 }else{
			 window.location.href = basepath+'/product/hotelResource/list';
		 }
    });


	$(".noBorderCommonTab input[type='text'] ").change(function(){
 		isEdit = true;
	});

     $(".noBorderCommonTab textarea ").change(function(){
  		isEdit = true;
     });

     $(".activeFonts").change(function(){
 		isEdit = true;
 	});

     $(".dropdown-menu li").click(function(){
  		isEdit = true;
  	});

     $(".move").click(function(){
 		isEdit = true;
 	});

 	$("#file").change(function(){
 		isEdit = true;
 	});

// 	目的地（国家）
 	$("#continent-menu li").click(function(){

 		$("#nation-menu .dropdown-menu").remove();
		$("#city-menu .dropdown-menu").remove();
		$('#nation').html('');
		$('#city').html('');

 		var continentName= $.trim($("#continent-menu .active").children("a").text());
 		if(continentName !='不限'){
 			$.ajax({
 			    type: "POST",
 			    url: basepath + '/product/back/hotel/loadNation',
 			    data: continentName,
 			    headers : {
 				    'Accept' : 'application/json',
 				    'Content-Type' : 'application/json'
 			    },
 			    success: function(result){
 			    	$("#nation-menu").html(result);
 			    }
 		    });
 		}
 	});

//目的地（城市）
 	$("#nation-menu").on("click", "li", function(){

		$("#city-menu .dropdown-menu").remove();
		$('#city').html('');

		$(this).addClass("active");
	    $(this).siblings().removeClass();
		$("#nation").html($("#nation-menu .dropdown-menu").children("li[class='active']").children().html());

 		var nationName= $.trim($("#nation-menu .active").children("a").text());
 		if(nationName !='不限'){
 			$.ajax({
			    type: "POST",
			    url: basepath + '/product/back/hotel/loadCity',
			    data: nationName,
			    headers : {
				    'Accept' : 'application/json',
				    'Content-Type' : 'application/json'
			    },
			    success: function(result){
			    	$("#city-menu").html(result);
			    }
		    });
 		}
 	});

 	$("#city-menu").on("click", "li", function(){
 		$(this).addClass("active");
	    $(this).siblings().removeClass();
		$("#city").html($("#city-menu .dropdown-menu").children("li[class='active']").children().html());
 	});

});


     function saveHotel(isComplete){
    	 var data = {};
    	 data.hotelNameCn= $.trim($('#hotelNameCn').val());
    	 data.phone= $.trim($('#phone').val());
    	 data.hotelNameEn= $.trim($('#hotelNameEn').val());
    	 data.continent= $.trim($('#continent').html());
    	 data.nation= $.trim($('#nation').html());
    	 data.city= $.trim($('#city').html());
    	 data.lon= $.trim($('#lon').val());
    	 data.lat= $.trim($('#lat').val());
    	 data.address= $.trim($('#address').val());
    	 data.type= $.trim($('#type').children("li[class='active']").attr('typeenum'));
    	 data.rating= $.trim($('#rating').children("li[class='active']").attr('ratingenum'));
    	 data.advantage= $.trim($('#advantage').val());
    	 data.describe= $.trim($('#describe').val());
    	 var pictureIds =[];
    	 var t=$('.hotelImgList').children('li').children("input[name='pictureId']").length;
    	  for (var i=0;i<t;i++){
    		  pictureIds[i]=$('.hotelImgList').children('li').children("input[name='pictureId']")[i].value;
    	  }
    	 data.pictureIds= pictureIds;
    	 data.compFacilities= $.trim($('#compFacilities').val());
    	 data.cateringFacilities= $.trim($('#cateringFacilities').val());
    	 data.networkFacilities= $.trim($('#networkFacilities').val());
    	 data.activityFacilities= $.trim($('#activityFacilities').val());
    	 data.serviceFacilities= $.trim($('#serviceFacilities').val());
    	 data.notes= $.trim($('#notes').val());
    	 data.id= $.trim($("input[name='id']").val());
    	 data.isComplete= isComplete;

    	 ajaxOpera(data);
    	}

     function ajaxOpera(data){
    	 $.ajax({
			   type: "POST",
			   url: basepath + '/product/back/hotel/save',
			   headers : {
					'Accept' : 'application/json',
					'Content-Type' : 'application/json'
				},
			   data : JSON.stringify(data),
			   dataType: "json",
			   success: function(resp){
				   if (resp.res_code == 'SO_PROD_1002') {
					   window.location.href = basepath+'/product/hotelResource/list';
					}else{
						error(resp.res_msg);
					}
					 }
			});
     }
