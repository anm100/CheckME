package checkme3;

import javax.swing.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
import java.awt.Component;
public class EditCheckFeedbackGUI extends JFrame{
	private JLabel  lblNewLabel_2;
	private JLabel lblNewLabel;
	   private JButton btnNewButton;
		private EditCheckFeedbackGUI thisref=this;
		JPanel editCheckFeedback;
		JPanel panel=null;
		private String checkHistoryDATA=new String("");
		private JTextField amount2;
		private JTextField datee;
		private JTextField checknum1;
		private JTextField checkstatus2;
	    ArrayList <Check> arr = new ArrayList<Check>();
		private JRadioButton honor;
		private JRadioButton rejectedtec;
		private JRadioButton rejectnomoney;
		private JsonParser parser = new JsonParser();

		
		public EditCheckFeedbackGUI(Check check){

		
			frame1(check);
			setVisible(true);
		}
		 public void frame1(Check check){
			// getContentPane().add(lblNewLabel_1);
			 String hashid=check.getCheckNum();
			 JLabel lblNewLabel_2 = new JLabel("");
			 lblNewLabel_2.setIcon(new ImageIcon("C:\\\\finalProject\\\\CheckME\\\\checkme\\\\src\\\\image\\\\checkmelogo.png"));
			 lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
			 lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 15));
			
			 lblNewLabel_2.setBounds(5, -4, 379, 96);
		//	 getContentPane().add(btnRegister);

			  editCheckFeedback = new JPanel();
			 editCheckFeedback.setBounds(0, 0, 460, 458);
			 getContentPane().add(editCheckFeedback);
			// getContentPane().remove(lohinPanel);
			 //getContentPane().add(register);
			 editCheckFeedback.setLayout(null);
			editCheckFeedback.add(lblNewLabel_2);
								
									
									//loginPanel.setVisible(false);
			
			setSize(405, 467);
			 setVisible(true);
			 getContentPane().setLayout(null);
			 getContentPane().add(editCheckFeedback);
			 
			 JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			 tabbedPane.setBounds(15, 103, 364, 326);
			 editCheckFeedback.add(tabbedPane);
			 
			 JPanel panel_1 = new JPanel();
			 tabbedPane.addTab("status feed back for check", null, panel_1, null);
			 panel_1.setLayout(null);
			 
			 JRadioButton honor = new JRadioButton("honored");
			 honor.setBounds(6, 155, 65, 23);
			 panel_1.add(honor);
			 honor.setHorizontalAlignment(SwingConstants.LEFT);
			 
			 JRadioButton rejectedtec = new JRadioButton("reject technically");
			 rejectedtec.setBounds(92, 155, 107, 23);
			 panel_1.add(rejectedtec);
			 
			 JRadioButton rejectnomoney = new JRadioButton("reject not enough money");
			 rejectnomoney.setBounds(206, 155, 147, 23);
			 panel_1.add(rejectnomoney);
			 
			 amount2 = new JTextField();
			 amount2.setText(check.getamount());
			 amount2.setBounds(145, 104, 86, 20);
			 panel_1.add(amount2);
			 amount2.setColumns(10);
			 
			 JLabel label = new JLabel("check number");
			 label.setBounds(32, 49, 86, 14);
			 panel_1.add(label);
			 label.setHorizontalAlignment(SwingConstants.CENTER);
			 
			 JLabel label_1 = new JLabel("date");
			 label_1.setBounds(32, 79, 46, 14);
			 panel_1.add(label_1);
			 label_1.setHorizontalAlignment(SwingConstants.CENTER);
			 
			 datee = new JTextField();
			 datee.setBounds(145, 76, 86, 20);
			 
			 datee.setText(check.getdate());
			 panel_1.add(datee);
			 datee.setColumns(10);
			 
			 checknum1 = new JTextField();
			 checknum1.disable();
			 checknum1.setBounds(145, 46, 86, 20);
			 checknum1.setText(check.getCheckNum());
			 panel_1.add(checknum1);
			 checknum1.setColumns(10);
			 
			 JLabel label_2 = new JLabel("amount");
			 label_2.setBounds(42, 107, 46, 14);
			 panel_1.add(label_2);
			 label_2.setHorizontalAlignment(SwingConstants.CENTER);
			 
			 checkstatus2 = new JTextField();
			 checkstatus2.setText(check.getcheckstatus());
			 checkstatus2.setBounds(145, 15, 86, 20);
			 panel_1.add(checkstatus2);
			 checkstatus2.setColumns(10);
			 
			 JLabel label_3 = new JLabel("check status");
			 label_3.setBounds(32, 18, 72, 14);
			 panel_1.add(label_3);
			 label_3.setHorizontalAlignment(SwingConstants.CENTER);
			 
			 JButton button = new JButton("change status");
			 button.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent e) {
			 		System.out.println("start save check feedback");
					String jsnobject3 =(String) ConnectToServer.savestatusCheckfeedback(getchecknum1(),getamount2(), getdatee(),getcheckstatus2());
					System.out.println(jsnobject3);
					
					//////////////////
			 		System.out.println("start save check history");

					String jsnobject1 =(String) ConnectToServer.savehistorysCheckfeedback(getchecknum1(), getdatee(),getcheckstatus2());
					System.out.println(jsnobject1);
					thisref.dispose();

			 	}
					

					
			 	
			 });
			 
			 button.setBounds(102, 212, 139, 23);
			 panel_1.add(button);
			 
			 JPanel panel_2 = new JPanel();
			 tabbedPane.addTab("history feed back for check", null, panel_2, null);
			 panel_2.setLayout(null);
			 
			 JScrollPane scrollPane = new JScrollPane();
			 scrollPane.setBounds(0, 11, 359, 261);
			 panel_2.add(scrollPane);
			 
			 JTextArea textArea = new JTextArea();
			 scrollPane.setViewportView(textArea);
			 textArea.setText("");
				JsonParser parser = new JsonParser();
				JsonObject	 jsnobject = (JsonObject) parser.parse(ConnectToServer.gethistoryCheckfeedback(hashid));
				
				 JsonArray	jsonArray = jsnobject.getAsJsonArray("Checks");
				if(jsonArray !=null){
					
				
				for (int i = 0; i < jsonArray.size(); i++) {
					JsonObject e = jsonArray.get(i).getAsJsonObject();
					checkHistoryDATA+=" data:"+e.get("date").toString()+" status:"+e.get("checkstatus").toString()+"\n";
				}
				textArea.setText(checkHistoryDATA);
				}else {
					textArea.setText("not have history yet");
				}
		 }
			public String getcheckstatus2() {
				return checkstatus2.getText().toString();
			}
			public void setcheckstatus2 ( String checkstatus ) {
				  this.checkstatus2.setText(checkstatus);
				}
			public String getamount2() {
				return amount2.getText().toString();
			}
			public void setamount2 ( String amount ) {
				  this.amount2.setText(amount);
				}
			public String getdatee() {
				return datee.getText().toString();
			}
			public void setdatee ( String date ) {
				  this.datee.setText(date);
				}
			public String getchecknum1() {
				return checknum1.getText().toString();
			}
			public void setchecknum1 ( String CheckNumber ) {
				  this.checknum1.setText(CheckNumber);
				}
			
			
		
			public String getHonorSelected() {
				return" honored";
			}
			public void setHonorSelected(boolean selected) {
				honor.setSelected(selected);
				honor.setText("honored");
			}
			public String getRejectedtecSelected() {
				return "rejectedtechnacly";
			}
			public void setRejectedtecSelected(boolean selected_1) {
				rejectedtec.setSelected(selected_1);
				rejectedtec.setText("rejected technacely");
			}
			public String getRejectnomoneySelected() {
				return "reject no enough money";
			}
			public void setRejectnomoneySelected(boolean selected_2) {
				rejectnomoney.setSelected(selected_2);
				rejectnomoney.setText(" reject no enough money");
			}
	}

