package checkme3;

import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import application.Resources;
import checkmeController.Check;
import checkmeController.ConnectToServer;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.util.ArrayList;

public class FeedbackGUI  {
	private JLabel  lblNewLabel_2;
	private JLabel lblNewLabel;
	private String checkHistoryDATA=new String("");
	   private JButton btnNewButton;
	   private JTextField checkstatus2;
	   private JTextField amount2;
	   private JTextField datee;
	   private JTextField checknum1;
	   ArrayList <Check> arr = new ArrayList<Check>();
		private FeedbackGUI thisref=this;

	private JFrame disID;
	private JRadioButton honor;
	private JRadioButton rejectedtec;
	private JRadioButton rejectnomoney;

	public FeedbackGUI(String hashid) {
		initialize(hashid);
		
	
	}


	private void initialize(String hashid) {
		disID = new JFrame();
		disID.setTitle("rename-feedback");
		disID.getContentPane().setLayout(null);
		
		honor = new JRadioButton("honored");
		honor.setHorizontalAlignment(SwingConstants.LEFT);
		honor.setBounds(6, 265, 109, 23);
		disID.getContentPane().add(honor);
		rejectedtec = new JRadioButton("reject technically");
		rejectedtec.setSelected(true);
		rejectedtec.setBounds(6, 291, 109, 23);
		disID.getContentPane().add(rejectedtec);
		
		rejectnomoney = new JRadioButton("reject not enough money");
		rejectnomoney.setSelected(true);
		rejectnomoney.setBounds(6, 317, 123, 23);
		disID.getContentPane().add(rejectnomoney);
		
		checkstatus2 = new JTextField();
		checkstatus2.setBounds(104, 194, 86, 20);
		disID.getContentPane().add(checkstatus2);
		checkstatus2.setColumns(10);
		
		amount2 = new JTextField();
		amount2.setBounds(104, 163, 86, 20);
		disID.getContentPane().add(amount2);
		amount2.setColumns(10);
		
		datee = new JTextField();
		datee.setBounds(104, 132, 86, 20);
		disID.getContentPane().add(datee);
		datee.setColumns(10);
		
		checknum1 = new JTextField();
		checknum1.setBounds(104, 101, 86, 20);
		disID.getContentPane().add(checknum1);
		checknum1.setColumns(10);
		
		JLabel checknum = new JLabel("check number");
		checknum.setHorizontalAlignment(SwingConstants.CENTER);
		checknum.setBounds(6, 104, 88, 14);
		disID.getContentPane().add(checknum);
		
		JLabel date1 = new JLabel("date");
		date1.setHorizontalAlignment(SwingConstants.CENTER);
		date1.setBounds(22, 135, 46, 14);
		disID.getContentPane().add(date1);
		
		JLabel amount1 = new JLabel("amount");
		amount1.setHorizontalAlignment(SwingConstants.CENTER);
		amount1.setBounds(22, 166, 46, 14);
		disID.getContentPane().add(amount1);
		
		JLabel checkstatus1 = new JLabel("check status");
		checkstatus1.setHorizontalAlignment(SwingConstants.CENTER);
		checkstatus1.setBounds(22, 197, 72, 14);
		disID.getContentPane().add(checkstatus1);
		
		JButton changestatus = new JButton("change status");
		
		changestatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JsonParser parser = new JsonParser();
				System.out.println(ConnectToServer.getCheckhistory(getchecknum1()));
				JsonObject jsnobject = (JsonObject) parser.parse(ConnectToServer.getCheckhistory(getchecknum1()));

				JsonArray jsonArray = jsnobject.getAsJsonArray("Checks");
				
				
				JsonArray arr2 = new JsonArray();
				JsonObject jsnobject2 = (JsonObject) parser.parse(ConnectToServer.savehistorysCheckfeedback(getchecknum1(), getdatee(),getcheckstatus2()));

				JsonArray jsonArray2 = jsnobject.getAsJsonArray("Checks");
				for (int i = 0; i < jsonArray.size(); i++) {
					JsonObject e = jsonArray.get(i).getAsJsonObject();
					//model.addRow(new Object[] { e.get("hash"),e.get("date"),e.get("checkstatus") });
					
					
					thisref.arr.add(new Check(e.get("hash").toString(),e.get("amount").toString(),e.get("date").toString(),e.get("checkstatus").toString()));
					ConnectToServer. savehistorysCheckfeedback(e.get("hash").toString(),e.get("date").toString(),e.get("checkstatus").toString());	
					setdatee(e.get("date").toString());
					setchecknum1(e.get("hash").toString());
					setcheckstatus2(e.get("checkstatus").toString());
				}
				
				System.out.println("this array json"+jsonArray.toString());
				
				
				
				
				JsonArray arr3= new JsonArray();
				JsonObject jsnobject3 = (JsonObject) parser.parse(ConnectToServer.savestatusCheckfeedback(getchecknum1(),getamount2(), getdatee(),getcheckstatus2()));

				JsonArray jsonArray3 = jsnobject.getAsJsonArray("Checks");
				for (int i = 0; i < jsonArray.size(); i++) {
					JsonObject e = jsonArray.get(i).getAsJsonObject();
				    
					//model.addRow(new Object[] { e.get("hash"),e.get("amount"),e.get("date"),e.get("checkstatus") });
					
					thisref.arr.add(new Check(e.get("hash").toString(),e.get("amount").toString(),e.get("date").toString(),e.get("checkstatus").toString()));
					ConnectToServer.savestatusCheckfeedback(e.get("hash").toString(),e.get("amount").toString(),e.get("date").toString(),e.get("checkstatus").toString());
					setdatee(e.get("date").toString());
					setchecknum1(e.get("hash").toString());
					setamount2(e.get("amount").toString());
					setcheckstatus2(e.get("checkstatus").toString());
				}
				System.out.println("this array json"+jsonArray.toString());
				
				
				
				
				
				
			}
		});
		changestatus.setBounds(121, 291, 109, 23);
		disID.getContentPane().add(changestatus);
		
		JLabel statusfeedback = new JLabel("status feed back for check");
		statusfeedback.setHorizontalAlignment(SwingConstants.CENTER);
		statusfeedback.setBounds(22, 32, 133, 14);
		disID.getContentPane().add(statusfeedback);
		
		JLabel historyfeedback = new JLabel("history feed back for check");
		historyfeedback.setHorizontalAlignment(SwingConstants.CENTER);
		historyfeedback.setBounds(266, 32, 139, 14);
		disID.getContentPane().add(historyfeedback);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(236, 90, 169, 172);
		disID.getContentPane().add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		//frame1(hash, amount, date, checkstatus);
		
		JsonParser parser = new JsonParser();
		System.out.println("connect to server with:"+hashid);
		System.out.println(ConnectToServer.gethistoryCheckfeedback(hashid));
		JsonObject jsnobject = (JsonObject) parser.parse(ConnectToServer.gethistoryCheckfeedback(hashid));

		JsonArray jsonArray = jsnobject.getAsJsonArray("Checks");
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonObject e = jsonArray.get(i).getAsJsonObject();
			checkHistoryDATA+=" data:"+e.get("date").toString()+" status:"+e.get("checkstatus").toString()+"\n";
		}
		textArea.setText(checkHistoryDATA);
		disID.getContentPane().setLayout(null);

		
		disID.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		disID.setLocationRelativeTo(null);
		disID.setVisible(true);

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
	
	
	public JFrame getFrame() {
		return disID;
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
