import grails.converters.JSON
import invesco.InvescoApiaiEntry
import javax.net.ssl.SSLContext
import ai.api.model.AIResponse
import ai.recast.*;
import java.util.Random;



class ApiaiController {
	String urls="http://35.154.92.3:8080/Invesco/"
	//String urls="http://192.168.0.222:8080/Invesco/"

	Client client = null;
	Response recastResponse=null;
	Conversation conv = null;
	
	def getAidata()
	{
		String username;
		def jsonData=[]
		def suggestion=[]
		def resp=[]
		String period=""
		AIResponse aiResponse=null
		aiResponse= TextClientApplication.getData(params.inputData);
		ProcessedResponse processedResponse;
		if(aiResponse!=null)
		{
			
			println "Speech:"+aiResponse.getResult().getFulfillment().getSpeech()
			println "Action:"+ aiResponse.getResult().getAction()
			processedResponse = new ProcessedResponse();
			if(aiResponse.getResult().getParameters()!=null)
			{
				aiResponse.getResult().getParameters().get("period")!=null?processedResponse.setPeriod(parsedString(aiResponse.getResult().getParameters().get("period") as String)):processedResponse.setPeriod("")
				aiResponse.getResult().getParameters().get("navtype")!=null?processedResponse.setNavtype(parsedString(aiResponse.getResult().getParameters().get("navtype") as String)):processedResponse.setNavtype("")
			}
			processedResponse.setResponseAction(aiResponse.getResult().getAction());
			//processedResponse.setResponseParameters(aiResponse.getResult().getParameters());
			processedResponse.setResponseText(aiResponse.getResult().getFulfillment().getSpeech() as String!=""?aiResponse.getResult().getFulfillment().getSpeech() as String:"NA");
			
		}

		else
		{
			try
			{
				println"Inside Recast.AI Controller"
/*
				client = new Client("f68d7eba67d0d42c30924c819ada1d6a");
				recastResponse=client.textRequest(params.inputData);
				//conv = client.request.doTextConverse(params.inputData);
				//Entity entity[] = conv.getEntities().get("name");
				//Response responses= client.textRequest(params.inputData);
				//Intent intent = responses.getIntent();
				//processedResponse = new ProcessedResponse();
				//processedResponse.setResponseAction(conv.getAction().getSlug());
				//processedResponse.setResponseParameters(rr);
				println "Recast Response-->${recastResponse.getSource()}"
				//Entity entity[]= rr.getEntities("name");
		 
*/
				
				
				client= new Client("f68d7eba67d0d42c30924c819ada1d6a");
				recastResponse=client.textRequest(params.inputData);
				processedResponse = new ProcessedResponse();
				
				processedResponse.setResponseAction(recastResponse.getIntent().getName())
				processedResponse.setNavtype((recastResponse.getEntity("navtype")!=null?recastResponse.getEntity("navtype").getValue():""));
				processedResponse.setPeriod((recastResponse.getEntity("duration")!=null?recastResponse.getEntity("duration").getData().optInt("years")+" year":0+" year"));
				processedResponse.setResponseText(randomText());
				
				
				//println("Source--> "+recastResponse.getSource());
				//println("Action--> "+recastResponse.getIntent().getName());
				//println("NavType--> "+(recastResponse.getEntity("navtype")!=null?recastResponse.getEntity("navtype").getValue():""));
				//println("Duration--> "+(recastResponse.getEntity("duration")!=null?recastResponse.getEntity("duration").getData().optInt("years")+" year":0+" year"));
			}
			catch(Exception e)
			{
				println "Error in recast.ai--->${e}"
			}
			finally
			{
				jsonData=["type":0,
				"text":"Internet Connectivity is not avilable!",
				"response":[],
				"suggestion":getSuggestion("default")]
			}
		}
		
		/**
		 * Calling a function to store data in table
		 */
		apiAiEntry(processedResponse);

		switch(processedResponse.getResponseAction())
		{
			case "master_sugg":
				suggestion=[["suggest":"Show me performance of fund"], ["suggest":"Show me the portfolio of the fund"], ["suggest":"Latest presentation of the fund"], ["suggest":"NAV of the fund"], ["suggest":"Investment objective of the fund"]]
				jsonData=["type":11,"text":"Sure, what would you like to know? \nHere are a few suggestions","response":[],
					"suggestion":suggestion]
				break;

			case "aum":
				jsonData=["type":0,
					"text":"Current size or AuM is Rs. 98.99Cr",
					"response":[],
					"suggestion":getSuggestion("aum")]
				break;

			case "benchmark":
				jsonData=["type":0,
					"text":"S&P BSE PSU Index",
					"response":[],
					"suggestion":getSuggestion("benchmark")]
				break;

			case "support-contacts":
				jsonData=["type":0,
					"text":"Email us at assistant@mapmymarketing.ai or vist mapmymarketing.ai for any questions regarding our products and services.",
					"response":[],
					"suggestion":getSuggestion("support-contacts")]
				break;

			case "dividend-history":
				jsonData=["type":25,
					"text":"Yes the fund has given the following dividend",
					"response":[["date":"26/11/2010","rate":"1.1"]],
					"suggestion":getSuggestion("dividend-history")]
				break;

			case "expense-ratio":
				jsonData=["type":0,
					"text":"Current expense of the fund is 2.40%",
					"response":[],
					"suggestion":getSuggestion("expense-ratio")]
				break;

			case "fund-details":
				jsonData=["type":9,
					"text":"The fund invests in P S U companies that carry the potential of promising growth and competence to deliver superior returns over the long term.",
					"isMap":false,
					"pdf":urls+"pdf/One Pager.pdf",
					"pages":2,"size":"83900","name":"One Pager.pdf","display_name":"One Pager",
					"response":[],
					"suggestion":getSuggestion("fund-details")]
				break;

			case "fund-manager":
				jsonData=["type":0,
					"text":"1. Amit Ganatra - Since 9 November, 2010\n2. Pranav Gokhale - Since 28 September, 2015.",
					"response":[],
					"suggestion":getSuggestion("fund-manager")]
				break;

			case "fund-presentation":
				def jasonData=["type":9,
					"text":"Sure! Here is the latest presentation of the fund",
					"isMap":false,
					"pdf":urls+"pdf/Invesco India PSU Equity Fund Presentation.pdf",
					"pages":29,"size":"1267712","name":"Invesco India PSU Equity Fund Presentation.pdf","display_name":"Invesco India PSU Equity Fund Presentation",
					"response":[],
					"suggestion":getSuggestion("fund-presentation")]

				render jasonData as JSON

				break;

			case "performance-details":

/*				if(processedResponse.getResponseParameters.get("period")!="")
				{period=processedResponse.getResponseParameters.get("period").toString()

					period = period.substring(1, period.length()-1)
				}
				println "Data for pr : ${period}"
*/
				period=processedResponse.getPeriod()
			
				switch(period)
				{
					case "1 year":
						resp=	[["year":"1yr","rfund":"51.29%","rbse":"53.18%","rnifty":"27.09%","vfund":"15,129","vbse":"15,318"]]
						break;
					case "3 year":
						resp=	[["year":"3yr","rfund":"28.09%","rbse":"15.34%","rnifty":"12.25%","vfund":"21,029","vbse":"15,348"]]
						break;
					case "5 year":
						resp=	[["year":"5yr","rfund":"11.39%","rbse":"1.74%","rnifty":"10.51%","vfund":"17,151","vbse":"10,902"]]
						break;
					case "7 year":
						resp=	[["year":"7yr","rfund":"8.57%","rbse":"-1.20%","rnifty":"8.78%","vfund":"17,794","vbse":"9,186"]]
						break;
					case "10 year":
						resp=	[["year":"10yr","rfund":"NA","rbse":"4.00%","rnifty":"9.01%","vfund":"NA","vbse":"14,813"]]
						break;
					case "since inception":
						resp=	[["year":"Since Inception","rfund":"8.05%","rbse":"-1.15%","rnifty":"8.04%","vfund":"17,580","vbse":"9,191"]]
						break;
					default:
						resp=	[
							["year":"1yr","rfund":"51.29%","rbse":"53.18%","rnifty":"27.09%","vfund":"15,129","vbse":"15,318"],
							["year":"3yr","rfund":"28.09%","rbse":"15.34%","rnifty":"12.25%","vfund":"21,029","vbse":"15,348"],
							["year":"5yr","rfund":"11.39%","rbse":"1.74%","rnifty":"10.51%","vfund":"17,151","vbse":"10,902"],
							["year":"7yr","rfund":"8.57%","rbse":"-1.20%","rnifty":"8.78%","vfund":"17,794","vbse":"9,186"],
							["year":"10yr","rfund":"NA","rbse":"4.00%","rnifty":"9.01%","vfund":"NA","vbse":"14,813"],
							["year":"Since Inception","rfund":"8.05%","rbse":"-1.15%","rnifty":"8.04%","vfund":"17,580","vbse":"9,191"]
						]
						break;
				}
				jsonData=["type":24,
					"text":"Performance of the fund",
					"pdf":urls+"pdf/Performance.pdf",
					"pages":1,"size":"421888","name":"Performance.pdf","display_name":"Performance Report",
					"response":resp,
					"suggestion":getSuggestion("performance-details")]
				break;

			case "inception-date":
				jsonData=["type":0,
					"text":"18th November 2009",
					"response":[],
					"suggestion":getSuggestion("inception-date")]
				break;

			case "investment-objective":
				jsonData=["type":0,
					"text":"To generate capital appreciation by investing in equity and equity related instruments of companies where the Central / State Government(s) has majority share holding or management control or powers to appoint majority of directors.",
					"response":[],
					"suggestion":getSuggestion("investment-objective")]
				break;

			case "nav":
				period=""
/*
				if(processedResponse.getResponseParameters.get("navtype")!="")
				{period=processedResponse.getResponseParameters.get("navtype").toString()

					period = period.substring(1, period.length()-1)
				}
*/
				period=processedResponse.getNavtype()
				println "NAV Type : ${period}"

				switch(period)
				{
					case "indirect":
						resp=[["type":"Indirect","growth":"17.92","dividend":"16.17"]]
						break;
					case "direct":
						resp=[["type":"Direct","growth":"18.96","dividend":"17.02"]]
						break;
					default:
						resp=[["type":"Direct","growth":"18.96","dividend":"17.02"], ["type":"Indirect","growth":"17.92","dividend":"16.17"]]
						break;

				}
				jsonData=["type":26,
					"text":"Net Asset Value",
					"response":resp,
					"suggestion":getSuggestion("nav")]
				break;


			case "portfolio-details":
				def jasonData=["type":27,"text":"Top 10 holdings in the portfolio",
					"response":[
						["pdf":urls+"pdf/State Bank of India.pdf","pages":1,"size":"248832","name":"State Bank of India.pdf","display_name":"State Bank of India"],
						["pdf":urls+"pdf/Indian Oil Corporation Limited.pdf","pages":1,"size":"248832","name":"Indian Oil Corporation Limited.pdf","display_name":"Indian Oil Corporation Limited"],
						["pdf":urls+"pdf/Power Grid Corporation of India.pdf","pages":1,"size":"248832","name":"Power Grid Corporation of India.pdf","display_name":"Power Grid Corporation of India"],
						["pdf":urls+"pdf/Coal India Limited.pdf","pages":1,"size":"248832","name":"Coal India Limited.pdf","display_name":"Coal India Limited"],
						["pdf":urls+"pdf/NTPC Limited.pdf","pages":1,"size":"248832","name":"NTPC Limited.pdf","display_name":"NTPC Limited"],
						["pdf":urls+"pdf/LIC Housing Finance Limited.pdf","pages":1,"size":"248832","name":"LIC Housing Finance Limited.pdf","display_name":"LIC Housing Finance Limited"],
						["pdf":urls+"pdf/GAIL (India) Limited.pdf","pages":1,"size":"248832","name":"GAIL (India) Limited.pdf","display_name":"GAIL (India) Limited"],
						["pdf":urls+"pdf/National Aluminium Company Limited.pdf","pages":1,"size":"248832","name":"National Aluminium Company Limited.pdf","display_name":"National Aluminium Company Limited"],
						["pdf":urls+"pdf/Gujarat Gas Limited.pdf","pages":1,"size":"248832","name":"Gujarat Gas Limited.pdf","display_name":"Gujarat Gas Limited"],
						["pdf":urls+"pdf/Indraprastha Gas Limites.pdf","pages":1,"size":"248832","name":"Indraprastha Gas Limites.pdf","display_name":"Indraprastha Gas Limites"]
					],
					"suggestion":getSuggestion("portfolio-details")]
				render jasonData as JSON

				break;

			default:
				if(processedResponse.getResponseText()!=null)
					jsonData=["type":0,
						"text":processedResponse.getResponseText(),
						"response":[],
						"suggestion":getSuggestion("default")]
				else
					jsonData=["type":0,
						"text":"Opps! Something went wrong with internet connection!",
						"response":[],
						"suggestion":getSuggestion("default")]
				break;
		}

		render jsonData as JSON
	}





	def getSuggestion(action)
	{
		switch(action)
		{
			case "aum":
				return [["suggest":"Show me NAV"], ["suggest":"What has been the dividend history of the fund?"], ["suggest":"What is the TER of the fund?"]]
				break;

			case "benchmark":
				return [["suggest":"Show me performance of the fund"], ["suggest":"Who manages the fund?"], ["suggest":"Show the current size of fund"]]
				break;

			case "support-contacts":
				return [["suggest":"Investment objective of the fund"], ["suggest":"Who manages the fund?"], ["suggest":"Show the current size of fund"]]
				break;

			case "dividend-history":
				return [["suggest":"Give me details of the portfolio"], ["suggest":"Show me the latest presentation"], ["suggest":"Show me performance of the fund"]]
				break;

			case "expense-ratio":
				return [["suggest":"Show me the NAV"], ["suggest":"What has been the dividend history of the fund?"], ["suggest":"Show me the latest presentation"]]
				break;

			case "fund-details":
				return [["suggest":"Show me performance of the fund"], ["suggest":"Show me the NAV"], ["suggest":"Show me the latest presentation"]]
				break;

			case "fund-manager":
				return [["suggest":"Give me details of the portfolio"], ["suggest":"Give me details on performance"], ["suggest":"Show me the NAV"]]
				break;

			case "fund-presentation":
				return [["suggest":"Give me details of the portfolio"], ["suggest":"Where does the fund invests?"], ["suggest":"NAV of the fund"]]
				break;

			case "performance-details":
				return [["suggest":"Who is fund manager?"], ["suggest":"Where does the fund invests?"], ["suggest":"NAV of the fund"]]
				break;

			case "inception-date":
				return [["suggest":"Investment objective of the fund"], ["suggest":"Where does the fund invests?"], ["suggest":"Who is fund manager?"]]
				break;

			case "investment-objective":
				return [["suggest":"Where does the fund invests?"], ["suggest":"Who is fund manager?"], ["suggest":"Dividend history of the fund"], ["suggest":"Show me the NAV"]]
				break;

			case "nav":
				return [["suggest":"Has the fund given dividend"], ["suggest":"Investment objective of the fund"], ["suggest":"Show me the latest presentation"]]
				break;

			case "portfolio-details":
				return [["suggest":"Show me performance of fund"], ["suggest":"Investment objective of the fund"], ["suggest":"Show me the NAV"]]
				break;

			default:
				return [["suggest":"Show me the NAV"], ["suggest":"Who is fund manager?"], ["suggest":"Where does the fund invests?"]]
				break;
		}
	}

	String parsedString(String str)
	{
		return str.substring(1, str.length()-1)
	}
	
	String randomText()
	{
		String []texts =["Sorry! I did not get the last statement!","Sorry I am trying to learn! Please give me some time to learn","Last part is new for me! Let me work on it!","Sorry I missed the last part!","Ohh! I did not get the last part!"];
	return texts[new Random().nextInt(4)]
	}
	
	
	def apiAiEntry(processedResponse)
	{
		try{
			InvescoApiaiEntry newQ= new InvescoApiaiEntry()
			newQ.usrCd=params.empcode!=null?params.empcode:"DEMO"
			newQ.apiQue=params.inputData
			newQ.apiAns=processedResponse.getResponseText();
			newQ.apiAction=processedResponse.getResponseAction();
			newQ.apiDate= new java.sql.Timestamp(new Date().getTime())
			newQ.save()
		}
		catch(Exception e)
		{
			println"Error in storing answer....${e}"
		}
	}
	
	def test()
	{
		
		client= new Client("f68d7eba67d0d42c30924c819ada1d6a");
		recastResponse=client.textRequest(params.inputData);
		
		println("Action--> "+recastResponse.getIntent().getName());
		println("NavType--> "+(recastResponse.getEntity("navtype")!=null?recastResponse.getEntity("navtype").getValue():""));
		println("Duration--> "+(recastResponse.getEntity("duration")!=null?recastResponse.getEntity("duration").getData().optInt("years")+" year":0+" year"));
		def jsonData=["action":recastResponse.getIntent().getName()]
		//Random rand = new Random();
		
		//int randomNum = rand.nextInt(3);
		//println"Random No--> ${randomNum}"
		//println "recasrResponse--> ${recastResponse}"
		
		render jsonData as JSON
	}
}