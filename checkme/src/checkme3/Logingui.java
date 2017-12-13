package checkme3;

import javax.swing.*;

import application.MainController;

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
		public Logingui(){
			setTitle("log in page");
			frame1();
			setVisible(true);
		}
		 public void frame1(){
			 
			 getContentPane().setLayout(null);
			 
			 JLabel lblNewLabel = new JLabel("user name");
			 lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
			 lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			 lblNewLabel.setBounds(30, 140, 118, 24);
			 getContentPane().add(lblNewLabel);
			 
			 JLabel lblNewLabel_1 = new JLabel("password");
			 lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 15));
			 lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			
			 lblNewLabel_1.setBounds(48, 187, 89, 24);
			 getContentPane().add(lblNewLabel_1);
			 
			 JLabel lblNewLabel_2 = new JLabel("log in page");
			 lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
			 lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 15));
			
			 lblNewLabel_2.setBounds(153, 30, 137, 24);
			 getContentPane().add(lblNewLabel_2);
			 
			 txtUserhere = new JTextField();
			 txtUserhere.setHorizontalAlignment(SwingConstants.TRAILING);
			 txtUserhere.setBounds(182, 144, 86, 20);
			 txtUserhere.setVisible(true);
			 getContentPane().add(txtUserhere);
			 txtUserhere.setColumns(10);
			 
			 txtPassword = new JTextField();
			 txtPassword.setHorizontalAlignment(SwingConstants.TRAILING);
			 txtPassword.setBounds(182, 190, 86, 20);
			 txtPassword.setVisible(true);
			 getContentPane().add(txtPassword);
			 txtPassword.setColumns(10);
			 
			 JButton btnNewButton = new JButton("log in");
			 btnNewButton.setActionCommand("LogIn");
			 btnNewButton.setHorizontalAlignment(SwingConstants.TRAILING);
			 btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 15));
			 btnNewButton.setBounds(166, 241, 89, 23);
			 //btnNewButton.setVisible(true);
			 getContentPane().add(btnNewButton);
			 
			 JButton forgetpass = new JButton("forget password");
			 forgetpass.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent arg0) {
			 		
			 		
			 		
			 		
			 		
			 		
			 	}
			 });
			 forgetpass.setFont(new Font("Times New Roman", Font.BOLD, 18));
			 forgetpass.setBounds(258, 242, 155, 23);
			 getContentPane().add(forgetpass);
			 
			 JLabel newinfo = new JLabel("please enter details in two feilds");
			 newinfo.addComponentListener(new ComponentAdapter() {
			 
			 	
			 });
			 newinfo.setFont(new Font("Times New Roman", Font.BOLD, 16));
			 newinfo.setHorizontalAlignment(SwingConstants.CENTER);
			 newinfo.setBounds(80, 85, 278, 24);
			 getContentPane().add(newinfo);
			
			setSize(477, 452);
			 
			 
			 btnNewButton.addActionListener(MainController.getInstance());
		 }
			public String getTxtUserhere() {
				return txtUserhere.getText().toString();
			}
			public String getTxtPassword() {
				return txtPassword.getText().toString();
			}
	}

