package application;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import checkme3.Historygui;
import checkme3.Logingui;
import checkme3.MainFramegui;
import checkme3.Validategui;
import checkmeController.Check;
import checkmeController.ConnectToServer;
import checkme3.Aboutusgui;
import checkme3.Checkfeedbackgui;
import checkme3.Registergui;
 public class MainController  implements  ActionListener {
	 private static final int newinfo = 0;

	private static MainController instance = null;
	 
		Historygui history =new Historygui();
		Aboutusgui  aboutus =new Aboutusgui();
		Checkfeedbackgui checkfeedback= new Checkfeedbackgui();
		Registergui register=new Registergui();
		Validategui validate=new Validategui();
		static MainFramegui mainFarame=null;
		static Logingui login=null;
		Check check=new Check();

		
		
		JPanel panel=null;
		
		
	   protected MainController() {
	      // Exists only to defeat instantiation.
	   }
	   public static MainController getInstance() {
	      if(instance == null) {
	         instance = new MainController();
	      }
	      return instance;
	   }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 login=new Logingui();

		

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch(e.getActionCommand())
		{
		case "LogIn":
			if(login.getTxtUserhere().isEmpty()||login.getTxtPassword().isEmpty())
			{
			   System.out.println(" please enter the details in two feilds");
				login.dispose();
			    login=new Logingui();
			   
			}

			else if(ConnectToServer.Checkuser(login.getTxtUserhere(),login.getTxtPassword()))
			{
				
				
			 mainFarame=new MainFramegui();
			login.dispose();
			}
			else
			{
				mainFarame=new MainFramegui();
				if(panel!=null)
				{
					 
			mainFarame.getContentPane().remove(panel);
			mainFarame.getContentPane().add(register);
			panel=register;
			mainFarame.repaint();
				
			login.dispose();
				}
			else
			{
			
				mainFarame.getContentPane().add(register);
				panel=register;
				mainFarame.repaint();
				login.dispose();
		
			}
		
			
			}
			break;
			
		case "about us":
			if(panel!=null)
			mainFarame.getContentPane().remove(panel);
			mainFarame.getContentPane().add(aboutus);
			panel=aboutus;
			mainFarame.repaint();
			break;
		case "check feedback":
			//if(ConnectToServer.Checkchecks( checkfeedback.getchecknumber(), checkfeedback.getamount(), checkfeedback.getdate(), checkfeedback.getid(), checkfeedback.getcheckstatus()))
			if(panel!=null)
			mainFarame.getContentPane().remove(panel);
			mainFarame.getContentPane().add(checkfeedback);
			panel=checkfeedback;
			mainFarame.repaint();
			break;
		case "history":
			
			if(panel!=null)
			mainFarame.getContentPane().remove(panel);
			mainFarame.getContentPane().add(history);
			panel=history;
			mainFarame.repaint();
			break;
		case "validate":
			
			if(panel!=null)
			mainFarame.getContentPane().remove(panel);
			mainFarame.getContentPane().add(validate);
			panel=validate;
			mainFarame.repaint();
			break;
		case "log out":
			if(panel!=null)
			mainFarame.getContentPane().remove(panel);
			mainFarame.dispose();
		
			break;
		
		}

		
	}

}
