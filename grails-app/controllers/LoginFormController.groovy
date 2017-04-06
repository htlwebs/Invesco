import grails.converters.JSON
import invesco.Registration

class LoginFormController {

	def index() { }

	def registration() {
		def msg="Save not suceesfully"
		def isSaved=false;
		def jasonData=[]
		def name=params.name;
		def email=params.email;
		def password=params.password;

		Registration obj= new Registration();
		obj.email=email;
		obj.name=name;
		obj.password=password;

		obj.version=0;
		if(Registration.findByEmail(email) == null) {
			if(obj.save(flash:true)) {
				msg="Save suceesfully";
				isSaved=true;
			}
		}
		else {
			msg="Email ID Aready registered"
			isSaved=false;
		}
		jasonData=["msg":msg,"isSaved":isSaved]


		render jasonData as JSON
	}

	def login() {
		def jasonData=[]
		def msg="Incorrect Email id or password"
		def success=false
		def email=params.email
		def password=params.password
		Registration regObj= Registration.findByEmailAndPassword(email,password)
		if(regObj!=null) {
			msg="Login sucess"
			success=true
		}
		jasonData=["msg":msg,"success":success]
		render jasonData as JSON
	}

	def forgetPassword() {
		def email=params.email
		def msg="Incorrect Email id"
		def success=false
		def jasonData=[]
		Registration reg=Registration.findByEmail(email)
		if(reg.getPassword()!=null) {
			SendMail send = new SendMail()
			send.sendMail(email, reg.getPassword())
			msg="Email send Successfully"
			success=true
		}
		jasonData=["msg":msg,"success":success]
		render jasonData as JSON
	}
}
