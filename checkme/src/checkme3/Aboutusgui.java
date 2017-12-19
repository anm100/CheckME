package checkme3;

import java.awt.Color;
import java.nio.file.Path;

import javax.swing.JPanel;

import application.Resources;

public class Aboutusgui extends JPanel {

	public Aboutusgui()
	{
	setBounds(45, 124, 381, 315);
	setLayout(null);
	setBackground(Color.RED);
	setVisible(true);
	
	Resources imagegrab = new Resources();
	
	imagegrab.getImageFromPath("C:\\finalProject\\CheckME");
	}

}
