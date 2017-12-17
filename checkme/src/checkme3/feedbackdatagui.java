package checkme3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Utils.MyTableModel;
import application.myApp;
import checkmeController.Check;
import checkmeController.ConnectToServer;

import java.awt.event.*;
import java.util.ArrayList;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Dimension;
public class feedbackdatagui extends JFrame{

	
	private JLabel  lblNewLabel_2;
	private JLabel lblNewLabel;
	   private JButton btnNewButton;
	   private JTextField checkstatus2;
	   private JTextField amount2;
	   private JTextField datee;
	   private JTextField checknum1;
	   ArrayList <Check> arr = new ArrayList<Check>();
		private feedbackdatagui thisref=this;
		public feedbackdatagui(String hash,String amount,String date,String checkstatus){
			
			setTitle("log in page");
			getContentPane().setLayout(null);
			
			JRadioButton honor = new JRadioButton("honored");
			honor.setHorizontalAlignment(SwingConstants.LEFT);
			honor.setBounds(6, 265, 109, 23);
			getContentPane().add(honor);
			
			JRadioButton rejectedtec = new JRadioButton("reject technically");
			rejectedtec.setBounds(6, 291, 109, 23);
			getContentPane().add(rejectedtec);
			
			JRadioButton rejectnomoney = new JRadioButton("reject not enough money");
			rejectnomoney.setBounds(6, 317, 123, 23);
			getContentPane().add(rejectnomoney);
			
			checkstatus2 = new JTextField();
			checkstatus2.setBounds(104, 194, 86, 20);
			getContentPane().add(checkstatus2);
			checkstatus2.setColumns(10);
			
			amount2 = new JTextField();
			amount2.setBounds(104, 163, 86, 20);
			getContentPane().add(amount2);
			amount2.setColumns(10);
			
			datee = new JTextField();
			datee.setBounds(104, 132, 86, 20);
			getContentPane().add(datee);
			datee.setColumns(10);
			
			checknum1 = new JTextField();
			checknum1.setBounds(104, 101, 86, 20);
			getContentPane().add(checknum1);
			checknum1.setColumns(10);
			
			JLabel checknum = new JLabel("check number");
			checknum.setHorizontalAlignment(SwingConstants.CENTER);
			checknum.setBounds(6, 104, 88, 14);
			getContentPane().add(checknum);
			
			JLabel date1 = new JLabel("date");
			date1.setHorizontalAlignment(SwingConstants.CENTER);
			date1.setBounds(22, 135, 46, 14);
			getContentPane().add(date1);
			
			JLabel amount1 = new JLabel("amount");
			amount1.setHorizontalAlignment(SwingConstants.CENTER);
			amount1.setBounds(22, 166, 46, 14);
			getContentPane().add(amount1);
			
			JLabel checkstatus1 = new JLabel("check status");
			checkstatus1.setHorizontalAlignment(SwingConstants.CENTER);
			checkstatus1.setBounds(22, 197, 72, 14);
			getContentPane().add(checkstatus1);
			
			JButton changestatus = new JButton("change status");
			
			changestatus.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					
					
					
					JsonParser parser = new JsonParser();
					
					JsonArray arr = new JsonArray();
					JsonObject jsnobject = (JsonObject) parser.parse(ConnectToServer.getCheckhistory(getchecknum1()));

					JsonArray jsonArray = jsnobject.getAsJsonArray("Checks");
					for (int i = 0; i < jsonArray.size(); i++) {
						JsonObject e = jsonArray.get(i).getAsJsonObject();
					    
						//model.addRow(new Object[] { e.get("hash"),e.get("amount"),e.get("date"),e.get("checkstatus") });
						thisref.arr.add(new Check(e.get("hash").toString(),e.get("amount").toString(),e.get("date").toString(),e.get("checkstatus").toString()));
					}
					System.out.println("this array json"+jsonArray.toString());
					
					JsonArray arr2 = new JsonArray();
					JsonObject jsnobject2 = (JsonObject) parser.parse(ConnectToServer.savehistorysCheckfeedback(getchecknum1(), getdatee(),getcheckstatus2()));

					JsonArray jsonArray2 = jsnobject.getAsJsonArray("Checks");
					for (int i = 0; i < jsonArray.size(); i++) {
						JsonObject e = jsonArray.get(i).getAsJsonObject();
					    
						//model.addRow(new Object[] { e.get("hash"),e.get("amount"),e.get("date"),e.get("checkstatus") });
						thisref.arr.add(new Check(e.get("hash").toString(),e.get("amount").toString(),e.get("date").toString(),e.get("checkstatus").toString()));
					}
					System.out.println("this array json"+jsonArray.toString());
					
					
					
					
					JsonArray arr3= new JsonArray();
					JsonObject jsnobject3 = (JsonObject) parser.parse(ConnectToServer.savestatusCheckfeedback(getchecknum1(),getamount2(), getdatee(),getcheckstatus2()));

					JsonArray jsonArray3 = jsnobject.getAsJsonArray("Checks");
					for (int i = 0; i < jsonArray.size(); i++) {
						JsonObject e = jsonArray.get(i).getAsJsonObject();
					    
						//model.addRow(new Object[] { e.get("hash"),e.get("amount"),e.get("date"),e.get("checkstatus") });
						thisref.arr.add(new Check(e.get("hash").toString(),e.get("amount").toString(),e.get("date").toString(),e.get("checkstatus").toString()));
					}
					System.out.println("this array json"+jsonArray.toString());
					
				}
			});
			changestatus.setBounds(121, 291, 109, 23);
			getContentPane().add(changestatus);
			
			JLabel statusfeedback = new JLabel("status feed back for check");
			statusfeedback.setHorizontalAlignment(SwingConstants.CENTER);
			statusfeedback.setBounds(22, 32, 133, 14);
			getContentPane().add(statusfeedback);
			
			JLabel historyfeedback = new JLabel("history feed back for check");
			historyfeedback.setHorizontalAlignment(SwingConstants.CENTER);
			historyfeedback.setBounds(266, 32, 139, 14);
			getContentPane().add(historyfeedback);
			frame1(hash, amount, date, checkstatus);
			setVisible(true);
			
			
			
			
			
			
		}
		 
		 
		private void frame1(String hash, String amount, String date, String checkstatus) {
			// TODO Auto-generated method stub
			
		}


		public String getcheckstatus2() {
			return checkstatus2.getText().toString();
		}
		public String getamount2() {
			return amount2.getText().toString();
		}
		public String getdatee() {
			return datee.getText().toString();
		}
		public String getchecknum1() {
			return checknum1.getText().toString();
		}
		 
		 
		 
		 

	}

