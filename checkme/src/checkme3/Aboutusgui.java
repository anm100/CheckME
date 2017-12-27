package checkme3;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import application.Resources;
import javax.swing.JLabel;

public class Aboutusgui extends JPanel {
	 private Image image;
	 
	public Aboutusgui()
	{
	setBounds(89, 124, 396, 423);
	setLayout(null);
	setBackground(Color.white);
	
//	JPanel panel = new JPanel();
//	panel.setBounds(50, 50, 596, 401);
//	add(panel);
//	panel.setLayout(null);
	
	JLabel lblNewLabel = new JLabel("");
	lblNewLabel.setIcon(new ImageIcon("C:\\\\\\\\finalProject\\\\\\\\CheckME\\\\\\\\checkme\\\\\\\\src\\\\\\\\image\\\\\\\\rsz_layout-2017-12-18-225350.jpg"));
	lblNewLabel.setBounds(0, 0, 386, 423);
	lblNewLabel.setVisible(true);
	add(lblNewLabel);

	

   
	
	 
	  
    
	}
}


	

