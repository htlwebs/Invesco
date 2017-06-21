<html>

<head>
<title>Invesco Chat-Bot</title>

<link rel="stylesheet" href="${resource(dir: 'css', file: 'chatstyle.css')}" type="text/css">
</head>
<body>
<g:javascript src="jquery-3.2.1.js" />

<script>

nlp = window.nlp_compromise;

var messages = [], //array that hold the record of each string in chat
  lastUserMessage = "", //keeps track of the most recent input string from the user
  botMessage = "", //var keeps track of what the chatbot is going to say
  botName = 'SalesAssistant', //name of the chatbot
  talking = true; //when false the speach function doesn't work
  var text="";
//edit this function to change what the chatbot says
function chatbotResponse() {
  talking = true;
  botMessage = "I'm confused"; //the default message
  console.log("Usermsg-->"+lastUserMessage) ;
		        //start ajax request
                $.ajax({
					type: "GET",
					data:{'inputData':lastUserMessage},
					async: false, 
					crossDomain: true,
					url: "/Invesco/apiai/getAidata",
                    //force to handle it as jason
                    dataType: "json",
					contentType: "application/json; charset=utf-8",
                    success: function(response) {
                         
                        //Implemenation of various layout based on type
                        text="";
						if(response.type=="24")
							{
								console.log("Inside if");
							 	var array=response.response;
								text="<table class='chatlog' align='left' border='1' style='font-size:12px;'><tr><th>Year<th>R-Fund<th>Rbse<th>Rnifty<th>Vfund<th>Vbse</tr>";
                         		for (i = 0; i < array.length; i++) { 
                        	    	text += "<tr><td>"+array[i].year+"<td>"+array[i].rfund+"<td>"+array[i].rbse+"<td>"+array[i].rnifty+"<td>"+array[i].vfund+"<td>"+array[i].vbse+ "<tr>";
                        											}
                     			text+="<tr colspan='2'><a href="+response.pdf+">Click here</a> to download document</table>"
						 		console.log("Sucess-->"+text);
							}
						 botMessage=response.text;
						}
                });
          
  
  
  if (lastUserMessage === 'hi') {
    botMessage = 'Howdy!';
  }

  if (lastUserMessage === 'name') {
    botMessage = 'My name is ' + botName;
  }
}

//this runs each time enter is pressed.
//It controls the overall input and output
function newEntry() {
  //if the message from the user isn't empty then run 
  if (document.getElementById("chatbox").value != "") {
    //pulls the value from the chatbox ands sets it to lastUserMessage
    lastUserMessage = document.getElementById("chatbox").value;
    //sets the chat box to be clear
    document.getElementById("chatbox").value = "";
    //adds the value of the chatbox to the array messages
    messages.push("<p align='right'><b>You:</b><br>"+lastUserMessage+"<p>");
    //Speech(lastUserMessage);  //says what the user typed outloud
    //sets the variable botMessage in response to lastUserMessage
    chatbotResponse();
    //add the chatbot's name and message to the array messages
    messages.push("<b>" + botName + ":</b> " +"<br>"+ botMessage+text);

 
    // says the message using the text to speech function written below
    Speech(botMessage);
    //outputs the last few array elements of messages to html
    for (var i = 1; i < 8; i++) {
      if (messages[messages.length - i])
        document.getElementById("chatlog" + i).innerHTML = messages[messages.length - i];
    }
   
  }
}

//text to Speech
//https://developers.google.com/web/updates/2014/01/Web-apps-that-talk-Introduction-to-the-Speech-Synthesis-API
function Speech(say) {
  if ('speechSynthesis' in window && talking) {
    var utterance = new SpeechSynthesisUtterance(say);
    //msg.voice = voices[10]; // Note: some voices don't support altering params
    //msg.voiceURI = 'native';
    //utterance.volume = 1; // 0 to 1
    //utterance.rate = 0.1; // 0.1 to 10
    //utterance.pitch = 1; //0 to 2
    //utterance.text = 'Hello World';
    //utterance.lang = 'en-US';
    speechSynthesis.speak(utterance);
  }
}

//runs the keypress() function when a key is pressed
document.onkeypress = keyPress;
//if the key pressed is 'enter' runs the function newEntry()
function keyPress(e) {
  var x = e || window.event;
  var key = (x.keyCode || x.which);
  if (key == 13 || key == 3) {
    //runs this function when enter is pressed
    newEntry();
  }
  if (key == 38) {
    console.log('hi')
      //document.getElementById("chatbox").value = lastUserMessage;
  }
}

//clears the placeholder text ion the chatbox
//this function is set to run when the users brings focus to the chatbox, by clicking on it
function placeHolder() {
  document.getElementById("chatbox").placeholder = "";
}
</script>

<div id='bodybox'>
  <div id='chatborder'>
    <p id="chatlog7" class="chatlog">&nbsp;</p>
    <p id="chatlog6" class="chatlog">&nbsp;</p>
    <p id="chatlog5" class="chatlog">&nbsp;</p>
    <p id="chatlog4" class="chatlog">&nbsp;</p>
    <p id="chatlog3" class="chatlog">&nbsp;</p>
    <p id="chatlog2" class="chatlog">&nbsp;</p>
    <p id="chatlog1" class="chatlog">&nbsp;</p>
    <input type="text" name="chat" id="chatbox" placeholder="Hi there! Type here to talk to me." onfocus="placeHolder()">
  </div>
  <br>
  <br> 
</div>
</body>
</html>