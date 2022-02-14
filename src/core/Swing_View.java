package core;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Insets;
import java.security.PublicKey;
import java.security.KeyStore.PrivateKeyEntry;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import org.jdatepicker.impl.JDatePickerImpl;

public class Swing_View 
{
	private FileManager fm;
	private DataHolder dh;
	
	private JFrame frame;
	private JTable transactionTable;
	private SpringLayout layout;
	private JList accountList;
	private DefaultTableModel tableModel;
	private Object[] tableColumnNames = 
	{
		"Wert",
		"Bezeichnung",
		"Datum"
	};
	
	public Swing_View() 
	{
		fm = new FileManager();
		dh = new DataHolder();
		dh.SetAccountsList(fm.ReadFile(fm.GetUserFile()));
		dh.SetTransactionsList(fm.ReadFile(fm.GetDataFile()));
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
		frame = new JFrame("Sparbuch");
		frame.setBounds(100, 100, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		layout = new SpringLayout(); 
		
		accountList = new JList<String>();
		accountList.setBorder(new LineBorder(Color.black));
		
		layout.putConstraint(SpringLayout.WEST, accountList, 5, SpringLayout.WEST, frame);
		layout.putConstraint(SpringLayout.EAST, accountList, 300, SpringLayout.EAST, frame);
		layout.putConstraint(SpringLayout.NORTH, accountList, 30, SpringLayout.NORTH, frame);
		layout.putConstraint(SpringLayout.SOUTH, accountList, 500, SpringLayout.SOUTH, frame);
		
		transactionTable = new JTable();
		transactionTable.setBorder(new LineBorder(Color.black));
		
		layout.putConstraint(SpringLayout.WEST, transactionTable, 305, SpringLayout.WEST, frame);
		layout.putConstraint(SpringLayout.EAST, transactionTable, 600, SpringLayout.EAST, frame);
		layout.putConstraint(SpringLayout.NORTH, transactionTable, 30, SpringLayout.NORTH, frame);
		layout.putConstraint(SpringLayout.SOUTH, transactionTable, 500, SpringLayout.SOUTH, frame);
		
		frame.setLayout(layout);
		frame.add(accountList);
		frame.add(transactionTable);
		UpdateAccountsList();
	}
	
	private void UpdateTransactionTable(int accountID) 
	{
		tableModel = new DefaultTableModel(0, tableColumnNames.length)
		{
			public boolean isCellEditable(int row, int column) 
			{
				return false;
			}
		};
		tableModel.addRow(tableColumnNames);
		transactionTable.setModel(tableModel);
	}
	
	private void UpdateAccountsList()
	{
		DefaultListModel<String> model = new DefaultListModel<String>();
		ArrayList<String> accounts = dh.GetAccountsList();
		for(int i = 0; i < accounts.size(); i++)
		{
			model.addElement(accounts.get(i).split(";")[1]);
		}
		accountList.setModel(model);
	}

}
