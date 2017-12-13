package checkme3;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import checkmeController.ConnectToServer;
import checkmeController.Check;

import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Checkfeedbackgui extends JPanel {
    Check check=new Check();
    
	
	private JTextField amount1;
	private JTextField date1;
	private JTextField checknumber1;
	private JTextField id1;
	private JTextField checkstatus1;

	


	


	
	
	public  Checkfeedbackgui ()
	{
		
		setBounds(45, 124, 469, 390);
		setLayout(null);
		setBackground(Color.MAGENTA);
		
		
		
		amount1 = new JTextField();
		amount1.setBounds(242, 106, 86, 20);
		add(amount1);
		amount1.setColumns(10);
		
		date1 = new JTextField();
		date1.setBounds(242, 156, 86, 20);
		add(date1);
		date1.setColumns(10);
		
		checknumber1 = new JTextField();
		checknumber1.setBounds(242, 58, 86, 20);
		add(checknumber1);
		checknumber1.setColumns(10);
		
		JLabel checknumber = new JLabel("check num");
		checknumber.setHorizontalAlignment(SwingConstants.CENTER);
		checknumber.setBounds(35, 60, 114, 17);
		add(checknumber);
		
		JLabel amount = new JLabel("amount");
		amount.setHorizontalAlignment(SwingConstants.CENTER);
		amount.setBounds(58, 109, 68, 14);
		add(amount);
		
		JLabel date = new JLabel("date");
		date.setHorizontalAlignment(SwingConstants.CENTER);
		date.setBounds(58, 159, 68, 14);
		add(date);
		
		JLabel checkfeedback = new JLabel("check feedback");
		checkfeedback.setFont(new Font("Times New Roman", Font.BOLD, 18));
		checkfeedback.setHorizontalAlignment(SwingConstants.CENTER);
		checkfeedback.setBounds(147, 11, 162, 38);
		add(checkfeedback);
		
		JButton finish = new JButton("finish");
		finish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {						
				if(ConnectToServer.Checkchecks( getchecknumber(),getamount(),getdate(),getid(),getcheckstatus()))
				{
					setCheckNumber(getchecknumber() );
				    setcheckstatus(getcheckstatus());
					setamount(getamount());
					setid(getid());
					setdate(getdate());
					 
					  
				}
				
				
				else
				{
					
				}
				
			}
		});
		finish.setBounds(180, 323, 89, 23);
		add(finish);
		
		JLabel id = new JLabel("id");
		id.setHorizontalAlignment(SwingConstants.CENTER);
		id.setBounds(74, 211, 46, 14);
		add(id);
		
		id1 = new JTextField();
		id1.setHorizontalAlignment(SwingConstants.CENTER);
		id1.setBounds(242, 208, 86, 20);
		add(id1);
		id1.setColumns(10);
		
		JLabel checkstatus = new JLabel("check status");
		checkstatus.setHorizontalAlignment(SwingConstants.CENTER);
		checkstatus.setBounds(74, 260, 86, 14);
		add(checkstatus);
		
		checkstatus1 = new JTextField();
		checkstatus1.setHorizontalAlignment(SwingConstants.CENTER);
		checkstatus1.setBounds(242, 257, 86, 20);
		add(checkstatus1);
		checkstatus1.setColumns(10);
		setVisible(true);
	}
	
	
	

	public void setamount ( String amount ) {
		  this.amount1.setText(amount);
		}
	public String getamount() {
		return amount1.getText().toString();
	}

	
	public void setid ( String id ) {
		  this.id1.setText(id);
		}
	public String getid () {
		return id1.getText().toString();
	}
	public void setcheckstatus ( String checkstatus ) {
		  this.checkstatus1.setText(checkstatus);
		}
	public String getcheckstatus() {
		return checkstatus1.getText().toString();
	}
	

	public void setdate ( String date ) {
		  this.date1.setText(date);
		}
	
	public String getdate() {
		return date1.getText().toString();
	}
	
	

	public void setCheckNumber ( String CheckNumber ) {
		  this.checknumber1.setText(CheckNumber);
		}
	
	public String getchecknumber() {
		return checknumber1.getText().toString();
	}
	
}
