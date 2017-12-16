package application;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.UIManager;

import checkme3.Historygui;
import checkme3.Logingui;
import checkme3.MainFramegui;
import checkme3.Validategui;
import checkmeController.Check;
import checkmeController.ConnectToServer;
import checkme3.Aboutusgui;
import checkme3.Checkfeedbackgui;
import checkme3.Registergui;
 public class myApp  implements  ActionListener {
	 private static final int newinfo = 0;

	private static myApp instance = null;
	 
		Historygui history =new Historygui();
		Aboutusgui  aboutus =new Aboutusgui();
		Checkfeedbackgui checkfeedback= new Checkfeedbackgui();
		Registergui register=new Registergui();
		Validategui validate=new Validategui();
		static MainFramegui mainFarame=null;
		static Logingui login=null;
		Check check=new Check();

		
		
		JPanel panel=null;
		
		
	   protected myApp() {
	      // Exists only to defeat instantiation.
	   }
	   public static myApp getInstance() {
	      if(instance == null) {
	         instance = new myApp();
	      }
	      return instance;
	   }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
            //here you can put the selected theme class name in JTattoo
            UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
 
        } catch (ClassNotFoundException ex) {
        //    java.util.logging.Logger.getLogger(PC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
        //    java.util.logging.Logger.getLogger(PC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
        //    java.util.logging.Logger.getLogger(PC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            //java.util.logging.Logger.getLogger(PC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
			
			break;
			
		case "about us":
			if(panel!=null)
			mainFarame.getContentPane().remove(panel);
			mainFarame.getContentPane().add(aboutus);
			panel=aboutus;
			mainFarame.repaint();
			break;
		case "_register":
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
