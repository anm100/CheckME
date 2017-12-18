package checkme3;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;

import checkmeController.ConnectToServer;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Registergui extends JPanel {
	private JTextField username;
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
	password.setBounds(95, 141, 53, 14);
	add(password);
	
	username = new JTextField();
	username.setBounds(207, 97, 86, 20);
	add(username);
	username.setColumns(10);
	
	pass = new JTextField();
	pass.setBounds(207, 138, 86, 20);
	add(pass);
	pass.setColumns(10);
	
	JButton finish = new JButton("finish");
	finish.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			ConnectToServer.connectwriting(username.getText().toString(),pass.getText().toString(), mail.getText().toString());

		}
	});
	finish.setBounds(142, 242, 89, 23);
	add(finish);
	
	JLabel email = new JLabel("email");
	email.setHorizontalAlignment(SwingConstants.CENTER);
	email.setBounds(95, 182, 46, 14);
	add(email);
	
	mail = new JTextField();
	mail.setBounds(207, 179, 86, 20);
	add(mail);
	mail.setColumns(10);
	setVisible(true);
	}

	

}
