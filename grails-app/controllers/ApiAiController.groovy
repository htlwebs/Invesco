import grails.converters.JSON
import ai.api.model.AIResponse
class ApiAiController {
	String urls="http://35.154.92.3:8080/Invesco/"
	//String urls="http://192.168.0.222:8080/Invesco/"
	def index() { }
	String action=""
	def getAidata() {

		String username;
		def jsonData=[]
		def suggestion=[]
		def resp=[]
		String period=""
		AIResponse response=TextClientApplication.getData(params.inputData)
		if(response!=null) {

			if(response.getStatus().getCode()==200) {
				println "Speech:"+response.getResult().getFulfillment().getSpeech()
				println "Action:"+ response.getResult().getAction()
				action=response.getResult().getAction();
				switch(response.getResult().getAction()) {


					case "master_sugg":
						suggestion=[["suggest":"Show me performance of fund"], ["suggest":"Show me the portfolio of the fund"], ["suggest":"Latest presentation of the fund"], ["suggest":"Nav of the fund"], ["suggest":"Investment objective of the fund"]]
						jsonData=["type":11,
							"text":"Hey! Here is something that you can ask!",
							"response":[],
							"suggestion":suggestion]
						break;

					case"aum":
						jsonData=["type":0,
							"text":response.getResult().getFulfillment().getSpeech(),
							"response":[],
							"suggestion":getSuggestion("aum")]
						break;

					case"benchmark":
						jsonData=["type":0,
							"text":response.getResult().getFulfillment().getSpeech(),
							"response":[],
							"suggestion":getSuggestion("benchmark")]
						break;

					case"support.contacts":
						jsonData=["type":0,
							"text":response.getResult().getFulfillment().getSpeech(),
							"response":[],
							"suggestion":getSuggestion("support.contacts")]
						break;

					case"dividend.history":
						jsonData=["type":25,
							"text":"Yes the fund has given the following dividend",
							"response":[["date":"26/11/2010","rate":"1.1"]],
							"suggestion":getSuggestion("dividend.history")]
						break;

					case"expense.ratio":
						jsonData=["type":0,
							"text":response.getResult().getFulfillment().getSpeech(),
							"response":[],
							"suggestion":getSuggestion("expense.ratio")]
						break;

					case"fund.details":
						jsonData=["type":9,
							"text":response.getResult().getFulfillment().getSpeech(),
							"isMap":false,
							"pdf":urls+"pdf/One Pager.pdf",
							"pages":2,"size":"83900","name":"One Pager.pdf","display_name":"One Pager",
							"response":[],
							"suggestion":getSuggestion("fund.details")]
						break;

					case"fund.manager":
						jsonData=["type":0,
							"text":response.getResult().getFulfillment().getSpeech(),
							"response":[],
							"suggestion":getSuggestion("fund.manager")]
						break;

					case"fund.presentation":
						def jasonData=["type":9,"text":"Sure! Here the latest presentation of the fund",
							"isMap":false,
							"pdf":urls+"pdf/Invesco India PSU Equity Fund Presentation.pdf",
							"pages":29,"size":"1267712","name":"Invesco India PSU Equity Fund Presentation.pdf","display_name":"Invesco India PSU Equity Fund Presentation",
							"response":[],
							"suggestion":getSuggestion("fund.presentation")]

						render jasonData as JSON

						break;

					case "performance.details":

						if(response.getResult().getParameters().get("period")!="")
						{period=response.getResult().getParameters().get("period").toString()

							period = period.substring(1, period.length()-1)
						}
						println "Data for pr : ${period}"

						switch(period)
						{
							case "1 year":
								resp=	[["year":"1yr","rfund":"51.19%","rbse":"53.18%","rnifty":"27.09%","vfund":"15,129","vbse":"15,318"]]
								break;
							case "3 year":
								resp=	[["year":"3yr","rfund":"28.09%","rbse":"15.34%","rnifty":"12.25%","vfund":"21,029","vbse":"15,318"]]
								break;
							case "5 year":
								resp=	[["year":"5yr","rfund":"11.39%","rbse":"1.74%","rnifty":"10.51%","vfund":"17,151","vbse":"15,318"]]
								break;
							case "7 year":
								resp=	[["year":"7yr","rfund":"8.57%","rbse":"-1.20%","rnifty":"8.78%","vfund":"17,794","vbse":"15,318"]]
								break;
							case "10 year":
								resp=	[["year":"10yr","rfund":"NA","rbse":"4.00%","rnifty":"9.01%","vfund":"NA","vbse":"15,318"]]
								break;
							case "since inception":
								resp=	[["year":"Since Inception","rfund":"8.05%","rbse":"-1.15%","rnifty":"8.04%","vfund":"157,580","vbse":"9,191"]]
								break;
							default:
								resp=	[
									["year":"1yr","rfund":"51.19%","rbse":"53.18%","rnifty":"27.09%","vfund":"15,129","vbse":"15,318"],
									["year":"3yr","rfund":"28.09%","rbse":"15.34%","rnifty":"12.25%","vfund":"21,029","vbse":"15,318"],
									["year":"5yr","rfund":"11.39%","rbse":"1.74%","rnifty":"10.51%","vfund":"17,151","vbse":"15,318"],
									["year":"7yr","rfund":"8.57%","rbse":"-1.20%","rnifty":"8.78%","vfund":"17,794","vbse":"15,318"],
									["year":"10yr","rfund":"NA","rbse":"4.00%","rnifty":"9.01%","vfund":"NA","vbse":"15,318"],
									["year":"Since Inception","rfund":"8.05%","rbse":"-1.15%","rnifty":"8.04%","vfund":"157,580","vbse":"9,191"]
								]
								break;
						}
						jsonData=["type":24,
							"text":"Performance of the fund",
							"pdf":urls+"pdf/Performance.pdf",
							"pages":1,"size":"421888","name":"Performance.pdf","display_name":"Performance Report",
							"response":resp,
							"suggestion":getSuggestion("performance.details")]
						break;

					case"inception.date":
						jsonData=["type":0,
							"text":response.getResult().getFulfillment().getSpeech(),
							"response":[],
							"suggestion":getSuggestion("inception.date")]
						break;

					case"investment.objective":
						jsonData=["type":0,
							"text":response.getResult().getFulfillment().getSpeech(),
							"response":[],
							"suggestion":getSuggestion("investment.objective")]
						break;

					case"nav":
						period=""
						if(response.getResult().getParameters().get("navtype")!="")
						{period=response.getResult().getParameters().get("navtype").toString()

							period = period.substring(1, period.length()-1)
						}
						println "Nav Type : ${period}"

						switch(period)
						{
							case"direct":
								resp=[["type":"Direct","growth":"17.92","dividend":"16.17"]]
								break;
							case"indirect":
								resp=[["type":"Indirect","growth":"18.96","dividend":"17.02"]]
								break;
							default:
								resp=[["type":"Direct","growth":"17.92","dividend":"16.17"], ["type":"Indirect","growth":"18.96","dividend":"17.02"]]
								break;

						}
						jsonData=["type":26,
							"text":"Net Asset Value",
							"response":resp,
							"suggestion":getSuggestion("nav")]
						break;


					case "portfolio.details":
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
							"suggestion":getSuggestion("portfolio.details")]
						render jasonData as JSON

						break;


					default:
						if(response.getResult().getFulfillment().getSpeech()!=null)
							jsonData=["type":0,
								"text":response.getResult().getFulfillment().getSpeech(),
								"response":[],
								"suggestion":getSuggestion("default")]
						else
							jsonData=["type":0,
								"text":"Opps! Something went wrong with internet connection!",
								"response":[],
								"suggestion":getSuggestion("default")]
						break;
				}
			}
			render jsonData as JSON
		}

		else
		{
			jsonData=["type":0,
				"text":"Internet Connectivity is not avilable!",
				"response":[],
				"suggestion":getSuggestion("default")]
		}
	}


	def getSuggestion(action)
	{
		switch(action)
		{
			case"aum":
				return [["suggest":"Show me NAV"], ["suggest":"What has been the dividend history of the fund?"], ["suggest":"What is the TER of the fund?"]]
				break;

			case"benchmark":
				return [["suggest":"Show me perfomance of the fund"], ["suggest":"Who manages the fund?"], ["suggest":"Show the current size of fund"]]
				break;

			case"support.contacts":
				return [["suggest":"Investment objective of the fund"], ["suggest":"Who manages the fund?"], ["suggest":"Show the current size of fund"]]
				break;

			case"dividend.history":
				return [["suggest":"Give me details of the portfolio"], ["suggest":"Show me the latest presentation"], ["suggest":"Show me perfomance of the fund"]]
				break;

			case"expense.ratio":
				return [["suggest":"Show me the NAV"], ["suggest":"What has been the dividend history of the fund?"], ["suggest":"Show me the latest presentation"]]
				break;

			case"fund.details":
				return [["suggest":"Show me perfomance of the fund"], ["suggest":"Show me the NAV"], ["suggest":"Show me the latest presentation"]]
				break;

			case"fund.manager":
				return [["suggest":"Give me details of the portfolio"], ["suggest":"Give me details on performance"], ["suggest":"Show me the NAV"]]
				break;

			case"fund.presentation":
				return [["suggest":"Who is fund manager?"], ["suggest":"Where does the fund invests?"], ["suggest":"Nav of the fund"]]
				break;

			case "performance.details":
				return [["suggest":"Who is fund manager?"], ["suggest":"Where does the fund invests?"], ["suggest":"Nav of the fund"]]
				break;

			case"inception.date":
				return [["suggest":"Investment objective of the fund"], ["suggest":"Where does the fund invests?"], ["suggest":"Who is fund manager?"]]
				break;

			case"investment.objective":
				return [["Where does the fund invests?"], ["suggest":"Who is fund manager?"], ["suggest":"Show me the NAV"]]
				break;

			case"nav":
				return [["suggest":"Has the fund given dividend"], ["suggest":"Investment objective of the fund"], ["suggest":"Show me the latest presentation"]]
				break;

			case "portfolio.details":
				return [["suggest":"Show me performance of fund"], ["suggest":"Investment objective of the fund"], ["suggest":"Show me the NAV"]]
				break;

			default:
				return [["suggest":"Show me the NAV"], ["suggest":"Who is fund manager?"], ["suggest":"Where does the fund invests?"]]
				break;
		}
	}

	String parsedString(String str)
	{return str.substring(1, str.length()-1)}
}