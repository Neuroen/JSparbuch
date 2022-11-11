package core;

import javax.swing.JOptionPane;

public class Starter 
{
	public static void main(String[] args) 
	{
		if(System.getProperty("os.name").contains("Windows"))
		{
			JOptionPane.showMessageDialog(null, "Windows wird nicht unterstützt.");
			return;
		}
		Swing_View sw = new Swing_View();
		sw.StartGui();
	}
}
