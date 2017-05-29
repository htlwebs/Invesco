package invesco

class InvescoApiaiEntry {

   String usrCd
	String apiQue
	String apiAns
	String apiAction
	String apiDate
	int version
	static constraints = {

		usrCd(nullable:false,blank:false)
		apiQue(nullable:false,blank:false)
		apiAns(nullable:false,blank:false)
		apiAction(nullable:false,blank:false)
		apiDate(nullable:false)
		version(default:0)
	}
	static mapping = {
		columns { id column:"entry_id" }
	}
}