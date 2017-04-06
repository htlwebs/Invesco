package invesco

import java.util.Date;

class Registration {
	String name
	String email
	String password
	int version=0


	static constraints = {

		name(nullable:false,blank:false)
		email(nullable:false,blank:false)
		password(nullable:false,blank:false)
		version(nullable:true,blank:false)
	}
	static mapping ={
		columns{ id column:"reg_id" }
	}
}
