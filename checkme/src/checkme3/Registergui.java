package checkme3;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Registergui extends JPanel {
	private JTextField fname;
	private JTextField lname;
	private JTextField pass;
	private JTextField id1;
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
	
	JLabel firstname = new JLabel("first name");
	firstname.setHorizontalAlignment(SwingConstants.CENTER);
	firstname.setBounds(84, 100, 64, 14);
	add(firstname);
	
	JLabel lastname = new JLabel("last name");
	lastname.setHorizontalAlignment(SwingConstants.CENTER);
	lastname.setBounds(84, 142, 64, 14);
	add(lastname);
	
	JLabel password = new JLabel("password");
	password.setBounds(95, 231, 53, 14);
	add(password);
	
	fname = new JTextField();
	fname.setBounds(207, 97, 86, 20);
	add(fname);
	fname.setColumns(10);
	
	lname = new JTextField();
	lname.setBounds(207, 142, 86, 20);
	add(lname);
	lname.setColumns(10);
	
	pass = new JTextField();
	pass.setBounds(207, 228, 86, 20);
	add(pass);
	pass.setColumns(10);
	
	JButton finish = new JButton("finish");
	finish.setBounds(159, 383, 89, 23);
	add(finish);
	
	JLabel id = new JLabel("id");
	id.setHorizontalAlignment(SwingConstants.CENTER);
	id.setBounds(84, 185, 64, 14);
	add(id);
	
	id1 = new JTextField();
	id1.setBounds(207, 187, 86, 20);
	add(id1);
	id1.setColumns(10);
	
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
