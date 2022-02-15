package core;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.PublicKey;
import java.security.KeyStore.PrivateKeyEntry;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdatepicker.*;

public class Swing_View 
{
	private FileManager fm;
	private DataHolder dh;
	private Calculator calc;
	
	private JFrame frame;
	private JTable transactionTable;
	private SpringLayout layout;
	private JList accountList;
	private DefaultTableModel tableModel;
	private JLabel totalBalanceLabel;
	private JPanel targetCalculateBox;
	private JButton addTransactionButton;
	//Menu Bar
	private JMenuBar menuBar;
	//Account Menu
	private JMenu accountMenu;
	private JMenuItem createNewAccountItem;
	private JMenuItem editAccountItem;
	private JMenuItem deleteAccountItem;
	private JMenuItem importAccountListItem;
	//Transaction Menu
	private JMenu transactionMenu;
	private JMenuItem editTransactionItem;
	private JMenuItem deleteTransactionItem;
	
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
		calc = new Calculator();
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
		frame.setResizable(false);
		
		layout = new SpringLayout(); 
		//Accounts List
		accountList = new JList<String>();
		accountList.setBorder(new LineBorder(Color.black));
		layout.putConstraint(SpringLayout.WEST, accountList, 5, SpringLayout.WEST, frame);
		layout.putConstraint(SpringLayout.EAST, accountList, 300, SpringLayout.EAST, frame);
		layout.putConstraint(SpringLayout.NORTH, accountList, 30, SpringLayout.NORTH, frame);
		layout.putConstraint(SpringLayout.SOUTH, accountList, 500, SpringLayout.SOUTH, frame);
		//Transaction Table
		transactionTable = new JTable();
		transactionTable.setBorder(new LineBorder(Color.black));
		layout.putConstraint(SpringLayout.WEST, transactionTable, 305, SpringLayout.WEST, frame);
		layout.putConstraint(SpringLayout.EAST, transactionTable, 700, SpringLayout.EAST, frame);
		layout.putConstraint(SpringLayout.NORTH, transactionTable, 30, SpringLayout.NORTH, frame);
		layout.putConstraint(SpringLayout.SOUTH, transactionTable, 500, SpringLayout.SOUTH, frame);
		//Account Calc Box
		targetCalculateBox = new JPanel();
		targetCalculateBox.setBorder(new LineBorder(Color.black));
		layout.putConstraint(SpringLayout.WEST, targetCalculateBox, 705, SpringLayout.WEST, frame);
		layout.putConstraint(SpringLayout.EAST, targetCalculateBox, 995, SpringLayout.EAST, frame);
		layout.putConstraint(SpringLayout.NORTH, targetCalculateBox, 250, SpringLayout.NORTH, frame);
		layout.putConstraint(SpringLayout.SOUTH, targetCalculateBox, 450, SpringLayout.SOUTH, frame);
		//Add Transaction Button
		addTransactionButton = new JButton("Geld Buchen");
		layout.putConstraint(SpringLayout.WEST, addTransactionButton, 705, SpringLayout.WEST, frame);
		layout.putConstraint(SpringLayout.EAST, addTransactionButton, 995, SpringLayout.EAST, frame);
		layout.putConstraint(SpringLayout.NORTH, addTransactionButton, 490, SpringLayout.NORTH, frame);
		layout.putConstraint(SpringLayout.SOUTH, addTransactionButton, 500, SpringLayout.SOUTH, frame);
		//Labels
		totalBalanceLabel = new JLabel("0€");
		Font balanceFont = new Font("arial", 0, 32);
		totalBalanceLabel.setFont(balanceFont);
		layout.putConstraint(SpringLayout.WEST, totalBalanceLabel, 705, SpringLayout.WEST, frame);
		layout.putConstraint(SpringLayout.EAST, totalBalanceLabel, 1000, SpringLayout.EAST, frame);
		layout.putConstraint(SpringLayout.NORTH, totalBalanceLabel, 30, SpringLayout.NORTH, frame);
		
		//Menu Bar, Menus und Items
		menuBar = new JMenuBar();
		//Account Menu
		accountMenu = new JMenu("Konten");
		createNewAccountItem = new JMenuItem("Neues Konto erstellen");
		editAccountItem = new JMenuItem("Konto Bearbeiten");
		deleteAccountItem = new JMenuItem("Konto Löschen");
		importAccountListItem = new JMenuItem("Kontenliste Importieren");
		//TODO: Exportliste
		accountMenu.add(createNewAccountItem);
		accountMenu.add(editAccountItem);
		accountMenu.add(deleteAccountItem);
		accountMenu.add(new JSeparator());
		accountMenu.add(importAccountListItem);
		editAccountItem.setEnabled(false);
		deleteAccountItem.setEnabled(false);
		//Transaction Menu
		transactionMenu = new JMenu("Transaktionen");
		editTransactionItem = new JMenuItem("Transaktion Bearbeiten");
		deleteTransactionItem = new JMenuItem("Transaktion Löschen");
		transactionMenu.add(editTransactionItem);
		transactionMenu.add(deleteTransactionItem);
		editTransactionItem.setEnabled(false);
		deleteTransactionItem.setEnabled(false);
		
		menuBar.add(accountMenu);
		menuBar.add(transactionMenu);
		
		
		frame.setLayout(layout);
		frame.add(accountList);
		frame.add(transactionTable);
		frame.add(targetCalculateBox);
		frame.add(addTransactionButton);
		frame.add(totalBalanceLabel);
		frame.add(menuBar);
		
		
		//Events
		accountList.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent event)
			{
				ArrayList<String> transactions = dh.GetTransactionsForAccount(accountList.getSelectedValue().toString());
				UpdateTransactionTable(transactions);
				float target = dh.GetAccountTarget(accountList.getSelectedValue().toString());
				float balance = calc.CalculateTotalBalance(transactions);
				if(target == -1)
				{
					totalBalanceLabel.setText("<html><body>" + balance + "€<br><br>Kein Ziel Gesetzt</body></html>");
				}
				else 
				{
					float difference = target - balance;
					totalBalanceLabel.setText("<html><body>" + balance + "€ /<br>" + target + "€<br><br>noch " + difference + "€ bis zum Ziel</body></html>");
				}
				if(accountList.getSelectedIndex() != -1)
				{
					editAccountItem.setEnabled(true);
					deleteAccountItem.setEnabled(true);
				}
			}
		});
		
		transactionTable.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent event)
			{
				if(transactionTable.getSelectedRow() != -1)
				{
					editTransactionItem.setEnabled(true);
					deleteTransactionItem.setEnabled(true);
				}
			}
		});
		
		createNewAccountItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String dataString = OpenAccountDialog();
				if(dataString == null)
				{
					return;
				}
				dh.InsertNewAccount(dataString);
				UpdateAccountsList();				
				fm.WriteFile(fm.GetUserFile(), dh.GetAccountDataAsStringFormattet());
			}
		});
		
		deleteAccountItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(accountList.getSelectedIndex() == -1)
				{
					return;
				}
				dh.DeleteAccount(accountList.getSelectedValue().toString());
				fm.WriteFile(fm.GetUserFile(), dh.GetAccountDataAsStringFormattet());
				fm.WriteFile(fm.GetDataFile(), dh.GetTransactionDataAsStringFormattet());
				UpdateAccountsList();
			}
		});
		
		UpdateAccountsList();
	}
	
	private String OpenAccountDialog()
	{
		JTextField accountNameField = new JTextField();
		JTextField accountTargetField = new JTextField();
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
		final JComponent[] inputComponents = new JComponent[]
		{
			new JLabel("Kontoname"),
			accountNameField,
			new JLabel("Sparziel"),
			accountTargetField,
			new JLabel("Datum Sparziel"),
			datePicker
		};
		
		int result = JOptionPane.showConfirmDialog(null, inputComponents, "Konto Manager", JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION)
		{
			String name = accountNameField.getText();
			if(name.length() <= 0)
			{
				JOptionPane.showConfirmDialog(null, null, "Kein Kontoname eingefügt", JOptionPane.ERROR_MESSAGE);
				return null;
			}
			String target = accountTargetField.getText();
			String date = "";
			if(target.length() <= 0)
			{
				target = "none";
			}
			if(datePicker.getModel().getValue() == null)
			{
				date = "none";
			}
			else 
			{
				date = datePicker.getModel().getValue().toString();
			}
			String accountData = accountNameField.getText() + ";" + target + ";" + date;
			return accountData;
		}
		return null;
	}
	
	private void UpdateTransactionTable(ArrayList<String> transactions) 
	{
		tableModel = new DefaultTableModel(0, tableColumnNames.length)
		{
			public boolean isCellEditable(int row, int column) 
			{
				return false;
			}
		};
		tableModel.addRow(tableColumnNames);
		for(int i = 0; i < transactions.size(); i++)
		{
			String currentTransaction = transactions.get(i).substring(transactions.get(i).indexOf(";") + 1);
			tableModel.addRow(currentTransaction.split(";"));
		}
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
