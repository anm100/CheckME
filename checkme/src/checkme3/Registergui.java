package checkme3;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;

import Utils.Messages;
import application.myApp;
import checkmeController.ConnectToServer;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Registergui extends JFrame {
	private JTextField username;
	private Registergui thisref=this;
    
   	JPanel panel=null;
	public JTextField getUsername() {
		return username;
	}

	public void setUsername(JTextField username) {
		this.username = username;
	}

	public JTextField getPass() {
		return pass;
	}

	public void setPass(JTextField pass) {
		this.pass = pass;
	}

	public JTextField getMail() {
		return mail;
	}

	public  void setMail(JTextField mail) {
		this.mail = mail;
	}

	private JTextField pass;
	private JTextField mail;
	
	public Registergui()
	{
	setBounds(45, 124, 525, 380);
	getContentPane().setLayout(null);
	setBackground(Color.WHITE);
	
	JLabel register = new JLabel("register");
	register.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 25));
	register.setHorizontalAlignment(SwingConstants.CENTER);
	register.setBounds(95, 26, 255, 26);
	getContentPane().add(register);
	
	JLabel firstname = new JLabel("username");
	firstname.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
	firstname.setHorizontalAlignment(SwingConstants.CENTER);
	firstname.setBounds(68, 97, 80, 33);
	getContentPane().add(firstname);
	
	JLabel password = new JLabel("password");
	password.setHorizontalAlignment(SwingConstants.CENTER);
	password.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
	password.setBounds(68, 141, 80, 30);
	getContentPane().add(password);
	
	username = new JTextField();
	username.setBounds(207, 97, 86, 20);
	getContentPane().add(username);
	username.setColumns(10);
	
	pass = new JTextField();
	pass.setBounds(207, 138, 86, 20);
	getContentPane().add(pass);
	pass.setColumns(10);
	
	JButton finish = new JButton("finish");
	finish.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
	finish.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!username.getText().isEmpty()&&!pass.getText().isEmpty()&&!mail.getText().isEmpty())
			{
				ConnectToServer.connectwriting(username.getText().toString(),pass.getText().toString(), mail.getText().toString());
				Messages.successMessage( "register ok","register",null);
				thisref.dispose();
				myApp.login();
			}
			
			else{
				
				
				
				
				Messages.errorMessage(" insert correct details again!!","register",null);
			
				new Registergui();
				
			}

			
		}
	});
	finish.setBounds(109, 239, 89, 33);
	getContentPane().add(finish);
	
	JLabel email = new JLabel("email");
	email.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
	email.setHorizontalAlignment(SwingConstants.CENTER);
	email.setBounds(68, 182, 73, 17);
	getContentPane().add(email);
	
	mail = new JTextField();
	mail.setBounds(207, 179, 86, 20);
	getContentPane().add(mail);
	mail.setColumns(10);
	
	JButton back1 = new JButton("back");
	back1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		
			new Logingui();
			thisref.dispose();
			
		}
	});
	back1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
	back1.setBounds(224, 239, 89, 33);
	getContentPane().add(back1);
	setVisible(true);
	}

	

}
