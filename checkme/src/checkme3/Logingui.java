package checkme3;

import javax.swing.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Utils.Messages;
import application.myApp;
import checkmeController.ConnectToServer;

import java.awt.event.*;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Dimension;
public class Logingui extends JFrame{

	private JTextField txtUserhere;
	private JTextField txtPassword;
	private JLabel  lblNewLabel_2;
	private JLabel lblNewLabel;
	   private JButton btnNewButton;
		private Logingui thisref=this;
		static MainFramegui mainFarame=null;
		JPanel loginPanel;
		 forgetpasswordgui forgetpassword;
		static Logingui login=null;
		Registergui register;
		JPanel panel=null;
		
		public Logingui(){

		
			frame1();
			setVisible(true);
		}
		 public void frame1(){
			// register= new Registergui();
	
			 JLabel lblNewLabel = new JLabel("Username");
			 lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
			 lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			 lblNewLabel.setBounds(47, 107, 118, 24);
			 //getContentPane().add(lblNewLabel);
			 
			 JLabel lblNewLabel_1 = new JLabel("Password");
			 lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
			 lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			
			 lblNewLabel_1.setBounds(57, 153, 89, 24);
			// getContentPane().add(lblNewLabel_1);
			 
			 JLabel lblNewLabel_2 = new JLabel("");
			 lblNewLabel_2.setIcon(new ImageIcon("C:\\\\finalProject\\\\CheckME\\\\checkme\\\\src\\\\image\\\\checkmelogo.png"));
			 lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
			 lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 15));
			
			 lblNewLabel_2.setBounds(10, 11, 397, 83);
			// getContentPane().add(lblNewLabel_2);
			 
			 txtUserhere = new JTextField();
			 txtUserhere.setHorizontalAlignment(SwingConstants.TRAILING);
			 txtUserhere.setBounds(158, 103, 162, 35);
			 txtUserhere.setVisible(true);
			// getContentPane().add(txtUserhere);
			 txtUserhere.setColumns(10);
			 
			 txtPassword = new JTextField();
			 txtPassword.setHorizontalAlignment(SwingConstants.TRAILING);
			 txtPassword.setBounds(158, 148, 162, 35);
			 txtPassword.setVisible(true);
			// getContentPane().add(txtPassword);
			 txtPassword.setColumns(10);
			 
			 JButton btnNewButton = new JButton("Log in");
			 btnNewButton.setActionCommand("LogIn");
			 btnNewButton.setHorizontalAlignment(SwingConstants.TRAILING);
			 btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 19));
			 btnNewButton.setBounds(183, 208, 89, 35);
			 //btnNewButton.setVisible(true);
			 //getContentPane().add(btnNewButton);
			 
			 JButton forgetpass = new JButton("Forget Password");
			 forgetpass.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent arg0) {
			 		
//					if(txtPassword.getText().isEmpty()&&txtUserhere.getText().isEmpty())
//					   {
//						Messages. warningMessage(" please enter the details in two feilds","forgotpassword",null);
//						thisref.dispose();
//						new Logingui();
//					    
//					   
//					    }
			 		
			 		
			 	
					
			 		forgetpassword= new forgetpasswordgui();
			 		 thisref.dispose();
		 	
			 	}
			 	
			 		
	
			 });
			 forgetpass.setFont(new Font("Times New Roman", Font.PLAIN, 18));
			 forgetpass.setBounds(149, 269, 155, 23);
			// getContentPane().add(forgetpass);
			 
			 JButton btnRegister = new JButton("Register");
			 btnRegister.addActionListener(new ActionListener() {
				 	public void actionPerformed(ActionEvent arg0) {
				 		
				 		
				 		
				 	 register= new Registergui();
				 	 thisref.dispose();
				 		
						
					
				 		
				 	}
				 });
		
			 btnRegister.addActionListener(myApp.getInstance());
			 btnRegister.setActionCommand("register");
			 btnRegister.setFont(new Font("Times New Roman", Font.PLAIN, 18));
			 btnRegister.setBounds(149, 305, 155, 23);
		//	 getContentPane().add(btnRegister);

			  loginPanel = new JPanel();
			 loginPanel.setBounds(0, 0, 474, 360);
			 getContentPane().add(loginPanel);
			// getContentPane().remove(lohinPanel);
			 //getContentPane().add(register);
			 loginPanel.setLayout(null);
			 loginPanel.add(txtUserhere);
			 loginPanel.add(txtPassword);
			loginPanel.add(btnRegister);
			loginPanel.add(lblNewLabel_2);
			loginPanel.add(lblNewLabel);
					loginPanel.add(lblNewLabel_1);
							loginPanel.add(btnNewButton);
									loginPanel.add(forgetpass);
								
									
									//loginPanel.setVisible(false);
			
			setSize(433, 399);
			 
			 btnNewButton.addActionListener(myApp.getInstance());
			 setVisible(true);
			 getContentPane().setLayout(null);
			 //getContentPane().add(register);
			// getContentPane().remove(register);
			 getContentPane().add(loginPanel);
		 }
		 
			public String getTxtUserhere() {
				return txtUserhere.getText().toString();
				
				
			}
			public String getTxtPassword() {
				return txtPassword.getText().toString();
			}
			public void setTxtPassword(String password) {
				
				 this.txtPassword.setText(password);
			}
	}

