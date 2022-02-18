package core;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.GeneralPath;
import java.security.PublicKey;
import java.security.KeyStore.PrivateKeyEntry;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
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
	
	//Labels
	private JLabel totalBalanceLabel;
	private JLabel totalTimeToTargetDateLabel;
	
	//Calculation Module
	private JPanel targetCalculateBox;
	private JTabbedPane calculatorTabs;
	private JPanel weekSavingPanel;
	private JPanel monthSavingPanel;
	private JPanel quarterSavingPanel;
	private JPanel yearSavingPanel;
	private JLabel weekSavingDescriptionLabel;
	private JLabel monthSavingDescriptionLabel;
	private JLabel quarterSavingDescriptionLabel;
	private JLabel yearSavingDescriptionLabel;
	
	private JButton addTransactionButton;
	//Menu Bar
	private JMenuBar menuBar;
	//General Menu
	private JMenu generalMenu;
	private JMenuItem aboutItem;
	private JMenuItem settingsItem;
	private JMenuItem exItem;
	//Account Menu
	private JMenu accountMenu;
	private JMenuItem createNewAccountItem;
	private JMenuItem editAccountItem;
	private JMenuItem deleteAccountItem;
	private JMenuItem importAccountListItem;
	private JMenuItem exportAccountListItem;
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
		accountList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		accountList.setBorder(new LineBorder(Color.black));
		layout.putConstraint(SpringLayout.WEST, accountList, 5, SpringLayout.WEST, frame);
		layout.putConstraint(SpringLayout.EAST, accountList, 300, SpringLayout.EAST, frame);
		layout.putConstraint(SpringLayout.NORTH, accountList, 30, SpringLayout.NORTH, frame);
		layout.putConstraint(SpringLayout.SOUTH, accountList, 500, SpringLayout.SOUTH, frame);
		
		//Transaction Table
		transactionTable = new JTable();
		transactionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
		layout.putConstraint(SpringLayout.NORTH, targetCalculateBox, 300, SpringLayout.NORTH, frame);
		layout.putConstraint(SpringLayout.SOUTH, targetCalculateBox, 450, SpringLayout.SOUTH, frame);
		
		targetCalculateBox.setLayout(new GridLayout());
		calculatorTabs = new JTabbedPane();
		calculatorTabs.setEnabled(false);
		
		weekSavingPanel = new JPanel();
		weekSavingPanel.setLayout(new GridLayout());
		
		monthSavingPanel = new JPanel();
		monthSavingPanel.setLayout(new GridLayout());
		
		quarterSavingPanel = new JPanel();
		quarterSavingPanel.setLayout(new GridLayout());
		
		yearSavingPanel = new JPanel();
		yearSavingPanel.setLayout(new GridLayout());
		
		weekSavingDescriptionLabel = new JLabel("");
		monthSavingDescriptionLabel = new JLabel("");
		quarterSavingDescriptionLabel = new JLabel("");
		yearSavingDescriptionLabel = new JLabel("");
		
		weekSavingPanel.add(weekSavingDescriptionLabel);
		monthSavingPanel.add(monthSavingDescriptionLabel);
		quarterSavingPanel.add(quarterSavingDescriptionLabel);
		yearSavingPanel.add(yearSavingDescriptionLabel);
		
		calculatorTabs.addTab("Woche", weekSavingPanel);
		calculatorTabs.addTab("Monat", monthSavingPanel);
		calculatorTabs.addTab("Quartal", quarterSavingPanel);
		calculatorTabs.addTab("Jahr", yearSavingPanel);
		
		targetCalculateBox.add(calculatorTabs);
		
		//Add Transaction Button
		addTransactionButton = new JButton("Geld Buchen");
		layout.putConstraint(SpringLayout.WEST, addTransactionButton, 705, SpringLayout.WEST, frame);
		layout.putConstraint(SpringLayout.EAST, addTransactionButton, 995, SpringLayout.EAST, frame);
		layout.putConstraint(SpringLayout.NORTH, addTransactionButton, 490, SpringLayout.NORTH, frame);
		layout.putConstraint(SpringLayout.SOUTH, addTransactionButton, 500, SpringLayout.SOUTH, frame);
		
		//Labels
		totalBalanceLabel = new JLabel("0" + Static_Settings.GetCurrency());
		Font balanceFont = new Font("arial", 0, 32);
		totalBalanceLabel.setFont(balanceFont);
		layout.putConstraint(SpringLayout.WEST, totalBalanceLabel, 705, SpringLayout.WEST, frame);
		layout.putConstraint(SpringLayout.EAST, totalBalanceLabel, 1000, SpringLayout.EAST, frame);
		layout.putConstraint(SpringLayout.NORTH, totalBalanceLabel, 30, SpringLayout.NORTH, frame);
		
		totalTimeToTargetDateLabel = new JLabel("");
		layout.putConstraint(SpringLayout.WEST, totalTimeToTargetDateLabel, 705, SpringLayout.WEST, frame);
		layout.putConstraint(SpringLayout.EAST, totalTimeToTargetDateLabel, 1000, SpringLayout.EAST, frame);
		layout.putConstraint(SpringLayout.NORTH, totalTimeToTargetDateLabel, 270, SpringLayout.NORTH, frame);
		
		//Menu Bar, Menus und Items
		menuBar = new JMenuBar();
		//General Menu
		generalMenu = new JMenu("Allgemein");
		aboutItem = new JMenuItem("Über Sparbuch");
		settingsItem = new JMenuItem("Einstellungen");
		exItem = new JMenuItem("Beenden");
		
		generalMenu.add(aboutItem);
		generalMenu.add(settingsItem);
		generalMenu.add(new JSeparator());
		generalMenu.add(exItem);
		//Account Menu
		accountMenu = new JMenu("Konten");
		createNewAccountItem = new JMenuItem("Neues Konto erstellen");
		editAccountItem = new JMenuItem("Konto Bearbeiten");
		deleteAccountItem = new JMenuItem("Konto Löschen");
		importAccountListItem = new JMenuItem("Kontenliste Importieren");
		exportAccountListItem = new JMenuItem("Kontenliste Exportieren");
		
		accountMenu.add(createNewAccountItem);
		accountMenu.add(editAccountItem);
		accountMenu.add(deleteAccountItem);
		accountMenu.add(new JSeparator());
		accountMenu.add(importAccountListItem);
		accountMenu.add(exportAccountListItem);
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
		
		//menuBar.add(generalMenu); Einbauen wenn Fertig
		menuBar.add(accountMenu);
		menuBar.add(transactionMenu);
		
		frame.setLayout(layout);
		frame.add(accountList);
		frame.add(transactionTable);
		frame.add(targetCalculateBox);
		frame.add(addTransactionButton);
		frame.add(totalBalanceLabel);
		frame.add(totalTimeToTargetDateLabel);
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
				editAccountItem.setEnabled(false);
				deleteAccountItem.setEnabled(false);
				UpdateAccountsList();
				UpdateBalanceText();
			}
		});
		
		importAccountListItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				JFileChooser jfc = new JFileChooser();
				FileFilter fileFilter = new FileNameExtensionFilter("Morton List File", "mlf");
				jfc.setFileFilter(fileFilter);
				jfc.setDialogType(JFileChooser.SAVE_DIALOG);
				jfc.setDialogTitle("Kontenliste Importieren");
				jfc.setMultiSelectionEnabled(false);
				int result = jfc.showDialog(frame, "Importieren");
				if(result == JFileChooser.APPROVE_OPTION)
				{
					String[] data = fm.ReadFile(jfc.getSelectedFile().getAbsolutePath());
					for(int i = 0; i < data.length; i++)
					{
						String currentAccount = data[i].substring(data[i].indexOf(";") + 1);
						if(!dh.CheckIfAccountExists(currentAccount))
						{
							dh.InsertNewAccount(currentAccount);
						}
					}
					fm.WriteFile(fm.GetUserFile(), dh.GetAccountDataAsStringFormattet());
					UpdateAccountsList();
				}
			}
		});
		
		exportAccountListItem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				JFileChooser jfc = new JFileChooser();
				FileFilter fileFilter = new FileNameExtensionFilter("Morton List File", "mlf");
				jfc.setFileFilter(fileFilter);
				jfc.setDialogType(JFileChooser.SAVE_DIALOG);
				jfc.setDialogTitle("Kontenliste Exportieren");
				jfc.setMultiSelectionEnabled(false);
				int result = jfc.showDialog(frame, "Exportieren");
				if(result == JFileChooser.APPROVE_OPTION)
				{
					fm.WriteFile(jfc.getSelectedFile().getAbsolutePath() + ".mlf", dh.GetAccountDataAsStringFormattet());
				}
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
		UpdateBalanceText();
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
		if(accountList.getSelectedIndex() == -1)
		{
			totalBalanceLabel.setText("Konto auswählen");
			weekSavingDescriptionLabel.setText("");
			monthSavingDescriptionLabel.setText("");
			totalTimeToTargetDateLabel.setText("");
			addTransactionButton.setEnabled(false);
			return;
		}
		else 
		{
			addTransactionButton.setEnabled(true);
		}
		ArrayList<String> transactions = dh.GetTransactionsForAccount(accountList.getSelectedValue().toString());
		UpdateTransactionTable(transactions);
		float target = dh.GetAccountTarget(accountList.getSelectedValue().toString());
		float balance = calc.CalculateTotalBalance(transactions);
		float difference = 0.0f;
		if(target == -1)
		{
			totalBalanceLabel.setText("<html><body>" + balance + Static_Settings.GetCurrency() + "<br><br>Kein Ziel Gesetzt</body></html>");
		}
		else 
		{
			difference = target - balance;
			totalBalanceLabel.setText("<html><body>" + balance + Static_Settings.GetCurrency() + " /<br>" + target + Static_Settings.GetCurrency() + "<br><br>noch " + difference + Static_Settings.GetCurrency() + " bis zum Ziel</body></html>");
		}
		
		//Prüfe ob Target Date vorhanden wenn ja aktiviere das Berechnungsmodul
		String date = dh.GetAccountTargetDate(accountList.getSelectedValue().toString());
		if(date.equals("none"))
		{
			totalTimeToTargetDateLabel.setText("Kein Zieldatum angegeben.");
			weekSavingDescriptionLabel.setText("<html><body>Für die Berechnung muss ein Zieldatum angegeben sein.</body></html>");
			monthSavingDescriptionLabel.setText("<html><body>Für die Berechnung muss ein Zieldatum angegeben sein.</body></html>");
			quarterSavingDescriptionLabel.setText("<html><body>Für die Berechnung muss ein Zieldatum angegeben sein.</body></html>");
			yearSavingDescriptionLabel.setText("<html><body>Für die Berechnung muss ein Zieldatum angegeben sein.</body></html>");
			calculatorTabs.setEnabled(false);
		}
		else 
		{
			int days = calc.CalculateDaysToTargetDate(date);
			CheckTabAvailbillity(days);
			totalTimeToTargetDateLabel.setText("<html><body>Zieldatum: " + date + "<br>" + days + " Tage verbleibend</body></html>");
			calculatorTabs.setEnabled(true);
			if(calc.CalculateWeeksFromDays(days) <= 0)
			{
				days = 7;
			}
			weekSavingDescriptionLabel.setText("<html><body>Noch " + calc.CalculateWeeksFromDays(days) + " Wochen verbleibend. Sparen Sie pro Woche " + String.format("%.02f", calc.CalculateMoneyPerWeek(days, difference)) + Static_Settings.GetCurrency() + " um das Ziel zu erreichen</body></html>");
			monthSavingDescriptionLabel.setText("<html><body>Noch " + calc.CalculateMonthsFromDays(days) + " Monat(e) verbleibend. Sparen Sie pro Monat " + String.format("%.02f", calc.CalculateMoneyPerMonth(days, difference)) + Static_Settings.GetCurrency() + " um das Ziel zu erreichen</body></html>");
			quarterSavingDescriptionLabel.setText("<html><body>Noch " + calc.CalculateQuartersFromDays(days) + " Quartal(e) verbleibend. Sparen Sie pro Quartal " + String.format("%.02f", calc.CalculateMoneyPerQuarter(days, difference)) + Static_Settings.GetCurrency() + " um das Ziel zu erreichen</body></html>");
			yearSavingDescriptionLabel.setText("<html><body>Noch " + calc.CalculateYearsFromDays(days) + " Quartal(e) verbleibend. Sparen Sie pro Quartal " + String.format("%.02f", calc.CalculateMoneyPerYear(days, difference)) + Static_Settings.GetCurrency() + " um das Ziel zu erreichen</body></html>");
		}
	}
	
	private void CheckTabAvailbillity(int days)
	{
		//Check Months
		if(calc.CalculateMonthsFromDays(days) <= 0)
		{
			calculatorTabs.setEnabledAt(1, false);
		}
		else 
		{
			calculatorTabs.setEnabledAt(1, true);
		}
		//Check Quarter
		if(calc.CalculateMonthsFromDays(days) < 3)
		{
			calculatorTabs.setEnabledAt(2, false);
		}
		else 
		{
			calculatorTabs.setEnabledAt(2, true);
		}
		//Check Year
		if(calc.CalculateMonthsFromDays(days) < 12)
		{
			calculatorTabs.setEnabledAt(3, false);
		}
		else 
		{
			calculatorTabs.setEnabledAt(3, true);
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
		if(accounts.size() > 0)
		{
			exportAccountListItem.setEnabled(true);
		}
		else 
		{
			exportAccountListItem.setEnabled(false);
		}
	}
}
