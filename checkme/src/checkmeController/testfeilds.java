package checkmeController;

import checkme3.Aboutusgui;
import checkme3.Checkfeedbackgui;
import checkme3.Historygui;
import checkme3.Logingui;
import checkme3.Registergui;
import checkme3.Validategui;
import checkmeController.user;
import checkme3.MainFramegui;
public class testfeilds {
	
	
	Historygui history =new Historygui();
	Aboutusgui  aboutus =new Aboutusgui();
	Checkfeedbackgui checkfeedback= new Checkfeedbackgui();
	Registergui register=new Registergui();
	Validategui validate=new Validategui();
     
	Check check=new Check();

	
	  public boolean passconfirm() // check matching passwords
	    {
			return false;
	       
	    }


	    public boolean checkUsername(String username) // check username
	    {
			return false;
	    
	    }

	    public boolean checkPassword(String password) // check password
	    {
			return false;
	       

	    }
	
	

}
