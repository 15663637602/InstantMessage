<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="overflow-y:hidden;">
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<LINK rel="Bookmark" href="/favicon.ico">
<LINK rel="Shortcut Icon" href="/favicon.ico" />
<link href="css/H-ui.css" rel="stylesheet" type="text/css" />
<link href="css/H-ui.admin.css" rel="stylesheet" type="text/css" />
<link type="text/css" rel="stylesheet" href="font/font-awesome.min.css" />
<title>Geek Chat</title>
<meta name="keywords" content="LYQ">
<meta name="description" content="Jake Li">
</head>
<body style="overflow:hidden">
	<header class="Hui-header cl"> <a class="Hui-logo l"
		title="H-ui.admin v2.1" href="/">Geek Chat</a> <a
		class="Hui-logo-m l" href="/" title="H-ui.admin">geekchat</a> <span
		class="Hui-subtitle l">V1.0</span> <span class="Hui-userbox"><span
		class="c-white">${sessionScope.username}</span> <a
		class="btn btn-danger radius ml-10" href="logout.do" title="signout"><i
			class="icon-off"></i> Sign out</a></span> <a aria-hidden="false"
		class="Hui-nav-toggle" id="nav-toggle" href="#"></a> </header>
	<div class="cl Hui-main">
		<aside class="Hui-aside" style=""> <input runat="server"
			id="divScrollValue" type="hidden" value="" />
		<div class="menu_dropdown bk_2">
			<dl id="menu-user">
				<dt>
					<i class="icon-user"></i> Online Users<b></b>
				</dt>
				<dd>
					<ul id="users">
						
					</ul>
				</dd>
			</dl>
			
			<dl id="menu-comments">
				<dt>
					<i class="icon-comments"></i> New Messages<b></b>
				</dt>
				<dd>
					<ul id="messages">
						
					</ul>
				</dd>
			</dl>
			
			<dl id="menu-article">
				<dt>
					<i class="icon-edit"></i> Public Chat<b></b>
				</dt>
				<dd>
					<ul>
						<li><a _href="openPublicC.do" href="javascript:;"> Public Chat</a></li>
					</ul>
				</dd>
			</dl>
		</div>
		
		</aside>
		<div class="dislpayArrow">
			<a class="pngfix" href="javascript:void(0);"></a>
		</div>
		<section class="Hui-article">
		<div id="Hui-tabNav" class="Hui-tabNav">
			<div class="Hui-tabNav-wp">
				<ul id="min_title_list" class="acrossTab cl">
					<li class="active"><span title="Welcome" data-href="welcome.jsp">Welcome</span><em></em></li>
				</ul>
			</div>
			<div class="Hui-tabNav-more btn-group">
				<a id="js-tabNav-prev" class="btn radius btn-default btn-small"
					href="javascript:;"><i class="icon-step-backward"></i></a><a
					id="js-tabNav-next" class="btn radius btn-default btn-small"
					href="javascript:;"><i class="icon-step-forward"></i></a>
			</div>
		</div>
		<div id="iframe_box" class="Hui-articlebox">
			<div class="show_iframe">
				<div style="display:none" class="loading"></div>
				<iframe scrolling="yes" frameborder="0" src="welcome.jsp"></iframe>
			</div>
		</div>
		</section>
	</div>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/Validform_v5.3.2_min.js"></script>
	<script type="text/javascript" src="layer/layer.min.js"></script>
	<script type="text/javascript" src="js/H-ui.js"></script>
	<script type="text/javascript" src="js/H-ui.admin.js"></script>
	<script>
    $(document).ready(function() {
        // websocket url
        var websocket;
        if ('WebSocket' in window) {
			websocket = new WebSocket("ws://localhost:8080/GeekChat/ws.do");
		} else if ('MozWebSocket' in window) {
			websocket = new MozWebSocket("ws://localhost:8080/GeekChat/ws.do");
		} else {
			websocket = new SockJS("http://localhost:8080/GeekChat/ws/sockjs.do");
		}
		
        websocket.onmessage = function(event) {
       	 var data=JSON.parse(event.data);
       	 	if(data.from==1){//private message
            openReceiveTalk(data.fromId,data.fromName);
            // Scrollbar rolling to the lowest part
            privatescrollToBottom();
            }else if(data.from==0){//Online message
            
            	if(data.text!="${sessionScope.username}")
            	{	
            		$("#users").append('<li><a href="#" id="'+ data.fromId +'" onclick="talk(this)" >'+data.text+'</a></li>');
            		alert(data.text+" is online");
            	}
            }else if(data.from==-2){//Offline message
            	if(data.text!="${sessionScope.username}")
            	{	
            	var off = document.getElementById(data.fromId);
            	off.remove();
            		alert(data.text+" is offline");
            	}
            }
        };
        $.get("onlineusers.do",function(data){
    		for(var i=0;i<data.length;i++)
    			$("#users").append('<li><a href="#" id="'+ data[i].u_id +'" onclick="talk(this)">'+data[i].username+'</a></li>');
    	});
        
    });
   
   function talk(username){
   	window.open("openChat.do?uid="+username.id,"Chat with "+username.innerHTML, "height=100, width=400, toolbar =no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
   }
   function receiveTalk(username){
   window.open("openChat.do?uid="+username.name,"Chat with "+username.innerHTML, "height=100, width=400, toolbar =no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
   var off = document.getElementById("m"+username.name);
   off.remove();
   }
   function openReceiveTalk(uid,username){
   alert("uid  "+uid);
   alert("username  "+username);
   $("#messages").append('<li><a href="#" name="'+uid+'" id="m'+ uid +'" onclick="receiveTalk(this)">'+username+'</a></li>');
   }
   
</script>
</body>
</html>