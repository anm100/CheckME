package checkmeController;

public class Check {
	
	
	private String CheckNum;
    private String checkstatus;
	private String  amount;
	private String date;
	private String id;
	
	public Check()
	{
		
	}
	public Check(String CheckNum,String amount, String date,String checkstatus)
	{
		 this. CheckNum = CheckNum;
    	this.id=id;
    	 this.amount=amount;
    	 this. date=date;
    	 this.checkstatus=checkstatus;
		
	}
	
	
	 
	public void setcheckstatus ( String checkstatus ) {
	       this.checkstatus= checkstatus;
	}

	 public String getcheckstatus() {
	 
	        return checkstatus;
	}
	 public void setid ( String id ) {
	       this.id= id;
	}

	 public String getid() {
	 
	        return id;
	}
				
	public void setCheckNum ( String CheckNum ) {
	       this.CheckNum= CheckNum;
	}

	 public String getCheckNum() {
	 
	        return CheckNum;
	} 

	
	
    
     public void setamount (String amount ) {
	
			this.amount= amount;
		 }

	 public String getamount() {
				 
			return amount;
		 }
	
	 public void setdate ( String date ) {
		   this.date= date;
		 }

	 public String getdate() {
	 				 
		  return date;
		 }

}
