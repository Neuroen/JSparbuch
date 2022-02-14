package core;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Swing_View 
{
	private JFrame frame;
	
	public Swing_View() 
	{
		initialize();
	}
	
	public static void StartGui() 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					Swing_View window = new Swing_View();
					window.frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	private void initialize() 
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
