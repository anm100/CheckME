package checkme3;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Utils.MyTableModel;
import checkmeController.ConnectToServer;
import checkmeController.Check;

import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class Checkfeedbackgui extends JPanel {
    Check check=new Check();
    ArrayList <Check> arr = new ArrayList<Check>();
	private Checkfeedbackgui thisref=this;
	private JTextField amount1;
	private JTextField date1;
	private JTextField checknumber1;
	private JTextField id1;
	private JTextField checkstatus1;
	private JTable tblToday;
	private int ex_id;

	


	


	
	
	public  Checkfeedbackgui ()
	{
		
		
		
		
	
			setBounds(45, 124, 471, 401);
			setLayout(null);
			setBackground(Color.LIGHT_GRAY);
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 127, 741, 409);
			add(scrollPane);

			tblToday = new JTable();
			scrollPane.setViewportView(tblToday);
			tblToday.setModel(
					new MyTableModel(new String[] { "CheckNum", "amount", "date", "checkStatus" }, new Object[][] {}));

			JLabel lblTodaysExaminations = new JLabel("check feedback");
			lblTodaysExaminations.setHorizontalAlignment(SwingConstants.CENTER);
			lblTodaysExaminations.setBounds(209, 102, 170, 14);
			add(lblTodaysExaminations);

			fillExaminations(tblToday);
			tblToday.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent event) {
					System.out.println("clicked - row");
					
					
					
					
					if (event.getValueIsAdjusting())
						return;
					int row = tblToday.getSelectedRow();
					System.out.println(row);
					
					if(row<0)
						return;
				//	ex_id = (int) tblToday.getModel().getValueAt(row, 0);
                    check=thisref.arr.get(	row );
                    System.out.println(check.getCheckNum());
                    ConnectToServer.Checkchecks("00103641126140066696");
                   //( new feedbackdatagui(check.getCheckNum() ).setVisible(true);
				/*	Examination ex = ExaminationController.getById(ex_id);
					ExamEditor edit = new ExamEditor(ex,row,true,self);*/

				}
			});
		//	setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[] { logo }));
			setBounds(100, 100, 763, 576);
		
		
			setVisible(true);
		}


	public void removeRow(int index){
		((DefaultTableModel)tblToday.getModel()).removeRow(index);
	}
	public void fillExaminations(JTable tbl) {
		DefaultTableModel model = (DefaultTableModel) tbl.getModel();
		JsonParser parser = new JsonParser();
		
		JsonArray arr = new JsonArray();
		JsonObject jsnobject = (JsonObject) parser.parse(ConnectToServer.getCheckhistory("ameer"));

		JsonArray jsonArray = jsnobject.getAsJsonArray("Checks");
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonObject e = jsonArray.get(i).getAsJsonObject();
		    
			model.addRow(new Object[] { e.get("hash"),e.get("amount"),e.get("date"),e.get("checkstatus") });
			this.arr.add(new Check(e.get("hash").toString(),e.get("amount").toString(),e.get("date").toString(),e.get("checkstatus").toString()));
		}
		System.out.println("this array json"+jsonArray.toString());


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
