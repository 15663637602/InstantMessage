<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>Chat with ${objectName}</title>
    <script src="./js/jquery-1.12.3.min.js"></script>
<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<style>
body{
	margin-top:5px;
}
</style>
</head>
  <body>
    <div class="container">
    	<div class="row">
    		
  			<div class="col-md-9">
  				<div class="panel panel-primary">
				  <div class="panel-heading">
				    <h3 class="panel-title" id="talktitle"></h3>
				  </div>
				  <div class="panel-body">
				    <div class="well" id="log-container-private" style="height:300px;overflow-y:scroll">
				    
				    </div>
				    	<input type="text" id="myinfo" class="form-control col-md-12" /> <br>
				    	<button id="send" type="button" class="btn btn-primary">Send</button>
				    </div>
				</div>
  			</div>
			
			
			
    	</div>
    </div> 
<script>
    $(document).ready(function() {
        // websocket url
        var websocket;
        if ('WebSocket' in window) {
			websocket = new WebSocket("ws://localhost:8080/GeekChat/ws.do?objectId=${objectId}");
		} else if ('MozWebSocket' in window) {
			websocket = new MozWebSocket("ws://localhost:8080/GeekChat/ws.do?objectId=${objectId}");
		} else {
			websocket = new SockJS("http://localhost:8080/GeekChat/ws/sockjs.do?objectId=${objectId}");
		}
		
        websocket.onmessage = function(event) {
       	 var data=JSON.parse(event.data);
       	 
       	 	if(data.from==1&&data.fromId=="${objectId}"){//private message
            //receive real-time messages from user
            $("#log-container-private").append("<div class='bg-info'><label class='text-danger'>"+data.fromName+"&nbsp;->&nbsp;Me&nbsp;"+data.date+"</label><div class='text-success'>"+data.text+"</div></div><br>");
            // Scrollbar rolling to the lowest part
            privatescrollToBottom();
            }
        };
        
        
        $("#send").click(function(){
        		
        		var v=$("#myinfo").val();
        		
				if(v==""){
					return;
				}else{
					var data={};
					data["from"]="1";
					data["fromId"]="${sessionScope.uid}"
					data["fromName"]="${sessionScope.username}";
					data["to"]="${objectId}";
					data["text"]=v;
					websocket.send(JSON.stringify(data));
					$("#log-container-private").append("<div class='bg-success'><label class='text-info'>Me&nbsp;&nbsp;"+"&nbsp;&nbsp;"+new Date().toString()+"</label><div class='text-info'>"+v+"</div></div><br>");
					privatescrollToBottom();
					$("#myinfo").val("");
					}
        		
        });
        $.get("getMessages.do?myId=${sessionScope.uid}&objectId=${objectId}",function(data){
    		for(var i=0;i<data.length;i++)
    			$("#log-container-private").append("<div class='bg-info'><label class='text-danger'>"+data[i].fromName+"&nbsp;"+data[i].date+"</label><div class='text-success'>"+data[i].text+"</div></div><br>");
    	});
    });

	function privatescrollToBottom(){
		var div = document.getElementById('log-container-private');
		div.scrollTop = div.scrollHeight;
	}
</script>    
    
  </body>
</html>
