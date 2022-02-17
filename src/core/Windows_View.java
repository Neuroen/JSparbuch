package core;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class Windows_View 
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
		"Datum",
		"Bezeichnung",
		"Wert"
	};
	
	public Windows_View() 
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
					Windows_View window = new Windows_View();
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
		accountList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		accountList.setBorder(new LineBorder(Color.black));		
		layout.putConstraint(SpringLayout.WEST, accountList, 10, SpringLayout.WEST, frame);
		layout.putConstraint(SpringLayout.EAST, accountList, 290, SpringLayout.EAST, frame);
		layout.putConstraint(SpringLayout.NORTH, accountList, 30, SpringLayout.NORTH, frame);
		layout.putConstraint(SpringLayout.SOUTH, accountList, 500, SpringLayout.SOUTH, frame);
		//Transaction Table
		transactionTable = new JTable();
		transactionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		transactionTable.setBorder(new LineBorder(Color.black));		
		//Layout for Windows
		layout.putConstraint(SpringLayout.WEST, transactionTable, 315, SpringLayout.WEST, frame);
		layout.putConstraint(SpringLayout.EAST, transactionTable, 680, SpringLayout.EAST, frame);
		layout.putConstraint(SpringLayout.NORTH, transactionTable, 30, SpringLayout.NORTH, frame);
		layout.putConstraint(SpringLayout.SOUTH, transactionTable, 500, SpringLayout.SOUTH, frame);		
		//Account Calc Box TODO Implementieren und Freigeben
		/*targetCalculateBox = new JPanel();
		targetCalculateBox.setBorder(new LineBorder(Color.black));
		layout.putConstraint(SpringLayout.WEST, targetCalculateBox, 705, SpringLayout.WEST, frame);
		layout.putConstraint(SpringLayout.EAST, targetCalculateBox, 995, SpringLayout.EAST, frame);
		layout.putConstraint(SpringLayout.NORTH, targetCalculateBox, 250, SpringLayout.NORTH, frame);
		layout.putConstraint(SpringLayout.SOUTH, targetCalculateBox, 450, SpringLayout.SOUTH, frame);*/
		//Add Transaction Button
		addTransactionButton = new JButton("Geld Buchen");		
		layout.putConstraint(SpringLayout.WEST, addTransactionButton, 705, SpringLayout.WEST, frame);
		layout.putConstraint(SpringLayout.EAST, addTransactionButton, 960, SpringLayout.EAST, frame);
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
		//accountMenu.add(importAccountListItem); Freigeben wenn Fertig
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
		//frame.add(targetCalculateBox);
		frame.add(addTransactionButton);
		frame.add(totalBalanceLabel);
		frame.add(menuBar);
		
		
		//Events
		accountList.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent event)
			{
				UpdateBalanceText();
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
				if(transactionTable.getSelectedRow() > 0)
				{
					editTransactionItem.setEnabled(true);
					deleteTransactionItem.setEnabled(true);
				}
				else 
				{
					editTransactionItem.setEnabled(false);
					deleteTransactionItem.setEnabled(false);
				}
			}
		});
		
		createNewAccountItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				AccountDialog ad = new AccountDialog();
				String dataString = ad.OpenAccountDialog();
				if(dataString == null)
				{
					return;
				}
				dh.InsertNewAccount(dataString);
				UpdateAccountsList();				
				fm.WriteFile(fm.GetUserFile(), dh.GetAccountDataAsStringFormattet());
			}
		});
		
		editAccountItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				AccountDialog ad = new AccountDialog();
				String accountData = dh.GetAccountDataFromName(accountList.getSelectedValue().toString());
				ad.SetAccountNameField(accountData.split(";")[1]);
				if(!accountData.split(";")[2].equals("none"))
				{
					ad.SetAccountTargetField(accountData.split(";")[2]);
				}
				if(!accountData.split(";")[3].equals("none"))
				{
					ad.SetAccountTargetDate(accountData.split(";")[3]);
				}
				accountData = accountData.split(";")[0] + ";" + ad.OpenAccountDialog();
				dh.EditAccount(accountList.getSelectedValue().toString(), accountData);
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
		
		addTransactionButton.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e) 
			{
				TransactionDialog td = new TransactionDialog();
				String transactionData = td.OpenTransactionDialog();
				if(transactionData == null)
				{
					return;
				}
				dh.AddTransaction(accountList.getSelectedValue().toString(), transactionData);
				UpdateTransactionTable(dh.GetTransactionsForAccount(accountList.getSelectedValue().toString()));
				UpdateBalanceText();
				fm.WriteFile(fm.GetDataFile(), dh.GetTransactionDataAsStringFormattet());
			}
		});
		
		editTransactionItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				TransactionDialog td = new TransactionDialog();
				String transactionData = dh.GetTransactionsForAccount(accountList.getSelectedValue().toString()).get(transactionTable.getSelectedRow() - 1);
				td.SetTransactionDate(transactionData.split(";")[1]);
				td.SetDescriptionField(transactionData.split(";")[2]);
				td.SetMoneyField(transactionData.split(";")[3]);
				String oldTransactionData = transactionData;
				transactionData = td.OpenTransactionDialog();
				if(transactionData == null)
				{
					return;
				}
				System.out.println(transactionData);
				dh.EditTransaction(oldTransactionData, transactionData);
				UpdateTransactionTable(dh.GetTransactionsForAccount(accountList.getSelectedValue().toString()));
				UpdateBalanceText();
				fm.WriteFile(fm.GetDataFile(), dh.GetTransactionDataAsStringFormattet());
			}
		});
		
		deleteTransactionItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				int tableID = transactionTable.getSelectedRow();
				//TODO Warnungs Message ausgeben.
				dh.DeleteTransactionFromIdentifier(dh.GetTransactionsForAccount(accountList.getSelectedValue().toString()).get(tableID - 1));
				fm.WriteFile(fm.GetDataFile(), dh.GetTransactionDataAsStringFormattet());
				UpdateTransactionTable(dh.GetTransactionsForAccount(accountList.getSelectedValue().toString()));
				UpdateBalanceText();
			}
		});
		
		UpdateAccountsList();
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
	
	private void UpdateBalanceText()
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
