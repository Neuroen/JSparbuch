package core;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class AboutWindow 
{
	private JFrame frame;
	private SpringLayout layout;
	private JLabel logo;
	private JLabel aboutLabel;
	private JLabel linkToGitLabel;
	
	public AboutWindow() 
	{
		initialize();
	}
	
	public void StartGui() 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					AboutWindow window = new AboutWindow();
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
		frame = new JFrame("Über Sparbuch");
		frame.setBounds(100, 100, 400, 300);
		frame.setResizable(false);
		
		layout = new SpringLayout();
		
		logo = new JLabel(new ImageIcon(this.getClass().getResource("dollar-icon.png")));
		layout.putConstraint(SpringLayout.WEST, logo, 72, SpringLayout.WEST, frame);
		layout.putConstraint(SpringLayout.NORTH, logo, 5, SpringLayout.NORTH, frame);
		layout.putConstraint(SpringLayout.SOUTH, logo, 200, SpringLayout.NORTH, frame);
		//layout.putConstraint(SpringLayout.EAST, logo, 120, SpringLayout.EAST, frame);
		
		
		aboutLabel = new JLabel("Sparbuch Version 2.0");
		
		layout.putConstraint(SpringLayout.WEST, aboutLabel, 120, SpringLayout.WEST, frame);
		layout.putConstraint(SpringLayout.NORTH, aboutLabel, 200, SpringLayout.NORTH, frame);
		
		linkToGitLabel = new JLabel("<html><a href=''>Sparbuch auf GitHub</a></html>");
		linkToGitLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		
		layout.putConstraint(SpringLayout.WEST, linkToGitLabel, 120, SpringLayout.WEST, frame);
		layout.putConstraint(SpringLayout.NORTH, linkToGitLabel, 250, SpringLayout.NORTH, frame);
		
		frame.setLayout(layout);
		frame.add(logo);
		frame.add(aboutLabel);
		frame.add(linkToGitLabel);
		
		
		linkToGitLabel.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e) 
			{
				if(Desktop.isDesktopSupported())
				{
					try 
					{
						Desktop.getDesktop().browse(new URI("https://github.com/neuroen/Sparbuch"));
					} 
					catch (Exception ex) 
					{

					}
					
				}
			}
		});
	}

}
