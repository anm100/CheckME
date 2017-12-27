package application;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import checkme3.Historygui;
import checkme3.FeedbackGUI;
import checkme3.Logingui;

import checkme3.MainFramegui;
import checkme3.Validategui;
import checkmeController.Check;
import checkmeController.ConnectToServer;
import checkme3.Aboutusgui;
import checkme3.Checkfeedbackgui;
import checkme3.Registergui;
import javax.swing.JTextArea;
 public class myApp  implements  ActionListener {
	 private static final int newinfo = 0;

	private static myApp instance = null;
	 	private static String username ;
	 	private static String mail ;
	
		Historygui history =new Historygui();
		Aboutusgui  aboutus =new Aboutusgui();
		Checkfeedbackgui checkfeedback;
		Registergui register=new Registergui();
		Validategui validate=new Validategui();
		static MainFramegui mainFarame=null;
		
		static Logingui login=null;
		

		
		
		
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
		 login.getContentPane().setLayout(null);
	 
		

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
			else if(!login.getTxtUserhere().isEmpty()&&login.getTxtPassword().isEmpty())
			   {
				myApp.setUsername(login.getTxtUserhere());
				myApp.setMail(ConnectToServer.getforgetpassword((ConnectToServer.getforgetmail(login.getTxtUserhere()))));
				
				
			   }
			   

			else if(ConnectToServer.Checkuser(login.getTxtUserhere(),login.getTxtPassword())==false)
			{
				
			}
			
			
			
			else 
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
		
		case "check feedback":
			
			if(panel!=null)
			mainFarame.getContentPane().remove(panel);
			checkfeedback=new Checkfeedbackgui();
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
	
	public static String getMail() {
		return mail;
	}
	public static void setMail(String mail) {
		myApp.mail = mail;
	}
	public static String getUsername() {
		return username;
	}
	public static void setUsername(String username) {
		myApp.username = username;
	}
}
