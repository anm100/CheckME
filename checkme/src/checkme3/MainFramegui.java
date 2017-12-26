package checkme3;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.border.MatteBorder;

import application.myApp;

import javax.swing.UIManager;
import java.awt.Font;
import javax.swing.ImageIcon;

public  class MainFramegui extends JFrame  {

	public MainFramegui() {
		setSize(1000,1000);
		setTitle("main screen");
		getContentPane().setLayout(null);
		setVisible(true);
		
		JButton b1 = new JButton("about us");
		b1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		b1.setActionCommand("about us");
		b1.setBounds(897, 132, 89, 23);
		getContentPane().add(b1);
		b1.addActionListener(myApp.getInstance());
		JButton b2 = new JButton("check feedback");
		b2.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		b2.setActionCommand("check feedback");
		b2.setBounds(862, 177, 137, 23);
		getContentPane().add(b2);
		b2.addActionListener(myApp.getInstance());
	
		
		JLabel label = new JLabel("New label");
		label.setForeground(Color.WHITE);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(66, 101, 268, 118);

		JButton b3 = new JButton("history");
		b3.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		b3.setActionCommand("history");
		b3.setBounds(910, 238, 89, 23);
		getContentPane().add(b3);
		b3.addActionListener(myApp.getInstance());
		JButton b5 = new JButton("validate");
		b5.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		b5.setActionCommand("validate");
		b5.setBounds(897, 290, 89, 23);
		getContentPane().add(b5);

		b5.addActionListener(myApp.getInstance());
		
		JButton b6 = new JButton("log out");
		b6.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		b6.setActionCommand("log out");
		b6.setBounds(897, 338, 89, 23);
		getContentPane().add(b6);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon("C:\\\\finalProject\\\\CheckME\\\\checkme\\\\src\\\\image\\\\checkmelogo.png"));
		label_1.setBounds(226, 11, 533, 132);
		getContentPane().add(label_1);
		b6.addActionListener(myApp.getInstance());
	}
}
