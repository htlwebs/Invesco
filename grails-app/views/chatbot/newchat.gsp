<html>
<head>
<%--<link rel="stylesheet" href="${resource(dir: 'css', file: 'bootstrap.min.css')}" type="text/css">--%>
<%--<g:javascript src="jquery.min.js" />--%>
<%--<g:javascript src="bootstrap.min.js" />--%>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
 <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="${resource(dir: 'css', file: 'chatbot.css')}">
<g:javascript src="chatbot.js" />

</head>
<body>
<div class="container">
    <div class="row chat-window col-xs-5 col-md-3" id="chat_window_1" style="margin-left:10px;">
        <div class="col-xs-12 col-md-12">
        	<div class="panel panel-default">
                <div class="panel-heading top-bar">
                    <div class="col-md-8 col-xs-8">
                        <h4 class="panel-title"><span class="glyphicon glyphicon-comment"></span> Chat - Invesco</h4>
                    </div>
                    <div class="col-md-4 col-xs-4" style="text-align: right;">
                        <a href="#"><span id="minim_chat_window" class="glyphicon glyphicon-minus icon_minim"></span></a>
                       <!-- <a href="#"><span class="glyphicon glyphicon-remove icon_close" data-id="chat_window_1"></span></a> -->
                    </div>
                </div>
                <div class="panel-body msg_container_base" id="baseChat">
                   <!-- <div class="row msg_container base_sent">
					   <div class="col-md-10 col-xs-10">
					        <div class="messages msg_sent">
                                <p>Heloo how are u ?</p>
                            </div>
                       </div>
					   
                        <div class="col-md-2 col-xs-2 avatar">
                            <img src="demoLogo.png" class=" img-responsive ">
                        </div>      
                    </div>
                    
                    <div class="row msg_container base_receive">
                        <div class="col-md-2 col-xs-2 avatar">
                            <img src="ailogo.png" class=" img-responsive ">
                        </div>
                    
					<div class="col-md-10 col-xs-10">
                            <div class="messages msg_receive">
                               <p>I m fine thank you!</p>
                                <time datetime="2017-06-20T11:00"> 51 min</time>
                            </div>
                    </div>
                    </div>
					
					 <div class="row msg_container base_sent">
					   <div class="col-md-10 col-xs-10">
					        <div class="messages msg_sent">
                                <p>Heloo how are u ?</p>
                            </div>
                       </div>
					   
                        <div class="col-md-2 col-xs-2 avatar">
                            <img src="demoLogo.png" class=" img-responsive ">
                        </div>      
                    </div>
					
					-->
					

            </div>
					
					
                </div>
                <div class="panel-footer">
                    <div class="input-group">
                        <input id="btn-input" type="text" class="form-control input-sm chat_input" placeholder="Write your message here..." />
                        <span class="input-group-btn">
                        <button class="btn btn-primary btn-sm" id="btn-chat" onclick="submitChat()">Send</button>
                        </span>
                    </div>
                </div>
    		</div>
        </div>
    </div>
    
 <!--   <div class="btn-group dropup">
        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
            <span class="glyphicon glyphicon-cog"></span>
            <span class="sr-only">Toggle Dropdown</span>
        </button>
        <ul class="dropdown-menu" role="menu">
            <li><a href="#" id="new_chat"><span class="glyphicon glyphicon-plus"></span> Newchat</a></li>
            <li><a href="#"><span class="glyphicon glyphicon-list"></span> Ver outras</a></li>
            <li><a href="#"><span class="glyphicon glyphicon-remove"></span> Fechar Tudo</a></li>
            <li class="divider"></li>
            <li><a href="#"><span class="glyphicon glyphicon-eye-close"></span> Invisivel</a></li>
        </ul>
    </div> -->
</div>
</body>
</html>