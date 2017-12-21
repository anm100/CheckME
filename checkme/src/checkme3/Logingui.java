package checkme3;

import javax.swing.*;

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
		static MainFrameLogin mainFrame=null;
		static Logingui login=null;
		Registergui register= new Registergui();
		JPanel panel=null;
		
		public Logingui(){

			setTitle("log in page");
			frame1();
			setVisible(true);
		}
		 public void frame1(){
			 
			 getContentPane().setLayout(null);
			 
			 JLabel lblNewLabel = new JLabel("Username");
			 lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
			 lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			 lblNewLabel.setBounds(47, 107, 118, 24);
			 getContentPane().add(lblNewLabel);
			 
			 JLabel lblNewLabel_1 = new JLabel("Password");
			 lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
			 lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			
			 lblNewLabel_1.setBounds(57, 153, 89, 24);
			 getContentPane().add(lblNewLabel_1);
			 
			 JLabel lblNewLabel_2 = new JLabel("Log in - logo");
			 lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
			 lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 15));
			
			 lblNewLabel_2.setBounds(155, 36, 137, 24);
			 getContentPane().add(lblNewLabel_2);
			 
			 txtUserhere = new JTextField();
			 txtUserhere.setHorizontalAlignment(SwingConstants.TRAILING);
			 txtUserhere.setBounds(158, 103, 162, 35);
			 txtUserhere.setVisible(true);
			 getContentPane().add(txtUserhere);
			 txtUserhere.setColumns(10);
			 
			 txtPassword = new JTextField();
			 txtPassword.setHorizontalAlignment(SwingConstants.TRAILING);
			 txtPassword.setBounds(158, 148, 162, 35);
			 txtPassword.setVisible(true);
			 getContentPane().add(txtPassword);
			 txtPassword.setColumns(10);
			 
			 JButton btnNewButton = new JButton("Log in");
			 btnNewButton.setActionCommand("LogIn");
			 btnNewButton.setHorizontalAlignment(SwingConstants.TRAILING);
			 btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 19));
			 btnNewButton.setBounds(183, 208, 89, 35);
			 //btnNewButton.setVisible(true);
			 getContentPane().add(btnNewButton);
			 
			 JButton forgetpass = new JButton("Forget Password");
			 forgetpass.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent arg0) {
			 	
			 		 
			 	
					//ConnectToServer.getforgetpassword(mail);
			 	
			 	
			 		
			 		
			 	}
			 });
			 forgetpass.setFont(new Font("Times New Roman", Font.PLAIN, 18));
			 forgetpass.setBounds(149, 269, 155, 23);
			 getContentPane().add(forgetpass);
			 
			 JButton btnRegister = new JButton("Register");
			 btnRegister.addActionListener(new ActionListener() {
				 	public void actionPerformed(ActionEvent arg0) {
				 		
				 		 mainFrame=new MainFrameLogin();
						   if(panel!=null)
						   {
							
							mainFrame.getContentPane().remove(panel);
							mainFrame.getContentPane().add(register);
							panel=register;
							
							
							mainFrame.repaint();
						
							thisref.dispose();
						   }
						   
							else
							{
								mainFrame.getContentPane().add(register);
								panel=register;
								
                           
                              mainFrame.repaint();
                              thisref.dispose();
								
							}
				 	
				 		 
				 
				 		
				 	}
				 });
		
			 btnRegister.addActionListener(myApp.getInstance());
			 btnRegister.setActionCommand("register");
			 btnRegister.setFont(new Font("Times New Roman", Font.PLAIN, 18));
			 btnRegister.setBounds(149, 305, 155, 23);
			 getContentPane().add(btnRegister);
			
			setSize(433, 399);
			 
			 btnNewButton.addActionListener(myApp.getInstance());
			 setVisible(true);
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

