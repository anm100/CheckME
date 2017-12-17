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
	private String checkHistoryDATA=new String("");
	   private JButton btnNewButton;
	   private JTextField checkstatus2;
	   private JTextField amount2;
	   private JTextField datee;
	   private JTextField checknum1;
	   ArrayList <Check> arr = new ArrayList<Check>();
		private feedbackdatagui thisref=this;
		public feedbackdatagui(String hashid){
			setTitle("feed baaack");
			frame(hashid);
			setVisible(true);
			
					
		}
		 
		public void frame(String hashid){
			
			
			
		}


		
	}

