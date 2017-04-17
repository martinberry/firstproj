<#import "/common/opera/htmlIndex.ftl" as html/>
<@html.htmlIndex curModule="后台管理" title="dwr receive"
	>


	<div class="main-container">
	
        dwr接收<br />
        <input type="button" id="content" style="width:200px;height:30px;border:1px solid;text-align:center;padding:5px;"></button>

    <script type="text/javascript" >


		 $(function(){
		 
			$("#content").click(function(){
			
			
				$.ajax({
					type: 'GET',
				    url: "http://localhost:8280/ztravel-sso" + '/login/jsonp' ,
					contentType : 'application/json',			    
				    dataType: 'jsonp' ,
					jsonpCallback: "callback",	
					success: function(data){
				        console.log(data.res_code);
  					},
  					error : function(response){
  						console.log(response);
  					}
				
				})
			
				
			});

		 })

    </script>	    
	</div>
    
</@html.htmlIndex>