package checkme3;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import checkmeController.ConnectToServer;
import application.myApp;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class forgetpasswordgui  extends JFrame {
	private forgetpasswordgui thisref=this;
	private JTextField mail1;
	public forgetpasswordgui() {
		setBounds(100, 100,550, 500);
		setVisible(true);
		setBackground(Color.white);
		setBackground(Color.LIGHT_GRAY);
		setForeground(Color.BLACK);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(10, 5, 555, 99);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon("C:\\finalProject\\CheckME\\checkme\\src\\image\\checkmelogo.png"));
		add(lblNewLabel);
		
		mail1 = new JTextField();
		mail1.setBounds(270, 172, 195, 25);
		mail1.setHorizontalAlignment(SwingConstants.CENTER);
		add(mail1);
		mail1.setColumns(10);
		
		JLabel email1 = new JLabel("email");
		email1.setBounds(127, 171, 133, 25);
		email1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		email1.setHorizontalAlignment(SwingConstants.CENTER);
		add(email1);
		
		JLabel deatils = new JLabel("");
		deatils.setBounds(32, 231, 316, 122);
		deatils.setHorizontalAlignment(SwingConstants.CENTER);
		add(deatils);
		
		JButton getpassword = new JButton("get password");
		getpassword.setBounds(417, 276, 121, 35);
		getpassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
			 			JsonParser parser = new JsonParser();
			 			
			 			JsonArray arr = new JsonArray();
			 			JsonObject jsnobject = (JsonObject) parser.parse(ConnectToServer.getforgetpassword((mail1.getText().toString())));

			 			JsonArray jsonArray = jsnobject.getAsJsonArray("Person");
			 			if(jsonArray !=null){
			 			for (int i = 0; i < jsonArray.size(); i++) {
			 				JsonObject r = jsonArray.get(i).getAsJsonObject();
			 				 deatils.setText("Username :"+r.get("username").getAsString()+" Password :"+  r.get("password").getAsString());
			 				
			 				 
                               myApp.setPassword(r.get("password").getAsString());
			 			}
			 			
					
			 		}
			 		}
			
		});
		getpassword.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		add(getpassword);
		
		JButton back1 = new JButton("back");
		back1.setBounds(417, 334, 121, 35);
		back1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				thisref.setVisible(false);
				new Logingui();
				
			}
		});
		back1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		add(back1);
	}
}
