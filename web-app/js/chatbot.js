nlp = window.nlp_compromise;
var bottompos=50;
$(document).on('click', '.panel-heading span.icon_minim', function (e) {
    var $this = $(this);
    if (!$this.hasClass('panel-collapsed')) {
        $this.parents('.panel').find('.panel-body').slideUp();
        $this.addClass('panel-collapsed');
        $this.removeClass('glyphicon-minus').addClass('glyphicon-plus');
    } else {
        $this.parents('.panel').find('.panel-body').slideDown();
        $this.removeClass('panel-collapsed');
        $this.removeClass('glyphicon-plus').addClass('glyphicon-minus');
    }
});
$(document).on('focus', '.panel-footer input.chat_input', function (e) {
    var $this = $(this);
    if ($('#minim_chat_window').hasClass('panel-collapsed')) {
        $this.parents('.panel').find('.panel-body').slideDown();
        $('#minim_chat_window').removeClass('panel-collapsed');
        $('#minim_chat_window').removeClass('glyphicon-plus').addClass('glyphicon-minus');
    }
});
$(document).on('click', '#new_chat', function (e) {
    var size = $( ".chat-window:last-child" ).css("margin-left");
     size_total = parseInt(size) + 400;
    alert(size_total);
    var clone = $( "#chat_window_1" ).clone().appendTo( ".container" );
    clone.css("margin-left", size_total);
});
$(document).on('click', '.icon_close', function (e) {
    //$(this).parent().parent().parent().parent().remove();
    $( "#chat_window_1" ).remove();
});

document.onkeypress = keyPress;

//runs the keypress() function when a key is pressed
document.onkeypress = keyPress;
//if the key pressed is 'enter' runs the function newEntry()
function keyPress(e) {
  var x = e || window.event;
  var key = (x.keyCode || x.which);
  if (key == 13 || key == 3) {
	  if(document.getElementById("btn-input").value!="")
		submitChat();
  }

}

function submitChat()
{
	var inputText=document.getElementById("btn-input").value;
	sendMsgPost(inputText);
	receiveMsgPost(inputText);
	//  $(this).parents('.panel').find('.panel-body').slideDown();
	
	document.getElementById("btn-input").value="";

	
}

function sendMsgPost(msgtext)
{
	//msgtext+='<table style="width:100%" > <caption>Monthly savings</caption>  <tr>    <th>Month</th>    <th>Savings</th>  </tr>  <tr>    <td>January</td><td>$100</td>  </tr>  <tr>    <td>February</td>    <td>$50</td>  </tr></table>'
	document.getElementById("baseChat").innerHTML+="<div class='row msg_container base_sent'> <div class='col-md-10 col-xs-10'><div class='messages msg_sent'><p>"+msgtext+"</p></div></div><div class='col-md-2 col-xs-2 avatar'><img src='/Invesco/images/demoLogo.png' class= 'img-responsive'></div></div>";
	//$( "#baseChat" ).slideDown();
	bottompos+=500;
	$( "#baseChat" ).scrollTop(bottompos);
}

function receiveMsgPost(lastUserMessage)
{
	var responseData;
	var txtMsg="";
	var respMsg="";
	 $.ajax({
			type: "GET",
			data:{'inputData':lastUserMessage},
			async: false, 
			crossDomain: true,
			url: "/Invesco/apiai/getAidata",
            dataType: "json",
			contentType: "application/json; charset=utf-8",
         success: function(response) 
         {
        	 txtMsg=response.text;
        	 responseData=response;
         }
     });
	 Speech(txtMsg);	
	 respMsg=dynamicLayout(responseData);
	 console.log("ResponseData"+respMsg);
	document.getElementById("baseChat").innerHTML+="<div class='row msg_container base_receive'><div class='col-md-2 col-xs-2 avatar'><img src='/Invesco/images/ailogo.png' class='img-responsive'></div><div class='col-md-10 col-xs-10'><div class='messages msg_receive'><p>"+txtMsg+"<br>"+respMsg+"</p><time datetime="+new Date()+"> "+new Date()+"</time></div></div></div>"
	//$( "#baseChat" ).slideDown();
	bottompos+=500;
	$( "#baseChat" ).scrollTop(bottompos);
}

//text to Speech
//https://developers.google.com/web/updates/2014/01/Web-apps-that-talk-Introduction-to-the-Speech-Synthesis-API
function Speech(say) {
	  if ('speechSynthesis' in window ) 
	  {
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

function dynamicLayout(responseData)
{
	var sendTxt="";
	var type=responseData.type;
	switch(type)
	{
	//Plain Text
	case 0:
		
		sendTxt="";
		break;
	//Performance of fund	
	case 24:
	
		sendTxt="<table class='respTable'><tr><td>year<td>rfund<td>rbse<td>rnifty<td>vfund<td>vbse</tr>"
	for(i=0;i<responseData.response.length;i++)
		{
		sendTxt+="<tr><td>"+responseData.response[i].year+"<td>"+responseData.response[i].rfund+"<td>"+responseData.response[i].rbse+"<td>"+responseData.response[i].rnifty+"<td>"+responseData.response[i].vfund+"<td>"+responseData.response[i].vbse+"</tr>"
		}
		sendTxt+="</table>";
		break;
	//Where do fund invest	
	case 9:
		sendTxt="<a href="+responseData.pdf+">Click here to Download pdf</a>";
		break;
	//Nav use	
	case 26:

		sendTxt="<table>		<tbody>		<tr>		<td>Type</td>		<td>Growth</td>		<td>Dividend</td> </tr>		<tr>		<td>Direct</td><td>18.96</td><td>17.02</td>		</tr>		<tr>		<td>Indirect</td>		<td>17.92</td>		<td>16.17</td>		</tr>		</tbody>		</table>";
		
		break;
		
	}
	
	return sendTxt;
}