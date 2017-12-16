package checkme3;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import checkmeController.ConnectToServer;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Registergui extends JPanel {
	private JTextField username;
	private JTextField pass;
	private JTextField confirmpass;
	private JTextField mail;
	private JTextField confirmmail;
	
	public Registergui()
	{
	setBounds(45, 124, 474, 432);
	setLayout(null);
	setBackground(Color.orange);
	
	JLabel register = new JLabel("register");
	register.setFont(new Font("Times New Roman", Font.BOLD, 24));
	register.setHorizontalAlignment(SwingConstants.CENTER);
	register.setBounds(95, 26, 255, 26);
	add(register);
	
	JLabel firstname = new JLabel("username");
	firstname.setHorizontalAlignment(SwingConstants.CENTER);
	firstname.setBounds(84, 100, 64, 14);
	add(firstname);
	
	JLabel password = new JLabel("password");
	password.setBounds(95, 231, 53, 14);
	add(password);
	
	username = new JTextField();
	username.setBounds(207, 97, 86, 20);
	add(username);
	username.setColumns(10);
	
	pass = new JTextField();
	pass.setBounds(207, 228, 86, 20);
	add(pass);
	pass.setColumns(10);
	
	JButton finish = new JButton("finish");
	finish.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			ConnectToServer.connectwriting(username.getText().toString(),pass.getText().toString(), mail.getText().toString());

		}
	});
	finish.setBounds(159, 383, 89, 23);
	add(finish);
	
	JLabel confirmpassword = new JLabel("conifirm password");
	confirmpassword.setHorizontalAlignment(SwingConstants.CENTER);
	confirmpassword.setBounds(59, 274, 102, 14);
	add(confirmpassword);
	
	confirmpass = new JTextField();
	confirmpass.setBounds(207, 271, 86, 20);
	add(confirmpass);
	confirmpass.setColumns(10);
	
	JLabel email = new JLabel("email");
	email.setHorizontalAlignment(SwingConstants.CENTER);
	email.setBounds(91, 313, 46, 14);
	add(email);
	
	mail = new JTextField();
	mail.setBounds(207, 310, 86, 20);
	add(mail);
	mail.setColumns(10);
	
	JLabel confirmemail = new JLabel("confirm email");
	confirmemail.setHorizontalAlignment(SwingConstants.CENTER);
	confirmemail.setBounds(59, 351, 118, 14);
	add(confirmemail);
	
	confirmmail = new JTextField();
	confirmmail.setBounds(207, 348, 86, 20);
	add(confirmmail);
	confirmmail.setColumns(10);
	setVisible(true);
	}

}
