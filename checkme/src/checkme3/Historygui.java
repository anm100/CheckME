package checkme3;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Utils.MyTableModel;
import application.myApp;
import checkmeController.ConnectToServer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Historygui extends JPanel {
	private JTable tblToday;
	private int ex_id;
	
	public Historygui() {
		setBounds(45, 124, 471, 401);
		setLayout(null);
		setBackground(Color.LIGHT_GRAY);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 117, 741, 409);
		add(scrollPane);

		tblToday = new JTable();
		scrollPane.setViewportView(tblToday);
		tblToday.setModel(
				new MyTableModel(new String[] { "hashid", "amount", "date", "checkStatus" }, new Object[][] {}));

		JLabel lblTodaysExaminations = new JLabel("history");
		lblTodaysExaminations.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 28));
		lblTodaysExaminations.setForeground(Color.CYAN);
		lblTodaysExaminations.setHorizontalAlignment(SwingConstants.CENTER);
		lblTodaysExaminations.setBounds(286, 40, 170, 46);
		add(lblTodaysExaminations);

		fillExaminations(tblToday);
		tblToday.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				System.out.println("clicked - row");
				
				
				
				/*
				if (event.getValueIsAdjusting())
					return;
				int row = tblToday.getSelectedRow();
				if(row<0)
					return;
				ex_id = (int) tblToday.getModel().getValueAt(row, 0);*/

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
	JsonObject jsnobject = (JsonObject) parser.parse(ConnectToServer.getCheckhistory(myApp.getUsername()));

	JsonArray jsonArray = jsnobject.getAsJsonArray("Checks");
	if(jsonArray !=null){
	for (int i = 0; i < jsonArray.size(); i++) {
		JsonObject e = jsonArray.get(i).getAsJsonObject();
	    
		model.addRow(new Object[] { e.get("hash").getAsString(),e.get("amount").getAsString(),e.get("date").getAsString(),e.get("checkstatus").getAsString() });
	}
	
	System.out.println("this array json"+jsonArray.toString());
	}


	}
		
}
