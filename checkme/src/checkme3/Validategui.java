package checkme3;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import application.myApp;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import checkmeController.CSVReader;
import checkmeController.ConnectToServer;
public class Validategui extends JPanel {
	private JTextField checknum1;
	private JTextField banknum1;
	private JTextField branchnum1;
	private JTextField accountnum1;
	private JTextField date;
	private JTextField amount;
	private JTextField id;
	String[] reader;
	public Validategui()
	{
	setBounds(45, 124, 724, 468);
	setLayout(null);
	setBackground(Color.LIGHT_GRAY);
	
	JLabel checknum = new JLabel("check num");
	checknum.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
	checknum.setHorizontalAlignment(SwingConstants.CENTER);
	checknum.setBounds(68, 93, 86, 23);
	add(checknum);
	
	checknum1 = new JTextField();
	checknum1.setBounds(183, 95, 86, 20);
	add(checknum1);
	checknum1.setColumns(10);
	
	banknum1 = new JTextField();
	banknum1.setBounds(183, 139, 86, 20);
	add(banknum1);
	banknum1.setColumns(10);
	
	branchnum1 = new JTextField();
	branchnum1.setBounds(183, 184, 86, 20);
	add(branchnum1);
	branchnum1.setColumns(10);
	
	accountnum1 = new JTextField();
	accountnum1.setHorizontalAlignment(SwingConstants.CENTER);
	accountnum1.setBounds(183, 229, 86, 20);
	add(accountnum1);
	accountnum1.setColumns(10);
	
	JButton save1 = new JButton("save");
	save1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			System.out.println(myApp.getUsername());
			System.out.println(ConnectToServer.connectwritingcheck(checknum1.getText(), banknum1.getText(), branchnum1.getText(), accountnum1.getText(),amount.getText(),
																	date.getText(),id.getText(),myApp.getUsername(),
																	reader[0]+reader[1]+reader[2]+reader[3]));
			
		}
	});
	save1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
	save1.setBounds(230, 390, 122, 23);
	save1.setVisible(false);
	add(save1);
	
	JButton scan1 = new JButton("scan");
	scan1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
	scan1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
			 reader=CSVReader.reader();
			if(reader!=null)
			{
			checknum1.setText(reader[0]);
			banknum1.setText(reader[1]);
			branchnum1.setText(reader[2]);
			accountnum1.setText(reader[3]);
			
			save1.setVisible(true);
			
			
			CSVReader.delete();
			}
			else
			{
				System.out.println(" plase insert a check  the  check reader again");
				
			}
			
			
		}
	});
	scan1.setBounds(86, 390, 110, 23);
	add(scan1);
	
	JLabel banknum = new JLabel("bank num");
	banknum.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
	banknum.setForeground(new Color(0, 0, 0));
	banknum.setHorizontalAlignment(SwingConstants.CENTER);
	banknum.setBounds(68, 142, 86, 17);
	add(banknum);
	
	JLabel branchnum = new JLabel("branch num");
	branchnum.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
	branchnum.setHorizontalAlignment(SwingConstants.CENTER);
	branchnum.setBounds(51, 175, 103, 37);
	add(branchnum);
	
	JLabel accountnum = new JLabel("account num");
	accountnum.setForeground(new Color(0, 0, 0));
	accountnum.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
	accountnum.setHorizontalAlignment(SwingConstants.CENTER);
	accountnum.setBounds(51, 229, 103, 29);
	add(accountnum);
	
	JLabel lblNewLabel_4 = new JLabel("validation");
	lblNewLabel_4.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 18));
	lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
	lblNewLabel_4.setBounds(176, 11, 222, 48);
	add(lblNewLabel_4);
	
	JLabel date1 = new JLabel("date");
	date1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
	date1.setHorizontalAlignment(SwingConstants.CENTER);
	date1.setBounds(122, 294, 74, 23);
	add(date1);
	
	date = new JTextField();
	date.setBounds(230, 296, 86, 20);
	add(date);
	date.setColumns(10);
	
	JLabel amount1 = new JLabel("amount");
	amount1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
	amount1.setHorizontalAlignment(SwingConstants.CENTER);
	amount1.setBounds(122, 328, 74, 23);
	add(amount1);
	
	amount = new JTextField();
	amount.setBounds(230, 327, 86, 20);
	add(amount);
	amount.setColumns(10);
	
	JLabel massege = new JLabel("insert the date and the amount of the check");
	massege.setForeground(Color.RED);
	massege.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
	massege.setHorizontalAlignment(SwingConstants.CENTER);
	massege.setBounds(36, 260, 380, 29);
	add(massege);
	
	JLabel id1 = new JLabel("id");
	id1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
	id1.setHorizontalAlignment(SwingConstants.CENTER);
	id1.setBounds(142, 362, 46, 14);
	add(id1);
	
	id = new JTextField();
	id.setBounds(230, 359, 86, 20);
	add(id);
	id.setColumns(10);
	setVisible(true);
	}
}
